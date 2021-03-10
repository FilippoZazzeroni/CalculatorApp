package com.example.calculatorapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class CalculatorViewModel : ViewModel() {
    //! variabili degli operatori e tipi di calcoli
    private var operatore1: Double? = null
    private var operazioneDaFare = "="

    //! variabili che possono essere osservate dalla activity o dal fragment
    val result = MutableLiveData<String>()
    val newNumber = MutableLiveData<String>()
    val operation = MutableLiveData<String>()

    fun digitPressed(value: String) {
        if (newNumber.value != null) {
            newNumber.value = newNumber.value + value
        } else {
            newNumber.value = value
        }
    }

    fun operandPressed(op: String) {
        try {
            val value = newNumber.value?.toDouble()
            if (value != null) {
                performOperation(op, value)
            }
        } catch (e: NumberFormatException) {
            newNumber.value = ""
        }
        operazioneDaFare = op
        operation.value = op
    }

    fun negPressed() {
        if (newNumber.value != null) {
            if (newNumber.value?.isEmpty()!!) {
                newNumber.value = "-"
            } else {
                try {
                    newNumber.value = "${-1 * newNumber.value?.toDouble()!!}"
                } catch (e: NumberFormatException) {
                    newNumber.value = ""
                }
            }
        }
    }


    //! uso i due !! per fare in modo che sono sicuro che operazione1 è diversa da uno. in caso throw NullPointerException
    //! lo uso nel esle poichè sono sicuro che operatore1 è diverso da null, poiche lo messo nella condizione precedente
    private fun performOperation(op: String, value: Double) {
        if (operatore1 == null) {
            operatore1 = value
        } else {
            if (operazioneDaFare == "=") {
                operazioneDaFare = op
            }
            when (operazioneDaFare) {
                "=" -> operatore1 = value
                "/" -> operatore1 = if (value == 0.0) {
                    Double.NaN
                } else {
                    operatore1!! / value
                }
                "x" -> operatore1 = operatore1!! * value
                "+" -> operatore1 = operatore1!! + value
                "-" -> operatore1 = operatore1!! - value
            }
        }

        result.value = operatore1.toString()
        newNumber.value = ""
    }
}