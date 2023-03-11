package com.example.realtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.realtime.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        var count = 0
        val database = Firebase.database
        val myRef = database.reference


        binding.add.setOnClickListener {

            val name = binding.Name.text.toString()
            val id = binding.Number.text.toString()
            val age = binding.Address.text.toString()

            val person = hashMapOf(
                "name" to name,
                "id" to id,
                "age" to age
            )
            myRef.child("person").child("$count").setValue(person)
            count++

        }

        binding.show.setOnClickListener {
            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.value
                    binding.textView.text = value.toString()

                    Toast.makeText(applicationContext, "success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(applicationContext, "Failed to read value.", Toast.LENGTH_SHORT)
                        .show()

                }

            })
        }
    }
}