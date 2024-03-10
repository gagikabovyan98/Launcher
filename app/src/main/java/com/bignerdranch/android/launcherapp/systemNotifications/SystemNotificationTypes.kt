package com.bignerdranch.android.launcherapp.systemNotifications

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bignerdranch.android.launcherapp.R

sealed class SystemNotificationTypes(
    @DrawableRes
    val iconRes: Int,
    @ColorRes
    val color: Int,
    @StringRes
    val title: Int,
) {
    data object Success : SystemNotificationTypes(
        R.drawable.system_not_success,
        R.color.system_not_success,
        R.string.system_not_success,
    )
    data object Error : SystemNotificationTypes(
        R.drawable.system_not_error,
        R.color.system_not_error,
        R.string.system_not_error,
    )
    data object Alert : SystemNotificationTypes(
        R.drawable.system_not_alert,
        R.color.system_not_alert,
        R.string.system_not_alert,
    )
    data object Info : SystemNotificationTypes(
        R.drawable.system_not_info,
        R.color.system_not_info,
        R.string.system_not_info,
    )
    data object Action : SystemNotificationTypes(
        R.drawable.system_not_action,
        R.color.system_not_action,
        R.string.system_not_action,
    )

    fun isAction() = this is Action

    companion object {
        private var index = 0
        fun getRegularType(): SystemNotificationTypes {
            return when ((index++) % 5) {
                0 -> Success
                1 -> Error
                2 -> Alert
                3 -> Info
                else -> Action
            }
        }
    }
}