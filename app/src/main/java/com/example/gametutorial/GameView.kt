package com.example.gametutorial

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView

/**
 * GameView is our playground.
 */

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes),
   SurfaceHolder.Callback
{
   private val thread: GameThread
   private var grenade = Grenade(BitmapFactory.decodeResource(resources, R.drawable.grenade))
   private var player = Player(BitmapFactory.decodeResource(resources, R.drawable.grenade))
   private var touched: Boolean = false
   private var touchedX = 0
   private var touchedY = 0

   init
   {

      // add callback
      holder.addCallback(this)

      // instantiate the game thread
      thread = GameThread(holder, this)

   }

   override fun surfaceCreated(surfaceHolder: SurfaceHolder)
   {
      // game objects

      // start the game thread
      thread.setRunning(true)
      thread.start()
   }

   override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int)
   {

   }

   override fun surfaceDestroyed(surfaceHolder: SurfaceHolder)
   {
      var retry = true
      while (retry)
      {
         try
         {
            thread.setRunning(false)
            thread.join()
         } catch (e: Exception)
         {
            e.printStackTrace()
         }

         retry = false
      }
   }

   /**
    * Function to update the positions of sprites
    */
   fun update()
   {
      grenade.update()
      if (touched)
      {
         player.updateTouch(touchedX, touchedY)
      }
   }

   /**
    * Everything that has to be drawn on Canvas
    */
   override fun draw(canvas: Canvas)
   {
      super.draw(canvas)

      grenade.draw(canvas)
      player.draw(canvas)
   }

   override fun onTouchEvent(event: MotionEvent): Boolean
   {
      // when ever there is a touch on the screen,
      // we can get the position of touch
      // which we may use it for tracking some of the game objects
      touchedX = event.x.toInt()
      touchedY = event.y.toInt()

      when (event.action)
      {
         MotionEvent.ACTION_DOWN -> touched = true
         MotionEvent.ACTION_MOVE -> touched = true
         MotionEvent.ACTION_UP -> touched = false
         MotionEvent.ACTION_CANCEL -> touched = false
         MotionEvent.ACTION_OUTSIDE -> touched = false
      }

      // detect a hit
      val grenadeRect = Rect(grenade.x, grenade.y, grenade.x + grenade.w, grenade.y + grenade.h)
      val playerRect = Rect(player.x, player.y, player.x + player.w, player.y + player.h)
      if (grenadeRect.intersect(playerRect))
      {
         println("Collided.")
      }
      return performClick()
   }

   override fun performClick(): Boolean
   {
      super.performClick()
      return true
   }
}