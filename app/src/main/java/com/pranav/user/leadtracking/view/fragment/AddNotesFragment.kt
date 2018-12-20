package com.pranav.user.leadtracking.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AddNotesRequest
import com.pranav.user.leadtracking.controller.responce.AddNotesResponce

class AddNotesFragment : Fragment() {

    lateinit var btnSave: Button
    lateinit var edtTxtTitle: EditText
    lateinit var edtTextNote: EditText
    lateinit var imgBtnBack: ImageButton
    lateinit var txtVwTitle: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_add_notes, container, false)

        btnSave = mView.findViewById(R.id.btnSave)
        edtTxtTitle = mView.findViewById(R.id.edtTxtTitle)
        edtTextNote = mView.findViewById(R.id.edtTextNote)
        imgBtnBack = mView.findViewById(R.id.imgBtnBack)
        txtVwTitle = mView.findViewById(R.id.txtVwTitle)

        var ID = arguments!!.getString("ID")
        var NAME = arguments!!.getString("NAME")

//        txtVwTitle.text = NAME + " Add Notes"

        btnSave.setOnClickListener {
            if (edtTxtTitle.text.trim().isEmpty())
                Toast.makeText(activity, "Enter Title", Toast.LENGTH_SHORT).show()
            else if (edtTextNote.text.trim().isEmpty())
                Toast.makeText(activity, "Enter Note", Toast.LENGTH_SHORT).show()
            else {
                AddNotesRequest(activity!!, ResponceProcessorAddNotes()).AddNotes(ID,
                        edtTxtTitle.text.trim().toString(),
                        edtTextNote.text.trim().toString())
            }
        }

        imgBtnBack.setOnClickListener { activity!!.onBackPressed() }
        return mView
    }

    inner class ResponceProcessorAddNotes : ProcessResponcceInterphase<AddNotesResponce> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: AddNotesResponce) {
            Toast.makeText(activity, responce.message.toString(), Toast.LENGTH_SHORT).show()
            activity!!.onBackPressed()
        }

    }

}
