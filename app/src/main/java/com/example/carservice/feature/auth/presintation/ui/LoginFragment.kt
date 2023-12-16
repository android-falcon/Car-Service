package com.example.carservice.feature.auth.presintation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.carservice.R
import com.example.carservice.core.constant.NetworkUtils
import com.example.carservice.core.constant.TechnicalData
import com.example.carservice.databinding.FragmentLoginBinding
import com.example.carservice.feature.auth.domain.model.UserModel
import com.example.carservice.feature.auth.presintation.viewModel.LoginViewModel
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTechnicalData()
        loginClick()
    }

    private fun getTechnicalData() {

        if (TechnicalData.COMPANY_NUMBER.isEmpty() || TechnicalData.COMPANY_NUMBER.isEmpty() || TechnicalData.BASE_URL.isEmpty()) {
            Toast.makeText(requireContext(), "Technical Data Empty", Toast.LENGTH_SHORT).show()
        }

        Log.d(
            "getTechnicalData",
            "getTichnicalData COMPANY_NUMBER: ${TechnicalData.COMPANY_NUMBER}")
        Log.d("getTechnicalData", "getTichnicalData COMPANY_NAME: ${TechnicalData.COMPANY_NAME}")
        Log.d("getTechnicalData", "getTichnicalData BASE_URL: ${TechnicalData.BASE_URL}")

    }


    private fun loginClick() {
        binding.btnLogin.setOnClickListener {
            binding.brGif.visibility = View.VISIBLE
            if (binding.etPassword.text.isEmpty()) {
                binding.etPassword.setError("Error Add Password")
            } else if (binding.etUserName.text.isEmpty()) {
                binding.etUserName.setError("Error Add UserName")

            } else if (
                binding.etPassword.text.toString()=="123F" && binding.etUserName.text.toString()     =="123F") {
                findNavController().navigate(R.id.technicalSetttingFragment)
            } else {
                loginFromApi()
            }

        }
        binding.brGif.visibility = View.GONE
    }


    private fun loginFromApi() {
        try {
            lifecycleScope.launch {

                viewModel.loginUser(
                    TechnicalData.COMPANY_NUMBER,
                    binding.etUserName.text.toString()
                )
                viewModel.userListLiveData.observe(viewLifecycleOwner) { data ->
                    var user = data.get(0)
                    if (user.password.equals(binding.etPassword.text.toString())) {
                        saveData(user)
                        checkUserType(user)
                    } else {
                        showErrorSweet("Password isn't Correct")
                    }

                }
            }
            lifecycleScope.launch {
                viewModel.loadingLiveData.observe(viewLifecycleOwner) { loading ->
                    if (loading) {
                        binding.brGif.visibility = View.VISIBLE
                    } else {
                        binding.brGif.visibility = View.GONE
                    }
                }
            }
            lifecycleScope.launch {
                viewModel.errorLiveData.observe(viewLifecycleOwner) {
                    if (it != null) {
                        showErrorSweet(it.toString())
                        Log.d("TAGloginFromApi", "loginFromApi:${it.toString()} ")

                    }
                }
            }


        } catch (e: Exception) {
            Log.d("TAGloginFromApi", "loginFromApi:${e.localizedMessage} ")
            showErrorSweet(e.localizedMessage)
            Log.d("TAGloginFromApi", "loginFromApi:${e.localizedMessage} ")
        }

    }

    private fun checkUserType(user: UserModel) {
        if (user.userType.equals("0")) {
            findNavController().navigate(R.id.homeFragment)
        } else if (user.userType.equals("1")) {
            findNavController().navigate(R.id.addTicketFragment)
        }


    }

    private fun saveData(user: UserModel) {
        var prefrance = context?.getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor = prefrance?.edit()
        editor?.putString("userName", user.userName)
        editor?.putString("userType", user.userType)
        editor?.putString("password", user.password)
        editor?.putString("userNumber", user.userNumber)
        editor?.apply()
    }

    private fun checkFiledNotNull(): Boolean {

        if (binding.etPassword.text.toString().isEmpty() || binding.etUserName.text.toString()
                .isEmpty()
        ) {

            binding.textViewErrorUserName.visibility = View.VISIBLE
            binding.textViewErrorPassword.visibility = View.VISIBLE
            binding.textViewErrorUserName.text = "The UserName is Empty !"
            binding.textViewErrorPassword.text = "The Password is Empty !"
            return false

        } else if (binding.etPassword.text.toString().isEmpty()) {
            binding.textViewErrorPassword.visibility = View.VISIBLE
            binding.textViewErrorUserName.visibility = View.GONE
            binding.textViewErrorPassword.text = "The Password is Empty !"
            return false

        } else if (binding.etUserName.text.toString().isEmpty()) {
            binding.textViewErrorUserName.visibility = View.VISIBLE
            binding.textViewErrorPassword.visibility = View.GONE
            binding.textViewErrorUserName.text = "The UserName is Empty !"
            return false

        }
        return true
    }

    private fun showErrorSweet(message: String) {
        SweetAlertDialog(
            requireContext(), SweetAlertDialog.ERROR_TYPE
        ).setTitleText("Error").setContentText(message).show()

    }


}