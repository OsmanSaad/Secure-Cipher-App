package com.example.securecipher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.securecipher.databinding.FragmentHomeBinding
import com.google.android.material.appbar.AppBarLayout


class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater,container,false)
        initViews()
        return binding.root
    }



    private fun initViews(){
        val algorithms = Algorithm.getAlgorithms()
        val algorithmsAdapter = AlgorithmAdapter(algorithms,object :OnItemClickListener{
            override fun onItemClick(item: Algorithm) {
                val action =  HomeFragmentDirections.actionHomeFragmentToAlgorithmFragment(item)
                findNavController().navigate(action)
            }

        })
        binding.algorithmsResc.adapter = algorithmsAdapter

        binding.appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->

            val totalScrollRange = appBarLayout.totalScrollRange
            val currentScroll = totalScrollRange + verticalOffset

            if (currentScroll == 0) {
                binding.toolbartitle.visibility = View.VISIBLE
            } else {
                binding.toolbartitle.visibility = View.INVISIBLE
            }
        })
    }
}