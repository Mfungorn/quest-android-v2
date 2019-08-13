package com.quest.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quest.app.R
import com.quest.app.databinding.ItemQuestBinding
import com.quest.app.features.quests.domain.model.Quest


class QuestAdapter(
    private val callback: QuestClickCallback
) : RecyclerView.Adapter<QuestAdapter.QuestViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private var quests: List<Quest>? = null

    fun setQuests(list: List<Quest>) {
        if (quests == null) {
            quests = list
            notifyItemRangeInserted(0, list.size)
        } else {
            differ.submitList(list)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestViewHolder {
        val binding: ItemQuestBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_quest,
            parent, false
        )
        binding.callback = callback
        return QuestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuestViewHolder, position: Int) {
        holder.binding.quest = differ.currentList[position]
//        Glide.with(holder.itemView)
//            .load(differ.currentList[position].imageUrl)
//            .circleCrop()
//            .into(holder.binding.imageView)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = differ.currentList.size

    class QuestViewHolder(
        val binding: ItemQuestBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Quest>() {
            override fun areItemsTheSame(oldItem: Quest, newItem: Quest): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Quest, newItem: Quest): Boolean {
                return oldItem == newItem
            }
        }
    }
}