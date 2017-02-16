package dam2.fje.edu.npuzzle_gimenez_colomo.View;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by David on 16/02/2017.
 */

public class CustomImageView extends ImageView {
    int posicioActual, posicioCorrecte;

    public CustomImageView(Context context, int position) {
        super(context);
        posicioCorrecte = position;
    }

    public int getPosicioActual() {
        return posicioActual;
    }

    public void setPosicioActual(int posicioActual) {
        this.posicioActual = posicioActual;
    }

    public int getPosicioCorrecte() {
        return posicioCorrecte;
    }

    public void setPosicioCorrecte(int posicioCorrecte) {
        this.posicioCorrecte = posicioCorrecte;
    }
}
