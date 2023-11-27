package com.example.fakerslist

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface

object BitmapHelper {
    fun generateInitialsImage(
        firstName: String,
        lastName: String,
        position: Int
    ): Bitmap {
        val colors = arrayOf("#FF977D", "#7DFF9A", "#9A7DFF", "#7DBEFF", "#FF7DDF")

        val initials =
            "${firstName.firstOrNull()?.uppercase()}${lastName.firstOrNull()?.uppercase()}"
        val paint = Paint().apply {
            color = Color.parseColor(colors[(position + 7) % colors.size])
            textAlign = Paint.Align.CENTER
            style = Paint.Style.FILL
            isAntiAlias = true
            typeface = Typeface.DEFAULT_BOLD
            textSize = 30F // Adjust text size as needed
        }
        val width = 75
        val height = 75

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val x = canvas.width / 2F
        val y = (canvas.height / 2F) - (paint.descent() + paint.ascent()) / 2F

        canvas.drawCircle(width / 2f, height / 2f, width.coerceAtLeast(height) / 2f, paint)
        paint.color = Color.WHITE
        canvas.drawText(
            initials, x, y, paint
        )
        return bitmap
    }
}