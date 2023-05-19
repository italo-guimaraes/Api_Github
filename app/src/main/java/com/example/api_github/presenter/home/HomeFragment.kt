package com.example.api_github.presenter.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.api_github.R
import com.example.api_github.databinding.FragmentHomeBinding
import com.example.api_github.presenter.model.UserUiModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: HomeAdapter

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupView()
        return binding.root
    }

    private fun setupView() {
        setupRecyclerView()
        binding.searchInputText
        val handler = Handler(Looper.getMainLooper())
        var workRunnable = Runnable {}
        binding.searchInputText.doAfterTextChanged {
            handler.removeCallbacks(workRunnable)
            workRunnable = Runnable {
                viewModel.searchUsers(it.toString())
            }
            handler.postDelayed(workRunnable, DELAY_MS)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupRecyclerView() {
        adapter = HomeAdapter()
        binding.recyclerView.adapter = adapter
        adapter.listener = object : HomeAdapter.Listener {
            override fun onClick(user: UserUiModel) {
                val action = HomeFragmentDirections.actionToUserFragment(user.login)
                findNavController().navigate(action)
            }
        }
        binding.recyclerView.setOnTouchListener { _, _ ->
            activity?.currentFocus?.let { view ->
                val imm = context?.getSystemService(
                    Context.INPUT_METHOD_SERVICE
                ) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            binding.searchInputText.clearFocus()
            false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.pageState.observe(viewLifecycleOwner) {
            loadPageState(it)
        }
        viewModel.getUsers()

        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadPageState(pageState: HomeViewModel.PageState) {
        when (pageState) {
            HomeViewModel.PageState.Idle -> Unit
            HomeViewModel.PageState.Error -> loadErrorPageState()
            HomeViewModel.PageState.Loading -> loadLoadingPageState()
            is HomeViewModel.PageState.Loaded -> loadLoadedPageState(pageState.users)
        }
    }

    private fun loadLoadedPageState(users: List<UserUiModel>) {
        binding.progressBar.visibility = View.GONE
        adapter.setupData(users)
    }

    private fun loadLoadingPageState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun loadErrorPageState() {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(
            context,
            resources.getString(R.string.error_message),
            Toast.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val DELAY_MS = 300L
    }
}