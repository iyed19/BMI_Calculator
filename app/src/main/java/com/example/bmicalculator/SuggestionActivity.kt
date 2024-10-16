package com.example.bmicalculator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.bmicalculator.databinding.ActivitySuggestionBinding
import kotlin.math.pow

class SuggestionActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivitySuggestionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_suggestion)

        val btnBack = findViewById<Button>(R.id.btnBack)

        val height = intent.getStringExtra("height").toString()
        val weight = intent.getStringExtra("weight").toString()
        val currentBmi = intent.getFloatExtra("currentBmi", 0f).toFloat()
        val bestBMI = 21.75f

        displayResult(currentBmi, bestBMI, height, weight)

        btnBack.setOnClickListener {
            finish()
        }
    }


    private fun displayResult(currentBmi: Float, bestBMI: Float, height: String, weight: String){
        val h = (height.toFloat()/100).pow(2)
        val bestWeight = bestBMI*h

        val resultIndex = findViewById<TextView>(R.id.tvIndex2)
        val suggMSG = findViewById<TextView>(R.id.tvIndex3)
        resultIndex.text = String.format("%.2f", bestWeight)

        if (currentBmi < bestBMI){
            val x = bestWeight - weight.toFloat()
            val msg = "You need to gain "+String.format("%.1f", x)+" kg to reach the perfect weight"
            suggMSG.text = msg
        }else if (currentBmi > bestBMI){
            val x = weight.toFloat() - bestWeight
            val msg = "You need to lose "+String.format("%.1f", x)+" kg to reach the perfect weight"
            suggMSG.text = msg
        }

    }
}