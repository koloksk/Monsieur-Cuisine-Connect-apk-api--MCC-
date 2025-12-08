package fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import application.App;
import de.silpion.mc2.R;
import defpackage.dk;
import defpackage.g9;
import helper.LayoutHelper;
import java.util.ArrayList;
import java.util.List;
import machineAdapter.adapter.MachineCallbackAdapter;

/* loaded from: classes.dex */
public class TutorialFragment extends BaseFragment {
    public static final String g = TutorialFragment.class.getSimpleName();
    public final List<Integer> c = new ArrayList();
    public ViewPager d;
    public PagerAdapter e;
    public MachineCallbackAdapter f;

    public class a implements ViewPager.OnPageChangeListener {
        public a() {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int i) {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageScrolled(int i, float f, int i2) {
        }

        @Override // android.support.v4.view.ViewPager.OnPageChangeListener
        public void onPageSelected(int i) {
            Log.d(TutorialFragment.g, "onPageSelected >> position " + i);
            TutorialFragment.this.a();
        }
    }

    public class b extends FragmentStatePagerAdapter {
        public List<Integer> a;

        public /* synthetic */ b(TutorialFragment tutorialFragment, FragmentManager fragmentManager, List list, a aVar) {
            super(fragmentManager);
            this.a = list;
        }

        @Override // android.support.v4.view.PagerAdapter
        public int getCount() {
            return this.a.size();
        }

        @Override // android.support.v4.app.FragmentStatePagerAdapter
        public Fragment getItem(int i) {
            int iIntValue = this.a.get(i).intValue();
            TutorialViewPagerFragment tutorialViewPagerFragment = new TutorialViewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("ImageResID", iIntValue);
            tutorialViewPagerFragment.setArguments(bundle);
            return tutorialViewPagerFragment;
        }
    }

    public /* synthetic */ void a(View view2) throws Resources.NotFoundException {
        int currentItem = this.d.getCurrentItem();
        Log.d(g, "next click >> position " + currentItem);
        if (currentItem == this.e.getCount() - 1) {
            LayoutHelper.getInstance().closeTutorialFragment();
        } else {
            this.d.setCurrentItem(currentItem + 1, false);
        }
    }

    public /* synthetic */ void b(View view2) throws Resources.NotFoundException {
        String str = g;
        StringBuilder sbA = g9.a("back click >> position ");
        sbA.append(this.d.getCurrentItem());
        Log.d(str, sbA.toString());
        this.d.setCurrentItem(r3.getCurrentItem() - 1, false);
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws Resources.NotFoundException {
        ViewGroup viewGroup2 = (ViewGroup) layoutInflater.inflate(R.layout.fragment_tutorial, viewGroup, false);
        this.d = (ViewPager) viewGroup2.findViewById(R.id.tutorial_viewpager);
        b bVar = new b(this, getChildFragmentManager(), this.c, null);
        this.e = bVar;
        this.d.setAdapter(bVar);
        this.d.addOnPageChangeListener(new a());
        ((ImageView) viewGroup2.findViewById(R.id.tutorial_close_btn)).setOnClickListener(new View.OnClickListener() { // from class: lj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LayoutHelper.getInstance().closeTutorialFragment();
            }
        });
        ImageView imageView = (ImageView) viewGroup2.findViewById(R.id.tutorial_next_btn);
        imageView.setOnClickListener(new View.OnClickListener() { // from class: hj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws Resources.NotFoundException {
                this.a.a(view2);
            }
        });
        ImageView imageView2 = (ImageView) viewGroup2.findViewById(R.id.tutorial_back_btn);
        imageView2.setOnClickListener(new View.OnClickListener() { // from class: kj
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) throws Resources.NotFoundException {
                this.a.b(view2);
            }
        });
        imageView2.setImageResource(R.drawable.alphabet_background_color);
        imageView.setImageResource(R.drawable.alphabet_background_color);
        this.f = new dk(this);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.f);
        a();
        return viewGroup2;
    }

    @Override // fragment.BaseFragment, android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        if (this.f != null) {
            App.getInstance().getMachineAdapter().unregisterMachineCallback(this.f);
            this.f = null;
        }
    }

    public void setTutorialImages(List<Integer> list) {
        this.c.clear();
        if (list != null) {
            this.c.addAll(list);
        }
        PagerAdapter pagerAdapter = this.e;
        if (pagerAdapter != null) {
            pagerAdapter.notifyDataSetChanged();
        }
    }

    public final void a() {
        String str = g;
        StringBuilder sbA = g9.a("updatePagerButtons >> current position ");
        sbA.append(this.d.getCurrentItem());
        sbA.append(" >> max pos ");
        sbA.append(this.e.getCount() - 1);
        Log.d(str, sbA.toString());
        this.d.getCurrentItem();
        this.e.getCount();
    }
}
