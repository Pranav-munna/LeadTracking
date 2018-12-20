package com.pranav.user.leadtracking.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.NoteUpdateRequest
import com.pranav.user.leadtracking.controller.responce.ErrorMessageResponce
import com.pranav.user.leadtracking.controller.responce.NoteUpdateResponce

class NotesDetailsFragment : Fragment() {

    lateinit var imgBtnBack: ImageButton
    lateinit var txtVwTitle: TextView
    lateinit var btnSave: Button
    lateinit var edtTxtTitle: EditText
    lateinit var edtTextNote: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_add_notes, container, false)
        imgBtnBack = mView.findViewById(R.id.imgBtnBack)
        txtVwTitle = mView.findViewById(R.id.txtVwTitle)
        btnSave = mView.findViewById(R.id.btnSave)
        edtTxtTitle = mView.findViewById(R.id.edtTxtTitle)
        edtTextNote = mView.findViewById(R.id.edtTextNote)


        var ID = arguments!!.getString("ID")
        var TITLE = arguments!!.getString("TITLE")
        var BODY = arguments!!.getString("BODY")
        var NAME = arguments!!.getString("NAME")

//        txtVwTitle.text = NAME + " " + "Add Notes"
        edtTxtTitle.setText(TITLE)
        edtTextNote.setText(BODY)

        btnSave.setOnClickListener {
            NoteUpdateRequest(activity!!, ResponceProcessorNoteUpdate()).updateNotes(edtTextNote.text.trim().toString(),
                    ID, edtTxtTitle.text.trim().toString())
        }
        imgBtnBack.setOnClickListener { activity!!.onBackPressed() }
        return mView
    }

    inner class ResponceProcessorNoteUpdate : ProcessResponcceInterphase<NoteUpdateResponce> {
        override fun processResponce(responce: NoteUpdateResponce) {
            activity!!.finish()
        }

        override fun processResponceError(responce:Any?) {
        }

    }

}
