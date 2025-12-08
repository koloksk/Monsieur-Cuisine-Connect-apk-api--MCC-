package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.provider.FontsContractCompat;
import java.io.File;
import java.io.InputStream;

@RequiresApi(14)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class l4 implements TypefaceCompat.a {
    @Override // android.support.v4.graphics.TypefaceCompat.a
    @Nullable
    public Typeface createFromFontFamilyFilesResourceEntry(Context context, FontResourcesParserCompat.FontFamilyFilesResourceEntry fontFamilyFilesResourceEntry, Resources resources, int i) {
        FontResourcesParserCompat.FontFileResourceEntry[] entries = fontFamilyFilesResourceEntry.getEntries();
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        int i3 = Integer.MAX_VALUE;
        FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry = null;
        for (FontResourcesParserCompat.FontFileResourceEntry fontFileResourceEntry2 : entries) {
            int iAbs = (Math.abs(fontFileResourceEntry2.getWeight() - i2) * 2) + (fontFileResourceEntry2.isItalic() == z ? 0 : 1);
            if (fontFileResourceEntry == null || i3 > iAbs) {
                fontFileResourceEntry = fontFileResourceEntry2;
                i3 = iAbs;
            }
        }
        if (fontFileResourceEntry == null) {
            return null;
        }
        return TypefaceCompat.createFromResourcesFontFile(context, resources, fontFileResourceEntry.getResourceId(), fontFileResourceEntry.getFileName(), i);
    }

    @Override // android.support.v4.graphics.TypefaceCompat.a
    public Typeface createFromFontInfo(Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        throw null;
    }

    public Typeface createFromInputStream(Context context, InputStream inputStream) {
        File tempFile = TypefaceCompatUtil.getTempFile(context);
        if (tempFile == null) {
            return null;
        }
        try {
            if (TypefaceCompatUtil.copyToFile(tempFile, inputStream)) {
                return Typeface.createFromFile(tempFile.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            tempFile.delete();
        }
    }

    @Override // android.support.v4.graphics.TypefaceCompat.a
    @Nullable
    public Typeface createFromResourcesFontFile(Context context, Resources resources, int i, String str, int i2) {
        File tempFile = TypefaceCompatUtil.getTempFile(context);
        if (tempFile == null) {
            return null;
        }
        try {
            if (TypefaceCompatUtil.copyToFile(tempFile, resources, i)) {
                return Typeface.createFromFile(tempFile.getPath());
            }
            return null;
        } catch (RuntimeException unused) {
            return null;
        } finally {
            tempFile.delete();
        }
    }

    public FontsContractCompat.FontInfo findBestInfo(FontsContractCompat.FontInfo[] fontInfoArr, int i) {
        int i2 = (i & 1) == 0 ? 400 : 700;
        boolean z = (i & 2) != 0;
        FontsContractCompat.FontInfo fontInfo = null;
        int i3 = Integer.MAX_VALUE;
        for (FontsContractCompat.FontInfo fontInfo2 : fontInfoArr) {
            int iAbs = (Math.abs(fontInfo2.getWeight() - i2) * 2) + (fontInfo2.isItalic() == z ? 0 : 1);
            if (fontInfo == null || i3 > iAbs) {
                fontInfo = fontInfo2;
                i3 = iAbs;
            }
        }
        return fontInfo;
    }
}
