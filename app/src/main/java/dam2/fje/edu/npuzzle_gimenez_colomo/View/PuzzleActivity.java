package dam2.fje.edu.npuzzle_gimenez_colomo.View;
/**
 * Created by David on 10/02/2017.
 */

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Point;
import android.os.Bundle;
import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import dam2.fje.edu.npuzzle_gimenez_colomo.Controller.BackgroundMusicService;
import dam2.fje.edu.npuzzle_gimenez_colomo.R;

public class PuzzleActivity extends AppCompatActivity implements View.OnTouchListener{
    Integer[] imageIDs = {
            R.drawable.img01,
            R.drawable.img02,
            R.drawable.img03,
            R.drawable.img04,
            R.drawable.img05,
            R.drawable.img06,
            R.drawable.img07,
            R.drawable.img08,
            R.drawable.img09
    };

    ImageView solucio;
    Button btnSolucio;
    boolean imageSelector = true;
    GridView grid;
    boolean firstTime = true;
    boolean mBound = false;
    static Menu menuOnRestart;
    BackgroundMusicService mService;
    ImageAdapter im;
    ViewParent pare;
    int posicioInvisible = 8;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        Intent svc = new Intent(this, BackgroundMusicService.class);

        //Obtenir dimensions imatges
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Toast.makeText(this, "x: " + String.valueOf(width) + "; y: " + String.valueOf(height), Toast.LENGTH_SHORT).show();
        btnSolucio = (Button) findViewById(R.id.btnSolucio);
        btnSolucio.setOnTouchListener(this);
        solucio = (ImageView) findViewById(R.id.imageView);
        solucio.setVisibility(View.GONE);
        grid = (GridView) findViewById(R.id.gridview);
        im = new ImageAdapter(this);
        grid.setAdapter(im);
        mService = PrincipalActivity.getmService();
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        grid.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,
                                    View v, int position, long id)
            {
                boolean disponible = comprovaDisponible((CustomImageView)v);
                //Toast.makeText(getBaseContext(), "Imatge " + (position + 1) + " seleccionada", Toast.LENGTH_SHORT).show();
                //Toast.makeText(getBaseContext(), String.valueOf(disponible), Toast.LENGTH_SHORT).show();
                System.out.println(disponible);

                ImageView invisible = (ImageView)parent.getChildAt(8);

                if (disponible){
                    float xActual = v.getX();
                    float yActual = v.getY();
                    float xInvisible = invisible.getX();
                    float yInvisible = invisible.getY();
                    v.setX(xInvisible);
                    v.setY(yInvisible);
                    invisible.setX(xActual);
                    invisible.setY(yActual);
                    int posicioAuxiliar = ((CustomImageView) v).getPosicioActual();
                    ((CustomImageView) v).setPosicioActual(posicioInvisible);
                    posicioInvisible = posicioAuxiliar;
                }
            }
        });
    }

    public boolean comprovaDisponible(CustomImageView v){
        System.out.println("POSICIO ACTUAL: " + v.getPosicioActual() + " POSICIO CORRECTE: " + v.getPosicioCorrecte());
        System.out.println("POSICIO INVISIBLE: " + posicioInvisible);
        int columna = v.getPosicioActual() % 3;
        int fila = v.getPosicioActual() / 3;

        int columnaInvisible = posicioInvisible % 3;
        int filaInvisible = posicioInvisible / 3;

        if ((columna == columnaInvisible && fila-1 == filaInvisible || columna == columnaInvisible && fila+1 == filaInvisible) || (columna-1 == columnaInvisible && fila == filaInvisible || columna+1 == columnaInvisible && fila == filaInvisible)){
            return true;
        } else{
            return false;
        }

    }

    /*
    @Override
    public void onClick(View v) {
        if (solucio.getVisibility() == View.GONE){
            solucio.setVisibility(View.VISIBLE);
        } else {
            solucio.setVisibility(View.GONE);
        }
    }
    */

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                solucio.setVisibility(View.VISIBLE);
                break;
            case MotionEvent.ACTION_UP:
                solucio.setVisibility(View.GONE);
                break;
        }

        return false;
    }

    public class ImageAdapter extends BaseAdapter{
        private Context context;

        public ImageAdapter(Context c)
        {
            context = c;
        }

        //---returns the number of images---
        public int getCount() {
            return imageIDs.length;
        }

        //---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent){
            pare = parent;
            CustomImageView imageView;
            if (convertView == null) {
                imageView = new CustomImageView(context, position);
                imageView.setLayoutParams(new GridView.LayoutParams(415, 415));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
            } else {
                imageView = (CustomImageView) convertView;
            }
            if(position==8){
                imageView.setVisibility(View.GONE);
            }
            imageView.setPosicioCorrecte(position);
            imageView.setPosicioActual(position);
            imageView.setImageResource(imageIDs[position]);
            return imageView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.menuOnRestart = menu;
        if(PrincipalActivity.checkMusic()){menuOnRestart.getItem(0).setIcon(R.drawable.mute);}
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
        mService.stop();
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

    public View getActionBarView() {
        Window window = getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }
    public static  boolean checkMusic(){
        if(menuOnRestart.getItem(0).getTitle()=="Mute") return true;
        else return false;
    }
}