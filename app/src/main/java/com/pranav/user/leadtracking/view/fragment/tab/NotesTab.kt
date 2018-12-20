package com.pranav.user.leadtracking.view.fragment.tab

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.NotesRequest
import com.pranav.user.leadtracking.controller.responce.ErrorMessageResponce
import com.pranav.user.leadtracking.controller.responce.NotesResponce
import com.pranav.user.leadtracking.view.activity.AddNotesActivity
import com.pranav.user.leadtracking.view.adapter.RvNotesAdapter

class NotesTab : Fragment(), RvNotesAdapter.MCallback {

    lateinit var rvNotes: RecyclerView
    lateinit var rvNotesAdapter: RvNotesAdapter
    lateinit var imgVwAdd: ImageView
    var ID = ""
    var NAME = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.tab_notes, container, false)
        rvNotes = mView.findViewById(R.id.rvNotes)
        imgVwAdd = mView.findViewById(R.id.imgVwAdd)


        ID = arguments!!.getString("ID")
        NAME = arguments!!.getString("NAME")

        rvNotesAdapter = RvNotesAdapter(activity!!)
        rvNotesAdapter.setCallBack(this)
        rvNotes.adapter = rvNotesAdapter

        NotesRequest(activity!!, ResponceProcessorNotes()).Notes(ID, 1)

        imgVwAdd.setOnClickListener {
            val next = Intent(context, AddNotesActivity::class.java)
            next.putExtra("ID", ID)
            next.putExtra("NAME", NAME)
            startActivity(next)
        }

        return mView
    }

    override fun onResume() {
        super.onResume()
        NotesRequest(activity!!, ResponceProcessorNotes()).Notes(ID, 1)
    }

    override fun noteCallback() {
        NotesRequest(activity!!, ResponceProcessorNotes()).Notes(ID, 1)
    }

    inner class ResponceProcessorNotes : ProcessResponcceInterphase<Array<NotesResponce>> {
        override fun processResponceError(responce:Any?) {
        }

        override fun processResponce(responce: Array<NotesResponce>) {
            rvNotesAdapter.set(responce, NAME)
            rvNotes.recycledViewPool.setMaxRecycledViews(0, 0)
            rvNotes.layoutManager = GridLayoutManager(activity, 2)

        }

    }
}