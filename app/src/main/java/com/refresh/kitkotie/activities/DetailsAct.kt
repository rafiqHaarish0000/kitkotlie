package com.refresh.kitkotie.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.refresh.kitkotie.R
import com.refresh.kitkotie.fragments.AlaramFragment

class DetailsAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailact)
        val getTitlePos = intent.getStringExtra("group_title")
        Log.d("title",getTitlePos.toString())
        if (getTitlePos.equals("Alarm Clock")){
            loadFragment(AlaramFragment())
        }
    }
    // Function to load a fragment dynamically
    fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        // Replace the current fragment in the container
        transaction.replace(R.id.fragment_container, fragment)

        // Optionally, you can add the transaction to the back stack
        transaction.addToBackStack(null)

        // Commit the transaction
        transaction.commit()
    }
    // Override the back button press behavior
    override fun onBackPressed() {
        // Check if there are fragments in the back stack
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment is AlaramFragment) {
            val intent = Intent(this, HomeAct::class.java)
            startActivity(intent)
        } else {
            // Otherwise, we just pop the fragment from the stack
            super.onBackPressed()
        }
    }
}