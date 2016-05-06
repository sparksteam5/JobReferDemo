package netuinfotech.jobreferdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import netuinfotech.jobreferdemo.R;

public class JobDetail extends AppCompatActivity {

    TextView txtTitle, txtCompany, txtDate, txtAddress, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtTitle= (TextView) findViewById(R.id.title);
        txtCompany= (TextView) findViewById(R.id.company);
        txtDate= (TextView) findViewById(R.id.date);
        txtAddress= (TextView) findViewById(R.id.address);
        txtDescription= (TextView) findViewById(R.id.description);

        Intent ob=getIntent();

        txtTitle.setText(ob.getStringExtra("title"));
        txtCompany.setText(ob.getStringExtra("company"));
        txtDate.setText(ob.getStringExtra("date"));
        txtAddress.setText(ob.getStringExtra("address"));
        txtDescription.setText(ob.getStringExtra("description"));

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
