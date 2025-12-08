package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import de.silpion.mc2.R;
import java.util.ArrayList;
import java.util.List;
import model.HelpContentItem;

/* loaded from: classes.dex */
public class HelpContentItemAdapter extends RecyclerView.Adapter<a> {
    public LayoutInflater c;
    public List<HelpContentItem> d = new ArrayList();

    public class a extends RecyclerView.ViewHolder {
        public ImageView s;
        public TextView t;

        public a(HelpContentItemAdapter helpContentItemAdapter, View view2) {
            super(view2);
            this.t = (TextView) view2.findViewById(R.id.item_helpcontent_title);
            this.s = (ImageView) view2.findViewById(R.id.item_helpcontent_background);
        }
    }

    public HelpContentItemAdapter(Context context) {
        this.c = LayoutInflater.from(context);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.d.size();
    }

    public void setItems(List<? extends HelpContentItem> list) {
        this.d.clear();
        this.d.addAll(list);
        notifyItemRangeInserted(0, list.size() - 1);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(a aVar, int i) {
        HelpContentItem helpContentItem = this.d.get(i);
        aVar.t.setText(helpContentItem.getTitle());
        if (helpContentItem.isSelected()) {
            aVar.t.setSelected(true);
        } else {
            aVar.t.setSelected(false);
        }
        aVar.t.setText(helpContentItem.getTitle());
        aVar.s.setImageResource(helpContentItem.getBackground().intValue());
        aVar.itemView.setOnClickListener(helpContentItem.getOnClickListener());
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public a onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new a(this, this.c.inflate(R.layout.item_helpcontent, viewGroup, false));
    }
}
