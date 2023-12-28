package com.example.securecipher.onboarding

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.securecipher.R
import com.example.securecipher.databinding.FragmentFourthBinding

class FourthFragment : Fragment() {
    lateinit var binding: FragmentFourthBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFourthBinding.inflate(layoutInflater,container,false)

        binding.gobtn.setOnClickListener {
            findNavController().navigate(R.id.action_pagerFragment_to_homeFragment)
            finishOnBoarding()
        }

        return binding.root
    }


    private fun finishOnBoarding(){
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("state",true)
        editor.commit()
    }

}