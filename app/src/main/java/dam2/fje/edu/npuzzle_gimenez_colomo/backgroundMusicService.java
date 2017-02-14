package dam2.fje.edu.npuzzle_gimenez_colomo;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;

/**
 * Created by miquel on 2/6/2017.
 */

public class backgroundMusicService extends IntentService implements  MediaPlayer.OnPreparedListener{
    MediaPlayer musicaFons ;
    int currPosition = 0;
    IBinder iBinder = new LocalBinder();

    public class LocalBinder extends Binder{public  backgroundMusicService getService(){return backgroundMusicService.this;}}

    public IBinder onBind(Intent arg0) {
        musicaFons = MediaPlayer.create(this, R.raw.backgroundsound2);
        musicaFons.setLooping(true);
        musicaFons.setVolume(100, 100);
        musicaFons.setOnPreparedListener(this);
        return iBinder;
    }

    public backgroundMusicService() {super("backgroundMusicService");}

    @Override
    protected void onHandleIntent(Intent intent){}

    @Override
    public void onPrepared(MediaPlayer mp) {mp.start();}

    public  void play(){musicaFons.start();}

    public  void stop(){getCurrentPosition();musicaFons.pause();}

    public  void getCurrentPosition(){currPosition = musicaFons.getCurrentPosition();}
}
