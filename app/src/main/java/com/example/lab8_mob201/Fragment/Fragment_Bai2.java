package com.example.lab8_mob201.Fragment;

import android.content.ContentResolver;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.lab8_mob201.R;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Fragment_Bai2 extends Fragment {
    private TextView tvName, tvStartTime, tvEndTime;
    private SeekBar sbMusic;
    private ImageButton ibStop, ibStart, ibPause;
    private MediaPlayer mediaPlayer;
    private double startTime = 0;
    private double finalTime = 0;
    private Handler handler;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    public static int oneTimeOnly = 0;
    private ListView lvMusic;
    private List<String> stringList = new ArrayList<>();
    private List<Uri> uriList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__bai2, container, false);
        this.initViewById(view);
        this.initHanderAndMeadiaPlayer();
        this.initButton();
        this.initListView();
        return view;
    }

    private void initListView() {
        getListRaw();
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, stringList);
        this.lvMusic.setAdapter(stringArrayAdapter);
        this.lvMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mediaPlayer.reset();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String name = stringList.get(position);
                tvName.setText(name);
                Uri uri = getRawUri(name);
                mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), uri);
                mediaPlayer.start();
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();
                if (oneTimeOnly == 0) {
                    sbMusic.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }
                tvStartTime.setText(String.format("%d phút,%d giây",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                                - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
                tvEndTime.setText(String.format("%d phút,%d giây",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
                                - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));
                sbMusic.setProgress((int) startTime);
                handler.postDelayed(UpdateSongTime, 100);
                ibPause.setClickable(true);
                ibStart.setClickable(false);
            }
        });
    }

    private void initButton() {
        this.ibStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Bắt đầu phát", Toast.LENGTH_LONG).show();
                mediaPlayer.start();
                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();
                if (oneTimeOnly == 0) {
                    sbMusic.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }
                tvStartTime.setText(String.format("%d phút,%d giây",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                                - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
                tvEndTime.setText(String.format("%d phút,%d giây",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime)
                                - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) finalTime))));
                sbMusic.setProgress((int) startTime);
                handler.postDelayed(UpdateSongTime, 100);
                ibPause.setClickable(true);
                ibStart.setClickable(false);
            }
        });
        this.ibPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Tạm dừng bài hát", Toast.LENGTH_LONG).show();
                mediaPlayer.pause();
                ibPause.setClickable(false);
                ibStart.setClickable(true);
            }
        });
        this.ibStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int) startTime;
                if ((temp + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    Toast.makeText(getActivity(), "không thể nhảy 5 giây tiếp", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void getListRaw() {
        Field[] fields = R.raw.class.getFields();
        for (int i = 0; i < fields.length; i++) {
            stringList.add(fields[i].getName());
            Uri uri = getRawUri(fields[i].getName());
        }
    }

    private Runnable UpdateSongTime = new Runnable() {
        @Override
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tvStartTime.setText(String.format("%d phút,%d giây",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime)
                            - TimeUnit.MILLISECONDS.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) startTime))));
            sbMusic.setProgress((int) startTime);
            handler.postDelayed(this, 100);
        }
    };

    private void initHanderAndMeadiaPlayer() {
        String url = "https://hungnttg.github.io/aksmm.mp3";
        this.sbMusic.setClickable(false);
        this.ibPause.setClickable(false);
        this.handler = new Handler();
        mediaPlayer = MediaPlayer.create(getActivity().getApplicationContext(), Uri.parse(url));
    }

    private Uri getRawUri(String fileName) {
        return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + File.pathSeparator
                + File.separator
                + File.separator
                + getActivity().getPackageName() + "/raw/" + fileName);
    }

    private void initViewById(View view) {
        this.tvName = view.findViewById(R.id.tv_name);
        this.sbMusic = view.findViewById(R.id.sb_music);
        this.ibPause = view.findViewById(R.id.ib_pause);
        this.ibStart = view.findViewById(R.id.ib_play);
        this.ibStop = view.findViewById(R.id.ib_stop);
        this.tvEndTime = view.findViewById(R.id.tv_endTime);
        this.tvStartTime = view.findViewById(R.id.tv_startTime);
        this.lvMusic = view.findViewById(R.id.lv_music);
    }
}