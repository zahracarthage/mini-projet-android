package com.example.mini_projet.views.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mini_projet.models.Restaurant
import com.example.mini_projet.utils.ApiActions
import com.example.mini_projet.utils.AppResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel : ViewModel() {
    var resturantsStable: java.util.ArrayList<Restaurant>? = arrayListOf()
    private val TAG = "HomeViewModel"
    val apiActions = ApiActions()

    val categories = MutableLiveData<ArrayList<String>>()
    val resturants = MutableLiveData<ArrayList<Restaurant>>()

    fun getCategories() = viewModelScope.launch {
        apiActions.getCategories().catch { e ->
            Log.e(TAG, "getCategories: ", e)
        }.distinctUntilChanged().collect {
            when (it) {
                is AppResult.Error.NonRecoverableError -> {
                    Log.e(TAG, "getCategories: ", it.exception)
                }
                is AppResult.Error.RecoverableError -> {
                    Log.e(TAG, "getCategories: ", it.exception)
                }
                is AppResult.Failure -> {


                }
                AppResult.InProgress -> {}
                is AppResult.Success -> {
                    categories.postValue(it.data ?: arrayListOf())
                }
            }

        }
    }

    fun getResturants() = viewModelScope.launch {
        apiActions.getAllRestaurants().catch { e ->
            Log.e(TAG, "getCategories: ", e)
        }.distinctUntilChanged().collect {
            when (it) {
                is AppResult.Error.NonRecoverableError -> {
                    Log.e(TAG, "getRestaurants: ", it.exception)
                }
                is AppResult.Error.RecoverableError -> {
                    Log.e(TAG, "getRestaurants: ", it.exception)
                }
                is AppResult.Failure -> {


                }
                AppResult.InProgress -> {}
                is AppResult.Success -> {
                    resturantsStable = it.data
                    resturants.postValue(it.data ?: arrayListOf())
                }
            }

        }
    }

    fun handleSelection(item: String) {
        println("resurants ${resturantsStable}")

        val filtered: ArrayList<Restaurant>? = resturantsStable?.filter {
            it.category?.contains(item) == true
        } as ArrayList<Restaurant>?

        resturants.value = filtered ?: arrayListOf()

    }


}