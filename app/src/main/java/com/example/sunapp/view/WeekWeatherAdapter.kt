package com.example.sunapp.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sunapp.R
import com.example.sunapp.model.DayWeather
import com.example.sunapp.model.common.GradeType
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private lateinit var weatherList: RecyclerView
private lateinit var adapter: ItemWeatherAdapter

class WeekWeatherAdapter(
    private val dataSet: Map<Int, List<DayWeather>>,
    private val kindGrade: GradeType
) :
    RecyclerView.Adapter<WeekWeatherAdapter.DayHolder>() {


    class DayHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val viewPool = RecyclerView.RecycledViewPool()
        private val textDay: TextView = view.findViewById(R.id.parent_item_title)

        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(dataItem: List<DayWeather>, valueGrade: String) {
            val date = dataItem.first().dt_txt.toString()
            val formatDate = SimpleDateFormat("yyyy-MM-dd")
            val formatDay = SimpleDateFormat("EEEE")
            var day = formatDay.format(formatDate.parse(date))


            val calendar: Calendar = Calendar.getInstance()
            val dayNow: Int = calendar.get(Calendar.DAY_OF_YEAR)
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val localDateTime = LocalDateTime.parse(date, pattern)
            var dayItem = localDateTime.dayOfYear

            if (dayNow == dayItem) {
                textDay.text = "Today"
            } else {
                if (dayNow.plus(1) == dayItem) {
                    textDay.text = "Tomorrow"
                } else {
                    textDay.text = day.toString()
                }
            }



            weatherList = view.findViewById(R.id.child_recyclerview)
            weatherList.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            weatherList.setRecycledViewPool(viewPool)


            adapter = ItemWeatherAdapter(dataItem, kindGrade = valueGrade) { dayDetail ->
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
                R.layout.week_weather_layout,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.onBind(dataSet.values.elementAt(position), kindGrade.valueGrade)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}