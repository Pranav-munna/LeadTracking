package com.pranav.user.leadtracking.model

import com.pranav.user.leadtracking.controller.responce2.Option

class QuestionsOneLevelModel(var Q_Id: String,
                             var Q_title: String,
                             var Q_type: String,
                             var Q_opportunity_field: String,
                             var Q_options: List<Option>?,
                             var Q_ref_id: String,
                             var Q_sort_order: Int,
                             var Q_main_q_id: String)