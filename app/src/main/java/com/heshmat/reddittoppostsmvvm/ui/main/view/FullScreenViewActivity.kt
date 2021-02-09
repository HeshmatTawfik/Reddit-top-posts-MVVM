package com.heshmat.reddittoppostsmvvm.ui.main.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.heshmat.reddittoppostsmvvm.R
import kotlinx.android.synthetic.main.activity_full_screen_view.*
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.*


class FullScreenViewActivity : AppCompatActivity() {
    private val STORAGE_REQUEST_CODE: Int = 101;

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==STORAGE_REQUEST_CODE && grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            progressDownload(imgUrl)

        }
    }
      private var imgUrl:String? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_view)

         imgUrl = intent.getStringExtra("IMG_URL")
        Glide.with(this)
            .asBitmap()
            .load(imgUrl)
            .into(imgDisplay)

        closeIv.setOnClickListener { view: View? -> finish() }
        downloadIv.setOnClickListener { view: View? ->

            if (ContextCompat.checkSelfPermission(this@FullScreenViewActivity, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED)
            progressDownload(imgUrl)
            else
                requestPermission()


        }

    }

    private fun progressDownload(imgUrl: String?) {
        closeIv.isEnabled = false
        downloadProgrssBar.visibility = View.VISIBLE
        val result: Deferred<String?> = CoroutineScope(Dispatchers.IO).async {
            saveImage(
                Glide.with(this@FullScreenViewActivity)
                    .asBitmap()
                    .load(imgUrl).submit().get()
            )
        }
        GlobalScope.launch(Dispatchers.Main) {
            val string = result.await()
            string.apply {
                closeIv.isEnabled = true
                downloadProgrssBar.visibility = View.INVISIBLE
                if (!this.isNullOrEmpty()) {
                    Toast.makeText(
                        this@FullScreenViewActivity,
                        "Saved successfully $this",
                        Toast.LENGTH_LONG
                    ).show()

                } else {
                    Toast.makeText(
                        this@FullScreenViewActivity,
                        "Something went Wrong!",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }
    }

    private fun saveImage(image: Bitmap): String? {
        var savedImagePath: String?
        val sdf = SimpleDateFormat("yyyyMMDD_HHmmss", Locale.ENGLISH)
        val imageName = sdf.format(Date(System.currentTimeMillis()))
        val imageFileName = "IMG_$imageName.jpg"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                .toString() + "/RedditMvvm"
        )
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.absolutePath
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
                // Add the image to the system gallery
                galleryAddPic(savedImagePath)
                return savedImagePath


            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        return null
    }

    private fun galleryAddPic(imagePath: String?) {
        imagePath?.let { path ->
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val f = File(path)
            val contentUri: Uri = Uri.fromFile(f)
            mediaScanIntent.data = contentUri
            sendBroadcast(mediaScanIntent)
        }
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE),
            STORAGE_REQUEST_CODE)
    }

}
