package com.priambudi19.findgithub.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.priambudi19.core.data.Resource
import com.priambudi19.core.ui.UserAdapter
import com.priambudi19.findgithub.R
import com.priambudi19.findgithub.databinding.FragmentSearchBinding
import com.priambudi19.findgithub.ui.detail.DetailActivity
import org.koin.android.viewmodel.ext.android.viewModel


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModel()
    private var userAdapter: UserAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        userAdapter = UserAdapter(emptyList())
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainToolbar.btnFav.setOnClickListener {
            toFavoriteFragment()
        }
        setSearchBar()
        setRecyclerView()

    }


    private fun toFavoriteFragment() {
        val fragment = instantiateFragment()
        if (fragment != null) {
            parentFragmentManager.commit {
                replace(R.id.container_fragment, fragment)
                addToBackStack(null)
            }
        }
    }

    private fun instantiateFragment(): Fragment? {
        return try {
            Class.forName("com.priambudi19.favorite.ui.FavoriteFragment").newInstance() as Fragment
        } catch (e: Exception) {
            Toast.makeText(context?.applicationContext, "Module not found", Toast.LENGTH_SHORT)
                .show()
            null
        }
    }


    private fun setSearchBar() {
        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getData(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })


    }

    private fun setRecyclerView() {
        with(binding.rvListUser) {
            layoutManager = LinearLayoutManager(context)
            adapter = userAdapter
        }
    }

    fun getData(query: String?) {
        if (query != null && query.isNotBlank()) {
            binding.progressBar.visibility = View.VISIBLE
            viewModel.getSearchUser(query).observe(viewLifecycleOwner, {
                when (it) {
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        if (it.data.isNullOrEmpty()) {
                            Toast.makeText(
                                context?.applicationContext,
                                getString(R.string.user_not_found),
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            binding.rvListUser.visibility = View.VISIBLE
                            binding.groupNoData.visibility = View.GONE
                            userAdapter = UserAdapter(it.data ?: emptyList())
                            binding.rvListUser.adapter = userAdapter
                            userAdapter?.let { adapter ->
                                adapter.onItemClick = { user ->
                                    val intent = Intent(activity, DetailActivity::class.java)
                                    val bundle: Bundle =
                                        bundleOf("id" to user.id, "username" to user.login)
                                    intent.putExtras(bundle)
                                    startActivity(intent)
                                }
                            }
                            binding.imgError.visibility = View.GONE
                            binding.groupNoData.visibility = View.GONE
                        }


                    }
                    is Resource.Error -> {
                        binding.rvListUser.visibility = View.GONE
                        binding.imgError.visibility = View.VISIBLE
                        binding.groupNoData.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                        binding.groupNoData.visibility = View.GONE
                        Toast.makeText(
                            context?.applicationContext,
                            "${it.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is Resource.Loading -> {
                        binding.rvListUser.visibility = View.VISIBLE
                        binding.imgError.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        userAdapter = null
    }


}