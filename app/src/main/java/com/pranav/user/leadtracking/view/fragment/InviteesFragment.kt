package com.pranav.user.leadtracking.view.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.view.adapter.RvMailAdapter

class InviteesFragment : Fragment() {
    lateinit var edtTxtEmail: EditText
    lateinit var txtVwTravelTime: TextView
    lateinit var txtVwBack: TextView
    lateinit var txtVwDone: TextView
    lateinit var txtVwCancel: TextView
    lateinit var rvMails: RecyclerView
    lateinit var rvMailAdapter: RvMailAdapter
    lateinit var dialogAddInvitees: Dialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.invitees_fragment, container, false)

        txtVwTravelTime = mView.findViewById(R.id.txtVwTravelTime)
        rvMails = mView.findViewById(R.id.rvMails)
        txtVwBack = mView.findViewById(R.id.txtVwBack)

        dialogAddInvitees = Dialog(activity)
        dialogAddInvitees.setContentView(R.layout.dialog_add_invitees)
        dialogAddInvitees.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogAddInvitees.setCancelable(false)
        txtVwCancel = dialogAddInvitees.findViewById(R.id.txtVwCancel)
        txtVwDone = dialogAddInvitees.findViewById(R.id.txtVwDone)
        edtTxtEmail = dialogAddInvitees.findViewById(R.id.edtTxtEmail)

        rvMailAdapter = RvMailAdapter(activity!!)
        rvMails.adapter = rvMailAdapter
        rvMailAdapter.set()
        rvMails.recycledViewPool.setMaxRecycledViews(0, 0)
        rvMails.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)


        txtVwTravelTime.setOnClickListener {
            val email = activity!!.getSharedPreferences("email", Context.MODE_PRIVATE).getString("EMAIL", "")
            edtTxtEmail.setText("$email,")
            dialogAddInvitees.show()
        }
        txtVwCancel.setOnClickListener { dialogAddInvitees.dismiss() }
        txtVwDone.setOnClickListener {
            try {
                if (edtTxtEmail.text[edtTxtEmail.text.length - 1].toString() == ",") {
                    edtTxtEmail.setText(edtTxtEmail.text.substring(0, edtTxtEmail.text.trim().length - 1))
                }
                if (edtTxtEmail.text[0].toString() == ",") {
                    edtTxtEmail.setText(edtTxtEmail.text.substring(1, edtTxtEmail.text.trim().length))
                }
                var count = 0
                while (count != edtTxtEmail.text.split(",").size) {
                    if (!isValidEmail(edtTxtEmail.text.split(",")[count])) {
                        Toast.makeText(activity, edtTxtEmail.text.split(",")[count] + " not a valid Email", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    count++
                }
                val prefs = activity!!.getSharedPreferences("email", Context.MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("EMAIL", edtTxtEmail.text.trim().toString().replace(" ", ""))
                editor.apply()
                rvMailAdapter.set()
                rvMails.recycledViewPool.setMaxRecycledViews(0, 0)
                rvMails.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

                dialogAddInvitees.dismiss()
            } catch (e: Exception) {
            }
        }

        txtVwBack.setOnClickListener { activity!!.finish() }

        return mView
    }

    fun isValidEmail(e_mail: String): Boolean {
        return e_mail.matches("^[0-9a-zA-Z!#$%&;'*+\\-/\\=\\?\\^_`\\.{|}~]{1,64}@[0-9a-zA-Z]{1,255}\\.[a-zA-Z]{1,10}".toRegex()) && e_mail.length <= 320
    }

}
