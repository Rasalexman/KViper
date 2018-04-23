package com.mincor.kviper.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
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
        // главный контейнер приложения
        val container = frameLayout { lparams(matchParent, matchParent) }
        mainRouter = mainRouter ?: Conductor.attachRouter(this, container, savedInstanceState)
        if (!mainRouter!!.hasRootController()) {
            // показываем экран предзагрузки пока проверяем подписку
            mainRouter!!.setRoot(RouterTransaction.with(SearchWeatherController()))
        }
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
