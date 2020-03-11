package com.shahbaz514.finalterm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var signup_btn: Button? = null
    private var signin_btn: Button? = null
    private var user_email: EditText? = null
    private var user_pass:EditText? = null
    private var forget_pass_link:TextView? = null
    private var forget_pass_email: EditText? = null
    private var forget_pass_btn: Button? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signup_btn = findViewById(R.id.signup_btn_main)
        signin_btn = findViewById(R.id.signin_btn_main)
        user_email = findViewById(R.id.enter_email_main)
        user_pass = findViewById(R.id.enter_password_main)
        forget_pass_link = findViewById(R.id.forgetpassword_link)
        forget_pass_btn = findViewById(R.id.forget_pass_btn)
        forget_pass_email = findViewById(R.id.forget_email_edit_text)
        auth = FirebaseAuth.getInstance()

        forget_pass_link?.setOnClickListener()
        {
            forget_pass_email?.visibility = View.VISIBLE
            forget_pass_btn?.visibility = View.VISIBLE


            forget_pass_btn?.setOnClickListener()
            {
                ResetPassword()
            }
        }

        signup_btn?.setOnClickListener()
        {
            RegisterNewUser();
        }
        signin_btn?.setOnClickListener()
        {
            LoginUser();
        }
    }

    private fun ResetPassword()
    {
        var email = forget_pass_email?.text.toString()
        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(applicationContext, "Enter Email", Toast.LENGTH_SHORT).show()
        }
        else
        {
            auth?.sendPasswordResetEmail(email)?.addOnCompleteListener(object : OnCompleteListener<Void>
            {
                override fun onComplete(task: Task<Void>)
                {
                    if (task.isSuccessful)
                    {
                        Toast.makeText(applicationContext, "Reset Password Link Send Successfully...", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Error while Sending Reset Password Link...", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun LoginUser()
    {

        var email = user_email?.text.toString()
        var pass = user_pass?.text.toString()

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(applicationContext, "Enter Email", Toast.LENGTH_SHORT).show()
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_SHORT).show()
        }
        else
        {
            auth?.signInWithEmailAndPassword(email, pass)?.addOnCompleteListener(object : OnCompleteListener<AuthResult>
            {
                override fun onComplete(task: Task<AuthResult>)
                {
                    if (task.isSuccessful)
                    {
                        Toast.makeText(applicationContext, "LogedIn Successfull", Toast.LENGTH_SHORT).show()
                        val signUser:FirebaseUser = auth?.currentUser!!
                        if (signUser.isEmailVerified)
                        {
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                        }
                        else
                        {
                            Toast.makeText(applicationContext, "Email Not Varified", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Error while Login", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    private fun RegisterNewUser()
    {
        var email = user_email?.text.toString()
        var pass = user_pass?.text.toString()

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(applicationContext, "Enter Email", Toast.LENGTH_SHORT).show()
        }
        if (TextUtils.isEmpty(pass))
        {
            Toast.makeText(applicationContext, "Enter Password", Toast.LENGTH_SHORT).show()
        }
        else
        {
            auth?.createUserWithEmailAndPassword(email, pass)?.addOnCompleteListener(object : OnCompleteListener<AuthResult>
            {
                override fun onComplete(task: Task<AuthResult>)
                {
                    if (task.isSuccessful)
                    {
                        Toast.makeText(applicationContext, "Registration Successfull", Toast.LENGTH_SHORT).show()
                        val user:FirebaseUser = auth!!.currentUser!!
                        user.sendEmailVerification().addOnCompleteListener (object : OnCompleteListener<Void>{
                            override fun onComplete(task: Task<Void>)
                            {
                                if (task.isSuccessful)
                                {
                                    Toast.makeText(applicationContext, "Check Email And Varify the Email address....", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(this@MainActivity, MainActivity::class.java))
                                }
                                else
                                {
                                    val error = task.exception?.message
                                    Toast.makeText(applicationContext, "Error : "+error, Toast.LENGTH_SHORT).show()
                                }
                            }

                        })
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Error while registering", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }
}
