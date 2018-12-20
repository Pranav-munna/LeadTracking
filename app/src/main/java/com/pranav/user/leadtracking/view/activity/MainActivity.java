package com.pranav.user.leadtracking.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.microsoft.aad.adal.ADALError;
import com.microsoft.aad.adal.AuthenticationCallback;
import com.microsoft.aad.adal.AuthenticationContext;
import com.microsoft.aad.adal.AuthenticationException;
import com.microsoft.aad.adal.AuthenticationResult;
import com.microsoft.aad.adal.IDispatcher;
import com.microsoft.aad.adal.Logger;
import com.microsoft.aad.adal.PromptBehavior;
import com.microsoft.aad.adal.Telemetry;
import com.pranav.user.leadtracking.R;
import com.pranav.user.leadtracking.controller.ProcessResponcceInterphase;
import com.pranav.user.leadtracking.controller.request.LoginRquest;
import com.pranav.user.leadtracking.controller.responce.LoginResponce;

import org.jetbrains.annotations.Nullable;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button btnLogin;

    private static final String AUTHORITY = "https://login.windows.net/a4431f4b-c207-4733-9530-34c08a9b2b8d";
    private static final String CLIENT_ID = "cdc17e17-f17d-494d-9f65-f5a811b4ec9a";
    private static final String RESOURCE_ID = "22f922df-7a6c-4d0e-bd49-b39336ab8035";
    private static final String REDIRECT_URI = "https://leadtracking.ust-global.com";


    private AuthenticationContext mAuthContext;
    private AuthenticationResult mAuthResult;


    private Handler mAcquireTokenHandler;
    private static AtomicBoolean sIntSignInInvoked = new AtomicBoolean();
    private static final int MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO = 1;
    private static final int MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS = 2;

    private static final String USER_ID = "user_id";

    private static final boolean sTelemetryAggregationIsRequired = true;
    Dialog progressDialog;

    static {
        Telemetry.getInstance().registerDispatcher(new IDispatcher() {
            @Override
            public void dispatchEvent(Map<String, String> events) {
                for (Map.Entry<String, String> entry : events.entrySet()) {
                    Log.d(TAG, entry.getKey() + ": " + entry.getValue());
                }
            }
        }, sTelemetryAggregationIsRequired);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        btnLogin = findViewById(R.id.btnLogin);

        progressDialog = new Dialog(getActivity());
        progressDialog.setContentView(R.layout.progress);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCancelable(false);

        mAuthContext = new AuthenticationContext(getApplicationContext(), AUTHORITY, false);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onCallGraphClicked();
            }
        });


       /* String prefs = this.getSharedPreferences("Token_", Context.MODE_PRIVATE).getString("LOGIN", "0");

        if (prefs.equals("0"))*/
