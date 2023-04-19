package com.fut.core.utils.extensions

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.fut.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou

fun ImageView.load(url: String?, context: Context) {
    Glide.with(context)
        .load(url ?: "")
        .error(R.drawable.unkown_team_placeholder)
        .error(R.drawable.unkown_team_placeholder)
        .into(this)
}

fun ImageView.load(url: String?, activity: AppCompatActivity) {
    GlideToVectorYou.justLoadImage(activity, url?.toUri(), this)
}