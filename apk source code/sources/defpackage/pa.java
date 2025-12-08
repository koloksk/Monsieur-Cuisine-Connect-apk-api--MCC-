package defpackage;

import com.google.zxing.datamatrix.encoder.HighLevelEncoder;

/* loaded from: classes.dex */
public class pa implements sa {
    public static void b(ta taVar, StringBuilder sb) {
        int iCharAt = (sb.charAt(1) * '(') + (sb.charAt(0) * 1600) + sb.charAt(2) + 1;
        taVar.e.append(new String(new char[]{(char) (iCharAt / 256), (char) (iCharAt % 256)}));
        sb.delete(0, 3);
    }

    public int a() {
        return 1;
    }

    @Override // defpackage.sa
    public void a(ta taVar) {
        int iA;
        StringBuilder sb = new StringBuilder();
        while (true) {
            if (!taVar.d()) {
                break;
            }
            char cB = taVar.b();
            taVar.f++;
            int iA2 = a(cB, sb);
            int iA3 = taVar.a() + ((sb.length() / 3) << 1);
            taVar.a(iA3);
            int dataCapacity = taVar.h.getDataCapacity() - iA3;
            if (!taVar.d()) {
                StringBuilder sb2 = new StringBuilder();
                if (sb.length() % 3 == 2 && (dataCapacity < 2 || dataCapacity > 2)) {
                    iA2 = a(taVar, sb, sb2, iA2);
                }
                while (sb.length() % 3 == 1 && ((iA2 <= 3 && dataCapacity != 1) || iA2 > 3)) {
                    iA2 = a(taVar, sb, sb2, iA2);
                }
            } else if (sb.length() % 3 == 0 && (iA = HighLevelEncoder.a(taVar.a, taVar.f, a())) != a()) {
                taVar.g = iA;
                break;
            }
        }
        a(taVar, sb);
    }

    public final int a(ta taVar, StringBuilder sb, StringBuilder sb2, int i) {
        int length = sb.length();
        sb.delete(length - i, length);
        taVar.f--;
        int iA = a(taVar.b(), sb2);
        taVar.h = null;
        return iA;
    }

    public void a(ta taVar, StringBuilder sb) {
        int length = (sb.length() / 3) << 1;
        int length2 = sb.length() % 3;
        int iA = taVar.a() + length;
        taVar.a(iA);
        int dataCapacity = taVar.h.getDataCapacity() - iA;
        if (length2 == 2) {
            sb.append((char) 0);
            while (sb.length() >= 3) {
                b(taVar, sb);
            }
            if (taVar.d()) {
                taVar.e.append((char) 254);
            }
        } else if (dataCapacity == 1 && length2 == 1) {
            while (sb.length() >= 3) {
                b(taVar, sb);
            }
            if (taVar.d()) {
                taVar.e.append((char) 254);
            }
            taVar.f--;
        } else if (length2 == 0) {
            while (sb.length() >= 3) {
                b(taVar, sb);
            }
            if (dataCapacity > 0 || taVar.d()) {
                taVar.e.append((char) 254);
            }
        } else {
            throw new IllegalStateException("Unexpected case. Please report!");
        }
        taVar.g = 0;
    }

    public int a(char c, StringBuilder sb) {
        if (c == ' ') {
            sb.append((char) 3);
            return 1;
        }
        if (c >= '0' && c <= '9') {
            sb.append((char) ((c - '0') + 4));
            return 1;
        }
        if (c >= 'A' && c <= 'Z') {
            sb.append((char) ((c - 'A') + 14));
            return 1;
        }
        if (c >= 0 && c <= 31) {
            sb.append((char) 0);
            sb.append(c);
            return 2;
        }
        if (c >= '!' && c <= '/') {
            sb.append((char) 1);
            sb.append((char) (c - '!'));
            return 2;
        }
        if (c >= ':' && c <= '@') {
            sb.append((char) 1);
            sb.append((char) ((c - ':') + 15));
            return 2;
        }
        if (c >= '[' && c <= '_') {
            sb.append((char) 1);
            sb.append((char) ((c - '[') + 22));
            return 2;
        }
        if (c >= '`' && c <= 127) {
            sb.append((char) 2);
            sb.append((char) (c - '`'));
            return 2;
        }
        if (c >= 128) {
            sb.append("\u0001\u001e");
            return a((char) (c - 128), sb) + 2;
        }
        throw new IllegalArgumentException("Illegal character: " + c);
    }
}
