package com.bignerdranch.android.launcherapp.utils

import android.content.Context
import com.bignerdranch.android.launcherapp.common.Constants

fun Context.openTelegram() {
    Utils.openApp(this, Constants.TG_URI, Constants.TG_WEB_URI)
}

fun Context.openVK() {
    Utils.openApp(this, Constants.VK_URI, Constants.VK_URI)
}