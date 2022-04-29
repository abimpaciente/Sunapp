package com.example.sunapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sunapp.databinding.ActivityMainBinding
import com.example.sunapp.view.FragmentListDayLayout

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        openFragmentWeather()
    }

    private fun openFragmentWeather(){
        supportFragmentManager.beginTransaction().replace(R.id.container,FragmentListDayLayout())
            .commit()
    }

}