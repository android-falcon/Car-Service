package com.example.carservice.feature.services.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carservice.databinding.RowServiceBinding
import com.example.carservice.feature.services.domain.model.ServiceBodyModel
import com.example.carservice.feature.services.domain.model.ServiceModel

class NewServiceAdapter(
    private val serviceList: List<ServiceModel>,
    private val onItemCheckedListener: (ServiceBodyModel, Boolean) -> Unit
) : RecyclerView.Adapter<NewServiceAdapter.ServiceViewHolder>() {
    private val selectedServices = mutableListOf<ServiceBodyModel>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ServiceViewHolder {
        val binding = RowServiceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ServiceViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service:ServiceModel =serviceList[position]
        val serviceBody:ServiceBodyModel = ServiceBodyModel(serviceList[position].itemCode,serviceList[position].nameEnglish)
        holder.bind(service)
        holder.itemView.setOnClickListener {
            holder.checkBox.isChecked = !holder.checkBox.isChecked
            onItemCheckedListener.invoke(serviceBody, holder.checkBox.isChecked)
            if (holder.checkBox.isChecked) {
                selectedServices.add(serviceBody)
            } else {
                selectedServices.remove(serviceBody)
            }
        }

    }

    class ServiceViewHolder(private val binding: RowServiceBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val serviceNameArabic = binding.tvEmployee
        val checkBox = binding.cbSelect
        fun bind(item: ServiceModel) {
            binding.model = item
        }

    }

    fun getSelectedServices(): List<ServiceBodyModel> {
        return selectedServices.toList()
    }
}