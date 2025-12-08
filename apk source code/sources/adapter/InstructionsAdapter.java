package adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import db.model.Recipe;
import de.silpion.mc2.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class InstructionsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public boolean c;
    public LayoutInflater d;
    public List<d> e = new ArrayList();

    public class b extends RecyclerView.ViewHolder {
        public TextView s;
        public TextView t;

        public b(InstructionsAdapter instructionsAdapter, View view2) {
            super(view2);
            this.s = (TextView) view2.findViewById(R.id.item_instruction_header_tv);
            this.t = (TextView) view2.findViewById(R.id.item_instruction_tv);
        }
    }

    public class c extends RecyclerView.ViewHolder {
        public TextView s;
        public TextView t;

        public c(InstructionsAdapter instructionsAdapter, View view2) {
            super(view2);
            this.t = (TextView) view2.findViewById(R.id.item_instruction_number_tv);
            this.s = (TextView) view2.findViewById(R.id.item_instruction_tv);
        }
    }

    public class d {
        public boolean a;
        public Integer b;
        public String c;

        public /* synthetic */ d(InstructionsAdapter instructionsAdapter, a aVar) {
        }
    }

    public InstructionsAdapter(Context context, Recipe recipe) {
        this.d = LayoutInflater.from(context);
        List<String> preparations = recipe.getPreparations();
        List<String> instructions = recipe.getInstructions();
        a aVar = null;
        if (preparations != null && preparations.size() > 0) {
            if (!TextUtils.isEmpty(preparations.get(0))) {
                d dVar = new d(this, aVar);
                dVar.c = context.getString(R.string.preparation);
                dVar.a = true;
                this.e.add(dVar);
            }
            for (String str : preparations) {
                if (!TextUtils.isEmpty(str)) {
                    d dVar2 = new d(this, aVar);
                    dVar2.c = str;
                    this.e.add(dVar2);
                }
            }
        }
        this.c = this.e.size() > 0;
        if (instructions == null || instructions.size() <= 0) {
            return;
        }
        d dVar3 = new d(this, aVar);
        dVar3.c = context.getString(R.string.f4cooking);
        dVar3.a = true;
        this.e.add(dVar3);
        for (String str2 : instructions) {
            d dVar4 = new d(this, aVar);
            dVar4.c = str2;
            this.e.add(dVar4);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.e.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return this.e.get(i).a ? 0 : 1;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        String str;
        d dVar = this.e.get(i);
        if (dVar.a) {
            b bVar = (b) viewHolder;
            bVar.s.setVisibility(i == 0 && this.c ? 0 : 8);
            bVar.t.setText(dVar.c);
            return;
        }
        c cVar = (c) viewHolder;
        TextView textView = cVar.t;
        if (dVar.b != null) {
            str = dVar.b + ".";
        } else {
            str = "";
        }
        textView.setText(str);
        cVar.s.setText(dVar.c);
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return i != 0 ? i != 1 ? new b(this, this.d.inflate(R.layout.item_instruction_header, viewGroup, false)) : new c(this, this.d.inflate(R.layout.item_instruction, viewGroup, false)) : new b(this, this.d.inflate(R.layout.item_instruction_header, viewGroup, false));
    }
}
