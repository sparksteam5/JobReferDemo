package netuinfotech.jobreferdemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import netuinfotech.jobreferdemo.R;

public class JobDetail extends AppCompatActivity {

    TextView txtTitle, txtCompany, txtDate, txtAddress, txtDescription, txtSalarytype;

    private ShareActionProvider mShareActionProvider;
    StringBuffer shareString=new StringBuffer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtTitle= (TextView) findViewById(R.id.title);
        txtCompany= (TextView) findViewById(R.id.company);
        txtDate= (TextView) findViewById(R.id.date);
        txtAddress= (TextView) findViewById(R.id.address);
        txtDescription= (TextView) findViewById(R.id.description);
        txtSalarytype= (TextView) findViewById(R.id.salarytype);

        Intent ob=getIntent();

        txtTitle.setText(ob.getStringExtra("title"));
        txtCompany.setText(ob.getStringExtra("company"));
        txtDate.setText(ob.getStringExtra("date"));
        txtAddress.setText(ob.getStringExtra("address"));
        txtDescription.setText(ob.getStringExtra("description"));
        txtSalarytype.setText(ob.getStringExtra("salarytype"));

        shareString.append("Job Requirement").append("\n");
        shareString.append("Job Title : ").append(ob.getStringExtra("title")).append("\n");
        shareString.append("Company : ").append(ob.getStringExtra("company")).append("\n");
        shareString.append("Date : ").append(ob.getStringExtra("date")).append("\n");
        shareString.append("Salary Type : ").append(ob.getStringExtra("salarytype")).append("\n");
        shareString.append("Description : ").append(ob.getStringExtra("description")).append("\n");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_detail, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat
                .getActionProvider(item);

        mShareActionProvider.setShareIntent(createShareIntent());

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

    private Intent createShareIntent() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareString.toString());
        sendIntent.setType("text/plain");
        return sendIntent;
    }

}
