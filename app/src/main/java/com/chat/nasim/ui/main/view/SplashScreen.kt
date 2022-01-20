package com.chat.nasim.ui.main.view

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Pair
import android.widget.ImageView
import com.chat.nasim.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()


        val image: ImageView = findViewById(R.id.imageView)
        Handler(Looper.getMainLooper()).postDelayed({
            val options = ActivityOptions.makeSceneTransitionAnimation(
                this,
                Pair(image, "transitionItem")
            )
            val intent = Intent(this, Login::class.java)
            startActivity(intent, options.toBundle())
            finish()
        }, 1400)


    }
}