package studio.smartters.mowordsub_admin.Fragment;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.smartters.mowordsub_admin.MainActivity;
import studio.smartters.mowordsub_admin.R;

public class HomeFragment extends Fragment {
    private View v;
    private AppCompatActivity main;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_home, container, false);
        main = (AppCompatActivity)getActivity();
        main.getSupportActionBar().setTitle("Home");
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        NavigationView navigationView = (NavigationView) main.findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.nav_home);
    }
}
