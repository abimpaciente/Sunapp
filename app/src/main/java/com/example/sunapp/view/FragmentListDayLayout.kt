package com.example.sunapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sunapp.databinding.FragmentListDayLayoutBinding
import com.example.sunapp.databinding.GradesLayaoutBinding

class FragmentListDayLayout : Fragment() {
    private lateinit var binding: GradesLayaoutBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = GradesLayaoutBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }
}