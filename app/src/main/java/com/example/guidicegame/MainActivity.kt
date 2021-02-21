package com.example.guidicegame
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.guidicegame.databinding.ActivityMainBinding //for binding
import kotlin.random.Random
import android.util.Log
import android.widget.ImageView
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

    val TAG = "Log"


    //called when app is opened
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater) //for binding
        setContentView(binding.root) //for binding

        binding.player1ScoreTv.text = "Player 1 Total: 0"
        binding.player2ScoreTv.text = "Player 2 Total: 0"
        binding.currentPlayerTv.text = "Current player: P1"
        binding.turnTotalTv.text = "Turn total: 0"

        updateDiceImages(0, 0)
    }


    //generates random dice number and decides if turn should switch or continue
    fun handleRollDice(view: View) {

        //generate random numbers for dice roll
        rightDiceNumber = (1..6).random()
        leftDiceNumber = (1..6).random()

        binding.holdDice.isEnabled = true
        binding.holdDice.isClickable = true


        Log.i(TAG, "inside handleRollDice function. rolled ($leftDiceNumber, $rightDiceNumber)")


        //update the view images
        updateDiceImages(rightDiceNumber, leftDiceNumber)

        if(rightDiceNumber == 1 || leftDiceNumber == 1){

            if(rightDiceNumber == 1 && leftDiceNumber == 1){ //rolled (1, 1) -> remove total score and switch turn
                Log.i(TAG, currentPlayerName[currentPlayer] + " lost total points for rolling (1, 1), total score was: " + totalScore[currentPlayer])
                totalScore[currentPlayer] = 0
                if(currentPlayer == 0){
                    binding.player1ScoreTv.text = "Player 1 Total: 0"
                }
                else{
                    binding.player2ScoreTv.text = "Player 2 Total: 0"
                }
            }
            else{ //rolled (1, x) return turn total and switch to next player
                Log.i(TAG, currentPlayerName[currentPlayer] + " lost the turn's points for rolling a 1")
            }

            turnTotal[currentPlayer] = 0

            binding.holdDice.isClickable = false
            switchTurn()
        }
        else{ //turn may continue, if player wants to switch turn, they may click the hold button
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


        //A player has won the game
        if(totalScore[0] >= 50 || totalScore[1] >= 50){
            announceWinner()
        }


    }

    fun handleHold(view: View) {

        Log.i(TAG, "Inside hold function, dice values of ($leftDiceNumber, $rightDiceNumber)")

        totalScore[currentPlayer] += turnTotal[currentPlayer] //add turn total to total score
        if(currentPlayer == 0){
            binding.player1ScoreTv.text = "Player 1 Total: " + totalScore[currentPlayer]
        }
        else{
            binding.player2ScoreTv.text = "Player 2 Total: " + totalScore[currentPlayer]
        }

        turnTotal[currentPlayer] = 0
        binding.turnTotalTv.text = "Turn total: " + turnTotal[currentPlayer]

        //a player has one
        if(totalScore[0] >= 50 || totalScore[1] >= 50){
            announceWinner()
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


    //switch the turn to the next person and update view
    private fun switchTurn(){
        //next player's turn
        currentPlayer = (currentPlayer + 1) % 2
//        binding.currentPlayerTv.text = currentPlayerName[currentPlayer]

        Timer().schedule(1000){
            updateDiceImages(0, 0)
            binding.currentPlayerTv.text = currentPlayerName[currentPlayer]
        }
    }


    //updates gui with winner and disables all buttons
    private fun announceWinner(){
        //turn text color of winning person green
        if(totalScore[0] >= 50){
            Log.i(TAG, "Player 1 has won, total score: " + totalScore[0])
            binding.player1ScoreTv.setTextColor(Color.parseColor("#18993b"))
        }
        else{
            Log.i(TAG, "Player 2 has won, total score: " + totalScore[1])
            binding.player2ScoreTv.setTextColor(Color.parseColor("#18993b"))
        }

        Log.i(TAG, "Inside hold winning, ($leftDiceNumber, $rightDiceNumber)")
        updateDiceImages(rightDiceNumber, leftDiceNumber)

        //disable all buttons since someone has won the game
        binding.holdDice.isEnabled = false
        binding.holdDice.isClickable = false
        binding.rollDiceButton.isEnabled = false
        binding.rollDiceButton.isClickable = false

    }

}

