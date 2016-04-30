package netuinfotech.jobreferdemo.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import netuinfotech.jobreferdemo.app.AppController;

/**
 * Created by Jay Mataji on 3/22/2016.
 */
public class FragmentProfile extends Fragment {

    Spinner spnQual, spnYear;
    EditText txtName, txtEmail, txtPhone, txtAdd, txtPer, txtSkill, txtComapny, txtExp;
    Button btnSubmit, btnProfile, btnEducation, btnExperience;

    private ProgressDialog pDialog;
    AppConfig obAC = new AppConfig();

    // Animation
    Animation animSideDownProf, animSideDownEdu, animSideDownExp;
    Animation animSlideUpProf, animSlideUpEdu, animSlideUpExp;

    LinearLayout llProfile, llEducation, llExperience;

    int flagProf = 0, flagEdu = 0, flagExp = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        spnQual = (Spinner) getView().findViewById(R.id.spn_qualification);
        spnYear = (Spinner) getView().findViewById(R.id.spn_year);

        txtName = (EditText) getView().findViewById(R.id.txtName);
        txtEmail = (EditText) getView().findViewById(R.id.txtEmail);
        txtPhone = (EditText) getView().findViewById(R.id.txtPhone);
        txtAdd = (EditText) getView().findViewById(R.id.txtAdd);
        txtPer = (EditText) getView().findViewById(R.id.txtPer);
        txtSkill = (EditText) getView().findViewById(R.id.txtSkill);
        txtComapny = (EditText) getView().findViewById(R.id.txtCompany);
        txtExp = (EditText) getView().findViewById(R.id.txtExp);

        btnSubmit = (Button) getView().findViewById(R.id.btnSubmit);
        btnProfile = (Button) getView().findViewById(R.id.btnProfile);
        btnEducation = (Button) getView().findViewById(R.id.btnEducation);
        btnExperience = (Button) getView().findViewById(R.id.btnExperience);

