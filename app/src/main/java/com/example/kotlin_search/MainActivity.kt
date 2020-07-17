package com.example.kotlin_search

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    fun onClickButton(view: View){
        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)
    }
}
