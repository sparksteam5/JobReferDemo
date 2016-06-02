package netuinfotech.jobreferdemo.activity;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Locale;
import java.util.Map;

import netuinfotech.jobreferdemo.R;
import netuinfotech.jobreferdemo.adapter.MyRecyclerViewAdapter;
import netuinfotech.jobreferdemo.app.AppConfig;
import netuinfotech.jobreferdemo.app.AppController;
import netuinfotech.jobreferdemo.model.Job;

public class FragmentJobList extends Fragment {

    private ArrayList<Job> jobList = new ArrayList<Job>();
    private ProgressDialog pDialog;
    JSONArray jobs = null;

    private RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_card_view, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().setTitle("Job");

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

                        for (int i = jobs.length()-1; i > 0; i--) {

                            JSONObject obj = jobs.getJSONObject(i);

                            Job job = new Job();
                            job.setName(obj.getString("name"));
                            job.setDate(obj.getString("date"));
                            job.setTitle(obj.getString("title"));
                            job.setAddress(obj.getString("address"));
                            job.setDescription(obj.getString("description"));
                            job.setSalarytype(obj.getString("salarytype"));

                            jobList.add(job);

                        }

                    } else {
                        Log.d("dsd","calll");
                        Toast.makeText(getActivity(),
                                "Job Not Found", Toast.LENGTH_LONG).show();
                    }

                    MyRecyclerViewAdapter.arraylist.addAll(jobList);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

//        getActivity().getMenuInflater().inflate(R.menu.menu_category,
//                menu);

        inflater.inflate(R.menu.menu_temple_list,menu);

        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();

        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity().getComponentName()));
        searchView.setIconifiedByDefault(false);

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                // this is your adapter that will be filtered
                // myAdapter.getFilter().filter(newText);
                // System.out.println("on text chnge text: " + newText);
                Log.d("hello", newText);

                String text = newText.toString().toLowerCase(
                        Locale.getDefault());
                MyRecyclerViewAdapter.filter(text);
                resetDataset();

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                // this is your adapter that will be filtered
                // myAdapter.getFilter().filter(query);
                // System.out.println("on query submit: " + query);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void resetDataset(){
        mAdapter.notifyDataSetChanged();
    }

}
