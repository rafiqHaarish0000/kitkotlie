package com.refresh.kitkotie

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.refresh.kitkotie.R
import com.refresh.kitkotie.activities.HomeAct

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Thread{
            try {
                Thread.sleep(3000)
            }catch (e: InterruptedException){
                e.printStackTrace()
            }
            var name = "mohammed rafiq"
            startActivity(Intent(this, HomeAct::class.java).apply {
                putExtra("hi",name)
            })
        }.start()
    }
}