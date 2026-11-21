package com.example.myandroidapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Создаем корневой контейнер (LinearLayout) прямо в коде
        val rootLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.WHITE)
        }

        // 2. Создаем текстовую надпись
        val textViewTitle = TextView(this).apply {
            text = "Привет, мир!"
            textSize = 24f
            setTextColor(Color.BLACK)
            gravity = Gravity.CENTER
        }

        // 3. Создаем кнопку
        val buttonChangeText = Button(this).apply {
            text = "Изменить текст"
            isAllCaps = false
        }

        // 4. Добавляем отступ между надписью и кнопкой
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            bottomMargin = 32
        }
        textViewTitle.layoutParams = params

        // 5. Собираем всё вместе в единый экран
        rootLayout.addView(textViewTitle)
        rootLayout.addView(buttonChangeText)

        // 6. Устанавливаем созданный контейнер как главный экран активности
        setContentView(rootLayout)

        // 7. Логика нажатия на кнопку (меняем текст)
        var isChanged = false
        buttonChangeText.setOnClickListener {
            if (isChanged) {
                textViewTitle.text = "Привет, мир!"
            } else {
                textViewTitle.text = "Текст успешно изменен!"
            }
            isChanged = !isChanged
        }
    }
}
