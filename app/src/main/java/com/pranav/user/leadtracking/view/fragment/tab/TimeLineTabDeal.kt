package com.pranav.user.leadtracking.view.fragment.tab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.TimelineRequest
import com.pranav.user.leadtracking.controller.responce.TimelineResponce
import com.pranav.user.leadtracking.view.adapter.RvTimelineAdapter

class TimeLineTabDeal : Fragment() {

    lateinit var rvTimelineAdapter: RvTimelineAdapter
    lateinit var rvTimeline: RecyclerView
    var ID: String = ""

    override fun onResume() {
        super.onResume()
        TimelineRequest(activity!!, ResponceProcessorTimeline()).timeline(ID)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_time_line, container, false)

        rvTimeline = mView.findViewById(R.id.rvTimeline)

        ID = arguments!!.getString("ID")


        rvTimelineAdapter = RvTimelineAdapter(activity!!)
        rvTimeline.adapter = rvTimelineAdapter

        return mView
    }

    inner class ResponceProcessorTimeline : ProcessResponcceInterphase<Array<TimelineResponce>> {
        override fun processResponce(responce: Array<TimelineResponce>) {
            rvTimelineAdapter.set(responce)
            rvTimeline.recycledViewPool.setMaxRecycledViews(0, 0)
            rvTimeline.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        }

        override fun processResponceError(responce: Any?) {
        }

    }
}