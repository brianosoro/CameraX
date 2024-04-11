package com.symatechlabs.camerax.views.activities.about

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.symatechlabs.camerax.common.Constants.EMAIL_ADDRESS
import com.symatechlabs.camerax.common.Constants.PHONE_NUMBER
import com.symatechlabs.camerax.common.Constants.WEBSITE_LINK
import com.symatechlabs.camerax.databinding.AboutAppBinding
import com.symatechlabs.camerax.utils.Utilities


class AboutMvc(inflater: LayoutInflater, parent: ViewGroup?, application: AppCompatActivity)  :
    AboutInterface {

    var rootView: View;
    var aboutAppBinding: AboutAppBinding;
    var application_: AppCompatActivity;
    var utilities: Utilities;

    init {
        aboutAppBinding =  AboutAppBinding.inflate(inflater);
        rootView = aboutAppBinding.root;
        application_ = application;
        utilities = Utilities(application);
    }

    override fun setListerners() {

        aboutAppBinding.phone.setOnClickListener {
            utilities.callPhone(PHONE_NUMBER);
        }

        aboutAppBinding.website.setOnClickListener {
            utilities.openLink(WEBSITE_LINK);
        }

        aboutAppBinding.email.setOnClickListener {
            utilities.sendEmail(EMAIL_ADDRESS, "Contact Symatech Labs Ltd");
        }

    }


    override fun getRootView_(): View {
            return rootView;
    }

    override fun getContext(): Context {

        return rootView.context;
    }



}