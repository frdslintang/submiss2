package com.dicoding.submiss2.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submiss2.data.local.database.FavoriteUser
import com.dicoding.submiss2.data.response.ResponseUser
import com.dicoding.submiss2.databinding.ActivityFavoriteUserBinding
import com.dicoding.submiss2.detail.DetailActivity
import com.dicoding.submiss2.main.UserAdapter


class userFavActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    private val adapter by lazy {
        UserAdapter { user ->
            Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                putExtra(DetailActivity.EXTRA_ID, user.id)
                putExtra(DetailActivity.EXTRA_URL, user.avatarUrl)
                startActivity(this)
            }
        }
    }

    private lateinit var viewModel: favViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUser.layoutManager = LinearLayoutManager(this)
        binding.rvUser.setHasFixedSize(true)
        binding.rvUser.adapter = adapter

        // Enable the up navigation (back button)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Favorite"

        viewModel = ViewModelProvider(this).get(favViewModel::class.java)

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(this@userFavActivity)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapList(it)
                Log.d("USERMAPPED", list.toString())
                adapter.setData(list)
            }
        }


    }

    private fun mapList(users: List<FavoriteUser>): ArrayList<ResponseUser.Item> {
        Log.d("LISTAWAL", users.toString())
        val listUsers = ArrayList<ResponseUser.Item>()

        for (user in users){
            val userMapped = ResponseUser.Item(
                login = user.login,
                htmlUrl = user.avatar_url,
                id = user.id,
                avatarUrl = user.avatar_url
            )
            listUsers.add(userMapped)
        }
        return listUsers

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Handle the up button click, e.g., navigate back
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

}