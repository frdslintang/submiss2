package com.dicoding.submiss2.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.submiss2.R
import com.dicoding.submiss2.data.response.ResponseUser
import com.dicoding.submiss2.databinding.ActivityMainBinding
import com.dicoding.submiss2.detail.DetailActivity
import com.dicoding.submiss2.favorite.userFavActivity
import com.dicoding.submiss2.settings.ThemeSettingsActivity
import com.dicoding.submiss2.utils.Result


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()
    private val adapter = UserAdapter { user ->
        Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.EXTRA_USERNAME, user.login)
            putExtra(DetailActivity.EXTRA_ID, user.id)
            putExtra(DetailActivity.EXTRA_URL, user.avatarUrl)
            startActivity(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        getThemeSetting()
        setUpSearchBar()
        observeUserResult()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun getThemeSetting() {
        viewModel.getThemeSettings().observe(this) { isDarkMode ->
            val nightMode = if (isDarkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(nightMode)
        }
    }

    private fun setUpSearchBar() {
        binding.apply {
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.favorite_menu -> {
                        val intent = Intent(this@MainActivity, userFavActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.setting_menu -> {
                        val intent = Intent(this@MainActivity, ThemeSettingsActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> true
                }
            }

            searchView.setupWithSearchBar(searchBar)
            searchView.editText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    val query = searchView.text.toString().trim()
                    searchBar.text = searchView.text
                    searchView.hide()
                    viewModel.getUser(query)
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
        }
    }

    private fun observeUserResult() {
        viewModel.resultUser.observe(this) { result ->
            when (result) {
                is Result.Success<*> -> handleSuccess(result.data)
                is Result.Error -> handleError(result.exception.message.toString())
                is Result.Loading -> showLoading(result.isLoading)
            }
        }
    }

    private fun handleSuccess(data: Any?) {
        if (data is MutableList<*> && data.all { it is ResponseUser.Item }) {
            val userList = data as MutableList<ResponseUser.Item>
            adapter.setData(userList)
        } else {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

}
