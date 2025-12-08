package view.fastscroll;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class AlphabetAdapter extends RecyclerView.Adapter<ViewHolder> {
    public OnItemClickListener d;
    public WeakReference<RecyclerView> e;
    public final List<AlphabetItem> c = new ArrayList();
    public Integer f = null;

    public interface OnItemClickListener {
        void OnItemClicked(int i, int i2);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public Integer s;
        public Integer t;

        @BindView(R.id.tv_word)
        public TextView tvWord;

        public ViewHolder(View view2) {
            super(view2);
            this.s = null;
            this.t = null;
            ButterKnife.bind(this, view2);
            this.tvWord.setSelected(false);
        }
    }

    public class ViewHolder_ViewBinding implements Unbinder {
        public ViewHolder a;
        public View b;

        public class a extends DebouncingOnClickListener {
            public final /* synthetic */ ViewHolder c;

            public a(ViewHolder_ViewBinding viewHolder_ViewBinding, ViewHolder viewHolder) {
                this.c = viewHolder;
            }

            @Override // butterknife.internal.DebouncingOnClickListener
            public void doClick(View view2) {
                ViewHolder viewHolder = this.c;
                Integer num = viewHolder.s;
                if (num == null || viewHolder.t == null) {
                    return;
                }
                AlphabetAdapter.this.selectPosition(num.intValue());
                OnItemClickListener onItemClickListener = AlphabetAdapter.this.d;
                if (onItemClickListener != null) {
                    onItemClickListener.OnItemClicked(viewHolder.s.intValue(), viewHolder.t.intValue());
                }
            }
        }

        @UiThread
        public ViewHolder_ViewBinding(ViewHolder viewHolder, View view2) {
            this.a = viewHolder;
            View viewFindRequiredView = Utils.findRequiredView(view2, R.id.tv_word, "field 'tvWord' and method 'onWordClick'");
            viewHolder.tvWord = (TextView) Utils.castView(viewFindRequiredView, R.id.tv_word, "field 'tvWord'", TextView.class);
            this.b = viewFindRequiredView;
            viewFindRequiredView.setOnClickListener(new a(this, viewHolder));
        }

        @Override // butterknife.Unbinder
        @CallSuper
        public void unbind() {
            ViewHolder viewHolder = this.a;
            if (viewHolder == null) {
                throw new IllegalStateException("Bindings already cleared.");
            }
            this.a = null;
            viewHolder.tvWord = null;
            this.b.setOnClickListener(null);
            this.b = null;
        }
    }

    public void convertAndSetData(List<String> list) {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new AlphabetItem(arrayList.size(), it.next()));
        }
        setData(arrayList);
    }

    public AlphabetItem getItem(int i) {
        if (i < 0 || i >= this.c.size()) {
            return null;
        }
        return this.c.get(i);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.c.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.e = new WeakReference<>(recyclerView);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.e = null;
    }

    public void selectItem(String str) {
        if (str == null) {
            selectPosition(-1);
            return;
        }
        for (int i = 0; i < this.c.size(); i++) {
            if (str.equals(this.c.get(i).getWord())) {
                selectPosition(i);
                return;
            }
        }
    }

    public void selectPosition(int i) {
        Integer num = this.f;
        if (num != null) {
            int iIntValue = num.intValue();
            if (iIntValue >= 0 && iIntValue < this.c.size()) {
                AlphabetItem alphabetItem = this.c.get(iIntValue);
                if (alphabetItem.b) {
                    alphabetItem.b = false;
                    notifyItemChanged(iIntValue);
                }
            }
            this.f = null;
        }
        if (i < 0 || i >= this.c.size()) {
            this.f = null;
            return;
        }
        Integer numValueOf = Integer.valueOf(i);
        this.f = numValueOf;
        int iIntValue2 = numValueOf.intValue();
        if (iIntValue2 < 0 || iIntValue2 >= this.c.size()) {
            return;
        }
        AlphabetItem alphabetItem2 = this.c.get(iIntValue2);
        if (!alphabetItem2.b) {
            alphabetItem2.b = true;
            notifyItemChanged(iIntValue2);
        }
        if (this.e.get() != null) {
            this.e.get().smoothScrollToPosition(iIntValue2);
        }
    }

    public void setData(List<AlphabetItem> list) {
        this.c.clear();
        if (list != null) {
            this.c.addAll(list);
        }
        this.f = null;
        notifyDataSetChanged();
        selectPosition(0);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.d = onItemClickListener;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        AlphabetItem alphabetItem = this.c.get(i);
        if (viewHolder == null) {
            throw null;
        }
        if (alphabetItem == null) {
            return;
        }
        viewHolder.s = Integer.valueOf(i);
        viewHolder.t = Integer.valueOf(alphabetItem.a);
        String str = alphabetItem.c;
        if (str != null) {
            viewHolder.tvWord.setText(str);
        } else {
            viewHolder.tvWord.setText("");
        }
        viewHolder.tvWord.setSelected(alphabetItem.b);
        if (alphabetItem.b) {
            TextView textView = viewHolder.tvWord;
            textView.setTypeface(textView.getTypeface(), 1);
        } else {
            TextView textView2 = viewHolder.tvWord;
            textView2.setTypeface(textView2.getTypeface(), 0);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_alphabet_layout, viewGroup, false));
    }
}
