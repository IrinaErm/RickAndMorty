package com.ermilova.android.characters_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ermilova.android.characters_list.R
import com.ermilova.android.characters_list.databinding.CharactersListItemBinding
import com.ermilova.android.core.Character

class CharactersListAdapter(private val onItemClick: (position: Int) -> Unit) :
    ListAdapter<Character, CharactersListAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    private var unfilteredList = mutableListOf<Character>()

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent, onItemClick)
    }

    class CharacterViewHolder private constructor(private val binding: CharactersListItemBinding, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Character) {
            binding.characterName.text = item.name

            item.image?.let {
                Glide.with(binding.characterImg.context)
                    .load(item.image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.broken_image)
                    )
                    .into(binding.characterImg)
            }
        }

        override fun onClick(view: View?) {
            onItemClick(adapterPosition)
        }

        companion object {
            fun from(parent: ViewGroup, onItemClick: (position: Int) -> Unit): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                //val view = layoutInflater.inflate(R.layout.characters_list_item, parent, false)
                val binding = CharactersListItemBinding.inflate(layoutInflater, parent, false)
                return CharacterViewHolder(binding, onItemClick)
            }
        }
    }

    fun filter(query: CharSequence?) {
        if (!query.isNullOrEmpty()) {
            submitList(
                unfilteredList.filter { character ->
                    character.name.contains(query, ignoreCase = true)
                }
            )
        } else {
            submitList(unfilteredList)
        }
    }

    override fun submitList(list: List<Character>?) {
        if (unfilteredList.isEmpty()) {
            list?.let { characters ->
                unfilteredList.addAll(characters)
            }
        }
        super.submitList(list)
    }
}

class CharacterDiffCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
        return oldItem == newItem
    }
}