package com.asep.mysharedpreferences.preferencessettingscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.asep.mysharedpreferences.R

class MainActivitySettingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_setting_screen)

        supportFragmentManager.beginTransaction().add(R.id.setting_holder, MyPreferenceFragment()).commit()
    }

}