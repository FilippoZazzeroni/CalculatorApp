package com.example.calculatorapp


import android.os.*
import android.view.View
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

//! permette di non usare findViewbyId ma di usare l'id assegnato in xml come variabile
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //nasconde l'action bar
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        val viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)


        operazione.text = ""

        val listener = View.OnClickListener { v ->
            viewModel.digitPressed((v as Button).text.toString())
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonPunto.setOnClickListener(listener)

        //! v sta per oggeto di tipo view
        val opListener = View.OnClickListener { v ->
            viewModel.operandPressed((v as Button).text.toString())
        }

        buttonDiviso.setOnClickListener(opListener)
        buttonPiu.setOnClickListener(opListener)
        buttonPer.setOnClickListener(opListener)
        buttonMeno.setOnClickListener(opListener)
        buttonUguale.setOnClickListener(opListener)

        buttonMeno.setOnLongClickListener { v ->
            viewModel.negPressed()
            true
        }

    }

}


//! cast di v as Button poichè cosî sono sicuro che la proprieta text esista
//            val b = v as Button
//            newNumber.append(b.text)




// non perdo lo protezione dal fatto che edit text non puo essere nulla
// ma con lateint la vado ad inizializzare dopo
//private lateinit var result: EditText
//private lateinit var newNumber: EditText

//! piu robusto come metodo poichè posso dichiarare le variabile anche come val.
//! quando la variabile viene richiamata allora la la varibiale viene istanziata. i.e simile a lazySingleTone
// lazyThreadSafetyMode.NONE mettondo nella nella dizhiarazione di lazy() si disabilità la protezione da piu trhead
// ? da capire
//private val displayOperation by lazy { findViewById<TextView>(R.id.result) }




//bottoni input
//val button0 = findViewById<Button>(R.id.n0)
//val button1 = findViewById<Button>(R.id.n1)
//        val button2 = findViewById<Button>(R.id.n2)
//        val button3 = findViewById<Button>(R.id.n3)
//        val button4 = findViewById<Button>(R.id.n4)
//        val button5 = findViewById<Button>(R.id.n5)
//        val button6 = findViewById<Button>(R.id.n6)
//        val button7 = findViewById<Button>(R.id.n7)
//        val button8 = findViewById<Button>(R.id.n8)
//        val button9 = findViewById<Button>(R.id.n9)
//        val buttonPunto = findViewById<Button>(R.id.punto)
//
//        //bottoni operazione
//        val buttonPiu = findViewById<Button>(R.id.piu)
//        val buttonMeno = findViewById<Button>(R.id.meno)
//        val buttonDiviso = findViewById<Button>(R.id.diviso)
//        val buttonPer = findViewById<Button>(R.id.per)
//        val buttonUguale = findViewById<Button>(R.id.uguale)