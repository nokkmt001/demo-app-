package com.dev.demoapp.dev.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore.Images
import androidx.core.content.FileProvider
import com.dev.demoapp.model.Barcode
import com.dev.demoapp.model.ParsedBarcode
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

object BarcodeImageSaver {

   private var job = SupervisorJob()
   private val ioContext = job + Dispatchers.IO // background context
   private val ioScope = CoroutineScope(ioContext)

    fun saveImageToCache(context: Context, image: Bitmap, barcode: ParsedBarcode): Uri? {
        // Create folder for image
        val imagesFolder = File(context.cacheDir, "images")
        imagesFolder.mkdirs()

        // Create image file
        val imageFileName = "${barcode.format}_${barcode.schema}_${barcode.date}.png"
        val imageFile = File(imagesFolder, imageFileName)

        // Save image to file
        FileOutputStream(imageFile).apply {
            image.compress(Bitmap.CompressFormat.PNG, 100, this)
            flush()
            close()
        }

        // Return Uri for image file
        return FileProvider.getUriForFile(context, ".aeonbookingappp", imageFile)
    }

    fun savePngImageToPublicDirectory(context: Context, image: Bitmap, barcode: Barcode) {
        ioScope.launch {
            try {
                saveToPublicDirectory(context, barcode, "image/png") { outputStream ->
                    image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                }
            } catch (ex: Exception) {
            }
        }
    }

    fun saveSvgImageToPublicDirectory(context: Context, image: String, barcode: Barcode) {
        ioScope.launch {
            try {
                saveToPublicDirectory(context, barcode, "image/svg+xml") { outputStream ->
                    outputStream.write(image.toByteArray())
                }
            } catch (ex: Exception) {
            }
        }
    }

    private fun saveToPublicDirectory(context: Context, barcode: Barcode, mimeType:String, action: (OutputStream)-> Unit) {
        val contentResolver = context.contentResolver ?: return

        val imageTitle = "${barcode.format}_${barcode.schema}_${barcode.date}"

        val values = ContentValues().apply {
            put(Images.Media.TITLE, imageTitle)
            put(Images.Media.DISPLAY_NAME, imageTitle)
            put(Images.Media.MIME_TYPE, mimeType)
            put(Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000)
        }

        val uri = contentResolver.insert(Images.Media.EXTERNAL_CONTENT_URI, values) ?: return
        contentResolver.openOutputStream(uri)?.apply {
            action(this)
            flush()
            close()
        }
    }

    fun clear(){
        job.cancel()
    }
}
