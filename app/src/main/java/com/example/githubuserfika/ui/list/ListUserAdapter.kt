package com.example.githubuserfika.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserfika.databinding.ItemRowBinding
import com.example.githubuserfika.response.ItemsItem
import com.example.githubuserfika.ui.detail.DetailActivity

class ListUserAdapter : ListAdapter<ItemsItem, ListUserAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        val user = getItem(position)
        holder.bind(user)
    }
    class  MyViewHolder( val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem){
            binding.apply {
                tvItemName.text = "${user.login}\n"
                Glide
                    .with(itemView.context)
                    .load(user.avatarUrl)
                    .into(binding.imgProfile)
                itemView.setOnClickListener{
                    val intentDetail = Intent(itemView.context, DetailActivity::class.java)
                    intentDetail.putExtra(DetailActivity.USERNAME, user.login)
                    itemView.context.startActivities(arrayOf(intentDetail))
                }
            }
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>(){
            override fun areItemsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(
                oldItem: ItemsItem,
                newItem: ItemsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

}