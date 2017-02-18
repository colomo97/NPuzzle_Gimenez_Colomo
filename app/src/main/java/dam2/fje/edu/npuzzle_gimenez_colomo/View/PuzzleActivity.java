package dam2.fje.edu.npuzzle_gimenez_colomo.View;
/**
 * Created by David on 10/02/2017.
 */

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.media.AudioManager;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import dam2.fje.edu.npuzzle_gimenez_colomo.Controller.BackgroundMusicService;
import dam2.fje.edu.npuzzle_gimenez_colomo.R;

public class PuzzleActivity extends AppCompatActivity implements View.OnTouchListener{
    PuzzleActivity(){}
    Integer[] imageIDs = {R.drawable.img01, R.drawable.img02,R.drawable.img03,R.drawable.img04,R.drawable.img05,R.drawable.img06,R.drawable.img07,R.drawable.img08, R.drawable.img09};
    ImageView solucio;
    Button btnSolucio;
    GridView grid;
    BackgroundMusicService mService;
    ImageAdapter im;
    ViewParent pare;
    List<Integer> posicionsAleatoriesList;
    int posicioInvisible = 8;
    boolean checkmService;
    boolean imageSelector = true;
    boolean firstTime = true;
    boolean mBound = false;
    static Menu menuOnRestart;
    /**
    Bitmap originalBm = BitmapFactory.decodeFile("fileUrl"); // Let's say this bitmap is 300 x 600 pixels
    Bitmap bm1 = Bitmap.createBitmap(originalBm, 0, 0, originalBm.getWidth(), (originalBm.getHeight() / 2));
    Bitmap bm2 = Bitmap.createBitmap(originalBm, 0, (originalBm.getHeight() / 2), originalBm.getWidth(), (originalBm.getHeight() / 2));
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        Intent svc = new Intent(this, BackgroundMusicService.class);
        bindService(svc, mConnection, Context.BIND_AUTO_CREATE);
        checkmService = true;



        //Creacio aleatoris
        Integer[] posicionsAleatories = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        posicionsAleatoriesList = new ArrayList<Integer>(Arrays.asList(posicionsAleatories));
        Collections.shuffle(posicionsAleatoriesList);

        //Obtenir dimensions imatges
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        //Toast.makeText(this, "x: " + String.valueOf(width) + "; y: " + String.valueOf(height), Toast.LENGTH_SHORT).show();
        btnSolucio = (Button) findViewById(R.id.btnSolucio);
        btnSolucio.setOnTouchListener(this);
        solucio = (ImageView) findViewById(R.id.imageSolucio);
        solucio.setImageBitmap(PrincipalActivity.getBitmap());
        solucio.setBackgroundColor(Color.BLACK);
        solucio.setVisibility(View.GONE);


        grid = (GridView) findViewById(R.id.gridview);
        im = new ImageAdapter(this);
        grid.setAdapter(im);
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        grid.setOnItemClickListener(new OnItemClickListener()
        {
            public void onItemClick(AdapterView<?> parent,View v, int position, long id) {
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
                    mService.playMoveSound();
                }
                System.out.println("POSICIO ACTUAL: " + ((CustomImageView) v).getPosicioActual() + " POSICIO CORRECTE: " + ((CustomImageView) v).getPosicioCorrecte());
                System.out.println("POSICIO INVISIBLE: " + posicioInvisible);
                if (((CustomImageView) v).getPosicioActual() == ((CustomImageView) v).getPosicioCorrecte()){
                    Toast.makeText(getApplicationContext(), "Posicio correcte: " + ((CustomImageView) v).getPosicioActual(), Toast.LENGTH_SHORT).show();
                }
                comprovaSolucio();
            }
        });

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, int position, long id) {
                ImageView invisible = (ImageView)parent.getChildAt(8);

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
                mService.playMoveSound();
                comprovaSolucio();

                return true;
            }
        });
    }

    public void comprovaSolucio(){
        int totsSolucionats = 0;
        for (int i = 0; i<grid.getChildCount(); i++){
            CustomImageView imatge  = (CustomImageView) grid.getChildAt(i);
            if (imatge.getPosicioActual() == imatge.getPosicioCorrecte()){
                totsSolucionats++;
            }
        }
        if (totsSolucionats==8){
            //Toast.makeText(getApplicationContext(), "Molt Bé! Puzzle Solucionat!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SolucioActivity.class);
            startActivity(intent);
        }
    }

    public boolean comprovaDisponible(CustomImageView v){
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
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
            } else {
                imageView = (CustomImageView) convertView;
            }
            if(position==8){
                imageView.setVisibility(View.GONE);
            }

            imageView.setPosicioCorrecte(posicionsAleatoriesList.get(position));
            imageView.setPosicioActual(position);
            imageView.setImageResource(imageIDs[posicionsAleatoriesList.get(position)]);
            return imageView;
        }
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
                mService.setPosition();
                finish();
                return true;

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

    public View getActionBarView() {
        Window window = getWindow();
        View v = window.getDecorView();
        int resId = getResources().getIdentifier("action_bar_container", "id", "android");
        return v.findViewById(resId);
    }


    @Override
    public void onBackPressed() {
        mService.setPosition();
        mService.stop();
        finish();
    }


    private AudioManager.OnAudioFocusChangeListener mAudioFocusListener = new AudioManager.OnAudioFocusChangeListener() {
        public void onAudioFocusChange(int focusChange) {

            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                case AudioManager.AUDIOFOCUS_LOSS:
                    if (mService.isPlating()){mService.stop();mService.playMoveSound();}
                    break;

                case AudioManager.AUDIOFOCUS_GAIN:
                    mService.play();
                    mService.playMoveSound();
                    break;
            }
        }
    };
}