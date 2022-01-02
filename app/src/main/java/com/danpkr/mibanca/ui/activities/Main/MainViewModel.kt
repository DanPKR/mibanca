package com.danpkr.mibanca.ui.activities.Main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danpkr.dbmodule.Entities.Card
import com.danpkr.dbmodule.Entities.Payment
import com.danpkr.mibanca.app.AppConfig
import com.danpkr.mibanca.domain.CardService
import com.danpkr.mibanca.domain.PaymentService
import com.danpkr.mibanca.domain.UserService
import com.dts.dbmodule.Entities.User
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel: ViewModel() {

    val user = MutableLiveData<User>()
    val cards = MutableLiveData<List<Card>>()
    val payments = MutableLiveData<List<Payment>>()

    fun loadData(){
        viewModelScope.launch {
            val u  = UserService.GetUserInfo(AppConfig.LoggedUser!!)
            val lc = CardService.getCards(u.userId!!)
            val lp = PaymentService.getPayments(u.userId!!)
            user.postValue(u)
            cards.postValue(lc)
            payments.postValue(lp)
        }
    }

    fun makeTransfer( amount: String, dest_card: String, emit_card: String, concept: String, dest_name: String, ) {
        viewModelScope.launch {
            PaymentService.createPayment(
                user.value?.userId!!,
                amount,
                emit_card,
                dest_card,
                dest_name,
                concept)
            loadData()
        }
    }

    fun makeRecharge( amount: String, emit_card: String ){
        viewModelScope.launch {
            PaymentService.makeRecharge(user.value?.userId!!,amount,emit_card)
            loadData()
        }
    }

    fun addCard(pan: String, expDate: String, cvv: String, cardHolder: String){
        viewModelScope.launch {
            try {
                CardService.createNewCard(user.value?.userId!!, pan,expDate,cardHolder)
                loadData()
            } catch ( e : Exception){
                Log.e("TAG", "addCard: ", e)
            }
        }
    }


}