package com.example.bmicalculator

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val weightInput = findViewById<EditText>(R.id.editWeight)
        val heightInput = findViewById<EditText>(R.id.editHeight)
        val calcButton = findViewById<Button>(R.id.btnCalc)
        val suggestionsButton = findViewById<Button>(R.id.btnSuggestions)
        val clearButton = findViewById<Button>(R.id.btnClear)


        calcButton.setOnClickListener{
            val weight = weightInput.text.toString()
            val height = heightInput.text.toString()

            if (validateInput(weight, height)){
                val bmi = weight.toFloat()/((height.toFloat()/100).pow(2))
                val bmi2Digits = String.format("%.2f", bmi).toFloat()

                displayResult(bmi2Digits)

                if (bmi2Digits < 21 || bmi2Digits > 22.5f){
                    calcButton.visibility = INVISIBLE
                    suggestionsButton.visibility = VISIBLE
                }
            }
        }

        suggestionsButton.setOnClickListener{
            val weight = weightInput.text.toString()
            val height = heightInput.text.toString()

            val intent = Intent(this, SuggestionActivity::class.java)

            intent.putExtra("weight", weight)
            intent.putExtra("height", height)

            val bmi = weight.toFloat()/((height.toFloat()/100).pow(2))
            val bmi2Digits = String.format("%.2f", bmi).toFloat()
            intent.putExtra("currentBmi", bmi2Digits)

            startActivity(intent)
        }

        clearButton.setOnClickListener{
            weightInput.text.clear()
            heightInput.text.clear()

            val resultIndex = findViewById<TextView>(R.id.tvIndex)
            val resultDescription = findViewById<TextView>(R.id.tvResult)

            resultIndex.text = ""
            resultDescription.text = ""

            calcButton.visibility = VISIBLE
            suggestionsButton.visibility = INVISIBLE

            Toast.makeText(this, "Inputs cleared", Toast.LENGTH_SHORT).show()
        }

    }

    private fun validateInput(weight:String?, height:String?): Boolean {
        return when{
            weight.isNullOrEmpty() -> {
                Toast.makeText(this, "Weight is empty", Toast.LENGTH_SHORT).show()
                return false
            }
            height.isNullOrEmpty() -> {
                Toast.makeText(this, "Height is empty", Toast.LENGTH_SHORT).show()
                return false
            }
            else -> {
                return true
            }
        }
    }

    private fun displayResult(bmi: Float){
        val resultIndex = findViewById<TextView>(R.id.tvIndex)
        val resultDescription = findViewById<TextView>(R.id.tvResult)
        //val info = findViewById<TextView>(R.id.tvInfo)

        resultIndex.text = bmi.toString()
        var resultText = ""
        var color = 0

        when {
            bmi<18.5 -> {
                resultText = "Underweight"
                color = R.color.underWeight
            }
            bmi>=18.5 && bmi<25 ->{
                resultText = "Normal"
                color = R.color.normal
            }
            bmi>=25 && bmi<30 ->{
                resultText = "Overweight"
                color = R.color.overWeight
            }
            bmi>=30 && bmi<35 ->{
                resultText = "Obese"
                color = R.color.obese
            }
            bmi>=35 ->{
                resultText = "Very obese"
                color = R.color.veryObese
            }
        }
        resultDescription.setTextColor(ContextCompat.getColor(this, color))
        resultDescription.text = resultText
    }

}
