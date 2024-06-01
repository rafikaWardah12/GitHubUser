package com.example.githubuserfika.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserfika.R
import com.example.githubuserfika.databinding.ActivityMainBinding
import com.example.githubuserfika.response.ItemsItem
import com.example.githubuserfika.ui.ViewModelFactory
import com.example.githubuserfika.ui.insert.FavoriteActivity
import com.example.githubuserfika.ui.list.ListUserAdapter
import com.example.githubuserfika.ui.list.ListUserViewModel
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListUserAdapter
    private val listUserViewModel: ListUserViewModel by viewModels{ViewModelFactory.getInstance(this)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        listUserViewModel.userData.observe(this){
            setDataUser(it)
        }
        listUserViewModel.isLoading.observe(this){isLoading ->
            showLoading(isLoading)
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvList.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvList.addItemDecoration(itemDecoration)

        adapter = ListUserAdapter()
        binding.rvList.adapter = adapter

        with(binding) {
            val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)
            listUserViewModel.getThemeSetting().observe(this@MainActivity){
                switchTheme.isChecked = it
            }
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean  ->
                if(isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    switchTheme.isChecked = true
                    listUserViewModel.saveThemeSetting(true)
                } else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    switchTheme.isChecked = false
                    listUserViewModel.saveThemeSetting(false)
                }

            }
            searchView.setupWithSearchBar(searchBar)
            searchBar.setOnMenuItemClickListener{
                when(it.itemId){
                    R.id.list_favorite->{
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else->{
                        Log.d("Error Message", it.itemId.toString())
                        false
                    }
                }
            }
            searchView
                .editText
                .setOnEditorActionListener {textView, actionId, event ->
                    searchBar.text = searchView.text
                    listUserViewModel.findUser(searchBar.text.toString())
                    searchView.hide()
                    Toast.makeText(this@MainActivity, searchView.text, Toast.LENGTH_SHORT).show()
                    false
                }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDataUser(it: List<ItemsItem>?) {
       adapter.submitList(it)
           binding.searchBar.setText("")

    }
}