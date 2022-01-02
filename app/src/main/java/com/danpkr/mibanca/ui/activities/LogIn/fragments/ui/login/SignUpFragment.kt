package com.danpkr.mibanca.ui.activities.LogIn.fragments.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.danpkr.mibanca.R
import com.danpkr.mibanca.databinding.FragmentSignUpBinding
import com.danpkr.mibanca.ui.activities.LogIn.LoginViewModel
import com.danpkr.mibanca.ui.models.EventType
import com.danpkr.mibanca.ui.models.UiEvent
import com.danpkr.mibanca.utils.Validator


class SignUpFragment : Fragment() {
    private var bind: FragmentSignUpBinding? = null
    private lateinit var vm: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vm = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        bind = FragmentSignUpBinding.inflate(inflater,container,false)
        initViews()
        subscribe()
        return bind!!.root
    }

    private fun subscribe() = vm.event.observe(viewLifecycleOwner,{processUiEvent(it)})


    private fun processUiEvent(event: UiEvent){
        if (event.eventType != EventType.USER_SIGNUP) return
        when(event.succeed){
            true -> {parentFragmentManager
                .beginTransaction()
                .replace(R.id.LogInFragContainer,SignInFragment.newInstance("",""))
                .commit()}
            false -> { Toast.makeText(context,event.message, Toast.LENGTH_SHORT).show()}
        }
    }

    private fun initViews(){
        bind!!.textFieldEmail.editText?.doOnTextChanged{ text, start, before, count ->
            if (!Validator.isValidEmail(text.toString())){
                bind!!.textFieldEmail.error = "Ingrese un email vailido"
            } else {
                bind!!.textFieldEmail.error = null
            }
            bind!!.SignUpButton.isEnabled =
                Validator.isValidEmail(text.toString())
                        && !bind!!.textFieldPassword.editText?.text.isNullOrBlank()
                        && !bind!!.textFieldName.editText?.text.isNullOrBlank()
                        && !bind!!.textFieldLastName.editText?.text.isNullOrBlank()
                        && bind!!.checkboxTerms.isChecked
        }

        bind!!.textFieldPassword.editText?.doOnTextChanged{ text, start, before, count ->
            if (!Validator.isValidPassword(text.toString())){
                bind!!.textFieldPassword.error = "La contraseña debe contener al menos 8 carateres, 1 letra mayuscula, 1 numero"
            } else {
                bind!!.textFieldPassword.error = null
            }
            bind!!.SignUpButton.isEnabled =
                Validator.isValidPassword(text.toString())
                        && !bind!!.textFieldPassword.editText?.text.isNullOrBlank()
                        && !bind!!.textFieldName.editText?.text.isNullOrBlank()
                        && !bind!!.textFieldLastName.editText?.text.isNullOrBlank()
                        && bind!!.checkboxTerms.isChecked
        }

        bind!!.checkboxTerms.setOnCheckedChangeListener { compoundButton, b ->
            bind!!.SignUpButton.isEnabled =
                Validator.isValidPassword(bind!!.textFieldPassword.editText?.text.toString())
                        && !bind!!.textFieldPassword.editText?.text.isNullOrBlank()
                        && !bind!!.textFieldName.editText?.text.isNullOrBlank()
                        && !bind!!.textFieldLastName.editText?.text.isNullOrBlank()
                        && b
        }

        bind!!.SignUpButton.setOnClickListener{
            val name = bind!!.textFieldName.editText?.text.toString()
            val lname = bind!!.textFieldLastName.editText?.text.toString()
            val email = bind!!.textFieldEmail.editText?.text.toString()
            val password = bind!!.textFieldPassword.editText?.text.toString()
            vm.RegisterNewUser(name,lname,email,password)
        }
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignUpFragment()
    }
}