package edu.fsu.cs.mobile.onethousandwords;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static android.R.attr.data;

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
    private Random random;
    private Button upload;
    private String RandomAudioFileName = "asdfasd";
        //colors
    private ImageButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12;

    private ImageButton brushBtn, eraseBtn, newBtn, saveBtn;
    Button logoutBtn;
        //brush sizes
    private float smallBrush, medBrush, medBrush2, lgBrush;
    String savedImg;
    FirebaseAuth auth;

    public DrawingFragment() {
        // Required empty public constructor
    }

    public static DrawingFragment newInstance() {
        DrawingFragment fragment = new DrawingFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
        upload = (Button) rootView.findViewById(R.id.upload_btn);
        random = new Random();
        play.setEnabled(false);

            //show what is currently pressed
        currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));

        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if audio is recording, change button to stop and begin recording
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

                    } else {    // set button to record if recording has stopped
                        record.setImageResource(R.drawable.record);
                        mediaRecorder.stop();
                    }

                    playing = !playing;
                }
                else {  // call function to request runtime permissions
                    requestPermission();
                }
            }
        });

        // playback button onClick listener - opens recording filepath in media player
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

        // onClick listener for upload - prompts user to select photo from gallery
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
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
        logoutBtn = (Button) rootView.findViewById(R.id.logout_btn);
        logoutBtn.setOnClickListener(this);
        saveBtn = (ImageButton) rootView.findViewById(R.id.save_btn);
        saveBtn.setOnClickListener(this);

        smallBrush = getResources().getInteger(R.integer.small_size);
        medBrush = getResources().getInteger(R.integer.medium_size);
        medBrush2 = getResources().getInteger(R.integer.med2_size);
        lgBrush = getResources().getInteger(R.integer.large_size);

        drawingView.setBrushSize(medBrush);
        // Inflate the layout for this fragment
        return rootView;
    }

    // onClick listener for upload button calls this
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            String path = getPathFromCameraData(data, this.getActivity());
            Log.i("PICTURE", "Path: " + path);

            // converts selected gallery photo to a drawable and sets as background
            if (path != null) {
                Drawable photo = Drawable.createFromPath(path);
                drawingView.setBackground(photo);
            }
        }
    }

    // helper function for gallery selection
    public static String getPathFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        return picturePath;
    }

    @Override
    public void onClick(View v) {
        //Checking to see which color button was chosen to update color choice
        if ((v != currentPaint) && (v.getId() != R.id.brush_btn) && (v.getId() != R.id.erase_btn)
                && (v.getId() != R.id.draw_btn) && (v.getId() != R.id.logout_btn) && (v.getId() != R.id.save_btn)){
            drawingView.setErase(false);    //make sure it's not erasing
                //if they erased before use previous brush size
            drawingView.setBrushSize(drawingView.getOldBrushSize());
            ImageButton img = (ImageButton) v;  //get the color
            String color = v.getTag().toString();   //get tag color
            drawingView.setColor(color);    //set the color

                //show which color is picked
            img.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.pressed, null));
            currentPaint.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.paint, null));
            currentPaint = (ImageButton) v;
        }

        // Checking for which buttons were used
        if (v.getId() == R.id.brush_btn)
        {
                //changes brush size for drawing
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
                //changes brush size for coloring
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
                //create a new drawing and discard the previous one
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("New Drawing").setMessage("Discard Changes?");
            alertDialog.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    drawingView.setBackgroundResource(R.color.whitebg);
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
        else if (v.getId() == R.id.logout_btn){
                //Logs the user out and goes back to the login page
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
            alertDialog.setTitle("").setMessage("Logout and discard changes?");
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

        //make sure the necessary permissions are granted
    public boolean permission(){
        int res1 = ContextCompat.checkSelfPermission(getContext(),
                "android.permission.WRITE_EXTERNAL_STORAGE");
        int res2 = ContextCompat.checkSelfPermission(getContext(),
                "android.permission.RECORD_AUDIO");

        return (res1 == PackageManager.PERMISSION_GRANTED) && (res2 == PackageManager.PERMISSION_GRANTED);
    }

        //request permission if not granted
    private void requestPermission(){
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.RECORD_AUDIO"},
                36);
    }

}
