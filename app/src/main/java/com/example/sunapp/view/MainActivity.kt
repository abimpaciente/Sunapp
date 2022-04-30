package com.example.sunapp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunapp.databinding.ActivityMainBinding
import com.example.sunapp.model.RequestWeather
import com.example.sunapp.model.WeatherResponse
import com.example.sunapp.model.common.GradeType
import com.example.sunapp.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var weatherList: RecyclerView
    private lateinit var adapter: WeekWeatherAdapter
    private lateinit var requestWeather: RequestWeather
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
        } ?: showError("No response from server")
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

    private fun initViews() {


        requestWeather =
            RequestWeather(
                "30339",
                "US",
                "abca655c8fb6c771b90146dd2e747976",
                GradeType.FAHRENHEIT
            )
        weatherList = binding.parentRecyclerview
        weatherList.layoutManager = LinearLayoutManager(this)


    }


}