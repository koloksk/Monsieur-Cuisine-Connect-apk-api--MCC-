package adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import de.silpion.mc2.R;
import defpackage.g9;
import java.util.List;
import java.util.Locale;
import view.QuicksandTextView;

/* loaded from: classes.dex */
public class LanguageAdapter extends ArrayAdapter<Locale> {
    public LanguageAdapter(Context context, int i, List<Locale> list) {
        super(context, i, list);
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    @NonNull
    public View getView(int i, View view2, @NonNull ViewGroup viewGroup) {
        if (view2 == null) {
            view2 = LayoutInflater.from(getContext()).inflate(R.layout.item_language, viewGroup, false);
        }
        Locale item = getItem(i);
        if (item != null) {
            QuicksandTextView quicksandTextView = (QuicksandTextView) view2.findViewById(R.id.language_text);
            String displayName = item.getDisplayName(item);
            quicksandTextView.setText(String.format("%s%s", displayName.substring(0, 1).toUpperCase(), displayName.substring(1)));
            ImageView imageView = (ImageView) view2.findViewById(R.id.language_flag);
            Resources resources = getContext().getResources();
            StringBuilder sbA = g9.a("flag_");
            sbA.append(item.getLanguage());
            imageView.setImageDrawable(ResourcesCompat.getDrawable(resources, resources.getIdentifier(sbA.toString(), "drawable", getContext().getPackageName()), null));
        }
        return view2;
    }
}
