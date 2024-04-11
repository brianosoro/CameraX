package com.symatechlabs.camerax.utils

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import com.symatechlabs.camerax.common.Constants.PHOTO_CAPTURED
import com.symatechlabs.camerax.databinding.MainActivityBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CameraUtils(appCompatActivity: AppCompatActivity, lifecycleOwner: LifecycleOwner, previewView: PreviewView) :
    CameraUtilsInterface {

    var appCompatActivity: AppCompatActivity;
    var imageCapture: ImageCapture;
    var cameraProvider: ProcessCameraProvider? = null;
    var LOG_TAG: String = "CameraUtils";
    var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
    var lifecycleOwner: LifecycleOwner;
    var previewView: PreviewView
    var utilities: Utilities;
    var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>;
    private var camera: Camera? = null;


    init {
        this.appCompatActivity = appCompatActivity;
        this.lifecycleOwner = lifecycleOwner;
        this.previewView = previewView;
        this.cameraProviderFuture = ProcessCameraProvider.getInstance(appCompatActivity);
        this.imageCapture = ImageCapture.Builder()
            .build()
        this.utilities = Utilities(appCompatActivity);
    }

    override fun permissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            appCompatActivity, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun startCamera() {

            cameraProviderFuture.addListener({

                /* bind the camera lifecyle to the lifecycle owner */
                cameraProvider = cameraProviderFuture.get()

                /* set the camera preview on the PreviewView as on the xml */
                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }

                try {
                    /* unbind the camera provider use cases before this point */
                    cameraProvider?.unbindAll()
                    camera = cameraProvider?.bindToLifecycle(
                        lifecycleOwner, cameraSelector, preview, imageCapture
                    )

                } catch (e: Exception) {
                    Log.d(LOG_TAG, "startCamera: " + e.message.toString());
                }

            }, ContextCompat.getMainExecutor(appCompatActivity))

    }

    override fun takePhoto(mainActivityBinding: MainActivityBinding) {

        val photoFile = utilities.createFile(); /* create the image file so that it can be set on the outputOptions below */
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        val utilities = Utilities(appCompatActivity);

            imageCapture.takePicture(
                outputOptions,
                ContextCompat.getMainExecutor(appCompatActivity),
                object : ImageCapture.OnImageSavedCallback {
                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {

                        try {
                            CoroutineScope(Dispatchers.Main).launch {
                                /* set the photo taken to the ImageView(capturedPhoto) after it gets taken. The reason for this CoroutineScope is that
                                * in some devices, you might experience "Canvas: trying to draw too large" error */
                                utilities.getImageFromUri(
                                    photoFile.toUri(),
                                    mainActivityBinding.capturedPhoto
                                );
                            }
                            Toast.makeText(appCompatActivity, PHOTO_CAPTURED, Toast.LENGTH_SHORT)
                                .show();
                        } catch (e: Exception) {
                            Log.d(LOG_TAG, "onImageSaved: " + e.message.toString());
                        }
                    }

                    override fun onError(exception: ImageCaptureException) {
                        Log.d(LOG_TAG, "onError: " + exception.message.toString());
                    }

                }
            )

    }

    override fun releaseCamera() {
        cameraProvider?.unbindAll()
        cameraProvider = null;
    }

    override fun toggleCamera() {
        /* This handles changing of the camera from the front/back and vice versa. */
        if (this.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
            this.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        } else {
            this.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;
        }
        startCamera();
        cameraState();
    }

    override fun cameraState() {
        /* Getting the state of the Camera i.e OPEN, CLOSED... */
        camera?.cameraInfo?.cameraState?.observe(lifecycleOwner) { state ->
            Log.d(LOG_TAG, state.type.toString());
        };
    }



}

