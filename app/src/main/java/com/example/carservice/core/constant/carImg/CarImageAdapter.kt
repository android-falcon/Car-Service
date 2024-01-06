package com.example.carservice.core.constant.carImg

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carservice.databinding.RowCarModelBinding
import com.example.carservice.feature.home.domain.model.TicketResponse


class CarImageAdapter(
    private val items: ArrayList<CarImageModel>,
    private val onClick: (CarImageModel) -> Unit
) : RecyclerView.Adapter<CarImageAdapter.CarImageViewHolder>() {
    class CarImageViewHolder(val binding: RowCarModelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarImageModel) {
            binding.model = item
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarImageViewHolder {
        val binding = RowCarModelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarImageViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: CarImageViewHolder, position: Int) {
        var item = items.get(position)
        holder.bind(item)
        holder.binding.ivCarlogo.setImageResource(item.img)
        holder.itemView.setOnClickListener {
            onClick(item)
        }
    }
    fun setFilteredList(list: List<CarImageModel>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}