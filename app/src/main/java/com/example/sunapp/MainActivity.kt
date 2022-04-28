package com.example.sunapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sunapp.view.FragmentListDayLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction().replace(R.id.container,FragmentListDayLayout())
            .commit()
    }
}