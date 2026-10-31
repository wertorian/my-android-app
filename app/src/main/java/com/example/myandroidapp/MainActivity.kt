package com.example.myandroidapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Создаем простой текстовый элемент прямо в коде
        val textView = TextView(this).apply {
            text = "Привет, мир из Kotlin на GitHub!"
            textSize = 24f
            setPadding(50, 50, 50, 50)
        }
        
        setContentView(textView)
    }
}
