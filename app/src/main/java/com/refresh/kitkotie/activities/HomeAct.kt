package com.refresh.kitkotie.activities

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.refresh.kitkotie.R
import com.refresh.kitkotie.adapters.CustomExpandableListAdapter

class HomeAct : AppCompatActivity() {
    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: CustomExpandableListAdapter
    private val groupTitles = listOf("Alarm Clock", "Calculator", "Login", "CRUD", "Product","Audio & Video","API fetching","MVVM","Dagger")
    private val childData = HashMap<String, String>()
    private lateinit var userName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_act)
        userName = findViewById(R.id.usernameID)
        expandableListView = findViewById(R.id.expandableListView)

        val getUserName = intent.getStringExtra("hi")
        userName.setText(getUserName)

        for (title in groupTitles) {
            childData[title] = "Check out the $title"
        }

        adapter = CustomExpandableListAdapter(this, groupTitles, childData)
        expandableListView.setAdapter(adapter)
    }
}