package com.leonel.pokexcaretlp.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.leonel.pokexcaretlp.MainActivity
import com.leonel.pokexcaretlp.R
import com.leonel.pokexcaretlp.databinding.ActivityLoginBinding
import com.leonel.pokexcaretlp.model.User
import com.leonel.pokexcaretlp.utils.Constant
import com.leonel.pokexcaretlp.utils.CustomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        validateSesion()

        binding.btnLogin.setOnClickListener {
            viewModel.validateInfo(binding.etxtUser.text.toString(),binding.etxtPassword.text.toString())
        }
        binding.txtPassshow.setOnClickListener {
            showDialogCredentials()
        }

        viewModel.isValidateUser.observe(this ){
            if(it){
                saveSesion()
            }
            else
                viewModel.alertvalidation(this)
        }
        viewModel.passError.observe(this) { binding.etxtPassword.error = getString(R.string.txt_setError) }
        viewModel.userError.observe(this){ binding.etxtUser.error = getString(R.string.txt_setError) }

    }

    private fun validateSesion(){
        val sharedPref = this?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        val inicializado = sharedPref.getBoolean(Constant.INITSHARED, false)
        if(inicializado){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            }
    }

    private fun saveSesion(){
        val sharedPref = this?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(Constant.INITSHARED, true)
            apply()
        }
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showDialogCredentials(){
        val customDialog = CustomDialog(this)
        customDialog.one(
            description = "Los datos de acceso son: \n Usuario -> ${Constant.USER} \n Password -> ${Constant.PASSWORD}",
            titleOfPositiveButton = getString(R.string.dialog_btn_accept),
            positiveButtonFunction = {
            }).show()
    }
}