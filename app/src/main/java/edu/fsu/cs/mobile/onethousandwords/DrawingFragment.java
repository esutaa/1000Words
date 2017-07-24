package edu.fsu.cs.mobile.onethousandwords;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawingFragment extends Fragment implements View.OnClickListener{
    private DrawingView drawingView;
    private ImageButton currentPaint;
    private ImageButton b1;
    private ImageButton b2;
    private ImageButton b3;
    private ImageButton b4;
    private ImageButton b5;
    private ImageButton b6;
    private ImageButton b7;
    private ImageButton b8;
    private ImageButton b9;
    private ImageButton b10;
    private ImageButton b11;
    private ImageButton b12;
    
    public DrawingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_drawing, container, false);

        drawingView = (DrawingView) rootView.findViewById(R.id.draw);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.brush_color);
        currentPaint = (ImageButton) layout.getChildAt(0);

        currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));

        b1 = (ImageButton) rootView.findViewById(R.id.c1);
        b1.setOnClickListener(this);
        b2 = (ImageButton) rootView.findViewById(R.id.c2);
        b2.setOnClickListener(this);
        b3 = (ImageButton) rootView.findViewById(R.id.c3);
        b3.setOnClickListener(this);
        b4 = (ImageButton) rootView.findViewById(R.id.c4);
        b4.setOnClickListener(this);
        b5 = (ImageButton) rootView.findViewById(R.id.c5);
        b5.setOnClickListener(this);
        b6 = (ImageButton) rootView.findViewById(R.id.c6);
        b6.setOnClickListener(this);
        b7 = (ImageButton) rootView.findViewById(R.id.c7);
        b7.setOnClickListener(this);
        b8 = (ImageButton) rootView.findViewById(R.id.c8);
        b8.setOnClickListener(this);
        b9 = (ImageButton) rootView.findViewById(R.id.c9);
        b9.setOnClickListener(this);
        b10 = (ImageButton) rootView.findViewById(R.id.c10);
        b10.setOnClickListener(this);
        b11 = (ImageButton) rootView.findViewById(R.id.c11);
        b11.setOnClickListener(this);
        b12 = (ImageButton) rootView.findViewById(R.id.c12);
        b12.setOnClickListener(this);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(View v) {
        //update color
        if (v != currentPaint){
            //bc.onDrawingButtonClicked(view);
            ImageButton img = (ImageButton) v;
            String color = v.getTag().toString();
            drawingView.setColor(color);

            img.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));
            currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.paint, null));
            currentPaint = (ImageButton) v;
        }

    }

}
