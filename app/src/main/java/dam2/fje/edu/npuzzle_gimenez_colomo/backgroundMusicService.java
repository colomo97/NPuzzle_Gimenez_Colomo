package dam2.fje.edu.npuzzle_gimenez_colomo;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;

/**
 * Created by miquel on 2/6/2017.
 */

public class backgroundMusicService extends IntentService{
    static MediaPlayer musicaFons;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public backgroundMusicService() {
        super("backgroundMusicService");
    }

    public IBinder onBind(Intent arg0) {
            return null;
        }

    @Override
        protected void onHandleIntent(Intent intent) {
           musicaFons = MediaPlayer.create(this, R.raw.backgroundsound2);
           musicaFons.setLooping(true);
           musicaFons.setVolume(100, 100);
           musicaFons.start();
    }

    public static void play(){
        musicaFons.start();
    }

    public static void pause(){
        musicaFons.pause();
    }

    public static void stop(){
        musicaFons.stop();
    }

}

