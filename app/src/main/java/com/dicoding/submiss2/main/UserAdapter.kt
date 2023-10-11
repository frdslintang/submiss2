package com.dicoding.submiss2.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.submiss2.data.response.ResponseUser
import com.dicoding.submiss2.databinding.ItemUserBinding


class UserAdapter(private val data: MutableList<ResponseUser.Item> = mutableListOf(),
                  private val listener: (ResponseUser.Item) -> Unit

) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    fun setData(data: MutableList<ResponseUser.Item>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class UserViewHolder(private val v: ItemUserBinding) : RecyclerView.ViewHolder(v.root) {
        fun bind(item: ResponseUser.Item) {
            v.image.load(item.avatarUrl) {
                transformations(CircleCropTransformation())
            }
            v.username.text = item.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener(item)
        }
    }

    override fun getItemCount(): Int = data.size
}