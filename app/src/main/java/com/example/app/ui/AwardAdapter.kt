package com.example.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.databinding.ItemAwardBinding
import com.example.app.features.quests.domain.model.Award
import kotlinx.android.synthetic.main.item_award.view.*


class AwardAdapter(
) : RecyclerView.Adapter<AwardAdapter.AwardViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private var awards: List<Award>? = null

    fun setAwards(list: List<Award>) {
//        if (awards == null) {
//            awards = list
//            notifyItemRangeInserted(0, list.size)
//        } else {
//            differ.submitList(list)
//        }
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AwardViewHolder {
        val binding: ItemAwardBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_award,
            parent, false
        )
        return AwardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AwardViewHolder, position: Int) {
        holder.binding.award = differ.currentList[position]
        Glide.with(holder.itemView.context)
            .load(differ.currentList[position].imageUrl)
            .centerCrop()
            .into(holder.itemView.awardImage)

        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = differ.currentList.size

    class AwardViewHolder(
        val binding: ItemAwardBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Award>() {
            override fun areItemsTheSame(oldItem: Award, newItem: Award): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Award, newItem: Award): Boolean {
                return oldItem == newItem
            }
        }
    }
}