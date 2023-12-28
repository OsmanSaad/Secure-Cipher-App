package com.example.securecipher.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.securecipher.R
import com.example.securecipher.databinding.FragmentPagerBinding

class PagerFragment : Fragment() {
    lateinit var binding: FragmentPagerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagerBinding.inflate(layoutInflater,container,false)
        val fragmentList = listOf(FirstFragment(),
            SecoundFragment(),
            ThirdFragment(),
            FourthFragment())
        val viewPagerAdapter = ViewPagerAdapter(fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.pager2.adapter = viewPagerAdapter
        return binding.root
    }
}