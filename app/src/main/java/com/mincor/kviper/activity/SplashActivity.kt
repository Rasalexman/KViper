package ru.fortgroup.dpru.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mincor.kviper.activity.MainActivity


/**
 * Created by a.minkin on 21.09.2017.
 */

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showMainActivity()
    }

    /**
     * показыавем основнуюа активити
     */
    private fun showMainActivity() {
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)
        finish()
    }

}
