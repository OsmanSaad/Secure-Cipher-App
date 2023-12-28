package com.example.securecipher

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.securecipher.databinding.AlgorithmItemBinding

class AlgorithmAdapter(private val algorithms:List<Algorithm>,val onItemClickListener: OnItemClickListener):
    RecyclerView.Adapter<AlgorithmAdapter.ViewHolder>() {


    override fun getItemCount():Int = algorithms.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = AlgorithmItemBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
        if(onItemClickListener!=null){
            holder.binding.root.setOnClickListener {
                onItemClickListener.onItemClick(algorithms[position])
            }
        }
    }

    inner class ViewHolder(val binding: AlgorithmItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            val item = algorithms[position]
            binding.algorithmImg.setImageResource(item.image)
            binding.algorithmName.text = item.name
            binding.algorithmRate.rating = item.rate
        }
    }

}

interface OnItemClickListener{
    fun onItemClick(item:Algorithm)
}
