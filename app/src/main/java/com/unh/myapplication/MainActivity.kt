package com.unh.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.unh.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val className = "F24"
    private lateinit var binding: ActivityMainBinding
    private val db = Firebase.firestore
    private var TAG = "Myapplication-Icetag"

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
    private fun getQuestionsFromFirebase(){
        db.collection("Questions")
            .get()
            .addOnSuccessListener { result ->
                questionBank = mutableListOf()
                for(document in result){
                    val question = document.toObject(Questions::class.java)
                    questionBank!!.add(question)
                    Log.d(TAG,"$question")
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "error", e)
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
        val student = hashMapOf(
            "firstname" to firstName.toString(),
            "lastname" to lastName.toString(),
            "prefname" to prefName.toString(),
            "answer" to answer.toString(),
            "class" to className,
            "question" to binding.txtQuestion.text.toString()
        )
        db.collection( "students")
            .add(student)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG,"Document Snapshot written successfully with ID: ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error adding Document", exception)
            }


    }
    }
