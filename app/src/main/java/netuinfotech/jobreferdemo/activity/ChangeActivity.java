package netuinfotech.jobreferdemo.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import netuinfotech.jobreferdemo.R;
import netuinfotech.jobreferdemo.app.AppConfig;
import netuinfotech.jobreferdemo.app.AppController1;

public class ChangeActivity extends AppCompatActivity {

    private static final String TAG = ChangeActivity.class.getSimpleName();

    EditText txtPassword, txtConfirmPassword;
    Button btnSubmit;

    private ProgressDialog pDialog;
    AppConfig obAC = new AppConfig();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        txtPassword = (EditText) findViewById(R.id.password);
        txtConfirmPassword = (EditText) findViewById(R.id.confirmPassword);

        btnSubmit=(Button)findViewById(R.id.btnSubmit);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        Intent ob = getIntent();
        String id = ob.getStringExtra("id");

        obAC.URL_PROFILE += id + "/pupdate.json";


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bool = 0;
                String pass= txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();

                if (pass.isEmpty()) {
                    txtPassword.setError(txtPassword.getHint() + " Required");
                    bool = 1;
                }

                if (confirmPassword.isEmpty()) {
                    txtConfirmPassword.setError(txtConfirmPassword.getHint() + " Required");
                    bool = 1;
                }

                if( (!pass.isEmpty() && !confirmPassword.isEmpty()) && !pass.equals(confirmPassword)){

                    txtPassword.setError("Password Mismatch");
                    txtConfirmPassword.setError("Password Mismatch");
                    txtPassword.setText("");
                    txtConfirmPassword.setText("");
                    bool=1;
                }

                if (bool == 0) {
                    setUpdateProfile();
                }
            }
        });
    }

    public void setUpdateProfile() {
        // Tag used to cancel the request
        String tag_string_req = "req_update_password";

        pDialog.setMessage("Pleasse wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                obAC.URL_PROFILE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Response", "Update Password Response: " + response.toString());
                hideDialog();

                try {
                    boolean flag = response.contains("code");

                    // Check for error node in json
                    if (flag) {
                        Toast.makeText(ChangeActivity.this,"Password Successfully Changed",Toast.LENGTH_LONG).show();
                        Intent ob=new Intent(ChangeActivity.this,LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ob);

                    } else {
                        Toast.makeText(ChangeActivity.this,
                                "User Not Found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ChangeActivity.this, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ChangeActivity.this,
                        "Username or password is wrong", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("password", txtPassword.getText().toString());
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
