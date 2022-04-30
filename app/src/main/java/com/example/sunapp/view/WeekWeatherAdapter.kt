package com.example.sunapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunapp.R
import com.example.sunapp.databinding.FragmentWeatherDayLayoutBinding
import com.example.sunapp.databinding.ItemTemperatureDetailBinding
import com.example.sunapp.model.DayWeather
import java.text.SimpleDateFormat

private lateinit var binding: FragmentWeatherDayLayoutBinding
private lateinit var weatherList: RecyclerView
private lateinit var kindGrade: String
private lateinit var adapter: ItemWeatherAdapter

class WeekWeatherAdapter(
    private val dataSet: Map<Int, List<DayWeather>>
) :
    RecyclerView.Adapter<WeekWeatherAdapter.DayHolder>() {


    class DayHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val textDay: TextView = view.findViewById(R.id.tv_day_detail)

        fun onBind(dataItem: List<DayWeather>) {
            val date = dataItem.first().dt_txt.toString()
            val formatDate = SimpleDateFormat("yyyy-MM-dd")
            val formatDay = SimpleDateFormat("EEEE")
            var day = formatDay.format(formatDate.parse(date))
            textDay.text = day.toString()


//            mediaList = view.findViewById(R.id.movie_list_rock)
//            weatherList = binding.hoursList
            weatherList = view.findViewById(R.id.hours_list)
            weatherList.layoutManager = LinearLayoutManager(view.context)


            adapter = ItemWeatherAdapter(dataItem) { dayDetail ->
                showDetails(dayDetail)
            }
            weatherList.adapter = adapter

        }

        private fun showDetails(dayDetail: DayWeather) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        return DayHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fragment_weather_day_layout,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.onBind(dataSet.values.elementAt(position))
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}