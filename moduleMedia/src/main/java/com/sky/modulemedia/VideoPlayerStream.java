package com.sky.modulemedia;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.media.MediaPlayer;

import java.io.IOException;

public class VideoPlayerStream extends Activity implements View.OnClickListener,SurfaceHolder.Callback,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnVideoSizeChangedListener,
        MediaPlayer.OnErrorListener,
        MediaPlayer.OnInfoListener
{

    private static final String TAG = "VideoStream";
    private static final String VIDEO_PATH_LOCAL = "/sdcard/samplevideo.3gp";
    private static final String VIDEO_PATH_STREAM = "rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";

    private Context mContext;

    private MediaPlayer mediaPlayer;
    private Display display;
    private int videoWidth = 0;
    private int videoHeight = 0;
    private boolean isReadyToPlay = false;


    private SurfaceHolder surfaceHolder;
    private SurfaceView surfaceView;
    private SeekBar seekBar;
    private Button btnPrev, btnPlayPause, btnNext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate() >>>>>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_stream);

        init();

        //TODO
    }

    private void init() {
        Log.d(TAG, "init() >>>>>");
        mContext = this;
        // TODO


        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnInfoListener(this);


        seekBar = (SeekBar)findViewById(R.id.seekBar);
        btnPrev = (Button)findViewById(R.id.btnPrev);
        btnPlayPause = (Button)findViewById(R.id.btnPlayPause);
        btnNext = (Button)findViewById(R.id.btnNext);
        btnPrev.setOnClickListener(this);
        btnPlayPause.setOnClickListener(this);
        btnNext.setOnClickListener(this);

        surfaceView = (SurfaceView)findViewById(R.id.surfaceView);
        surfaceView.setKeepScreenOn(true);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        display = getWindowManager().getDefaultDisplay();
    }



    @Override
    protected void onResume() {
        Log.d(TAG, "onResume() >>>>>");
        super.onResume();
        // TODO
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause() >>>>>");
        super.onPause();
        // TODO
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy() >>>>>");
        super.onDestroy();
        // TODO
        mediaPlayer.release();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed() >>>>>");
        super.onBackPressed();
        // TODO
    }


    // ************************************************************
    // Methods
    // ************************************************************



    // ************************************************************
    // OnClickListener
    // ************************************************************
    @Override
    public void onClick(View v) {
        // TODO

        switch (v.getId()) {
            case R.id.btnPrev:
                break;

            case R.id.btnPlayPause:
                break;

            case R.id.btnNext:
                break;
        }

    }

    //****************************************
    //  Mediaplayer Callbacks
    //****************************************
    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.d(TAG,"onPrepared() >>>");
        /*
        videoWidth = mediaPlayer.getVideoWidth();
        videoHeight = mediaPlayer.getVideoHeight();
        Point point = new Point();
        display.getSize(point);
        if (videoWidth > point.x || videoHeight > point.y) {
            float heightRatio = (float) videoHeight
                    / point.x;
            float widthRatio = (float) videoWidth
                    / point.y;


            if (heightRatio > widthRatio) {
                videoHeight = (int) Math.ceil((float) videoHeight
                        / (float) heightRatio);
                videoWidth = (int) Math.ceil((float) videoWidth
                        / (float) heightRatio);
            } else {
                videoHeight = (int) Math.ceil((float) videoHeight
                        / (float) widthRatio);
                videoWidth = (int) Math.ceil((float) videoWidth
                        / (float) widthRatio);
            }

        }

        surfaceView.setLayoutParams(new LinearLayout.LayoutParams(videoWidth, videoHeight));*/

        mediaPlayer.start();
    }


    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        Log.d(TAG,"onCompletion() >>>");
    }


    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        Log.d(TAG, "onSeekComplete() >>>");

    }


    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, int percent) {
        Log.d(TAG,"onBufferingUpdate() >>> percent = " + percent);
    }


    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        Log.d(TAG,"onVideoSizeChanged() >>> width = " + width + " height = " + height);

    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {

        String errorType = "--";
        String extraCode = "--";
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                errorType = "MEDIA_ERROR_UNKNOWN";
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                errorType = "MEDIA_ERROR_SERVER_DIED";
                break;
            default:
                break;
        }

        switch (extra) {
            case MediaPlayer.MEDIA_ERROR_IO:
                extraCode = "MEDIA_ERROR_IO";
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                extraCode = "MEDIA_ERROR_MALFORMED";
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                extraCode = "MEDIA_ERROR_UNSUPPORTED";
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                extraCode = "MEDIA_ERROR_TIMED_OUT";
                break;
            case -2147483648:
                extraCode = "MEDIA_ERROR_SYSTEM";
                break;
            default:
                break;
        }

        Log.d(TAG, "onError() >>> errorType = " + errorType + " extraCode = " + extraCode);
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, int what, int extra) {
        String infoType = "--";

        switch (what) {
            case MediaPlayer.MEDIA_INFO_UNKNOWN:
                infoType = "MEDIA_INFO_UNKNOWN";
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
                infoType = "MEDIA_INFO_VIDEO_TRACK_LAGGING";
                break;
            case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                infoType = "MEDIA_INFO_VIDEO_RENDERING_START";
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                infoType = "MEDIA_INFO_BUFFERING_START";
                break;
            case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                infoType = "MEDIA_INFO_BUFFERING_END";
                break;
            case 703:
                infoType = "MEDIA_INFO_NETWORK_BANDWIDTH";
                break;
            case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
                infoType = "MEDIA_INFO_BAD_INTERLEAVING";
                break;
            case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
                infoType = "MEDIA_INFO_NOT_SEEKABLE";
                break;
            case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
                infoType = "MEDIA_INFO_METADATA_UPDATE";
                break;
            case MediaPlayer.MEDIA_INFO_UNSUPPORTED_SUBTITLE:
                infoType = "MEDIA_INFO_UNSUPPORTED_SUBTITLE";
                break;
            case MediaPlayer.MEDIA_INFO_SUBTITLE_TIMED_OUT:
                infoType = "MEDIA_INFO_SUBTITLE_TIMED_OUT";
                break;
            default:
                break;
        }

        Log.d(TAG, "onInfo() >>> infoType = " + infoType + " extraCode = " + extra);
        return false;
    }





    //****************************************
    //  SurfaceHolder Callbacks
    //****************************************
    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated() >>>");
        mediaPlayer.setDisplay(surfaceHolder);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(VIDEO_PATH_STREAM);
            mediaPlayer.prepareAsync();
        } catch (IllegalArgumentException e) {
            Log.d(TAG, "[IllegalArgumentException] " + e.getMessage());
        } catch (IllegalStateException e) {
            Log.d(TAG, "[IllegalStateException] " + e.getMessage());
        }catch(SecurityException e){
            Log.d(TAG, "[SecurityException] " + e.getMessage());
        } catch(IOException e) {
            Log.d(TAG, "[IOException] " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged() >>> width = " + width + " height = " + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed() >>>");
        mediaPlayer.release();
    }
}