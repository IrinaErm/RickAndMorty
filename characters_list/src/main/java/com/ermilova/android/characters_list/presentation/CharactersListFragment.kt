package com.ermilova.android.characters_list.presentation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.ermilova.android.characters_list.R
import com.ermilova.android.characters_list.databinding.FragmentCharactersListBinding
import com.ermilova.android.characters_list.di.CharactersListComponentProvider
import com.ermilova.android.core.utils.ApiStatus
import com.ermilova.android.core.utils.ViewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


class CharactersListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CharactersListViewModel>

    private val charactersListViewModel: CharactersListViewModel by viewModels {
        viewModelFactory
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

        charactersListViewModel.loadingStatus.observe(viewLifecycleOwner) { loadingStatus ->
            when(loadingStatus) {
                ApiStatus.DONE -> {
                    charactersListAdapter.submitList(charactersListViewModel.characters.value)
                    hideProgressBar()
                }
                ApiStatus.ERROR -> {
                    showErrorToast()
                    hideProgressBar()
                }

            }
        }

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().applicationContext as CharactersListComponentProvider).provideCharactersListComponent()
            .inject(this)
    }

    private fun onListItemClick(position: Int) {
        val uri = Uri.parse("App://characterDetailsFragment/${charactersListAdapter.currentList[position].id}")
        findNavController(this).navigate(uri)
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