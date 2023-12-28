package com.example.securecipher.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.securecipher.R
import com.example.securecipher.databinding.FragmentFirstBinding

class FirstFragment : Fragment() {
    lateinit var binding: FragmentFirstBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFirstBinding.inflate(layoutInflater,container,false)

        binding = FragmentFirstBinding.inflate(layoutInflater,container,false)

        val viewpager = activity?.findViewById<ViewPager2>(R.id.pager2)

        binding.nextBtn1.setOnClickListener {
            viewpager?.currentItem = 1
        }
        return binding.root
    }

}