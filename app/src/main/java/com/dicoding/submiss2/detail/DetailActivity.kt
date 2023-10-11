package com.dicoding.submiss2.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.CircleCropTransformation
import com.dicoding.submiss2.R
import com.dicoding.submiss2.data.response.ResponseDetail
import com.dicoding.submiss2.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.properties.Delegates
import com.dicoding.submiss2.utils.Result

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel by viewModels<DetailViewModel>()

    private var id by Delegates.notNull<Int>()
    private lateinit var username:String
    private lateinit var avatarUrl:String


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra(EXTRA_USERNAME)!!
        id = intent.getIntExtra(EXTRA_ID, 0)
        avatarUrl = intent.getStringExtra(EXTRA_URL)!!

        viewModel.getDetailUser(username)
        Log.d("USERNAME1", username)

        viewModel.resultDetailUser.observe(this) {
            when (it) {
                is Result.Success<*> -> {
                    val user = it.data as ResponseDetail
                    binding.image.load(user.avatarUrl) {
                        transformations(CircleCropTransformation())
                    }
                    binding.nama.text = user.name
                    binding.username.text = user.login
                    val followersCount = user.followers
                    val followingCount = user.following

                    binding.tvFollowers.text = "$followersCount Followers"
                    binding.tvFollowing.text = "$followingCount Following"
                }

                is Result.Error -> {
                    Toast.makeText(this, it.exception.message.toString(), Toast.LENGTH_SHORT).show()
                }

                is Result.Loading -> {
                    binding.progressBar.isVisible = it.isLoading
                }
            }
        }


        val fragment = mutableListOf<Fragment>(
            FollowFragment.newInstance(FollowFragment.Followers),
            FollowFragment.newInstance(FollowFragment.Following),
        )
        val titleFragments = mutableListOf(
            getString(R.string.followers), getString(R.string.following)
        )

        val adapter = DetailAdapter(this, fragment)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, posisi ->
            tab.text = titleFragments[posisi]
        }.attach()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    viewModel.getFollowers(username)
                } else {
                    viewModel.getFollowing(username)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        viewModel.getFollowers(username)
        viewModel.getFollowing(username)

        userFavorite()

    }

    private fun userFavorite(){
        var _isChecked = false
        CoroutineScope(Dispatchers.IO).launch {
            val count = viewModel.checkUser(id)
            withContext(Dispatchers.Main){
                if (count !=null){
                    if (count>0){
                        binding.favoriteToggle.isChecked = true
                        _isChecked = true
                    }else{
                        binding.favoriteToggle.isChecked = false
                        _isChecked = false
                    }
                }
            }
        }

        binding.favoriteToggle.setOnClickListener{
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.addToFavorite(username, id, avatarUrl)
                Toast.makeText(this, "$username is added to favorites!", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.removeFromFavorite(id)
                Toast.makeText(this, "$username is removed from favorites!", Toast.LENGTH_SHORT).show()
            }
            binding.favoriteToggle.isChecked = _isChecked
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_URL = "extra_url"
    }
}