        // load the animation
        animSideDownProf = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);

        animSideDownEdu = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);

        animSideDownExp = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_down);


        // load the animation
        animSlideUpProf = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);

        animSlideUpEdu = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);

        animSlideUpExp = AnimationUtils.loadAnimation(getActivity(),
                R.anim.slide_up);


        llProfile = (LinearLayout) getView().findViewById(R.id.llProfile);
        llEducation = (LinearLayout) getView().findViewById(R.id.llEducation);
        llExperience = (LinearLayout) getView().findViewById(R.id.llExperience);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.qualification_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnQual.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.year_array, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnYear.setAdapter(adapter1);

        Intent ob = getActivity().getIntent();

        Integer id = ob.getIntExtra("id", 0);

        obAC.URL_PROFILE += id + "/update.json";
        Log.d("update URL", obAC.URL_PROFILE);

        obAC.URL_VIEW += id + "/.json";

        setProfile();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUpdateProfile();
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flagProf == 0) {
                    llProfile.setVisibility(View.VISIBLE);
                    txtName.setVisibility(View.VISIBLE);
                    txtEmail.setVisibility(View.VISIBLE);
                    txtPhone.setVisibility(View.VISIBLE);
                    txtAdd.setVisibility(View.VISIBLE);

                    llProfile.startAnimation(animSideDownProf);
                    btnProfile.setBackgroundResource(R.drawable.up);
                    flagProf = 1;
                } else if (flagProf == 1) {
                    llProfile.startAnimation(animSlideUpProf);
                    llProfile.setVisibility(View.GONE);
                    txtName.setVisibility(View.GONE);
                    txtEmail.setVisibility(View.GONE);
                    txtAdd.setVisibility(View.GONE);
                    txtPhone.setVisibility(View.GONE);

                    btnProfile.setBackgroundResource(R.drawable.down);
                    flagProf = 0;
                }
            }
        });

        btnEducation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagEdu == 0) {
                    llEducation.setVisibility(View.VISIBLE);
                    spnQual.setVisibility(View.VISIBLE);
                    spnYear.setVisibility(View.VISIBLE);
                    txtPer.setVisibility(View.VISIBLE);
                    txtSkill.setVisibility(View.VISIBLE);

                    llEducation.startAnimation(animSideDownEdu);
                    btnEducation.setBackgroundResource(R.drawable.up);
                    flagEdu = 1;
                } else if (flagEdu == 1) {
                    llEducation.startAnimation(animSlideUpEdu);
                    llEducation.setVisibility(View.GONE);
                    spnQual.setVisibility(View.GONE);
                    spnYear.setVisibility(View.GONE);
                    txtPer.setVisibility(View.GONE);
                    txtSkill.setVisibility(View.GONE);

                    btnEducation.setBackgroundResource(R.drawable.down);
                    flagEdu = 0;
                }
            }
        });

        btnExperience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagExp == 0) {
                    llExperience.setVisibility(View.VISIBLE);
                    txtComapny.setVisibility(View.VISIBLE);
                    txtExp.setVisibility(View.VISIBLE);

                    llExperience.startAnimation(animSideDownExp);
                    btnExperience.setBackgroundResource(R.drawable.up);
                    flagExp = 1;
                } else if (flagExp == 1) {
                    llExperience.startAnimation(animSlideUpExp);
                    llExperience.setVisibility(View.GONE);
                    txtComapny.setVisibility(View.GONE);
                    txtExp.setVisibility(View.GONE);

                    btnExperience.setBackgroundResource(R.drawable.down);
                    flagExp = 0;
                }
            }
        });
    }

    public void setProfile() {
        // Tag used to cancel the request
        String tag_string_req = "req_view";

        pDialog.setMessage("Please wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                obAC.URL_VIEW, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("View Response: ", response.toString());
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
                        Log.d("Email", email);

                        txtName.setText((user.getString("name") != "null") ? user.getString("name") : "");
                        txtEmail.setText((user.getString("email") != "null") ? user.getString("email") : "");
                        txtPhone.setText((user.getString("phone") != "null") ? user.getString("phone") : "");
                        txtAdd.setText((user.getString("address") != "null") ? user.getString("address") : "");
                        spnQual.setSelection((user.getString("qualification") != "null") ? Integer.parseInt(user.getString("qualification")) : 0);
                        spnYear.setSelection((user.getString("completion_year") != "null") ? Integer.parseInt(user.getString("completion_year")) : 0);
                        txtPer.setText((user.getString("percentage") != "null") ? user.getString("percentage") : "");
                        txtSkill.setText((user.getString("skills") != "null") ? user.getString("skills") : "");
                        txtComapny.setText((user.getString("company") != "null") ? user.getString("company") : "");
                        txtExp.setText((user.getString("experience") != "null") ? user.getString("experience") : "");

                    } else {
                        Log.d("dsd", "calll");
                        // Error in login. Get the error message
                        //String errorMsg = jObj.getString("error");
                        Toast.makeText(getActivity(),
                                "User Not Found", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(),
                        "Username or password is wrong", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
//                params.put("user", email);
//                params.put("pass", password);
                return params;
            }
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    public void setUpdateProfile() {
        // Tag used to cancel the request
        String tag_string_req = "req_profile";

        pDialog.setMessage("Pleasse wait ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                obAC.URL_PROFILE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Response", "Login Response: " + response.toString());
                hideDialog();

                try {
                    boolean flag = response.contains("code");

                    // Check for error node in json
                    if (flag) {

                    } else {
                        Log.d("dsd", "calll");
                        // Error in login. Get the error message
                        //String errorMsg = jObj.getString("error");
                        Toast.makeText(getActivity(),
                                "User Not Found", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),
                        "Username or password is wrong", Toast.LENGTH_LONG).show();
                hideDialog();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", txtName.getText().toString());
                params.put("email", txtEmail.getText().toString());
                params.put("phone", txtPhone.getText().toString());
                params.put("address", txtAdd.getText().toString());
                params.put("qualification", spnQual.getSelectedItemId() + "");
                params.put("completion_year", spnYear.getSelectedItemId() + "");
                params.put("percentage", txtPer.getText().toString());
                params.put("skills", txtSkill.getText().toString());
                params.put("company", txtComapny.getText().toString());
                params.put("experience", txtExp.getText().toString());
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        return rootView;

    }
}
