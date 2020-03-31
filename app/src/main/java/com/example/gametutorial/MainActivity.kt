package com.example.gametutorial

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager

class MainActivity : AppCompatActivity()
{
   override fun onCreate(savedInstanceState: Bundle?)
   {
      super.onCreate(savedInstanceState)

      // remove notification bar
      this.window.setFlags(
         WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN
      )

      setContentView(R.layout.activity_main)
   }
}