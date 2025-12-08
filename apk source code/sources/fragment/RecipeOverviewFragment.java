package fragment;

import adapter.RecipeCardAdapter;
import adapter.RecipeCardViewHolder;
import adapter.RecipeListAdapter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import db.DbHelper;
import db.model.Recipe;
import de.silpion.mc2.R;
import defpackage.cg;
import defpackage.g9;
import defpackage.uj;
import defpackage.vd;
import defpackage.wd;
import defpackage.wj;
import defpackage.xj;
import helper.ActionListener;
import helper.DialogHelper;
import helper.LayoutHelper;
import helper.SharedPreferencesHelper;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.MachineCallbackAdapter;
import mcapi.McApi;
import mcapi.ResponseListener;
import sound.SoundLength;
import view.RecipeInfoRelativeLayout;
import view.fastscroll.AlphabetAdapter;
import view.fastscroll.AlphabetItem;

/* loaded from: classes.dex */
public class RecipeOverviewFragment extends BaseFragment {
    public static final String G = RecipeOverviewFragment.class.getSimpleName();
    public RecipeListAdapter A;
    public View B;
    public LinearLayoutManager D;
    public RecyclerView E;

    @BindView(R.id.recipes_order_by_name)
    public ImageButton alphabetImageButton;

    @BindView(R.id.recipe_bottom_scroller)
    public RecyclerView alphabetRecyclerView;

    @BindView(R.id.recipes_order_by_category)
    public ImageButton categoriesImageButton;

    @BindView(R.id.recipes_show_favorites)
    public ImageButton favoriteImageButton;
    public AlphabetAdapter h;
    public boolean k;
    public boolean l;
    public boolean m;
    public RecipeCardAdapter o;
    public LinearLayoutManager p;
    public RecipeListAdapter q;
    public LinearLayoutManager r;

    @BindView(R.id.recipe_info_layout)
    public RecipeInfoRelativeLayout recipeInfo;

    @BindView(R.id.recipe_list_card)
    public ViewGroup recipeVerticalCard;

    @BindView(R.id.recipe_cards)
    public RecyclerView recipesCardView;

    @BindView(R.id.recipe_list_container)
    public LinearLayout recipesListContainer;

    @BindView(R.id.recipe_list)
    public RecyclerView recipesListView;
    public TextView s;

    @BindView(R.id.recipes_search)
    public ImageButton searchImageButton;

    @BindView(R.id.recipes_orientation_toggle)
    public ImageButton switchOrientationImageButton;
    public TextView t;
    public Switch u;
    public AlphabetAdapter v;
    public RecyclerView w;
    public String x;
    public ViewGroup y;
    public EditText z;
    public final int[] c = {20, -20};
    public final LinkedList<String> d = new LinkedList<>();
    public final List<Recipe> e = new ArrayList();
    public final Handler f = new Handler();
    public final Handler g = new Handler();
    public int i = 0;
    public boolean j = false;
    public Integer n = null;
    public Integer C = null;
    public final IMachineCallback F = new a();

    public class a extends MachineCallbackAdapter {
        public a() {
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialPushed(int i) {
            RecipeOverviewFragment.a(RecipeOverviewFragment.this, i);
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialTurned(int i, long j) {
            RecipeOverviewFragment.a(RecipeOverviewFragment.this, i, j);
        }
    }

    public class b extends RecyclerView.OnScrollListener {
        public b() {
        }

        @Override // android.support.v7.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (i == 0) {
                RecipeOverviewFragment.this.o.selectPosition(RecipeOverviewFragment.this.p.findFirstCompletelyVisibleItemPosition());
                if (RecipeOverviewFragment.this.alphabetImageButton.isSelected()) {
                    RecipeOverviewFragment recipeOverviewFragment = RecipeOverviewFragment.this;
                    recipeOverviewFragment.h.selectItem(recipeOverviewFragment.o.getSelectedPositionBubbleText());
                }
            }
        }
    }

    public class c extends RecyclerView.OnScrollListener {
        public c() {
        }

