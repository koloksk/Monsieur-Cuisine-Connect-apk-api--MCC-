package defpackage;

import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

/* loaded from: classes.dex */
public final class va extends pa {
    @Override // defpackage.pa
    public int a() {
        return 3;
    }

    @Override // defpackage.pa, defpackage.sa
    public void a(ta taVar) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!taVar.d()) {
                break;
            }
            char cB = taVar.b();
            taVar.f++;
            a(cB, sb);
            if (sb.length() % 3 == 0) {
                pa.b(taVar, sb);
                int iA = HighLevelEncoder.a(taVar.a, taVar.f, 3);
                if (iA != 3) {
                    taVar.g = iA;
                    break;
                }
            }
        }
        a(taVar, sb);
    }

    @Override // defpackage.pa
    public int a(char c, StringBuilder sb) {
        if (c == '\r') {
            sb.append((char) 0);
        } else if (c == '*') {
            sb.append((char) 1);
        } else if (c == '>') {
            sb.append((char) 2);
        } else if (c == ' ') {
            sb.append((char) 3);
        } else if (c >= '0' && c <= '9') {
            sb.append((char) ((c - '0') + 4));
        } else if (c >= 'A' && c <= 'Z') {
            sb.append((char) ((c - 'A') + 14));
        } else {
            HighLevelEncoder.a(c);
            throw null;
        }
        return 1;
    }

    @Override // defpackage.pa
    public void a(ta taVar, StringBuilder sb) {
        taVar.e();
        int dataCapacity = taVar.h.getDataCapacity() - taVar.a();
        taVar.f -= sb.length();
        if (taVar.c() > 1 || dataCapacity > 1 || taVar.c() != dataCapacity) {
            taVar.e.append((char) 254);
        }
        if (taVar.g < 0) {
            taVar.g = 0;
        }
    }
}
