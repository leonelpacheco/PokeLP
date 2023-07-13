package com.leonel.pokexcaretlp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.leonel.pokexcaretlp.ui.login.LoginActivity
import com.leonel.pokexcaretlp.utils.Constant
import com.leonel.pokexcaretlp.utils.CustomDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)

    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.exit_menu->{
                val customDialog = CustomDialog(this)
                customDialog.one(
                    description = getString(R.string.dialog_info_txt_contenido),
                    titleOfPositiveButton = getString(R.string.dialog_btn_accept),
                    titleOfNegativeButton = getString(R.string.dialog_btn_cancel),
                    positiveButtonFunction = {
                        deleteSesion()
                    },
                    negativeButtonFunction = {

                    }).show()

            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun deleteSesion(){
        val sharedPref = this?.getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE) ?: return
        with (sharedPref.edit()) {
            putBoolean(Constant.INITSHARED, false)
            apply()
        }
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

}