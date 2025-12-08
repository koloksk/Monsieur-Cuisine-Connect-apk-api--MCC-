package helper;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import db.model.Recipe;
import defpackage.g9;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class RecipeAssetsHelper {
    public static Semaphore a = new Semaphore(1);

    public static boolean a(String str, List<String> list, AssetManager assetManager, String str2) throws IOException {
        Log.i("RecipeAssetsHelper", str);
        try {
            InputStream inputStreamOpen = assetManager.open("recipe_images/" + str);
            FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + str);
            byte[] bArr = new byte[1024];
            while (true) {
                int i = inputStreamOpen.read(bArr);
                if (i == -1) {
                    break;
                }
                fileOutputStream.write(bArr, 0, i);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            inputStreamOpen.close();
            Log.i("RecipeAssetsHelper", " > copied");
            if (!list.contains(str)) {
                return true;
            }
            list.remove(str);
            return true;
        } catch (Exception e) {
            Log.e("RecipeAssetsHelper", " > failed to copy asset.", e);
            return false;
        }
    }

    public static void clearRecipeAssets(Context context) {
        File file = new File(getImageFolderPath(context));
        if (file.exists()) {
            file.delete();
        }
    }

    public static boolean copyAssets(Context context, List<Recipe> list) {
        Log.i("RecipeAssetsHelper", "copyAssets");
        String imageFolderPath = getImageFolderPath(context);
        File file = new File(imageFolderPath);
        if (!file.exists()) {
            file.mkdir();
        }
        ArrayList arrayList = new ArrayList(Arrays.asList(file.list()));
        AssetManager assets = context.getAssets();
        Iterator<Recipe> it = list.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Recipe next = it.next();
            Log.i("RecipeAssetsHelper", next.getName());
            if (a(next.getLargeImageName(), arrayList, assets, imageFolderPath) && a(next.getSmallImageName(), arrayList, assets, imageFolderPath)) {
                i++;
            }
        }
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            String str = (String) it2.next();
            try {
                Log.i("RecipeAssetsHelper", "* deleting " + imageFolderPath + "/" + str);
                StringBuilder sb = new StringBuilder();
                sb.append(imageFolderPath);
                sb.append("/");
                sb.append(str);
                new File(sb.toString()).delete();
            } catch (Exception e) {
                Log.e("RecipeAssetsHelper", "!! failed to delete " + imageFolderPath + "/" + str, e);
            }
        }
        return i == list.size();
    }

    public static void deleteRecipeAssets(Context context, Recipe recipe) {
        String imageFolderPath = getImageFolderPath(context);
        try {
            new File(imageFolderPath + "/" + recipe.getLargeImageName()).delete();
        } catch (Exception unused) {
        }
        try {
            new File(imageFolderPath + "/" + recipe.getSmallImageName()).delete();
        } catch (Exception unused2) {
        }
    }

    public static boolean download(Context context, List<Recipe> list) {
        Log.i("RecipeAssetsHelper", "download");
        String imageFolderPath = getImageFolderPath(context);
        File file = new File(imageFolderPath);
        if (!file.exists()) {
            file.mkdir();
        }
        List listAsList = Arrays.asList(file.list());
        Picasso picassoBuild = new Picasso.Builder(context).downloader(new OkHttp3Downloader(context)).build();
        Iterator<Recipe> it = list.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Recipe next = it.next();
            StringBuilder sbA = g9.a("download image ");
            sbA.append(next.getRecipeType());
            sbA.append(StringUtils.SPACE);
            sbA.append(next.getName());
            Log.i("RecipeAssetsHelper", sbA.toString());
            Log.i("RecipeAssetsHelper", "download for recipe " + next.toString());
            if (a(next, next.getLargeImageName(), listAsList, picassoBuild, imageFolderPath) && a(next, next.getSmallImageName(), listAsList, picassoBuild, imageFolderPath)) {
                i++;
            }
        }
        StringBuilder sbA2 = g9.a("downloaded ", i, "/");
        sbA2.append(list.size());
        sbA2.append(" images");
        Log.i("RecipeAssetsHelper", sbA2.toString());
        return i == list.size();
    }

    public static String getImageFolderPath(Context context) {
        return context.getFilesDir().getAbsolutePath() + "/recipe_images";
    }

    public static File getLargeRecipeImageFile(String str, String str2) {
        return getRecipeImageFile(str, str2, false);
    }

    public static File getRecipeImageFile(String str, String str2, boolean z) {
        File file = new File(str2);
        return !file.exists() ? new File(g9.a(str, "/", z ? "small_placeholder.jpg" : "large_placeholder.jpg")) : file;
    }

    public static File getSmallRecipeImageFile(String str, String str2) {
        return getRecipeImageFile(str, str2, true);
    }

    public static String readTextFile(Context context, int i) throws IOException {
        Log.i("RecipeAssetsHelper", "readTextFile >> resId " + i);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getResources().openRawResource(i)));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String line = bufferedReader.readLine();
                if (line == null) {
                    return sb.toString();
                }
                sb.append(line);
                sb.append('\n');
            } catch (IOException e) {
                Log.e("RecipeAssetsHelper", "error on readTextFile: " + e);
                return null;
            }
        }
    }

    public static boolean a(Recipe recipe, String str, List<String> list, Picasso picasso, String str2) {
        boolean z = false;
        if (str == null || recipe.getImageBase() == null) {
            return false;
        }
        StringBuilder sb = new StringBuilder(str);
        if (!list.contains(str)) {
            sb.append(str + " > not present, yet.");
            Uri uri = Uri.parse(recipe.getImageBase() + str);
            Log.i("RecipeAssetsHelper", "🔒 downloadIfMissing");
            Log.i("RecipeAssetsHelper", "🔒 download from " + uri);
            try {
                try {
                    a.acquire();
                    Bitmap bitmap = picasso.load(uri).get();
                    FileOutputStream fileOutputStream = new FileOutputStream(str2 + "/" + str);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 75, fileOutputStream);
                    fileOutputStream.close();
                    a.release();
                    a.release();
                    z = true;
                } catch (IOException e) {
                    Log.e("RecipeAssetsHelper", e.getMessage());
                    SharedPreferencesHelper.getInstance().incrementFailedRequestCounter();
                    Log.i("RecipeAssetsHelper", sb.toString() + " > started download.");
                    return z;
                } catch (InterruptedException e2) {
                    Log.e("RecipeAssetsHelper", e2.getMessage());
                    Log.i("RecipeAssetsHelper", sb.toString() + " > started download.");
                    return z;
                } catch (Exception e3) {
                    Log.e("RecipeAssetsHelper", "Failed while downloading from: " + recipe.getImageBase() + StringUtils.SPACE + str);
                    Log.e("RecipeAssetsHelper", e3.getMessage());
                    Log.i("RecipeAssetsHelper", sb.toString() + " > started download.");
                    return z;
                }
                Log.i("RecipeAssetsHelper", sb.toString() + " > started download.");
                return z;
            } finally {
                a.release();
            }
        }
        Log.d("RecipeAssetsHelper", sb.toString() + " > exists, keep");
        return true;
    }
}
