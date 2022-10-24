package com.ielts.preparation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.ielts.preparation.LoginActivity
import com.ielts.preparation.MainActivity
import com.ielts.preparation.R
import com.ielts.preparation.databinding.ActivitySplashScreenBinding

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    var auth: FirebaseAuth? = null
    var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.btnSplashContinue.setOnClickListener {

            CheckUserLogedIn()
            /*val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            this.finish()*/
        }
    }

    private fun CheckUserLogedIn() {
        if (auth!!.currentUser != null) {
            if (auth!!.currentUser!!.isEmailVerified) {
                auth = FirebaseAuth.getInstance()
                databaseReference = FirebaseDatabase.getInstance().getReference("User")
                    .child(FirebaseAuth.getInstance().uid!!)
                databaseReference!!.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val role = dataSnapshot.child("role").getValue(
                            String::class.java
                        )
                        val username = dataSnapshot.child("username").getValue(
                            String::class.java
                        )
                        if (role == "student") {
                            val intent =
                                Intent(this@SplashScreenActivity, MainActivity::class.java)
                            intent.putExtra("username", username)
                            startActivity(intent)
                            overridePendingTransition(R.anim.anim_slideup, R.anim.anim_slidebottom)
                            finish()
                        }
                    }

                    override fun onCancelled(databaseError: DatabaseError) {
                        Toast.makeText(
                            this@SplashScreenActivity,
                            databaseError.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            }
        } else {
            nextActivity() //memory leak
        }
    }

    private fun nextActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        this.finish()
    }
}