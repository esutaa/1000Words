package edu.fsu.cs.mobile.onethousandwords;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrawingFragment extends Fragment implements View.OnClickListener{
    private DrawingView drawingView;
    private ImageButton currentPaint;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private boolean playing = true;
    private ImageButton record;
    private ImageButton play;
    private String savePath = null;
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
    private ImageButton brushBtn, eraseBtn, newBtn;
    Button backBtn;
    private float smallBrush, medBrush, medBrush2, lgBrush;
    //ImageButton smallBtn, medBtn, med2Btn, lgBtn;
    
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
        record = (ImageButton) rootView.findViewById(R.id.record_btn);
        play = (ImageButton) rootView.findViewById(R.id.play_btn);

        currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (playing) {
                    record.setImageResource(R.drawable.stop);
                    savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
                    mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    mediaRecorder.setOutputFile(savePath);

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                else {
                    record.setImageResource(R.drawable.record);
                    mediaRecorder.stop();
                }

                playing = !playing;
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = new MediaPlayer();
                try {
                    mediaPlayer.setDataSource(savePath);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        });

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

        brushBtn = (ImageButton) rootView.findViewById(R.id.brush_btn);
        brushBtn.setOnClickListener(this);
        eraseBtn = (ImageButton) rootView.findViewById(R.id.erase_btn);
        eraseBtn.setOnClickListener(this);
        newBtn = (ImageButton) rootView.findViewById(R.id.draw_btn);
        newBtn.setOnClickListener(this);
        backBtn = (Button) rootView.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(this);

        smallBrush = getResources().getInteger(R.integer.small_size);
        medBrush = getResources().getInteger(R.integer.medium_size);
        medBrush2 = getResources().getInteger(R.integer.med2_size);
        lgBrush = getResources().getInteger(R.integer.large_size);

        drawingView.setBrushSize(medBrush);
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onClick(View v) {
        //update color
        if ((v != currentPaint) && (v.getId() != R.id.brush_btn) && (v.getId() != R.id.erase_btn)
                && (v.getId() != R.id.draw_btn) && (v.getId() != R.id.back_btn)){
            drawingView.setErase(false);
            drawingView.setBrushSize(drawingView.getOldBrushSize());
            ImageButton img = (ImageButton) v;
            String color = v.getTag().toString();
            drawingView.setColor(color);

            img.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));
            currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.paint, null));
            currentPaint = (ImageButton) v;
        }

        if (v.getId() == R.id.brush_btn)
        {
            final Dialog brushDialog = new Dialog(getActivity());
            brushDialog.setTitle("Brush Size");
            brushDialog.setContentView(R.layout.choose_brush);

            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setBrushSize(smallBrush);
                    drawingView.setOldBrushSize(smallBrush);
                    drawingView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            ImageButton  medBtn = (ImageButton) brushDialog.findViewById(R.id.medium);
            medBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setBrushSize(medBrush);
                    drawingView.setOldBrushSize(medBrush);
                    drawingView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            ImageButton med2Btn = (ImageButton) brushDialog.findViewById(R.id.medium2);
            med2Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setBrushSize(medBrush2);
                    drawingView.setOldBrushSize(medBrush2);
                    drawingView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            ImageButton lgBtn = (ImageButton) brushDialog.findViewById(R.id.large);
            lgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setBrushSize(lgBrush);
                    drawingView.setOldBrushSize(lgBrush);
                    drawingView.setErase(false);
                    brushDialog.dismiss();
                }
            });

            brushDialog.show();
        }
        else if(v.getId() == R.id.erase_btn){
            final Dialog brushDialog = new Dialog(getActivity());
            brushDialog.setTitle("Eraser Size");
            brushDialog.setContentView(R.layout.choose_brush);

            ImageButton smallBtn = (ImageButton) brushDialog.findViewById(R.id.small);
            smallBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setErase(true);
                    drawingView.setBrushSize(smallBrush);
                    brushDialog.dismiss();
                }
            });

            ImageButton  medBtn = (ImageButton) brushDialog.findViewById(R.id.medium);
            medBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setErase(true);
                    drawingView.setBrushSize(medBrush);
                    brushDialog.dismiss();
                }
            });

            ImageButton med2Btn = (ImageButton) brushDialog.findViewById(R.id.medium2);
            med2Btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setErase(true);
                    drawingView.setBrushSize(medBrush2);
                    brushDialog.dismiss();
                }
            });

            ImageButton lgBtn = (ImageButton) brushDialog.findViewById(R.id.large);
            lgBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawingView.setErase(true);
                    drawingView.setBrushSize(lgBrush);
                    brushDialog.dismiss();
                }
            });

            brushDialog.show();
        }
        else if (v.getId() == R.id.draw_btn){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("New Drawing").setMessage("Discard Changes?");
            alertDialog.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    drawingView.newDraw();
                    dialog.dismiss();
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }
        else if (v.getId() == R.id.back_btn){
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("").setMessage("Go back and discard changes?");
            alertDialog.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    fm.popBackStackImmediate();
                }
            });

            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            alertDialog.show();
        }

    }

}
