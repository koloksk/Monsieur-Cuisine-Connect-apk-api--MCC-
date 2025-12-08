package defpackage;

import android.graphics.Rect;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;

@RequiresApi(21)
/* loaded from: classes.dex */
public class w7 implements y7 {
    public void a(x7 x7Var, float f) {
        p8 p8VarA = a(x7Var);
        CardView.a aVar = (CardView.a) x7Var;
        boolean useCompatPadding = CardView.this.getUseCompatPadding();
        boolean zA = aVar.a();
        if (f != p8VarA.e || p8VarA.f != useCompatPadding || p8VarA.g != zA) {
            p8VarA.e = f;
            p8VarA.f = useCompatPadding;
            p8VarA.g = zA;
            p8VarA.a((Rect) null);
            p8VarA.invalidateSelf();
        }
        d(x7Var);
    }

    public float b(x7 x7Var) {
        return a(x7Var).e;
    }

    public float c(x7 x7Var) {
        return a(x7Var).a;
    }

    public void d(x7 x7Var) {
        CardView.a aVar = (CardView.a) x7Var;
        if (!CardView.this.getUseCompatPadding()) {
            aVar.a(0, 0, 0, 0);
            return;
        }
        float f = a(x7Var).e;
        float f2 = a(x7Var).a;
        int iCeil = (int) Math.ceil(q8.a(f, f2, aVar.a()));
        int iCeil2 = (int) Math.ceil(q8.b(f, f2, aVar.a()));
        aVar.a(iCeil, iCeil2, iCeil, iCeil2);
    }

    public final p8 a(x7 x7Var) {
        return (p8) ((CardView.a) x7Var).a;
    }
}
