package com.example.carservice.feature.addTicket.presintation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carservice.R
import com.example.carservice.core.constant.carImg.CarGenerate
import com.example.carservice.databinding.RowCarBinding
import com.example.carservice.feature.addTicket.domain.model.CarResponse


class CarAdapter(
    private val items: ArrayList<CarResponse>,
    private val onClick: (CarResponse) -> Unit
) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding = RowCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val item = items[position]
        if (item.carModel.equals("-1")) {
            holder.binding.ivCarType.setImageResource(R.drawable.img_car)
        } else {
            val img = item.carModel?.let { CarGenerate.carList().get(it.toInt()).img }
            holder.binding.ivCarType.setImageResource(img!!)
        }
        holder.bind(item)
        holder.binding.cvData.setOnClickListener {
            onClick(item)
        }
    }


    override fun getItemCount(): Int {
        return items.size
    }

    fun setFilteredList(list: List<CarResponse>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    class CarViewHolder(val binding: RowCarBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CarResponse) {
            binding.model = item
            binding.executePendingBindings()
        }
    }
}