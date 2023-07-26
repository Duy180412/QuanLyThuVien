package com.example.qltvkotlin.presentation.helper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.qltvkotlin.domain.helper.ActivityRetriever
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.channels.Channel

abstract class BasePermissionAccessible(
    private val activityRetriever: ActivityRetriever
) {

    private fun keyOf(requestCode: Any): String {
        return "PermissionAccessibleImpl:$requestCode"
    }

    protected suspend fun checkOrRequestPermission(
        requestCode: Int,
        vararg permissions: String
    ): Boolean {
        val activity = activityRetriever() as? FragmentActivity ?: return false
        val registry = activity.activityResultRegistry
        val cache = activity
            .getSharedPreferences("android:support:permission", Context.MODE_PRIVATE)

        val isAllAccepted = isAllAccessible(activity, permissions)
        if (isAllAccepted) return true
        val signal = Channel<Boolean>(1)

        var resultLauncher: ActivityResultLauncher<Intent>? = null
        var permissionLauncher: ActivityResultLauncher<Array<String>>? = null

        resultLauncher = registry.register(
            keyOf(requestCode),
            ActivityResultContracts.StartActivityForResult()
        ) {
            resultLauncher?.unregister()
            signal.trySend(isAllAccessible(activity, permissions))
        }

        permissionLauncher = registry.register(
            keyOf("permission:$requestCode"),
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            permissionLauncher?.unregister()

            if (isAllAccessible(activity, permissions)) {
                cache.edit().remove(requestCode.toString()).apply()
                signal.trySend(true)
                return@register
            }

            val isDenied = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permissions.first()
            )

            if (isDenied) {
                cache.edit()
                    .putString(requestCode.toString(), "denied")
                    .apply()
                signal.trySend(false)
                return@register
            }

            val isNeverAskAgain = cache.getString(requestCode.toString(), "") == "denied"

            if (isNeverAskAgain) cache.edit()
                .putString(requestCode.toString(), "never_ask_again")
                .apply()
            signal.trySend(false)
        }

        if (cache.getString(requestCode.toString(), "") == "never_ask_again"
            && !ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                permissions.first()
            )
        ) {
            activity.openSettings(requestCode)
        } else {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }

        return try {
            signal.receive()
        } catch (e: CancellationException) {
            resultLauncher.unregister()
            permissionLauncher.unregister()
            throw e
        }
    }

    private fun Activity.openSettings(code: Int) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${packageName}")
        )
        startActivityForResult(intent, code)
    }

    private fun isAllAccessible(
        activity: FragmentActivity,
        permissions: Array<out String>
    ): Boolean {
        return permissions.all {
            ActivityCompat.checkSelfPermission(
                activity,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}