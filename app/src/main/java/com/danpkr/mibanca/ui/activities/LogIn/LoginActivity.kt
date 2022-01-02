package com.danpkr.mibanca.ui.activities.LogIn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.danpkr.mibanca.databinding.ActivityMainBinding
import com.danpkr.mibanca.ui.activities.LogIn.fragments.ui.login.SignInFragment
import com.danpkr.mibanca.ui.activities.LogIn.fragments.ui.login.SignUpFragment

class LoginActivity : AppCompatActivity() {
    private lateinit var bind: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)
        loadDefaultScreen()
    }

    private fun loadDefaultScreen(){
        supportFragmentManager
            .beginTransaction()
            .add(bind.LogInFragContainer.id, SignInFragment.newInstance("",""))
            .commit()
    }
}