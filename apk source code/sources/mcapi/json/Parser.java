package mcapi.json;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import defpackage.g9;
import java.io.IOException;
import java.util.List;
import mcapi.json.recipes.Recipe;

/* loaded from: classes.dex */
public class Parser {
    public static final Gson a = new GsonBuilder().registerTypeAdapter(Integer.class, new i()).create();

    public static class a extends TypeToken<AuthenticationResponse> {
    }

    public static class b extends TypeToken<ConstraintResponse> {
    }

    public static class c extends TypeToken<FavoritesResponse> {
    }

    public static class d extends TypeToken<MachineConfigResponse> {
    }

    public static class e extends TypeToken<List<Recipe>> {
    }

    public static class f extends TypeToken<RecipeIdsResponse> {
    }

    public static class g extends TypeToken<List<RecipeResponse>> {
    }

    public static class h extends TypeToken<UserDataResponse> {
    }

    public static class i extends TypeAdapter<Number> {
        @Override // com.google.gson.TypeAdapter
        public Number read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            try {
                String strNextString = jsonReader.nextString();
                if ("".equals(strNextString)) {
                    return null;
                }
                return Integer.valueOf(Integer.parseInt(strNextString));
            } catch (NumberFormatException e) {
                throw new JsonSyntaxException(e);
            }
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, Number number) throws IOException {
            jsonWriter.value(number);
        }
    }

    public static AuthenticationResponse authenticationResponse(String str) {
        try {
            return (AuthenticationResponse) a.fromJson(str, new a().getType());
        } catch (Exception e2) {
            StringBuilder sbA = g9.a("failed to parse authentication response from: ");
            sbA.append(str.substring(0, Math.min(str.length(), 100)));
            sbA.append("..");
            Log.e("Parser", sbA.toString(), e2);
            return null;
        }
    }

    public static ConstraintResponse constraintResponse(String str) {
        try {
            return (ConstraintResponse) a.fromJson(str, new b().getType());
        } catch (Exception e2) {
            Log.e("Parser", "failed to parse constraint response from: " + str, e2);
            return null;
        }
    }

    public static FavoritesResponse favoritesResponse(String str) {
        try {
            return (FavoritesResponse) a.fromJson(str, new c().getType());
        } catch (Exception e2) {
            StringBuilder sbA = g9.a("failed to parse favorites from: ");
            sbA.append(str.substring(0, Math.min(str.length(), 100)));
            sbA.append("..");
            Log.e("Parser", sbA.toString(), e2);
            return null;
        }
    }

    public static MachineConfigResponse machineConfigResponse(String str) {
        try {
            return (MachineConfigResponse) a.fromJson(str, new d().getType());
        } catch (Exception e2) {
            Log.e("Parser", "failed to parse machine config response from: " + str, e2);
            return null;
        }
    }

    public static List<Recipe> parseRecipes(String str) {
        try {
            return (List) a.fromJson(str, new e().getType());
        } catch (Exception e2) {
            StringBuilder sbA = g9.a("failed to parse recipes from: ");
            sbA.append(str.substring(0, Math.min(str.length(), 100)));
            sbA.append("..");
            Log.e("Parser", sbA.toString(), e2);
            return null;
        }
    }

    public static RecipeIdsResponse recipeIdsResponse(String str) {
        try {
            return (RecipeIdsResponse) a.fromJson(str, new f().getType());
        } catch (Exception e2) {
            StringBuilder sbA = g9.a("failed to parse recipe ids from: ");
            sbA.append(str.substring(0, Math.min(str.length(), 100)));
            sbA.append("..");
            Log.e("Parser", sbA.toString(), e2);
            return null;
        }
    }

    public static List<RecipeResponse> recipeResponse(String str) {
        try {
            return (List) a.fromJson(str, new g().getType());
        } catch (Exception e2) {
            StringBuilder sbA = g9.a("failed to parse recipes from: ");
            sbA.append(str.substring(0, Math.min(str.length(), 100)));
            sbA.append("..");
            Log.e("Parser", sbA.toString(), e2);
            return null;
        }
    }

    public static String serialize(Object obj) {
        return a.toJson(obj);
    }

    public static UserDataResponse userDataResponse(String str) {
        try {
            return (UserDataResponse) a.fromJson(str, new h().getType());
        } catch (Exception e2) {
            Log.e("Parser", "failed to parse user data response from: " + str, e2);
            return null;
        }
    }
}
