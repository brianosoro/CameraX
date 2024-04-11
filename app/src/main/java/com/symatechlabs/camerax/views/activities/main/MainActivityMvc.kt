package com.symatechlabs.camerax.views.activities.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.symatechlabs.camerax.views.activities.about.About
import com.symatechlabs.camerax.databinding.MainActivityBinding
import com.symatechlabs.camerax.utils.CameraUtils
import com.symatechlabs.camerax.utils.Utilities

class MainActivityMvc(inflater: LayoutInflater,
                      parent: ViewGroup?,
                      appCompatActivity: AppCompatActivity,) : MainActivityInterface {

    var rootView: View;
    var mainActivityBinding: MainActivityBinding;
    var appCompatActivity: AppCompatActivity;
    var cameraUtils: CameraUtils;
    var utilities: Utilities;

    init {
        this.mainActivityBinding =  MainActivityBinding.inflate(inflater);
        this.rootView = mainActivityBinding.root;
        this.appCompatActivity = appCompatActivity;
        this.utilities = Utilities(appCompatActivity);
        this.cameraUtils = CameraUtils(appCompatActivity, appCompatActivity, mainActivityBinding.previewView);
    }



    override fun getRootView_(): View {
        return rootView;
    }

    override fun getContext(): Context {
        return rootView.context;
    }

    override fun setListerners() {
        cameraUtils.toggleCamera(); /*Start camera when app launches*/

        mainActivityBinding.toggleCamera.setOnClickListener {
            cameraUtils.toggleCamera();
        }

        mainActivityBinding.capture.setOnClickListener {
            cameraUtils.takePhoto(mainActivityBinding)
        }

        mainActivityBinding.about.setOnClickListener {
            appCompatActivity.startActivity(Intent(appCompatActivity, About::class.java));
        }

    }

    override fun setPermissions() {
        requestMultiplePermissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_MEDIA_IMAGES,
            )
        )

    }

    override fun onResume() {

    }

    override fun onDestroy() {
        cameraUtils.releaseCamera();
    }


    private val requestMultiplePermissionLauncher =
       appCompatActivity.registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->

        }

}