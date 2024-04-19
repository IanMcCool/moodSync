package com.example.moodsync

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import java.util.Date


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        findViewById<Button>(R.id.bCreateAccount).setOnClickListener {
            val dateOfBirth = findViewById<EditText>(R.id.txDateOfBirth).text.toString()
            val email = findViewById<EditText>(R.id.txEmail).text.toString()
            val name = findViewById<EditText>(R.id.txName).text.toString()
            val password = findViewById<EditText>(R.id.txPassword).text.toString()




                val db = FirebaseFirestore.getInstance()
                val user: MutableMap<String, Any> = HashMap()
                user["DateOfBirth"] = dateOfBirth
                user["Email"] = email
                user["Name"] = name
                user["Password"] = password

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
                        Log.d("dbfirebase", "save: ${user}")
                    }
                    .addOnFailureListener {
                        Log.d("dbfirebase Failed", "${user}")
                    }
                db.collection("users")
                    .get()
                    .addOnCompleteListener {
                        val result: StringBuffer = StringBuffer()
                        if (it.isSuccessful) {
                            for (document in it.result!!) {
                                Log.d(
                                    "dbfirebase", "retrieve: " +
                                            "${document.data.getValue("DateOfBirth")}" +
                                            "${document.data.getValue("Email")}" +
                                            "${document.data.getValue("Name")}" +
                                            "${document.data.getValue("Password")}"

                                )

                            }
                            startActivity(Intent(this,MainActivity::class.java))
                        }
                    }

        }

    }
}
