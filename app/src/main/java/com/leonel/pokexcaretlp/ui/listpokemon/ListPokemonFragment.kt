package com.leonel.pokexcaretlp.ui.listpokemon

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.leonel.pokexcaretlp.R
import com.leonel.pokexcaretlp.adapter.PokemonAdapter
import com.leonel.pokexcaretlp.databinding.FragmentListPokemonBinding
import com.leonel.pokexcaretlp.model.PokemonResult
import com.leonel.pokexcaretlp.utils.Constant
import com.leonel.pokexcaretlp.utils.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class ListPokemonFragment : Fragment() {
    private var _binding: FragmentListPokemonBinding? = null
    private val binding get() = _binding!!
    private var isLoadingPagination = false
    private var page=1

    companion object {
        fun newInstance() = ListPokemonFragment()
    }

    private lateinit var viewModel: ListPokemonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListPokemonBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ListPokemonViewModel::class.java)
        binding.loading.isVisible=false

        GlobalScope.async {
            if (NetworkUtils.isInternetReachable(requireContext())) {
                viewModel.getPokemones(true)
            } else {
                viewModel.getPokemones(false)
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), getString(R.string.error_internet_connection), Toast.LENGTH_SHORT).show()
                }
            }
        }
        binding.rvPokemones.layoutManager = GridLayoutManager(activity, 2)
        viewModel.pokemonesLiveData.observe(viewLifecycleOwner){
            val adapter = PokemonAdapter(it)
            binding.rvPokemones.adapter = adapter
            adapter.setOnClickListener(object :
            PokemonAdapter.OnClickListener{
                override fun onClick(position: Int, model: PokemonResult) {
                    val id = model.url.split("/")
                    val bundle = Bundle()
                    bundle.putString(Constant.ID_POKEMON, id[id.size-2])
                    findNavController().navigate(R.id.action_listPokemonFragment_to_detailPokemonFragment, bundle)
                }
            })
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            binding.loading.isVisible = it
        }
        return binding.root
    }
}