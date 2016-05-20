package netuinfotech.jobreferdemo.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import netuinfotech.jobreferdemo.R;
import netuinfotech.jobreferdemo.app.AppConfig;
import netuinfotech.jobreferdemo.app.AppController1;

public class LoginActivity extends Activity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private Button btnLogin;
    private Button btnLinkToRegister, btnLinkToAgreement, btnForgot;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        btnLinkToAgreement = (Button) findViewById(R.id.btnLinkToAgreement);
        btnForgot= (Button) findViewById(R.id.btnForgot);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                int bool = 0;
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                if (email.isEmpty()) {
                    inputEmail.setError(inputEmail.getHint() + " Required");
                    bool = 1;
                }

                if (password.isEmpty()) {
                    inputPassword.setError(inputPassword.getHint() + " Required");
                    bool = 1;
                }

                if (bool == 0) {
                    checkLogin(email, password);
                }
            }
        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnLinkToAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),
                        AgreementActivity.class);
                startActivity(i);
            }
        });

        btnForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent ob=new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(ob);
            }
        });

    }

    private void checkLogin(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_login";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int code = jObj.getInt("code");
                    Log.d("Code", code + "");

                    // Check for error node in json
                    if (code == 200) {

                        JSONObject uid = jObj.getJSONObject("data");
                        JSONObject user = uid.getJSONObject("user");

                        Integer id = user.getInt("id");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        int status = Integer.parseInt(user.getString("status"));

                        if (status == 1) {
                            // Launch main activity
                            Intent intent = new Intent(LoginActivity.this,
                                    MainActivity.class);

                            intent.putExtra("id", id);
                            intent.putExtra("uname", name);
                            intent.putExtra("email", email);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "User is not verified", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("dsd", "calll");
                        // Error in login. Get the error message
                        //String errorMsg = jObj.getString("error");
                        Toast.makeText(LoginActivity.this,
                                "User Not Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(LoginActivity.this,
                        "Username or password is wrong", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", email);
                params.put("pass", password);

                return params;
            }
        };

        // Adding request to request queue
        AppController1.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
