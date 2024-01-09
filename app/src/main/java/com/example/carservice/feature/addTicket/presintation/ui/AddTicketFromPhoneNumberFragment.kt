package com.example.carservice.feature.addTicket.presintation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.carImg.CarGenerate
import com.example.carservice.core.constant.carImg.CarImageModel
import com.example.carservice.databinding.FragmentAddTicketFromPhoneNumberBinding
import com.example.carservice.feature.addTicket.domain.model.CarResponse
import com.example.carservice.feature.addTicket.presintation.viewModel.AddTicketViewModel


class AddTicketFromPhoneNumberFragment : Fragment() {
    private lateinit var binding: FragmentAddTicketFromPhoneNumberBinding
    private val viewModel: AddTicketViewModel by viewModels()
    private lateinit var imageModel: CarImageModel
    private lateinit var imgId: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddTicketFromPhoneNumberBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataFromBundle()
        onClickChooseCarModel()
        onClickAddTicket()

    }

    private fun onClickAddTicket() {
        binding.btnService.setOnClickListener {
            if (checkFiled()) {
                saveTicket()
            } else {
                showErrorSweet("Fill filled")
            }

        }
    }

    private fun saveTicket() {
        val data = saveDataToBundle()
        cleanField()
        findNavController().navigate(R.id.createTicketFragment, data)
    }


    private fun cleanField() {
        binding.etPhoneNumber.text.clear()
        binding.etCarType.text.clear()
        binding.etUserName.text.clear()
        binding.etCarModel.text.clear()
        binding.etCarColor.text.clear()
        binding.etNote.text.clear()
        binding.rbtNormal.isChecked
        binding.etCode.text.clear()
        binding.etPlateNumber.text.clear()
    }

    private fun checkFiled(): Boolean {
        if (binding.etCarType.text.toString().isEmpty() || binding.etCarColor.text.toString()
                .isEmpty() || binding.etCarModel.text.toString()
                .isEmpty() || binding.etPhoneNumber.text.toString()
                .isEmpty() || binding.etUserName.text.toString().isEmpty()
        ) {
            if (binding.etUserName.text.toString().isEmpty()) {
                showErrorSweet("Add User Name")
                return false
            } else if (binding.etCarType.text.isEmpty()) {
                showErrorSweet("Add Car Type")
                return false

            } else if (binding.etPhoneNumber.text.toString().isEmpty()) {
                showErrorSweet("Add Phone Number")
                return false

            } else if (binding.etCarColor.text.toString().isEmpty()) {
                showErrorSweet("Add car Color")
                return false

            } else {
                showErrorSweet("Error Fill The Field")
                return false

            }
        } else {
            return true
        }
    }

    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }

    private fun saveDataToBundle(): Bundle {
        val bundle = Bundle()
        val carColor = binding.etCarColor.text
        val carModel = binding.etCarModel.text
        val personName = binding.etUserName.text
        val phoneNumber = binding.etPhoneNumber.text
        val customerType = if (binding.rbtNormal.isChecked) 1 else 0
        val note = binding.etNote.text
        val carImg = imgId
        val carType = binding.etCarModel.text
        val plateNumber = "${binding.etCode.text}-${binding.etPlateNumber.text}"
        val code = binding.etCode.text

        bundle.putString("carColor", carColor.toString())
        bundle.putString("carImg", carImg)
        bundle.putString("carModel", carModel.toString())
        bundle.putString("customerName", personName.toString())
        bundle.putString("phoneNumber", phoneNumber.toString())
        bundle.putString("customerType", customerType.toString())
        bundle.putString("plateNumber", plateNumber.toString())
        bundle.putString("code", code.toString())
        bundle.putString("note", note.toString())
        bundle.putString("carType", carType.toString())
        return bundle
    }


    private fun onClickChooseCarModel() {
        binding.btnSearchCar.setOnClickListener {
            val bundle = setBundle()
            findNavController().navigate(R.id.carModelListFragment, bundle)
        }
        binding.etCarType.setOnClickListener {
            val bundle = setBundle()
            findNavController().navigate(R.id.carModelListFragment, bundle)
        }
    }

    private fun setBundle(): Bundle? {
        val bundle = Bundle()
        bundle.putString("PlateNumber", binding.etPlateNumber.text.toString())
        bundle.putString("Code", binding.etCode.text.toString())
        bundle.putString("Note", binding.etNote.text.toString())
        bundle.putString("CarColor", binding.etCarColor.text.toString())
        bundle.putString("UserName", binding.etUserName.text.toString())
        bundle.putString("CarModel", binding.etCarModel.text.toString())
        bundle.putString("PhoneNumber", binding.etPhoneNumber.text.toString())
        return bundle
    }

    private fun getDataFromBundle() {
        val arguments = arguments
        if (arguments != null && arguments.containsKey("carResponse")) {
            val carResponse = arguments.getParcelable<CarResponse>("carResponse")
            if (carResponse != null) {
                setDataToView(carResponse)
            }

        }
    }

    private fun setDataToView(carResponse: CarResponse) {
        try {
            binding.brGif.visibility = View.GONE
            binding.etPhoneNumber.setText(carResponse.phoneNumber)
            binding.etCarColor.setText(carResponse.carColor)
            binding.etCarType.setText(carResponse.carModel?.let {
                CarGenerate.carList().get(it.toInt()).name
            })
            imgId = carResponse.carModel.toString()

            binding.etCarModel.setText(carResponse.carType)
            binding.etUserName.setText(carResponse.customerName)
            binding.etCarColor.setText(carResponse.carColor)
            val (beforeHyphen, afterHyphen) = carResponse.carId!!.split("-")
            binding.etCode.setText(beforeHyphen)
            binding.etPlateNumber.setText(afterHyphen)
        } catch (e: Exception) {
            showErrorSweet(e.localizedMessage)
            Log.d("TAGsetDataToViewATFPNF", "setDataToViewATFPNF:${e.localizedMessage} ")
        }


    }

    private fun getDataFromBundle(arguments: Bundle?) {
        if (arguments != null) {
            setDataToField(arguments)
        }

    }

    private fun setDataToField(carResponse: Bundle) {
        binding.etCarType.setText(carResponse.getString("TypeCar", ""))
        imageModel = CarImageModel(
            id = carResponse.getString("id", "-1"),
            img = carResponse.getInt("img", 0),
            name = carResponse.getString("TypeCar", "")
        )
        Log.d("TAGImageModel", "ImageModel: ${imageModel}")
        val bundle = carResponse.getBundle("bundle")
        Log.d("TAGBundle", "bundle:${bundle} ")
        val plateNumber = bundle?.getString("PlateNumber")
        val carModel = bundle?.getString("CarModel")
        val userName = bundle?.getString("UserName")
        val code = bundle?.getString("Code")
        val phoneNumber = bundle?.getString("PhoneNumber")

        binding.etPlateNumber.setText(plateNumber)
        binding.etCarModel.setText(carModel)
        binding.etUserName.setText(userName)
        binding.etCode.setText(code)
        binding.etPhoneNumber.setText(phoneNumber)
    }


}