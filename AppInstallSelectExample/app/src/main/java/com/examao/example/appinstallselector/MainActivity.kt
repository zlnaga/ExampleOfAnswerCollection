package com.examao.example.appinstallselector

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.examao.example.appinstallselector.entity.ApkInfo
import com.examao.example.appinstallselector.utils.ApkInstaller
import com.examao.example.appinstallselector.utils.ApkLoader
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    class CardBannerAdapter(
        fm: androidx.fragment.app.FragmentManager
    ) : FragmentStatePagerAdapter(
        fm, androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
        override fun getCount(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getItem(position: Int): Fragment {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    private companion object {
        const val REQ_READ_PERMISSION = 0xAEAD
        const val REQ_CHOOSE_APK = 0xF11E
    }

    private val apkLoader by lazy {
        ApkLoader(this)
    }

    private var selectedApk: ApkInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        browseButton.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQ_READ_PERMISSION
                )
            } else {
                chooseApk()
            }
        }

        installButton.setOnClickListener {
            selectedApk?.run {
                ApkInstaller(this@MainActivity, this)
                    .onCompleted { appPackageName ->
                        startActivity(packageManager.getLaunchIntentForPackage(appPackageName))
                    }
                    .install()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CHOOSE_APK && resultCode == Activity.RESULT_OK) {
            data?.data?.let {
                selectedApk = try {
                    apkLoader.load(it)
                } catch (error: IllegalArgumentException) {
                    Log.w(this::class.java.name, error)
                    null
                }
                selectedApk?.apply {
                    this.icon.setBounds(
                        0,
                        0,
                        120 /* width */,
                        120 /* height */
                    )
                    browseButton.setCompoundDrawables(
                        null,
                        this.icon,
                        null,
                        null
                    )
                    browseButton.text = this.name
                    installButton.isEnabled = true
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_READ_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            chooseApk()
        }
    }

    private fun chooseApk() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/vnd.android.package-archive"
        }

        startActivityForResult(
            Intent.createChooser(intent, "Select an APK"),
            REQ_CHOOSE_APK
        )
    }
}
