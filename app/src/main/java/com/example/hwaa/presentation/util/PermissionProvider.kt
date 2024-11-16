package com.example.hwaa.presentation.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

interface PermissionsResultCallback {
    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    )
}

object PermissionProvider {

    val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    const val CAMERA_PERMISSION_REQUEST_CODE = 100
    const val READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 101
    const val WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 102

    private fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
        activity.requestPermissions(arrayOf(permission), requestCode)
    }

    fun checkPermission(
        activity: Activity,
        permission: String,
        resultHandler: PermissionsResultCallback
    ) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            resultHandler.onRequestPermissionsResult(
                when (permission) {
                    Manifest.permission.CAMERA -> CAMERA_PERMISSION_REQUEST_CODE
                    Manifest.permission.READ_EXTERNAL_STORAGE -> READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
                    else -> 0
                },
                arrayOf(permission),
                intArrayOf(PackageManager.PERMISSION_GRANTED)
            )
        } else {
            requestPermission(
                activity, permission, when (permission) {
                    Manifest.permission.CAMERA -> CAMERA_PERMISSION_REQUEST_CODE
                    Manifest.permission.READ_EXTERNAL_STORAGE -> READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
                    Manifest.permission.WRITE_EXTERNAL_STORAGE -> WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE
                    else -> 0
                }
            )
        }
    }
}
