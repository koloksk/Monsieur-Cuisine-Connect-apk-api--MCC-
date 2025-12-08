package defpackage;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class xb {
    public final BitArray a;
    public final rb b = new rb();
    public final StringBuilder c = new StringBuilder();

    public xb(BitArray bitArray) {
        this.a = bitArray;
    }

    public String a(StringBuilder sb, int i) throws NotFoundException, FormatException {
        String str = null;
        while (true) {
            tb tbVarA = a(i, str);
            String strA = wb.a(tbVarA.b);
            if (strA != null) {
                sb.append(strA);
            }
            String strValueOf = tbVarA.d ? String.valueOf(tbVarA.c) : null;
            int i2 = tbVarA.a;
            if (i == i2) {
                return sb.toString();
            }
            i = i2;
            str = strValueOf;
        }
    }

    public final boolean b(int i) {
        int i2;
        if (i + 1 > this.a.getSize()) {
            return false;
        }
        for (int i3 = 0; i3 < 5 && (i2 = i3 + i) < this.a.getSize(); i3++) {
            if (i3 == 2) {
                if (!this.a.get(i + 2)) {
                    return false;
                }
            } else if (this.a.get(i2)) {
                return false;
            }
        }
        return true;
    }

    public int a(int i, int i2) {
        return a(this.a, i, i2);
    }

    public static int a(BitArray bitArray, int i, int i2) {
        int i3 = 0;
        for (int i4 = 0; i4 < i2; i4++) {
            if (bitArray.get(i + i4)) {
                i3 |= 1 << ((i2 - i4) - 1);
            }
        }
        return i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:166:0x02ca, code lost:
    
        r1 = r3.b;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x03b3, code lost:
    
        r1 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:184:0x030a  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x03ea  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0411  */
    /* JADX WARN: Removed duplicated region for block: B:236:0x0414 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:255:0x0416 A[ADDED_TO_REGION, REMOVE, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x03be A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public defpackage.tb a(int r18, java.lang.String r19) throws com.google.zxing.FormatException {
        /*
            Method dump skipped, instructions count: 1150
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xb.a(int, java.lang.String):tb");
    }

    public final boolean a(int i) {
        int i2 = i + 3;
        if (i2 > this.a.getSize()) {
            return false;
        }
        while (i < i2) {
            if (this.a.get(i)) {
                return false;
            }
            i++;
        }
        return true;
    }
}
