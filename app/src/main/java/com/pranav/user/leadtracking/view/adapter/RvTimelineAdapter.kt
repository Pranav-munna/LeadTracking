package com.pranav.user.leadtracking.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.responce.TimelineResponce
import java.text.SimpleDateFormat
import java.util.*


class RvTimelineAdapter(var context: Context) : RecyclerView.Adapter<RvTimelineAdapter.ViewHolder>() {

    var size = 0
    lateinit var list: Array<TimelineResponce>
    var dateAsString = "AM GMT+05:30"
    var symbol = "+"

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_timeline, p0, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if (p1 == 0) p0.vwTop.visibility = View.GONE
        if (p1 + 1 == size) p0.vwBottom.visibility = View.GONE

//        Toast.makeText(context, list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[0].split("-")[1], Toast.LENGTH_SHORT).show()
        val mon = when (list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[0].split("-")[1].trim()) {
            "01" -> "Jan"
            "02" -> "Feb"
            "03" -> "Mar"
            "04" -> "Apr"
            "05" -> "May"
            "06" -> "Jun"
            "07" -> "Jly"
            "08" -> "Aug"
            "09" -> "Spt"
            "10" -> "Oct"
            "11" -> "Nov"
            "12" -> "Dec"
            else -> "   "
        }
//
        p0.txtVwDate.text = list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[0].split("-")[2] + " $mon"
        if (dateAsString[6].toString() == "+")
            p0.txtVwTime.text = hr(p1) + ":" + ((list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[1].split(":")[1].toInt()
                    + dateAsString.split("+")[1].split(":")[1].toInt()) % 60).toString() + dateAsString.split(" ")[0]
        else
            p0.txtVwTime.text = (list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[1].split(":")[0].toInt() - dateAsString.split("+")[1].split(":")[0].toInt()).toString() +
                    ":" + (list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[1].split(":")[1].toInt() - dateAsString.split("+")[1].split(":")[1].toInt()).toString() +
                    dateAsString.split(" ")[0]

        p0.txtVwEvntName.text = list[p1].getAdditionalProperties()["event_name"].toString()

    }


    override fun getItemCount(): Int {
        return size
    }

    fun set(responce: Array<TimelineResponce>) {
        if (responce.isNotEmpty()) {
            list = responce
            size = list.size
        }
        val myDate = Date()
        val calendar = Calendar.getInstance()
        calendar.timeZone = TimeZone.getTimeZone("GMT")
        calendar.time = myDate
        val time = calendar.time
        val outputFmt = SimpleDateFormat("a zz")
//        dateAsString = outputFmt.format(time)
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var vwTop: TextView = itemView.findViewById(R.id.vwTop)
        var vwBottom: TextView = itemView.findViewById(R.id.vwBottom)
        var txtVwDate: TextView = itemView.findViewById(R.id.txtVwDate)
        var txtVwEvntName: TextView = itemView.findViewById(R.id.txtVwEvntName)
        var txtVwTime: TextView = itemView.findViewById(R.id.txtVwTime)

        init {

        }

    }

    fun hr(p1: Int): String {
        var date = ((list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[1].split(":")[0].toInt()
                + dateAsString.split("+")[1].split(":")[0].toInt() + ((list[p1].getAdditionalProperties()["event_date"].toString().split(" ")[1].split(":")[1].toInt()
                + dateAsString.split("+")[1].split(":")[1].toInt()) / 60)) % 12).toString()
        if (dateAsString.split(" ")[0] == "PM" && date == "0") {
            return "12"
        }
        return date
    }

}