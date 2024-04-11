package com.symatechlabs.camerax.utils

import android.net.Uri
import android.widget.ImageView
import java.io.File

interface UtilitiesInterface {
    fun createFile(): File;
    suspend fun getImageFromUri(uri: Uri, view: ImageView);
    fun sendEmail(recipient: String, subject: String);
    fun openLink(link: String);
    fun callPhone(phoneNumber: String);
}