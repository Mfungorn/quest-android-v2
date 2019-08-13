package com.example.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.SubscriberItemBinding
import com.example.app.features.profile.domain.model.User


class SubscribersAdapter(
    private val callback: SubscriberClickCallback
) : RecyclerView.Adapter<SubscribersAdapter.SubscriberViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private var subscribers: List<User>? = null

    fun setSubscribers(list: List<User>) {
//        if (subscribers == null) {
//            subscribers = list
//            notifyItemRangeInserted(0, list.size)
//        } else {
//            differ.submitList(list)
//        }
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val binding: SubscriberItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.subscriber_item,
            parent, false)
        binding.callback = callback
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.binding.subscriber = differ.currentList[position]
//        Glide.with(holder.itemView)
//            .load(differ.currentList[position].imageUrl)
//            .circleCrop()
//            .into(holder.binding.imageView)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = differ.currentList.size

    class SubscriberViewHolder(
        val binding: SubscriberItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}