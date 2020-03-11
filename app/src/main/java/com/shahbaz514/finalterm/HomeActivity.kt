package com.shahbaz514.finalterm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity()
{
    private var logout_btn: Button? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        logout_btn = findViewById(R.id.logout_btn)
        auth = FirebaseAuth.getInstance()

        logout_btn?.setOnClickListener()
        {
            auth?.signOut()

            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        }

    }
}
