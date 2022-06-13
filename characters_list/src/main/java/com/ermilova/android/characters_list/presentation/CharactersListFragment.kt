package com.ermilova.android.characters_list.presentation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.paging.LoadState
import com.ermilova.android.characters_list.R
import com.ermilova.android.characters_list.databinding.FragmentCharactersListBinding
import com.ermilova.android.characters_list.di.CharactersListComponentProvider
import com.ermilova.android.core.utils.ViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
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

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                charactersListViewModel.characters.collectLatest {
                    charactersListAdapter.submitData(it)
                }
            }
        }

        lifecycleScope.launch {
            charactersListAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                binding.charactersList.isVisible = loadStates.refresh !is LoadState.Loading

                binding.prependProgress.isVisible = loadStates.prepend is LoadState.Loading
                binding.appendProgress.isVisible = loadStates.append is LoadState.Loading

                if (loadStates.refresh is LoadState.Error) {
                    showErrorToast(binding.root)
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

    override fun onDetach() {
        searchJob?.cancel()
        super.onDetach()
    }

    private fun onListItemClick(characterId: Long) {
        val uri =
            Uri.parse("App://characterDetailsFragment/${characterId}")
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
            //charactersListAdapter.filter(query)
        }
    }

    private fun showErrorToast(view: View) {
        Snackbar.make(
            view,
            getString(com.ermilova.android.core.R.string.error_message),
            SHOW_TOAST_DURATION
        ).show()
    }

    private companion object {
        const val SEARCH_QUERY_DELAY = 500L
        const val SHOW_TOAST_DURATION = 3000
    }
}