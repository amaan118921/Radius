package com.example.radius.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.commit
import com.example.radius.R
import com.example.radius.databinding.ActivityMainBinding
import com.example.radius.helpers.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        addFragment(Constants.HOME_FRAGMENT)
    }


    private fun addFragment(id: String) {
        supportFragmentManager.commit {
            add(R.id.container, Constants.getFragment(id), null)
            setReorderingAllowed(true)
            addToBackStack(id)
        }
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}