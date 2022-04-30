package com.example.sunapp.view

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.sunapp.R
import com.example.sunapp.model.DayWeather
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

class ItemWeatherAdapter(
    private val dataSet: List<DayWeather>,
    private val kindGrade: String,
    private val OnSelect: (DayWeather) -> Unit
) : RecyclerView.Adapter<ItemWeatherAdapter.ItemWeatherHolder>() {
    class ItemWeatherHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val imageWeather: ImageView = view.findViewById(R.id.iv_day_weather_image)
        private val hourDetail: TextView = view.findViewById(R.id.tv_day_weather_hour)
        private val gradeDetail: TextView = view.findViewById(R.id.tv_day_weather_grade)

        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(
            dataItem: DayWeather, OnClickListener: (DayWeather) -> Unit, kindGrade: String
        ) {
            val urlImage =
                "https://openweathermap.org/img/wn/" + dataItem.weather[0].icon + "@2x.png"

            Picasso.get().load(urlImage)
                .resize(180, 180)
                .centerCrop().into(imageWeather)

            var date = dataItem.dt_txt

            val text = date
            val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            val localDateTime = LocalDateTime.parse(text, pattern)
            date = localDateTime.toLocalTime().toString()
            val hourFormat = SimpleDateFormat("H:mm")
            val asd = hourFormat.parse(date)
            date = SimpleDateFormat("hh:mm aa").format(asd)

            hourDetail.text = date.toString()
            var grade = dataItem.main.temp.toInt().toString() + "°"
            grade += when (kindGrade) {
                "imperial" -> {
                    "F"
                }
                "metric" -> {
                    "C"
                }
                else -> {
                    "K"
                }
            }
            gradeDetail.text = grade
            view.setOnClickListener { OnClickListener(dataItem) }


        }

        companion object {
            private const val TAG = "ItemWeatherAdapter"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemWeatherHolder {
        return ItemWeatherHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.day_weather_layout,
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemWeatherHolder, position: Int) {
        holder.onBind(dataSet[position], OnSelect, kindGrade)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}