package com.movieapp.utils

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.movieapp.data.remote.api.EndPoints
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import java.io.Serializable


fun Context.showSnackBar(view: View, msg: String) {
    Snackbar.make(this, view, msg, Snackbar.LENGTH_LONG).show()
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(path: String) {
    val url = "${EndPoints.IMAGES_BASE_URL}${path}"
    Glide.with(this.context)
        .load(url)
        .into(this)
}

inline fun <reified T : Serializable?> Bundle.serializable(key: String): T? = when {
    Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}