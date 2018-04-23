package com.mincor.kviper.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.mincor.kviper.consts.Consts
import com.mincor.kviper.controllers.MainPageController
import com.mincor.kviper.controllers.SearchWeatherController
import com.mincor.kviper.viper.baseui.actionbar.ActionBarProvider
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class MainActivity : AppCompatActivity(), ActionBarProvider {

    // главный роутер приложения
    var mainRouter: Router? = null

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainRouter = mainRouter ?: let {
            // главный контейнер приложения
            val container = frameLayout { lparams(matchParent, matchParent) }
            Conductor.attachRouter(this, container, savedInstanceState)
        }
        showMainScreenAndCheckPermissions()
    }

    private fun showMainScreenAndCheckPermissions(){
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION), Consts.REQUEST_PERMISSION_CODE)
            return
        }

        if (!mainRouter!!.hasRootController()) {
            // показываем экран предзагрузки пока проверяем подписку
            mainRouter!!.setRoot(RouterTransaction.with(MainPageController()))
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == Consts.REQUEST_PERMISSION_CODE) {
            showMainScreenAndCheckPermissions()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        toolbar?.let {
            super.setSupportActionBar(toolbar)
        }
    }

    override fun onBackPressed() {
        //L.d("ON BACK PRESSED")
        if (!mainRouter!!.handleBack()) {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        //GlideApp.with(this.applicationContext).pauseAllRequests()
        mainRouter = null
        super.onDestroy()
    }

    /*private fun closeApp() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        intent.putExtra("LOGOUT", true)
        startActivity(intent)
    }*/
}
