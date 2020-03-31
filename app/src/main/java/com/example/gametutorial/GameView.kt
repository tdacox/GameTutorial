package com.example.gametutorial

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.SurfaceHolder

/**
 * GameView is our playground.
 */

class GameView(context: Context, attributes: AttributeSet) : SurfaceView(context, attributes), SurfaceHolder.Callback {
    private val thread: GameThread
    private var grenade: Grenade? = null
    private var player: Player? = null

    private var touched: Boolean = false
    private var touched_x: Int = 0
    private var touched_y: Int = 0

    init {

        // add callback
        holder.addCallback(this)

        // instantiate the game thread
        thread = GameThread(holder, this)
    }

    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        // game objects
        grenade = Grenade(BitmapFactory.decodeResource(resources, R.drawable.grenade))
        player = Player(BitmapFactory.decodeResource(resources, R.drawable.grenade))

        // start the game thread
        thread.setRunning(true)
        thread.start()
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {

    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        var retry = true
        while (retry) {
            try {
                thread.setRunning(false)
                thread.join()
            } catch (e: Exception) {
                e.printStackTrace()
            }

            retry = false
        }
    }

    /**
     * Function to update the positions of sprites
     */
    fun update() {
        grenade!!.update()
        if(touched) {
            player!!.updateTouch(touched_x, touched_y)
        }
    }

    /**
     * Everything that has to be drawn on Canvas
     */
    override fun draw(canvas: Canvas) {
        super.draw(canvas)

        grenade!!.draw(canvas)
        player!!.draw(canvas)
    }

     override fun onTouchEvent(event: MotionEvent): Boolean {
        // when ever there is a touch on the screen,
        // we can get the position of touch
        // which we may use it for tracking some of the game objects
        touched_x = event.x.toInt()
        touched_y = event.y.toInt()

        val action = event.action
        when (action) {
            MotionEvent.ACTION_DOWN -> touched = true
            MotionEvent.ACTION_MOVE -> touched = true
            MotionEvent.ACTION_UP -> touched = false
            MotionEvent.ACTION_CANCEL -> touched = false
            MotionEvent.ACTION_OUTSIDE -> touched = false
        }

        // detect a hit
        val grenadeRect: Rect = Rect(grenade!!.x, grenade!!.y, grenade!!.x+ grenade!!.w, grenade!!.y+ grenade!!.h)
        val playerRect: Rect = Rect(player!!.x, player!!.y, player!!.x+ player!!.w, player!!.y+ player!!.h)
        if(grenadeRect.intersect(playerRect)){
            println("Collided.")
        }
        return true
    }

}