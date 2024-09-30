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
    private var questionBank: MutableList<Questions>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getQuestionsFromFirebase()

        binding.setRandomQuestion.setOnClickListener {
            val randomQuestion = questionBank!!.random().text
            binding.txtQuestion.text = randomQuestion
        }
        binding.submitQuestion.setOnClickListener {
            writeStudentToFirebase()
            binding.txtQuestion.text = ""
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
        val firstName = binding.txtFirstName
        val lastName = binding.txtLastName
        val prefName = binding.txtPrefName
        val answer = binding.ansTxt

        val tag = "Myapplication-Icetag"
        val msg = "Variables: $firstName $lastName $prefName $answer"

        Log.d(tag, msg)
        val student = hashMapOf(
            "firstname" to firstName.text.toString(),
            "lastname" to lastName.text.toString(),
            "prefname" to prefName.text.toString(),
            "answer" to answer.text.toString(),
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
        firstName.setText("")
        lastName.setText("")
        prefName.setText("")
        answer.setText("")
        binding.txtQuestion.text = ""

    }
    }
