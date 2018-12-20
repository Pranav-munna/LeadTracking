package com.pranav.user.leadtracking.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AllAccountsRequest
import com.pranav.user.leadtracking.controller.request.UpdateAccountRequest
import com.pranav.user.leadtracking.controller.responce.AnswersResponce
import com.pranav.user.leadtracking.controller.responce.DeleteNotesResponce
import com.pranav.user.leadtracking.view.adapter.RvAddAccountAdapter
import com.pranav.user.leadtracking.view.adapter.RvAllAccountAdapter

class AccountAddFragment : Fragment(), RvAllAccountAdapter.AccountCallBack, RvAddAccountAdapter.AccountCallBack {

    lateinit var rvAccounts: RecyclerView
    lateinit var rvAllAccount: RecyclerView
    lateinit var edtTxtSearch: EditText
    lateinit var txtVwCalBack: TextView
    lateinit var rvAllAccountAdapter: RvAllAccountAdapter
    lateinit var rvAddAccountAdapter: RvAddAccountAdapter
    var searchTag = ""
    var NAMEs = ""
    var IDs = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mView = inflater.inflate(R.layout.fragment_accoun_add, container, false)
        rvAccounts = mView.findViewById(R.id.rvAccounts)
        rvAllAccount = mView.findViewById(R.id.rvAllAccount)
        edtTxtSearch = mView.findViewById(R.id.edtTxtSearch)
        txtVwCalBack = mView.findViewById(R.id.txtVwCalBack)
        NAMEs = arguments!!.getString("NAMEs")
        IDs = arguments!!.getString("IDs")



        Log.e("Resultsss4", NAMEs)

        rvAllAccountAdapter = RvAllAccountAdapter(activity!!)
        rvAllAccountAdapter.setCallBack(this)
        rvAllAccount.adapter = rvAllAccountAdapter

        rvAddAccountAdapter = RvAddAccountAdapter(activity!!)
        rvAddAccountAdapter.setCallBack(this)
        rvAccounts.adapter = rvAddAccountAdapter
        rvAddAccountAdapter.set(NAMEs, IDs)
        rvAccounts.recycledViewPool.setMaxRecycledViews(0, 0)
//        rvAccounts.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        rvAccounts.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.HORIZONTAL)

        searchTag = ""

        edtTxtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchTag = p0.toString()
                AllAccountsRequest(activity!!, ResponceProcessorAllAccount()).AllAccounts(searchTag, 1)
            }
        })

        AllAccountsRequest(activity!!, ResponceProcessorAllAccount()).AllAccounts(searchTag, 1)

        txtVwCalBack.setOnClickListener { activity!!.onBackPressed() }

        return mView
    }

    inner class ResponceProcessorAllAccount : ProcessResponcceInterphase<Array<AnswersResponce>> {
        override fun processResponceError(responce: Any?) {

        }

        override fun processResponce(responce: Array<AnswersResponce>) {
            rvAllAccountAdapter.set(responce, searchTag, IDs)
            rvAllAccount.recycledViewPool.setMaxRecycledViews(0, 0)
            rvAllAccount.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)

        }

    }

    override fun setCallbackAdd(accountid: String, accountname: String) {
        NAMEs = accountname.trim()
        IDs = accountid.trim()
        rvAddAccountAdapter.set(NAMEs, IDs)
        rvAccounts.recycledViewPool.setMaxRecycledViews(0, 0)
        rvAccounts.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.HORIZONTAL)
        UpdateAccountRequest(activity!!, ResponceProcessorDeleteAcnt()).UpdateAccount("[$IDs]")
    }

    inner class ResponceProcessorDeleteAcnt : ProcessResponcceInterphase<DeleteNotesResponce> {
        override fun processResponce(responce: DeleteNotesResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun setCallbackAddAccount(accountid: String, accountname: String) {
        if (NAMEs.trim().isEmpty()) {
            NAMEs = accountname
            IDs = accountid
        } else {
            NAMEs += ",$accountname"
            IDs += ",$accountid"

        }
        rvAddAccountAdapter.set(NAMEs, IDs)
        rvAccounts.recycledViewPool.setMaxRecycledViews(0, 0)
        rvAccounts.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.HORIZONTAL)
        UpdateAccountRequest(activity!!, ResponceProcessorDeleteAcnt()).UpdateAccount("[$IDs]")
    }


}
