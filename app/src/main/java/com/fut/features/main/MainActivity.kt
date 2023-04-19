package com.fut.features.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.manager.SupportRequestManagerFragment
import com.fut.R
import com.fut.core.utils.Constants
import com.fut.core.utils.extensions.getSharedPreferences
import com.fut.databinding.ActivityMainBinding
import com.fut.features.highlights.presentation.HighlightsFragment
import com.fut.features.profile.ProfileFragment
import com.fut.features.search.presentation.SearchFragment
import com.fut.features.matches.presentation.MatchesFragment
import com.fut.core.utils.extensions.push
import com.fut.features.userpref.presentation.UserStartPreferencesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = requireNotNull(_binding)

    private var backBtnVisibility = false
        set(value) {
            field = value
            supportActionBar!!.setDisplayHomeAsUpEnabled(value)
        }

    var bnvMenuVisibility = false
        set(value) {
            field = value
            binding.bnvMenu.visibility = if (value) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActivity()
    }

    private fun setupActivity() {
        setSupportActionBar(binding.tlbDefault.toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        bnvMenuVisibility = true
        setupBackBtn()
        checkIfHasFavoriteTeam()
        setupBottomNav()
    }

    private fun checkIfHasFavoriteTeam() {
        val sharedPref = getSharedPreferences()
        val teamId = sharedPref.getString(Constants.SELECTED_TEAM_ID, "")
        if (teamId?.isEmpty() == true) push(UserStartPreferencesActivity())
    }

    private fun setupBackBtn() {
        val fragmentManager = supportFragmentManager
        fragmentManager.addFragmentOnAttachListener { _, fragment ->
            backBtnVisibility =
                !(fragment is SupportRequestManagerFragment || fragment is HighlightsFragment || fragment is MatchesFragment || fragment is ProfileFragment || fragment is SearchFragment)
        }
    }

    private fun setupBottomNav() {
        push(HighlightsFragment())
        binding.bnvMenu.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.highlights -> push(HighlightsFragment())
                R.id.matches -> push(MatchesFragment())
                R.id.search -> push(SearchFragment())
                R.id.profile -> push(ProfileFragment())
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                backBtnVisibilityHandler()
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun backBtnVisibilityHandler() {
        val backStackCount = supportFragmentManager.backStackEntryCount
        backBtnVisibility = backStackCount > 1
    }

}