package com.danpkr.mibanca.ui.activities.Main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danpkr.mibanca.app.AppConfig
import com.danpkr.mibanca.databinding.FragmentDashboardBinding
import com.danpkr.mibanca.ui.activities.LogIn.LoginActivity
import com.danpkr.mibanca.ui.activities.Main.MainViewModel
import com.danpkr.mibanca.ui.activities.Main.adapters.PaymentAdapter


class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var vm: MainViewModel
    private val paymentAdapter = PaymentAdapter(emptyList())
    private val listMaskedCards = ArrayList<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        initViews()
        vm.loadData()
        return binding.root
    }

    private fun initViews(){
        binding.rvLastTransactions.adapter = paymentAdapter
        binding.rvLastTransactions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.btTransfer.setOnClickListener {
            TransferFormFragment.newInstance(transferListener,listMaskedCards,TransferMode.BASIC_TRANSFER)
                .show(parentFragmentManager,"Tranfer")
        }
        binding.btRecharge.setOnClickListener {
            TransferFormFragment.newInstance(transferListener,listMaskedCards,TransferMode.RECHARGE_TRANSFER)
                .show(parentFragmentManager,"Tranfer")
        }
        binding.btLogOut.setOnClickListener {
            AppConfig.LoggedUser = null
            startActivity(Intent(activity,LoginActivity::class.java))
            activity?.finish()
        }
        subscribe()
    }

    private val transferListener = object: TransferFormListener{
        override fun onTransferValidated(
            amount: String,
            dest_card: String,
            emit_card: String,
            concept: String,
            dest_name: String
        ) {
            vm.makeTransfer(amount, dest_card, emit_card, concept, dest_name)
        }

        override fun onRechargeValidated(amount: String, emit_card: String) {
            vm.makeRecharge(amount,emit_card)
        }

    }

    private fun subscribe(){
        vm.user.observe(viewLifecycleOwner,{
            binding.helloTv.text = "Hola, ${it.nombre}"
            binding.balanceTv.text = String.format("$%,.2f",it.balance)
        })

        vm.cards.observe(viewLifecycleOwner,{
            listMaskedCards.clear()
            it.forEach{
                listMaskedCards.add(it.masked_pan)
            }
        })

        vm.payments.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.rvLastTransactions.visibility = View.VISIBLE
                binding.defaultBlank.visibility = View.GONE
            } else {
                binding.rvLastTransactions.visibility = View.GONE
                binding.defaultBlank.visibility = View.VISIBLE
            }
            paymentAdapter.values = it
            paymentAdapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}