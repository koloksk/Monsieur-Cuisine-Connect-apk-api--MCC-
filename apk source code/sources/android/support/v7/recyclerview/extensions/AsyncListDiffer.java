package android.support.v7.recyclerview.extensions;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.AsyncDifferConfig;
import android.support.v7.util.AdapterListUpdateCallback;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public class AsyncListDiffer<T> {
    public final ListUpdateCallback a;
    public final AsyncDifferConfig<T> b;

    @Nullable
    public List<T> c;

    @NonNull
    public List<T> d = Collections.emptyList();
    public int e;

    public class a implements Runnable {
        public final /* synthetic */ List a;
        public final /* synthetic */ List b;
        public final /* synthetic */ int c;

        /* renamed from: android.support.v7.recyclerview.extensions.AsyncListDiffer$a$a, reason: collision with other inner class name */
        public class C0006a extends DiffUtil.Callback {
            public C0006a() {
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.support.v7.util.DiffUtil.Callback
            public boolean areContentsTheSame(int i, int i2) {
                return AsyncListDiffer.this.b.getDiffCallback().areContentsTheSame(a.this.a.get(i), a.this.b.get(i2));
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // android.support.v7.util.DiffUtil.Callback
            public boolean areItemsTheSame(int i, int i2) {
                return AsyncListDiffer.this.b.getDiffCallback().areItemsTheSame(a.this.a.get(i), a.this.b.get(i2));
            }

            @Override // android.support.v7.util.DiffUtil.Callback
            public int getNewListSize() {
                return a.this.b.size();
            }

            @Override // android.support.v7.util.DiffUtil.Callback
            public int getOldListSize() {
                return a.this.a.size();
            }
        }

        public class b implements Runnable {
            public final /* synthetic */ DiffUtil.DiffResult a;

            public b(DiffUtil.DiffResult diffResult) {
                this.a = diffResult;
            }

            @Override // java.lang.Runnable
            public void run() {
                a aVar = a.this;
                AsyncListDiffer asyncListDiffer = AsyncListDiffer.this;
                if (asyncListDiffer.e == aVar.c) {
                    List<T> list = aVar.b;
                    this.a.dispatchUpdatesTo(asyncListDiffer.a);
                    asyncListDiffer.c = list;
                    asyncListDiffer.d = Collections.unmodifiableList(list);
                }
            }
        }

        public a(List list, List list2, int i) {
            this.a = list;
            this.b = list2;
            this.c = i;
        }

        @Override // java.lang.Runnable
        public void run() {
            AsyncListDiffer.this.b.getMainThreadExecutor().execute(new b(DiffUtil.calculateDiff(new C0006a())));
        }
    }

    public AsyncListDiffer(@NonNull RecyclerView.Adapter adapter2, @NonNull DiffUtil.ItemCallback<T> itemCallback) {
        this.a = new AdapterListUpdateCallback(adapter2);
        this.b = new AsyncDifferConfig.Builder(itemCallback).build();
    }

    @NonNull
    public List<T> getCurrentList() {
        return this.d;
    }

    public void submitList(List<T> list) {
        List<T> list2 = this.c;
        if (list == list2) {
            return;
        }
        int i = this.e + 1;
        this.e = i;
        if (list == null) {
            this.a.onRemoved(0, list2.size());
            this.c = null;
            this.d = Collections.emptyList();
        } else {
            if (list2 != null) {
                this.b.getBackgroundThreadExecutor().execute(new a(list2, list, i));
                return;
            }
            this.a.onInserted(0, list.size());
            this.c = list;
            this.d = Collections.unmodifiableList(list);
        }
    }

    public AsyncListDiffer(@NonNull ListUpdateCallback listUpdateCallback, @NonNull AsyncDifferConfig<T> asyncDifferConfig) {
        this.a = listUpdateCallback;
        this.b = asyncDifferConfig;
    }
}
