package android.databinding;

import android.util.Log;
import android.view.View;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes.dex */
public class MergedDataBinderMapper extends DataBinderMapper {
    public Set<Class<? extends DataBinderMapper>> a = new HashSet();
    public List<DataBinderMapper> b = new CopyOnWriteArrayList();
    public List<String> c = new CopyOnWriteArrayList();

    public final boolean a() throws ClassNotFoundException {
        boolean z = false;
        for (String str : this.c) {
            try {
                Class<?> cls = Class.forName(str);
                if (DataBinderMapper.class.isAssignableFrom(cls)) {
                    addMapper((DataBinderMapper) cls.newInstance());
                    this.c.remove(str);
                    z = true;
                }
            } catch (ClassNotFoundException unused) {
            } catch (IllegalAccessException e) {
                Log.e("MergedDataBinderMapper", "unable to add feature mapper for " + str, e);
            } catch (InstantiationException e2) {
                Log.e("MergedDataBinderMapper", "unable to add feature mapper for " + str, e2);
            }
        }
        return z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addMapper(DataBinderMapper dataBinderMapper) {
        if (this.a.add(dataBinderMapper.getClass())) {
            this.b.add(dataBinderMapper);
            Iterator<DataBinderMapper> it = dataBinderMapper.collectDependencies().iterator();
            while (it.hasNext()) {
                addMapper(it.next());
            }
        }
    }

    @Override // android.databinding.DataBinderMapper
    public String convertBrIdToString(int i) {
        Iterator<DataBinderMapper> it = this.b.iterator();
        while (it.hasNext()) {
            String strConvertBrIdToString = it.next().convertBrIdToString(i);
            if (strConvertBrIdToString != null) {
                return strConvertBrIdToString;
            }
        }
        if (a()) {
            return convertBrIdToString(i);
        }
        return null;
    }

    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View view2, int i) {
        Iterator<DataBinderMapper> it = this.b.iterator();
        while (it.hasNext()) {
            ViewDataBinding dataBinder = it.next().getDataBinder(dataBindingComponent, view2, i);
            if (dataBinder != null) {
                return dataBinder;
            }
        }
        if (a()) {
            return getDataBinder(dataBindingComponent, view2, i);
        }
        return null;
    }

    @Override // android.databinding.DataBinderMapper
    public int getLayoutId(String str) {
        Iterator<DataBinderMapper> it = this.b.iterator();
        while (it.hasNext()) {
            int layoutId = it.next().getLayoutId(str);
            if (layoutId != 0) {
                return layoutId;
            }
        }
        if (a()) {
            return getLayoutId(str);
        }
        return 0;
    }

    @Override // android.databinding.DataBinderMapper
    public ViewDataBinding getDataBinder(DataBindingComponent dataBindingComponent, View[] viewArr, int i) {
        Iterator<DataBinderMapper> it = this.b.iterator();
        while (it.hasNext()) {
            ViewDataBinding dataBinder = it.next().getDataBinder(dataBindingComponent, viewArr, i);
            if (dataBinder != null) {
                return dataBinder;
            }
        }
        if (a()) {
            return getDataBinder(dataBindingComponent, viewArr, i);
        }
        return null;
    }

    public void addMapper(String str) {
        this.c.add(str + ".DataBinderMapperImpl");
    }
}
