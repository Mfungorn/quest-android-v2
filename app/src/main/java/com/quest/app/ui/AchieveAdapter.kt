package com.quest.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.quest.app.R
import com.quest.app.databinding.ItemAchieveBinding
import com.quest.app.features.profile.domain.model.Achieve
import kotlinx.android.synthetic.main.item_achieve.view.*


class AchieveAdapter : RecyclerView.Adapter<AchieveAdapter.AchieveViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun setAchieves(list: List<Achieve>) {
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchieveViewHolder {
        val binding: ItemAchieveBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_achieve,
            parent, false
        )
        return AchieveViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AchieveViewHolder, position: Int) {
        val achieve = differ.currentList[position]
        holder.binding.achieve = achieve
        holder.itemView.apply {
            achieveTitle.text = achieve.name
            textView.text = achieve.description
        }
//        Glide.with(holder.itemView)
//            .load(differ.currentList[position].imageUrl)
//            .circleCrop()
//            .into(holder.binding.imageView)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = differ.currentList.size

    class AchieveViewHolder(
        val binding: ItemAchieveBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Achieve>() {
            override fun areItemsTheSame(oldItem: Achieve, newItem: Achieve): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Achieve, newItem: Achieve): Boolean {
                return oldItem == newItem
            }
        }
    }
}