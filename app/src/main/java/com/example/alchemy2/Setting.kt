package com.example.alchemy2

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class Setting : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        App.resumeMusic()//возобновляет воспроизведение музыки при переходе из другой активности
    }

    override fun onPause() {
        super.onPause()
        App.pauseMusic()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT //запрет поворота экрана

        super.onCreate(savedInstanceState)

        setContentView(R.layout.settings)//привязываю xml-разметку к этой активности

        val musicSwitch = findViewById<Switch>(R.id.switch_music)
        val soundSwitch = findViewById<Switch>(R.id.switch_sounds)

        //устанавливаю текущее состояние из App
        musicSwitch.isChecked = App.isMusicEnabled()
        soundSwitch.isChecked = App.areSoundEnabled()

        //слушатель(срабатывает, когда пользователь меняет положение переключателя музыки)
        musicSwitch.setOnCheckedChangeListener { _, isChecked ->
            App.setMusicEnabled(this, isChecked)//меняю состояния
        }

        //слушатель для звуков
        soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            App.setSoundEnabled(this, isChecked)
        }
        //стрелка
        val pointer = findViewById<ImageView>(R.id.pointer)

        pointer.setOnClickListener {//обработка нажатия

            val intent = Intent(this, MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}