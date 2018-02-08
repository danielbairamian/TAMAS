package ca.mcgill.ecse321.tamas;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by User on 2017-02-21.
 */

public class MainJobPosting extends Fragment {

        public MainJobPosting(){

        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the laysout for this fragment
            return inflater.inflate(R.layout.job_postings, container, false);
        }
}
