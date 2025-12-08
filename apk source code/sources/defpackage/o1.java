package defpackage;

import android.databinding.BaseObservable;
import android.databinding.Observable;

/* loaded from: classes.dex */
public abstract class o1 extends BaseObservable {

    public class a extends Observable.OnPropertyChangedCallback {
        public a() {
        }

        @Override // android.databinding.Observable.OnPropertyChangedCallback
        public void onPropertyChanged(Observable observable, int i) {
            o1.this.notifyChange();
        }
    }

    public o1() {
    }

    public o1(Observable... observableArr) {
        if (observableArr == null || observableArr.length == 0) {
            return;
        }
        a aVar = new a();
        for (Observable observable : observableArr) {
            observable.addOnPropertyChangedCallback(aVar);
        }
    }
}
