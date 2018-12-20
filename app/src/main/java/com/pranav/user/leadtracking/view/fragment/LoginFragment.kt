package com.pranav.user.leadtracking.view.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.pranav.user.leadtracking.R
import java.util.concurrent.atomic.AtomicBoolean
import android.widget.Toast
import com.microsoft.aad.adal.*
import com.microsoft.aad.adal.AuthenticationContext
import com.pranav.user.leadtracking.view.activity.HomeFeedsActivity
import java.lang.Exception


class LoginFragment : Fragment() {

    private val TAG = "LoginFragmentError"
    lateinit var btnLogin: Button
    val AUTHORITY = "https://login.windows.net/a4431f4b-c207-4733-9530-34c08a9b2b8d"
    val RESOURCE_ID = "22f922df-7a6c-4d0e-bd49-b39336ab8035"
    val CLIENT_ID = "cdc17e17-f17d-494d-9f65-f5a811b4ec9a"
    val REDIRECT_URI = "https://leadtracking.ust-global.com"
    val sIntSignInInvoked = AtomicBoolean()
    val MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO = 1
    val MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS = 2
    private var mAuthResult: AuthenticationResult? = null
    lateinit var mAcquireTokenHandler: Handler
    //    lateinit var mAuthContext: AuthenticationContext
    var mAuthContext: AuthenticationContext? = null



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_login, container, false)
        btnLogin = mView.findViewById(R.id.btnLogin)


       /* mAuthContext = AuthenticationContext(
                activity,
                AUTHORITY,
                false)*/

        btnLogin.setOnClickListener {


            mAcquireTokenHandler = object : Handler(Looper.getMainLooper()) {
                override fun handleMessage(msg: Message) {
                    if (sIntSignInInvoked.compareAndSet(false, true)) {
                        if (msg.what == MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO) {
                            mAuthContext!!.acquireToken(activity, RESOURCE_ID, CLIENT_ID, REDIRECT_URI, PromptBehavior.Auto, getAuthInteractiveCallback())
                        } else if (msg.what == MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS) {
                            mAuthContext!!.acquireToken(activity, RESOURCE_ID, CLIENT_ID, REDIRECT_URI, PromptBehavior.Always, getAuthInteractiveCallback())
                        }
                    }
                }
            }




            /*mAuthContext!!.acquireToken(
                    activity,
                    RESOURCE_ID,
                    CLIENT_ID,
                    REDIRECT_URI,
                    PromptBehavior.Always,
                    getAuthInteractiveCallback())*/

//            startActivity(Intent(activity, HomeFeedsActivity::class.java))
        }

        /*mAuthContext = AuthenticationContext(activity, AUTHORITY, false)
        btnLogin.setOnClickListener {
            if (mAuthResult != null)
                Toast.makeText(activity, mAuthResult!!.accessToken.toString(), Toast.LENGTH_SHORT).show()
            else {
                Toast.makeText(activity, "Token missing", Toast.LENGTH_SHORT).show()
                mAuthContext.acquireToken(
                        activity,
                        RESOURCE_ID,
                        CLIENT_ID,
                        REDIRECT_URI,
                        PromptBehavior.Always,
                        callback
                )
               *//* mAuthContext.acquireToken(activity, RESOURCE_ID, CLIENT_ID, REDIRECT_URI, "C10447@ust-global.com", PromptBehavior.Always, "",
                        callback)*//*
//                mAuthContext.cache.removeAll()
            }

//                        startActivity(Intent(activity, HomeFeedsActivity::class.java))
//            startActivity(Intent(activity, MainActivity::class.java))
        }*/
        return mView
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mAuthContext!!.onActivityResult(requestCode, resultCode, data)
    }

    private fun getAuthInteractiveCallback(): AuthenticationCallback<AuthenticationResult>? {
        return object : AuthenticationCallback<AuthenticationResult> {
            override fun onSuccess(result: AuthenticationResult?) {
                Toast.makeText(activity, "ok", Toast.LENGTH_SHORT).show()
            }

            override fun onError(exc: Exception?) {
                Toast.makeText(activity, "not ok", Toast.LENGTH_SHORT).show()
            }

        }

    }

}
