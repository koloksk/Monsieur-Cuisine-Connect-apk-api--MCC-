package fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import helper.LayoutHelper;

/* loaded from: classes.dex */
public class BaseFragment extends Fragment {
    public View a;
    public Unbinder b;

    @Override // android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        Unbinder unbinder = this.b;
        if (unbinder != null) {
            unbinder.unbind();
            this.b = null;
        }
    }

    @Override // android.support.v4.app.Fragment
    public void onViewCreated(@NonNull View view2, Bundle bundle) {
        super.onViewCreated(view2, bundle);
        this.b = ButterKnife.bind(this, view2);
        this.a = view2;
        LayoutHelper.applyFullscreen(view2);
    }
}
