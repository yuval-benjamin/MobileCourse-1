package com.example.tictactoe

import android.os.Bundle
import android.util.Log
import android.util.Xml
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    enum class Turn
    {
        O,
        X
    }

    private var firstTurn = Turn.X
    private var currentTurn = Turn.X

    private var xScore = 0
    private var oScore = 0

    private var boardList = mutableListOf<Button>()

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initBoard()
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initBoard() {
        boardList.add(binding.a1)
        boardList.add(binding.a2)
        boardList.add(binding.a3)
        boardList.add(binding.b1)
        boardList.add(binding.b2)
        boardList.add(binding.b3)
        boardList.add(binding.c1)
        boardList.add(binding.c2)
        boardList.add(binding.c3)
    }

    private fun fullBoard(): Boolean {
        for(button in boardList){
            if(button.text == "")
                return false
        }
        return true
    }

    fun boardTapped(view: View) {
        if(view !is Button)
            return
        addToBoard(view)

        if(victory(O)){
            oScore++
            result("O is the winner!")
        }
        else if(victory(X)){
            xScore++
            result("X is the winner!")
        } else if(fullBoard()){
            result("This is a tie!")
        }
    }

    private fun victory(s: String): Boolean {

        // Check if a row is the same, if so, there is a winner
        if(match(binding.a1,s) && match(binding.a2,s) && match(binding.a3,s))
            return true
        if(match(binding.b1,s) && match(binding.b2,s) && match(binding.b3,s))
            return true
        if(match(binding.c1,s) && match(binding.c2,s) && match(binding.c3,s))
            return true

        // Check if a column is the same, if so, there is a winner
        if(match(binding.a1,s) && match(binding.b1,s) && match(binding.c1,s))
            return true
        if(match(binding.a2,s) && match(binding.b2,s) && match(binding.c2,s))
            return true
        if(match(binding.a3,s) && match(binding.b3,s) && match(binding.c3,s))
            return true

        // Check if an X is the same, if so, there is a winner
        if(match(binding.a1,s) && match(binding.b2,s) && match(binding.c3,s))
            return true
        if(match(binding.a3,s) && match(binding.b2,s) && match(binding.c1,s))
            return true
        return false
    }

    private fun match(button: Button, symbol: String): Boolean = button.text == symbol

    private fun result(title: String) {
        val message = "\nO score: $oScore\n \nX score: $xScore"
        AlertDialog.Builder(this).setTitle(title)
            .setMessage(message)
            .setPositiveButton("Play Again")
            { _,_ ->
                resetBoard()
            }
            .setCancelable(false)
            .show()


    }

    private fun resetBoard() {
        for(button in boardList){
            button.text = ""
        }

        if(firstTurn == Turn.O)
            firstTurn = Turn.X
        else if(firstTurn == Turn.X)
            firstTurn = Turn.O

        currentTurn = firstTurn
        setTurnLabel()
    }

    private fun addToBoard(button: Button) {
        if(button.text != "")
            return

        if(currentTurn == Turn.O){
            button.text = O
            currentTurn = Turn.X
        } else if(currentTurn == Turn.X){
            button.text = X
            currentTurn = Turn.O
        }
        setTurnLabel()
    }

    private fun setTurnLabel() {
        var turnText = ""
        if(currentTurn == Turn.X)
            turnText = "This is $X's Turn"
        else if(currentTurn == Turn.O)
            turnText = "This is $O's Turn"
        binding.turnTV.text = turnText
    }

    companion object {
        const val O = "O"
        const val X = "X"
    }
}