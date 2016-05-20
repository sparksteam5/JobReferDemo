package netuinfotech.jobreferdemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
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

public class ForgotActivity extends AppCompatActivity {

    private static final String TAG = ForgotActivity.class.getSimpleName();

    EditText txtEmail, txtAnswer;
    Spinner spnQuestion;
    Button btnSubmit;

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        txtEmail = (EditText) findViewById(R.id.email);
        txtAnswer = (EditText) findViewById(R.id.txtAnswer);
        spnQuestion = (Spinner) findViewById(R.id.spn_question);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.question_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnQuestion.setAdapter(adapter);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bool = 0;
                String email = txtEmail.getText().toString().trim();
                String answer = txtAnswer.getText().toString().trim();

                if (email.isEmpty()) {
                    txtEmail.setError(txtEmail.getHint() + " Required");
                    bool = 1;
                }

                if (answer.isEmpty()) {
                    txtAnswer.setError(txtAnswer.getHint() + " Required");
                    bool = 1;
                }

                if (bool == 0) {
                    checkUser(email, answer);
                }
            }
        });

    }

    private void checkUser(final String email, final String password) {
        // Tag used to cancel the request
        String tag_string_req = "req_user";

        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_CHECKUSER, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Forgot Response: " + response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int code = jObj.getInt("code");
                    Log.d("Code", code + "");

                    // Check for error node in json
                    if (code == 200) {

                        JSONObject uid = jObj.getJSONObject("data");
                        JSONObject user = uid.getJSONObject("user");

                        int id = user.getInt("id");
                        String answer = user.getString("answer");
                        String email1 = user.getString("email");

                        Log.d("email",email+" " + email1);

                        if (email.equalsIgnoreCase(email1)) {

                            if (answer.equalsIgnoreCase(txtAnswer.getText().toString())) {
                                Intent ob = new Intent(ForgotActivity.this, ChangeActivity.class);
                                ob.putExtra("id",id+"");
                                startActivity(ob);
                            } else {
                                txtAnswer.setError("Answer is wrong");
                                Toast.makeText(ForgotActivity.this,"Answer is wrong",Toast.LENGTH_LONG).show();
                            }

                        } else {
                            txtEmail.setError("User is not exist");
                            Toast.makeText(ForgotActivity.this,"User is not exist",Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.d("dsd", "calll");
                        // Error in login. Get the error message
                        //String errorMsg = jObj.getString("error");
                        Toast.makeText(ForgotActivity.this,
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
                txtEmail.setError("User is not exist");
                Toast.makeText(ForgotActivity.this,"User is not exist",Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", email);
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
