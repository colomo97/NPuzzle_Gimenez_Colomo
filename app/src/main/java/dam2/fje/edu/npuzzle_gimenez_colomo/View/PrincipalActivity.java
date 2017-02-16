package dam2.fje.edu.npuzzle_gimenez_colomo.View;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import dam2.fje.edu.npuzzle_gimenez_colomo.Controller.BackgroundMusicService;
import dam2.fje.edu.npuzzle_gimenez_colomo.R;

public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    boolean mBound = false;
    boolean imageSelector = true;
    boolean firstTime = true;
    boolean checkmService;
    Button startGame;
    static Menu menuOnRestart;
    static BackgroundMusicService mService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent svc = new Intent(this, BackgroundMusicService.class);
        bindService(svc, mConnection, Context.BIND_AUTO_CREATE);
        startGame = (Button) findViewById(R.id.bStart);
        startGame.setOnClickListener(this);
        checkmService = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.menuOnRestart = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_sound:
                if(imageSelector){
                    item.setIcon(R.drawable.mute);
                    item.setTitle("Mute");
                    mService.stop();
                    imageSelector = false;
                }else{
                    item.setIcon(R.drawable.volumen_up);
                    item.setTitle("Music");
                    mService.play();
                    imageSelector = true;
                }
                return true;

            case R.id.goBack:
                finish();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mConnection);
            mBound = false;
        }
        if(checkmService ==true){
            menuOnRestart.getItem(0).setIcon(R.drawable.mute);
            mService.stop();
        }

    }


    @Override
    public void onResume(){
        super.onResume();
        if(firstTime){
            firstTime = false;
        }else{
              if(menuOnRestart.getItem(0).getTitle()=="Mute"){
              mService.stop();
              }else mService.play();
            }
    }



    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            BackgroundMusicService.LocalBinder binder = (BackgroundMusicService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }
        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case (R.id.bStart):
                checkmService = false;
                Intent intent = new Intent(this, PuzzleActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    public static  boolean checkMusic(){
        if(menuOnRestart.getItem(0).getTitle()=="Mute") return true;
        else return false;
    }
    public static BackgroundMusicService getmService(){
        return  mService;
    }
    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS:
                    if (mService.isPlating()){mService.stop();}
                    break;

                case AudioManager.AUDIOFOCUS_GAIN:
                    mService.play();
                    break;
            }
        }
    };
}