//            mAuthContext.getCache().removeAll();

        mAcquireTokenHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                if (sIntSignInInvoked.compareAndSet(false, true)) {
                    if (msg.what == MSG_INTERACTIVE_SIGN_IN_PROMPT_AUTO) {
                        mAuthContext.acquireToken(getActivity(), RESOURCE_ID, CLIENT_ID, REDIRECT_URI, PromptBehavior.Auto, getAuthInteractiveCallback());
                    } else if (msg.what == MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS) {
                        mAuthContext.acquireToken(getActivity(), RESOURCE_ID, CLIENT_ID, REDIRECT_URI, PromptBehavior.FORCE_PROMPT, getAuthInteractiveCallback());
                    }
                }
            }
        };


        Logger.getInstance().setExternalLogger(new Logger.ILogger() {
            @Override
            public void Log(String tag, String message, String additionalMessage, Logger.LogLevel level, ADALError errorCode) {
                Log.d(TAG, message + " " + additionalMessage);
            }
        });

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userId = preferences.getString(USER_ID, "");
        if (!TextUtils.isEmpty(userId)) {
//            mAuthContext.acquireTokenSilentAsync(RESOURCE_ID, CLIENT_ID, userId, getAuthSilentCallback());
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mAuthContext.onActivityResult(requestCode, resultCode, data);
    }

    private void onCallGraphClicked() {
        mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS);
        progressDialog.show();
    }

    private void onSignOutClicked() {
        mAuthContext.getCache().removeAll();
        updateSignedOutUI();
    }

    @SuppressLint("SetTextI18n")
    private void updateSuccessUI() {


//        Toast.makeText(getActivity(), "ok " + mAuthResult.getAccessToken().toString(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    private void updateSignedOutUI() {
        btnLogin.setVisibility(View.VISIBLE);
    }


    public Activity getActivity() {
        return this;
    }

    private AuthenticationCallback<AuthenticationResult> getAuthSilentCallback() {
        return new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                if (authenticationResult == null || TextUtils.isEmpty(authenticationResult.getAccessToken())
                        || authenticationResult.getStatus() != AuthenticationResult.AuthenticationStatus.Succeeded) {
                    Log.d(TAG, "Silent acquire token Authentication Result is invalid, retrying with interactive");

                    mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS);
                    return;
                }
                Log.d(TAG, "Successfully authenticated");
                mAuthResult = authenticationResult;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        Timestamp timestamp = new Timestamp(mAuthResult.getExpiresOn().getTime());
                        System.out.println(timestamp.getTime());

                        Log.e("Token_access", mAuthResult.getAccessToken());
                        Log.e("Token_refresh", mAuthResult.getRefreshToken());
                        Log.e("Token_expires1", String.valueOf(timestamp.getTime()));
                        Log.e("Token_expires2", mAuthResult.getExpiresOn().toString());

                        btnLogin.setText("Loading...");
                        btnLogin.setClickable(false);

                        new LoginRquest(getActivity(), new ResponceProcessorLogin())
                                .login(mAuthResult.getAccessToken(),
                                        mAuthResult.getRefreshToken(),
                                        mAuthResult.getExpiresOn().toString());

                        updateSuccessUI();
                    }
                });
            }

            @Override
            public void onError(Exception exception) {
                progressDialog.dismiss();
                Log.e(TAG, "Authentication failed: " + exception.toString());
                if (exception instanceof AuthenticationException) {
                    AuthenticationException authException = ((AuthenticationException) exception);
                    ADALError error = authException.getCode();
                    logHttpErrors(authException);

                    if (error == ADALError.AUTH_REFRESH_FAILED_PROMPT_NOT_ALLOWED) {
                        mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS);
                    } else if (error == ADALError.NO_NETWORK_CONNECTION_POWER_OPTIMIZATION) {
                        Log.e(TAG, "Device is in doze mode or the app is in standby mode");
                    }
                    return;
                }
                mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS);
            }
        };
    }

    private void logHttpErrors(AuthenticationException authException) {
        int httpResponseCode = authException.getServiceStatusCode();
        Log.d(TAG, "HTTP Response code: " + authException.getServiceStatusCode());
        if (httpResponseCode < 200 || httpResponseCode > 300) {
            HashMap<String, List<String>> headers = authException.getHttpResponseHeaders();
            if (headers != null) {
                StringBuilder sb = new StringBuilder();
                for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                    sb.append(entry.getKey());
                    sb.append(":");
                    sb.append(entry.getValue().toString());
                    sb.append("; ");
                }
                Log.e(TAG, "HTTP Response headers: " + sb.toString());
            }
        }
    }

    private AuthenticationCallback<AuthenticationResult> getAuthInteractiveCallback() {
        return new AuthenticationCallback<AuthenticationResult>() {
            @Override
            public void onSuccess(AuthenticationResult authenticationResult) {
                if (authenticationResult == null || TextUtils.isEmpty(authenticationResult.getAccessToken())
                        || authenticationResult.getStatus() != AuthenticationResult.AuthenticationStatus.Succeeded) {
                    Log.e(TAG, "Authentication Result is invalid");
                    return;
                }
                Log.d(TAG, "Successfully authenticated");
                Log.d(TAG, "ID Token: " + authenticationResult.getIdToken());
                mAuthResult = authenticationResult;


                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                preferences.edit().putString(USER_ID, authenticationResult.getUserInfo().getUserId()).apply();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        new LoginRquest(getActivity(), new ResponceProcessorLogin())
                                .login(mAuthResult.getAccessToken(),
                                        mAuthResult.getRefreshToken(),
                                        mAuthResult.getExpiresOn().toString());
//
                        updateSuccessUI();
                    }
                });
                sIntSignInInvoked.set(false);
            }

            @Override
            public void onError(Exception exception) {
                progressDialog.dismiss();
               /*Log.e(TAG, "Authentication failed: " + exception.toString());
                 if (exception instanceof AuthenticationException) {
                    ADALError error = ((AuthenticationException) exception).getCode();
                    if (error == ADALError.AUTH_FAILED_CANCELLED) {
                        Log.e(TAG, "The user cancelled the authorization request");
                    } else if (error == ADALError.AUTH_FAILED_NO_TOKEN) {
                        mAcquireTokenHandler.sendEmptyMessage(MSG_INTERACTIVE_SIGN_IN_PROMPT_ALWAYS);
                    } else if (error == ADALError.NO_NETWORK_CONNECTION_POWER_OPTIMIZATION) {
                        Log.e(TAG, "Device is in doze mode or the app is in standby mode");
                    }
                }*/
                sIntSignInInvoked.set(false);
            }
        };
    }

    private class ResponceProcessorLogin implements ProcessResponcceInterphase<LoginResponce> {
        @Override
        public void processResponce(LoginResponce responce) {
            if (responce != null) {
                SharedPreferences prefs = getActivity().getSharedPreferences("Token_", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("TOKEN", responce.getAccessToken());
                editor.putString("LOGIN", "1");
                editor.apply();

                startActivity(new Intent(getActivity(), HomeFeedsActivity.class));
            }
        }


        @Override
        public void processResponceError(@Nullable Object responce) {

        }
    }
}
