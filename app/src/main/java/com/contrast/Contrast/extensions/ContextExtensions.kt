package com.contrast.Contrast.extensions

import android.content.Context

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build

fun Context.getAppVersionName(): String {
    val packageManager = packageManager.getPackageInfoCompat(packageName)
    return packageManager.versionName?:""
}
fun PackageManager.getPackageInfoCompat(
    packageName: String,
    flags: Int = 0,
): PackageInfo =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(flags.toLong()))
    } else {
        @Suppress("DEPRECATION")
        getPackageInfo(packageName, flags)
    }