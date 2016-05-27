package netuinfotech.jobreferdemo.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import netuinfotech.jobreferdemo.R;
import netuinfotech.jobreferdemo.app.AppConfig;
import netuinfotech.jobreferdemo.app.AppController;

public class FragmentJob extends Fragment{

    EditText txtTitle, txtName,txtAddress, txtDescription, txtSalaryType;
    Button btnAdd;

    private ProgressDialog pDialog;
    String formattedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_job, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txtTitle=(EditText)getView().findViewById(R.id.txtTitle);
        txtName=(EditText)getView().findViewById(R.id.txtName);
        txtAddress=(EditText)getView().findViewById(R.id.txtAddress);
        txtDescription=(EditText)getView().findViewById(R.id.txtDescription);
        txtSalaryType=(EditText)getView().findViewById(R.id.txtSalaryType);

        btnAdd=(Button)getView().findViewById(R.id.btnAdd);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bool=0;
                String t=txtTitle.getText().toString();
                String c=txtName.getText().toString();
                String a=txtAddress.getText().toString();
                String s=txtSalaryType.getText().toString();
                String d=txtDescription.getText().toString();

                if(t.length()==0) {
                    txtTitle.setError(txtTitle.getHint() + " Required");
                    txtTitle.setFocusable(true);
                    bool=1;
                }

                if(c.length()==0){
                    txtName.setError(txtName.getHint() + " Required");
                    txtName.setFocusable(true);
                    bool=1;
                }

                if(a.length()==0){
                    txtAddress.setError(txtAddress.getHint() + " Required");
                    txtAddress.setFocusable(true);
                    bool=1;
                }

                if(s.length()==0){
                    txtSalaryType.setError(txtSalaryType.getHint() + " Required");
                    txtSalaryType.setFocusable(true);
                    bool=1;
                }

                if(d.length()==0){
                    txtDescription.setError(txtDescription.getHint() + " Required");
                    txtDescription.setFocusable(true);
                    bool=1;
                }

                if(bool==0){
                    addJob();
                }

            }
        });

        //Get Current Date
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());

    }

    public  void addJob(){
        // Tag used to cancel the request
        String tag_string_req = "req_job";

        pDialog.setMessage("Loading ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_JOB_ADD, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("JOB Register Response: ", response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int code = jObj.getInt("code");
                    if (code==201) {
                        Toast.makeText(getActivity(), "Job successfully Added", Toast.LENGTH_LONG).show();

                    } else {
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getActivity(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Registration Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();

                params.put("title", txtTitle.getText().toString());
                params.put("name", txtName.getText().toString());
                params.put("date", formattedDate);
//                params.put("vacany", spnVacnay.getSelectedItem().toString());
                params.put("address", txtAddress.getText().toString());
                params.put("description", txtDescription.getText().toString());
                params.put("salarytype", txtSalaryType.getText().toString());
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
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
