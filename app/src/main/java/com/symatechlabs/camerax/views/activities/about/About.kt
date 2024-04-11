package com.symatechlabs.camerax.views.activities.about

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity

public class About: AppCompatActivity() {



    lateinit var aboutMvc: AboutMvc;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aboutMvc = AboutMvc(LayoutInflater.from(this), null, this);
        setContentView(aboutMvc.getRootView_())
        aboutMvc.setListerners();
    }





}