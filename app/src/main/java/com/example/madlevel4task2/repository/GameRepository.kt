package com.example.madlevel4task2.repository

import android.content.Context
import com.example.madlevel4task2.dao.GameDao
import com.example.madlevel4task2.database.GameRoomDatabase
import com.example.madlevel4task2.model.Game

class GameRepository(context: Context) {
    private var gameDao: GameDao

    init {
        val reminderRoomDatabase = GameRoomDatabase.getDatabase(context)
        gameDao = reminderRoomDatabase!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

    suspend fun insertGame(reminder: Game) = gameDao.insertGame(reminder)

    suspend fun deleteGame(reminder: Game) = gameDao.deleteGame(reminder)

    suspend fun updateGame(reminder: Game) = gameDao.updateGame(reminder)
}