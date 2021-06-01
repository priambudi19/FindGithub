package com.priambudi19.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.priambudi19.core.ui.UserAdapter
import com.priambudi19.favorite.databinding.FragmentFavoriteBinding
import com.priambudi19.favorite.di.favoriteModule
import com.priambudi19.findgithub.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class FavoriteFragment : Fragment() {
    private val viewModel : FavoriteViewModel by viewModel()
    private var _binding : FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarFavorite.btnBackFav.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        viewModel.getListFavorite().observe(viewLifecycleOwner,{
            Log.d("TAG", "Favorite: $it")
            binding.rvFavorite.apply {
                val userAdapter = UserAdapter(it)
                adapter =  userAdapter
                userAdapter.let { adapter ->
                    adapter.onItemClick = { user ->
                        val intent = Intent(activity, DetailActivity::class.java)
                        val bundle: Bundle =
                            bundleOf("id" to user.id, "username" to user.login)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                }
                layoutManager = LinearLayoutManager(context)
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        unloadKoinModules(favoriteModule)
    }


}