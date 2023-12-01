package com.by_jogehrt.mygot.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.by_jogehrt.mygot.R
import com.by_jogehrt.mygot.databinding.ActivityLogginBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException

class Loggin : AppCompatActivity() {
    private lateinit var binding: ActivityLogginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var password: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Para firebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()


        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MasterViewInfo::class.java))
            finish()
        }
        
        with(binding) {
            binding.btnLogin.setOnClickListener {
                if (!checkFields()) return@setOnClickListener
                //autenticamos al usuario
                checkUser(email, password)
            }
            binding.btnRegister.setOnClickListener {
                if (!checkFields()) return@setOnClickListener
                //registramos al usuario
                registerUser(email, password)
            }

            binding.tvResetPassword.setOnClickListener {
                val resetEmail = EditText(it.context)
                resetEmail.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS

                val passwordResetDialog = AlertDialog.Builder(it.context)
                    .setTitle(getString(R.string.restore_pass))
                    .setMessage(getString(R.string.log_to_res))
                    .setView(resetEmail)
                    .setPositiveButton(getString(R.string.send)) { _, _ ->
                        val mail = resetEmail.text.toString()
                        if (mail.isNotEmpty()) {
                            firebaseAuth.sendPasswordResetEmail(mail).addOnSuccessListener {
                                Toast.makeText(
                                    this@Loggin,
                                    getString(R.string.mail_to_res_send),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.addOnFailureListener {
                                Toast.makeText(
                                    this@Loggin,
                                    getString(R.string.mail_to_res_fail),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(
                                this@Loggin,
                                getString(R.string.mail_please),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }
        }
    }
    private fun checkFields(): Boolean{
        email = binding.tietEmail.text.toString().trim() //para que quite espacios en blanco
        password = binding.tietPassword.text.toString().trim()

        if(email.isEmpty()){
            binding.tietEmail.error = getString(R.string.mail_please)
            binding.tietEmail.requestFocus()
            return false
        }

        if(password.isEmpty() || password.length < 6){
            binding.tietPassword.error = getString(R.string.password_request)
            binding.tietPassword.requestFocus()
            return false
        }

        return true
    }

    private fun errorManager(task: Task<AuthResult>){
        var errorCode = ""

        try{
                errorCode = (task.exception as FirebaseAuthException).errorCode
        }catch(e: Exception){
                e.printStackTrace()
        }

        when(errorCode){
            "ERROR_INVALID_EMAIL" -> {
                Toast.makeText(this, getString(R.string.error_mail_format), Toast.LENGTH_SHORT).show()
                binding.tietEmail.error = getString(R.string.error_mail_format)
                binding.tietEmail.requestFocus()
            }
            "ERROR_WRONG_PASSWORD" -> {
                Toast.makeText(this, getString(R.string.error_pwrd_format), Toast.LENGTH_SHORT).show()
                binding.tietPassword.error = getString(R.string.error_pwrd_format)
                binding.tietPassword.requestFocus()
                binding.tietPassword.setText("")

            }
            "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL" -> {
                //An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.
                Toast.makeText(this, getString(R.string.error_count_albind), Toast.LENGTH_SHORT).show()
            }
            "ERROR_EMAIL_ALREADY_IN_USE" -> {
                Toast.makeText(this, getString(R.string.error_mail_aluse), Toast.LENGTH_LONG).show()
                binding.tietEmail.error = getString(R.string.error_mail_aluse)
                binding.tietEmail.requestFocus()
            }
            "ERROR_USER_TOKEN_EXPIRED" -> {
                Toast.makeText(this, getString(R.string.error_sesion_exp), Toast.LENGTH_LONG).show()
            }
            "ERROR_USER_NOT_FOUND" -> {
                Toast.makeText(this, getString(R.string.error_user_notf), Toast.LENGTH_LONG).show()
            }
            "ERROR_WEAK_PASSWORD" -> {
                Toast.makeText(this, getString(R.string.error_pwrd_format), Toast.LENGTH_LONG).show()
                binding.tietPassword.error = getString(R.string.password_request)
                binding.tietPassword.requestFocus()
            }
            "NO_NETWORK" -> {
                Toast.makeText(this, getString(R.string.error_no_online), Toast.LENGTH_LONG).show()
            }
            else -> {
                Toast.makeText(this, getString(R.string.error_anycase), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkUser(usr: String, psw: String){
        binding.progressBar.visibility = View.VISIBLE
        firebaseAuth.signInWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->
            binding.progressBar.visibility = View.GONE
            if(authResult.isSuccessful){
                Toast.makeText(this, getString(R.string.succ_aut), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MasterViewInfo::class.java))
                finish()
            }else errorManager(authResult)

        }
    }

    private fun registerUser(usr: String, psw: String){
        binding.progressBar.visibility = View.VISIBLE
        firebaseAuth.createUserWithEmailAndPassword(usr, psw).addOnCompleteListener { authResult ->

            if(authResult.isSuccessful){
                val user_fb = firebaseAuth.currentUser

                user_fb?.sendEmailVerification()?.addOnSuccessListener {
                    Toast.makeText(this, getString(R.string.mail_to_log_send), Toast.LENGTH_SHORT).show()
                }?.addOnFailureListener {
                    Toast.makeText(this, getString(R.string.mail_to_log_fail), Toast.LENGTH_SHORT).show()
                }

                Toast.makeText(this, getString(R.string.logged_user), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MasterViewInfo::class.java))
                finish()
            }else errorManager(authResult)
            }
        }
}