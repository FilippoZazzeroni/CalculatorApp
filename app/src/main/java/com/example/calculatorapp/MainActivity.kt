package com.example.calculatorapp

import android.content.Context
import android.os.*
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.Toast
//! permette di non usare findViewbyId ma di usare l'id assegnato in xml come variabile
import kotlinx.android.synthetic.main.activity_main.*

private const val PENDING_OPERATION = "PENDING_OPERATION"
private const val OPERATOR1 = "OPERATOR1"

class MainActivity : AppCompatActivity() {


    // non perdo lo protezione dal fatto che edit text non puo essere nulla
    // ma con lateint la vado ad inizializzare dopo
    //private lateinit var result: EditText
    //private lateinit var newNumber: EditText

    //! piu robusto come metodo poichè posso dichiarare le variabile anche come val.
    //! quando la variabile viene richiamata allora la la varibiale viene istanziata. i.e simile a lazySingleTone
    // lazyThreadSafetyMode.NONE mettondo nella nella dizhiarazione di lazy() si disabilità la protezione da piu trhead
    // ? da capire
    //private val displayOperation by lazy { findViewById<TextView>(R.id.result) }

    //! variabili degli operatori e tipi di calcoli
    private var operatore1: Double? = null
    private var operazioneDaFare = "="


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //nasconde l'action bar
        supportActionBar?.hide()

        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.editResult)
//        newNumber = findViewById(R.id.editNewNumber)

        operazione.text = ""

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


        val listener = View.OnClickListener { v ->
            //! cast di v as Button poichè cosî sono sicuro che la proprieta text esista
            val b = v as Button
            newNumber.append(b.text)
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
            val op = (v as Button).text.toString()
            try {
                val value = newNumber.text.toString().toDouble()
                performOperation(op, value)
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            operazioneDaFare = op
            operazione.text = op
        }

        buttonDiviso.setOnClickListener(opListener)
        buttonPiu.setOnClickListener(opListener)
        buttonPer.setOnClickListener(opListener)
        buttonMeno.setOnClickListener(opListener)
        buttonUguale.setOnClickListener(opListener)

        buttonUguale.setOnLongClickListener {
            //vibrazione del bottone
            val vibrator =
                applicationContext?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(200)
            }

            //toast con il messaggio che il testo è stato eliminato
            Toast.makeText(applicationContext, "text cleared", Toast.LENGTH_SHORT).show()

            operatore1 = null
            result.setText("")
            newNumber.setText("")
            //metto true poichè la funzione ritorna un boleano
            true

        }

        buttonMeno.setOnLongClickListener {

            //vibrazione del bottone
            val vibrator = applicationContext?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        200,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(200)
            }

            val value = newNumber.text.toString()

            if(value.isEmpty()) {
                newNumber.setText("-")
            } else {
                try {
                    newNumber.setText("${-1*value.toDouble()}")
                } catch (e: NumberFormatException) {
                    newNumber.setText("")
                }
            }
            
            true

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

        result.setText(operatore1.toString())
        newNumber.setText("")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PENDING_OPERATION,operazione.text.toString())
        if(operatore1 != null) {
            outState.putDouble(OPERATOR1,operatore1!!)
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        operazioneDaFare = savedInstanceState.getString(PENDING_OPERATION,"")
        operatore1 = savedInstanceState.getDouble(OPERATOR1,0.0)
        operazione.text = operazioneDaFare
    }

}
