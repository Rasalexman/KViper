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
import org.jetbrains.anko.frameLayout
import org.jetbrains.anko.matchParent

class MainActivity : AppCompatActivity(), ru.fortgroup.dpru.activity.actionbar.ActionBarProvider {

    // главный роутер приложения
    var mainRouter: Router? = null

    @SuppressLint("MissingSuperCall")
    override fun onCreate(savedInstanceState: Bundle?) {
        // выход из приложения по запросу из вне
        /*if (intent.getBooleanExtra("LOGOUT", false)) {
            finishActivity(-1)
        }*/
        super.onCreate(savedInstanceState)
        // главный контейнер приложения
        val container = frameLayout { lparams(matchParent, matchParent) }
        mainRouter = mainRouter ?: Conductor.attachRouter(this, container, savedInstanceState)
        if (!mainRouter!!.hasRootController()) {
            // показываем экран предзагрузки пока проверяем подписку
            //mainRouter!!.setRoot(RouterTransaction.with(PreloaderPageController()))
        }

        // получаем данные от firebase
        //fetchFirebaseConfig()
    }

   /* private fun fetchFirebaseConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder().setDeveloperModeEnabled(BuildConfig.DEBUG).build()
        mFirebaseRemoteConfig!!.setConfigSettings(configSettings)
        mFirebaseRemoteConfig!!.setDefaults(R.xml.remote_config_defaults)

        val cacheExpiration: Long = if(mFirebaseRemoteConfig!!.info.configSettings.isDeveloperModeEnabled) 0 else 7200 // 2 hour in seconds.
        mFirebaseRemoteConfig!!.fetch(cacheExpiration).addOnCompleteListener(this, {
            if (it.isSuccessful) {
                // After config data is successfully fetched, it must be activated before newly fetched
                // values are returned.
                mFirebaseRemoteConfig!!.activateFetched()
                val remoteVersionCode = mFirebaseRemoteConfig!!.getLong(Consts.FIREBASE_MIN_APP_VERSION)
                checkForUpdateDialog(remoteVersionCode)
            }
        })
    }*/

    /*private fun checkForUpdateDialog(remoteVersionCode: Long) {
        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            val localVersionCode = pInfo.versionCode

            //FGLogger.e("----------> LOCAL VERSION CODE", String.valueOf(localVersionCode));
            //FGLogger.e("----------> REMOTE VERSION CODE", String.valueOf(remoteVersionCode));

            if (remoteVersionCode > localVersionCode) {
                val builder = AlertDialog.Builder(this@MainActivity)
                builder.setTitle(getString(R.string.update_message_head))
                        .setMessage(getString(R.string.update_message))
                        .setCancelable(false)
                        .setNegativeButton(getString(R.string.update_button)
                        ) { _, _ ->
                            val appPackageName = packageName
                            try {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?guid=$appPackageName")))
                            } catch (anfe: android.content.ActivityNotFoundException) {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?guid=$appPackageName")))
                            }
                        }
                val alert = builder.create()
                alert.show()
            }

        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }*/

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
        /*alert(R.string.exit_body, R.string.warning){
            yesButton { closeApp() }
            noButton {}
        }.show()*/
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
