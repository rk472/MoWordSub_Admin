package studio.smartters.mowordsub_admin.Fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studio.smartters.mowordsub_admin.Dialog.CreateDialogBooth;
import studio.smartters.mowordsub_admin.R;

public class ViewBoothFragment extends Fragment {
    private View root;
    private AppCompatActivity main;
    private FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_view_booth, container, false);
        main = (AppCompatActivity) getActivity();
        main.getSupportActionBar().setTitle("View Booth");

        fab = root.findViewById(R.id.add_booth_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CreateDialogBooth dialog= new CreateDialogBooth(main);
                dialog.show();
            }
        });

        return root;
    }

}
