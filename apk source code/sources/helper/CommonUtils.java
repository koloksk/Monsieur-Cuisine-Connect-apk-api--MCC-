package helper;

import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

/* loaded from: classes.dex */
public class CommonUtils {
    public static Bitmap generateQrCodeBitmap(String str, int i) {
        HashMap map = new HashMap();
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            BitMatrix bitMatrixEncode = new QRCodeWriter().encode(str, BarcodeFormat.QR_CODE, i, i, map);
            int width = bitMatrixEncode.getWidth();
            int height = bitMatrixEncode.getHeight();
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int i2 = 0; i2 < width; i2++) {
                for (int i3 = 0; i3 < height; i3++) {
                    bitmapCreateBitmap.setPixel(i2, i3, bitMatrixEncode.get(i2, i3) ? ViewCompat.MEASURED_STATE_MASK : -1);
                }
            }
            return bitmapCreateBitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static UUID makeUUIDv3(UUID uuid, String str) {
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bytes.length + 16);
        byteBufferAllocate.putLong(uuid.getMostSignificantBits());
        byteBufferAllocate.putLong(uuid.getLeastSignificantBits());
        byteBufferAllocate.put(bytes);
        return UUID.nameUUIDFromBytes(byteBufferAllocate.array());
    }
}
