package com.example.sunapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunapp.R
import com.example.sunapp.databinding.FragmentWeekGradesLayaoutBinding
import com.example.sunapp.model.WeatherResponse
import com.example.sunapp.viewModel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*


class FragmentWeekDayLayout : Fragment() {
    private lateinit var binding: FragmentWeekGradesLayaoutBinding
    private val viewModel: WeatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    private lateinit var weatherList: RecyclerView
    private lateinit var kindGrade: String
    private lateinit var adapter: WeekWeatherAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        kindGrade = "standar"
        if (!::binding.isInitialized)
            binding = FragmentWeekGradesLayaoutBinding.inflate(
                inflater,
                container,
                false
            )

        weatherList = binding.weatherListDay
        weatherList.layoutManager = LinearLayoutManager(requireContext())

        viewModel.weatherModel.observe(viewLifecycleOwner) {
            // invoke after changes are "push" to the current Observer.
            updateUI(it)
        }
        viewModel.getWeather("30339", "US", "abca655c8fb6c771b90146dd2e747976", kindGrade)

        return binding.root
    }

    private fun initViews(view: View) {
        weatherList = view.findViewById(R.id.weather_list_day)
        weatherList.layoutManager = LinearLayoutManager(requireContext())

    }

    private fun updateUI(data: WeatherResponse?) {
        data?.let { it ->
            val place = data.city.name
            val city = data.city.country
            var grade = data.list[0].main.temp.toInt().toString()
            grade += "°" + if (kindGrade == "metric") "C" else "F"
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
            adapter = WeekWeatherAdapter(res)
            weatherList.adapter = adapter
        } ?: showError("No response from server")
    }

    private fun showError(errorMessage: String) {
        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT)
    }

}