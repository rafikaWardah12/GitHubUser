package com.example.githubuserfika.ui.follow

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserfika.databinding.ItemRowBinding
import com.example.githubuserfika.response.ItemsItem
import com.example.githubuserfika.ui.detail.DetailActivity

class ListFollowAdapter(private val listUser: List<ItemsItem>) :
    RecyclerView.Adapter<ListFollowAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemsItem) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(item.avatarUrl)
                    .into(imgProfile)
                tvItemName.text = item.login
                itemView.setOnClickListener {
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.USERNAME, item.login)
                    itemView.context.startActivity(intentDetail)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = listUser.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listUser[position])
    }
}