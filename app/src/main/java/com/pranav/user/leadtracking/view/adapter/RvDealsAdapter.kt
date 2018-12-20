package com.pranav.user.leadtracking.view.adapter

import android.Manifest
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
import android.widget.*
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.DealsRequest
import com.pranav.user.leadtracking.controller.responce.DealsResponce
import com.pranav.user.leadtracking.controller.responce2.DatumDealResponce
import com.pranav.user.leadtracking.view.activity.DealProfileActivity
import com.pranav.user.leadtracking.view.activity.DealQuestionsActivity
import com.pranav.user.leadtracking.controller.request.CallRequest
import com.pranav.user.leadtracking.controller.responce.AddDealResponce


class RvDealsAdapter(var context: Context) : RecyclerView.Adapter<RvDealsAdapter.ViewHolder>() {

    //    lateinit var datas: ArrayList<DatumDealResponce>()
    var datas = ArrayList<DatumDealResponce>()
    val ALL_PERMISSIONS = 101
    var count = 2
    val permissions = arrayOf(Manifest.permission.CALL_PHONE)
    var MEMBERS = ""
    private var accountF = ""
    private var levelF = ""
    private var search = ""

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        val layoutInflator = LayoutInflater.from(context)
        val view = layoutInflator.inflate(R.layout.rv_deals, p0, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {

        if (accountF.trim() == "" && levelF.trim() == "") {
            try {
                p0.SeekBarId.progress = datas[p1].level!!.value!!.toInt() * 10
            } catch (e: Exception) {
            }
            try {
                p0.txtVwNameMain.text = datas[p1].name
            } catch (e: Exception) {
            }
            try {
                p0.txtVwName.text = datas[p1].contact!!.name
            } catch (e: Exception) {
            }
            try {
                p0.txtVwAccount.text = datas[p1].group!!.getAdditionalProperties()["account_name"].toString()
            } catch (e: Exception) {
            }
            try {
                p0.txtVwValue.text = "$" + datas[p1].estimatedRevenue
            } catch (e: Exception) {
            }
            try {
                p0.txtVwPreNego.text = datas[p1].level!!.title
            } catch (e: Exception) {
            }
        } else
            if (accountF.trim() == "" && levelF.trim() == datas[p1].level!!.title) {
                try {
                    p0.SeekBarId.progress = datas[p1].level!!.value!!.toInt() * 10
                } catch (e: Exception) {
                }
                try {
                    p0.txtVwNameMain.text = datas[p1].name
                } catch (e: Exception) {
                }
                try {
                    p0.txtVwName.text = datas[p1].contact!!.name
                } catch (e: Exception) {
                }
                try {
                    p0.txtVwAccount.text = datas[p1].group!!.getAdditionalProperties()["account_name"].toString()
                } catch (e: Exception) {
                }
                try {
                    p0.txtVwValue.text = "$" + datas[p1].estimatedRevenue
                } catch (e: Exception) {
                }
                try {
                    p0.txtVwPreNego.text = datas[p1].level!!.title
                } catch (e: Exception) {
                }
            } else
                if (accountF.trim() == datas[p1].group!!.getAdditionalProperties()["account_name"].toString() && levelF.trim() == "") {
                    try {
                        p0.SeekBarId.progress = datas[p1].level!!.value!!.toInt() * 10
                    } catch (e: Exception) {
                    }
                    try {
                        p0.txtVwNameMain.text = datas[p1].name
                    } catch (e: Exception) {
                    }
                    try {
                        p0.txtVwName.text = datas[p1].contact!!.name
                    } catch (e: Exception) {
                    }
                    try {
                        p0.txtVwAccount.text = datas[p1].group!!.getAdditionalProperties()["account_name"].toString()
                    } catch (e: Exception) {
                    }
                    try {
                        p0.txtVwValue.text = "$" + datas[p1].estimatedRevenue
                    } catch (e: Exception) {
                    }
                    try {
                        p0.txtVwPreNego.text = datas[p1].level!!.title
                    } catch (e: Exception) {
                    }
                } else
                    if (accountF.trim() == datas[p1].group!!.getAdditionalProperties()["account_name"].toString() && levelF.trim() == datas[p1].level!!.title) {
                        try {
                            p0.SeekBarId.progress = datas[p1].level!!.value!!.toInt() * 10
                        } catch (e: Exception) {
                        }
                        try {
                            p0.txtVwNameMain.text = datas[p1].name
                        } catch (e: Exception) {
                        }
                        try {
                            p0.txtVwName.text = datas[p1].contact!!.name
                        } catch (e: Exception) {
                        }
                        try {
                            p0.txtVwAccount.text = datas[p1].group!!.getAdditionalProperties()["account_name"].toString()
                        } catch (e: Exception) {
                        }
                        try {
                            p0.txtVwValue.text = "$" + datas[p1].estimatedRevenue
                        } catch (e: Exception) {
                        }
                        try {
                            p0.txtVwPreNego.text = datas[p1].level!!.title
                        } catch (e: Exception) {
                        }
                    } else {
                        p0.cvDeal.visibility = View.GONE
                        val params = p0.cvDeal.layoutParams
                        params.height = 0
                        params.width = 0
                        p0.cvDeal.layoutParams = params

                        val layoutParams = p0.cvDeal.layoutParams as ViewGroup.MarginLayoutParams
                        layoutParams.setMargins(0, 0, 0, 0)
                        p0.cvDeal.requestLayout()
                    }

        if (p1 == datas.size - 2) {
            if (search.trim() == "")
                DealsRequest(context, ResponceProcessorDl()).dealList(count++)
            else
                DealsRequest(context, ResponceProcessorDl()).dealListSearch(search, count++)
        }
    }

    inner class ResponceProcessorDl : ProcessResponcceInterphase<DealsResponce> {
        override fun processResponce(responce: DealsResponce) {
            val list = responce.data as ArrayList
            datas.addAll(list)
            notifyItemRangeInserted(datas.size - list.size, list.size)
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    override fun getItemCount(): Int {
        return datas.size
    }

    fun set(data: ArrayList<DatumDealResponce>, account: String, level: String, searchKey: String) {
        /*val list = ArrayList(Arrays.asList(*data))
        items = list*/
        accountF = account
        levelF = level
        datas = data
        search = searchKey
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //        var txtVwName: TextView
        var SeekBarId: SeekBar = itemView.findViewById(R.id.SeekBarId)
        var txtVwNameMain: TextView = itemView.findViewById(R.id.txtVwNameMain)
        var txtVwName: TextView = itemView.findViewById(R.id.txtVwName)
        var txtVwAccount: TextView = itemView.findViewById(R.id.txtVwAccount)
        var txtVwValue: TextView = itemView.findViewById(R.id.txtVwValue)
        var txtVwPreNego: TextView = itemView.findViewById(R.id.txtVwPreNego)
        var btnCall: Button = itemView.findViewById(R.id.btnCall)
        var relativeLayout: RelativeLayout = itemView.findViewById(R.id.relativeLayout)
        var imgVwEdit: ImageView = itemView.findViewById(R.id.imgVwEdit)
        var cvDeal: CardView = itemView.findViewById(R.id.cvDeal)


        init {
//            txtVwName = itemView.findViewById(R.id.txtVwName)

            SeekBarId.setOnTouchListener { view, motionEvent -> true }

            imgVwEdit.setOnClickListener {

                if (!(context as Activity).isFinishing) {
                    val next = Intent(context, DealQuestionsActivity::class.java)
                    next.putExtra("FLAG", "0")
                    next.putExtra("ID", datas[adapterPosition].id.toString())
                    next.putExtra("LEVEL_ID", datas[adapterPosition].levelId.toString())
                    context.startActivity(next)
                }


//                Toast.makeText(context,datas[adapterPosition].id.toString(),Toast.LENGTH_SHORT).show()
//                context.startActivity(Intent(context, DealQuestionsActivity::class.java))
            }
            btnCall.setOnClickListener {

                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + datas[adapterPosition].contact!!.number))
                    CallRequest(context, ResponceProcesorCall()).DealCall(datas[adapterPosition].id.toString())
                    context.startActivity(intent)
                } else {
                    ActivityCompat.requestPermissions(context as Activity, permissions, ALL_PERMISSIONS)
                    Toast.makeText(context, "Call Permission denied... ", Toast.LENGTH_SHORT).show()
                }
            }
            relativeLayout.setOnClickListener {
                val next = Intent(context, DealProfileActivity::class.java)
                try {
                    next.putExtra("NAME", datas[adapterPosition].name.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("ACCOUNT_NAME", datas[adapterPosition].group!!.getAdditionalProperties()["account_name"].toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("COUNTRY_CODE", if (datas[adapterPosition].contact!!.countryCode != null) datas[adapterPosition].contact!!.countryCode.toString() else "null")
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("PHONE", datas[adapterPosition].contact!!.number.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("DESIGNATION", datas[adapterPosition].contact!!.designation!!.name.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("DESIGNATION_ID", datas[adapterPosition].contact!!.designation!!.id.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("LAST_CONNECTED_AT", datas[adapterPosition].lastContactedAt.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("MAIL", datas[adapterPosition].contact!!.email.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("IMAGE", datas[adapterPosition].contact!!.image.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("GROUP_ID", datas[adapterPosition].group!!.id.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("VALUE", datas[adapterPosition].estimatedRevenue.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("NAME2", datas[adapterPosition].contact!!.name.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("LEVEL_VALUE", datas[adapterPosition].level!!.value!!.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("LEVEL_ID", datas[adapterPosition].levelId.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("CONTACT_ID", datas[adapterPosition].contactId.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("START_DATE", datas[adapterPosition].estimatedStartDate!!.split(" ")[0])
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("END_DATE", datas[adapterPosition].estimatedEndDate!!.split(" ")[0])
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("BILLING_TYPE", datas[adapterPosition].billingType.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("DELIVERABLE_TYPE", datas[adapterPosition].deliverableType.toString())
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("STAGE", datas[adapterPosition].level!!.title.toString())
                } catch (e: Exception) {
                }
                /*
        accountId[account_pos],
        */try {
                var count = 0
                while (count != datas[adapterPosition].members!!.size) {
                    MEMBERS += datas[adapterPosition].members!![count].id.toString() + "##" + datas[adapterPosition].members!![count].name + ","
                    count++
                }
            } catch (e: Exception) {
            }
                try {
                    next.putExtra("MEMBERS", MEMBERS)
                } catch (e: Exception) {
                }
                try {
                    next.putExtra("ID", datas[adapterPosition].id.toString())
                } catch (e: Exception) {
                }

                context.startActivity(next)
            }
        }
    }

    inner class ResponceProcesorCall : ProcessResponcceInterphase<AddDealResponce> {
        override fun processResponce(responce: AddDealResponce) {

        }

        override fun processResponceError(responce: Any?) {
        }

    }


}