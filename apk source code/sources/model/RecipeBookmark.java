package model;

import defpackage.g9;

/* loaded from: classes.dex */
public class RecipeBookmark {
    public long a;
    public int b;

    public RecipeBookmark(long j, int i) {
        this.a = j;
        this.b = i;
    }

    public int getPage() {
        return this.b;
    }

    public long getRecipeId() {
        return this.a;
    }

    public void setPage(int i) {
        this.b = i;
    }

    public void setRecipeId(long j) {
        this.a = j;
    }

    public String toString() {
        return Long.toString(this.a) + "." + Integer.toString(this.b);
    }

    public RecipeBookmark(String str) {
        if (str != null && str.matches("^\\d+.\\d+$")) {
            String[] strArrSplit = str.split("\\.");
            this.a = Long.parseLong(strArrSplit[0]);
            this.b = Integer.parseInt(strArrSplit[1]);
            return;
        }
        throw new IllegalArgumentException(g9.b("Wrong bookmark format ", str));
    }
}
