package com.example.api_github.presenter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.api_github.databinding.UserItemBinding
import com.example.api_github.presenter.model.UserUiModel

class HomeAdapter() : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface Listener {
        fun onClick(user: UserUiModel)
    }

    var listener: Listener? = null
    private val users: MutableList<UserUiModel> = mutableListOf()

    inner class HomeViewHolder(
        val binding: UserItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(view)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val user = users[position]

        holder.binding.nameTextview.text = user.login
        holder.binding.userImage.load(user.avatar_url) {
            transformations(CircleCropTransformation())
        }

        holder.binding.root.setOnClickListener {
            listener?.onClick(user)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setupData(users: List<UserUiModel>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }
}