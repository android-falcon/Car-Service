package com.example.carservice.feature.employee.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carservice.databinding.RowEmployeeBinding
import com.example.carservice.feature.employee.domain.model.EmployeeModel

class EmployeeAdapter(
    private val items: List<EmployeeModel>,
    private val onClick: (EmployeeModel, Boolean) -> Unit
) : RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = RowEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.binding.cbSelect.setOnCheckedChangeListener { _, isCheacked ->
            onClick(item, isCheacked)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class EmployeeViewHolder(val binding: RowEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EmployeeModel) {
            binding.model = item
            binding.executePendingBindings()
        }
    }
}