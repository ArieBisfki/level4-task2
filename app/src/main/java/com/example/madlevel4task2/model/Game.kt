package com.example.madlevel4task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.madlevel4task2.util.ChoiceComparator
import java.lang.Exception

@Entity(tableName = "game")
class Game(
    @ColumnInfo(name = "datePlayed")
    val datePlayed: String,

    @ColumnInfo(name = "computerChoice")
    val computerChoice: Choice,

    @ColumnInfo(name = "playerChoice")
    val playerChoice: Choice,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long? = null
) {
    @delegate:Ignore
    val result: GameResult by lazy {
        val comparisonResult = ChoiceComparator.instance.compare(computerChoice, playerChoice)
        when (comparisonResult) {
            -1 -> GameResult.WIN_PLAYER
            0 -> GameResult.DRAW
            1 -> GameResult.WIN_COMPUTER
            else -> {
                throw Exception("Computer/player choice comparison returned unexpected result.")
            }
        }
    }
}
