package android.support.v4.app;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import defpackage.g9;
import fragment.TutorialFragment;
import fragment.TutorialViewPagerFragment;
import java.util.ArrayList;

/* loaded from: classes.dex */
public abstract class FragmentStatePagerAdapter extends PagerAdapter {
    public static final boolean DEBUG = false;
    public static final String TAG = "FragmentStatePagerAdapt";
    public final FragmentManager mFragmentManager;
    public FragmentTransaction mCurTransaction = null;
    public ArrayList<Fragment.SavedState> mSavedState = new ArrayList<>();
    public ArrayList<Fragment> mFragments = new ArrayList<>();
    public Fragment mCurrentPrimaryItem = null;

    public FragmentStatePagerAdapter(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment2 = (Fragment) obj;
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        while (this.mSavedState.size() <= i) {
            this.mSavedState.add(null);
        }
        this.mSavedState.set(i, fragment2.isAdded() ? this.mFragmentManager.saveFragmentInstanceState(fragment2) : null);
        this.mFragments.set(i, null);
        this.mCurTransaction.remove(fragment2);
    }

    @Override // android.support.v4.view.PagerAdapter
    public void finishUpdate(ViewGroup viewGroup) {
        FragmentTransaction fragmentTransaction = this.mCurTransaction;
        if (fragmentTransaction != null) {
            fragmentTransaction.commitNowAllowingStateLoss();
            this.mCurTransaction = null;
        }
    }

    public abstract Fragment getItem(int i);

    @Override // android.support.v4.view.PagerAdapter
    public Object instantiateItem(ViewGroup viewGroup, int i) {
        Fragment.SavedState savedState;
        Fragment fragment2;
        if (this.mFragments.size() > i && (fragment2 = this.mFragments.get(i)) != null) {
            return fragment2;
        }
        if (this.mCurTransaction == null) {
            this.mCurTransaction = this.mFragmentManager.beginTransaction();
        }
        int iIntValue = ((TutorialFragment.b) this).a.get(i).intValue();
        TutorialViewPagerFragment tutorialViewPagerFragment = new TutorialViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("ImageResID", iIntValue);
        tutorialViewPagerFragment.setArguments(bundle);
        if (this.mSavedState.size() > i && (savedState = this.mSavedState.get(i)) != null) {
            tutorialViewPagerFragment.setInitialSavedState(savedState);
        }
        while (this.mFragments.size() <= i) {
            this.mFragments.add(null);
        }
        tutorialViewPagerFragment.setMenuVisibility(false);
        tutorialViewPagerFragment.setUserVisibleHint(false);
        this.mFragments.set(i, tutorialViewPagerFragment);
        this.mCurTransaction.add(viewGroup.getId(), tutorialViewPagerFragment);
        return tutorialViewPagerFragment;
    }

    @Override // android.support.v4.view.PagerAdapter
    public boolean isViewFromObject(View view2, Object obj) {
        return ((Fragment) obj).getView() == view2;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void restoreState(Parcelable parcelable, ClassLoader classLoader) throws NumberFormatException {
        if (parcelable != null) {
            Bundle bundle = (Bundle) parcelable;
            bundle.setClassLoader(classLoader);
            Parcelable[] parcelableArray = bundle.getParcelableArray("states");
            this.mSavedState.clear();
            this.mFragments.clear();
            if (parcelableArray != null) {
                for (Parcelable parcelable2 : parcelableArray) {
                    this.mSavedState.add((Fragment.SavedState) parcelable2);
                }
            }
            for (String str : bundle.keySet()) {
                if (str.startsWith("f")) {
                    int i = Integer.parseInt(str.substring(1));
                    Fragment fragment2 = this.mFragmentManager.getFragment(bundle, str);
                    if (fragment2 != null) {
                        while (this.mFragments.size() <= i) {
                            this.mFragments.add(null);
                        }
                        fragment2.setMenuVisibility(false);
                        this.mFragments.set(i, fragment2);
                    } else {
                        Log.w(TAG, "Bad fragment at key " + str);
                    }
                }
            }
        }
    }

    @Override // android.support.v4.view.PagerAdapter
    public Parcelable saveState() {
        Bundle bundle;
        if (this.mSavedState.size() > 0) {
            bundle = new Bundle();
            Fragment.SavedState[] savedStateArr = new Fragment.SavedState[this.mSavedState.size()];
            this.mSavedState.toArray(savedStateArr);
            bundle.putParcelableArray("states", savedStateArr);
        } else {
            bundle = null;
        }
        for (int i = 0; i < this.mFragments.size(); i++) {
            Fragment fragment2 = this.mFragments.get(i);
            if (fragment2 != null && fragment2.isAdded()) {
                if (bundle == null) {
                    bundle = new Bundle();
                }
                this.mFragmentManager.putFragment(bundle, g9.b("f", i), fragment2);
            }
        }
        return bundle;
    }

    @Override // android.support.v4.view.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        Fragment fragment2 = (Fragment) obj;
        Fragment fragment3 = this.mCurrentPrimaryItem;
        if (fragment2 != fragment3) {
            if (fragment3 != null) {
                fragment3.setMenuVisibility(false);
                this.mCurrentPrimaryItem.setUserVisibleHint(false);
            }
            if (fragment2 != null) {
                fragment2.setMenuVisibility(true);
                fragment2.setUserVisibleHint(true);
            }
            this.mCurrentPrimaryItem = fragment2;
        }
    }

    @Override // android.support.v4.view.PagerAdapter
    public void startUpdate(ViewGroup viewGroup) {
        if (viewGroup.getId() != -1) {
            return;
        }
        throw new IllegalStateException("ViewPager with adapter " + this + " requires a view id");
    }
}
