package android.databinding;

import android.annotation.TargetApi;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.OnLifecycleEvent;
import android.content.res.ColorStateList;
import android.databinding.CallbackRegistry;
import android.databinding.Observable;
import android.databinding.ObservableList;
import android.databinding.ObservableMap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.util.LongSparseArray;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.viewbinding.ViewBinding;
import com.android.databinding.library.R;
import defpackage.g9;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class ViewDataBinding extends BaseObservable implements ViewBinding {
    public static final String BINDING_TAG_PREFIX = "binding_";
    public static int p;
    public static final boolean q;
    public static final i r;
    public static final i s;
    public static final i t;
    public static final i u;
    public static final CallbackRegistry.NotifierCallback<OnRebindCallback, ViewDataBinding, Void> v;
    public static final ReferenceQueue<ViewDataBinding> w;
    public static final View.OnAttachStateChangeListener x;
    public final Runnable b;
    public boolean c;
    public boolean d;
    public m[] e;
    public final View f;
    public CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> g;
    public boolean h;
    public Choreographer i;
    public final Choreographer.FrameCallback j;
    public Handler k;
    public ViewDataBinding l;
    public LifecycleOwner m;
    public final DataBindingComponent mBindingComponent;
    public OnStartListener n;
    public boolean o;

    public static class IncludedLayouts {
        public final int[][] indexes;
        public final int[][] layoutIds;
        public final String[][] layouts;

        public IncludedLayouts(int i) {
            this.layouts = new String[i][];
            this.indexes = new int[i][];
            this.layoutIds = new int[i][];
        }

        public void setIncludes(int i, String[] strArr, int[] iArr, int[] iArr2) {
            this.layouts[i] = strArr;
            this.indexes[i] = iArr;
            this.layoutIds[i] = iArr2;
        }
    }

    public static class OnStartListener implements LifecycleObserver {
        public final WeakReference<ViewDataBinding> a;

        public /* synthetic */ OnStartListener(ViewDataBinding viewDataBinding, a aVar) {
            this.a = new WeakReference<>(viewDataBinding);
        }

        @OnLifecycleEvent(Lifecycle.Event.ON_START)
        public void onStart() {
            ViewDataBinding viewDataBinding = this.a.get();
            if (viewDataBinding != null) {
                viewDataBinding.executePendingBindings();
            }
        }
    }

    public static abstract class PropertyChangedInverseListener extends Observable.OnPropertyChangedCallback implements InverseBindingListener {
        public final int a;

        public PropertyChangedInverseListener(int i) {
            this.a = i;
        }

        @Override // android.databinding.Observable.OnPropertyChangedCallback
        public void onPropertyChanged(Observable observable, int i) {
            if (i == this.a || i == 0) {
                onChange();
            }
        }
    }

    public static class a implements i {
        @Override // android.databinding.ViewDataBinding.i
        public m a(ViewDataBinding viewDataBinding, int i) {
            return new o(viewDataBinding, i).a;
        }
    }

    public static class b implements i {
        @Override // android.databinding.ViewDataBinding.i
        public m a(ViewDataBinding viewDataBinding, int i) {
            return new l(viewDataBinding, i).a;
        }
    }

    public static class c implements i {
        @Override // android.databinding.ViewDataBinding.i
        public m a(ViewDataBinding viewDataBinding, int i) {
            return new n(viewDataBinding, i).a;
        }
    }

    public static class d implements i {
        @Override // android.databinding.ViewDataBinding.i
        public m a(ViewDataBinding viewDataBinding, int i) {
            return new j(viewDataBinding, i).a;
        }
    }

    public static class e extends CallbackRegistry.NotifierCallback<OnRebindCallback, ViewDataBinding, Void> {
        @Override // android.databinding.CallbackRegistry.NotifierCallback
        public void onNotifyCallback(OnRebindCallback onRebindCallback, ViewDataBinding viewDataBinding, int i, Void r4) {
            OnRebindCallback onRebindCallback2 = onRebindCallback;
            ViewDataBinding viewDataBinding2 = viewDataBinding;
            if (i == 1) {
                if (onRebindCallback2.onPreBind(viewDataBinding2)) {
                    return;
                }
                viewDataBinding2.d = true;
            } else if (i == 2) {
                onRebindCallback2.onCanceled(viewDataBinding2);
            } else {
                if (i != 3) {
                    return;
                }
                onRebindCallback2.onBound(viewDataBinding2);
            }
        }
    }

    public static class f implements View.OnAttachStateChangeListener {
        @Override // android.view.View.OnAttachStateChangeListener
        @TargetApi(19)
        public void onViewAttachedToWindow(View view2) {
            ViewDataBinding.a(view2).b.run();
            view2.removeOnAttachStateChangeListener(this);
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view2) {
        }
    }

    public class g implements Runnable {
        public g() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (this) {
                ViewDataBinding.this.c = false;
            }
            while (true) {
                Reference<? extends ViewDataBinding> referencePoll = ViewDataBinding.w.poll();
                if (referencePoll == null) {
                    break;
                } else if (referencePoll instanceof m) {
                    ((m) referencePoll).b();
                }
            }
            if (ViewDataBinding.this.f.isAttachedToWindow()) {
                ViewDataBinding.this.executePendingBindings();
            } else {
                ViewDataBinding.this.f.removeOnAttachStateChangeListener(ViewDataBinding.x);
                ViewDataBinding.this.f.addOnAttachStateChangeListener(ViewDataBinding.x);
            }
        }
    }

    public class h implements Choreographer.FrameCallback {
        public h() {
        }

        @Override // android.view.Choreographer.FrameCallback
        public void doFrame(long j) {
            ViewDataBinding.this.b.run();
        }
    }

    public interface i {
        m a(ViewDataBinding viewDataBinding, int i);
    }

    public interface k<T> {
        void a(LifecycleOwner lifecycleOwner);

        void a(T t);

        void b(T t);
    }

    public static class l extends ObservableList.OnListChangedCallback implements k<ObservableList> {
        public final m<ObservableList> a;

        public l(ViewDataBinding viewDataBinding, int i) {
            this.a = new m<>(viewDataBinding, i, this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(LifecycleOwner lifecycleOwner) {
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(ObservableList observableList) {
            observableList.removeOnListChangedCallback(this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void b(ObservableList observableList) {
            observableList.addOnListChangedCallback(this);
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onChanged(ObservableList observableList) {
            m<ObservableList> mVar;
            ObservableList observableList2;
            ViewDataBinding viewDataBindingA = this.a.a();
            if (viewDataBindingA != null && (observableList2 = (mVar = this.a).c) == observableList) {
                ViewDataBinding.a(viewDataBindingA, mVar.b, observableList2, 0);
            }
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeChanged(ObservableList observableList, int i, int i2) {
            onChanged(observableList);
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeInserted(ObservableList observableList, int i, int i2) {
            onChanged(observableList);
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeMoved(ObservableList observableList, int i, int i2, int i3) {
            onChanged(observableList);
        }

        @Override // android.databinding.ObservableList.OnListChangedCallback
        public void onItemRangeRemoved(ObservableList observableList, int i, int i2) {
            onChanged(observableList);
        }
    }

    public static class m<T> extends WeakReference<ViewDataBinding> {
        public final k<T> a;
        public final int b;
        public T c;

        public m(ViewDataBinding viewDataBinding, int i, k<T> kVar) {
            super(viewDataBinding, ViewDataBinding.w);
            this.b = i;
            this.a = kVar;
        }

        public ViewDataBinding a() {
            ViewDataBinding viewDataBinding = (ViewDataBinding) get();
            if (viewDataBinding == null) {
                b();
            }
            return viewDataBinding;
        }

        public boolean b() {
            boolean z;
            T t = this.c;
            if (t != null) {
                this.a.a((k<T>) t);
                z = true;
            } else {
                z = false;
            }
            this.c = null;
            return z;
        }
    }

    public static class n extends ObservableMap.OnMapChangedCallback implements k<ObservableMap> {
        public final m<ObservableMap> a;

        public n(ViewDataBinding viewDataBinding, int i) {
            this.a = new m<>(viewDataBinding, i, this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(LifecycleOwner lifecycleOwner) {
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(ObservableMap observableMap) {
            observableMap.removeOnMapChangedCallback(this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void b(ObservableMap observableMap) {
            observableMap.addOnMapChangedCallback(this);
        }

        @Override // android.databinding.ObservableMap.OnMapChangedCallback
        public void onMapChanged(ObservableMap observableMap, Object obj) {
            ViewDataBinding viewDataBindingA = this.a.a();
            if (viewDataBindingA != null) {
                m<ObservableMap> mVar = this.a;
                if (observableMap != mVar.c) {
                    return;
                }
                ViewDataBinding.a(viewDataBindingA, mVar.b, observableMap, 0);
            }
        }
    }

    public static class o extends Observable.OnPropertyChangedCallback implements k<Observable> {
        public final m<Observable> a;

        public o(ViewDataBinding viewDataBinding, int i) {
            this.a = new m<>(viewDataBinding, i, this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(LifecycleOwner lifecycleOwner) {
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(Observable observable) {
            observable.removeOnPropertyChangedCallback(this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void b(Observable observable) {
            observable.addOnPropertyChangedCallback(this);
        }

        @Override // android.databinding.Observable.OnPropertyChangedCallback
        public void onPropertyChanged(Observable observable, int i) {
            ViewDataBinding viewDataBindingA = this.a.a();
            if (viewDataBindingA == null) {
                return;
            }
            m<Observable> mVar = this.a;
            if (mVar.c != observable) {
                return;
            }
            ViewDataBinding.a(viewDataBindingA, mVar.b, observable, i);
        }
    }

    static {
        int i2 = Build.VERSION.SDK_INT;
        p = i2;
        q = i2 >= 16;
        r = new a();
        s = new b();
        t = new c();
        u = new d();
        v = new e();
        w = new ReferenceQueue<>();
        x = new f();
    }

    public ViewDataBinding(DataBindingComponent dataBindingComponent, View view2, int i2) {
        this.b = new g();
        this.c = false;
        this.d = false;
        this.mBindingComponent = dataBindingComponent;
        this.e = new m[i2];
        this.f = view2;
        if (Looper.myLooper() == null) {
            throw new IllegalStateException("DataBinding must be created in view's UI Thread");
        }
        if (q) {
            this.i = Choreographer.getInstance();
            this.j = new h();
        } else {
            this.j = null;
            this.k = new Handler(Looper.myLooper());
        }
    }

    public static DataBindingComponent a(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof DataBindingComponent) {
            return (DataBindingComponent) obj;
        }
        throw new IllegalArgumentException("The provided bindingComponent parameter must be an instance of DataBindingComponent. See  https://issuetracker.google.com/issues/116541301 for details of why this parameter is not defined as DataBindingComponent");
    }

    public static ViewDataBinding bind(Object obj, View view2, int i2) {
        return DataBindingUtil.a(a(obj), view2, i2);
    }

    public static void executeBindingsOn(ViewDataBinding viewDataBinding) {
        viewDataBinding.a();
    }

    public static int getBuildSdkInt() {
        return p;
    }

    public static int getColorFromResource(View view2, int i2) {
        return view2.getContext().getColor(i2);
    }

    public static ColorStateList getColorStateListFromResource(View view2, int i2) {
        return view2.getContext().getColorStateList(i2);
    }

    public static Drawable getDrawableFromResource(View view2, int i2) {
        return view2.getContext().getDrawable(i2);
    }

    public static <K, T> T getFrom(Map<K, T> map, K k2) {
        if (map == null) {
            return null;
        }
        return map.get(k2);
    }

    public static <T> T getFromArray(T[] tArr, int i2) {
        if (tArr == null || i2 < 0 || i2 >= tArr.length) {
            return null;
        }
        return tArr[i2];
    }

    public static <T> T getFromList(List<T> list, int i2) {
        if (list == null || i2 < 0 || i2 >= list.size()) {
            return null;
        }
        return list.get(i2);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static <T extends ViewDataBinding> T inflateInternal(@NonNull LayoutInflater layoutInflater, int i2, @Nullable ViewGroup viewGroup, boolean z, @Nullable Object obj) {
        return (T) DataBindingUtil.inflate(layoutInflater, i2, viewGroup, z, a(obj));
    }

    public static Object[] mapBindings(DataBindingComponent dataBindingComponent, View view2, int i2, IncludedLayouts includedLayouts, SparseIntArray sparseIntArray) {
        Object[] objArr = new Object[i2];
        a(dataBindingComponent, view2, objArr, includedLayouts, sparseIntArray, true);
        return objArr;
    }

    public static boolean parse(String str, boolean z) {
        return str == null ? z : Boolean.parseBoolean(str);
    }

    public static int safeUnbox(Integer num) {
        if (num == null) {
            return 0;
        }
        return num.intValue();
    }

    public static void setBindingInverseListener(ViewDataBinding viewDataBinding, InverseBindingListener inverseBindingListener, PropertyChangedInverseListener propertyChangedInverseListener) {
        if (inverseBindingListener != propertyChangedInverseListener) {
            if (inverseBindingListener != null) {
                viewDataBinding.removeOnPropertyChangedCallback((PropertyChangedInverseListener) inverseBindingListener);
            }
            if (propertyChangedInverseListener != null) {
                viewDataBinding.addOnPropertyChangedCallback(propertyChangedInverseListener);
            }
        }
    }

    public static <T> void setTo(T[] tArr, int i2, T t2) {
        if (tArr == null || i2 < 0 || i2 >= tArr.length) {
            return;
        }
        tArr[i2] = t2;
    }

    public void addOnRebindCallback(@NonNull OnRebindCallback onRebindCallback) {
        if (this.g == null) {
            this.g = new CallbackRegistry<>(v);
        }
        this.g.add(onRebindCallback);
    }

    public void b() {
        executeBindings();
    }

    public void ensureBindingComponentIsNotNull(Class<?> cls) {
        if (this.mBindingComponent != null) {
            return;
        }
        StringBuilder sbA = g9.a("Required DataBindingComponent is null in class ");
        sbA.append(ViewDataBinding.class.getSimpleName());
        sbA.append(". A BindingAdapter in ");
        sbA.append(cls.getCanonicalName());
        sbA.append(" is not static and requires an object to use, retrieved from the DataBindingComponent. If you don't use an inflation method taking a DataBindingComponent, use DataBindingUtil.setDefaultComponent or make all BindingAdapter methods static.");
        throw new IllegalStateException(sbA.toString());
    }

    public abstract void executeBindings();

    public void executePendingBindings() {
        ViewDataBinding viewDataBinding = this.l;
        if (viewDataBinding == null) {
            a();
        } else {
            viewDataBinding.executePendingBindings();
        }
    }

    @Nullable
    public LifecycleOwner getLifecycleOwner() {
        return this.m;
    }

    public Object getObservedField(int i2) {
        m mVar = this.e[i2];
        if (mVar == null) {
            return null;
        }
        return mVar.c;
    }

    @Override // android.viewbinding.ViewBinding
    @NonNull
    public View getRoot() {
        return this.f;
    }

    public abstract boolean hasPendingBindings();

    public abstract void invalidateAll();

    public abstract boolean onFieldChange(int i2, Object obj, int i3);

    /* JADX WARN: Multi-variable type inference failed */
    public void registerTo(int i2, Object obj, i iVar) {
        if (obj == 0) {
            return;
        }
        m mVarA = this.e[i2];
        if (mVarA == null) {
            mVarA = iVar.a(this, i2);
            this.e[i2] = mVarA;
            LifecycleOwner lifecycleOwner = this.m;
            if (lifecycleOwner != null) {
                mVarA.a.a(lifecycleOwner);
            }
        }
        mVarA.b();
        mVarA.c = obj;
        mVarA.a.b(obj);
    }

    public void removeOnRebindCallback(@NonNull OnRebindCallback onRebindCallback) {
        CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry = this.g;
        if (callbackRegistry != null) {
            callbackRegistry.remove(onRebindCallback);
        }
    }

    public void requestRebind() {
        ViewDataBinding viewDataBinding = this.l;
        if (viewDataBinding != null) {
            viewDataBinding.requestRebind();
            return;
        }
        LifecycleOwner lifecycleOwner = this.m;
        if (lifecycleOwner == null || lifecycleOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED)) {
            synchronized (this) {
                if (this.c) {
                    return;
                }
                this.c = true;
                if (q) {
                    this.i.postFrameCallback(this.j);
                } else {
                    this.k.post(this.b);
                }
            }
        }
    }

    public void setContainedBinding(ViewDataBinding viewDataBinding) {
        if (viewDataBinding != null) {
            viewDataBinding.l = this;
        }
    }

    @MainThread
    public void setLifecycleOwner(@Nullable LifecycleOwner lifecycleOwner) {
        LifecycleOwner lifecycleOwner2 = this.m;
        if (lifecycleOwner2 == lifecycleOwner) {
            return;
        }
        if (lifecycleOwner2 != null) {
            lifecycleOwner2.getLifecycle().removeObserver(this.n);
        }
        this.m = lifecycleOwner;
        if (lifecycleOwner != null) {
            if (this.n == null) {
                this.n = new OnStartListener(this, null);
            }
            lifecycleOwner.getLifecycle().addObserver(this.n);
        }
        for (m mVar : this.e) {
            if (mVar != null) {
                mVar.a.a(lifecycleOwner);
            }
        }
    }

    public void setRootTag(View view2) {
        view2.setTag(R.id.dataBinding, this);
    }

    public abstract boolean setVariable(int i2, @Nullable Object obj);

    public void unbind() {
        for (m mVar : this.e) {
            if (mVar != null) {
                mVar.b();
            }
        }
    }

    public boolean unregisterFrom(int i2) {
        m mVar = this.e[i2];
        if (mVar != null) {
            return mVar.b();
        }
        return false;
    }

    public boolean updateLiveDataRegistration(int i2, LiveData<?> liveData) {
        this.o = true;
        try {
            return a(i2, liveData, u);
        } finally {
            this.o = false;
        }
    }

    public boolean updateRegistration(int i2, Observable observable) {
        return a(i2, observable, r);
    }

    public static class j implements Observer, k<LiveData<?>> {
        public final m<LiveData<?>> a;
        public LifecycleOwner b;

        public j(ViewDataBinding viewDataBinding, int i) {
            this.a = new m<>(viewDataBinding, i, this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(LiveData<?> liveData) {
            liveData.removeObserver(this);
        }

        @Override // android.databinding.ViewDataBinding.k
        public void b(LiveData<?> liveData) {
            LiveData<?> liveData2 = liveData;
            LifecycleOwner lifecycleOwner = this.b;
            if (lifecycleOwner != null) {
                liveData2.observe(lifecycleOwner, this);
            }
        }

        @Override // android.arch.lifecycle.Observer
        public void onChanged(@Nullable Object obj) {
            ViewDataBinding viewDataBindingA = this.a.a();
            if (viewDataBindingA != null) {
                m<LiveData<?>> mVar = this.a;
                ViewDataBinding.a(viewDataBindingA, mVar.b, mVar.c, 0);
            }
        }

        @Override // android.databinding.ViewDataBinding.k
        public void a(LifecycleOwner lifecycleOwner) {
            LiveData<?> liveData = this.a.c;
            if (liveData != null) {
                if (this.b != null) {
                    liveData.removeObserver(this);
                }
                if (lifecycleOwner != null) {
                    liveData.observe(lifecycleOwner, this);
                }
            }
            this.b = lifecycleOwner;
        }
    }

    public static int b(String str, int i2) {
        int iCharAt = 0;
        while (i2 < str.length()) {
            iCharAt = (iCharAt * 10) + (str.charAt(i2) - '0');
            i2++;
        }
        return iCharAt;
    }

    public static byte parse(String str, byte b2) {
        try {
            return Byte.parseByte(str);
        } catch (NumberFormatException unused) {
            return b2;
        }
    }

    public static long safeUnbox(Long l2) {
        if (l2 == null) {
            return 0L;
        }
        return l2.longValue();
    }

    public void setRootTag(View[] viewArr) {
        for (View view2 : viewArr) {
            view2.setTag(R.id.dataBinding, this);
        }
    }

    public boolean updateRegistration(int i2, ObservableList observableList) {
        return a(i2, observableList, s);
    }

    public static boolean getFromArray(boolean[] zArr, int i2) {
        if (zArr == null || i2 < 0 || i2 >= zArr.length) {
            return false;
        }
        return zArr[i2];
    }

    public static <T> T getFromList(SparseArray<T> sparseArray, int i2) {
        if (sparseArray == null || i2 < 0) {
            return null;
        }
        return sparseArray.get(i2);
    }

    public static Object[] mapBindings(DataBindingComponent dataBindingComponent, View[] viewArr, int i2, IncludedLayouts includedLayouts, SparseIntArray sparseIntArray) {
        Object[] objArr = new Object[i2];
        for (View view2 : viewArr) {
            a(dataBindingComponent, view2, objArr, includedLayouts, sparseIntArray, true);
        }
        return objArr;
    }

    public static short parse(String str, short s2) {
        try {
            return Short.parseShort(str);
        } catch (NumberFormatException unused) {
            return s2;
        }
    }

    public static short safeUnbox(Short sh) {
        if (sh == null) {
            return (short) 0;
        }
        return sh.shortValue();
    }

    public static void setTo(boolean[] zArr, int i2, boolean z) {
        if (zArr == null || i2 < 0 || i2 >= zArr.length) {
            return;
        }
        zArr[i2] = z;
    }

    public boolean updateRegistration(int i2, ObservableMap observableMap) {
        return a(i2, observableMap, t);
    }

    @TargetApi(16)
    public static <T> T getFromList(LongSparseArray<T> longSparseArray, int i2) {
        if (longSparseArray == null || i2 < 0) {
            return null;
        }
        return longSparseArray.get(i2);
    }

    public static int parse(String str, int i2) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException unused) {
            return i2;
        }
    }

    public static byte safeUnbox(Byte b2) {
        if (b2 == null) {
            return (byte) 0;
        }
        return b2.byteValue();
    }

    public final void a() {
        if (this.h) {
            requestRebind();
            return;
        }
        if (hasPendingBindings()) {
            this.h = true;
            this.d = false;
            CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry = this.g;
            if (callbackRegistry != null) {
                callbackRegistry.notifyCallbacks(this, 1, null);
                if (this.d) {
                    this.g.notifyCallbacks(this, 2, null);
                }
            }
            if (!this.d) {
                executeBindings();
                CallbackRegistry<OnRebindCallback, ViewDataBinding, Void> callbackRegistry2 = this.g;
                if (callbackRegistry2 != null) {
                    callbackRegistry2.notifyCallbacks(this, 3, null);
                }
            }
            this.h = false;
        }
    }

    public static byte getFromArray(byte[] bArr, int i2) {
        if (bArr == null || i2 < 0 || i2 >= bArr.length) {
            return (byte) 0;
        }
        return bArr[i2];
    }

    public static <T> T getFromList(android.support.v4.util.LongSparseArray<T> longSparseArray, int i2) {
        if (longSparseArray == null || i2 < 0) {
            return null;
        }
        return longSparseArray.get(i2);
    }

    public static long parse(String str, long j2) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException unused) {
            return j2;
        }
    }

    public static char safeUnbox(Character ch) {
        if (ch == null) {
            return (char) 0;
        }
        return ch.charValue();
    }

    public static void setTo(byte[] bArr, int i2, byte b2) {
        if (bArr == null || i2 < 0 || i2 >= bArr.length) {
            return;
        }
        bArr[i2] = b2;
    }

    public static boolean getFromList(SparseBooleanArray sparseBooleanArray, int i2) {
        if (sparseBooleanArray == null || i2 < 0) {
            return false;
        }
        return sparseBooleanArray.get(i2);
    }

    public static float parse(String str, float f2) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException unused) {
            return f2;
        }
    }

    public static double safeUnbox(Double d2) {
        if (d2 == null) {
            return 0.0d;
        }
        return d2.doubleValue();
    }

    public static short getFromArray(short[] sArr, int i2) {
        if (sArr == null || i2 < 0 || i2 >= sArr.length) {
            return (short) 0;
        }
        return sArr[i2];
    }

    public static int getFromList(SparseIntArray sparseIntArray, int i2) {
        if (sparseIntArray == null || i2 < 0) {
            return 0;
        }
        return sparseIntArray.get(i2);
    }

    public static double parse(String str, double d2) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException unused) {
            return d2;
        }
    }

    public static float safeUnbox(Float f2) {
        if (f2 == null) {
            return 0.0f;
        }
        return f2.floatValue();
    }

    public static void setTo(short[] sArr, int i2, short s2) {
        if (sArr == null || i2 < 0 || i2 >= sArr.length) {
            return;
        }
        sArr[i2] = s2;
    }

    @TargetApi(18)
    public static long getFromList(SparseLongArray sparseLongArray, int i2) {
        if (sparseLongArray == null || i2 < 0) {
            return 0L;
        }
        return sparseLongArray.get(i2);
    }

    public static char parse(String str, char c2) {
        return (str == null || str.isEmpty()) ? c2 : str.charAt(0);
    }

    public static boolean safeUnbox(Boolean bool) {
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    public static char getFromArray(char[] cArr, int i2) {
        if (cArr == null || i2 < 0 || i2 >= cArr.length) {
            return (char) 0;
        }
        return cArr[i2];
    }

    public static void setTo(char[] cArr, int i2, char c2) {
        if (cArr == null || i2 < 0 || i2 >= cArr.length) {
            return;
        }
        cArr[i2] = c2;
    }

    public static int getFromArray(int[] iArr, int i2) {
        if (iArr == null || i2 < 0 || i2 >= iArr.length) {
            return 0;
        }
        return iArr[i2];
    }

    public static void setTo(int[] iArr, int i2, int i3) {
        if (iArr == null || i2 < 0 || i2 >= iArr.length) {
            return;
        }
        iArr[i2] = i3;
    }

    public static long getFromArray(long[] jArr, int i2) {
        if (jArr == null || i2 < 0 || i2 >= jArr.length) {
            return 0L;
        }
        return jArr[i2];
    }

    public static void setTo(long[] jArr, int i2, long j2) {
        if (jArr == null || i2 < 0 || i2 >= jArr.length) {
            return;
        }
        jArr[i2] = j2;
    }

    public ViewDataBinding(Object obj, View view2, int i2) {
        this(a(obj), view2, i2);
    }

    public static float getFromArray(float[] fArr, int i2) {
        if (fArr == null || i2 < 0 || i2 >= fArr.length) {
            return 0.0f;
        }
        return fArr[i2];
    }

    public static void setTo(float[] fArr, int i2, float f2) {
        if (fArr == null || i2 < 0 || i2 >= fArr.length) {
            return;
        }
        fArr[i2] = f2;
    }

    public static double getFromArray(double[] dArr, int i2) {
        if (dArr == null || i2 < 0 || i2 >= dArr.length) {
            return 0.0d;
        }
        return dArr[i2];
    }

    public static void setTo(double[] dArr, int i2, double d2) {
        if (dArr == null || i2 < 0 || i2 >= dArr.length) {
            return;
        }
        dArr[i2] = d2;
    }

    public static ViewDataBinding a(View view2) {
        if (view2 != null) {
            return (ViewDataBinding) view2.getTag(R.id.dataBinding);
        }
        return null;
    }

    public static /* synthetic */ void a(ViewDataBinding viewDataBinding, int i2, Object obj, int i3) {
        if (!viewDataBinding.o && viewDataBinding.onFieldChange(i2, obj, i3)) {
            viewDataBinding.requestRebind();
        }
    }

    public static <T> void setTo(List<T> list, int i2, T t2) {
        if (list == null || i2 < 0 || i2 >= list.size()) {
            return;
        }
        list.set(i2, t2);
    }

    public static <T> void setTo(SparseArray<T> sparseArray, int i2, T t2) {
        if (sparseArray == null || i2 < 0 || i2 >= sparseArray.size()) {
            return;
        }
        sparseArray.put(i2, t2);
    }

    public final boolean a(int i2, Object obj, i iVar) {
        if (obj == null) {
            return unregisterFrom(i2);
        }
        m mVar = this.e[i2];
        if (mVar == null) {
            registerTo(i2, obj, iVar);
            return true;
        }
        if (mVar.c == obj) {
            return false;
        }
        unregisterFrom(i2);
        registerTo(i2, obj, iVar);
        return true;
    }

    @TargetApi(16)
    public static <T> void setTo(LongSparseArray<T> longSparseArray, int i2, T t2) {
        if (longSparseArray == null || i2 < 0 || i2 >= longSparseArray.size()) {
            return;
        }
        longSparseArray.put(i2, t2);
    }

    public static <T> void setTo(android.support.v4.util.LongSparseArray<T> longSparseArray, int i2, T t2) {
        if (longSparseArray == null || i2 < 0 || i2 >= longSparseArray.size()) {
            return;
        }
        longSparseArray.put(i2, t2);
    }

    public static void setTo(SparseBooleanArray sparseBooleanArray, int i2, boolean z) {
        if (sparseBooleanArray == null || i2 < 0 || i2 >= sparseBooleanArray.size()) {
            return;
        }
        sparseBooleanArray.put(i2, z);
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x018b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.databinding.DataBindingComponent r18, android.view.View r19, java.lang.Object[] r20, android.databinding.ViewDataBinding.IncludedLayouts r21, android.util.SparseIntArray r22, boolean r23) {
        /*
            Method dump skipped, instructions count: 439
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.databinding.ViewDataBinding.a(android.databinding.DataBindingComponent, android.view.View, java.lang.Object[], android.databinding.ViewDataBinding$IncludedLayouts, android.util.SparseIntArray, boolean):void");
    }

    public static void setTo(SparseIntArray sparseIntArray, int i2, int i3) {
        if (sparseIntArray == null || i2 < 0 || i2 >= sparseIntArray.size()) {
            return;
        }
        sparseIntArray.put(i2, i3);
    }

    @TargetApi(18)
    public static void setTo(SparseLongArray sparseLongArray, int i2, long j2) {
        if (sparseLongArray == null || i2 < 0 || i2 >= sparseLongArray.size()) {
            return;
        }
        sparseLongArray.put(i2, j2);
    }

    public static <K, T> void setTo(Map<K, T> map, K k2, T t2) {
        if (map == null) {
            return;
        }
        map.put(k2, t2);
    }

    public static boolean a(String str, int i2) {
        int length = str.length();
        if (length == i2) {
            return false;
        }
        while (i2 < length) {
            if (!Character.isDigit(str.charAt(i2))) {
                return false;
            }
            i2++;
        }
        return true;
    }
}
