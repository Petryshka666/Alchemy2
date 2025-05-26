package com.example.alchemy2

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        App.resumeMusic()//возобновлю фоновую музыку
    }

    override fun onPause() {
        super.onPause()
        App.pauseMusic()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //запрет поворота экрана

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)//установлю xml-разметку

        val startButton = findViewById<Button>(R.id.startButton)
        startButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)//создается объект Intent, который говорит Android, что нужно перейти к новой активности (MainActivity)
            startActivity(intent)
            finish()//закрываю
        }

        val setting=findViewById<ImageView>(R.id.settings)
        setting.setOnClickListener{
            val intent = Intent(this, Setting::class.java)
            startActivity(intent)
            finish()
        }
    }
}