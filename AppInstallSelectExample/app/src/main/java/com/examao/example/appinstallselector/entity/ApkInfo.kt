package com.examao.example.appinstallselector.entity

import android.graphics.drawable.Drawable
import android.net.Uri

data class ApkInfo(
    val uri: Uri,
    val path: String,
    val applicationId: String,
    val icon: Drawable,
    val name: CharSequence
)