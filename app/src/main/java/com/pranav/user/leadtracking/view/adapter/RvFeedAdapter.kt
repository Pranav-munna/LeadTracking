package com.pranav.user.leadtracking.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.*
import com.bumptech.glide.Glide
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.*
import com.pranav.user.leadtracking.controller.responce.*
import com.pranav.user.leadtracking.view.activity.ZoomImageActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class RvFeedAdapter(var context: Context) : RecyclerView.Adapter<RvFeedAdapter.ViewHolder>() {
    var items = ArrayList<AllFeedsResponce>()
    var count = 2
    @SuppressLint("SimpleDateFormat")
    var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_feed, p0, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        CommentsAllRequest(context, ResponceProcessorAllComment(p0.textVwComments, p0.txtVwViewComments)).AllComment(items[p1].id.toString())

        p0.tvName.text = items[p1].user!!.firstName + " " + items[p1].user!!.lastName
        p0.txtVwEmail.text = items[p1].user!!.email
        try {
            p0.txtTTle.text = items[p1].content!!/*.replace(""""""", "")*/
        } catch (e: Exception) {
            p0.txtTTle.text = ""
        }
        try {
            Glide.with(context)
                    .asBitmap()
                    .load(items[p1].user!!.profileImage)
                    .into(p0.cirImgVwProfile)
        } catch (e: java.lang.Exception) {
        }

        p0.imgVwImage.adjustViewBounds = true

        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        val currentDateandTime = sdf.format(Date())

        OneFeedRequest(context, ResponceProcessorLikeCmd(p0, p1)).feedLikeComments(items[p1].id.toString())


        try {
            val date1 = simpleDateFormat.parse(items[p1].createdAt)

            val date2 = simpleDateFormat.parse(currentDateandTime.toString())

            p0.tvTime.text = printDifference(date1, GetUTCdatetimeAsDate()!!)/*currentDateandTime.toString()*/

            if (items[p1].selfLiked!!.toInt() == 1) {
                p0.imgVwLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart))
            }

        } catch (e: Exception) {
            Log.e("RvFeedAdapterError", e.toString())
        }

        try {
            Glide.with(context)
                    .asBitmap()
                    .load(items[p1].image!!.image)
                    .into(p0.imgVwImage)

        } catch (e: Exception) {
        }
        if (p1 == items.size - 2) {
            AllFeedsRequest(context, ResponceProcessorFeedsAdapter()).getCategories(count++)
        }
    }

    inner class ResponceProcessorAllComment(p0: TextView, txtVwViewComments: TextView) : ProcessResponcceInterphase<CommentsAllResponce> {
        var pos = p0
        var isComments = txtVwViewComments
        override fun processResponce(responce: CommentsAllResponce) {
            var count = 0
            var cmmt = ""
            if (responce.comments!!.isEmpty()) {
                isComments.isClickable = false
                isComments.isEnabled = false
                isComments.setTextColor(ContextCompat.getColor(context, R.color.textHint))
            }
            while (count < responce.comments!!.size - 1) {
                cmmt = cmmt + responce.comments!![count].user!!.firstName.toString() + " " +
                        responce.comments!![count].user!!.lastName.toString() + ": " +
                        responce.comments!![count].content.toString() + "\n\n"

                count++
            }
            if (responce.comments!!.isNotEmpty()) {
                cmmt = cmmt + responce.comments!![responce.comments!!.size - 1].user!!.firstName.toString() + " " +
                        responce.comments!![responce.comments!!.size - 1].user!!.lastName.toString() + ": " +
                        responce.comments!![responce.comments!!.size - 1].content.toString()
            }
            pos.text = cmmt

        }

        override fun processResponceError(responce: Any?) {
        }

    }

    inner class ResponceProcessorLikeCmd(p0: ViewHolder, p1: Int) : ProcessResponcceInterphase<OneFeedResponce> {
        override fun processResponceError(responce: Any?) {
        }

        private var pp0 = p0
        var pp1 = p1
        override fun processResponce(responce: OneFeedResponce) {
            if (responce.comments!!.isNotEmpty()) {
            }
            if (responce.likes!!.isNotEmpty()) {
                pp0.txtVwLike.text = responce.likes!!.size.toString()
            } else
                pp0.txtVwLike.text = "0"
        }

    }

    inner class ResponceProcessorFeedsAdapter : ProcessResponcceInterphase<Array<AllFeedsResponce>> {
        override fun processResponceError(responce: Any?) {
        }

        override fun processResponce(responce: Array<AllFeedsResponce>) {
            val list = ArrayList(Arrays.asList(*responce))
            items.addAll(list)
            notifyItemRangeInserted(items.size - list.size, list.size)

        }

    }

    override fun getItemCount(): Int {
        return items.size
//        return 1
    }

    fun set(responce: Array<AllFeedsResponce>) {
        val list = ArrayList(Arrays.asList(*responce))
        items = list
    }

    private fun printDifference(startDate: Date, endDate: Date): String {
        //milliseconds
        var different = endDate.time - startDate.time
        Log.e("Date end", endDate.toString())

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = different / daysInMilli
        different %= daysInMilli

        val elapsedHours = different / hoursInMilli
        different %= hoursInMilli

        val elapsedMinutes = different / minutesInMilli
        different %= minutesInMilli

        val elapsedSeconds = different / secondsInMilli

        return if (elapsedDays > 0)
            if (elapsedDays > 365)
                (elapsedDays / 365).toString() + "yr ago"
            else
                if (elapsedDays > 30)
                    (elapsedDays / 30).toString() + "M ago"
                else
                    elapsedDays.toString() + "d ago"
        else
            if (elapsedHours > 0)
                elapsedHours.toString() + "h" + elapsedMinutes.toString() + "m ago"
            else
                if (elapsedMinutes > 0)
                    elapsedMinutes.toString() + "m"
                else
                    "just now"
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtVwLike: TextView = itemView.findViewById(R.id.txtVwLike)
        var tvTime: TextView = itemView.findViewById(R.id.tvTime)
        var txtVwEmail: TextView = itemView.findViewById(R.id.txtVwEmail)
        var tvName: TextView = itemView.findViewById(R.id.tvName)
        var imgVwLike: ImageButton = itemView.findViewById(R.id.imgVwLike)
        var imgVwComment: ImageButton = itemView.findViewById(R.id.imgVwComment)
        var imgBtnCloseComment: ImageButton = itemView.findViewById(R.id.imgBtnCloseComment)
        var txtVwViewComments: TextView = itemView.findViewById(R.id.txtVwViewComments)
        var imgVwImage: ImageView = itemView.findViewById(R.id.imgVwImage)
        var textVwComments: TextView = itemView.findViewById(R.id.textVwComments)
        var txtTTle: TextView = itemView.findViewById(R.id.txtTTle)
        var llComment: LinearLayout = itemView.findViewById(R.id.llComment)
        var edtTxtComment: EditText = itemView.findViewById(R.id.edtTxtComment)
        var cirImgVwProfile: CircleImageView = itemView.findViewById(R.id.cirImgVwProfile)

        init {

            txtVwViewComments.setOnClickListener {

                if (txtVwViewComments.text == "View More Comments") {
                    txtVwViewComments.text = "View Less Comments"
                    val params = textVwComments.layoutParams
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    textVwComments.layoutParams = params
                } else {
                    txtVwViewComments.text = "View More Comments"
                    val params = textVwComments.layoutParams
                    params.height = (20 * context.resources.displayMetrics.density).toInt()
                    textVwComments.layoutParams = params

                }

            }

            imgVwLike.setOnClickListener {
                if (items[adapterPosition].selfLiked!!.toInt() == 1) {
                    imgVwLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart_gray))
                    items[adapterPosition].selfLiked = 0
                    try {
                        txtVwLike.text = (txtVwLike.text.toString().toInt() - 1).toString()
                    } catch (e: Exception) {
                        txtVwLike.text = ""
                    }


                    LikeRequest(context, ResponceProcessorLike()).like(items[adapterPosition].id.toString(), "unlike")
                } else {
                    items[adapterPosition].selfLiked = 1
                    try {
                        txtVwLike.text = (txtVwLike.text.toString().toInt() + 1).toString()
                    } catch (e: Exception) {
                        txtVwLike.text = ""
                    }
                    LikeRequest(context, ResponceProcessorLike()).like(items[adapterPosition].id.toString(), "likes")
                    imgVwLike.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.heart))
                }
            }

            imgVwImage.setOnClickListener {
                if (items[adapterPosition].user!!.profileImage != null) {
                    val next = Intent(context, ZoomImageActivity::class.java)
                    next.putExtra("URL", items[adapterPosition].image!!.image)
                    context.startActivity(next)
                }
            }

            imgVwComment.setOnClickListener {
                edtTxtComment.setText("")
                llComment.visibility = View.VISIBLE
            }
            imgBtnCloseComment.setOnClickListener {
                llComment.visibility = View.GONE
            }
            edtTxtComment.setOnEditorActionListener { _, actionId, _ ->
                return@setOnEditorActionListener when (actionId) {
                    EditorInfo.IME_ACTION_SEND -> {
                        if (edtTxtComment.text.trim().isNotEmpty()) {
                            CommentRequest(context, ResponceProcessorComment()).Comment(items[adapterPosition].id.toString(), edtTxtComment.text.trim().toString())
                            llComment.visibility = View.GONE
                            /*textVwComments.text = textVwComments.text.toString() +"\n\n"+
                                    "firstName.toString()" + " " +
                                    "lastName.toString()" + ": " +
                                    edtTxtComment.text.toString()*/
                            CommentsAllRequest(context, ResponceProcessorAllComment(textVwComments, txtVwViewComments)).AllComment(items[adapterPosition].id.toString())
                            txtVwViewComments.text = "View Less Comments"
                            txtVwViewComments.isClickable = true
                            txtVwViewComments.isEnabled = true
                            txtVwViewComments.setTextColor(ContextCompat.getColor(context, R.color.textBlue))
                            val params = textVwComments.layoutParams
                            params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                            textVwComments.layoutParams = params
                            edtTxtComment.setText("")
                        }
                        true
                    }
                    else -> false
                }
            }


            /*val rvComments = RvComments(context)
            rvComments.adapter = rvComments
            rvComments.getRecycledViewPool().setMaxRecycledViews(0, 0)
            rvComments.layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, false)
*/
        }

    }

    inner class ResponceProcessorComment : ProcessResponcceInterphase<CommentResponce> {
        override fun processResponce(responce: CommentResponce) {
            Toast.makeText(context, responce.message, Toast.LENGTH_SHORT).show()
        }

        override fun processResponceError(responce: Any?) {
        }
    }

    inner class ResponceProcessorLike : ProcessResponcceInterphase<LikeResponce> {
        override fun processResponce(responce: LikeResponce) {
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    private val DATEFORMAT = "yyyy-MM-dd HH:mm:ss"

    private fun GetUTCdatetimeAsDate(): Date? {
        //note: doesn't check for null
        return StringDateToDate(GetUTCdatetimeAsString())
    }

    private fun GetUTCdatetimeAsString(): String {
        val sdf = SimpleDateFormat(DATEFORMAT)
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        return sdf.format(Date())
    }

    private fun StringDateToDate(StrDate: String): Date? {
        var dateToReturn: Date? = null
        val dateFormat = SimpleDateFormat(DATEFORMAT)

        try {
            dateToReturn = dateFormat.parse(StrDate) as Date
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return dateToReturn
    }
}