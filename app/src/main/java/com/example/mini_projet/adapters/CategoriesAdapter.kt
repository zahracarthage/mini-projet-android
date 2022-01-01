package com.example.mini_projet.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_projet.databinding.CategorieCardViewBinding

class CategoriesAdapter(private val customListeners: CustomListeners) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    private val diffUtilItemCallback = object : DiffUtil.ItemCallback<String>() {

        //pk is the primary key for the data class.
        //replace with any unique identifier of your specific data class.
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }

    private val listDiffer = AsyncListDiffer(this, diffUtilItemCallback)

    private lateinit var binding: CategorieCardViewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        binding =
            CategorieCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoriesViewHolder(binding, customListeners)
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(listDiffer.currentList[position])
    }

    override fun getItemCount(): Int {
        return listDiffer.currentList.size
    }

    fun submitList(list: List<String>) {
        listDiffer.submitList(list)
    }

    class CategoriesViewHolder
    constructor(
        private val binding: CategorieCardViewBinding,
        private val customListeners: CustomListeners
    ) : RecyclerView.ViewHolder(binding.root) {

        val check = binding.chip4

        fun bind(item: String) {

            check.text = item
            check.setOnClickListener {
                customListeners.onItemSelected(
                    item
                )
            }

            //Custom onClick for whole item onClick
            binding.root.setOnClickListener {
                //Pass respective parameter, adapterPosition or pk.
                customListeners.onItemSelected(item)
            }


        }
    }

    // Interface to be inherited by view to provide
    //custom listeners for each item based on position
    //or other custom parameters (ex : Primary key)

    interface CustomListeners {
        fun onItemSelected(item: String)
        // add your view listeners here
        // ex : fun onSwitchChecked(..) , fun onItemLongPress(..)
    }
}
