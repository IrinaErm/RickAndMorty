package com.ermilova.android.characters_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ermilova.android.characters_list.R
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

    class CharacterViewHolder private constructor(itemView: View, private val onItemClick: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val characterName: TextView = itemView.findViewById(R.id.character_name)
        private val characterImage: ImageView = itemView.findViewById(R.id.character_img)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Character) {
            characterName.text = item.name

            item.image?.let {
                Glide.with(characterImage.context)
                    .load(item.image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.broken_image)
                    )
                    .into(characterImage)
            }
        }

        override fun onClick(view: View?) {
            onItemClick(adapterPosition)
        }

        companion object {
            fun from(parent: ViewGroup, onItemClick: (position: Int) -> Unit): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.characters_list_item, parent, false)
                return CharacterViewHolder(view, onItemClick)
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