package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AllAccountsRequest
import com.pranav.user.leadtracking.controller.responce.AllFeedsResponce
import com.pranav.user.leadtracking.controller.responce.AnswersResponce
import com.pranav.user.leadtracking.view.fragment.AccountAddFragment
import java.util.*


class RvAddAccountAdapter(var context: Context) : RecyclerView.Adapter<RvAddAccountAdapter.ViewHolder>() {


    lateinit var callback: AccountCallBack
    lateinit var namE: List<String>
    lateinit var iD: List<String>
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_accounts, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwName.text = namE[p1]

    }

    override fun getItemCount(): Int {
        return namE.size
    }

    fun setCallBack(accountAddFragment: AccountAddFragment) {
        callback = accountAddFragment
    }

    fun set(namEs: String?, iDs: String?) {
        if (namEs!!.isNotEmpty()) {
            namE = namEs!!.split(",")
            iD = iDs!!.split(",")
            } else {
            namE = emptyList()
            iD = emptyList()
        }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var imgVwDelete: ImageView = itemView.findViewById(R.id.imgVwDelete)

        init {

            imgVwDelete.setOnClickListener {

                var count = 0
                var id = ""
                var name = ""
                while (count != iD.size) {
                    if (adapterPosition != count) {
                        if (id.isEmpty()) {
                            id = iD[count]
                            name = namE[count]
                        } else {
                            id += "," + iD[count]
                            name += "," + namE[count]
                        }
                    }
                    count++
                }
                callback.setCallbackAdd(id, name)
            }

        }

    }

    interface AccountCallBack {
        fun setCallbackAdd(accountid: String, accountname: String)
    }

}