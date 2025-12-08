package helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import de.silpion.mc2.R;
import defpackage.g9;
import helper.DialogHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import machineAdapter.impl.service.HardwareLEDService;
import machineAdapter.impl.service.LEDColor;
import model.Presets;
import org.apache.commons.lang3.StringUtils;
import sound.SoundLength;

/* loaded from: classes.dex */
public class DialogHelper {
    public static DialogHelper b;
    public Map<Integer, String> a = new a(this);

    @Retention(RetentionPolicy.SOURCE)
    public @interface Selection {
        public static final int CANCEL = 1;
        public static final int LEAVE = 3;
        public static final int SAVE = 2;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectionAlreadyCooking {
        public static final int CANCEL = 1;
        public static final int PREVIOUS = 2;
        public static final int START = 3;
    }

    public interface SelectionListener {
        void onSelect(int i);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectionRecipeChanged {
        public static final int CANCEL = 1;
        public static final int YES = 2;
    }

    public class a extends HashMap<Integer, String> {
        public a(DialogHelper dialogHelper) {
            App app = App.getInstance();
            put(1, app.getString(R.string.dialog_error_title_internal));
            put(2, app.getString(R.string.dialog_error_title_overvoltage));
            put(3, app.getString(R.string.dialog_error_title_undervoltage));
            put(4, app.getString(R.string.dialog_error_title_motor));
            put(5, app.getString(R.string.dialog_error_title_no_water));
            put(6, app.getString(R.string.dialog_error_title_default));
            put(7, app.getString(R.string.dialog_error_title_scale_overload));
            put(8, app.getString(R.string.dialog_error_title_no_bowl));
            put(9, app.getString(R.string.dialog_lid_open_hint));
            put(10, app.getString(R.string.dialog_lid_opened_during_cooking));
            put(11, app.getString(R.string.dialog_no_favorites));
            put(12, app.getString(R.string.dialog_error_title_motor_overheat));
            put(99, app.getString(R.string.dialog_warning_no_internet));
            put(98, app.getString(R.string.dialog_warning_not_logged_in));
            put(13, app.getString(R.string.dialog_error_title_max_runtime_exceeded));
        }
    }

    public static /* synthetic */ void b(SelectionListener selectionListener, RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        selectionListener.onSelect(2);
        relativeLayout.setVisibility(8);
        Log.w("DialogHelper", "saveGuidedCookingStepDialog - leave");
    }

    public static /* synthetic */ void c(SelectionListener selectionListener, RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        selectionListener.onSelect(3);
        relativeLayout.setVisibility(8);
        Log.w("DialogHelper", "saveGuidedCookingStepDialog - save");
    }

    public static /* synthetic */ void d(SelectionListener selectionListener, RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        selectionListener.onSelect(1);
        relativeLayout.setVisibility(8);
    }

    public static /* synthetic */ void e(SelectionListener selectionListener, RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        selectionListener.onSelect(2);
        relativeLayout.setVisibility(8);
    }

    public static /* synthetic */ void f(SelectionListener selectionListener, RelativeLayout relativeLayout, View view2) {
        Log.w("DialogHelper", "showStopDialog - cancel");
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        selectionListener.onSelect(1);
        relativeLayout.setVisibility(8);
    }

    public static /* synthetic */ void g(SelectionListener selectionListener, RelativeLayout relativeLayout, View view2) {
        Log.w("DialogHelper", "showStopDialog - leave");
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        selectionListener.onSelect(3);
        relativeLayout.setVisibility(8);
    }

    public static DialogHelper getInstance() {
        if (b == null) {
            b = new DialogHelper();
        }
        return b;
    }

    public final void a() {
        Log.w("DialogHelper", "turnLedForDialogOn");
        HardwareLEDService.getInstance().blinkLED(LEDColor.RED, 3000);
    }

