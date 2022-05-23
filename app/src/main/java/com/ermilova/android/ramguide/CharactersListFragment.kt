package com.ermilova.android.ramguide

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.findNavController
import com.ermilova.android.characters_list.CharactersListAdapter
import com.ermilova.android.characters_list.CharactersListViewModel
import com.ermilova.android.characters_list.databinding.FragmentCharactersListBinding
import com.ermilova.android.core.utils.ViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class CharactersListFragment : Fragment() {
    private val charactersListViewModel: CharactersListViewModel by viewModels {
        ViewModelFactory {
            (requireNotNull(activity).application as MyApplication).appComponent.charactersListViewModel()
        }
    }

    private lateinit var charactersListAdapter: CharactersListAdapter

    private lateinit var binding: FragmentCharactersListBinding

    private var searchJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersListBinding.inflate(inflater)

        charactersListAdapter = CharactersListAdapter { position -> onListItemClick(position) }
        binding.charactersList.adapter = charactersListAdapter

        charactersListViewModel.characters.observe(viewLifecycleOwner) { characters ->
            if(characters.isNotEmpty()) {
                characters?.let { charactersListAdapter.submitList(characters) }
                hideProgressBar()
            } else {
                showErrorToast()
                hideProgressBar()
            }
        }

        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)
        (this.activity as MainActivity).supportActionBar?.setTitle(R.string.character_list)

        (activity as MainActivity).hideUpButton()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireNotNull(activity).application as MyApplication).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        requireNotNull(activity).actionBar?.setTitle(R.string.character_list)
    }

    private fun onListItemClick(position: Int) {
        view?.findNavController()?.navigate(
            CharactersListFragmentDirections.actionCharactersListFragmentToCharacterDetailsFragment(
                charactersListAdapter.currentList[position].id
            )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val menuItem = menu.findItem(R.id.search)
        val searchView = menuItem.actionView as SearchView
        searchView.isIconifiedByDefault = false
        searchView.queryHint = getString(R.string.search_hint)

        searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchCharacters(query = query)
                    return false
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    searchCharacters(query = query, delay = SEARCH_QUERY_DELAY)
                    return false
                }
            }
        )
    }

    private fun searchCharacters(query: String?, delay: Long = 0L) {
        searchJob?.cancel()
        searchJob = lifecycle.coroutineScope.launch {
            delay(delay)
            charactersListAdapter.filter(query)
        }
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
        binding.charactersList.visibility = View.VISIBLE
    }

    private fun showErrorToast() {
        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        searchJob?.cancel()
        super.onDetach()
    }

    private companion object {
        const val SEARCH_QUERY_DELAY = 500L
    }
}