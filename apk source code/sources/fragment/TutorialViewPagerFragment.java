package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class TutorialViewPagerFragment extends Fragment {
    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fragment_tutorial_page, viewGroup, false);
        ((ImageView) viewGroup2.findViewById(R.id.tutorial_img)).setImageResource(getArguments().getInt("ImageResID", 0));
        return viewGroup2;
    }
}
