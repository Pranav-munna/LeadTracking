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
import com.pranav.user.leadtracking.controller.request.CallRequest
import com.pranav.user.leadtracking.controller.request.NetworkRequest
import com.pranav.user.leadtracking.controller.responce.AddDealResponce
import com.pranav.user.leadtracking.controller.responce.NetworkResponce
import com.pranav.user.leadtracking.controller.responce2.Datum
import com.pranav.user.leadtracking.view.activity.NetworkDetailsActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat


class RvNetworkAdapter(var context: Context) : RecyclerView.Adapter<RvNetworkAdapter.ViewHolder>() {

    lateinit var list: ArrayList<Datum>
    var count = 2
    private var accountFilter = ""
    private var designationFilter = ""
    private var flp = ""
    val ALL_PERMISSIONS = 101
    val permissions = arrayOf(Manifest.permission.CALL_PHONE)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_network, p0, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        if (accountFilter.trim() == "" && designationFilter.trim() == "") {

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
            p0.txtVwDesign.text = list[p1].designation!!.name + " @ " + list[p1].groups!![0].accountName
        } else
            if (accountFilter.trim() == "" && designationFilter.trim() == list[p1].designation!!.name) {

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
                p0.txtVwDesign.text = list[p1].designation!!.name + " @ " + list[p1].groups!![0].accountName

            } else
                if (accountFilter.trim() == list[p1].groups!![0].accountName && designationFilter.trim() == "") {
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
                    p0.txtVwDesign.text = list[p1].designation!!.name + " @ " + list[p1].groups!![0].accountName

                } else
                    if (accountFilter.trim() == list[p1].groups!![0].accountName && designationFilter.trim() == list[p1].designation!!.name) {

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
                        p0.txtVwDesign.text = list[p1].designation!!.name + " @ " + list[p1].groups!![0].accountName

                    } else {
                        p0.cvNetwork.visibility = View.GONE
                        val params = p0.cvNetwork.layoutParams
                        params.height = 0
                        params.width = 0
                        p0.cvNetwork.layoutParams = params
                    }

        if (p1 == list.size - 2)
            if (flp.trim().isEmpty())
                NetworkRequest(context, ResponceProcessorNetwork1()).networkList(count++)
            else
                NetworkRequest(context, ResponceProcessorNetwork1()).networkList(flp, count++)
    }

    inner class ResponceProcessorNetwork1 : ProcessResponcceInterphase<NetworkResponce> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: NetworkResponce) {
            val list_ = ArrayList<Datum>(responce.data!!)
            list.addAll(list_)
            notifyItemRangeInserted(list.size - list_.size, list_.size)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set(data: List<Datum>, accountF: String, designationF: String, flip: String) {
        list = data as ArrayList<Datum>
        accountFilter = accountF
        designationFilter = designationF
        flp = flip
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var txtVwName: TextView
        var cirImgVwProfile: CircleImageView = itemView.findViewById(R.id.cirImgVwProfile)
        var llDetails: LinearLayout = itemView.findViewById(R.id.LLdetails)
        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var txtVwDesign: TextView = itemView.findViewById(R.id.txtVwdesig)
        var txtVwLastCon: TextView = itemView.findViewById(R.id.txtVwLastCon)
        var txtVwProfileName: TextView = itemView.findViewById(R.id.txtVwProfileName)
        var btnCall: Button = itemView.findViewById(R.id.btnCall)
        var cvNetwork: CardView = itemView.findViewById(R.id.cvNetwork)


        init {

            cvNetwork.setOnClickListener {
                val next = Intent(context, NetworkDetailsActivity::class.java)
                next.putExtra("NAME", list[adapterPosition].name.toString())
                next.putExtra("ACCOUNT_NAME", list[adapterPosition].groups!![0].accountName.toString())
                next.putExtra("COUNTRY_CODE", list[adapterPosition].countryCode.toString())
                next.putExtra("PHONE", list[adapterPosition].number.toString())
                next.putExtra("DESIGNATION", list[adapterPosition].designation!!.name.toString())
                next.putExtra("DESIGNATION_ID", list[adapterPosition].designation!!.id.toString())
                next.putExtra("LAST_CONNECTED_AT", list[adapterPosition].lastContactedAt.toString())
                next.putExtra("MAIL", list[adapterPosition].email.toString())
                next.putExtra("IMAGE", list[adapterPosition].image.toString())
                next.putExtra("GROUP_ID", list[adapterPosition].groups!![0].id.toString())
                next.putExtra("ID", list[adapterPosition].id.toString())
                context.startActivity(next)

            }
            btnCall.setOnClickListener {
                val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + list[adapterPosition].countryCode + (list[adapterPosition].number)!!.replace(list[adapterPosition].countryCode!!,"")))
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    CallRequest(context, ResponceProcessorCall()).NetworkCall(list[adapterPosition].id.toString())
                    context.startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(context as Activity, permissions, ALL_PERMISSIONS)
                    Toast.makeText(context, "Call Permission denied... ", Toast.LENGTH_SHORT).show()
                }
            }

        }

        inner class ResponceProcessorCall : ProcessResponcceInterphase<AddDealResponce> {
            override fun processResponce(responce: AddDealResponce) {
                txtVwLastCon.text = "Last Contacted : Just Now"
            }

            override fun processResponceError(responce: Any?) {
            }

        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun date(sec: Long): String? {
        val unixSeconds: Long = sec
        val date = java.util.Date(unixSeconds * 1000L)
        val sdf = java.text.SimpleDateFormat("yyyy MMM dd HH:mma")
        return sdf.format(date)
    }

}