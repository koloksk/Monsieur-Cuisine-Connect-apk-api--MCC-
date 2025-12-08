package view;

import adapter.LanguageAdapter;
import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.silpion.mc2.R;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes.dex */
public class LanguageSelectLayout extends RelativeLayout {

    @BindView(R.id.language_select_header)
    public TextView header;

    @BindView(R.id.language_select_list)
    public ListView languageList;

    public LanguageSelectLayout(Context context) {
        this(context, null);
    }

    public LanguageSelectLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LanguageSelectLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public LanguageSelectLayout(Context context, AttributeSet attributeSet, int i, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i, i2);
        RelativeLayout.inflate(context, R.layout.language_select_layout, this);
        ButterKnife.bind(this);
        final ArrayList arrayList = new ArrayList();
        for (String str : getResources().getStringArray(R.array.supported_languages)) {
            arrayList.add(new Locale(str));
        }
        this.languageList.setAdapter((ListAdapter) new LanguageAdapter(getContext(), R.layout.item_language, arrayList));
        this.languageList.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: pp
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view2, int i3, long j) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
                App.getInstance().changeLocale((Locale) arrayList.get(i3));
            }
        });
    }
}