        @Override // android.support.v7.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            super.onScrollStateChanged(recyclerView, i);
            if (i == 0) {
                RecipeOverviewFragment recipeOverviewFragment = RecipeOverviewFragment.this;
                if (recipeOverviewFragment.k) {
                    return;
                }
                recipeOverviewFragment.n = null;
                int iFindFirstVisibleItemPosition = recipeOverviewFragment.r.findFirstVisibleItemPosition();
                int iFindLastVisibleItemPosition = RecipeOverviewFragment.this.r.findLastVisibleItemPosition() - iFindFirstVisibleItemPosition;
                int i2 = iFindLastVisibleItemPosition / 2;
                int selectedPosition = RecipeOverviewFragment.this.q.getSelectedPosition();
                int itemCount = ((RecipeOverviewFragment.this.q.getItemCount() - 1) - iFindLastVisibleItemPosition) + i2;
                String str = RecipeOverviewFragment.G;
                StringBuilder sb = new StringBuilder();
                sb.append("IDLE (Recipes) >> p ");
                sb.append(iFindFirstVisibleItemPosition);
                sb.append(" >> o ");
                sb.append(i2);
                sb.append(" >> p+o ");
                int i3 = iFindFirstVisibleItemPosition + i2;
                sb.append(i3);
                sb.append(" >> c ");
                sb.append(selectedPosition);
                sb.append(" >> l ");
                sb.append(itemCount);
                Log.i(str, sb.toString());
                if ((i3 > i2 || (selectedPosition > i2 && i3 == i2)) && (i3 < itemCount || (selectedPosition < itemCount && i3 == itemCount))) {
                    Log.i(RecipeOverviewFragment.G, "    >> to " + i3);
                    RecipeOverviewFragment.this.q.selectPosition(i3, false);
                }
                if (RecipeOverviewFragment.this.alphabetImageButton.isSelected()) {
                    RecipeOverviewFragment recipeOverviewFragment2 = RecipeOverviewFragment.this;
                    recipeOverviewFragment2.h.selectItem(recipeOverviewFragment2.q.getSelectedPositionBubbleText());
                }
            }
        }
    }

    public /* synthetic */ void a(int i, int i2) {
        Log.i(G, "alphabet item click >> " + i + " >> " + i2 + " >> Aa " + this.alphabetImageButton.isSelected() + " >> cat " + this.categoriesImageButton.isSelected());
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (this.alphabetImageButton.isSelected()) {
            if (this.l) {
                this.q.selectPosition(i2);
                return;
            } else {
                this.p.scrollToPositionWithOffset(i2, 0);
                return;
            }
        }
        if (this.categoriesImageButton.isSelected() || this.favoriteImageButton.isSelected()) {
            c(a(this.h.getItem(i).getWord(), this.favoriteImageButton.isSelected()));
        }
    }

    public /* synthetic */ void b(int i) {
        this.o.selectPosition(i);
        this.recipesCardView.scrollToPosition(i);
    }

    public /* synthetic */ void c(View view2) {
        a(this.q.getSelectedRecipe());
    }

    public /* synthetic */ void d(int i) {
        this.k = false;
        this.q.selectPosition(i);
        if (this.alphabetImageButton.isSelected()) {
            this.h.selectItem(this.q.getSelectedPositionBubbleText());
        }
    }

    public /* synthetic */ void e(View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        this.switchOrientationImageButton.setSelected(!r3.isSelected());
        String str = G;
        StringBuilder sbA = g9.a("switch >> list ");
        sbA.append(this.q.getSelectedPosition());
        sbA.append(" >> cards ");
        sbA.append(this.o.getSelectedPosition());
        Log.i(str, sbA.toString());
        a();
    }

    public /* synthetic */ void f(View view2) {
        if (this.alphabetImageButton.isSelected()) {
            return;
        }
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        this.categoriesImageButton.setSelected(false);
        this.favoriteImageButton.setSelected(false);
        this.alphabetImageButton.setSelected(true);
        c(DbHelper.getInstance().getRecipesByName(false, true, true, true));
        b(this.e);
    }

    public /* synthetic */ void g(View view2) {
        if (this.categoriesImageButton.isSelected()) {
            return;
        }
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        b((String) null);
    }

    public /* synthetic */ void h(View view2) {
        if (this.favoriteImageButton.isSelected()) {
            return;
        }
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        this.alphabetImageButton.setSelected(false);
        this.categoriesImageButton.setSelected(false);
        this.favoriteImageButton.setSelected(true);
        new Thread(new wd(this)).start();
    }

    public void hideRecipeDetail() {
        ImageButton imageButton;
        RecipeInfoRelativeLayout recipeInfoRelativeLayout = this.recipeInfo;
        if (recipeInfoRelativeLayout != null) {
            recipeInfoRelativeLayout.setVisibility(8);
            if (this.recipeInfo.hasChangedIsFavorite() && (imageButton = this.favoriteImageButton) != null && imageButton.isSelected()) {
                refreshData();
            }
        }
    }

    public final boolean i() {
        return SharedPreferencesHelper.getInstance().isInitialImportPending() || this.e.size() == 0;
    }

    public final void j() {
        List<Recipe> listA;
        boolean z = true;
        a(true);
        if (this.d.size() <= 0 || (listA = a(this.d.get(0), true)) == null || listA.size() <= 0) {
            z = false;
        } else {
            c(listA);
            a(this.d);
        }
        if (z) {
            return;
        }
        DialogHelper.getInstance().showWarningDialog(11);
        this.alphabetImageButton.performClick();
    }

    public final void k() {
        TextView textView = (TextView) this.a.findViewById(R.id.recipes_import_overlay);
        if (!i()) {
            textView.setVisibility(8);
            this.alphabetRecyclerView.setVisibility(0);
            a();
        } else {
            textView.setVisibility(0);
            this.alphabetRecyclerView.setVisibility(4);
            this.recipesListContainer.setVisibility(4);
            this.recipesCardView.setVisibility(4);
        }
    }

    public final void l() {
        Log.d(G, "startCardsSwing: !!");
        this.i = 0;
        Log.d(G, "continueCardsSwingIfApplicable: ");
        int i = this.i;
        int[] iArr = this.c;
        if (i < iArr.length) {
            this.recipesCardView.smoothScrollBy(iArr[i], 0);
            RecyclerView recyclerView = this.recipesCardView;
            uj ujVar = new uj(this);
            long j = FragmentManagerImpl.ANIM_DUR;
            recyclerView.postDelayed(ujVar, j);
            this.alphabetRecyclerView.smoothScrollBy(this.c[this.i], 0);
            this.alphabetRecyclerView.postDelayed(new uj(this), j);
            this.i++;
        }
    }

    public final void m() {
        String str = G;
        StringBuilder sbA = g9.a("updateSearchBy >> checked ");
        sbA.append(this.u.isChecked());
        Log.i(str, sbA.toString());
        if (this.u.isChecked()) {
            this.s.setTextColor(ContextCompat.getColor(getContext(), R.color.accent_dark));
            this.t.setTextColor(ContextCompat.getColor(getContext(), R.color.dialog_text_color));
            this.z.setHint(R.string.search_by_ingredient);
        } else {
            this.s.setTextColor(ContextCompat.getColor(getContext(), R.color.dialog_text_color));
            this.t.setTextColor(ContextCompat.getColor(getContext(), R.color.accent_dark));
            this.z.setHint(R.string.search_by_recipe);
        }
    }

    @Override // android.support.v4.app.Fragment
    @Nullable
    public View onCreateView(@NonNull LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        View viewInflate = layoutInflater.inflate(R.layout.fragment_recipes, viewGroup, false);
        this.a = viewInflate;
        return viewInflate;
    }

    @Override // fragment.BaseFragment, android.support.v4.app.Fragment
    public void onDestroyView() {
        super.onDestroyView();
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.F);
    }

    @Override // fragment.BaseFragment, android.support.v4.app.Fragment
    public void onViewCreated(@NonNull View view2, Bundle bundle) {
        Log.i(G, "onViewCreated");
        super.onViewCreated(view2, bundle);
        this.recipeInfo.setVisibility(8);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.F);
        a(false);
        String str = G;
        StringBuilder sbA = g9.a(" .. categoryList >> ");
        sbA.append(this.d);
        Log.i(str, sbA.toString());
        this.e.clear();
        this.e.addAll(DbHelper.getInstance().getRecipesByName(false, true, true, true));
        this.r = (LinearLayoutManager) this.recipesListView.getLayoutManager();
        this.p = (LinearLayoutManager) this.recipesCardView.getLayoutManager();
        AlphabetAdapter alphabetAdapter = new AlphabetAdapter();
        this.h = alphabetAdapter;
        this.alphabetRecyclerView.setAdapter(alphabetAdapter);
        this.h.setOnItemClickListener(new AlphabetAdapter.OnItemClickListener() { // from class: wf
            @Override // view.fastscroll.AlphabetAdapter.OnItemClickListener
            public final void OnItemClicked(int i, int i2) {
                this.a.a(i, i2);
            }
        });
        b(this.e);
        RecipeCardAdapter recipeCardAdapter = new RecipeCardAdapter(this.e);
        this.o = recipeCardAdapter;
        recipeCardAdapter.setRecipeOpenListener(new ActionListener() { // from class: we
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((Recipe) obj);
            }
        });
        this.recipesCardView.setAdapter(this.o);
        this.recipesCardView.addOnScrollListener(new b());
        this.recipeVerticalCard.setOnClickListener(new View.OnClickListener() { // from class: tf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.c(view3);
            }
        });
        RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this.e, new RecipeCardViewHolder(this.recipeVerticalCard));
        this.q = recipeListAdapter;
        recipeListAdapter.setRecipeOpenListener(new ActionListener() { // from class: we
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((Recipe) obj);
            }
        });
        this.recipesListView.setAdapter(this.q);
        this.recipesListView.addOnScrollListener(new c());
        this.q.selectPosition(0);
        this.o.selectPosition(0);
        this.h.selectPosition(0);
        this.searchImageButton.setOnClickListener(new View.OnClickListener() { // from class: ag
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.d(view3);
            }
        });
        this.switchOrientationImageButton.setOnClickListener(new View.OnClickListener() { // from class: jg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.e(view3);
            }
        });
        this.alphabetImageButton.setOnClickListener(new View.OnClickListener() { // from class: dg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.f(view3);
            }
        });
        this.alphabetImageButton.setSelected(true);
        this.categoriesImageButton.setOnClickListener(new View.OnClickListener() { // from class: kg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.g(view3);
            }
        });
        this.favoriteImageButton.setOnClickListener(new View.OnClickListener() { // from class: hg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.h(view3);
            }
        });
        ViewGroup viewGroup = (ViewGroup) this.a.findViewById(R.id.search_layout_root);
        this.y = viewGroup;
        viewGroup.setVisibility(8);
        RecyclerView recyclerView = (RecyclerView) this.a.findViewById(R.id.search_result_list);
        this.E = recyclerView;
        this.D = (LinearLayoutManager) recyclerView.getLayoutManager();
        View viewFindViewById = this.a.findViewById(R.id.search_result_card_container);
        this.B = viewFindViewById;
        viewFindViewById.setVisibility(4);
        ViewGroup viewGroup2 = (ViewGroup) this.a.findViewById(R.id.search_result_card);
        viewGroup2.setOnClickListener(new View.OnClickListener() { // from class: uf
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.b(view3);
            }
        });
        RecipeListAdapter recipeListAdapter2 = new RecipeListAdapter(new ArrayList(), new RecipeCardViewHolder(viewGroup2));
        this.A = recipeListAdapter2;
        recipeListAdapter2.setRecipeOpenListener(new ActionListener() { // from class: we
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((Recipe) obj);
            }
        });
        this.E.setAdapter(this.A);
        this.E.addOnScrollListener(new wj(this));
        this.t = (TextView) this.a.findViewById(R.id.search_by_name);
        this.s = (TextView) this.a.findViewById(R.id.search_by_ingredients);
        EditText editText = (EditText) this.a.findViewById(R.id.search_query_edit);
        this.z = editText;
        editText.setOnTouchListener(new View.OnTouchListener() { // from class: ig
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                return this.a.a(view3, motionEvent);
            }
        });
        this.w = (RecyclerView) this.a.findViewById(R.id.search_category_selector);
        g();
        this.z.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: bg
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                return this.a.a(textView, i, keyEvent);
            }
        });
        this.u = (Switch) this.a.findViewById(R.id.search_by_toggle);
        ((ViewGroup) this.a.findViewById(R.id.search_by_toggle_container)).setOnClickListener(new View.OnClickListener() { // from class: gg
            @Override // android.view.View.OnClickListener
            public final void onClick(View view3) {
                this.a.a(view3);
            }
        });
        this.z.addTextChangedListener(new xj(this));
        this.z.setOnFocusChangeListener(new View.OnFocusChangeListener() { // from class: yf
            @Override // android.view.View.OnFocusChangeListener
            public final void onFocusChange(View view3, boolean z) {
                this.a.a(view3, z);
            }
        });
        k();
    }

    public void refreshData() {
        ImageButton imageButton = this.favoriteImageButton;
        if (imageButton == null || !imageButton.isSelected()) {
            ImageButton imageButton2 = this.categoriesImageButton;
            if (imageButton2 == null || !imageButton2.isSelected()) {
                ImageButton imageButton3 = this.alphabetImageButton;
                if (imageButton3 != null && imageButton3.isSelected()) {
                    c(DbHelper.getInstance().getRecipesByName(false, true, true, true));
                    b(this.e);
                }
            } else {
                a(false);
                c(a(this.d.size() > 0 ? this.d.get(0) : "", false));
                a(this.d);
            }
        } else {
            new Thread(new wd(this)).start();
        }
        k();
        e();
    }

    public void reset() {
        hideRecipeDetail();
        this.recipeInfo.reset();
        h();
        this.y.setVisibility(8);
        c();
        e();
    }

    public void showNewRecipes() {
        b(getString(R.string.new_recipes_category_name));
    }

    public /* synthetic */ void c(int i) {
        this.recipesCardView.smoothScrollToPosition(i);
    }

    public final void b() {
        Log.d(G, "continueCardsSwingIfApplicable: ");
        int i = this.i;
        int[] iArr = this.c;
        if (i < iArr.length) {
            this.recipesCardView.smoothScrollBy(iArr[i], 0);
            RecyclerView recyclerView = this.recipesCardView;
            uj ujVar = new uj(this);
            long j = FragmentManagerImpl.ANIM_DUR;
            recyclerView.postDelayed(ujVar, j);
            this.alphabetRecyclerView.smoothScrollBy(this.c[this.i], 0);
            this.alphabetRecyclerView.postDelayed(new uj(this), j);
            this.i++;
        }
    }

    public final void c() {
        InputMethodManager inputMethodManager;
        if (this.z != null && (inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method")) != null) {
            inputMethodManager.hideSoftInputFromWindow(this.z.getWindowToken(), 0);
        }
        this.m = false;
    }

    public final void g() {
        if (this.v == null) {
            AlphabetAdapter alphabetAdapter = new AlphabetAdapter();
            this.v = alphabetAdapter;
            this.w.setAdapter(alphabetAdapter);
            this.v.setOnItemClickListener(new AlphabetAdapter.OnItemClickListener() { // from class: rf
                @Override // view.fastscroll.AlphabetAdapter.OnItemClickListener
                public final void OnItemClicked(int i, int i2) {
                    this.a.b(i, i2);
                }
            });
        }
        List<String> categoryNames = DbHelper.getInstance().getCategoryNames(false, true);
        categoryNames.add(0, getString(R.string.all));
        this.v.convertAndSetData(categoryNames);
        this.x = categoryNames.get(0);
    }

    public final void d() {
        this.m = true;
        EditText editText = this.z;
        if (editText != null) {
            editText.requestFocus();
            EditText editText2 = this.z;
            editText2.setSelection(editText2.getText().length(), this.z.getText().length());
        }
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(2, 1);
        }
    }

    public /* synthetic */ void e(int i) {
        this.k = false;
        this.A.selectPosition(i);
    }

    public final void c(List<Recipe> list) {
        if (list == null) {
            list = new ArrayList<>(DbHelper.getInstance().getRecipesByName(false, true, true, true));
        }
        this.e.clear();
        this.e.addAll(list);
        this.o.notifyDataSetChanged();
        this.o.selectPosition(0);
        this.p.scrollToPositionWithOffset(0, 0);
        this.q.notifyDataSetChanged();
        this.q.selectPosition(0);
        e();
    }

    public final void e() {
        this.recipesCardView.postDelayed(new Runnable() { // from class: tj
            @Override // java.lang.Runnable
            public final void run() {
                this.a.l();
            }
        }, 200L);
    }

    public final void h() {
        this.z.setText("");
        if (this.u.isChecked()) {
            this.u.setChecked(false);
            m();
        }
        g();
        this.A.setItems(null);
        this.B.setVisibility(4);
    }

    public final void f() {
        if (!SharedPreferencesHelper.getInstance().hasUserToken()) {
            this.a.post(new vd(this));
        } else {
            McApi.getInstance().fetchFavoritesFast(new ResponseListener() { // from class: eg
                @Override // mcapi.ResponseListener
                public final void receivedResponse(int i, Object obj, Exception exc) {
                    this.a.a(i, (Long[]) obj, exc);
                }
            });
        }
    }

    public final void b(List<Recipe> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i).getName();
            if (name != null && !name.trim().isEmpty()) {
                String strSubstring = name.substring(0, 1);
                if (!arrayList2.contains(strSubstring)) {
                    arrayList2.add(strSubstring);
                    arrayList.add(new AlphabetItem(i, strSubstring));
                }
            }
        }
        this.h.setData(arrayList);
    }

    public /* synthetic */ void d(View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        this.y.setVisibility(0);
        LayoutHelper.getInstance().setSelectedView(5);
        d();
        h();
    }

    public final void a() {
        boolean zIsSelected = this.switchOrientationImageButton.isSelected();
        this.l = zIsSelected;
        if (zIsSelected) {
            final int selectedPosition = this.o.getSelectedPosition();
            this.recipesListContainer.setVisibility(0);
            this.recipesCardView.setVisibility(4);
            this.a.postDelayed(new Runnable() { // from class: vf
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a(selectedPosition);
                }
            }, 200L);
            return;
        }
        final int selectedPosition2 = this.q.getSelectedPosition();
        this.recipesListContainer.setVisibility(4);
        this.recipesCardView.setVisibility(0);
        this.a.postDelayed(new Runnable() { // from class: fg
            @Override // java.lang.Runnable
            public final void run() {
                this.a.b(selectedPosition2);
            }
        }, 200L);
    }

    /* renamed from: c, reason: merged with bridge method [inline-methods] */
    public final synchronized void a(String str) {
        List<Recipe> recipesBySearchString;
        Log.i(G, "updateSearch >> " + str);
        if (TextUtils.isEmpty(str)) {
            if (this.j) {
                this.z.setCompoundDrawables(null, null, null, null);
                this.j = false;
            }
        } else if (!this.j) {
            this.z.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, ContextCompat.getDrawable(getContext(), R.drawable.ic_clear_grey_scaled), (Drawable) null);
            this.j = true;
        }
        String str2 = this.x;
        if (getString(R.string.all).equals(str2)) {
            str2 = "";
        }
        String strReplaceAll = Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        String strReplaceAll2 = Normalizer.normalize(str2, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
        if (this.u.isChecked()) {
            recipesBySearchString = DbHelper.getInstance().getRecipesByIngredientsFromSearchString(strReplaceAll, strReplaceAll2);
        } else {
            recipesBySearchString = DbHelper.getInstance().getRecipesBySearchString(strReplaceAll, strReplaceAll2);
        }
        Log.i(G, "Search >> query '" + strReplaceAll + "' category >> '" + strReplaceAll2 + "'");
        this.A.setItems(recipesBySearchString);
    }

    public /* synthetic */ void b(View view2) {
        a(this.A.getSelectedRecipe());
    }

    public /* synthetic */ void a(int i) {
        this.q.selectPosition(i, false);
        this.recipesListView.smoothScrollToPosition(i);
    }

    public final void b(String str) {
        String str2;
        this.alphabetImageButton.setSelected(false);
        this.favoriteImageButton.setSelected(false);
        this.categoriesImageButton.setSelected(true);
        a(false);
        if (TextUtils.isEmpty(str)) {
            str2 = this.d.size() > 0 ? this.d.get(0) : "";
        } else {
            str2 = str;
        }
        c(a(str2, false));
        a(this.d);
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.h.selectItem(str);
    }

    public final void a(boolean z) {
        this.d.clear();
        this.d.addAll(DbHelper.getInstance().getCategoryNames(z, true));
        if (z) {
            this.d.add(0, getString(R.string.all));
        }
    }

    public final void a(List<String> list) {
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i);
            if (str != null && !str.isEmpty() && !arrayList2.contains(str)) {
                arrayList2.add(str);
                arrayList.add(new AlphabetItem(i, str));
            }
        }
        this.h.setData(arrayList);
    }

    public /* synthetic */ void b(int i, int i2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        AlphabetItem item = this.v.getItem(i);
        this.x = item == null ? "" : item.getWord();
        EditText editText = this.z;
        editText.setText(editText.getText().toString());
        EditText editText2 = this.z;
        editText2.setSelection(editText2.getText().length());
        String str = G;
        StringBuilder sbA = g9.a("search category item click >> searchCategoryString ");
        sbA.append(this.x);
        Log.i(str, sbA.toString());
    }

    public /* synthetic */ boolean a(View view2, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1) {
            if (this.z.getCompoundDrawables()[2] != null && motionEvent.getRawX() >= this.z.getRight() - r3.getBounds().width()) {
                App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
                this.z.setText("");
            }
        } else if (motionEvent.getAction() == 0) {
            this.z.requestFocus();
            if (!this.m) {
                d();
            }
        }
        return true;
    }

    public /* synthetic */ boolean a(TextView textView, int i, KeyEvent keyEvent) {
        Log.i(G, "editorAction >> actionId " + i + " >> event " + keyEvent);
        if (i != 6) {
            return false;
        }
        this.B.setVisibility(0);
        this.z.clearFocus();
        c();
        return true;
    }

    public /* synthetic */ void a(View view2) {
        String str = G;
        StringBuilder sbA = g9.a("search toggle container Click >> checked ");
        sbA.append(this.u.isChecked());
        Log.i(str, sbA.toString());
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        this.u.setChecked(!r5.isChecked());
        m();
        String string = this.z.getText().toString();
        this.f.removeCallbacksAndMessages(null);
        this.f.postDelayed(new cg(this, string), 200L);
    }

    public /* synthetic */ void a(View view2, boolean z) {
        Log.i(G, "search focus " + z);
        if (z) {
            this.B.setVisibility(4);
        }
    }

    public static /* synthetic */ void a(RecipeOverviewFragment recipeOverviewFragment, int i) {
        if (recipeOverviewFragment.i()) {
            return;
        }
        if (LayoutHelper.getInstance().isViewSelected(2)) {
            if (recipeOverviewFragment.switchOrientationImageButton.isSelected() && i == 0) {
                recipeOverviewFragment.a(recipeOverviewFragment.q.getSelectedRecipe());
                return;
            }
            return;
        }
        if (LayoutHelper.getInstance().isViewSelected(5) && i == 0) {
            if (recipeOverviewFragment.B.getVisibility() == 0 && recipeOverviewFragment.A.getSelectedRecipe() != null) {
                recipeOverviewFragment.a(recipeOverviewFragment.A.getSelectedRecipe());
                return;
            }
            recipeOverviewFragment.B.setVisibility(0);
            recipeOverviewFragment.z.clearFocus();
            recipeOverviewFragment.c();
        }
    }

    public static /* synthetic */ void a(final RecipeOverviewFragment recipeOverviewFragment, int i, long j) {
        final int iMax;
        if (recipeOverviewFragment.i()) {
            return;
        }
        if (LayoutHelper.getInstance().isViewSelected(3)) {
            int i2 = (int) (50 * j * j);
            if (i == 0) {
                i2 *= -1;
            }
            recipeOverviewFragment.recipeInfo.handleJogDialScroll(i2);
            return;
        }
        if (LayoutHelper.getInstance().isViewSelected(2)) {
            if (!recipeOverviewFragment.switchOrientationImageButton.isSelected()) {
                if (1 == i) {
                    iMax = Math.min(recipeOverviewFragment.p.findLastVisibleItemPosition() + 1, recipeOverviewFragment.e.size() - 1);
                } else {
                    iMax = Math.max(recipeOverviewFragment.p.findFirstVisibleItemPosition() - 1, 0);
                }
                recipeOverviewFragment.a.post(new Runnable() { // from class: lg
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.c(iMax);
                    }
                });
                return;
            }
            Integer num = recipeOverviewFragment.n;
            int iIntValue = num != null ? num.intValue() : recipeOverviewFragment.q.getSelectedPosition();
            if (iIntValue < 0) {
                return;
            }
            final int iMax2 = Math.max(0, Math.min(((i == 1 ? 1 : -1) * ((int) j)) + iIntValue, recipeOverviewFragment.q.getItemCount() - 1));
            recipeOverviewFragment.n = Integer.valueOf(iMax2);
            recipeOverviewFragment.k = true;
            recipeOverviewFragment.recipesListView.smoothScrollToPosition(iMax2);
            recipeOverviewFragment.g.removeCallbacksAndMessages(null);
            recipeOverviewFragment.g.postDelayed(new Runnable() { // from class: sf
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.d(iMax2);
                }
            }, 150L);
            return;
        }
        if (LayoutHelper.getInstance().isViewSelected(5)) {
            Integer num2 = recipeOverviewFragment.C;
            int iIntValue2 = num2 != null ? num2.intValue() : recipeOverviewFragment.A.getSelectedPosition();
            if (iIntValue2 < 0) {
                return;
            }
            final int iMax3 = Math.max(0, Math.min(((i == 1 ? 1 : -1) * ((int) j)) + iIntValue2, recipeOverviewFragment.A.getItemCount() - 1));
            recipeOverviewFragment.C = Integer.valueOf(iMax3);
            Log.i(G, "JogDial >> speed " + j + " >> dir " + i + " >> pos0 " + iIntValue2 + " >> pos1 " + iMax3);
            recipeOverviewFragment.k = true;
            recipeOverviewFragment.E.smoothScrollToPosition(iMax3);
            recipeOverviewFragment.g.removeCallbacksAndMessages(null);
            recipeOverviewFragment.g.postDelayed(new Runnable() { // from class: zf
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.e(iMax3);
                }
            }, 150L);
        }
    }

    public final void a(Recipe recipe) {
        Log.i(G, "openRecipe >> recipe " + recipe);
        this.recipeInfo.reset();
        h();
        this.y.setVisibility(8);
        c();
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        if (recipe != null) {
            LayoutHelper.getInstance().setSelectedView(3);
            this.recipeInfo.showRecipeDetail(recipe, true);
        } else {
            LayoutHelper.getInstance().setSelectedView(2);
        }
    }

    public final List<Recipe> a(String str, boolean z) {
        if (getString(R.string.all).equals(str)) {
            str = "";
        }
        return DbHelper.getInstance().getRecipesByCategory(str, z);
    }

    public /* synthetic */ void a(Boolean bool) {
        this.a.post(new vd(this));
    }

    public /* synthetic */ void a(int i, Long[] lArr, Exception exc) {
        if (i == 200 && lArr != null && exc == null) {
            DbHelper.getInstance().replaceFavorites(lArr, new ActionListener() { // from class: xf
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    this.a.a((Boolean) obj);
                }
            });
        } else {
            this.a.post(new vd(this));
        }
    }
}
