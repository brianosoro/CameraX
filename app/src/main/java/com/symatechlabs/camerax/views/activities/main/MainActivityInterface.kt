package com.symatechlabs.camerax.views.activities.main


import com.symatechlabs.camerax.common.BaseMvcInterface


interface MainActivityInterface: BaseMvcInterface {


    fun setListerners();
    fun setPermissions();
    fun onResume();
    fun onDestroy();

}