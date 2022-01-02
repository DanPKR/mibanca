package com.danpkr.mibanca.utils

import android.service.autofill.LuhnChecksumValidator
import android.util.Patterns
import java.util.regex.Pattern

object Validator {

    private val EMAIL_PATTERN  = Regex("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+")

    private val PASSWORD_PATTERN = Regex ("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$")

    private val EXP_DATE_PATTERN = Regex("^(0[1-9]|1[0-2])\\/?([0-9]{2})\$")

    private val AMOUNT_PATTERN = Regex("/^\\d+(\\.\\d{1,2})?\$/")



    fun isValidEmail(input : String) = EMAIL_PATTERN.matches(input)

    fun isValidPassword(input : String) = PASSWORD_PATTERN.matches(input)

    fun isValidExpDate(input : String) = EXP_DATE_PATTERN.matches(input)

    fun isValidAmount(input : String) = AMOUNT_PATTERN.matches(input)

    fun isValidCard(input: String): Boolean{
        var checksum: Int = 0
        for (i in input.length - 1 downTo 0 step 2) {
            checksum += input.get(i) - '0'
        }
        for (i in input.length - 2 downTo 0 step 2) {
            val n: Int = (input.get(i) - '0') * 2
            checksum += if (n > 9) n - 9 else n
        }
        return checksum%10 == 0
    }
}