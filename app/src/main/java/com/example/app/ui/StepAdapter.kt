package com.example.app.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app.R
import com.example.app.databinding.ItemStepBinding
import com.example.app.features.quests.domain.model.Step


class StepAdapter(
) : RecyclerView.Adapter<StepAdapter.StepViewHolder>() {

    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)
    private var steps: List<Step>? = null

    fun setSteps(list: List<Step>) {
//        if (steps == null) {
//            steps = list
//            notifyItemRangeInserted(0, list.size)
//        } else {
//            differ.submitList(list)
//        }
        differ.submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        val binding: ItemStepBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.item_step,
            parent, false
        )
        return StepViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.binding.step = differ.currentList[position]
//        Glide.with(holder.itemView)
//            .load(differ.currentList[position].imageUrl)
//            .circleCrop()
//            .into(holder.binding.imageView)
        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = differ.currentList.size

    class StepViewHolder(
        val binding: ItemStepBinding
    ) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Step>() {
            override fun areItemsTheSame(oldItem: Step, newItem: Step): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Step, newItem: Step): Boolean {
                return oldItem == newItem
            }
        }
    }
}