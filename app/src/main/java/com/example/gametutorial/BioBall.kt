package com.example.gametutorial

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.util.Log

/**
 * BioBall.
 */


class BioBall()
{
   var x: Int = -1
   var y: Int = -1
   var h: Int = 60
   var w: Int = 60
   private var xVelocity = 5
   private var yVelocity = 5
   private var screenWidth = 0
   private var screenHeight = 0
//   private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
//   private val screenHeight = Resources.getSystem().displayMetrics.heightPixels

   /**
    * Draws the object on to the canvas.
    */
   fun draw(canvas: Canvas)
   {
      if( x == -1 )
      {
         screenHeight = canvas.height
         screenWidth = canvas.width
         x = screenWidth / 2 - (w / 2)
         y = screenHeight / 2 - (h / 2)
      }

      var rec: Rect = Rect(x,y,x+w,y+h)
      ShapeDrawable().apply {
         shape = OvalShape()
         intrinsicHeight = h
         intrinsicWidth = w
         bounds = rec
         getPaint().apply {
            style = Paint.Style.FILL
            color = Color.parseColor("#FF0F0F")
         }
         draw(canvas)
      }
   }

   /**
    * update properties for the game object
    */
   fun update()
   {
   //    val randomNum = ThreadLocalRandom.current().nextInt(1, 5)
      if (x > screenWidth - w || x < w)
      {
         xVelocity *= -1
      }
      if (y > screenHeight - h || y < h)
      {
         yVelocity *= -1
      }

      x += (xVelocity)
      y += (yVelocity)

   }

}