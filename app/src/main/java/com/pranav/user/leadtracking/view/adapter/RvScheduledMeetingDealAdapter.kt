package com.pranav.user.leadtracking.view.adapter

import android.annotation.SuppressLint
import android.content.ContentUris
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce.ScheduledMeetingResponce
import com.pranav.user.leadtracking.view.fragment.tab.MeetingTabDeal
import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat


class RvScheduledMeetingDealAdapter(var context: Context) : RecyclerView.Adapter<RvScheduledMeetingDealAdapter.ViewHolder>() {

    lateinit var list: Array<ScheduledMeetingResponce>
    private lateinit var callback: Callback

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_scheduled_meetings, p0, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.txtVwTitle.text = list[p1].subject
        if (list[p1].scheduledOn!!.split(" ")[1].substring(0, 2).toInt() >= 12)
            p0.txtVwTime.text = list[p1].scheduledOn!!.substring(0, list[p1].scheduledOn!!.length - 3) + " PM"
        else
            p0.txtVwTime.text = list[p1].scheduledOn!!.substring(0, list[p1].scheduledOn!!.length - 3) + " AM"
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun set(responce: Array<ScheduledMeetingResponce>) {
        list = responce
    }

    fun setCallback(meetingTabDeal: MeetingTabDeal) {
        callback = meetingTabDeal
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwTitle: TextView = itemView.findViewById(R.id.txtVwTitle)
        var txtVwTime: TextView = itemView.findViewById(R.id.txtVwTime)
        var imgBtnDelete: ImageButton = itemView.findViewById(R.id.imgBtnDelete)
        var llCalendar: LinearLayout = itemView.findViewById(R.id.llCalendar)

        init {
            imgBtnDelete.setOnClickListener {
                callback.callback(list[adapterPosition].id.toString())
            }
            llCalendar.setOnClickListener {
                var timeInMilliseconds: Long? = null

                val givenDateString = "${list[adapterPosition].scheduledOn.toString().split("-")[1]} ${list[adapterPosition].scheduledOn.toString().split(" ")[0].split("-")[2]} 00:00:00 GMT+05:30 ${list[adapterPosition].scheduledOn.toString().split("-")[0]}"
               val sdf = SimpleDateFormat("MM dd HH:mm:ss z yyyy")
                try {
                    val mDate = sdf.parse(givenDateString)
                    timeInMilliseconds = mDate.time
//                    Log.e("Date", list[adapterPosition].scheduledOn.toString())

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                val builder: Uri.Builder = CalendarContract.CONTENT_URI.buildUpon()
                        .appendPath("time")
                ContentUris.appendId(builder, timeInMilliseconds!!)
                val intent = Intent(Intent.ACTION_VIEW)
                        .setData(builder.build())
                context.startActivity(intent)

            }
        }

    }

    interface Callback {
        fun callback(id: String)
    }

}