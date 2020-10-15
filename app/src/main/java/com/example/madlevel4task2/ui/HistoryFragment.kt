package com.example.madlevel4task2.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlevel4task2.R
import com.example.madlevel4task2.databinding.ActivityMainBinding
import com.example.madlevel4task2.databinding.FragmentHistoryBinding
import com.example.madlevel4task2.model.Game
import com.example.madlevel4task2.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

    private lateinit var gameRepository: GameRepository
    private val games = arrayListOf<Game>()
    private lateinit var gameAdapter: GameAdapter
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    init {
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Set binding
        _binding = FragmentHistoryBinding.inflate(inflater)

        // Handle toolbar
        val activity = requireActivity() as AppCompatActivity
        activity.findViewById<Toolbar>(R.id.toolbar).title = getString(R.string.fragment_history_toolbar_title)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        // Set repository
        gameRepository = GameRepository(requireContext())

        // Configure recycler view
        gameAdapter = GameAdapter(games)
        binding.rvGamesHistory.let {
            it.adapter = gameAdapter
            it.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            // Set item decoration
            val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            it.addItemDecoration(itemDecoration)
        }

        // Get games from the database
        CoroutineScope(Dispatchers.Main).launch {
            val games = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@HistoryFragment.games.clear()
            this@HistoryFragment.games.addAll(games)
            gameAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                CoroutineScope(Dispatchers.IO).launch {
                    gameRepository.deleteAllGames()
                }
                games.clear()
                gameAdapter.notifyDataSetChanged()
            }
            else -> findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}