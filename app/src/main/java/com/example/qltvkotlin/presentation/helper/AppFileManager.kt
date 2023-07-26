package com.example.qltvkotlin.presentation.helper

import android.app.Application
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.qltvkotlin.domain.enumeration.Role
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


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

    private fun getForlder(nameFolder: String): File {
        return File(context.getExternalFilesDir("ThuVien"), nameFolder).also { it.mkdirs() }
    }

    private fun getFolderCache(): File {
        return File(context.cacheDir, "photoTemp").also { it.mkdirs() }
    }

    fun save(keySave: String, uri: Uri?, role: Role): String {
        val outPut = when (role) {
            Role.Sach -> File(getForlder(nameFolderSach), "$keySave.jpg")
            Role.DocGia -> File(getForlder(nameFolderDocGia), "$keySave.jpg")
            else -> null
        }
        outPut ?: return ""
        uri ?: return outPut.toString()
        val saveImage = SaveImage(context)
        saveImage.invoke(uri, outPut)
        return outPut.toString()
    }


    companion object {
        lateinit var shared: AppFileManager
        private const val nameFolderSach = "Sach"
        private const val nameFolderDocGia = "DocGia"

        fun init(context: Application) {
            shared = AppFileManager(context)
        }
    }
}

class SaveImage(val context: Application) {
    private var input: InputStream? = null
    private var output: FileOutputStream? = null

    fun invoke(uri: Uri, outpuImage: File) {
        input = context.contentResolver.openInputStream(uri)
        output = FileOutputStream(outpuImage)
        input?.copyTo(output!!)
        input?.close()
        output?.close()
        context.contentResolver.delete(uri, null, null)
    }
}