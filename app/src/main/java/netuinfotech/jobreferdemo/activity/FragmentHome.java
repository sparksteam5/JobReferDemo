package netuinfotech.jobreferdemo.activity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import netuinfotech.jobreferdemo.R;

/**
 * Created by Jay Mataji on 3/24/2016.
 */
public class FragmentHome extends Fragment {

    TextView txt,txt1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(R.layout.fragment_home, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        txt=(TextView)getView().findViewById(R.id.txt);

        Intent ob=getActivity().getIntent();

        txt.setText("Welcome " + ob.getStringExtra("uname"));

    }
}
