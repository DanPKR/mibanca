package com.danpkr.mibanca.ui.activities.Main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.danpkr.mibanca.databinding.FragmentCardsBinding
import com.danpkr.mibanca.ui.activities.Main.MainViewModel
import com.danpkr.mibanca.ui.activities.Main.adapters.CardAdapter


class CardsFragment : Fragment() {


    private var _binding: FragmentCardsBinding? = null
    private val binding get() = _binding!!
    private val cardAdapter = CardAdapter(emptyList())

    private val vm by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCardsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        InitViews()
        return root
    }

    private fun InitViews(){
        binding.btAddCard.setOnClickListener {
            CardFormFragment.newInstance(cardFormListener).show(parentFragmentManager,"card_form")
        }

        binding.cardRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.cardRv.adapter = cardAdapter
        subscribe()
    }

    private fun subscribe(){
        vm.cards.observe(viewLifecycleOwner,{
            binding.cardRv.visibility = when(it.isNullOrEmpty()){
                true -> View.GONE
                false -> View.VISIBLE
            }
            cardAdapter.values  = it
            cardAdapter.notifyDataSetChanged()
        })

        vm.user.observe(viewLifecycleOwner,{
            binding.helloTv.text = "${it.nombre} ${it.apellidos}"
        })
    }

    private val cardFormListener = object : CardFormListener{
        override fun onCardAccepted(pan: String, expDate: String, cvv: String, cardHolder: String) {
            vm.addCard(pan,expDate,cvv,cardHolder)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}