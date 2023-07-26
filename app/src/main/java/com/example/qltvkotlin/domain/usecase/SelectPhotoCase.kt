package com.example.qltvkotlin.domain.usecase

import android.net.Uri
import com.example.qltvkotlin.domain.enumeration.SelectPhotoType
import com.example.qltvkotlin.domain.helper.AppNavigator
import com.example.qltvkotlin.domain.helper.DialogProvider
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.IPhotoField
import com.example.qltvkotlin.domain.model.Updatable
import com.example.qltvkotlin.presentation.extension.cast
import com.example.qltvkotlin.presentation.helper.PermissionAccessible

class SelectPhotoCase(
    private val dialogProvider: DialogProvider = DialogProvider.shared,
    private val permission: PermissionAccessible = PermissionAccessible.shared,
    private val appNavigator: AppNavigator = AppNavigator.shared
) {
    suspend operator fun invoke(item: IPhotoField) {
        val newImage = when (dialogProvider.chonAnhTuCameraHoacThuVien()) {
            SelectPhotoType.Camera -> selectByCamera()
            SelectPhotoType.Gallery -> selectByThuVien()
            else -> error("Not Sp")
        }
        item.cast<Updatable>()?.update(newImage)
    }

    private suspend fun selectByThuVien(): IImage? {
        if (!permission.accessReadFile()) return null
        val uri = appNavigator.selectPhotoByGallery() ?: return null
        return createImageUri(uri)
    }

    private fun createImageUri(uri: Uri): IImage {
        return object : IImage, IsImageUri {
            override var uriImage = uri
        }
    }

    private suspend fun selectByCamera(): IImage? {
        if (!permission.accessCamera()) return null
        val uri = appNavigator.selectPhotoByCamera() ?: return null
        return createImageUri(uri)
    }
}