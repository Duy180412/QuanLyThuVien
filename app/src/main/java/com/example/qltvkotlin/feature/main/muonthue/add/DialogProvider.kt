package com.example.qltvkotlin.feature.main.muonthue.add

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.qltvkotlin.feature.helper.spinner.IItemSpinner
import com.example.qltvkotlin.feature.main.help.dialogcustom.DocGiaSelecDialog
import com.example.qltvkotlin.feature.main.help.dialogcustom.SachSelecDialog
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class DialogProvider(private val application: Application) :
    Application.ActivityLifecycleCallbacks {
    private val mActivities = arrayListOf<Activity>()
    private val currentActivity get() = mActivities.lastOrNull()?.takeIf { !it.isFinishing }

    init {
        application.registerActivityLifecycleCallbacks(this)
    }

    suspend fun chonSach(): IItemSpinner? {
        val activity = currentActivity as? AppCompatActivity ?: return null
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
        val activity = currentActivity as? AppCompatActivity ?: return null

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

    companion object {
        lateinit var shared: DialogProvider
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        mActivities.add(activity)
    }

    override fun onActivityDestroyed(activity: Activity) {
        mActivities.remove(activity)
    }

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

}


