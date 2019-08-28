package com.examao.example.appinstallselector.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.examao.example.appinstallselector.entity.ApkInfo

class ApkInstaller(
    private val context: Context,
    private val apkInfo: ApkInfo
) {
    private val installReceiver by lazy {
        object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent?) {
                when (intent?.action) {
                    Intent.ACTION_PACKAGE_ADDED -> {
                        val id = intent.data?.schemeSpecificPart
                        if (id == apkInfo.applicationId) {
                            onCompleted?.invoke(id)
                        }
                    }
                }
                reset()
            }
        }
    }

    private var onCompleted: ((String) -> Unit)? = null

    fun onCompleted(onCompleted: (String) -> Unit): ApkInstaller {
        this.onCompleted = onCompleted
        return this
    }

    fun install() {
        context.registerReceiver(installReceiver, IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addDataScheme("package")
        })

        context.startActivity(Intent(Intent.ACTION_VIEW).apply {
            addFlags(
                Intent.FLAG_GRANT_READ_URI_PERMISSION
                        or Intent.FLAG_ACTIVITY_NEW_TASK
            )
            setDataAndType(
                apkInfo.uri,
                "application/vnd.android.package-archive"
            )
        })
    }

    private fun reset() {
        context.unregisterReceiver(installReceiver)
    }
}