package com.leonel.pokexcaretlp.ui.login

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.leonel.pokexcaretlp.R
import com.leonel.pokexcaretlp.utils.Constant
import com.leonel.pokexcaretlp.utils.CustomDialog
import com.leonel.pokexcaretlp.utils.StringResourcesProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginActivityViewModel  @Inject constructor(private val stringResourcesProvider: StringResourcesProvider): ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    var isValidateUser = MutableLiveData<Boolean>()
    var userError = MutableLiveData<String>()
    var passError = MutableLiveData<String>()

    fun consultUser(user: String, password: String){
        isValidateUser.postValue(user == Constant.USER && password == Constant.PASSWORD)
    }

    fun validateInfo(user: String, password: String){
        var validate = true
        if(user.isNullOrBlank()){
            userError.postValue(stringResourcesProvider.getString(R.string.txt_setError))
            validate= false
        }
        if(password.isNullOrBlank()){
            passError.postValue(stringResourcesProvider.getString(R.string.txt_setError))
            validate= false
        }
        if(validate){ consultUser(user,password)}
    }

    fun alertvalidation(context: Context){
        val customDialog = CustomDialog(context)
        customDialog.one(
            description = stringResourcesProvider.getString(R.string.login_dialog_error),
            titleOfPositiveButton = stringResourcesProvider.getString(R.string.dialog_btn_accept),
            positiveButtonFunction = {

            }).show()
    }

}