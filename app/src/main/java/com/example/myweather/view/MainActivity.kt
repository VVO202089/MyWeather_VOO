package com.example.myweather.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myweather.R
import com.example.myweather.view.main.MainFragment

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // тут у нас открывается фрагмент, где и происходит вся движуха (single - activity)
        if(savedInstanceState == null)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment.newInstance())
            .commit()
    }

}