package adapter;

import android.content.Context;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.silpion.mc2.R;
import defpackage.g9;
import java.util.Iterator;
import java.util.List;
import view.FixedWidthSpan;

/* loaded from: classes.dex */
public class FaqAdapter extends RecyclerView.Adapter<a> {
    public final String c = FaqAdapter.class.getSimpleName();
    public final LayoutInflater d;
    public final List<Faq> e;
    public FaqItemExpandListener f;

    @Keep
    public class Faq {
        public String content;
        public boolean expanded;
        public String title;

        public Faq() {
        }

        public String getContent() {
            return this.content;
        }

        public String getTitle() {
            return this.title;
        }

        public boolean isExpanded() {
            return this.expanded;
        }

        public void setContent(String str) {
            this.content = str;
        }

        public void setExpanded(boolean z) {
            this.expanded = z;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public String toString() {
            StringBuilder sbA = g9.a("Faq{title='");
            g9.a(sbA, this.title, '\'', ", content='");
            g9.a(sbA, this.content, '\'', ", expanded=");
            sbA.append(this.expanded);
            sbA.append('}');
            return sbA.toString();
        }
    }

    public interface FaqItemExpandListener {
        void onFaqItemExpanded(View view2);
    }

    public class a extends RecyclerView.ViewHolder {
        public final TextView s;
        public final TextView t;
        public final RelativeLayout u;
        public final ImageView v;

        public a(View view2) {
            super(view2);
            this.t = (TextView) view2.findViewById(R.id.faq_row_header_tv);
            this.s = (TextView) view2.findViewById(R.id.faq_row_content_tv);
            ImageView imageView = (ImageView) view2.findViewById(R.id.faq_row_plus);
            this.v = imageView;
            imageView.setSelected(false);
            this.u = (RelativeLayout) view2.findViewById(R.id.faq_row_plus_container_rl);
        }

        public /* synthetic */ void a(Faq faq, int i, View view2) {
            faq.setExpanded(!faq.isExpanded());
            FaqAdapter.this.notifyItemChanged(i);
        }
    }

    public FaqAdapter(Context context, List<Faq> list) {
        this.e = list;
        this.d = LayoutInflater.from(context);
    }

    public void collapseFaq() {
        List<Faq> list = this.e;
        if (list == null || list.size() <= 0) {
            return;
        }
        Iterator<Faq> it = this.e.iterator();
        while (it.hasNext()) {
            it.next().setExpanded(false);
        }
        notifyDataSetChanged();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.e.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return 1;
    }

    public void setFaqItemExpandListener(FaqItemExpandListener faqItemExpandListener) {
        this.f = faqItemExpandListener;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull final a aVar, int i) {
        final int adapterPosition = aVar.getAdapterPosition();
        if (adapterPosition == -1) {
            return;
        }
        final Faq faq = this.e.get(adapterPosition);
        aVar.t.setText(faq.getTitle());
        if (faq.isExpanded()) {
            aVar.t.setTypeface(null, 1);
            aVar.u.setBackgroundResource(R.drawable.shape_settings_rectangle_rounded_right_bottom_left);
            String content = faq.getContent();
            aVar.s.setVisibility(0);
            if (content.contains("\t")) {
                Log.d(FaqAdapter.this.c, "Building tabbed table from:\n" + content);
                SpannableString spannableString = new SpannableString(content);
                int iIndexOf = 0;
                int i2 = 0;
                while (iIndexOf != -1) {
                    if (i2 == 0) {
                        int iIndexOf2 = content.indexOf(9, iIndexOf);
                        Log.d(FaqAdapter.this.c, "MODE0 " + iIndexOf + " .. " + iIndexOf2);
                        if (iIndexOf2 != -1) {
                            iIndexOf2++;
                            spannableString.setSpan(new FixedWidthSpan(5), iIndexOf, iIndexOf2, 33);
                        }
                        iIndexOf = iIndexOf2;
                        i2++;
                    } else {
                        iIndexOf = content.indexOf(10, iIndexOf);
                        Log.d(FaqAdapter.this.c, "MODE1 " + iIndexOf);
                        if (iIndexOf != -1) {
                            iIndexOf++;
                        }
                        i2 = 0;
                    }
                }
                aVar.s.setText(spannableString);
            } else {
                aVar.s.setText(content);
            }
            aVar.v.setSelected(true);
            FaqAdapter.this.f.onFaqItemExpanded(aVar.s);
        } else {
            aVar.t.setTypeface(null, 0);
            aVar.u.setBackgroundResource(R.drawable.shape_settings_rectangle_rounded_right);
            aVar.s.setVisibility(8);
            aVar.v.setSelected(false);
        }
        aVar.s.invalidate();
        aVar.v.invalidate();
        aVar.u.setOnClickListener(new View.OnClickListener() { // from class: r0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                aVar.a(faq, adapterPosition, view2);
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    @NonNull
    public a onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new a(this.d.inflate(R.layout.item_faq, viewGroup, false));
    }
}
