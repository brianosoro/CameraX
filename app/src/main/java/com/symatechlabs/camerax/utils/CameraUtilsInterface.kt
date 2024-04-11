package com.symatechlabs.camerax.utils


import com.symatechlabs.camerax.databinding.MainActivityBinding


interface CameraUtilsInterface {

    fun permissionGranted(): Boolean;
    fun startCamera();
    fun takePhoto(mainActivityBinding: MainActivityBinding);
    fun releaseCamera();
    fun toggleCamera();
    fun cameraState();
}