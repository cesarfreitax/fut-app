package com.fut.core.utils.extensions

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun Fragment.getSharedPreferences() = activity?.getSharedPreferences("prefs", Context.MODE_PRIVATE)

fun AppCompatActivity.getSharedPreferences(): SharedPreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE)