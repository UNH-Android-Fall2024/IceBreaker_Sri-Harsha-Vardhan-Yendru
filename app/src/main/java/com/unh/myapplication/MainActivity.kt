package com.unh.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.unh.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.setRandomQuestion.setOnClickListener {
            binding.txtQuestion.text = "Hello"
        }
        binding.submitQuestion.setOnClickListener {
            binding.txtQuestion.text = ""

            writeStudentToFirebase()
        }
    }
    private fun writeStudentToFirebase() {
        val firstName = binding.txtFirstName.text
        val lastName = binding.txtLastName.text
        val prefName = binding.txtPrefName.text
        val answer = binding.ansTxt.text

        val tag = "Myapplication-Icetag"
        val msg = "Variables: $firstName $lastName $prefName $answer"

        Log.d(tag, msg)





    }
    }
