package com.example.madlevel4task2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.databinding.ReusableGameSummaryBinding

class GameAdapter(private val games: List<Game>): RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ReusableGameSummaryBinding.bind(itemView)

        fun databind(game: Game) {
            binding.tvResult.text = game.result.winningText
            binding.tvDate.text = game.datePlayed
            binding.ivChoiceComputer.setImageResource(game.computerChoice.drawable)
            binding.ivChoicePlayer.setImageResource(game.playerChoice.drawable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameAdapter.ViewHolder {
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