package dam2.fje.edu.npuzzle_gimenez_colomo.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import dam2.fje.edu.npuzzle_gimenez_colomo.R;

/**
 * Created by miquel on 2/15/2017.
 */

public class PrincipalFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

       return inflater.inflate(R.layout.fragments_layout, container);
    }


}






