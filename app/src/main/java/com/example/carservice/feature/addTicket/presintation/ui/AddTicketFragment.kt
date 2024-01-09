package com.example.carservice.feature.addTicket.presintation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.core.constant.carImg.CarGenerate
import com.example.carservice.core.constant.carImg.CarImageModel
import com.example.carservice.databinding.FragmentAddTicketBinding
import com.example.carservice.feature.addTicket.domain.model.CarResponse
import com.example.carservice.feature.addTicket.presintation.viewModel.AddTicketViewModel
import kotlinx.coroutines.launch


class AddTicketFragment : Fragment() {
    private lateinit var binding: FragmentAddTicketBinding
    private val viewModel: AddTicketViewModel by viewModels()
    private lateinit var imageModel: CarImageModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTicketBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.brGif.visibility = View.GONE
        getDataFromBundle(arguments)
        onClickChooseCarModel()
        onClickAddTicket()
        onClickCheckPhoneNumber()
        onClickCheckPlateNumber()

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

    private fun setBundle(): Bundle {
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


    private fun getDataFromBundle(arguments: Bundle?) {
        if (arguments != null) {
            setDataToField(arguments)
        }

    }

    private fun setDataToField(carResponse: Bundle) {
        if (carResponse.getString("key").toString().equals("2")) {
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


        } else if (carResponse.getString("key").toString().equals("1")) {
            Log.d("TAGsetDataToField", "setDataToField:${carResponse} ")
            val response: CarResponse = carResponse.getParcelable("carResponse")!!
            Log.d("TAGsetDataToField", "setDataToField:${response}")
            val splitParts = response.carId.toString().split('-')
            val code = splitParts[0]
            val plateNumber = splitParts[1]
            imageModel = CarGenerate.getCarByID(response.carModel!!)
            binding.etPlateNumber.setText(plateNumber)
            binding.etCode.setText(code)
            binding.etPhoneNumber.setText(response.phoneNumber)
            binding.etCarType.setText(imageModel.name)
            binding.etCarModel.setText(response.carType)
            binding.etUserName.setText(response.customerName)
            binding.etCarColor.setText(response.carColor)
            if (response.customerType.equals("1")) {
                binding.rbtNormal.isChecked = true
                binding.rbtnBusinessBark.isChecked = false
            } else if (response.customerType.equals("0")) {
                binding.rbtnBusinessBark.isChecked = false
                binding.rbtnBusinessBark.isChecked = true
            }
        }
        //get data from check phone number
        else if (carResponse.getString("key").toString().equals("1001")) {
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

    private fun onClickCheckPhoneNumber() {

        binding.btnCheck.setOnClickListener {
            if (!binding.etPhoneNumber.text.toString().isEmpty()) {
                observerPhoneNumberData(formatPhoneNumber(binding.etPhoneNumber.text.toString()))
            } else {
                binding.etPhoneNumber.error = "add phone number"
            }
        }
    }

    private fun onClickCheckPlateNumber() {

        binding.btnCheckPlateCode.setOnClickListener {
            if (!binding.etCode.text.toString().isEmpty() && !binding.etPlateNumber.text.toString()
                    .isEmpty()
            ) {
                val plateNumber = "${binding.etCode.text}-${binding.etPlateNumber.text}"
                observerPlateNumberData(plateNumber)
            } else {
                binding.etCode.error = "add plate number"
                binding.etPlateNumber.error = "add plate number"
            }
        }
    }

    private fun observerPhoneNumberData(phoneNumber: String) {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getCustomerByPhoneNumber(TechnicalData.COMPANY_NUMBER, phoneNumber)
                viewModel.carListLiveData.observe(viewLifecycleOwner) {
                    if (!it.isEmpty()) {
                        goToCarListPage(phoneNumber)
                    } else {
                        showErrorSweet("no Data for this number")
                    }
                }
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) binding.brGif.visibility = View.VISIBLE
                    else binding.brGif.visibility = View.GONE

                }
                viewModel._errorLiveData.observe(viewLifecycleOwner) {
                    if (!it.isEmpty()) {
                        showErrorSweet(it)
                        viewModel._errorLiveData.value = ""
                    }
                }
            }
        } catch (e: Exception) {
            showErrorSweet(e.localizedMessage)
        }
    }

    private fun observerPlateNumberData(plateNumber: String) {
        try {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.getCustomerByPlateNumber(TechnicalData.COMPANY_NUMBER, plateNumber)
                viewModel.carListLiveData.observe(viewLifecycleOwner) {
                    if (!it.isEmpty()) {
                        goToCarListPagePlateNumber(plateNumber)
                    } else {
                        showErrorSweet("no Data for this number")
                    }
                }
                viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                    if (it) binding.brGif.visibility = View.VISIBLE
                    else binding.brGif.visibility = View.GONE

                }
                viewModel._errorLiveData.observe(viewLifecycleOwner) {
                    if (!it.isEmpty()) {
                        showErrorSweet(it)
                        viewModel._errorLiveData.value = ""
                    }
                }
            }
        } catch (e: Exception) {
            showErrorSweet(e.localizedMessage)
        }
    }

    private fun goToCarListPage(phoneNumber: String) {
        val bundle = Bundle()
        bundle.putString("phoneNumber", phoneNumber)
        var carFragment = CarFragment()
        carFragment.arguments = bundle
        findNavController().navigate(
            R.id.action_addTicketFragment_to_checkPhoneNumberFragment,
            bundle
        )
    }

    private fun goToCarListPagePlateNumber(phoneNumber: String) {
        val bundle = Bundle()
        bundle.putString("plateNumber", phoneNumber)
        findNavController().navigate(R.id.carFragment, bundle)
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
        val carImg = imageModel.id
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

    private fun formatPhoneNumber(inputNumber: String): String {
        // Check if the number starts with "07" and is 10 characters long
        if (inputNumber.length == 10 && inputNumber.startsWith("07")) {
            // Add "962" at the beginning and remove the leading "0"
            return "962${inputNumber.substring(1)}"
        }

        // If the number doesn't match the above condition, return it as is
        return inputNumber
    }


}