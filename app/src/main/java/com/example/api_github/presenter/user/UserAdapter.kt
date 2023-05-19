package com.example.api_github.presenter.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.api_github.databinding.UserRepositoryItemBinding
import com.example.api_github.presenter.model.UserRepositoryUiModel

class UserAdapter(
    private val repositories: List<UserRepositoryUiModel>
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(
        val binding: UserRepositoryItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = UserRepositoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(view)
    }

    override fun getItemCount() = repositories.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userRepository = repositories[position]
        holder.binding.nameTextview.text = userRepository.name
    }
}