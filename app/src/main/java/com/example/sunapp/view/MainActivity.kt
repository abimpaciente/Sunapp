package com.example.sunapp.view

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunapp.databinding.ActivityMainBinding
import com.example.sunapp.model.RequestWeather
import com.example.sunapp.model.WeatherResponse
import com.example.sunapp.model.common.GradeType
import com.example.sunapp.viewModel.WeatherViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity(), SettingWeatherFragment.OnPassRequest {


    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherList: RecyclerView
    private lateinit var adapter: WeekWeatherAdapter
    private lateinit var requestWeather: RequestWeather
    private lateinit var bottomSheet: BottomSheetDialogFragment
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()


        viewModel.weatherModel.observe(this) {
            // invoke after changes are "push" to the current Observer.
            updateUI(it)


        }
        viewModel.error.observe(this) {

            val json = JSONObject(it)
            val message = json.getString("message")
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
            //Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
        }

        viewModel.getWeather(
            requestWeather
        )
    }

    private fun updateUI(data: WeatherResponse?) {
        data?.let { it ->
            val place = data.city.name
            val city = data.city.country
            var grade =
                data.list[0].main.temp.toInt().toString() + requestWeather.gradeType.valueGrade
            val main = data.list[0].weather[0].main
            binding.tvPlace.text = "$place,$city"
            binding.tvGrades.text = "$grade"
            binding.tvMain.text = main
            val format = SimpleDateFormat("yyyy-MM-dd")
            val calendar = GregorianCalendar.getInstance()
            val res = it.list.groupBy { item ->
                val date = format.parse(item.dt_txt)
                calendar.time = date
                calendar.get(Calendar.DAY_OF_YEAR)
            }
            adapter = WeekWeatherAdapter(res, requestWeather.gradeType)
            weatherList.adapter = adapter


            binding.ivSettings.setOnClickListener { openSettingFragment() }
            if (bottomSheet.isVisible)
                bottomSheet.dismiss()

        } ?: showError("No response from server")
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }


    private fun initViews() {

        requestWeather =
            RequestWeather(
                "30339",
                "US",
                GradeType.FAHRENHEIT
            )
        weatherList = binding.parentRecyclerview
        weatherList.layoutManager = LinearLayoutManager(this)
        bottomSheet = SettingWeatherFragment(viewModel)
    }

    private fun openSettingFragment() {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container_settings, SettingWeatherFragment(viewModel))
//            .addToBackStack(null)
//            .commit()

        if (!bottomSheet.isVisible)
            bottomSheet.show(supportFragmentManager, "settings")

    }

    override fun onDataPass(data: RequestWeather) {
        requestWeather = data
    }


}