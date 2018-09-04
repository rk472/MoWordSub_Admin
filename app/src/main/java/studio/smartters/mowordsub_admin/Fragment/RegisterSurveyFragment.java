package studio.smartters.mowordsub_admin.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.smartters.mowordsub_admin.R;

public class RegisterSurveyFragment extends Fragment {
    private View root;
    private AppCompatActivity main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register_survey, container, false);
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("Register Now");
        return root;
    }

}
