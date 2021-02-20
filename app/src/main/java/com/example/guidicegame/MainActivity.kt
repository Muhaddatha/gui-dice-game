package com.example.guidicegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guidicegame.databinding.ActivityMainBinding //for binding
import kotlin.random.Random
import android.util.Log
import android.widget.ImageView
import com.example.guidicegame.R.drawable.die_face_1

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding //for binding

    var currentPlayer = 1
    var player1Score = 0
    var player2Score = 0
    var turnTotal = 0
    var rightDiceNumber = 0
    var leftDiceNumber = 0

    var debugging = true

    //var rn = Random.nextInt(7) //object from random class
    val TAG = "Log"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater) //for binding
        setContentView(binding.root) //for binding

        binding.holdDice.text = "Something else"


    }

    fun handleRollDice(view: View) {
        if(debugging){
            Log.i(TAG, "inside handleRollDice function")
        }

        //generate random numbers for dice roll
        rightDiceNumber = (1..7).random()
        leftDiceNumber = (1..7).random()

        //update the view images
        updateDiceImages(rightDiceNumber, leftDiceNumber)



    }

    fun handleHold(view: View) {

    }

    private fun updateDiceImages(rightDiceNum: Int, leftDiceNum: Int){

        //updates dice picture/image view given the randomly generated numbers

        val drawableResourceDiceRight = when (rightDiceNum){
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }

       binding.rightDiceIv.setImageResource(drawableResourceDiceRight)

        val drawableResourceDiceLeft = when (leftDiceNum){
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }

        binding.leftDiceIv.setImageResource(drawableResourceDiceLeft)
    }


}

