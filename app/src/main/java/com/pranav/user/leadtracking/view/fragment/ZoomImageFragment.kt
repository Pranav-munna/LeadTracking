package com.pranav.user.leadtracking.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView
import com.pranav.user.leadtracking.R

class ZoomImageFragment : Fragment() {
    lateinit var myZoomageView: ZoomageView
    lateinit var txtVwClose: TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_zoom_image, container, false)

        myZoomageView = mView.findViewById(R.id.myZoomageView)
        txtVwClose = mView.findViewById(R.id.txtVwClose)

        val URL = arguments!!.getString("URL")
        Glide.with(activity!!)
                .asBitmap()
                .load(URL)
                .into(myZoomageView)

        txtVwClose.setOnClickListener { activity!!.finish() }

        return mView
    }

}
