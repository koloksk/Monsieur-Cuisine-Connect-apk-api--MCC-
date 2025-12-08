package com.google.zxing.datamatrix.encoder;

import android.support.v4.app.FragmentManagerImpl;
import android.support.v7.widget.helper.ItemTouchHelper;
import com.journeyapps.barcodescanner.ViewfinderView;
import cooking.Limits;
import de.silpion.mc2.BuildConfig;
import defpackage.g9;
import model.Presets;

/* loaded from: classes.dex */
public final class ErrorCorrection {
    public static final int[] a = {5, 7, 10, 11, 12, 14, 18, 20, 24, 28, 36, 42, 48, 56, 62, 68};
    public static final int[][] b = {new int[]{228, 48, 15, 111, 62}, new int[]{23, 68, 144, 134, 240, 92, 254}, new int[]{28, 24, 185, 166, 223, 248, 116, 255, 110, 61}, new int[]{175, 138, 205, 12, 194, 168, 39, 245, 60, 97, Presets.KNEADING_MAX_TIME}, new int[]{41, 153, 158, 91, 61, 42, 142, 213, 97, 178, 100, 242}, new int[]{156, 97, 192, 252, 95, 9, 157, 119, 138, 45, 18, 186, 83, 185}, new int[]{83, 195, 100, 39, 188, 75, 66, 61, 241, 213, 109, 129, 94, 254, 225, 48, 90, 188}, new int[]{15, 195, 244, 9, 233, 71, 168, 2, 188, ViewfinderView.CURRENT_POINT_OPACITY, 153, 145, 253, 79, 108, 82, 27, 174, 186, 172}, new int[]{52, 190, 88, 205, 109, 39, 176, 21, 155, 197, 251, 223, 155, 21, 5, 172, 254, 124, 12, 181, 184, 96, 50, 193}, new int[]{211, 231, 43, 97, 71, 96, 103, 174, 37, 151, 170, 53, 75, 34, 249, 121, 17, 138, 110, 213, 141, 136, Presets.KNEADING_MAX_TIME, 151, 233, 168, 93, 255}, new int[]{245, 127, 242, 218, Limits.MAX_TEMPERATURE, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 162, 181, 102, Presets.KNEADING_MAX_TIME, 84, BuildConfig.DefaultRecipeVersion, FragmentManagerImpl.ANIM_DUR, 251, 80, 182, 229, 18, 2, 4, 68, 33, 101, 137, 95, 119, 115, 44, 175, 184, 59, 25, 225, 98, 81, 112}, new int[]{77, 193, 137, 31, 19, 38, 22, 153, 247, 105, 122, 2, 245, 133, 242, 8, 175, 95, 100, 9, 167, 105, 214, 111, 57, 121, 21, 1, 253, 57, 54, 101, 248, 202, 69, 50, 150, 177, 226, 5, 9, 5}, new int[]{245, 132, 172, 223, 96, 32, 117, 22, 238, 133, 238, 231, 205, 188, 237, 87, 191, 106, 16, 147, 118, 23, 37, 90, 170, 205, 131, 88, Presets.KNEADING_MAX_TIME, 100, 66, 138, 186, 240, 82, 44, 176, 87, 187, 147, ViewfinderView.CURRENT_POINT_OPACITY, 175, 69, 213, 92, 253, 225, 19}, new int[]{175, 9, 223, 238, 12, 17, FragmentManagerImpl.ANIM_DUR, 208, 100, 29, 175, 170, 230, 192, 215, 235, 150, 159, 36, 223, 38, ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 132, 54, 228, 146, 218, 234, 117, 203, 29, 232, 144, 238, 22, 150, 201, 117, 62, 207, 164, 13, 137, 245, 127, 67, 247, 28, 155, 43, 203, 107, 233, 53, 143, 46}, new int[]{242, 93, 169, 50, 144, 210, 39, 118, 202, 188, 201, 189, 143, 108, 196, 37, 185, 112, 134, 230, 245, 63, 197, 190, ItemTouchHelper.Callback.DEFAULT_SWIPE_ANIMATION_DURATION, 106, 185, 221, 175, 64, 114, 71, 161, 44, 147, 6, 27, 218, 51, 63, 87, 10, 40, Limits.MAX_TEMPERATURE, 188, 17, 163, 31, 176, 170, 4, 107, 232, 7, 94, 166, 224, 124, 86, 47, 11, 204}, new int[]{FragmentManagerImpl.ANIM_DUR, 228, 173, 89, 251, 149, 159, 56, 89, 33, 147, 244, 154, 36, 73, 127, 213, 136, 248, 180, 234, 197, 158, 177, 68, 122, 93, 213, 15, ViewfinderView.CURRENT_POINT_OPACITY, 227, 236, 66, 139, 153, 185, 202, 167, BuildConfig.DefaultRecipeVersion, 25, FragmentManagerImpl.ANIM_DUR, 232, 96, 210, 231, 136, 223, 239, 181, 241, 59, 52, 172, 25, 49, 232, 211, 189, 64, 54, 108, 153, 132, 63, 96, 103, 82, 186}};
    public static final int[] c = new int[256];
    public static final int[] d = new int[255];

