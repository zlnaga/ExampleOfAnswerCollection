package com.examao.example.appinstallselector.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.examao.example.appinstallselector.entity.ApkInfo
import java.io.File

class ApkLoader(
    private val context: Context
) {
    @SuppressLint("Recycle")
    fun load(uri: Uri): ApkInfo {
        val file = context.contentResolver.query(
            uri,
            arrayOf(MediaStore.Files.FileColumns.DATA),
            null,
            null,
            null
        )?.run file@{
            return@file try {
                val index = this.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
                this.moveToFirst()
                File(this.getString(index))
            } catch (error: IllegalArgumentException) {
                Log.w(this::class.java.name, error)
                null
            }.apply {
                this@file.close()
            }
        } ?: throw IllegalArgumentException("Fail to load file!")

        val info = context.packageManager.getPackageArchiveInfo(file.path, 0)?.applicationInfo
            ?: throw IllegalArgumentException("Can't read the application info, please check the file and the permission!")

        info.sourceDir = file.path
        info.publicSourceDir = file.path

        return ApkInfo(
            uri = uri,
            path = file.path,
            applicationId = info.packageName,
            icon = info.loadIcon(context.packageManager),
            name = info.loadLabel(context.packageManager)
        )
    }
}