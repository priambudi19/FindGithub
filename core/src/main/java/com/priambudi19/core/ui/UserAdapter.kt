package com.priambudi19.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.priambudi19.core.databinding.RowItemBinding
import com.priambudi19.core.domain.model.User

class UserAdapter(private val list: List<User>) : RecyclerView.Adapter<UserAdapter.ListUserViewHolder>() {
    class ListUserViewHolder(val binding: RowItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserViewHolder {
        return ListUserViewHolder(
            RowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ListUserViewHolder, position: Int) {
        val data = list[position]
        with(holder.binding) {
            tvUsername.text = data.login
            tvUrl.text = data.htmlUrl
            Glide.with(this.circleImageView.context.applicationContext).load(data.avatarUrl).into(this.circleImageView)
            root.setOnClickListener { onItemClick?.invoke(data) }
        }
    }

    override fun getItemCount(): Int = list.size
    var onItemClick: ((User) -> Unit)? = null
}