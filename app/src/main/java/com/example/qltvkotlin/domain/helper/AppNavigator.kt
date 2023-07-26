package com.example.qltvkotlin.domain.helper

import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.qltvkotlin.presentation.helper.AppFileManager
import com.example.qltvkotlin.presentation.router.Router
import com.example.qltvkotlin.presentation.router.Routes
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppNavigator(
    private val activityRetriever: ActivityRetriever = ActivityRetriever.shared,
    private val appFileManager: AppFileManager = AppFileManager.shared
) {
    fun openMain() {
        with(activityRetriever()){
            Router.open(this, Routes.Main())
            finish()
        }

    }
    fun openAddSach() {
        with(activityRetriever()){
            Router.open(this, Routes.AddSach())
            finish()
        }
    }

    fun openLogin() {
        with(activityRetriever()){
            Router.open(this, Routes.Login())
            finish()
        }
    }
    fun openInfoSach(it:CharSequence){
        with(activityRetriever()){
            Router.open(this,Routes.InfoSach(it))
        }
    }

    fun closeCurrent() {
        activityRetriever().finish()
    }

    suspend fun selectPhotoByCamera(): Uri? {
        val activity = activityRetriever()
        val uri = appFileManager.createImageUri()
        return suspendCoroutine { con ->
            var launcher: ActivityResultLauncher<Uri?>? = null
            launcher = activity.activityResultRegistry.register("camera",ActivityResultContracts.TakePicture()) {
                launcher?.unregister()
                if (it) con.resume(uri)
            }
            launcher.launch(uri)
        }

    }

    suspend fun selectPhotoByGallery(): Uri? {
        val activity = activityRetriever()

        return suspendCoroutine { con ->
            var launcher:ActivityResultLauncher<String?>?= null
            launcher = activity.activityResultRegistry.register("thuvien",ActivityResultContracts.GetContent()){
                launcher?.unregister()
                con.resume(it)
            }
            launcher.launch("image/*")
        }
    }

    companion object {
        val shared: AppNavigator = AppNavigator()
    }

}