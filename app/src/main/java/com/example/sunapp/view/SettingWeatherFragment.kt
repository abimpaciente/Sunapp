package com.example.sunapp.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.sunapp.R
import com.example.sunapp.databinding.SettingsWeatherLayoutBinding
import com.example.sunapp.model.RequestWeather
import com.example.sunapp.model.common.GradeType
import com.example.sunapp.viewModel.WeatherViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SettingWeatherFragment(private var viewModel: WeatherViewModel) :
    BottomSheetDialogFragment() {

    private lateinit var binding: SettingsWeatherLayoutBinding
    lateinit var dataPasser: OnPassRequest

    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as OnPassRequest
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SettingsWeatherLayoutBinding.inflate(layoutInflater)
//        val v = inflater.inflate(R.layout.settings_weather_layout, container, false)


        val adapterGrades =
            ArrayAdapter(requireContext(), R.layout.drop_down_grades, GradeType.values())
        val countries = resources.getStringArray(R.array.Countries)
        val adapterCountries =
            ArrayAdapter(requireContext(), R.layout.drop_down_countries, countries)


        binding.spUnits.adapter = adapterGrades
        binding.spCountries.adapter = adapterCountries

        binding.btnAccept.setOnClickListener {

            val requestWeather =
                RequestWeather(
                    binding.txtZip.text.toString(),
                    binding.spCountries.selectedItem.toString(),
                    (GradeType.valueOf(binding.spUnits.selectedItem.toString()))
                )
            viewModel.getWeather(requestWeather)
            dataPasser.onDataPass(requestWeather)
        }
        return binding.root
    }


    interface OnPassRequest {
        fun onDataPass(data: RequestWeather)
    }
}
