package com.pranav.user.leadtracking.view.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DeleteNotesRequest
import com.pranav.user.leadtracking.controller.responce.DeleteNotesResponce
import com.pranav.user.leadtracking.controller.responce.NotesResponce
import com.pranav.user.leadtracking.view.activity.NotesDetailsActivity
import com.pranav.user.leadtracking.view.fragment.tab.NotesTab
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*


class RvNotesAdapter(var context: Context) : RecyclerView.Adapter<RvNotesAdapter.ViewHolder>() {
    var items = ArrayList<NotesResponce>()
    private lateinit var mcallBack: MCallback
    var NAME = ""

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {

        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_notes, p0, false)
        val viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.texVwTitle.text = items[p1].title
        p0.txtVwData.text = items[p1].body
        p0.txtVwCreated.text = "Created:" + items[p1].createdAt
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun set(responce: Array<NotesResponce>, name: String) {
        val list = ArrayList(Arrays.asList(*responce))
        items = list
        NAME = name

    }

    fun setCallBack(notesTab: NotesTab) {
        mcallBack = notesTab

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwData: TextView = itemView.findViewById(R.id.txtVwData)
        var texVwTitle: TextView = itemView.findViewById(R.id.texVwTitle)
        var txtVwCreated: TextView = itemView.findViewById(R.id.txtVwCreated)
        var imgVwDelete: ImageView = itemView.findViewById(R.id.imgVwDelete)
        var rlNotes: RelativeLayout = itemView.findViewById(R.id.rlNotes)
        var cvNotes: CardView = itemView.findViewById(R.id.cvNotes)

        init {
            imgVwDelete.setOnClickListener {
                cvNotes.visibility = View.GONE
                imgVwDelete.isEnabled = false
                imgVwDelete.isClickable = false
                DeleteNotesRequest(context, ResponceProcessorDeleteNote()).DeleteNotes(items[adapterPosition].id.toString())
            }
            rlNotes.setOnClickListener {
                //                Toast.makeText(context, items[adapterPosition].id.toString(), Toast.LENGTH_SHORT).show()
                val next = Intent(context, NotesDetailsActivity::class.java)
                next.putExtra("ID", items[adapterPosition].id.toString())
                next.putExtra("TITLE", items[adapterPosition].title.toString())
                next.putExtra("BODY", items[adapterPosition].body.toString())
                next.putExtra("NAME", NAME)
                context.startActivity(next)

            }

        }

    }

    inner class ResponceProcessorDeleteNote : ProcessResponcceInterphase<DeleteNotesResponce> {
        override fun processResponce(responce: DeleteNotesResponce) {
            mcallBack.noteCallback()
            Toast.makeText(context, responce.message.toString(), Toast.LENGTH_SHORT).show()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    interface MCallback {
        fun noteCallback()
    }

}