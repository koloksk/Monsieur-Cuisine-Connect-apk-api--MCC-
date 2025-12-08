package helper;

import android.app.Activity;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import application.App;
import db.model.Recipe;
import de.silpion.mc2.R;
import defpackage.g9;
import fragment.FirstLaunchFragment;
import fragment.GuidedCookingFragment;
import fragment.MainFragment;
import fragment.RecipeOverviewFragment;
import fragment.ScaleCalibrationFragment;
import fragment.SettingsFragment;
import fragment.TutorialFragment;
import fragment.VideoPlayerFragment;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import view.PresetLayout;

/* loaded from: classes.dex */
public class LayoutHelper {
    public static LayoutHelper k;
    public FragmentManager c;
    public WeakReference<View> d;
    public ScaleCalibrationFragment e;
    public Integer f;
    public SettingsFragment g;
    public TutorialFragment i;
    public VideoPlayerFragment j;
    public final ConcurrentHashMap<Integer, View> a = new ConcurrentHashMap<>();
    public final List<ActionListener<Integer>> b = new ArrayList();
    public int h = -1;

    public static void applyFullscreen(@Nullable final View view2) {
        if (view2 != null) {
            view2.setSystemUiVisibility(4871);
            view2.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() { // from class: qk
                @Override // android.view.View.OnSystemUiVisibilityChangeListener
                public final void onSystemUiVisibilityChange(int i) {
                    view2.setSystemUiVisibility(4871);
                }
            });
        }
    }

    public static LayoutHelper getInstance() {
        if (k == null) {
            k = new LayoutHelper();
        }
        return k;
    }

    public static void keepScreenOn(Activity activity2) {
        if (activity2 == null || activity2.getWindow() == null) {
            return;
        }
        activity2.getWindow().addFlags(128);
    }

    public final void a(@IdRes Integer num, boolean z) {
        if (this.a.contains(num)) {
            this.a.get(num).setVisibility(z ? 0 : 8);
            return;
        }
        View viewA = a();
        if (viewA != null) {
            viewA.findViewById(num.intValue()).setVisibility(z ? 0 : 8);
        }
    }

    public void addViewSelectionListener(ActionListener<Integer> actionListener) {
        if (actionListener != null) {
            this.b.add(actionListener);
        }
    }

    public final String b(int i) {
        switch (i) {
            case 0:
            case 11:
                return "MainFragment";
            case 1:
                return "PresetOverviewLayout";
            case 2:
                return "RecipeOverviewFragment";
            case 3:
                return "RecipeDetail";
            case 4:
                return "GuidedCookingFragment";
            case 5:
                return "Search";
            case 6:
                return "FirstLaunchFragment";
            case 7:
                return "SettingsFragment";
            case 8:
                return "TutorialFragment";
            case 9:
                return "VideoPlayerFragment";
            case 10:
            default:
                return "UNKNOWN";
        }
    }

    public final void b() {
        if (this.b.size() > 0) {
            for (ActionListener<Integer> actionListener : this.b) {
                if (actionListener != null) {
                    actionListener.onAction(this.f);
                }
            }
        }
    }

    public void closeFirstLaunchFragment() {
        setSelectedView(0);
        View viewA = a();
        if (viewA != null) {
            viewA.findViewById(R.id.container_firstlaunch).setVisibility(8);
            viewA.findViewById(R.id.top_bar_layout).setVisibility(0);
        }
    }

    public void closeScaleCalibrationFragment() {
        setSelectedView(7);
        View viewA = a();
        if (viewA != null) {
            viewA.findViewById(R.id.container_scale_calibration).setVisibility(8);
            viewA.findViewById(R.id.top_bar_layout).setVisibility(0);
        }
        setSettingsFragmentVisibility(true);
    }

    public void closeTutorialFragment() {
        StringBuilder sbA = g9.a("closeTutorialFragment >> tutorial ");
        sbA.append(this.h);
        Log.d("LayoutHelper", sbA.toString());
        int i = this.h;
        if (i == 0) {
            SharedPreferencesHelper.getInstance().setManualTutorialShown();
            getInstance().setSelectedView(0);
        } else if (i != 1) {
            setSettingsFragmentVisibility(true);
        } else {
            SharedPreferencesHelper.getInstance().setPresetTutorialShown();
            View viewA = a();
            if (viewA != null) {
                getInstance().setPresetLayoutVisibility(viewA, true);
            }
            getInstance().setSelectedView(1);
        }
        a(false);
        this.h = -1;
        this.c.beginTransaction().detach(this.i).remove(this.i).commit();
        this.i = null;
    }

    public void closeVideoPlayerFragment() {
        Log.d("LayoutHelper", "closeVideoPlayerFragment");
        getInstance().setSettingsFragmentVisibility(true);
        View viewA = a();
        if (viewA != null) {
            viewA.findViewById(R.id.top_bar_layout).setVisibility(0);
        }
        this.c.beginTransaction().detach(this.j).remove(this.j).commit();
        this.j = null;
    }

    @CheckResult
    public <T extends Fragment> T findFragment(int i) {
        return (T) this.c.findFragmentByTag(b(i));
    }

    public int getSelectedView() {
        return this.f.intValue();
    }

    public String getSelectedViewTag() {
        return b(this.f.intValue());
    }

    public SettingsFragment getSettingsFragment() {
        return this.g;
    }

    public GuidedCookingFragment inflateGuidedCookingFragment() {
        GuidedCookingFragment guidedCookingFragment = new GuidedCookingFragment();
        a(R.id.container_guided_cooking, guidedCookingFragment, 4);
        return guidedCookingFragment;
    }

    public void inflateMainFragment() {
        a(R.id.container_main, new MainFragment(), 0);
    }

    public void inflateRecipesFragment() {
        a(R.id.container_recipes, new RecipeOverviewFragment(), 2);
    }

    public void invalidateSelectedView() {
        b();
    }

    public boolean isRecipeOpened() {
        GuidedCookingFragment guidedCookingFragment = (GuidedCookingFragment) findFragment(4);
        return (guidedCookingFragment == null || !guidedCookingFragment.hasRecipe() || guidedCookingFragment.isFirstOrLastStepPosition()) ? false : true;
    }

    public boolean isVideoPlayerFragmentPlaying() {
        VideoPlayerFragment videoPlayerFragment = this.j;
        return videoPlayerFragment != null && videoPlayerFragment.isPlaying();
    }

    public boolean isViewSelected(int... iArr) {
        if (this.f == null) {
            return false;
        }
        for (int i : iArr) {
            if (this.f.intValue() == i) {
                return true;
            }
        }
        return false;
    }

    public void openFirstLaunchFragment() {
        a(R.id.container_firstlaunch, new FirstLaunchFragment(), 6);
        setSelectedView(6);
        a(Integer.valueOf(R.id.container_firstlaunch), true);
        View viewA = a();
        if (viewA != null) {
            viewA.findViewById(R.id.top_bar_layout).setVisibility(8);
        }
        a(R.id.container_firstlaunch);
    }

    public void openGuidedCooking(Recipe recipe) {
        Log.i("LayoutHelper", "openGuidedCooking -- for recipe " + recipe);
        GuidedCookingFragment guidedCookingFragmentInflateGuidedCookingFragment = (GuidedCookingFragment) findFragment(4);
        if (guidedCookingFragmentInflateGuidedCookingFragment == null) {
            Log.i("LayoutHelper", " .. inflating guided cooking fragment");
            guidedCookingFragmentInflateGuidedCookingFragment = inflateGuidedCookingFragment();
        }
        guidedCookingFragmentInflateGuidedCookingFragment.setRecipe(recipe);
        RecipeOverviewFragment recipeOverviewFragment = (RecipeOverviewFragment) findFragment(2);
        if (recipeOverviewFragment != null) {
            Log.i("LayoutHelper", "openGuidedCooking >> reset recipe overview.");
            recipeOverviewFragment.reset();
        } else {
            Log.i("LayoutHelper", "openGuidedCooking >> no recipe overview present.");
        }
        setSelectedView(4);
        a(Integer.valueOf(R.id.container_guided_cooking), true);
        a(R.id.container_guided_cooking);
        getInstance().setSelectedView(4);
    }

    public void openRecipeOverview() {
        RecipeOverviewFragment recipeOverviewFragment = (RecipeOverviewFragment) findFragment(2);
        if (recipeOverviewFragment == null) {
            inflateRecipesFragment();
        } else {
            recipeOverviewFragment.reset();
        }
        setSelectedView(2);
        a(Integer.valueOf(R.id.container_recipes), true);
        a(R.id.container_recipes);
    }

    public void openScaleCalibrationFragment() {
        if (this.e == null) {
            ScaleCalibrationFragment scaleCalibrationFragment = new ScaleCalibrationFragment();
            this.e = scaleCalibrationFragment;
            a(R.id.container_scale_calibration, scaleCalibrationFragment, 10);
        }
        setSelectedView(10);
        View viewA = a();
        if (viewA != null) {
            viewA.findViewById(R.id.container_scale_calibration).setVisibility(0);
            viewA.findViewById(R.id.top_bar_layout).setVisibility(8);
        }
        a(R.id.container_scale_calibration);
    }

    public void openTutorialFragment(List<Integer> list) {
        if (this.i == null) {
            Log.i("LayoutHelper", "inflateTutorialFragment");
            TutorialFragment tutorialFragment = new TutorialFragment();
            this.i = tutorialFragment;
            a(R.id.container_tutorial, tutorialFragment, 8);
        }
        a(true);
        this.i.setTutorialImages(list);
        setSelectedView(8);
    }

    public void openVideoPlayerFragment(int i) {
        if (this.j == null) {
            VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
            this.j = videoPlayerFragment;
            a(R.id.container_videoplayer, videoPlayerFragment, 9);
        }
        this.j.setVideoResId(i);
        a(Integer.valueOf(R.id.container_videoplayer), true);
        a(R.id.container_videoplayer);
        View viewA = a();
        if (viewA != null) {
            viewA.findViewById(R.id.top_bar_layout).setVisibility(8);
        }
        setSelectedView(9);
    }

    public void removeViewSelectionListener(ActionListener<Integer> actionListener) {
        if (actionListener != null) {
            this.b.remove(actionListener);
        }
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.c = fragmentManager;
    }

    public void setMainActivityView(View view2) {
        this.d = new WeakReference<>(view2);
    }

    public void setPresetLayoutVisibility(@NonNull View view2, boolean z) {
        PresetLayout presetLayout = (PresetLayout) view2.findViewById(R.id.preset_layout);
        if (!z) {
            presetLayout.setVisibility(8);
            return;
        }
        setSelectedView(1);
        presetLayout.setVisibility(0);
        presetLayout.init();
        a(R.id.preset_layout);
    }

    public void setSelectedView(int i) {
        this.f = Integer.valueOf(i);
        if (i == 0 && SharedPreferencesHelper.getInstance().shouldShowManualTutorial()) {
            openTutorialFragment(ResourceHelper.getInstance().getTutorialList(App.getInstance()).get(0).getTutorialImages());
            this.h = 0;
        }
        if (i == 1 && SharedPreferencesHelper.getInstance().shouldShowPresetTutorial()) {
            openTutorialFragment(ResourceHelper.getInstance().getTutorialList(App.getInstance()).get(1).getTutorialImages());
            this.h = 1;
        }
        b();
    }

    public void setSettingsFragmentVisibility(boolean z) {
        View viewA = a();
        if (viewA == null) {
            return;
        }
        View viewFindViewById = viewA.findViewById(R.id.container_settings);
        if (!z) {
            SettingsFragment settingsFragment = this.g;
            if (settingsFragment != null) {
                settingsFragment.deactivate();
            }
            viewFindViewById.setVisibility(8);
            return;
        }
        if (this.g == null) {
            SettingsFragment settingsFragment2 = new SettingsFragment();
            this.g = settingsFragment2;
            a(R.id.container_settings, settingsFragment2, 7);
        }
        this.g.activate();
        viewFindViewById.setVisibility(0);
        setSelectedView(7);
        a(R.id.container_settings);
    }

    public final View a() {
        WeakReference<View> weakReference = this.d;
        if (weakReference != null) {
            return weakReference.get();
        }
        return null;
    }

    public final void a(@IdRes int i) {
        if (i != R.id.preset_layout) {
            a(Integer.valueOf(R.id.preset_layout), false);
        }
        if (i != R.id.container_settings) {
            a(Integer.valueOf(R.id.container_settings), false);
        }
        if (i != R.id.container_videoplayer) {
            a(Integer.valueOf(R.id.container_videoplayer), false);
        }
        if (i != R.id.container_tutorial) {
            a(Integer.valueOf(R.id.container_tutorial), false);
        }
        if (i != R.id.container_firstlaunch) {
            a(Integer.valueOf(R.id.container_firstlaunch), false);
        }
    }

    public final void a(int i, Fragment fragment2, int i2) {
        FragmentTransaction fragmentTransactionBeginTransaction = this.c.beginTransaction();
        fragmentTransactionBeginTransaction.replace(i, fragment2, b(i2));
        fragmentTransactionBeginTransaction.commitAllowingStateLoss();
        View viewA = a();
        if (viewA == null) {
            return;
        }
        this.a.put(Integer.valueOf(i), viewA.findViewById(i));
    }

    public final void a(boolean z) {
        View viewA = a();
        if (viewA == null) {
            return;
        }
        View viewFindViewById = viewA.findViewById(R.id.container_tutorial);
        Log.i("LayoutHelper", "setTutorialFragmentVisibility >> show " + z + " >> content " + viewFindViewById);
        if (z) {
            viewFindViewById.setVisibility(0);
            viewA.findViewById(R.id.top_bar_layout).setVisibility(8);
            setSelectedView(8);
            a(R.id.container_tutorial);
            return;
        }
        viewFindViewById.setVisibility(8);
        viewA.findViewById(R.id.top_bar_layout).setVisibility(0);
    }
}
