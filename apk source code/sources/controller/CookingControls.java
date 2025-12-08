package controller;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import application.App;
import de.silpion.mc2.R;
import defpackage.g9;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import sound.SoundLength;

/* loaded from: classes.dex */
public class CookingControls {

    @NonNull
    public final ImageView a;
    public final StateChangeListener b;

    @NonNull
    public final ImageView c;
    public int d = 1;
    public boolean e = true;

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        public static final int IDLE = 1;
        public static final int PAUSE = 3;
        public static final int PLAY = 2;
        public static final int RESUME = 4;
        public static final int STOP = 5;
    }

    public interface StateChangeListener {
        void onStateChanged(int i);
    }

    public CookingControls(@NonNull RelativeLayout relativeLayout, StateChangeListener stateChangeListener) {
        this.b = stateChangeListener;
        this.a = (ImageView) relativeLayout.findViewById(R.id.play_iv);
        this.c = (ImageView) relativeLayout.findViewById(R.id.stop_iv);
        View.OnClickListener onClickListener = new View.OnClickListener() { // from class: rd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(view2);
            }
        };
        this.a.setOnClickListener(onClickListener);
        this.c.setOnClickListener(onClickListener);
    }

    public /* synthetic */ void a(View view2) {
        if (this.e) {
            Log.d("CookingControls", "onClick >> view " + view2);
            int id = view2.getId();
            if (id == R.id.play_iv) {
                App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
                a();
            } else {
                if (id != R.id.stop_iv) {
                    return;
                }
                App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
                this.d = 5;
                StateChangeListener stateChangeListener = this.b;
                if (stateChangeListener != null) {
                    stateChangeListener.onStateChanged(5);
                }
                b();
            }
        }
    }

    public final void b() {
        StringBuilder sbA = g9.a("updateButtonImages: current state >> ");
        sbA.append(this.d);
        Log.d("CookingControls", sbA.toString());
        int i = this.d;
        if (i != 1) {
            if (i != 2) {
                if (i == 3) {
                    this.a.setImageResource(R.drawable.button_play);
                    this.c.setImageResource(R.drawable.button_stop_playing);
                    return;
                } else if (i != 4) {
                    if (i != 5) {
                        return;
                    }
                }
            }
            this.a.setImageResource(R.drawable.button_pause);
            this.c.setImageResource(R.drawable.button_stop_playing);
            return;
        }
        this.a.setImageResource(R.drawable.button_play);
        this.c.setImageResource(R.drawable.button_stop);
    }

    public int getCurrentState() {
        return this.d;
    }

    public boolean isActive() {
        int i = this.d;
        return 2 == i || 3 == i || 4 == i;
    }

    public boolean isCooking() {
        int i = this.d;
        return 2 == i || 4 == i;
    }

    public void performPlayClick() {
        a();
    }

    public void performStopClick() {
        this.d = 5;
        StateChangeListener stateChangeListener = this.b;
        if (stateChangeListener != null) {
            stateChangeListener.onStateChanged(5);
        }
        b();
    }

    public void setCurrentState(int i) {
        Log.d("CookingControls", "setCurrentState >> " + i);
        this.d = i;
        b();
    }

    public void setEnabled(boolean z) {
        this.e = z;
    }

    public final void a() {
        int i = this.d;
        if (i == 1) {
            this.d = 2;
        } else if (i == 2) {
            this.d = 3;
        } else if (i == 3) {
            this.d = 4;
        } else if (i == 4) {
            this.d = 3;
        } else if (i == 5) {
            this.d = 2;
        }
        StateChangeListener stateChangeListener = this.b;
        if (stateChangeListener != null) {
            stateChangeListener.onStateChanged(this.d);
        }
        b();
    }
}
