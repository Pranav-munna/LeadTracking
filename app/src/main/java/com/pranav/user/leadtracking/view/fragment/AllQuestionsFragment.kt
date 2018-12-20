package com.pranav.user.leadtracking.view.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.pranav.user.leadtracking.R
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase
import com.pranav.user.leadtracking.controller.request.AllQuestionsRequest
import com.pranav.user.leadtracking.controller.request.AnswersRequest
import com.pranav.user.leadtracking.controller.responce.AllQuestionsResponce
import com.pranav.user.leadtracking.controller.responce.AnswersResponce
import com.pranav.user.leadtracking.model.AnswersOneLevelModel
import com.pranav.user.leadtracking.view.adapter.RvAllQuestionAdapter

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AllQuestionsFragment : Fragment(), RvAllQuestionAdapter.CallBackIntephase {

    lateinit var rvQuestion: RecyclerView
    lateinit var txtVwBack: TextView
    lateinit var btnSave: Button
    lateinit var rvAllQuestionAdapter: RvAllQuestionAdapter
    private var accountF = ""
    private var answerSave = ""
    var QuestCount = 0
    var level_no = "-1"
    var percentageFlag = 1
    var Q_item = ArrayList<AnswersOneLevelModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_all_questions, container, false)
        rvQuestion = mView.findViewById(R.id.rvQuestion)
        txtVwBack = mView.findViewById(R.id.txtVwBack)
        btnSave = mView.findViewById(R.id.btnSave)


        val prefs = activity!!.getSharedPreferences("PercentageData", Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("DATA", "")
        editor.apply()

        rvAllQuestionAdapter = RvAllQuestionAdapter(activity!!)
        accountF = arguments!!.get("ID").toString()
        rvQuestion.adapter = rvAllQuestionAdapter
        rvAllQuestionAdapter.setCallBack(this)
        rvQuestion.recycledViewPool.setMaxRecycledViews(0, 0)
        AllQuestionsRequest(activity!!, ResponceProcessorAllQuestion()).AllQuestions(arguments!!.get("ID").toString())
        txtVwBack.setOnClickListener { activity!!.onBackPressed() }

        btnSave.setOnClickListener {
            save()
            if (percentageFlag == 1) {
                answerSave = answerSave.substring(2, answerSave.length)
                answerSave = answerSave.substring(0, answerSave.length - 1)
                Log.e("answerSave", answerSave)
                AnswersRequest(activity!!, ResponceProcessorAnswer()).AnswersUpdate("{$answerSave}}", accountF)
            }
        }
        return mView
    }

    inner class ResponceProcessorAnswer : ProcessResponcceInterphase<AnswersResponce> {
        override fun processResponce(responce: AnswersResponce) {
            Toast.makeText(activity!!, responce.getAdditionalProperties()["message"].toString(), Toast.LENGTH_SHORT).show()
            activity!!.finish()
        }

        override fun processResponceError(responce: Any?) {
        }

    }

    fun save() {
        answerSave = ""
        var count = Q_item.size - 1
        while (-1 != count) {

            if (Q_item[count].A_level != level_no) {
                if (answerSave.trim().isNotEmpty())
                    answerSave = answerSave.substring(0, answerSave.length - 1)

                answerSave += "},\"${Q_item[count].A_level}\":{"
                level_no = Q_item[count].A_level
            }

            if (Q_item[count].Q_type == "text")
                textVw(Q_item[count], count)
            if (Q_item[count].Q_type == "multi-select") {
                if (Q_item[count].Q_options!![0].getAdditionalProperties()["has_metadata"].toString() == "1")
                    gridPercentage(Q_item[count], count)
                /*else
                    gridMultiSelect()*/
            }
            if (Q_item[count].Q_type == "date")
                calender(Q_item[count], count)
            if (Q_item[count].Q_type == "number")
                numberVw(Q_item[count], count)
            if (Q_item[count].Q_type == "percentage")
                gridPercentage(Q_item[count], count)
            if (Q_item[count].Q_type == "single-select")
                gridSingleSelect(Q_item[count], count)
            if (Q_item[count].Q_type == "currency")
                currency(Q_item[count], count)
            count--
        }

    }

    private fun gridSingleSelect(answersOneLevelModel: AnswersOneLevelModel, count: Int) {
        answerSave += "\"${Q_item[count].Q_Id}\":[" + """{"opportunity_field":"${Q_item[count].Q_opportunity_field}","option_id":"${Q_item[count].A_answer!![0].optionId}""""
//{"opportunity_field":"proposal_sow_required","metadata":"25%","option_id":"1"}
        if (Q_item[count].Q_options!![0].getAdditionalProperties()["has_metadata"].toString() == "1") {
            answerSave += ""","metadata":"${Q_item[count].A_answer!![0].metadata!![0].getAdditionalProperties()["value"].toString()}""""
        } else
            answerSave += ""","metadata":"""""

        answerSave += ""","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""
    }

    private fun gridPercentage(answersOneLevelModel: AnswersOneLevelModel, count: Int) {
        var percentageCount = 0.0
        answerSave += "\"${Q_item[count].Q_Id}\":["

        var anscount = 0
        while (anscount != Q_item[count].A_answer!!.size) {
            if (Q_item[count].A_answer!![anscount].metadata!![0].getAdditionalProperties()["value"].toString().trim().isNotEmpty()) {
                answerSave += if (Q_item[count].A_answer!![anscount].id != null) {
                    """{"opportunity_field":"${Q_item[count].Q_opportunity_field}","metadata":"${Q_item[count].A_answer!![anscount].metadata!![0].getAdditionalProperties()["value"].toString().replace("%", "")}","option_id":"${Q_item[count].A_answer!![anscount].optionId}","answer_id":"${Q_item[count].A_answer!![anscount].id}"},"""
                } else {
                    """{"opportunity_field":"${Q_item[count].Q_opportunity_field}","metadata":"${Q_item[count].A_answer!![anscount].metadata!![0].getAdditionalProperties()["value"].toString().replace("%", "")}","option_id":"${Q_item[count].A_answer!![anscount].optionId}"},"""
                }
                percentageCount += Q_item[count].A_answer!![anscount].metadata!![0].getAdditionalProperties()["value"].toString().replace("%", "").trim().toDouble()
            }
            anscount++
        }

        var data = activity!!.getSharedPreferences("PercentageData", Context.MODE_PRIVATE).getString("DATA", "").split("#lt#")
        anscount = 1
        while (anscount != data.size) {
            answerSave += """{"opportunity_field":"${Q_item[count].Q_opportunity_field}","metadata":"${data[anscount].split("##")[0].replace("%", "")}","option_id":"${data[anscount].split("##")[1]}"},"""

            if (data[anscount].split("##")[0].replace("%", "").trim().isNotEmpty())
                percentageCount += data[anscount].split("##")[0].replace("%", "").trim().toDouble()
            anscount++
        }
        answerSave = answerSave.substring(0, answerSave.length - 1) + "],"

        if (percentageCount != 100.0) {
            percentageFlag = 0
            Toast.makeText(activity!!, "${Q_item[count].Q_title} must be 100%", Toast.LENGTH_SHORT).show()
        }
    }

    private fun textVw(answersOneLevelModel: AnswersOneLevelModel, count: Int) {
        answerSave += "\"${Q_item[count].Q_Id}\":[" + if (Q_item[count].Q_opportunity_field.toString() == "null")
            """{"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + """","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""
        else
            """{"opportunity_field":"""" + Q_item[count].Q_opportunity_field + """",""" + """"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + """","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""

        Log.e("answerData", answerSave)
    }

    private fun calender(answersOneLevelModel: AnswersOneLevelModel, count: Int) {

        answerSave += "\"${Q_item[count].Q_Id}\":[" + if (Q_item[count].Q_opportunity_field.toString() == "null")
            """{"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + """","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""
        else
            """{"opportunity_field":"""" + Q_item[count].Q_opportunity_field + """",""" + """"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + """","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""

        Log.e("answerData", answerSave)
    }

    private fun numberVw(answersOneLevelModel: AnswersOneLevelModel, count: Int) {
        answerSave += "\"${Q_item[count].Q_Id}\":[" + if (Q_item[count].Q_opportunity_field.toString() == "null")
            """{"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + """","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""
        else
            """{"opportunity_field":"""" + Q_item[count].Q_opportunity_field + """",""" + """"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + ""","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""

        Log.e("answerData", answerSave)
    }

    private fun currency(answersOneLevelModel: AnswersOneLevelModel, count: Int) {
        answerSave += "\"${Q_item[count].Q_Id}\":[" + if (Q_item[count].Q_opportunity_field.toString() == "null")
            """{"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + """","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""
        else
            """{"opportunity_field":"""" + Q_item[count].Q_opportunity_field + """",""" + """"answer":"""" + answersOneLevelModel.A_answer!![0].answer.toString() + """","answer_id":"${answersOneLevelModel.A_answer!![0].id}"}],"""

    }

    inner class ResponceProcessorAllQuestion : ProcessResponcceInterphase<Array<AllQuestionsResponce>> {
        override fun processResponce(responce: Array<AllQuestionsResponce>) {
            QuestCount = 0
            while (QuestCount != responce.size) {
                Q_item.add(QuestCount, AnswersOneLevelModel(responce[QuestCount].id.toString(),
                        responce[QuestCount].getAdditionalProperties()["title"].toString(),
                        responce[QuestCount].field!!.getAdditionalProperties()["type"].toString(),
                        responce[QuestCount].getAdditionalProperties()["opportunity_field"].toString(),
                        responce[QuestCount].options!!,
                        "",
                        responce[QuestCount].levels!![0].getAdditionalProperties()["rank"].toString(),
                        responce[QuestCount].answers!![0].id.toString(),
                        responce[QuestCount].answers!![0].optionId.toString(),
                        responce[QuestCount].answers!![0].metadata,
                        responce[QuestCount].answers))
                QuestCount++
            }
            rvAllQuestionAdapter.set(Q_item)
            rvQuestion.layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            btnSave.visibility = View.VISIBLE
        }

        override fun processResponceError(responce: Any?) {
        }
    }

    override fun setCallbackSingleselects(q_pos: Int, data: String) {
        Q_item[q_pos].A_answer!![0].optionId = data.toInt()
        rvAllQuestionAdapter.set(Q_item)
        var linearLayoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        linearLayoutManager.scrollToPositionWithOffset(q_pos, 10)
        rvQuestion.layoutManager = linearLayoutManager
    }

    override fun setCallbackSingleselectDatas(data: String, pos: String) {
        Q_item[pos.toInt()].A_answer!![0].setAdditionalProperty("value", data)
    }

    override fun setCallbackSModel(data: java.util.ArrayList<AnswersOneLevelModel>) {
        Q_item = data
    }
}