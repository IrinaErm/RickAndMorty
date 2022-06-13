package com.ermilova.android.characters_list.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ermilova.android.characters_list.R
import com.ermilova.android.characters_list.databinding.CharactersListItemBinding
import com.ermilova.android.core.domain.model.CharacterDomainModel

class CharactersListAdapter(private val onItemClick: (characterId: Long) -> Unit) :
    PagingDataAdapter<CharacterDomainModel, CharactersListAdapter.CharacterViewHolder>(
        CharacterDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent, onItemClick)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(item) }
    }

    class CharacterViewHolder private constructor(
        private val binding: CharactersListItemBinding,
        private val onItemClick: (characterId: Long) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        var characterId = 1L

        fun bind(item: CharacterDomainModel) {
            binding.characterName.text = item.name
            characterId = item.id

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
            onItemClick(characterId)
        }

        companion object {
            fun from(parent: ViewGroup, onItemClick: (characterId: Long) -> Unit): CharacterViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CharactersListItemBinding.inflate(layoutInflater, parent, false)
                return CharacterViewHolder(binding, onItemClick)
            }
        }
    }

}

class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterDomainModel>() {
    override fun areItemsTheSame(
        oldItem: CharacterDomainModel,
        newItem: CharacterDomainModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CharacterDomainModel,
        newItem: CharacterDomainModel
    ): Boolean {
        return oldItem == newItem
    }
}