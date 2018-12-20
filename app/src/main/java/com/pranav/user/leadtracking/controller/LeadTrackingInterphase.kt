package com.pranav.user.leadtracking.controller

import com.pranav.user.leadtracking.controller.responce.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface LeadTrackingInterphase {

    /*@FormUrlEncoded
    @POST("api/categories/7")
    fun getCategories(@Field("shopId") shopId: String,
                      @Query("page") page: Int): Call<Array<ResponceCategories>>*/

    @GET("api/posts")
    fun getAllfeeds(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Query("page") page: Int): Call<Array<AllFeedsResponce>>

    @GET("api/all-billings")
    fun AllBilling(@Header("Authorization") authKey: String,
                   @Header("Accept") accept: String): Call<Array<AllBillingsResponce>>

    @GET("api/all-questions-of/{id}")
    fun AllQuestions(@Header("Authorization") authKey: String,
                     @Header("Accept") accept: String,
                     @Path("id") id: String): Call<Array<AllQuestionsResponce>>

    @GET("api/all-deliverables")
    fun AllDeliverables(@Header("Authorization") authKey: String,
                        @Header("Accept") accept: String): Call<Array<AllBillingsResponce>>

    @FormUrlEncoded
    @POST("api/azure/token")
    fun login(@Header("Accept") json: String,
              @Field("access_token") accessTkn: String,
              @Field("refresh_token") refreshTkn: String,
              @Field("expires_On") expireOn: String): Call<LoginResponce>

    @GET("api/posts/{id}")
    fun feedLikeComments(@Header("Authorization") authKey: String,
                         @Header("Accept") accept: String,
                         @Path("id") id: String): Call<OneFeedResponce>

    @GET("api/contacts")
    fun networklist(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Query("page") page: Int): Call<NetworkResponce>

    @GET("api/contacts")
    fun networklist(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Query("search") search: String,
                    @Query("page") page: Int): Call<NetworkResponce>

    @GET("api/leads")
    fun dealList(@Header("Authorization") authKey: String,
                 @Header("Accept") accept: String,
                 @Query("page") page: Int): Call<DealsResponce>

    @GET("api/leads")
    fun dealListSearch(@Header("Authorization") authKey: String,
                       @Header("Accept") accept: String,
                       @Query("search") search: String,
                       @Query("page") page: Int): Call<DealsResponce>

    @GET("api/score-board")
    fun ScoreboardList(@Header("Authorization") authKey: String,
                       @Header("Accept") accept: String,
                       @Query("page") page: Int): Call<Array<ScoreboardResponce>>

    @GET("api/profile")
    fun profile(@Header("Authorization") authKey: String,
                @Header("Accept") accept: String): Call<ProfileResponce>

    @GET("api/designations")
    fun desgination(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String): Call<Array<DesignationResponce>>

    @Multipart
    @POST("api/posts")
    fun getstatus(@Header("Authorization") authKey: String,
                  @Header("Accept") accept: String,
                  @Part("content") content: RequestBody,
                  @Part imagePart: MultipartBody.Part?): Call<FeedsUploadResponce>

    @GET("api/groups/{id}/contacts")
    fun DealDetails(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Path("id") id: String): Call<Array<DealDetailResponce>>

    @GET("api/contacts")
    fun contact(@Header("Authorization") authKey: String,
                @Header("Accept") accept: String,
                @Query("page") page: Int): Call<DealDetailContactResponce>

    @GET("api/contacts")
    fun contactSearch(@Header("Authorization") authKey: String,
                      @Header("Accept") accept: String,
                      @Query("search") search: String,
                      @Query("page") page: Int): Call<DealDetailContactResponce>

    @GET("api/level/{id}/{qno}/questions")
    fun Question(@Header("Authorization") authKey: String,
                 @Header("Accept") accept: String,
                 @Path("id") id: String,
                 @Path("qno") qno: String): Call<Array<QuestionsOneLevelResponce>>

    @GET("api/meeting-feedback-questions")
    fun MeetingQuestion(@Header("Authorization") authKey: String,
                        @Header("Accept") accept: String): Call<Array<QuestionsOneLevelResponce>>

    @FormUrlEncoded
    @POST("api/meeting-feedback-questions")
    fun MeetingQuestion(@Header("Authorization") authKey: String,
                        @Header("Accept") accept: String,
                        @Field("answers") answers: String,
                        @Field("meeting_id") meeting_id: String): Call<AddNetworkResponce>

    @GET("api/notes")
    fun Notes(@Header("Authorization") authKey: String,
              @Header("Accept") accept: String,
              @Query("contact") id: String,
              @Query("page") page: Int): Call<Array<NotesResponce>>

    @GET("api/all-meeting-locations")
    fun locations(@Header("Authorization") authKey: String,
                  @Header("Accept") accept: String): Call<Array<LocationResponce>>

    @GET("api/all-meeting-types")
    fun type(@Header("Authorization") authKey: String,
             @Header("Accept") accept: String): Call<Array<LocationResponce>>

    @GET("api/contact-search")
    fun contacts(@Header("Authorization") authKey: String,
                 @Header("Accept") accept: String,
                 @Query("page") page: Int): Call<ContactSearchResponce>

    @FormUrlEncoded
    @POST("api/meetings")
    fun setmeeting(@Header("Authorization") authKey: String,
                   @Header("Accept") accept: String,
                   @Field("agenda") agenda: String,
                   @Field("contacts") contacts: String,
                   @Field("location") location: String,
                   @Field("scheduled_on") scheduled: String,
                   @Field("subject") subject: String,
                   @Field("type") type: String,
                   @Field("calendar_id") calendar_id: String,
                   @Field("lead_id") lead_id: String): Call<SetMeetingResponce>

    @FormUrlEncoded
    @POST("api/meetings")
    fun setmeetingNetwork(@Header("Authorization") authKey: String,
                          @Header("Accept") accept: String,
                          @Field("agenda") agenda: String,
                          @Field("contacts") contacts: String,
                          @Field("location") location: String,
                          @Field("scheduled_on") scheduled: String,
                          @Field("subject") subject: String,
                          @Field("type") type: String,
                          @Field("calendar_id") calendar_id: String): Call<SetMeetingResponce>

    @FormUrlEncoded
    @POST("api/notes")
    fun AddNotes(@Header("Authorization") authKey: String,
                 @Header("Accept") accept: String,
                 @Field("contact_id") contact_id: String,
                 @Field("title") title: String,
                 @Field("body") body: String): Call<AddNotesResponce>

    @FormUrlEncoded
    @POST("api/leads")
    fun AddDeal(@Header("Authorization") authKey: String,
                @Header("Accept") accept: String,
                @Field("billing_type") billing_type: String,
                @Field("contact_id") contact_id: String,
                @Field("deliverable_type") deliverable_type: String,
                @Field("estimated_end_date") estimated_end_date: String,
                @Field("estimated_revenue") estimated_revenue: String,
                @Field("estimated_start_date") estimated_start_date: String,
                @Field("group_id") group_id: String,
                @Field("name") name: String): Call<AddDealResponce>

    @FormUrlEncoded
    @POST("api/last-contacted-at")
    fun NetworkCall(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Field("contact_id") contact_id: String): Call<AddDealResponce>

    @FormUrlEncoded
    @POST("api/calls")
    fun DealCall(@Header("Authorization") authKey: String,
                 @Header("Accept") accept: String,
                 @Field("lead_id") lead_id: String): Call<AddDealResponce>

    @FormUrlEncoded
    @POST("api/update-lead")
    fun AddDealUpdate(@Header("Authorization") authKey: String,
                      @Header("Accept") accept: String,
                      @Field("billing_type") billing_type: String,
                      @Field("contact_id") contact_id: String,
                      @Field("deliverable_type") deliverable_type: String,
                      @Field("estimated_end_date") estimated_end_date: String,
                      @Field("estimated_revenue") estimated_revenue: String,
                      @Field("estimated_start_date") estimated_start_date: String,
                      @Field("group_id") group_id: String,
                      @Field("lead") lead: String,
                      @Field("name") name: String): Call<AddDealResponce>

    @FormUrlEncoded
    @POST("api/level/answers")
    fun Answers(@Header("Authorization") authKey: String,
                @Header("Accept") accept: String,
                @Field("answers") answers: String,
                @Field("lead_id") lead_id: String): Call<AnswersResponce>

    @FormUrlEncoded
    @POST("api/contacts/{id}")
    fun DeleteContact(@Header("Authorization") authKey: String,
                      @Header("Accept") accept: String,
                      @Field("_method") method: String,
                      @Path("id") id: String): Call<AnswersResponce>

    @FormUrlEncoded
    @POST("api/level/answers")
    fun Answers(@Header("Authorization") authKey: String,
                @Header("Accept") accept: String,
                @Field("_method") _method: String,
                @Field("answers") answers: String,
                @Field("lead_id") lead_id: String): Call<AnswersResponce>

    @FormUrlEncoded
    @POST("api/delete-note")
    fun DeleteNotes(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Field("note") accounts: String): Call<DeleteNotesResponce>

    @FormUrlEncoded
    @POST("api/sync-groups-with-user")
    fun UpdateAccount(@Header("Authorization") authKey: String,
                      @Header("Accept") accept: String,
                      @Field("accounts") accounts: String): Call<DeleteNotesResponce>

    @GET("api/groups")
    fun groups(@Header("Authorization") authKey: String,
               @Header("Accept") accept: String): Call<Array<GroupResponce>>

    @GET("api/posts/{id}")
    fun AllComment(@Header("Authorization") authKey: String,
                   @Header("Accept") accept: String,
                   @Path("id") id: String): Call<CommentsAllResponce>

    @FormUrlEncoded
    @POST("api/contacts/{id}")
    fun EditNetwork(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Field("email") email: String,
                    @Field("account_id") account_id: String,
                    @Field("name") name: String,
                    @Field("country_code") country_code: String,
                    @Field("_method") _method: String,
                    @Field("number") number: String,
                    @Field("designation_id") designation_id: String,
                    @Path("id") id: String): Call<EditNetworkResponce>

    @Multipart
    @POST("api/contacts/{id}")
    fun EditNetwork(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Part imagePart: MultipartBody.Part,
                    @Path("id") id: String): Call<EditNetworkResponce>

    @FormUrlEncoded
    @POST("api/contacts")
    fun AddNetwork(@Header("Authorization") authKey: String,
                   @Header("Accept") accept: String,
                   @Field("number") number: String,
                   @Field("email") email: String,
                   @Field("account_id") account_id: String,
                   @Field("designation_id") designation_id: String,
                   @Field("name") name: String,
                   @Field("country_code") country_code: String): Call<AddNetworkResponce>

    @Multipart
    @POST("api/contacts")
    fun AddNetworkImage(@Header("Authorization") authKey: String,
                        @Header("Accept") accept: String,
                        @Part("number") email: RequestBody,
                        @Part("email") account_id: RequestBody,
                        @Part("account_id") name: RequestBody,
                        @Part("designation_id") country_code: RequestBody,
                        @Part("name") _method: RequestBody,
                        @Part("country_code") number: RequestBody,
                        @Part imagePart: MultipartBody.Part): Call<AddNetworkResponce>

    @FormUrlEncoded
    @POST("api/meetings/{id}")
    fun DeleteMeeting(@Header("Authorization") authKey: String,
                      @Header("Accept") accept: String,
                      @Field("_method") email: String,
                      @Path("id") id: String): Call<AddNetworkResponce>

    @FormUrlEncoded
    @POST("api/lead-participants")
    fun Addcontacts(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Field("_method") _method: String,
                    @Field("contacts") contacts: String,
                    @Field("lead_id") lead_id: String): Call<AddNetworkResponce>

    @FormUrlEncoded
    @POST("api/lead/update-level")
    fun UpdateLevel(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Field("lead_id") lead_id: String,
                    @Field("level_id") level_id: String): Call<AddNetworkResponce>

    @FormUrlEncoded
    @POST("api/{like}")
    fun like(@Header("Authorization") authKey: String,
             @Header("Accept") accept: String,
             @Field("post_id") id: String,
             @Path("like") like: String): Call<LikeResponce>


    @FormUrlEncoded
    @POST("api/comments")
    fun Comment(@Header("Authorization") authKey: String,
                @Header("Accept") accept: String,
                @Field("post_id") post_id: String,
                @Field("content") content: String): Call<CommentResponce>


    /* @Multipart
     @POST("api/contacts/{id}")
     fun EditNetworkImage(@Header("Authorization") authKey: String,
                          @Header("Accept") accept: String,
                          @Part param: HashMap<String, RequestBody>,
                          @Path("id") id: String,
                          @Part imagePart: MultipartBody.Part): Call<EditNetworkResponce>*/
    @Multipart
    @POST("api/contacts/{id}")
    fun EditNetworkImage(@Header("Authorization") authKey: String,
                         @Header("Accept") accept: String,
                         @Part("email") email: RequestBody,
                         @Part("account_id") account_id: RequestBody,
                         @Part("name") name: RequestBody,
                         @Part("country_code") country_code: RequestBody,
                         @Part("_method") _method: RequestBody,
                         @Part("number") number: RequestBody,
                         @Part("designation_id") designation_id: RequestBody,
                         @Path("id") id: String,
                         @Part imagePart: MultipartBody.Part): Call<EditNetworkResponce>

    @FormUrlEncoded
    @POST("api/update-note")
    fun updateNotes(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Field("body") body: String,
                    @Field("note") note: String,
                    @Field("title") title: String): Call<NoteUpdateResponce>

    @FormUrlEncoded
    @POST("api/update-profile-details")
    fun updateProfile(@Header("Authorization") authKey: String,
                      @Header("Accept") accept: String,
                      @Field("about") about: String,
                      @Field("contact_number") contact_number: String,
                      @Field("designation_id") designation_id: String,
                      @Field("first_name") first_name: String,
                      @Field("last_name") last_name: String,
                      @Field("location") location: String,
                      @Field("office_number") office_number: String): Call<EditNetworkResponce>

    @Multipart
    @POST("api/update-profile-details")
    fun updateProfile(@Header("Authorization") authKey: String,
                      @Header("Accept") accept: String,
                      @Part imagePart: MultipartBody.Part): Call<EditNetworkResponce>


    @GET("api/levels-all")
    fun AllLevel(@Header("Authorization") authKey: String,
                 @Header("Accept") accept: String): Call<Array<QuestionAllLevelsResponce>>

    @GET("api/meetings/lead/{id}")
    fun myMeeting(@Header("Authorization") authKey: String,
                  @Header("Accept") accept: String,
                  @Path("id") id: String): Call<Array<ScheduledMeetingResponce>>

    @GET("api/meetings/contact/{id}")
    fun myMeetingNetwork(@Header("Authorization") authKey: String,
                         @Header("Accept") accept: String,
                         @Path("id") id: String): Call<Array<ScheduledMeetingResponce>>

    @GET("api/timeline/{id}")
    fun timeLine(@Header("Authorization") authKey: String,
                 @Header("Accept") accept: String,
                 @Path("id") id: String): Call<Array<TimelineResponce>>

    @GET("api/all-groups")
    fun allAccounts(@Header("Authorization") authKey: String,
                    @Header("Accept") accept: String,
                    @Query("search") search: String,
                    @Query("page") page: Int): Call<Array<AnswersResponce>>

}/*http://138.197.32.34/lead_tracking/public/api/timeline/153*/