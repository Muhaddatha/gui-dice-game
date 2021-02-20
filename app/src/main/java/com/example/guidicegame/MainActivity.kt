package com.example.guidicegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guidicegame.databinding.ActivityMainBinding //for binding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding //for binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater) //for binding
        setContentView(binding.root) //for binding

        binding.holdDice.text = "Something else"


    }

    fun handleRollDice(view: View) {

    }

    fun handleHold(view: View) {

    }


}