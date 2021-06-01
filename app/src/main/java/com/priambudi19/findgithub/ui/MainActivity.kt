package com.priambudi19.findgithub.ui

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.priambudi19.findgithub.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q &&
            isTaskRoot &&
            supportFragmentManager.primaryNavigationFragment?.childFragmentManager?.backStackEntryCount ?: 0 == 0 &&
            supportFragmentManager.backStackEntryCount == 0
        ) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }
}