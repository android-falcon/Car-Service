package com.example.carservice.feature.carModelList

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.carservice.R
import com.example.carservice.core.constant.carImg.CarGenerate
import com.example.carservice.core.constant.carImg.CarImageAdapter
import com.example.carservice.core.constant.carImg.CarImageModel
import com.example.carservice.databinding.FragmentCarModelListBinding
import com.example.carservice.feature.carModelList.viewModel.CarViewModel
import com.example.carservice.feature.home.domain.model.TicketResponse
import java.util.Locale


class CarModelListFragment : Fragment() {
    private lateinit var binding: FragmentCarModelListBinding
    private lateinit var adapter: CarImageAdapter
    private lateinit var bundle1: Bundle
    private val viewModel: CarViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCarModelListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        getDataFromBundle(arguments)
        onClickAdd()
        onSearch()

    }

    private fun onSearch() {
        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                false
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterList(s.toString())
                true
            }

            override fun afterTextChanged(s: Editable?) {
                false
            }

        })
    }

    private fun filterList(newText: String?) {
        if (newText != null) {
            val filterList = ArrayList<CarImageModel>()
            for (i in CarGenerate.carList()) {
                if (i.name.contains(newText)
                ) {
                    filterList.add(i)
                }
            }
            if (filterList.isEmpty()) {
                Toast.makeText(requireContext(), "No Data Found", Toast.LENGTH_SHORT).show()

            } else {
                adapter.setFilteredList(filterList)
            }

        }
    }


    private fun onClickAdd() {
        binding.btnAdd.setOnClickListener {
            if (binding.etOtherType.text.isNotEmpty()) {
                onSelectedItem(
                    CarImageModel(
                        name = binding.etOtherType.text.toString(),
                        id = "-1",
                        img = 0
                    )
                )
            }
        }
    }

    private fun getDataFromBundle(arguments: Bundle?) {
        if (arguments != null) {
            bundle1 = arguments
            Log.d("TAGetDataFromBundle", "getDataFromBundle:${bundle1} ")
        }

    }

    private fun initAdapter() {
        adapter = CarImageAdapter(CarGenerate.carList(), ::onSelectedItem)
        binding.rvCars.adapter = adapter
    }


    private fun onSelectedItem(carImageModel: CarImageModel) {
        val bundle = Bundle()
        val key = "2"
        bundle.apply {
            bundle.putString("TypeCar", carImageModel.name)
            bundle.putInt("img", carImageModel.img)
            bundle.putString("id", carImageModel.id)
            bundle.putString("key", key)
            bundle.putBundle("bundle", bundle1)

            Log.d("TAGonSelectedItem", "onSelectedItem:${bundle} ")

        }

        findNavController().navigate(R.id.addTicketFragment, bundle)


    }


}