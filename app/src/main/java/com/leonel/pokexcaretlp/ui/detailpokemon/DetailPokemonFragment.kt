package com.leonel.pokexcaretlp.ui.detailpokemon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.leonel.pokexcaretlp.R
import com.leonel.pokexcaretlp.databinding.FragmentDetailPokemonBinding
import com.leonel.pokexcaretlp.databinding.FragmentListPokemonBinding
import com.leonel.pokexcaretlp.utils.Constant
import com.leonel.pokexcaretlp.utils.NetworkUtils
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class DetailPokemonFragment : Fragment() {
    private var _binding: FragmentDetailPokemonBinding? = null
    private val binding get() = _binding!!
    companion object {
        fun newInstance() = DetailPokemonFragment()
    }

    private lateinit var viewModel: DetailPokemonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailPokemonBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(DetailPokemonViewModel::class.java)
        binding.loading.isVisible=false
        val idPokemon = arguments?.getString(Constant.ID_POKEMON)

        idPokemon?.let {
            GlobalScope.async {
                if (NetworkUtils.isInternetReachable(requireContext())) {
                    viewModel.getPokemon(it)
                    binding.constraintContent.visibility=View.VISIBLE
                    binding.constraintWithoutinternet.visibility=View.GONE
                } else {
                    binding.constraintContent.visibility=View.GONE
                    binding.constraintWithoutinternet.visibility=View.VISIBLE
/*                    withContext(Dispatchers.Main) {
                        Toast.makeText(requireContext(), getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show()
                    }*/
                }
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.loading.isVisible = it
        }

        viewModel.pokemonLiveData.observe(viewLifecycleOwner){
            Picasso.get().load(it.sprites.front_default).into(binding.imgCard)
            binding.txtNamepok.text = it.name
            binding.txtHp.text = it.stats[0].stat.name + " : " + it.stats[0].base_stat.toString()
            binding.txtElementone.text =it.stats[1].stat.name + " : " + it.stats[1].base_stat.toString()
            binding.txtElementwo.text =it.stats[2].stat.name + " : " + it.stats[2].base_stat.toString()
            binding.txtElementhree.text =it.stats[3].stat.name + " : " + it.stats[3].base_stat.toString()
            binding.txtElementfive.text =it.stats[4].stat.name + " : " + it.stats[4].base_stat.toString()
        }

        return binding.root
    }

}