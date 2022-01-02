package com.danpkr.mibanca.ui.activities.Main.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.danpkr.mibanca.databinding.FragmentCardFormBinding
import com.danpkr.mibanca.utils.Validator

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CardFormFragment(val listener: CardFormListener) : BottomSheetDialogFragment() {

    private var binding: FragmentCardFormBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCardFormBinding.inflate(inflater,container,false)
        initView()
        return binding!!.root
    }

    private fun initView(){
        binding!!.textFieldPan.editText?.doOnTextChanged { text, start, before, count ->
            ValidatePanField()
        }

        binding!!.textFieldCardHolder.editText?.doOnTextChanged { text, start, before, count ->
            ValidateNameField()
        }

        binding!!.textFieldExpireDate.editText?.doOnTextChanged { text, start, before, count ->
            ValidateExpDate()
        }

        binding!!.textFieldCVV.editText?.doOnTextChanged { text, start, before, count ->
            ValidateCVC()
        }

        binding!!.AddCardBtn.setOnClickListener {
            if(ValidateFields()){
                listener.onCardAccepted(
                    binding!!.textFieldPan.editText?.text.toString(),
                    binding!!.textFieldExpireDate.editText?.text.toString(),
                    binding!!.textFieldCVV.editText?.text.toString(),
                    binding!!.textFieldCardHolder.editText?.text.toString(),
                )
                dismiss()
            }
        }
    }

    private fun ValidateFields(): Boolean{
        return ValidatePanField()
                && ValidateNameField()
                && ValidateCVC()
                && ValidateExpDate()
    }

    private fun ValidatePanField():Boolean{
        val panEt = binding!!.textFieldPan
        val panField = binding!!.textFieldPan.editText?.text
        if(!Validator.isValidCard(panField.toString())){
            panEt.error = "Ingrese una tarjeta valida"
            return false
        } else {
            panEt.error = null
        }
        return true
    }

    private fun ValidateNameField():Boolean{
        val nameEt = binding!!.textFieldCardHolder
        val nameField = binding!!.textFieldCardHolder.editText?.text
        if(nameField.isNullOrBlank()){
            nameEt.error = "Ingrese el Nombre que aparece en la tarjeta"
            return false
        } else {
            nameEt.error = null
        }
        return true
    }

    private fun ValidateExpDate():Boolean{
        val dateEt = binding!!.textFieldExpireDate
        val dateField = binding!!.textFieldExpireDate.editText?.text
        if(!Validator.isValidExpDate(dateField.toString())){
            dateEt.error = "Ingrese una Fecha mm/yy"
            return false
        } else {
            dateEt.error = null
        }
        return true
    }

    private fun ValidateCVC():Boolean{
        val panEt = binding!!.textFieldCVV
        val panField = binding!!.textFieldCVV.editText?.text
        if(panField.isNullOrBlank()){
            panEt.error = "Ingrese un CVC/CVV"
            return false
        } else {
            panEt.error = null
        }
        return true
    }


    companion object {
        fun newInstance(listener: CardFormListener) = CardFormFragment( listener )
    }
}