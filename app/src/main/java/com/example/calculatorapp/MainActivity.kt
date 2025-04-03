package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var button0: Button
    lateinit var equalButton: Button
    lateinit var expressionText: TextView
    lateinit var binaryResultText: TextView
    lateinit var hexadecimalResultText: TextView
    lateinit var decimalResultText: TextView


    private var firstOperand = 0.0
    private var secondOperand = 0.0
    private var operator = ""
    private var nextDigit = true

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        button0 = findViewById(R.id.button0)
        equalButton = findViewById(R.id.equalButton)
        expressionText = findViewById(R.id.expressionText)
        binaryResultText = findViewById(R.id.binaryResultText)
        hexadecimalResultText = findViewById(R.id.hexadecimalResultText)
        decimalResultText = findViewById(R.id.decimalResultText)

    }

    fun OnDigitClick( view: View) {
        val button = view as Button
        val digit = button.text.toString()

        if ( nextDigit ) {
            expressionText.text = digit
            nextDigit = false
        }
        else {
            expressionText.append( digit )
        }
    }


    fun OnOperatorClick( view: View ) {
        val button = view as Button
        firstOperand = expressionText.text.toString().toDoubleOrNull() ?: 0.0
        operator = button.text.toString()
        expressionText.text = "$firstOperand  $operator"
        nextDigit = true
    }

    fun OnEqualClick(view: View) {
        val secondOperand = expressionText.text.toString().toDoubleOrNull()?: 0.0
        val result = when (operator) {
            "*" -> firstOperand * secondOperand
            "+" -> firstOperand + secondOperand
            "-" -> firstOperand - secondOperand
            "x" -> firstOperand * secondOperand
            "/" -> if (secondOperand != 0.0) {
                firstOperand / secondOperand
            } else {
                binaryResultText.text = "Error"
                hexadecimalResultText.text = "Error"
                decimalResultText.text = "Error"
                expressionText.text = "$firstOperand $operator $secondOperand"
                return
            }
            else -> 0.0
        }
        expressionText.text = "$firstOperand $operator $secondOperand"
        binaryResultText.text = Integer.toBinaryString(result.toInt())
        hexadecimalResultText.text = Integer.toHexString(result.toInt())
        decimalResultText.text = result.toString()

        nextDigit = true
    }

    fun OnClearClick(view: View) {
        binaryResultText.text = ""
        hexadecimalResultText.text = ""
        decimalResultText.text = ""
        expressionText.text = ""
        firstOperand = 0.0
        secondOperand = 0.0
        operator = ""
    }

    fun onBackspaceClick(view: View) {
        val currentText = expressionText.text.toString()
        if (currentText.isNotEmpty()) {
            expressionText.text = currentText.substring(0, currentText.length - 1)
        }

    }

    fun OnPercentClick(view: View) {
        val currentText = expressionText.text.toString()
        val value = currentText.toDoubleOrNull() ?: 0.0
        val result = value / 100
        expressionText.text = result.toString()
    }
}