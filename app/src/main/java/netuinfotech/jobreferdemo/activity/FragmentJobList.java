package netuinfotech.jobreferdemo.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import netuinfotech.jobreferdemo.R;
import netuinfotech.jobreferdemo.adapter.CustomListAdapter;
import netuinfotech.jobreferdemo.adapter.MyRecyclerViewAdapter;
import netuinfotech.jobreferdemo.app.AppConfig;
import netuinfotech.jobreferdemo.app.AppController1;
import netuinfotech.jobreferdemo.model.Job;

/**
 * Created by Jay Mataji on 3/29/2016.
 */
public class FragmentJobList extends Fragment {

    ListView lstJob;
    private CustomListAdapter adapter;
    private ArrayList<Job> jobList = new ArrayList<Job>();
    private ProgressDialog pDialog;
    JSONArray jobs = null;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static String LOG_TAG = "CardViewActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_card_view, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyRecyclerViewAdapter(jobList,getActivity());
        mRecyclerView.setAdapter(mAdapter);

        // Progress dialog
        pDialog = new ProgressDialog(getActivity());
        pDialog.setCancelable(false);

        setJobList();


    }

    public void setJobList(){
        // Tag used to cancel the request
        String tag_string_req = "req_joblist";

        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_JOB_SHOW, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d("Job Response: " , response.toString());
                hideDialog();

                try {
                    JSONObject jObj = new JSONObject(response);
                    int code= jObj.getInt("code");
                    Log.d("Code",code+"");

                    // Check for error node in json
                    if (code==200) {

                        JSONObject uid = jObj.getJSONObject("data");
                        jobs=uid.getJSONArray("users");

                        for (int i = 0; i < jobs.length(); i++) {

                            JSONObject obj = jobs.getJSONObject(i);

                            Job job = new Job();
                            job.setName(obj.getString("name"));
                            job.setDate(obj.getString("date"));
                            job.setTitle(obj.getString("title"));
                            job.setAddress(obj.getString("address"));
                            job.setDescription(obj.getString("description"));

                            jobList.add(job);
                        }

                    } else {
                        Log.d("dsd","calll");
                        Toast.makeText(getActivity(),
                                "Job Not Found", Toast.LENGTH_LONG).show();
                    }

//                    adapter.arraylist.addAll(jobList);
//                    adapter.notifyDataSetChanged();
                    mAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
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
