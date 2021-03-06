package com.example.madlevel4task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.R
import com.example.madlevel4task2.databinding.ReusableGameSummaryBinding
import java.text.DateFormat
import java.util.*

class GameAdapter(private val games: List<Game>): RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ReusableGameSummaryBinding.bind(itemView)

        fun databind(game: Game) {
            binding.tvResult.text = game.result.winningText
            binding.tvDate.text = game.datePlayedString
            binding.ivChoiceComputer.setImageResource(game.computerChoice.imageResource)
            binding.ivChoicePlayer.setImageResource(game.playerChoice.imageResource)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(
            layoutInflater.inflate(R.layout.item_game, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount() = games.size
}