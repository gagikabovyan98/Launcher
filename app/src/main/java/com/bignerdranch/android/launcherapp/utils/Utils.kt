package com.bignerdranch.android.launcherapp.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

object Utils {
    fun openApp(context: Context, appUri: String, webUri: String) {
        try {
            val uri = Uri.parse(appUri)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        } catch (e: Exception) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(webUri))
            context.startActivity(webIntent)
        }
    }
}