    static {
        int i = 1;
        for (int i2 = 0; i2 < 255; i2++) {
            d[i2] = i;
            c[i] = i2;
            i <<= 1;
            if (i >= 256) {
                i ^= 301;
            }
        }
    }

    public static String a(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        while (true) {
            int[] iArr = a;
            if (i2 >= iArr.length) {
                i2 = -1;
                break;
            }
            if (iArr[i2] == i) {
                break;
            }
            i2++;
        }
        if (i2 < 0) {
            throw new IllegalArgumentException(g9.a("Illegal number of error correction codewords specified: ", i));
        }
        int[] iArr2 = b[i2];
        char[] cArr = new char[i];
        for (int i3 = 0; i3 < i; i3++) {
            cArr[i3] = 0;
        }
        for (int i4 = 0; i4 < 0 + length; i4++) {
            int i5 = i - 1;
            int iCharAt = cArr[i5] ^ charSequence.charAt(i4);
            while (i5 > 0) {
                if (iCharAt == 0 || iArr2[i5] == 0) {
                    cArr[i5] = cArr[i5 - 1];
                } else {
                    char c2 = cArr[i5 - 1];
                    int[] iArr3 = d;
                    int[] iArr4 = c;
                    cArr[i5] = (char) (c2 ^ iArr3[(iArr4[iCharAt] + iArr4[iArr2[i5]]) % 255]);
                }
                i5--;
            }
            if (iCharAt == 0 || iArr2[0] == 0) {
                cArr[0] = 0;
            } else {
                int[] iArr5 = d;
                int[] iArr6 = c;
                cArr[0] = (char) iArr5[(iArr6[iCharAt] + iArr6[iArr2[0]]) % 255];
            }
        }
        char[] cArr2 = new char[i];
        for (int i6 = 0; i6 < i; i6++) {
            cArr2[i6] = cArr[(i - i6) - 1];
        }
        return String.valueOf(cArr2);
    }

    public static String encodeECC200(String str, SymbolInfo symbolInfo) {
        if (str.length() != symbolInfo.getDataCapacity()) {
            throw new IllegalArgumentException("The number of codewords does not match the selected symbol");
        }
        StringBuilder sb = new StringBuilder(symbolInfo.getErrorCodewords() + symbolInfo.getDataCapacity());
        sb.append(str);
        int interleavedBlockCount = symbolInfo.getInterleavedBlockCount();
        if (interleavedBlockCount == 1) {
            sb.append(a(str, symbolInfo.getErrorCodewords()));
        } else {
            sb.setLength(sb.capacity());
            int[] iArr = new int[interleavedBlockCount];
            int[] iArr2 = new int[interleavedBlockCount];
            int[] iArr3 = new int[interleavedBlockCount];
            int i = 0;
            while (i < interleavedBlockCount) {
                int i2 = i + 1;
                iArr[i] = symbolInfo.getDataLengthForInterleavedBlock(i2);
                iArr2[i] = symbolInfo.getErrorLengthForInterleavedBlock(i2);
                iArr3[i] = 0;
                if (i > 0) {
                    iArr3[i] = iArr3[i - 1] + iArr[i];
                }
                i = i2;
            }
            for (int i3 = 0; i3 < interleavedBlockCount; i3++) {
                StringBuilder sb2 = new StringBuilder(iArr[i3]);
                for (int i4 = i3; i4 < symbolInfo.getDataCapacity(); i4 += interleavedBlockCount) {
                    sb2.append(str.charAt(i4));
                }
                String strA = a(sb2.toString(), iArr2[i3]);
                int i5 = i3;
                int i6 = 0;
                while (i5 < iArr2[i3] * interleavedBlockCount) {
                    sb.setCharAt(symbolInfo.getDataCapacity() + i5, strA.charAt(i6));
                    i5 += interleavedBlockCount;
                    i6++;
                }
            }
        }
        return sb.toString();
    }
}
