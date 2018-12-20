package com.pranav.user.leadtracking.view.adapter

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DealDetailContactRequest
import com.pranav.user.leadtracking.controller.responce.DealDetailContactResponce
import com.pranav.user.leadtracking.controller.responce2.Datum
import com.pranav.user.leadtracking.view.fragment.ContactsDealFragment
import de.hdodenhof.circleimageview.CircleImageView


class RvAddContactsDealAdapter(var context: Context) : RecyclerView.Adapter<RvAddContactsDealAdapter.ViewHolder>() {

    lateinit var list: ArrayList<Datum>
    var count = 2
    private lateinit var contactsDealCallback: ContactsDealCallBack
    var flag = "0"
    var data = ""
    var testid = ""
    var CONTACT_ID = ""
    val permissions = arrayOf(Manifest.permission.CALL_PHONE)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_network, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (CONTACT_ID.trim() == list[p1].id.toString()) {
            p0.cvNetwork.visibility = View.GONE
            val params = p0.cvNetwork.layoutParams
            params.height = 0
            params.width = 0
            p0.cvNetwork.layoutParams = params
        }

        if (list[p1].image != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(list[p1].image)
                    .into(p0.cirImgVwProfile)
        } else
            p0.txtVwProfileName.text = list[p1].name
        p0.txtVwName.text = list[p1].name
        if (list[p1].lastContactedAt != null) {
            p0.txtVwLastCon.text = "Last Contacted : " + list[p1].lastContactedAt
        }
        p0.txtVwdesig.text = list[p1].email
        if (p1 == list.size - 2)
            if (flag == "0")
                DealDetailContactRequest(context, ResponceProcessorContct()).contact(count++)
            else
                DealDetailContactRequest(context, ResponceProcessorContct()).contactSearch(data, count++)
    }

    inner class ResponceProcessorContct : ProcessResponcceInterphase<DealDetailContactResponce> {
        override fun processResponce(responce: DealDetailContactResponce) {

            var list_ = ArrayList<Datum>(responce.data)
            list.addAll(list_)
            notifyItemRangeChanged(list.size - list_.size, list_.size)
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    /*inner class ResponceProcessorContacts1 : ProcessResponcceInterphase<ContactSearchResponce> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: ContactSearchResponce) {
            var list_ = ArrayList<Contact>(responce.contacts)
            list.addAll(list_)
            notifyItemRangeChanged(list.size - list_.size, list_.size)
        }

    }
*/
    override fun getItemCount(): Int {
        return list.size
    }

    fun setCallBack(contactsDealFragment: ContactsDealFragment) {

        contactsDealCallback = contactsDealFragment
    }

    fun set(data: List<Datum>, s: String, s1: String, ids_name: String, contacT_ID: String) {
        list = data as ArrayList<Datum>
        flag = s
        this.data = s1
        testid = ids_name
        CONTACT_ID = contacT_ID
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cirImgVwProfile: CircleImageView = itemView.findViewById(R.id.cirImgVwProfile)
        var LLdetails: LinearLayout = itemView.findViewById(R.id.LLdetails)
        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var txtVwdesig: TextView = itemView.findViewById(R.id.txtVwdesig)
        var txtVwLastCon: TextView = itemView.findViewById(R.id.txtVwLastCon)
        var txtVwProfileName: TextView = itemView.findViewById(R.id.txtVwProfileName)
        var btnCall: Button = itemView.findViewById(R.id.btnCall)
        var ll: LinearLayout = itemView.findViewById(R.id.ll)
        var cvNetwork: CardView = itemView.findViewById(R.id.cvNetwork)


        init {
            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list[adapterPosition].countryCode + list[adapterPosition].number))
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    context.startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(context as Activity, permissions, 101)
                    Toast.makeText(context, "Call Permission denied... ", Toast.LENGTH_SHORT).show()
                }
            }
            ll.setOnClickListener {

                var id = testid.split(",")
                var count = 0
                var flag = true
                var idString = ""
                while (count != id.size - 1) {
                    if (id[count].split("##")[0].toInt() == list[adapterPosition].id!!.toInt()) {
                        flag = false
                        Toast.makeText(context, "Contact already added", Toast.LENGTH_SHORT).show()
                    }
                    count++
                }

                if (flag)
                    contactsDealCallback.setCallback(list[adapterPosition].name.toString(), list[adapterPosition].id.toString())
            }

        }

    }

    interface ContactsDealCallBack {
        fun setCallback(name: String, id: String)
    }

}