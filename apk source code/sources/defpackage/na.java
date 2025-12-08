package defpackage;

import com.google.zxing.datamatrix.encoder.HighLevelEncoder;
import cooking.Limits;

/* loaded from: classes.dex */
public final class na implements sa {
    @Override // defpackage.sa
    public void a(ta taVar) {
        if (HighLevelEncoder.determineConsecutiveDigitCount(taVar.a, taVar.f) >= 2) {
            char cCharAt = taVar.a.charAt(taVar.f);
            char cCharAt2 = taVar.a.charAt(taVar.f + 1);
            if (!HighLevelEncoder.b(cCharAt) || !HighLevelEncoder.b(cCharAt2)) {
                throw new IllegalArgumentException("not digits: " + cCharAt + cCharAt2);
            }
            taVar.e.append((char) ((cCharAt2 - '0') + ((cCharAt - '0') * 10) + Limits.MAX_TEMPERATURE));
            taVar.f += 2;
            return;
        }
        char cB = taVar.b();
        int iA = HighLevelEncoder.a(taVar.a, taVar.f, 0);
        if (iA == 0) {
            if (!HighLevelEncoder.c(cB)) {
                taVar.e.append((char) (cB + 1));
                taVar.f++;
                return;
            } else {
                taVar.e.append((char) 235);
                taVar.e.append((char) ((cB - 128) + 1));
                taVar.f++;
                return;
            }
        }
        if (iA == 1) {
            taVar.e.append((char) 230);
            taVar.g = 1;
            return;
        }
        if (iA == 2) {
            taVar.e.append((char) 239);
            taVar.g = 2;
            return;
        }
        if (iA == 3) {
            taVar.e.append((char) 238);
            taVar.g = 3;
        } else if (iA == 4) {
            taVar.e.append((char) 240);
            taVar.g = 4;
        } else {
            if (iA != 5) {
                throw new IllegalStateException(g9.a("Illegal mode: ", iA));
            }
            taVar.e.append((char) 231);
            taVar.g = 5;
        }
    }
}
