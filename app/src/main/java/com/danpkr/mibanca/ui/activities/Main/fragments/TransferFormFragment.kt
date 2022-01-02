package com.danpkr.mibanca.ui.activities.Main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.danpkr.mibanca.R
import com.danpkr.mibanca.databinding.FragmentTransferFormBinding
import com.danpkr.mibanca.utils.Validator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class TransferMode{
    BASIC_TRANSFER, RECHARGE_TRANSFER
}

class TransferFormFragment(val listener: TransferFormListener, val cardValues: List<String>, val mode: TransferMode) : BottomSheetDialogFragment() {

    lateinit var binding : FragmentTransferFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransferFormBinding.inflate(inflater,container,false)
        initViews()
        return binding.root
    }

    private fun initViews(){
        if (mode == TransferMode.RECHARGE_TRANSFER){
            setToRechargeTransfer()
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, cardValues)
        (binding.textFieldCardSelec.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        binding.TransferBtn.setOnClickListener {
            if (validateFields()){
                when(mode){
                    TransferMode.BASIC_TRANSFER -> {
                        listener.onTransferValidated(
                            binding.textFieldAmount.editText?.text.toString(),
                            binding.textFieldPan.editText?.text.toString(),
                            (binding.textFieldCardSelec.editText as? AutoCompleteTextView)?.text.toString(),
                            binding.textFieldConcept.editText?.text.toString(),
                            binding.textFieldCardHolder.editText?.text.toString()
                        )
                    }
                    TransferMode.RECHARGE_TRANSFER -> {
                        listener.onRechargeValidated(
                            binding.textFieldAmount.editText?.text.toString(),
                            (binding.textFieldCardSelec.editText as? AutoCompleteTextView)?.text.toString()
                            )
                    }
                }
                dismiss()
            }
        }

    }


    private fun validateFields(): Boolean = validateAmount()
            && validateCardField()
            && validateCardHolder()
            && validateConcept()
            && validateEmitCard()


    private fun setToRechargeTransfer() {
        binding.textFieldCardHolder.visibility = View.GONE
        binding.textFieldPan.visibility = View.GONE
        binding.textFieldConcept.visibility = View.GONE
    }

    private fun validateCardField(): Boolean {
        if (mode==TransferMode.RECHARGE_TRANSFER) { return true }
        val textField = binding!!.textFieldPan
        val text = binding!!.textFieldPan.editText?.text
        if(!Validator.isValidCard(text.toString())){
            textField.error = "Ingrese una tarjeta valida"
            return false
        } else {
            textField.error = null
        }
        return true
    }

    private fun validateCardHolder(): Boolean {
        if (mode == TransferMode.RECHARGE_TRANSFER) { return true }
        val textField = binding!!.textFieldCardHolder
        val text = binding!!.textFieldCardHolder.editText?.text
        if(text.isNullOrBlank()){
            textField.error = "Ingrese el nombre del Beneficiario"
            return false
        } else {
            textField.error = null
        }
        return true
    }

    private fun validateEmitCard(): Boolean {
        val textField = binding!!.textFieldCardSelec
        val text = (binding!!.textFieldCardSelec.editText as? AutoCompleteTextView)?.text
        if(text.isNullOrBlank()){
            textField.error = "Seleccione una tarjeta"
            return false
        } else {
            textField.error = null
        }
        return true
    }

    private fun validateAmount(): Boolean {
        if (mode == TransferMode.RECHARGE_TRANSFER) { return true }
        val textField = binding!!.textFieldAmount
        val text = binding!!.textFieldAmount.editText?.text
        if(Validator.isValidAmount(text.toString())){
            textField.error = "Seleccione una tarjeta"
            return false
        } else {
            textField.error = null
        }
        return true
    }

    private fun validateConcept(): Boolean {
        if (mode == TransferMode.RECHARGE_TRANSFER) { return true }
        val textField = binding!!.textFieldConcept
        val text = binding!!.textFieldConcept.editText?.text
        if(text.isNullOrBlank()){
            textField.error = "Ingrese un concepto"
            return false
        } else {
            textField.error = null
        }
        return true
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: TransferFormListener, param2: List<String>, mode: TransferMode) =
            TransferFormFragment(param1,param2,mode)
    }
}