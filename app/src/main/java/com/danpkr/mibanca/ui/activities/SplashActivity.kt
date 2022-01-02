package com.danpkr.mibanca.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.danpkr.mibanca.R
import com.danpkr.mibanca.ui.activities.LogIn.LoginActivity
import com.dts.dbmodule.Providers.DbProvider

class SplashActivity : AppCompatActivity() {

    private val TIME:Long =  3 * 1000
    private val timer = object: CountDownTimer(TIME,1000){
        override fun onTick(p0: Long) {}
        override fun onFinish() {
            enterApp()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        DbProvider.init(applicationContext)
        timer.start()
    }

    private fun enterApp(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}