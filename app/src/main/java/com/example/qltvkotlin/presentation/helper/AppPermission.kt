package com.example.qltvkotlin.presentation.helper

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.View
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.domain.helper.DialogProvider

interface AppPermissionOwer {
    val appPermission: AppPermission
        get() {
            return when (this) {
                is Activity -> AppPermission(this)
                is Fragment -> AppPermission(requireContext())
                else -> error("Lỗi")
            }
        }
}

class AppPermission(private val context: Context) {
    private val call = context as ActivityResultCaller
    private val dialog = DialogProvider.shared
    companion object {
        val PERMISSION_CAMERA = arrayOf(android.Manifest.permission.CAMERA)
    }

    fun checkPermissonCamera(onClick: View.OnClickListener): View.OnClickListener {
        val requests = PermissionRequests {
            if (it) onClick.onClick(null)
            else dialog.notification( "Không có quyền")
        }
        return View.OnClickListener { requests.checkPermission() }
    }


    inner class PermissionRequests(
        private val function: (Boolean) -> Unit
    ) : View.OnClickListener {
        private val mLauncher =
            call.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                val mValue = it.all { permission ->
                    permission.value
                }
                function.invoke(mValue)
            }


        override fun onClick(v: View?) {
            mLauncher.launch(PERMISSION_CAMERA)
        }

        fun checkPermission() {
            val boolean = PERMISSION_CAMERA.all {
                ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
            }
            if (boolean) function.invoke(true)
            else onClick(null)

        }
    }
}

