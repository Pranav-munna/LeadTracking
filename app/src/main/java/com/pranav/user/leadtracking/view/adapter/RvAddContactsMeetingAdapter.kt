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
import com.pranav.user.leadtracking.view.fragment.ContactsMeetingFragment
import de.hdodenhof.circleimageview.CircleImageView


class RvAddContactsMeetingAdapter(var context: Context) : RecyclerView.Adapter<RvAddContactsMeetingAdapter.ViewHolder>() {

    lateinit var list: ArrayList<Datum>
    var count = 2
    private lateinit var meetingCallback: MeetingCallBack
    var flag = "0"
    var data = ""
    var ID = ""
    val permissions = arrayOf(Manifest.permission.CALL_PHONE)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_network, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        if (ID == list[p1].id.toString()) {
            p0.cvNetwork.visibility = View.GONE
            val params = p0.cvNetwork.layoutParams
            params.height = 0
            params.width = 0
            p0.cvNetwork.layoutParams = params
        } else {
            if (list[p1].image != null) {
                Glide.with(context)
                        .asBitmap()
                        .load(list[p1].image)
                        .into(p0.cirImgVwProfile)
            } else
                p0.txtVwProfileName.text = list[p1].name
            p0.txtVwName.text = list[p1].name
            if (list[p1].lastContactedAt != null) {
                p0.txtVwLastCon.text = "Last Contacted : " + date(list[p1].lastContactedAt.toString().toLong())
            }
            p0.txtVwdesig.text = list[p1].email
            if (p1 == list.size - 2)
                if (flag == "0")
                    DealDetailContactRequest(context, ResponceProcessorContacts1()).contact(count++)
                else
                    DealDetailContactRequest(context, ResponceProcessorContacts1()).contactSearch(data, count++)

        }

    }

    inner class ResponceProcessorContacts1 : ProcessResponcceInterphase<DealDetailContactResponce> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: DealDetailContactResponce) {
            var list_ = ArrayList<Datum>(responce.data)
            list.addAll(list_)
            notifyItemRangeChanged(list.size - list_.size, list_.size)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setCallBack(contactsFragment: ContactsMeetingFragment) {
        meetingCallback = contactsFragment
    }

    fun set(data: List<Datum>, s: String, a: String, contacT_ID: String) {
        list = data as ArrayList<Datum>
        flag = s
        this.data = a
        ID = contacT_ID
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var txtVwName: TextView
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
                var name = context!!.getSharedPreferences("Contact_Meeting", Context.MODE_PRIVATE).getString("ID_NAME", "")
                var contacts_ids = name.split(",")
                var cnt = 0
                var flg = 1
                while (cnt < contacts_ids.size - 1) {
//                    str = str + contacts_ids[cnt].split("##")[0] + ","
                    if (list[adapterPosition].id.toString().trim() == contacts_ids[cnt].split("##")[0].trim())
                        flg = 0
                    cnt++
                }
                if (flg == 1)
                    meetingCallback.setCallback(list[adapterPosition].name.toString(), list[adapterPosition].id.toString())
                else
                    Toast.makeText(context, "Contact already added to meeting", Toast.LENGTH_SHORT).show()
            }

        }

    }

    interface MeetingCallBack {
        fun setCallback(name: String, id: String)
    }
    @SuppressLint("SimpleDateFormat")
    private fun date(sec: Long): String? {
        val unixSeconds: Long = sec
        val date = java.util.Date(unixSeconds * 1000L)
        val sdf = java.text.SimpleDateFormat("yyyy MMM dd HH:mma")
        return sdf.format(date)
    }
}