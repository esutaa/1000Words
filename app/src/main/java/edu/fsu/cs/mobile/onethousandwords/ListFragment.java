package edu.fsu.cs.mobile.onethousandwords;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {

    Button addButton;

    public ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        addButton = (Button) rootView.findViewById(R.id.button_add);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawingFragment fragment = new DrawingFragment();
                String tag = DrawingFragment.class.getCanonicalName();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_frame, fragment, tag).commit();
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

}
