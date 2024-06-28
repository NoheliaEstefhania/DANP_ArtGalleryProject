package com.example.danp_artgallery.beacon.utils
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat

class PermissionsHelper(val context: Context) {
    fun isPermissionGranted(permissionString: String): Boolean {
        return (
                ContextCompat.checkSelfPermission(
                    context,
                    permissionString
                ) == PackageManager.PERMISSION_GRANTED
        )
    }

    fun setFirstTimeAskingPermission(permissionString: String, isFirstTime: Boolean) {
        val sharedPreference = context.getSharedPreferences(
            "org.altbeacon.permissions",
            Context.MODE_PRIVATE
        )
        sharedPreference.edit().putBoolean(permissionString, isFirstTime).apply()
    }

    fun isFirstTimeAskingPermission(permissionString: String): Boolean {
        val sharedPreference = context.getSharedPreferences(
            "org.altbeacon.permissions",
            Context.MODE_PRIVATE
        )
        return sharedPreference.getBoolean(permissionString, true)
    }

    fun beaconScanPermissionGroupsNeeded(
        backgroundAccessRequested: Boolean = false
    ): List<Array<String>> {
        val permissions = ArrayList<Array<String>>()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissions.add(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && backgroundAccessRequested) {
            permissions.add(arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            permissions.add(
                arrayOf(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_CONNECT
                )
            )
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
        return permissions
    }

    fun allPermissionsGranted(backgroundAccessRequested: Boolean): Boolean {
        val permissionsGroups = beaconScanPermissionGroupsNeeded(backgroundAccessRequested)
        for (permissionsGroup in permissionsGroups) {
            for (permission in permissionsGroup) {
                if (!isPermissionGranted(permission)) {
                    return false
                }
            }
        }
        return true
    }
}
