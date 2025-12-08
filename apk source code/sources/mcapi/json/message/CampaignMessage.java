package mcapi.json.message;

import defpackage.g9;
import helper.CommonUtils;
import helper.EncryptionChipServiceAdapter;
import java.util.UUID;

/* loaded from: classes.dex */
public class CampaignMessage {
    public int campaign_id;
    public String display_text;
    public String display_title;

    public String getURL() {
        String sESerial = EncryptionChipServiceAdapter.getInstance().getSESerial();
        UUID uuidFromString = UUID.fromString("6ba7b811-9dad-11d1-80b4-00c04fd430c8");
        StringBuilder sbA = g9.a(sESerial, "-");
        sbA.append(this.campaign_id);
        return g9.b("https://api2.monsieur-cuisine.com/mcc/api/v1/campaign?id=", CommonUtils.makeUUIDv3(uuidFromString, sbA.toString()).toString());
    }
}