    public void saveGuidedCookingStepDialog(Context context, final RelativeLayout relativeLayout, @NonNull final SelectionListener selectionListener) {
        App.getInstance().playSound(R.raw.info, SoundLength.LONG);
        a();
        relativeLayout.setVisibility(0);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.cancel_iv);
        ImageView imageView2 = (ImageView) relativeLayout.findViewById(R.id.leave_iv);
        ImageView imageView3 = (ImageView) relativeLayout.findViewById(R.id.save_iv);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: jk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.a(selectionListener, relativeLayout, view2);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: mk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.b(selectionListener, relativeLayout, view2);
            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() { // from class: nk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.c(selectionListener, relativeLayout, view2);
            }
        });
    }

    public void showAfterKneadingWarningDialog() {
        showDialogWithText(App.getInstance().getString(R.string.after_kneading_warning_text));
    }

    public void showDialogWithText(String str) {
        final RelativeLayout relativeLayout = (RelativeLayout) App.getInstance().getMainActivity().findViewById(R.id.dialog_general);
        a();
        relativeLayout.setVisibility(0);
        ((TextView) relativeLayout.findViewById(R.id.title)).setText("");
        TextView textView = (TextView) relativeLayout.findViewById(R.id.message);
        textView.setText(str);
        textView.setTextSize(20.0f);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.ok_bt);
        ((TextView) relativeLayout.findViewById(R.id.ok_tv)).setText(App.getInstance().getString(R.string.ok));
        imageView.setOnClickListener(new View.OnClickListener() { // from class: hk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.a(relativeLayout, view2);
            }
        });
    }

    public void showLidOpenDialog(boolean z) {
        Log.w("DialogHelper", "showLidOpenDialog >> cooking " + z);
        if (z) {
            showWarningDialog(10);
        } else {
            showWarningDialog(9);
        }
    }

    public void showRecipeChangedDialog(@NonNull final RelativeLayout relativeLayout, @NonNull final SelectionListener selectionListener) {
        App.getInstance().playSound(R.raw.info, SoundLength.LONG);
        a();
        Log.i("DialogHelper", "showRecipeChangedDialog: ");
        relativeLayout.setVisibility(0);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.cancel_iv);
        ImageView imageView2 = (ImageView) relativeLayout.findViewById(R.id.yes_iv);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: ok
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.d(selectionListener, relativeLayout, view2);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: lk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.e(selectionListener, relativeLayout, view2);
            }
        });
    }

    public void showStopDialog(@NonNull Context context, int i, String str, @NonNull final RelativeLayout relativeLayout, @NonNull final SelectionListener selectionListener) {
        App.getInstance().playSound(R.raw.info, SoundLength.LONG);
        a();
        String string = i != 2 ? i != 3 ? (i == 10 && (Presets.Tags.KNEADING.equals(str) || Presets.Tags.STEAMING.equals(str) || Presets.Tags.ROASTING.equals(str))) ? context.getString(R.string.dialog_cooking_stop) : "" : context.getString(R.string.dialog_cooking_stop) : context.getString(R.string.dialog_cooking_default_stop);
        ((TextView) relativeLayout.findViewById(R.id.title)).setText(string);
        relativeLayout.setVisibility(0);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.cancel_iv);
        ImageView imageView2 = (ImageView) relativeLayout.findViewById(R.id.leave_iv);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: pk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.f(selectionListener, relativeLayout, view2);
            }
        });
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: kk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.g(selectionListener, relativeLayout, view2);
            }
        });
    }

    public void showTurboDisabledDialog(@NonNull final RelativeLayout relativeLayout) {
        if (relativeLayout.getVisibility() == 0) {
            return;
        }
        a();
        App.getInstance().playSound(R.raw.info, SoundLength.SHORT);
        relativeLayout.setVisibility(0);
        ((ImageView) relativeLayout.findViewById(R.id.ok_bt)).setOnClickListener(new View.OnClickListener() { // from class: gk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.b(relativeLayout, view2);
            }
        });
    }

    public TextView showUserTimeoutDialog(@NonNull final RelativeLayout relativeLayout, @NonNull final SelectionListener selectionListener) {
        App.getInstance().playSound(R.raw.info, SoundLength.LONG);
        a();
        relativeLayout.setVisibility(0);
        ImageView imageView = (ImageView) relativeLayout.findViewById(R.id.ok_bt);
        TextView textView = (TextView) relativeLayout.findViewById(R.id.message);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: fk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.a(relativeLayout, selectionListener, view2);
            }
        });
        return textView;
    }

    public void showWarningDialog(final RelativeLayout relativeLayout, int[] iArr) {
        StringBuilder sbA = g9.a("showWarningDialog >> warnings ");
        sbA.append(StringUtils.join(iArr, '#'));
        Log.w("DialogHelper", sbA.toString());
        App.getInstance().playSound(R.raw.error, SoundLength.LONG);
        a();
        ArrayList arrayList = new ArrayList();
        for (int i : iArr) {
            if (this.a.containsKey(Integer.valueOf(i))) {
                if (i == 6) {
                    arrayList.add(this.a.get(Integer.valueOf(i)) + ", ERR_CODE: " + UsageLogger.getInstance().f);
                } else {
                    arrayList.add(this.a.get(Integer.valueOf(i)));
                }
            }
        }
        relativeLayout.setVisibility(0);
        ((ListView) relativeLayout.findViewById(R.id.warnings_listview)).setAdapter((ListAdapter) new ArrayAdapter(App.getInstance(), R.layout.dialog_warning_item, arrayList));
        ((ImageView) relativeLayout.findViewById(R.id.ok_bt)).setOnClickListener(new View.OnClickListener() { // from class: ik
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                DialogHelper.c(relativeLayout, view2);
            }
        });
    }

    public static /* synthetic */ void a(SelectionListener selectionListener, RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        selectionListener.onSelect(1);
        relativeLayout.setVisibility(8);
        Log.w("DialogHelper", "saveGuidedCookingStepDialog - cancel");
    }

    public static /* synthetic */ void b(RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        relativeLayout.setVisibility(8);
        Log.w("DialogHelper", "showTurboDisabledDialog - ok");
    }

    public static /* synthetic */ void c(RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        relativeLayout.setVisibility(8);
        Log.w("DialogHelper", "showWarningDialog - ok");
    }

    public static /* synthetic */ void a(RelativeLayout relativeLayout, SelectionListener selectionListener, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        relativeLayout.setVisibility(8);
        selectionListener.onSelect(1);
    }

    public static /* synthetic */ void a(RelativeLayout relativeLayout, View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        relativeLayout.setVisibility(8);
    }

    public void showWarningDialog(int i) {
        Log.w("DialogHelper", "showWarningDialog " + i);
        showWarningDialog((RelativeLayout) App.getInstance().getMainActivity().findViewById(R.id.dialog_warning), new int[]{i});
    }
}
