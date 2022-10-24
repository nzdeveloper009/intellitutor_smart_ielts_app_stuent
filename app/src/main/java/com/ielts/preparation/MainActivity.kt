package com.ielts.preparation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.ielts.preparation.databinding.ActivityMainBinding
import com.ielts.preparation.ui.dashboard.viewModel.DashboardViewModel
import com.ielts.preparation.viewModel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        dashboardViewModel = ViewModelProvider(this)[DashboardViewModel::class.java]
        dashboardViewModel.saveTitle(this, getString(R.string.ielts_preparation_actionbar))
        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getToolbarTITLE(this)?.observe(this, Observer {
            binding.toolbar.tvActionBarHeading.text = it.toString()
        })
        mainViewModel.getButtonVisibility(this)?.observe(this) {
            binding.toolbar.btnGoBack.isVisible = it
            binding.toolbar.btnIconActionBar.isVisible = !it
        }
        binding.toolbar.btnGoBack.setOnClickListener {
            super.onBackPressed()
        }

        binding.toolbar.btnIconActionBar.setOnClickListener(View.OnClickListener {
            auth.signOut()
            val intent = Intent(applicationContext, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom)
            finish()
        })

    }


}
