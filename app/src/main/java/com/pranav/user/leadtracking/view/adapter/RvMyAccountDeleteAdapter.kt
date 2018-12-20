package com.pranav.user.leadtracking.view.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.UpdateAccountRequest
import com.pranav.user.leadtracking.controller.responce.DeleteNotesResponce
import com.pranav.user.leadtracking.controller.responce.GroupResponce
import com.pranav.user.leadtracking.view.fragment.AccountAddEditFragment


class RvMyAccountDeleteAdapter(var context: Context) : RecyclerView.Adapter<RvMyAccountDeleteAdapter.ViewHolder>() {

    lateinit var list: Array<GroupResponce>
    var accountid = "["
    var accountname = ""
    private lateinit var mcallBack: McallBack
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_my_account_delete, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwName.text = list[p1].accountName
        accountid += list[p1].id.toString() + ","
        accountname += list[p1].accountName.toString() + ","
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set(responce: Array<GroupResponce>) {
        accountid = "["
        list = responce

    }

    fun setCallback(accountAddEditFragment: AccountAddEditFragment) {
        mcallBack = accountAddEditFragment
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var progressDialog = Dialog(context)
        var btnNeg: Button
        var btnPos: Button
        var txtVwContent: TextView
        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var llMA_delete: LinearLayout = itemView.findViewById(R.id.llMA_delete)

        init {
            progressDialog.setContentView(R.layout.dialog_warning)
            progressDialog.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog.setCancelable(false)
            btnNeg = progressDialog.findViewById(R.id.btnNeg)
            btnPos = progressDialog.findViewById(R.id.btnPos)
            txtVwContent = progressDialog.findViewById(R.id.txtVwContent)
            txtVwContent.text = "do you want to delete this account?"
            txtVwName.setOnClickListener {
                progressDialog.show()
            }
            btnNeg.setOnClickListener {
                progressDialog.dismiss()
            }
            btnPos.setOnClickListener {
                mcallBack.setCallbackProgress()
                progressDialog.dismiss()
//                accountid = accountid.substring(0, accountid.length - 1) + "]"
                accountname = accountname.replace(list[adapterPosition].accountName!!, "")
                accountname = accountname.replace(",,", ",")

                accountid = accountid.replace("${list[adapterPosition].id},", "")
                accountid = accountid.replace(",${list[adapterPosition].id}", "")
                if (accountid[accountid.length - 1].toString() == ",") {
                    accountid = accountid.substring(0, accountid.length - 1) + "]"
                } else
                    accountid = "$accountid]"

                UpdateAccountRequest(context, ResponceProcessorDeleteAccount()).UpdateAccount(accountid)
            }
        }

        inner class ResponceProcessorDeleteAccount : ProcessResponcceInterphase<DeleteNotesResponce> {
            override fun processResponce(responce: DeleteNotesResponce) {
                accountid = accountid.replace("[", "")
                accountid = accountid.replace("]", "")
                Log.e("Resultsss1", accountname + " " + accountid)
                mcallBack.setCallback(accountid, accountname)
            }

            override fun processResponceError(responce: Any?) {
            }

        }

    }

    interface McallBack {
        fun setCallback(accountid: String, accountname: String)
        fun setCallbackProgress()
    }

}