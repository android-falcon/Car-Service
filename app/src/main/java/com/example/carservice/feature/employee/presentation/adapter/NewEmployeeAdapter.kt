package com.example.carservice.feature.employee.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carservice.databinding.RowEmployeeBinding
import com.example.carservice.feature.employee.domain.model.EmployeeModel

class NewEmployeeAdapter (
    private val employeeList: List<EmployeeModel>,
    private val onItemCheckedListener: (String, Boolean) -> Unit
) : RecyclerView.Adapter<NewEmployeeAdapter.EmployeeViewHolder>() {
    private val employeeServices = mutableListOf<String>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeViewHolder {
        val binding = RowEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return employeeList.size
    }

    override fun onBindViewHolder(holder: EmployeeViewHolder, position: Int) {
        val employee: EmployeeModel =employeeList[position]
        val employeeNumber=employee.code
        holder.bind(employee)
        holder.itemView.setOnClickListener {
            holder.checkBox.isChecked = !holder.checkBox.isChecked
            onItemCheckedListener.invoke(employeeNumber, holder.checkBox.isChecked)
            if (holder.checkBox.isChecked) {
                employeeServices.add(employeeNumber)
            } else {
                employeeServices.remove(employeeNumber)
            }
        }

    }

    class EmployeeViewHolder(private val binding: RowEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val serviceNameArabic = binding.tvEmployee
        val checkBox = binding.cbSelect
        fun bind(item: EmployeeModel) {
            binding.model = item
        }

    }

    fun getSelectedServices(): List<String> {
        return employeeServices.toList()
    }
}