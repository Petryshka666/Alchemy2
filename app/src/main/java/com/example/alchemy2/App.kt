package com.example.alchemy2

import android.app.Application//базовый класс для управления приложением
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer

class App : Application() {

    companion object {
        private var musicFon: MediaPlayer? = null
        //флаги музыки и звуков
        private var isMusicEnabled = true
        private var isSoundEnabled = true

        //получить SharedPreferences
        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
        }

        //сохранить состояние музыки
        fun saveMusicState(context: Context, enabled: Boolean) {
            val prefs = getSharedPreferences(context)//получу файл
            prefs.edit().putBoolean("music_enabled", enabled).apply()//putBoolean -  запись значения в файл
        }

        //загрузить состояние музыки
        fun loadMusicState(context: Context): Boolean {
            val prefs = getSharedPreferences(context)
            return prefs.getBoolean("music_enabled", true) // по умолчанию включена
        }

        //сохранить состояние звуков
        fun saveSoundState(context: Context, enabled: Boolean) {
            val prefs = getSharedPreferences(context)
            prefs.edit().putBoolean("sound_enabled", enabled).apply()
        }

        //загрузить состояние звуков
        fun loadSoundState(context: Context): Boolean {
            val prefs = getSharedPreferences(context)
            return prefs.getBoolean("sound_enabled", true) // по умолчанию включены
        }

        //управление музыкой
        fun pauseMusic() {
            if (musicFon?.isPlaying == true) {
                musicFon?.pause()
            }
        }

        fun resumeMusic() {
            if (isMusicEnabled && musicFon != null && !musicFon!!.isPlaying) {
                musicFon?.start()
            }
        }


        fun setMusicEnabled(context: Context, enabled: Boolean) {

            isMusicEnabled = enabled//получу состояние и сохраню его
            saveMusicState(context, enabled)
            if (enabled) {
                resumeMusic()
            } else {
                pauseMusic()
            }
        }

        fun isMusicEnabled(): Boolean = isMusicEnabled

        fun setSoundEnabled(context: Context, enabled: Boolean) {
            isSoundEnabled = enabled
            saveSoundState(context, enabled)
        }

        fun areSoundEnabled(): Boolean = isSoundEnabled
    }

    //точка входа
    override fun onCreate() {
        super.onCreate()

        //загружаем последнее состояние из SharedPreferences
        isMusicEnabled = loadMusicState(this)
        isSoundEnabled = loadSoundState(this)

        val mediaPlayer = MediaPlayer.create(this, R.raw.fon)
        mediaPlayer.isLooping = true//зацикливание
        mediaPlayer.setVolume(0.4f, 0.4f)

        if (isMusicEnabled) {
            mediaPlayer.start()
        }

        musicFon = mediaPlayer
    }
}