package com.priambudi19.findgithub.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.priambudi19.core.data.Resource
import com.priambudi19.core.domain.model.User
import com.priambudi19.findgithub.R
import com.priambudi19.findgithub.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModel()
    private var id: Int? = null
    private var username: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val favBundle = intent.getBundleExtra("favorite")
        if (favBundle == null) {
            id = intent.getIntExtra("id", 0)
            username = intent.getStringExtra("username")
        } else {
            id = favBundle.getInt("id")
            username = favBundle.getString("username")
        }
        if (username == null) {
            binding.group.visibility = View.GONE

        }
        prepareUI()
        getData()
    }

    private fun prepareUI() {
        with(binding.toolbar) {
            imgLogo.setOnClickListener { onBackPressed() }
            imgLogo
                .setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.ic_baseline_arrow_back_24
                    )
                )
            btnFav.visibility = View.GONE
            toolbarTitle.text = getString(R.string.detail)

        }


    }

    private fun getData() {
        lifecycleScope.launchWhenStarted {
            username?.let {
                viewModel.getDetailUser(it).observe(this@DetailActivity, { resource ->
                    when (resource) {
                        is Resource.Loading -> {
                            binding.loading.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {
                            val user = resource.data

                            with(binding) {
                                loading.visibility = View.GONE
                                group.visibility = View.VISIBLE
                                imgError.visibility = View.GONE
                            }
                            setUI(user)
                        }
                        is Resource.Error -> {
                            Toast.makeText(applicationContext, resource.message, Toast.LENGTH_SHORT).show()
                            with(binding) {
                                loading.visibility = View.GONE
                                group.visibility = View.GONE
                                imgError.visibility = View.VISIBLE
                            }
                        }
                        else -> Unit
                    }
                })
            }
        }
    }

    private fun setUI(user: User?) {
        if (user != null) {
            setDetail(user)
            id?.let { _id ->
                viewModel.checkFavorite(_id)
                    .observe(this, { check ->
                        if (check == 0) {
                            setFavoriteState(false)
                            binding.btnAdd.setOnClickListener {
                                viewModel.addToFavorite(
                                    user
                                )
                                Toast.makeText(
                                    this.applicationContext,
                                    getString(R.string.added),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        } else {
                            binding.btnAdd.setOnClickListener {
                                viewModel.deleteFavorite(
                                    user
                                )
                                Toast.makeText(
                                    this.applicationContext,
                                    getString(R.string.deleted),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                            setFavoriteState(true)
                        }
                    })
            }
        }
    }

    private fun setFavoriteState(state: Boolean) {
        if (state) {
            binding.btnAdd.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite
                )
            )
        } else {
            binding.btnAdd.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_unfavorite
                )
            )
        }
    }

    private fun setDetail(user: User?) {
        if (user != null) {
            with(binding) {
                Glide.with(imgDetail.context.applicationContext).load(user.avatarUrl).into(imgDetail)
                txtName.text = user.name
                txtUsername.text = user.login
                txtFollowers.text = user.followers.toString()
                txtFollowing.text = user.following.toString()
                txtRepo.text = user.publicRepos
                if (!user.company.isNullOrBlank()) {
                    txtCompany.text = user.company
                    imgCompany.visibility = View.VISIBLE
                    txtCompany.visibility = View.VISIBLE

                } else {
                    imgCompany.visibility = View.GONE
                    txtCompany.visibility = View.GONE
                }
                if (!user.location.isNullOrBlank()) {
                    txtLocation.text = user.location
                    imgLocation.visibility = View.VISIBLE
                    txtLocation.visibility = View.VISIBLE
                } else {
                    imgLocation.visibility = View.GONE
                    txtLocation.visibility = View.GONE
                }
            }


        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAfterTransition()
    }

}