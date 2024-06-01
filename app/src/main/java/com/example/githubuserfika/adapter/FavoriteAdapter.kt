package com.example.githubuserfika.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserfika.database.FavoriteUser
import com.example.githubuserfika.databinding.ItemRowBinding

import com.example.githubuserfika.ui.detail.DetailActivity

class FavoriteAdapter : ListAdapter<FavoriteUser, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val user = getItem(position)
        holder.bind(user)
    }
    class  MyViewHolder( val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favoriteUser: FavoriteUser){
            binding.apply {
                tvItemName.text = "${favoriteUser.username}\n"
                Glide
                    .with(itemView.context)
                    .load(favoriteUser.avatarUrl)
                    .into(binding.imgProfile)
                itemView.setOnClickListener{
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.USERNAME, favoriteUser.username)
                    itemView.context.startActivities(arrayOf(intentDetail))
                }
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUser>(){
            override fun areItemsTheSame(
                oldItem: FavoriteUser,
                newItem: FavoriteUser
            ): Boolean {
                return oldItem == newItem
            }
           @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: FavoriteUser,
                newItem: FavoriteUser
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}