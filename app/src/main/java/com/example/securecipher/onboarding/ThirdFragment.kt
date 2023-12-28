package com.example.securecipher.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.securecipher.R
import com.example.securecipher.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {
    lateinit var binding: FragmentThirdBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentThirdBinding.inflate(layoutInflater,container,false)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.pager2)

        binding.nextBtn3.setOnClickListener {
            viewpager?.currentItem = 3
        }

        return binding.root
    }
}