package com.danpkr.mibanca.ui.activities.LogIn.fragments.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isNotEmpty
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.danpkr.mibanca.R
import com.danpkr.mibanca.databinding.FragmentSignInBinding
import com.danpkr.mibanca.ui.activities.LogIn.LoginViewModel
import com.danpkr.mibanca.ui.activities.Main.MainActivity
import com.danpkr.mibanca.ui.models.EventType
import com.danpkr.mibanca.ui.models.UiEvent
import com.danpkr.mibanca.utils.Validator
import kotlin.concurrent.fixedRateTimer

class SignInFragment : Fragment() {

    private var bind: FragmentSignInBinding? = null
    private lateinit var vm: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        bind = FragmentSignInBinding.inflate(inflater,container,false)
        initView()
        subscribe()
        return bind!!.root
    }

    private fun subscribe() = vm.event.observe(viewLifecycleOwner,{processUiEvent(it)})


    private fun processUiEvent(event: UiEvent){
        if (event.eventType != EventType.USER_LOGIN) return
        when(event.succeed){
            true -> {
                startActivity(Intent(context,MainActivity::class.java))
                activity?.finish()
            }
            false -> { Toast.makeText(context,event.message,Toast.LENGTH_SHORT).show()}
        }
    }

    private fun initView(){
        bind!!.signUoButton.setOnClickListener{
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.LogInFragContainer,SignUpFragment.newInstance("",""))
                .addToBackStack("SignUp")
                .commit()
        }

        bind!!.textFieldEmail.editText?.doOnTextChanged{ text, start, before, count ->
            if (!Validator.isValidEmail(text.toString())){
                bind!!.textFieldEmail.error = "Ingrese un email vailido"
            } else {
                bind!!.textFieldEmail.error = null
            }
            bind!!.SignInButton.isEnabled = Validator.isValidEmail(text.toString()) && !bind!!.textFieldPassword.editText?.text.isNullOrBlank()
        }

        bind!!.textFieldPassword.editText?.doOnTextChanged{ text, start, before, count ->
            if (!Validator.isValidPassword(text.toString())){
                bind!!.textFieldPassword.error = "La contraseña debe contener al menos 8 carateres, 1 letra mayuscula, 1 numero"
            } else {
                bind!!.textFieldPassword.error = null
            }
            bind!!.SignInButton.isEnabled = Validator.isValidPassword(text.toString()) && !bind!!.textFieldPassword.editText?.text.isNullOrBlank()
        }

        bind!!.SignInButton.setOnClickListener{
            val email = bind!!.textFieldEmail.editText?.text.toString()
            val password = bind!!.textFieldPassword.editText?.text.toString()
            vm.LoginUser(email,password)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SignInFragment()
    }
}