package com.dev.demoapp.view.ui.main

import android.app.DownloadManager
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.dev.demoapp.R
import com.dev.demoapp.database.prefs.Preference
import com.dev.demoapp.databinding.ActivityMainBinding
import com.dev.demoapp.dev.event.DownloadEvent
import com.dev.demoapp.dev.utils.ContainsUtils
import com.dev.demoapp.dev.utils.LanguageUtil
import com.dev.demoapp.dev.xbase.BaseMainActivity
import com.dev.demoapp.dev.xbase.BaseMvvmFragment
import com.dev.demoapp.view.ui.splash.SplashFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

class MainActivity : BaseMainActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val splashFragment = SplashFragment()

    private var downloadID = 0L

    override fun initFragment(): Array<BaseMvvmFragment<*>?> {
        val rootFragments: Array<BaseMvvmFragment<*>?> = arrayOfNulls(1)
        rootFragments[0] = splashFragment
        return rootFragments
    }

    override fun useEventBus(): Boolean = true

    override var containerLayoutId: Int = R.id.containerLayout

    override fun setFragmentManager(): FragmentManager = supportFragmentManager

    override fun startFlow() {
        super.startFlow()
        requestedOrientation = if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            ActivityInfo.SCREEN_ORIENTATION_LOCKED
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        ContainsUtils.DEFAULT_LANGUAGE = LanguageUtil.getLanguageDefault().code

    }

    companion object {
        fun getHeaders(): HashMap<String, Any> {
            val headers = HashMap<String, Any>()
            headers[ContainsUtils.HEADER_LANG] =
                ContainsUtils.DEFAULT_LANGUAGE
            headers[ContainsUtils.HEADER_AUTHORIZATION] = "Bearer " + Preference.getToken()
            return headers
        }
    }

    private lateinit var bar: ProgressDialog

    fun updateApp(url: String) {
        DownLoadFile(url, "AEON_BOOKING.apk")
    }

    private var mDownloadManager: DownloadManager? = null
    fun DownLoadFile(url: String?, nameFile: String) {
        try {
            bar = ProgressDialog(this).apply {
                setCancelable(false)
                setMessage("Downloading...")
                isIndeterminate = true
                setCanceledOnTouchOutside(false)
                show()
            }
            val imageStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                "BOOKING"
            )
            if (!imageStorageDir.exists()) {
                imageStorageDir.mkdirs()
            }
            val file = nameFile
            mDownloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val downloadUri = Uri.parse(url)
            val request = DownloadManager.Request(downloadUri)
            val folder = "/Download"
            try {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setDescription("Downloading...")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, file)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            } catch (e: Exception) {
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setDescription("Downloading...")
                    .setDestinationInExternalPublicDir(folder, file)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED) // v28<=
            }
            var msg = ""
            bar.progress = 100
            msg = "Downloading..."


            bar.apply {
                isIndeterminate = false
                max = 100
                setMessage(msg)
            }
            downloadID = mDownloadManager!!.enqueue(request)
//            Toast.makeText(this, "DownLoad Success", Toast.LENGTH_LONG).show()
        } catch (ex: IllegalStateException) {
            Toast.makeText(applicationContext, "Storage Error", Toast.LENGTH_LONG).show()
            ex.printStackTrace()
        } catch (ex: Exception) {
            // just in case, it should never be called anyway
            Toast.makeText(applicationContext, "Unable to save image", Toast.LENGTH_LONG).show()
            ex.printStackTrace()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDownloadEvent(event: DownloadEvent) {
        if (event.downloadID != -1L) {
            if (bar != null) {
                bar.dismiss()

            }
            Toast.makeText(applicationContext, "start update", Toast.LENGTH_LONG).show()
            val fileIntent = Intent(Intent.ACTION_VIEW)

            // Grabs the Uri for the file that was downloaded.

            // Grabs the Uri for the file that was downloaded.
            val mostRecentDownload: Uri =
                mDownloadManager?.getUriForDownloadedFile(event.downloadID)!!
            // DownloadManager stores the Mime Type. Makes it really easy for us.
            // DownloadManager stores the Mime Type. Makes it really easy for us.
            val mimeType: String =
                mDownloadManager?.getMimeTypeForDownloadedFile(event.downloadID).toString()
            fileIntent.setDataAndType(mostRecentDownload, mimeType)
            fileIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                startActivity(fileIntent)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(
                    this, "No handler for this type of file.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
