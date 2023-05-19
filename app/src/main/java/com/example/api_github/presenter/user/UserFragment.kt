package com.example.api_github.presenter.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import coil.transform.CircleCropTransformation
import com.example.api_github.R
import com.example.api_github.databinding.FragmentUserBinding
import com.example.api_github.presenter.home.HomeAdapter
import com.example.api_github.presenter.model.UserDetailUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    private lateinit var binding: FragmentUserBinding
    private val args: UserFragmentArgs by navArgs()
    private val viewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.pageState.observe(viewLifecycleOwner) {
            loadPageState(it)
        }
        viewModel.getUserDetail(args.login)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun loadPageState(pageState: UserViewModel.PageState) {
        when (pageState) {
            UserViewModel.PageState.Idle -> Unit
            UserViewModel.PageState.Error -> loadErrorPageState()
            UserViewModel.PageState.Loading -> loadLoadingPageState()
            is UserViewModel.PageState.Loaded -> loadLoadedPageState(pageState.user)
        }
    }

    private fun loadLoadedPageState(user: UserDetailUiModel) {
        binding.progressBar.visibility = View.GONE
        binding.userImage.load(user.avatar_url) {
            transformations(CircleCropTransformation())
        }
        binding.nameTextview.text = user.login
        val adapter = UserAdapter(user.repositories)
        binding.recyclerView.adapter = adapter
    }

    private fun loadLoadingPageState() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun loadErrorPageState() {
        binding.progressBar.visibility = View.GONE
        Toast.makeText(
            context,
            resources.getString(R.string.error_message),
            Toast.LENGTH_LONG
        ).show()
    }

}