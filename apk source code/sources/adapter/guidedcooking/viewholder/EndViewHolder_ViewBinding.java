package adapter.guidedcooking.viewholder;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import sound.SoundLength;

/* loaded from: classes.dex */
public class EndViewHolder_ViewBinding implements Unbinder {
    public EndViewHolder a;
    public View b;

    public class a extends DebouncingOnClickListener {
        public final /* synthetic */ EndViewHolder c;

        public a(EndViewHolder_ViewBinding endViewHolder_ViewBinding, EndViewHolder endViewHolder) {
            this.c = endViewHolder;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            EndViewHolder endViewHolder = this.c;
            if (endViewHolder == null) {
                throw null;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            endViewHolder.favoriteImageButton.setSelected(!r0.isSelected());
            endViewHolder.t.setRecipeIsFavorite(endViewHolder.favoriteImageButton.isSelected());
        }
    }

    @UiThread
    public EndViewHolder_ViewBinding(EndViewHolder endViewHolder, View view2) {
        this.a = endViewHolder;
        endViewHolder.backgroundImageView = (ImageView) Utils.findRequiredViewAsType(view2, R.id.item_end_recipe_image, "field 'backgroundImageView'", ImageView.class);
        View viewFindRequiredView = Utils.findRequiredView(view2, R.id.item_end_favorite_ib, "field 'favoriteImageButton' and method 'onClickFavorite'");
        endViewHolder.favoriteImageButton = (ImageButton) Utils.castView(viewFindRequiredView, R.id.item_end_favorite_ib, "field 'favoriteImageButton'", ImageButton.class);
        this.b = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new a(this, endViewHolder));
        endViewHolder.nameTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.item_end_name_tv, "field 'nameTextView'", TextView.class);
        endViewHolder.numberTopContainerRelativeLayout = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.item_step_top_container_rl, "field 'numberTopContainerRelativeLayout'", RelativeLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        EndViewHolder endViewHolder = this.a;
        if (endViewHolder == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        endViewHolder.backgroundImageView = null;
        endViewHolder.favoriteImageButton = null;
        endViewHolder.nameTextView = null;
        endViewHolder.numberTopContainerRelativeLayout = null;
        this.b.setOnClickListener(null);
        this.b = null;
    }
}
