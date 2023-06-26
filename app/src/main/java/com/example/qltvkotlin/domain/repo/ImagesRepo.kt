package com.example.qltvkotlin.domain.repo

import android.net.Uri
import com.example.qltvkotlin.domain.model.IImage
import com.example.qltvkotlin.domain.model.IsImageUri
import com.example.qltvkotlin.domain.model.IsImageUrl
import com.example.qltvkotlin.feature.helper.Role
import com.example.qltvkotlin.feature.presentation.app.AppFileManager

class ImagesRepo {
    private val fileManager = AppFileManager.instance


    fun saveImage(keySave: String, images: IImage,role: Role): String {
        return when (images) {
            is IsImageUrl -> images.urlImage
            is IsImageUri -> saveImageUri(keySave, images.uriImage,role)
            else -> createUrlDefault(keySave,role)
        }
    }
    private fun saveImageUri(keySave: String, uriImage: Uri?,role: Role): String {
        return fileManager.save(keySave, uriImage,role)
    }

    private fun createUrlDefault(keySave: String,role: Role): String {
        return saveImageUri(keySave, null, role)
    }


    companion object {
        val imagesRepo = ImagesRepo()
    }
}