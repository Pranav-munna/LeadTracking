package com.pranav.user.leadtracking.view.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.GroupRequest
import com.pranav.user.leadtracking.controller.responce.GroupResponce
import com.pranav.user.leadtracking.view.activity.AccountAdd
import com.pranav.user.leadtracking.view.adapter.RvMyAccountAdapter
import com.pranav.user.leadtracking.view.adapter.RvMyAccountDeleteAdapter

class AccountAddEditFragment : Fragment(), RvMyAccountDeleteAdapter.McallBack {
    override fun setCallbackProgress() {
        progressDialog.show()
    }

    lateinit var txtVwEdit: TextView
    lateinit var txtVwAdd: TextView
    lateinit var txtVwCalBack: TextView
    lateinit var rvAccount: RecyclerView
    lateinit var rvAccountDelete: RecyclerView
    lateinit var rvMyAccountAdapter: RvMyAccountAdapter
    lateinit var rvMyAccountDeleteAdapter: RvMyAccountDeleteAdapter
    var IDs = ""
    var NAMEs= ""
    lateinit var progressDialog: Dialog

    override fun onResume() {
        super.onResume()
        GroupRequest(activity!!, ResponceProcessorGp()).groups()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_add_edit, container, false)

        txtVwAdd = mView.findViewById(R.id.txtVwAdd)
        txtVwEdit = mView.findViewById(R.id.txtVwEdit)
        rvAccount = mView.findViewById(R.id.rvAccount)
        rvAccountDelete = mView.findViewById(R.id.rvAccountDelete)
        txtVwCalBack = mView.findViewById(R.id.txtVwCalBack)

        progressDialog = Dialog(activity)
        progressDialog.setContentView(R.layout.progress)
        progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog.setCancelable(false)

        rvMyAccountAdapter = RvMyAccountAdapter(activity!!)
        rvAccount.adapter = rvMyAccountAdapter

        rvMyAccountDeleteAdapter = RvMyAccountDeleteAdapter(activity!!)
        rvMyAccountDeleteAdapter.setCallback(this)
        rvAccountDelete.adapter = rvMyAccountDeleteAdapter


        txtVwEdit.setOnClickListener {
            if (txtVwEdit.text == "Edit") {
                txtVwAdd.visibility = View.VISIBLE
                rvAccountDelete.visibility = View.VISIBLE
                rvAccount.visibility = View.GONE
                txtVwEdit.text = "Save"
            } else {
                txtVwAdd.visibility = View.INVISIBLE
                rvAccountDelete.visibility = View.GONE
                rvAccount.visibility = View.VISIBLE
                txtVwEdit.text = "Edit"
            }

        }

        txtVwAdd.setOnClickListener {
            Log.e("Resultsss3", NAMEs+ " " + IDs)
            var next = Intent(activity, AccountAdd::class.java)
            next.putExtra("IDs", IDs)
            next.putExtra("NAMEs", NAMEs)
            startActivity(next)

        }
        txtVwCalBack.setOnClickListener { activity!!.onBackPressed() }

        return mView
    }

    inner class ResponceProcessorGp : ProcessResponcceInterphase<Array<GroupResponce>> {
        override fun processResponce(responce: Array<GroupResponce>) {

            rvMyAccountAdapter.set(responce)
            rvAccount.recycledViewPool.setMaxRecycledViews(0, 0)
            rvAccount.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

            rvMyAccountDeleteAdapter.set(responce)
            rvAccountDelete.recycledViewPool.setMaxRecycledViews(0, 0)
            rvAccountDelete.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

            var count = 0
            IDs = ""
            NAMEs = ""
            while (count != responce.size) {
                if (count == 0) {
                    IDs += responce[count].id
                    NAMEs += responce[count].accountName
                } else {
                    IDs += "," + responce[count].id
                    NAMEs += "," + responce[count].accountName
                }
                count++
            }
            progressDialog.dismiss()
        }

        override fun processResponceError(responce: Any?) {
            progressDialog.dismiss()
        }

    }

    override fun setCallback(accountid: String, accountname: String) {
        progressDialog.show()
        GroupRequest(activity!!, ResponceProcessorGp()).groups()
        IDs = accountid
        NAMEs = accountname
        Log.e("Resultsss2", NAMEs+ " " + IDs)
    }

}
