package com.ermilova.android.ramguide

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.ermilova.android.character_details.CharacterDetailsViewModel
import com.ermilova.android.character_details.databinding.FragmentCharacterDetailsBinding
import com.ermilova.android.core.utils.ViewModelFactory
import javax.inject.Inject

class CharacterDetailsFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory<CharacterDetailsViewModel>

    private val characterDetailsViewModel: CharacterDetailsViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var binding: FragmentCharacterDetailsBinding

    private lateinit var args: CharacterDetailsFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater)

        args = CharacterDetailsFragmentArgs.fromBundle(requireArguments())

        characterDetailsViewModel.getCharacter(args.characterId)

        val toolbar = requireActivity().findViewById<View>(R.id.toolbar) as Toolbar
        (requireActivity() as MainActivity).setSupportActionBar(toolbar)

        characterDetailsViewModel.character.observe(viewLifecycleOwner) { character ->
            binding.img.let { imageView ->
                Glide.with(imageView.context)
                    .load(character.image)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.broken_image)
                    )
                    .into(imageView)
            }
            binding.nameText.append(" ${character.name}")
            binding.speciesText.append(" ${character.species}")
            binding.genderText.append(" ${character.gender}")
            binding.statusText.append(" ${character.status}")
            binding.createdText.append(" ${character.created}")

            (this.activity as MainActivity).supportActionBar?.title = character.name
        }

        (activity as MainActivity).showUpButton()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireNotNull(activity).application as MyApplication).appComponent.inject(this)
    }
}