package com.pranav.user.leadtracking.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pranav.user.leadtracking.R

class AddNewPostfragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
      val mView=inflater.inflate(R.layout.fragment_add_new_post,container,false)
        return mView
    }
}
