package com.example.guidicegame
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guidicegame.databinding.ActivityMainBinding //for binding
import kotlin.random.Random
import android.util.Log
import android.widget.ImageView
import com.example.guidicegame.R.drawable.die_face_1
import java.util.*
import kotlin.concurrent.schedule

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding //for binding

    //player 1 is 0 and player 2 is 1 as an index
    var currentPlayer = 0
    //var scores : IntArray = intArrayOf(0, 0)
    var totalScore : IntArray = intArrayOf(0, 0)
    var currentPlayerName =arrayOf("Current player: P1", "Current player: P2")
    var turnTotal : IntArray = intArrayOf(0, 0)

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

//        binding.player1ScoreTv.text = "Player 1 Total: 0"
        binding.player1ScoreTv.text = "Player 1 Total: 0"
        binding.player2ScoreTv.text = "Player 2 Total: 0"
        binding.currentPlayerTv.text = "Current player: P1"
        binding.turnTotalTv.text = "Turn total: 0"

        //both players start off with a score of zero
       // scores[0] = 0
       // scores[1] = 0

        updateDiceImages(0, 0)
    }

    fun handleRollDice(view: View) {

        //generate random numbers for dice roll
        rightDiceNumber = (1..6).random()
        leftDiceNumber = (1..6).random()

        binding.holdDice.isEnabled = true
        binding.holdDice.isClickable = true


        Log.i(TAG, "inside handleRollDice function. rolled ($leftDiceNumber, $rightDiceNumber)")


        //update the view images
        updateDiceImages(rightDiceNumber, leftDiceNumber)

        if(rightDiceNumber == 1 && leftDiceNumber == 1){

            Log.i(TAG, currentPlayerName[currentPlayer] + " lost total points for rolling (1, 1)")
            totalScore[currentPlayer] = 0
            turnTotal[currentPlayer] = 0

            binding.holdDice.isClickable = false
            //binding.turnTotalTv.text = "Turn total: 0"
            switchTurn()
        }
        else if(rightDiceNumber == 1 || leftDiceNumber == 1){

            Log.i(TAG, currentPlayerName[currentPlayer] + " lost the turn's points for rolling a 1")
            turnTotal[currentPlayer] = 0

            binding.holdDice.isClickable = false

            switchTurn()
        }
        else{
            if(rightDiceNumber != leftDiceNumber) {
                Log.i(TAG, currentPlayerName[currentPlayer] + " dice numbers different, turn can continue.")
            }
            else{
                Log.i(TAG, currentPlayerName[currentPlayer] + " rolled a double ($leftDiceNumber, $rightDiceNumber), turn must continue, HOLD button disabled.")
                //turn doesn't switch
                binding.holdDice.isEnabled = false
                binding.holdDice.isClickable = false
            }

            turnTotal[currentPlayer] += rightDiceNumber + leftDiceNumber

        }

        Log.i(TAG, "\n")
        binding.turnTotalTv.text = "Turn total: " + turnTotal[currentPlayer]

        //switchTurn()
        //A player has won the game
        if(totalScore[0] >= 50 || totalScore[1] >= 50){
            if(totalScore[0] >= 50){
                Log.i(TAG, "Player 1 has won with a score of ")
                binding.player1ScoreTv.setTextColor(Color.parseColor("#18993b"))
            }
            else if(totalScore[1] >= 50){
                Log.i(TAG, "Player 2 has won")
                binding.player1ScoreTv.setTextColor(Color.parseColor("#18993b"))
            }

            updateDiceImages(rightDiceNumber, leftDiceNumber)
            binding.holdDice.isEnabled = false
            binding.holdDice.isClickable = false
            binding.rollDiceButton.isEnabled = false
            binding.rollDiceButton.isClickable = false
        }


    }

    fun handleHold(view: View) {
        totalScore[currentPlayer] += turnTotal[currentPlayer]
        if(currentPlayer == 0){
            binding.player1ScoreTv.text = "Player 1 Total: " + totalScore[currentPlayer]
        }
        else{
            binding.player2ScoreTv.text = "Player 2 Total: " + totalScore[currentPlayer]
        }

        turnTotal[currentPlayer] = 0
        binding.turnTotalTv.text = "Turn total: " + turnTotal[currentPlayer]




        if(totalScore[0] >= 50 || totalScore[1] >= 50){
            if(currentPlayer == 0){
                Log.i(TAG, "Player 1 has won")
                binding.player1ScoreTv.setTextColor(Color.parseColor("#18993b"))
            }
            else{
                Log.i(TAG, "Player 2 has won")
                binding.player1ScoreTv.setTextColor(Color.parseColor("#18993b"))
            }

            Log.i(TAG, "Inside hold winning, ($leftDiceNumber, $rightDiceNumber)")
            updateDiceImages(rightDiceNumber, leftDiceNumber)
            binding.holdDice.isEnabled = false
            binding.holdDice.isClickable = false
            binding.rollDiceButton.isEnabled = false
            binding.rollDiceButton.isClickable = false
        }
        else {
            switchTurn()
        }


    }

    private fun updateDiceImages(rightDiceNum: Int, leftDiceNum: Int){

        //updates dice picture/image view given the randomly generated numbers

        val drawableResourceDiceRight = when (rightDiceNum){
            0 -> R.drawable.die_face_0
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }

       binding.rightDiceIv.setImageResource(drawableResourceDiceRight) //update the image view

        val drawableResourceDiceLeft = when (leftDiceNum){
            0 -> R.drawable.die_face_0
            1 -> R.drawable.die_face_1
            2 -> R.drawable.die_face_2
            3 -> R.drawable.die_face_3
            4 -> R.drawable.die_face_4
            5 -> R.drawable.die_face_5
            else -> R.drawable.die_face_6
        }

        binding.leftDiceIv.setImageResource(drawableResourceDiceLeft) //update the image view
    }


    private fun switchTurn(){
        //next player's turn
        currentPlayer = (currentPlayer + 1) % 2
        binding.currentPlayerTv.text = currentPlayerName[currentPlayer]

        Timer().schedule(1000){
            updateDiceImages(0, 0)
        }
    }

}

