package edu.fsu.cs.mobile.onethousandwords;


import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.UUID;


public class DrawingFragment extends Fragment implements View.OnClickListener{

    private DrawingView drawingView;
    private ImageButton currentPaint;
    private MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private boolean playing = true;
    private ImageButton record;
    private ImageButton play;
    private String savePath = null;
    private Random random;
    private String RandomAudioFileName = "asdfasd";
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
    //private Button brushBtn, eraseBtn, newBtn, backBtn;
  
    private ImageButton brushBtn, eraseBtn, newBtn, saveBtn;
    Button backBtn, logoutBtn;
    private float smallBrush, medBrush, medBrush2, lgBrush;
    String savedImg;
    FirebaseAuth auth;
    //ImageButton smallBtn, medBtn, med2Btn, lgBtn;
    
    public DrawingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_drawing, container, false);

        auth = FirebaseAuth.getInstance();
        drawingView = (DrawingView) rootView.findViewById(R.id.draw);
        LinearLayout layout = (LinearLayout) rootView.findViewById(R.id.brush_color);
        currentPaint = (ImageButton) layout.getChildAt(0);
        record = (ImageButton) rootView.findViewById(R.id.record_btn);
        play = (ImageButton) rootView.findViewById(R.id.play_btn);
        random = new Random();
        play.setEnabled(false);

        currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (permission()) {
                    if (playing) {
                        play.setEnabled(true);
                        record.setImageResource(R.drawable.stop);
                        savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                CreateRandomAudioFileName(5) + "Recording.3gp";

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
                    } else {
                        record.setImageResource(R.drawable.record);
                        mediaRecorder.stop();
                    }

                    playing = !playing;
                }
                else {
                    requestPermission();
                }
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
        saveBtn = (ImageButton) rootView.findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);
        logoutBtn = (Button) rootView.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(this);

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
                && (v.getId() != R.id.draw_btn) && (v.getId() != R.id.back_btn) && (v.getId() != R.id.save_btn)
                && (v.getId() != R.id.logout_btn)){
            drawingView.setErase(false);
            drawingView.setBrushSize(drawingView.getOldBrushSize());
            ImageButton img = (ImageButton) v;
            String color = v.getTag().toString();
            drawingView.setColor(color);

            img.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));
            currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.paint, null));
            currentPaint = (ImageButton) v;
        }

        // Checking for which buttons were used
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

        // Save button saves file to gallery
        else if (v.getId() == R.id.save_btn) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("").setMessage("Would you like to save your work?");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Checking to see if user already gave permission
                    if (ContextCompat.checkSelfPermission(getContext(),
                            "android.permission.WRITE_EXTERNAL_STORAGE")
                            == PackageManager.PERMISSION_GRANTED) {
                        savedImg = getUri();
                    }
                    // Asking user to have permission to save files to gallery
                    else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"},
                                36);
                        if (ContextCompat.checkSelfPermission(getContext(),
                                "android.permission.WRITE_EXTERNAL_STORAGE")
                                == PackageManager.PERMISSION_GRANTED) {
                            savedImg = getUri();
                        }
                        // If not allowed, tell user to enable permission
                        else {
                            Toast.makeText(getContext(), "Please enable permissions to save file.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    // Displaying to user if file was saved successfully
                    if(savedImg != null) {
                        /*StorageReference storageRef = FirebaseStorage.getReference().child("Users").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid());
                        storageRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).putFile(savedImg);*/
                        Toast.makeText(getActivity(), "Drawing saved!", Toast.LENGTH_SHORT).show();
                        drawingView.destroyDrawingCache();  // Deleting cache so cache not the same for future files
                    }

                    else {
                        Toast.makeText(getActivity(), "Uh oh! Image not saved. :(", Toast.LENGTH_SHORT).show();
                    }
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

        else if (v.getId() == R.id.logout_btn) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("").setMessage("Go back and discard changes?");
            alertDialog.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    auth.signOut();
                    LoginFragment loginFragment = new LoginFragment();
                    String tag = LoginFragment.class.getCanonicalName();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_frame, loginFragment, tag).commit();
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

    // Function gets URI of file
    public String getUri() {
        drawingView.setDrawingCacheEnabled(true);

        return MediaStore.Images.Media.insertImage(
                getActivity().getContentResolver(), drawingView.getDrawingCache(),
                UUID.randomUUID().toString()+".png", "1000Words");
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    public boolean permission(){
        int res1 = ContextCompat.checkSelfPermission(getContext(),
                "android.permission.WRITE_EXTERNAL_STORAGE");
        int res2 = ContextCompat.checkSelfPermission(getContext(),
                "android.permission.RECORD_AUDIO");

        return (res1 == PackageManager.PERMISSION_GRANTED) && (res2 == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"},
                36);
    }

}
