package com.example.qltvkotlin.domain.helper

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.example.qltvkotlin.presentation.extension.cast

class ActivityRetriever(application: Application) : Application.ActivityLifecycleCallbacks {
    private val listActivity = arrayListOf<Activity>()
    operator fun invoke(): FragmentActivity {
      return  listActivity.lastOrNull()?.takeIf { !it.isFinishing }.cast<FragmentActivity>() ?: error("Not Sp")
    }
    companion object {
        lateinit var shared: ActivityRetriever
    }
    init {
        application.registerActivityLifecycleCallbacks(this)
    }


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        listActivity.add(activity)
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        listActivity.remove(activity)
    }
}