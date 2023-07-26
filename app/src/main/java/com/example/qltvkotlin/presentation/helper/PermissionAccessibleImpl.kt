package com.example.qltvkotlin.presentation.helper

import android.os.Build
import com.example.qltvkotlin.domain.helper.ActivityRetriever

interface PermissionAccessible {
    suspend fun accessWriteFile(): Boolean

    suspend fun accessReadFile(): Boolean

    suspend fun accessCamera(): Boolean

    companion object {
        val shared: PermissionAccessible = PermissionAccessibleImpl(ActivityRetriever.shared)
    }
}

class PermissionAccessibleImpl(
    activityRetriever: ActivityRetriever
) : BasePermissionAccessible(activityRetriever), PermissionAccessible {
    override suspend fun accessWriteFile(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) return true
        return checkOrRequestPermission(
            1,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override suspend fun accessReadFile(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) return true
        return checkOrRequestPermission(
            2,
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    override suspend fun accessCamera(): Boolean {
        return checkOrRequestPermission(
            3,
            android.Manifest.permission.CAMERA
        )
    }
}