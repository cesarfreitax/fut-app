package com.fut.core.utils.extensions

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.fut.R

fun AppCompatActivity.push(fragment: Fragment) {
    supportFragmentManager.beginTransaction().apply {
        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).replace(
            R.id.fcv_main,
            fragment
        )
        commit()
    }
}

fun AppCompatActivity.push(
    activity: AppCompatActivity,
    data: String? = "",
    clearStack: Boolean? = false
) {
    val intent = Intent(this, activity::class.java)
    intent.putExtra("data", data)
    if (clearStack == true) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

fun Fragment.push(fragmentDestination: Fragment, args: Bundle? = null) {
    val transaction = parentFragmentManager.beginTransaction()
    transaction.addToBackStack("")
    args?.let {
        val bundle = Bundle(args)
        fragmentDestination.arguments = bundle
    }
    transaction.replace(R.id.fcv_main, fragmentDestination).commit()
}

fun Fragment.push(activity: AppCompatActivity, data: String? = "", clearStack: Boolean? = false) {
    val intent = Intent(requireActivity(), activity::class.java)
    intent.putExtra("data", data)
    if (clearStack == true) {
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}
