package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AllAccountsRequest
import com.pranav.user.leadtracking.controller.responce.AnswersResponce
import com.pranav.user.leadtracking.view.fragment.AccountAddFragment
import java.util.*


class RvAllAccountAdapter(var context: Context) : RecyclerView.Adapter<RvAllAccountAdapter.ViewHolder>() {

    var list = ArrayList<AnswersResponce>()
    var tag = ""
    var count = 2
    lateinit var callback: AccountCallBack
    lateinit var id: List<String>
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_my_account, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwName.text = list[p1].getAdditionalProperties()["account_name"].toString()

        var count = 0
        while (count != id.size) {
            if (list[p1].getAdditionalProperties()["id"].toString() == id[count]) {
                p0.ll.visibility = View.GONE
                val params = p0.ll.layoutParams
                params.height = 0
                params.width = 0
                p0.ll.layoutParams = params
            }
            count++
        }


        if (p1 == list.size - 2) {
            AllAccountsRequest(context, ResponceProcessorAllAccounts()).AllAccounts(tag, count++)
        }
    }

    inner class ResponceProcessorAllAccounts : ProcessResponcceInterphase<Array<AnswersResponce>> {
        override fun processResponce(responce: Array<AnswersResponce>) {
            val data = ArrayList(Arrays.asList(*responce))
            list.addAll(data)
            notifyItemRangeInserted(list.size - data.size, data.size)
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set(responce: Array<AnswersResponce>, searchTag: String, iDs: String) {
        list = ArrayList(Arrays.asList(*responce))
        tag = searchTag
        id = iDs.split(",")
    }

    fun setCallBack(accountAddFragment: AccountAddFragment) {
        callback = accountAddFragment
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var ll: LinearLayout = itemView.findViewById(R.id.ll)

        init {

            txtVwName.setOnClickListener {
                ll.visibility = View.GONE
                val params = ll.layoutParams
                params.height = 0
                params.width = 0
                ll.layoutParams = params
                callback.setCallbackAddAccount(list[adapterPosition].getAdditionalProperties()["id"].toString(), list[adapterPosition].getAdditionalProperties()["account_name"].toString())
            }


        }

    }

    interface AccountCallBack {
        fun setCallbackAddAccount(accountid: String, accountname: String)
    }

}