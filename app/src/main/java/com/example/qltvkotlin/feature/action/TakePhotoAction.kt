package com.example.qltvkotlin.feature.action


import android.app.Activity
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultCaller
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.feature.presentation.app.AppFileManager

interface TakePhotoActionOwner {
    val takePhotoAction: TakePhotoAction
        get() {
            return when (this) {
                is Activity -> TakePhotoAction(this as ActivityResultCaller)
                is Fragment -> TakePhotoAction(requireActivity() as ActivityResultCaller)
                else -> error("Lỗi")
            }
        }
}

class TakePhotoAction(private val caller: ActivityResultCaller) {
    private val appFileManager = AppFileManager.instance
    private val uri = appFileManager.createImageUri()

    fun fromCamera(function: (IsImageUri) -> Unit): View.OnClickListener {
        val mLauncher = caller.registerForActivityResult(
            ActivityResultContracts.TakePicture()
        ) {
            if (it == true) {
                function.invoke(object : IsImageUri {
                    override var uriImage = uri
                })
            }
        }
        return View.OnClickListener { mLauncher.launch(uri) }
    }

    fun fromLibrary(function: (IsImageUri) -> Unit): View.OnClickListener {
        val mLaucher = caller.registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it != null) {
                function.invoke(object : IsImageUri {
                    override var uriImage: Uri = it
                })
            }

        }
        return View.OnClickListener { mLaucher.launch("image/*") }
    }


}

