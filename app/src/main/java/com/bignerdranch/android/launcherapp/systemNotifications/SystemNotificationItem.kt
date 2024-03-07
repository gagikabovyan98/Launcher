package com.bignerdranch.android.launcherapp.systemNotifications

data class SystemNotificationItem(
    val timer: Long = SystemNotificationBuilder.MILLIS_IN_FUTURE,
    val type: SystemNotificationTypes,
    val description: String,
    val onActionClicked: (() -> Unit)? = null,
)