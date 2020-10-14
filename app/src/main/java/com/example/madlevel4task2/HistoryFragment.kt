package com.example.madlevel4task2

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.madlevel4task2.databinding.FragmentHistoryBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class HistoryFragment : Fragment() {

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
        val activity = requireActivity() as AppCompatActivity
        activity.findViewById<Toolbar>(R.id.toolbar).title = getString(R.string.fragment_history_toolbar_title)
        activity.supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        _binding = FragmentHistoryBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_history, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                println("deleted")
            }
            else -> findNavController().popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }
}