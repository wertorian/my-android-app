package com.example.myandroidapp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.abs
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(PongView(this))
    }
}

class PongView(context: Context) : View(context) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.DKGRAY
        strokeWidth = 6f
        style = Paint.Style.STROKE
        pathEffect = DashPathEffect(floatArrayOf(25f, 25f), 0f)
    }

    private var ballX = 0f
    private var ballY = 0f
    private var ballVx = 0f
    private var ballVy = 0f
    private val ballRadius = 22f

    private val paddleWidth = 240f
    private val paddleHeight = 32f
    private var playerX = 0f
    private var aiX = 0f
    private val aiSpeed = 11f

    private var playerScore = 0
    private var aiScore = 0
    private var isInitialized = false

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (!isInitialized && w > 0 && h > 0) {
            playerX = (w - paddleWidth) / 2f
            aiX = (w - paddleWidth) / 2f
            resetBall(w, h)
            isInitialized = true
        }
    }

    private fun resetBall(w: Int, h: Int) {
        ballX = w / 2f
        ballY = h / 2f
        val dirX = if (Random.nextBoolean()) 1f else -1f
        val dirY = if (Random.nextBoolean()) 1f else -1f
        ballVx = 10f * dirX
        ballVy = 13f * dirY
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (!isInitialized) return

        canvas.drawColor(Color.parseColor("#121212"))
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, linePaint)

        updateGame()

        paint.color = Color.WHITE
        paint.textSize = 90f
        paint.textAlign = Paint.Align.LEFT
        canvas.drawText(aiScore.toString(), 60f, height / 2f - 50f, paint)
        canvas.drawText(playerScore.toString(), 60f, height / 2f + 115f, paint)

        val aiTop = 80f
        val playerTop = height - 80f - paddleHeight

        paint.color = Color.parseColor("#FF5252")
        canvas.drawRoundRect(aiX, aiTop, aiX + paddleWidth, aiTop + paddleHeight, 16f, 16f, paint)

        paint.color = Color.parseColor("#448AFF")
        canvas.drawRoundRect(playerX, playerTop, playerX + paddleWidth, playerTop + paddleHeight, 16f, 16f, paint)

        paint.color = Color.WHITE
        canvas.drawCircle(ballX, ballY, ballRadius, paint)

        postInvalidateOnAnimation()
    }

    private fun updateGame() {
        ballX += ballVx
        ballY += ballVy

        if (ballX - ballRadius <= 0f) {
            ballX = ballRadius
            ballVx = abs(ballVx)
        } else if (ballX + ballRadius >= width) {
            ballX = width - ballRadius
            ballVx = -abs(ballVx)
        }

        val aiCenter = aiX + paddleWidth / 2f
        if (aiCenter < ballX - 15f) aiX += aiSpeed
        else if (aiCenter > ballX + 15f) aiX -= aiSpeed
        aiX = aiX.coerceIn(0f, width - paddleWidth)

        val playerTop = height - 80f - paddleHeight
        if (ballVy > 0 && ballY + ballRadius >= playerTop && ballY - ballRadius <= playerTop + paddleHeight) {
            if (ballX in playerX..(playerX + paddleWidth)) {
                ballY = playerTop - ballRadius
                ballVy = -abs(ballVy) * 1.04f
                val hitOffset = (ballX - (playerX + paddleWidth / 2f)) / (paddleWidth / 2f)
                ballVx = hitOffset * 16f
            }
        }

        val aiBottom = 80f + paddleHeight
        if (ballVy < 0 && ballY - ballRadius <= aiBottom && ballY + ballRadius >= 80f) {
            if (ballX in aiX..(aiX + paddleWidth)) {
                ballY = aiBottom + ballRadius
                ballVy = abs(ballVy) * 1.04f
            }
        }

        if (ballY < 0f) {
            playerScore++
            resetBall(width, height)
        } else if (ballY > height) {
            aiScore++
            resetBall(width, height)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_MOVE || event.action == MotionEvent.ACTION_DOWN) {
            playerX = (event.x - paddleWidth / 2f).coerceIn(0f, width - paddleWidth)
        }
        return true
    }
}
