package defpackage;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Field;

/* loaded from: classes.dex */
public class z9 extends ReflectiveTypeAdapterFactory.a {
    public final /* synthetic */ Field d;
    public final /* synthetic */ boolean e;
    public final /* synthetic */ TypeAdapter f;
    public final /* synthetic */ Gson g;
    public final /* synthetic */ TypeToken h;
    public final /* synthetic */ boolean i;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public z9(ReflectiveTypeAdapterFactory reflectiveTypeAdapterFactory, String str, boolean z, boolean z2, Field field, boolean z3, TypeAdapter typeAdapter, Gson gson, TypeToken typeToken, boolean z4) {
        super(str, z, z2);
        this.d = field;
        this.e = z3;
        this.f = typeAdapter;
        this.g = gson;
        this.h = typeToken;
        this.i = z4;
    }
}
