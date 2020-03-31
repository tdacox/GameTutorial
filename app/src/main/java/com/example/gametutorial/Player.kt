package com.example.gametutorial

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
 
/**
* Player Class.
*/
 
class Player(private val image: Bitmap) {
    var x: Int = 0
    var y: Int = 0
    val w: Int = image.width
    val h: Int = image.height
    private val screenWidth = Resources.getSystem().displayMetrics.widthPixels
    private val screenHeight = Resources.getSystem().displayMetrics.heightPixels
 
    init {
        x = screenWidth/2
        y = screenHeight - 200
    }
 
    /**
     * Draws the object on to the canvas.
     */
    fun draw(canvas: Canvas) {
        canvas.drawBitmap(image, x.toFloat(), y.toFloat(), null)
    }
 
    /**
     * update properties for the game object
     * when the player touches the screen, position the player bitmap there
     */
    fun updateTouch(touch_x: Int, touch_y: Int) {
        x = touch_x - w / 2
        y = touch_y - h / 2
    }
 
}