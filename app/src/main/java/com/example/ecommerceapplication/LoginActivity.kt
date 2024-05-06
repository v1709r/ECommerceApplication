package com.example.ecommerceapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var eID : EditText
    private lateinit var pass : EditText
    private lateinit var loginBtn : Button
    private lateinit var signUp : TextView

    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        eID = findViewById(R.id.eID)
        pass = findViewById(R.id.pass)
        loginBtn = findViewById(R.id.loginBtn)
        signUp  = findViewById(R.id.signUp)
        mAuth = Firebase.auth

        loginBtn.setOnClickListener {
            emailLogIn(eID.text.toString(),pass.text.toString())
        }

        signUp.setOnClickListener {
            emailSignUp(eID.text.toString(),pass.text.toString())
        }
    }

    private fun emailSignUp(email: String, pass: String) {
        mAuth.createUserWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Successfully Signed up, please login",Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception
                    val errorMessage = exception?.message ?: "Unknown Error"
                    Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun emailLogIn(email: String, pass: String) {
        mAuth.signInWithEmailAndPassword(email,pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(this,"Success",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,UploadProduct::class.java))
                } else {
                    // If sign in fails, display a message to the user.
                    val exception = task.exception
                    val errorMessage = exception?.message ?: "Unknown Error"
                    Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
                }
            }
    }
}