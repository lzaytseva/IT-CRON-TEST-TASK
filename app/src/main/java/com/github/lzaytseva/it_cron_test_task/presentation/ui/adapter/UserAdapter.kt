package com.github.lzaytseva.it_cron_test_task.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.github.lzaytseva.it_cron_test_task.R
import com.github.lzaytseva.it_cron_test_task.databinding.UserItemBinding
import com.github.lzaytseva.it_cron_test_task.domain.model.UserListItem

class UserAdapter(
    private val onUserClicked: (UserListItem) -> Unit
) : ListAdapter<UserListItem, UserAdapter.UserViewHolder>(UserDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)

        holder.itemView.setOnClickListener {
            onUserClicked.invoke(user)
        }

        holder.bind(user)
    }

    class UserViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserListItem) {
            with(binding) {
                tvId.text = user.id.toString()
                tvLogin.text = user.login

                val cornerRadius =
                    itemView.resources.getDimensionPixelSize(R.dimen.avatar_corner_radius)
                Glide.with(itemView)
                    .load(user.avatarUrl)
                    .transform(RoundedCorners(cornerRadius))
                    .placeholder(R.drawable.ic_no_avatar)
                    .into(binding.ivAvatar)
            }
        }
    }
}