package com.example.qltvkotlin.domain.helper

import android.app.AlertDialog
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.qltvkotlin.R
import com.example.qltvkotlin.databinding.CustomDialogBinding
import com.example.qltvkotlin.domain.enumeration.SelectPhotoType
import com.example.qltvkotlin.presentation.extension.bindingOf
import com.example.qltvkotlin.presentation.helper.DocGiaSelecDialog
import com.example.qltvkotlin.presentation.helper.SachSelecDialog
import com.example.qltvkotlin.presentation.widget.IItemSpinner
import com.google.android.material.snackbar.Snackbar
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DialogProvider(
    private val activityRetriever: ActivityRetriever = ActivityRetriever.shared
) {
    fun selectYesNo(
        text: String,
        onAccept: () -> Unit,
        onCancel: () -> Unit
    ): AlertDialog {
        val diaLog = AlertDialog.Builder(activityRetriever())
        diaLog.setMessage(text)
        diaLog.setPositiveButton("Yes") { d, _ ->
            onAccept.invoke()
            d.dismiss()
        }
        diaLog.setNegativeButton("No") { d, _ ->
            onCancel.invoke()
            d.dismiss()

        }
        return diaLog.show()
    }

    fun notification(
        text: String,
    ): AlertDialog {
        val diaLog = AlertDialog.Builder(activityRetriever())
        diaLog.setTitle("Error")
        diaLog.setMessage(text)
        diaLog.setPositiveButton("Ok") { d, _ ->
            d.dismiss()
        }
        return diaLog.show()
    }

    fun bottomUndo(view: View, it: String, function: () -> Unit) {
        val snackbar = Snackbar.make(view, it, Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo") {
            function.invoke()
        }
        return snackbar.show()
    }

    suspend fun chonSach(): IItemSpinner? {
        val activity = activityRetriever() as? AppCompatActivity ?: return null
        return suspendCoroutine { con ->
            SachSelecDialog(activity).apply {
                dialog?.setOnCancelListener {
                    con.resume(null)
                }
            }.showDialog {
                con.resume(it)
            }
        }
    }

    suspend fun chonDocGia(): IItemSpinner? {
        val activity = activityRetriever() as? AppCompatActivity ?: return null

        return suspendCoroutine { con ->
            DocGiaSelecDialog(activity).apply {
                dialog?.setOnCancelListener {
                    con.resume(null)
                }
            }.showDialog {
                con.resume(it)
            }
        }
    }

    suspend fun chonAnhTuCameraHoacThuVien(): SelectPhotoType?{
        return suspendCoroutine { con ->
            val binding = activityRetriever().bindingOf(CustomDialogBinding::inflate)
            val diaLog = AlertDialog.Builder(activityRetriever())
            diaLog.setView(binding.root)
            binding.dialogTitle.setText(R.string.title_themanh)
            binding.dialogMessage.visibility = View.GONE
            diaLog.setPositiveButton("Camera") { d, _->
                con.resume(SelectPhotoType.Camera)
                d.dismiss()
            }
            diaLog.setNegativeButton("Thư Viện") { d, _ ->
                con.resume(SelectPhotoType.Gallery)
                d.dismiss()
            }
            diaLog.setNeutralButton("Hủy") { d, _ ->
                con.resume(null)
                d.dismiss()
            }
            diaLog.setOnCancelListener {
                con.resume(null)
            }
            diaLog.show()
        }

    }

    companion object {
        val shared = DialogProvider()
    }
}


