package com.symatechlabs.camerax.utils



import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


class Utilities(appCompatActivity: AppCompatActivity): UtilitiesInterface {

    var appCompatActivity: AppCompatActivity;
    val LOG_TAG = "Utilities";

    init {
        this.appCompatActivity = appCompatActivity;
    }


    override fun createFile(): File {

        return  File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "${
                SimpleDateFormat(
                    "yyyyMMdd_HHmmss",
                    Locale.US
                ).format(System.currentTimeMillis())
            }.jpg");
    }


    override suspend fun getImageFromUri(uri: Uri, view: ImageView){
         withContext(Dispatchers.Main) {
            var inputStream: InputStream? = null
            try {
                inputStream = appCompatActivity?.contentResolver?.openInputStream(uri)
                view.setImageBitmap(BitmapFactory.decodeStream(inputStream));
            } catch (e: Exception) {
                Log.d(LOG_TAG, e.message.toString());
                e.printStackTrace()
            } finally {
                inputStream?.close()
            }
         }

    }

    override fun sendEmail(recipient: String, subject: String) {
        val emailIntent = Intent(Intent.ACTION_SEND)
        emailIntent.data = Uri.parse("mailto:")
        emailIntent.type = "text/plain"
        emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        try {
            appCompatActivity.startActivity(Intent.createChooser(emailIntent, "Select Mail App"))
        } catch (e: Exception){

        }
    }

    override fun openLink(link: String) {
        val openLinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        try {
            appCompatActivity. startActivity(openLinkIntent)
        } catch (e: Exception){

        }
    }

    override fun callPhone(phoneNumber: String) {
        val phoneCallIntent = Intent(Intent.ACTION_DIAL)
        phoneCallIntent.data = Uri.parse("tel:$phoneNumber")
        if (phoneCallIntent.resolveActivity(appCompatActivity.packageManager) != null) {
            appCompatActivity.startActivity(phoneCallIntent)
        } else {
        }
    }


}