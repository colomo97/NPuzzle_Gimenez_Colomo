package dam2.fje.edu.npuzzle_gimenez_colomo.View;
/**
 * Created by David on 10/02/2017.
 */

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.content.Context;
import android.os.IBinder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import dam2.fje.edu.npuzzle_gimenez_colomo.Controller.BackgroundMusicService;
import dam2.fje.edu.npuzzle_gimenez_colomo.R;

public class PuzzleActivity extends AppCompatActivity implements View.OnClickListener{
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
    boolean imageSelector = true;
    private GridView grid;
    boolean firstTime = true;
    boolean mBound = false;
    static Menu menuOnRestart;
    BackgroundMusicService mService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        grid = (GridView) findViewById(R.id.gridview);
        ImageAdapter im = new ImageAdapter(this);
        grid.setAdapter(im);
        mService = PrincipalActivity.getmService();
        ActionBar actionBar =getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

        }
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
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(context);
                imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(10, 10, 10, 10);
            } else {
                imageView = (ImageView) convertView;
            }
            if(position==8){
                imageView.setVisibility(View.GONE);
            }
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