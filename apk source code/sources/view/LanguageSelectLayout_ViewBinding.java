package view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class LanguageSelectLayout_ViewBinding implements Unbinder {
    public LanguageSelectLayout a;

    @UiThread
    public LanguageSelectLayout_ViewBinding(LanguageSelectLayout languageSelectLayout) {
        this(languageSelectLayout, languageSelectLayout);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        LanguageSelectLayout languageSelectLayout = this.a;
        if (languageSelectLayout == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        languageSelectLayout.languageList = null;
    }

    @UiThread
    public LanguageSelectLayout_ViewBinding(LanguageSelectLayout languageSelectLayout, View view2) {
        this.a = languageSelectLayout;
        languageSelectLayout.header = (TextView) Utils.findRequiredViewAsType(view2, R.id.language_select_header, "field 'header'", TextView.class);
        languageSelectLayout.languageList = (ListView) Utils.findRequiredViewAsType(view2, R.id.language_select_list, "field 'languageList'", ListView.class);
    }
}
