package dam2.fje.edu.npuzzle_gimenez_colomo.View;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.FileNotFoundException;
import dam2.fje.edu.npuzzle_gimenez_colomo.R;


public class PrincipalActivity extends AppCompatActivity implements View.OnClickListener{
    static Bitmap bitmap;
    Button openImage;
    Button startGame;
    ImageView targetImage;
    TextView textTargetImatge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        targetImage = (ImageView) findViewById(R.id.biblioImage);
        startGame = (Button) findViewById(R.id.bStart);
        startGame.setOnClickListener(this);
        textTargetImatge = (TextView) findViewById(R.id.txtImatge);
        textTargetImatge.setVisibility(View.GONE);
        openImage = (Button) findViewById(R.id.btOpenImage);
        openImage.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case (R.id.bStart):
                if(textTargetImatge.getVisibility()==View.VISIBLE){
                    Intent intent = new Intent(this, PuzzleActivity.class);
                    startActivity(intent);
            }else Toast.makeText(getApplicationContext(), "Has de seleccionar una imatge per poder jugar al puzzle", Toast.LENGTH_SHORT).show();
                break;

            case(R.id.btOpenImage):
                Intent intentImage = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentImage, 0);
                break;
            default:
                break;
        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();

            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
                textTargetImatge.setVisibility(View.VISIBLE);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
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
            case R.id.item_ajuda:
                Intent intent = new Intent(this, DisplayWebView.class);
                startActivity(intent);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public  static  Bitmap getBitmap(){
        return bitmap;
    }
}