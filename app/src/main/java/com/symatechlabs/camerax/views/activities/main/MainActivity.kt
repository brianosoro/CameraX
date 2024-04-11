package com.symatechlabs.camerax.views.activities.main

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

class MainActivity: AppCompatActivity() {

    lateinit var mainActivityMvc: MainActivityMvc;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        mainActivityMvc = MainActivityMvc(LayoutInflater.from(this), null, this);
        setContentView(mainActivityMvc.getRootView_());
        mainActivityMvc.setListerners();
        mainActivityMvc.setPermissions();
    }


    override fun onResume() {
        super.onResume();
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainActivityMvc.onDestroy();
    }
}