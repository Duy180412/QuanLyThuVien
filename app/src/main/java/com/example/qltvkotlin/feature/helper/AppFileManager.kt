package com.example.qltvkotlin.feature.helper

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


class AppFileManager(private val context: Application) {
    private val authority: String = "${context.packageName}.provider"

    fun createImageUri(): Uri {
        return FileProvider.getUriForFile(
            context,
            authority,
            getFileFromFolder(getFolderCache(), "${System.currentTimeMillis()}.jpg")
        )
    }

    private fun getFileFromFolder(folder: File, nameFile: String): File {
        return File(folder, nameFile)

    }

    private fun getFolderCache(): File {
        return File(context.cacheDir, "photoTemp").also { it.mkdirs() }
    }

    companion object {
       lateinit var instance: AppFileManager

        fun init(context: Application) {
            instance = AppFileManager(context)
        }
    }
}
