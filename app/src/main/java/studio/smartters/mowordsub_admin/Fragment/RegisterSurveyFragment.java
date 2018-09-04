package studio.smartters.mowordsub_admin.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.smartters.mowordsub_admin.R;

public class RegisterSurveyFragment extends Fragment {

    public RegisterSurveyFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_register_survey, container, false);
    }

}
