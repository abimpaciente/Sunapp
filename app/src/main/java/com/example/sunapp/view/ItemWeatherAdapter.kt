package com.example.sunapp.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sunapp.R
import com.example.sunapp.model.DayWeather
import com.example.sunapp.model.URL_IMG
import com.squareup.picasso.Picasso

class ItemWeatherAdapter(
    private val dataSet: List<DayWeather>,
    private val OnSelect: (DayWeather) -> Unit
) : RecyclerView.Adapter<ItemWeatherAdapter.ItemWeatherHolder>() {
    class ItemWeatherHolder(private val view: View) :
        RecyclerView.ViewHolder(view) {

        private val imageWeather: ImageView = view.findViewById(R.id.iv_main_weather)
        private val hourDetail: TextView = view.findViewById(R.id.tv_hour_detail)
        private val gradeDetail: TextView = view.findViewById(R.id.tv_grade_detail)

        fun onBind(
            dataItem: DayWeather, OnClickListener: (DayWeather) -> Unit
        ) {
            Picasso.get().load(URL_IMG.replace("valImage", dataItem.weather[0].icon)).into(imageWeather)
            hourDetail.text = dataItem.weather[0].main
            gradeDetail.text = dataItem.main.temp.toString()
//            priceMedia.text = dataItem.trackPrice + dataItem.currency
            view.setOnClickListener { OnClickListener(dataItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemWeatherHolder {
        return ItemWeatherHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_temperature_detail,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemWeatherHolder, position: Int) {
        holder.onBind(dataSet[position], OnSelect)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}