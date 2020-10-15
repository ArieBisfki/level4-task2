package com.example.madlevel4task2.ui

import android.graphics.drawable.ShapeDrawable
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.R
import com.example.madlevel4task2.databinding.FragmentGameBinding
import com.example.madlevel4task2.model.Choice
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import com.example.madlevel4task2.util.GameStatistics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class GameFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private var _fragmentGameBinding: FragmentGameBinding? = null
    private val fragmentGameBinding get() = _fragmentGameBinding!!
    private val reusableGameSummaryBinding get() = fragmentGameBinding.layoutReusableGameSummary
    private lateinit var statistics: GameStatistics

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Set binding
        _fragmentGameBinding = FragmentGameBinding.inflate(inflater, container, false)

        // Set repository
        gameRepository = GameRepository(requireContext())


        CoroutineScope(Dispatchers.Main).launch {
            // First empty statistics
            fragmentGameBinding.tvStatistics.text = ""

            // Fetch games (in order to create statistics)
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }

            // Set statistics
            statistics = GameStatistics(games)
            updateStatistics()

            // Attach click listeners. Important we do this after statistics have been fetched, otherwise might get unexpected behaviour.
            fragmentGameBinding.let {
                it.btnRock.setOnClickListener {
                    playerSelectedChoice(Choice.ROCK)
                }
                it.btnPaper.setOnClickListener {
                    playerSelectedChoice(Choice.PAPER)
                }
                it.btnScissors.setOnClickListener {
                    playerSelectedChoice(Choice.SCISSORS)
                }
            }
        }

        // Show game result
        displayGameResult(null)

        // Handle toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.findViewById<Toolbar>(R.id.toolbar).title = getString(R.string.fragment_game_toolbar_title)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        return fragmentGameBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_game, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.action_gameFragment_to_historyFragment)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentGameBinding = null
    }

    /**
     * Handler for bottom buttons click.
     */
    private fun playerSelectedChoice(playerChoice: Choice) {
        reusableGameSummaryBinding.ivChoicePlayer.setImageResource(playerChoice.imageResource)
        CoroutineScope(Dispatchers.Main).launch {
            val game = Game(
                datePlayed = Date(),
                computerChoice = Choice.values().random(),
                playerChoice = playerChoice
            )

            withContext(Dispatchers.IO) {
                gameRepository.insertGame(game)
            }

            // Update UI
            displayGameResult(game)
            statistics.add(game)
            updateStatistics()
        }

    }

    /**
     * Handles updating the middle bit of the screen, using a game instance.
     */
    private fun displayGameResult(game: Game?) {
        if (game == null) {
            ShapeDrawable().let {
                reusableGameSummaryBinding.ivChoiceComputer.setImageDrawable(it)
                reusableGameSummaryBinding.ivChoicePlayer.setImageDrawable(it)
            }
            reusableGameSummaryBinding.tvResult.text = ""
        } else {
            reusableGameSummaryBinding.ivChoiceComputer.setImageResource(game.computerChoice.imageResource)
            reusableGameSummaryBinding.ivChoicePlayer.setImageResource(game.playerChoice.imageResource)
            reusableGameSummaryBinding.tvResult.text = game.result.winningText
        }

        // In any case: do not show date
        reusableGameSummaryBinding.tvDate.text = ""
    }

    private fun updateStatistics() {
        statistics.let {
            fragmentGameBinding.tvStatistics.text = getString(R.string.fragment_game_statistics, it.wins, it.draws, it.losses)
        }
    }

}