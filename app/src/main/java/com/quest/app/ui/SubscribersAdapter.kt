package com.quest.app.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quest.app.R
import com.quest.app.databinding.SubscriberItemBinding
import com.quest.app.features.profile.domain.model.User
import kotlinx.android.synthetic.main.subscriber_item.view.*
import kotlin.math.roundToInt


class SubscribersAdapter(
    private val callback: SubscriberClickCallback
) : RecyclerView.Adapter<SubscribersAdapter.SubscriberViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

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
        return SubscriberViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        val subscriber = differ.currentList[position]
        holder.binding.subscriber = subscriber
        holder.itemView.apply {
            subscriberName.text = subscriber.name
            val nextLevelXp = ((subscriber.level + 1) / 0.1) * ((subscriber.level + 1) / 0.1)
            subscriberXpProgress.progress = ((subscriber.currentXp / nextLevelXp) * 100).roundToInt()
            subscriberLevel.text = subscriber.level.toString()
        }
//        holder.binding.apply {
//            name = subscriber.name
//            val nextLevelXp = ((subscriber.level + 1) / 0.1) * ((subscriber.level + 1) / 0.1)
//            progress = ((subscriber.currentXp / nextLevelXp) * 100).roundToInt()
//            level = subscriber.level.toString()
//        }
        holder.itemView.setOnClickListener { callback.onClick(subscriber) }
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = differ.currentList.size

    class SubscriberViewHolder(
        val view: View,
        val binding: SubscriberItemBinding
    ) : RecyclerView.ViewHolder(view)

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