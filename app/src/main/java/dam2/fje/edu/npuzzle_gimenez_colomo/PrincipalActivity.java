package dam2.fje.edu.npuzzle_gimenez_colomo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PrincipalActivity extends AppCompatActivity {
    static private boolean imageSelector = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Intent svc = new Intent(this, backgroundMusicService.class);
        startService(svc);
    }

    /**
     * Redifinició del mètode per a crear el memú d'opcions.
     * @param menu
     * @return menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Mètode per a saber l'item seleccionat del menú
     * @param item
     * @return Retorna la selecció escollida
     */


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
        switch (item.getItemId()){
            case R.id.item_sound:
                if(imageSelector){
                    item.setIcon(R.drawable.mute);
                    item.setTitle("Mute");
                    stopService(svc);
                    imageSelector = false;
                }else{
                    item.setIcon(R.drawable.volumen_up);
                    item.setTitle("Music");
                    startService(svc);
                    imageSelector = true;
                }
                return true;

            case R.id.goBack:
                finish();
                return  true;

            default:
                return super.onOptionsItemSelected(item);
        }
        */

        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPause() {
        super.onPause();
        //stopService(svc);
    }

    @Override
    public void onResume() {
        super.onPause();
        //startService(svc);

        }
}