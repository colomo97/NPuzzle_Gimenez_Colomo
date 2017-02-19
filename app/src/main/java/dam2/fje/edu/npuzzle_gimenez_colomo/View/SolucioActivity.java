package dam2.fje.edu.npuzzle_gimenez_colomo.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import dam2.fje.edu.npuzzle_gimenez_colomo.Controller.BackgroundMusicService;
import dam2.fje.edu.npuzzle_gimenez_colomo.R;

/**
 * Created by David on 18/02/2017.
 */

public class SolucioActivity extends AppCompatActivity{
    int numMoviments;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solucio);
        Intent intent = getIntent();
        numMoviments = intent.getIntExtra("movements", 0);
        TextView tvNumMoviments = (TextView) findViewById(R.id.tvNumMoviments);
        tvNumMoviments.setText(String.valueOf(numMoviments));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.removeItem(R.id.item_sound);
        menu.removeItem(R.id.item_settings);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.goBack:
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
