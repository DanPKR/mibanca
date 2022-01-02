package com.danpkr.mibanca.ui.models

 enum class EventType(){
     USER_LOGIN,
     USER_SIGNUP,
     CARD_ADDED,
     PAYMENT
 }
data class UiEvent(
    val succeed: Boolean,
    val eventType: EventType,
    val message: String? = null,
    val result: String? = null
)
