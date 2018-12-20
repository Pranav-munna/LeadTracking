package com.pranav.user.leadtracking.model

import com.pranav.user.leadtracking.controller.responce2.Answer
import com.pranav.user.leadtracking.controller.responce2.Option
import com.pranav.user.leadtracking.controller.responce3.Metadatum
import com.pranav.user.leadtracking.controller.responce3.SubQuestion

class AnswersOneLevelModel(var Q_Id: String,
                           var Q_title: String,
                           var Q_type: String,
                           var Q_opportunity_field: String,
                           var Q_options: List<Option>?,
                           var Q_ref_id: String,
                           var A_level: String,
                           var A_id: String,
                           var A_optionId: String,
                           var A_metadata: List<Metadatum>?,
                           var A_answer: List<Answer>?)