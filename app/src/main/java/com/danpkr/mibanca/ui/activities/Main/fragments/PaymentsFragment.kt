package com.danpkr.mibanca.ui.activities.Main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danpkr.mibanca.databinding.FragmentPaymentsBinding
import com.danpkr.mibanca.ui.activities.Main.MainViewModel
import com.danpkr.mibanca.ui.activities.Main.adapters.PaymentAdapter


class PaymentsFragment : Fragment() {

    private var _binding: FragmentPaymentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val adapter = PaymentAdapter(emptyList())

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        initViews()
        return root
    }

    private fun initViews(){
        binding.rvLastTransactions.adapter = adapter
        binding.rvLastTransactions.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        subscribe()
    }

    private fun subscribe(){
        vm.user.observe(viewLifecycleOwner,{
            binding.helloTv.text = "${it.nombre} ${it.apellidos}"
        })
        vm.payments.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty()){
                binding.rvLastTransactions.visibility = View.VISIBLE
                binding.defaultBlank.visibility = View.GONE
            } else {
                binding.rvLastTransactions.visibility = View.GONE
                binding.defaultBlank.visibility = View.VISIBLE
            }
            adapter.values = it
            adapter.notifyDataSetChanged()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}