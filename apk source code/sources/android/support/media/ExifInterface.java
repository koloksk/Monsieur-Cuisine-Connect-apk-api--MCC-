package android.support.media;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.InputDeviceCompat;
import android.util.Log;
import android.util.Pair;
import application.Configuration;
import defpackage.g9;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public class ExifInterface {
    public static final e[] A;
    public static final short ALTITUDE_ABOVE_SEA_LEVEL = 0;
    public static final short ALTITUDE_BELOW_SEA_LEVEL = 1;
    public static final e[] B;
    public static final e[] C;
    public static final int COLOR_SPACE_S_RGB = 1;
    public static final int COLOR_SPACE_UNCALIBRATED = 65535;
    public static final short CONTRAST_HARD = 2;
    public static final short CONTRAST_NORMAL = 0;
    public static final short CONTRAST_SOFT = 1;
    public static final e[] D;
    public static final int DATA_DEFLATE_ZIP = 8;
    public static final int DATA_HUFFMAN_COMPRESSED = 2;
    public static final int DATA_JPEG = 6;
    public static final int DATA_JPEG_COMPRESSED = 7;
    public static final int DATA_LOSSY_JPEG = 34892;
    public static final int DATA_PACK_BITS_COMPRESSED = 32773;
    public static final int DATA_UNCOMPRESSED = 1;
    public static final e E;
    public static final short EXPOSURE_MODE_AUTO = 0;
    public static final short EXPOSURE_MODE_AUTO_BRACKET = 2;
    public static final short EXPOSURE_MODE_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_ACTION = 6;
    public static final short EXPOSURE_PROGRAM_APERTURE_PRIORITY = 3;
    public static final short EXPOSURE_PROGRAM_CREATIVE = 5;
    public static final short EXPOSURE_PROGRAM_LANDSCAPE_MODE = 8;
    public static final short EXPOSURE_PROGRAM_MANUAL = 1;
    public static final short EXPOSURE_PROGRAM_NORMAL = 2;
    public static final short EXPOSURE_PROGRAM_NOT_DEFINED = 0;
    public static final short EXPOSURE_PROGRAM_PORTRAIT_MODE = 7;
    public static final short EXPOSURE_PROGRAM_SHUTTER_PRIORITY = 4;
    public static final e[] F;
    public static final short FILE_SOURCE_DSC = 3;
    public static final short FILE_SOURCE_OTHER = 0;
    public static final short FILE_SOURCE_REFLEX_SCANNER = 2;
    public static final short FILE_SOURCE_TRANSPARENT_SCANNER = 1;
    public static final short FLAG_FLASH_FIRED = 1;
    public static final short FLAG_FLASH_MODE_AUTO = 24;
    public static final short FLAG_FLASH_MODE_COMPULSORY_FIRING = 8;
    public static final short FLAG_FLASH_MODE_COMPULSORY_SUPPRESSION = 16;
    public static final short FLAG_FLASH_NO_FLASH_FUNCTION = 32;
    public static final short FLAG_FLASH_RED_EYE_SUPPORTED = 64;
    public static final short FLAG_FLASH_RETURN_LIGHT_DETECTED = 6;
    public static final short FLAG_FLASH_RETURN_LIGHT_NOT_DETECTED = 4;
    public static final short FORMAT_CHUNKY = 1;
    public static final short FORMAT_PLANAR = 2;
    public static final e[] G;
    public static final short GAIN_CONTROL_HIGH_GAIN_DOWN = 4;
    public static final short GAIN_CONTROL_HIGH_GAIN_UP = 2;
    public static final short GAIN_CONTROL_LOW_GAIN_DOWN = 3;
    public static final short GAIN_CONTROL_LOW_GAIN_UP = 1;
    public static final short GAIN_CONTROL_NONE = 0;
    public static final String GPS_DIRECTION_MAGNETIC = "M";
    public static final String GPS_DIRECTION_TRUE = "T";
    public static final String GPS_DISTANCE_KILOMETERS = "K";
    public static final String GPS_DISTANCE_MILES = "M";
    public static final String GPS_DISTANCE_NAUTICAL_MILES = "N";
    public static final String GPS_MEASUREMENT_2D = "2";
    public static final String GPS_MEASUREMENT_3D = "3";
    public static final short GPS_MEASUREMENT_DIFFERENTIAL_CORRECTED = 1;
    public static final String GPS_MEASUREMENT_INTERRUPTED = "V";
    public static final String GPS_MEASUREMENT_IN_PROGRESS = "A";
    public static final short GPS_MEASUREMENT_NO_DIFFERENTIAL = 0;
    public static final String GPS_SPEED_KILOMETERS_PER_HOUR = "K";
    public static final String GPS_SPEED_KNOTS = "N";
    public static final String GPS_SPEED_MILES_PER_HOUR = "M";
    public static final e[] H;
    public static final e[] I;
    public static final e[][] J;
    public static final e[] K;
    public static final e L;
    public static final String LATITUDE_NORTH = "N";
    public static final String LATITUDE_SOUTH = "S";
    public static final short LIGHT_SOURCE_CLOUDY_WEATHER = 10;
    public static final short LIGHT_SOURCE_COOL_WHITE_FLUORESCENT = 14;
    public static final short LIGHT_SOURCE_D50 = 23;
    public static final short LIGHT_SOURCE_D55 = 20;
    public static final short LIGHT_SOURCE_D65 = 21;
    public static final short LIGHT_SOURCE_D75 = 22;
    public static final short LIGHT_SOURCE_DAYLIGHT = 1;
    public static final short LIGHT_SOURCE_DAYLIGHT_FLUORESCENT = 12;
    public static final short LIGHT_SOURCE_DAY_WHITE_FLUORESCENT = 13;
    public static final short LIGHT_SOURCE_FINE_WEATHER = 9;
    public static final short LIGHT_SOURCE_FLASH = 4;
    public static final short LIGHT_SOURCE_FLUORESCENT = 2;
    public static final short LIGHT_SOURCE_ISO_STUDIO_TUNGSTEN = 24;
    public static final short LIGHT_SOURCE_OTHER = 255;
    public static final short LIGHT_SOURCE_SHADE = 11;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_A = 17;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_B = 18;
    public static final short LIGHT_SOURCE_STANDARD_LIGHT_C = 19;
    public static final short LIGHT_SOURCE_TUNGSTEN = 3;
    public static final short LIGHT_SOURCE_UNKNOWN = 0;
    public static final short LIGHT_SOURCE_WARM_WHITE_FLUORESCENT = 16;
    public static final short LIGHT_SOURCE_WHITE_FLUORESCENT = 15;
    public static final String LONGITUDE_EAST = "E";
    public static final String LONGITUDE_WEST = "W";
    public static final e M;
    public static final short METERING_MODE_AVERAGE = 1;
    public static final short METERING_MODE_CENTER_WEIGHT_AVERAGE = 2;
    public static final short METERING_MODE_MULTI_SPOT = 4;
    public static final short METERING_MODE_OTHER = 255;
    public static final short METERING_MODE_PARTIAL = 6;
    public static final short METERING_MODE_PATTERN = 5;
    public static final short METERING_MODE_SPOT = 3;
    public static final short METERING_MODE_UNKNOWN = 0;
    public static final HashMap<Integer, e>[] N;
    public static final HashMap<String, e>[] O;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    public static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    public static final HashSet<String> P;
    public static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    public static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    public static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    public static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    public static final HashMap<Integer, Integer> Q;
    public static final Charset R;
    public static final int REDUCED_RESOLUTION_IMAGE = 1;
    public static final short RENDERED_PROCESS_CUSTOM = 1;
    public static final short RENDERED_PROCESS_NORMAL = 0;
    public static final short RESOLUTION_UNIT_CENTIMETERS = 3;
    public static final short RESOLUTION_UNIT_INCHES = 2;
    public static final byte[] S;
    public static final short SATURATION_HIGH = 0;
    public static final short SATURATION_LOW = 0;
    public static final short SATURATION_NORMAL = 0;
    public static final short SCENE_CAPTURE_TYPE_LANDSCAPE = 1;
    public static final short SCENE_CAPTURE_TYPE_NIGHT = 3;
    public static final short SCENE_CAPTURE_TYPE_PORTRAIT = 2;
    public static final short SCENE_CAPTURE_TYPE_STANDARD = 0;
    public static final short SCENE_TYPE_DIRECTLY_PHOTOGRAPHED = 1;
    public static final short SENSITIVITY_TYPE_ISO_SPEED = 3;
    public static final short SENSITIVITY_TYPE_REI = 2;
    public static final short SENSITIVITY_TYPE_REI_AND_ISO = 6;
    public static final short SENSITIVITY_TYPE_SOS = 1;
    public static final short SENSITIVITY_TYPE_SOS_AND_ISO = 5;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI = 4;
    public static final short SENSITIVITY_TYPE_SOS_AND_REI_AND_ISO = 7;
    public static final short SENSITIVITY_TYPE_UNKNOWN = 0;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL = 5;
    public static final short SENSOR_TYPE_COLOR_SEQUENTIAL_LINEAR = 8;
    public static final short SENSOR_TYPE_NOT_DEFINED = 1;
    public static final short SENSOR_TYPE_ONE_CHIP = 2;
    public static final short SENSOR_TYPE_THREE_CHIP = 4;
    public static final short SENSOR_TYPE_TRILINEAR = 7;
    public static final short SENSOR_TYPE_TWO_CHIP = 3;
    public static final short SHARPNESS_HARD = 2;
    public static final short SHARPNESS_NORMAL = 0;
    public static final short SHARPNESS_SOFT = 1;
    public static final short SUBJECT_DISTANCE_RANGE_CLOSE_VIEW = 2;
    public static final short SUBJECT_DISTANCE_RANGE_DISTANT_VIEW = 3;
    public static final short SUBJECT_DISTANCE_RANGE_MACRO = 1;
    public static final short SUBJECT_DISTANCE_RANGE_UNKNOWN = 0;
    public static final Pattern T;
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BODY_SERIAL_NUMBER = "BodySerialNumber";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_CAMARA_OWNER_NAME = "CameraOwnerName";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GAMMA = "Gamma";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_H_POSITIONING_ERROR = "GPSHPositioningError";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    public static final String TAG_ISO_SPEED = "ISOSpeed";
    public static final String TAG_ISO_SPEED_LATITUDE_YYY = "ISOSpeedLatitudeyyy";
    public static final String TAG_ISO_SPEED_LATITUDE_ZZZ = "ISOSpeedLatitudezzz";

    @Deprecated
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LENS_MAKE = "LensMake";
    public static final String TAG_LENS_MODEL = "LensModel";
    public static final String TAG_LENS_SERIAL_NUMBER = "LensSerialNumber";
    public static final String TAG_LENS_SPECIFICATION = "LensSpecification";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOGRAPHIC_SENSITIVITY = "PhotographicSensitivity";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    public static final String TAG_RECOMMENDED_EXPOSURE_INDEX = "RecommendedExposureIndex";
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SENSITIVITY_TYPE = "SensitivityType";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STANDARD_OUTPUT_SENSITIVITY = "StandardOutputSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    public static final Pattern U;

    @Deprecated
    public static final int WHITEBALANCE_AUTO = 0;

    @Deprecated
    public static final int WHITEBALANCE_MANUAL = 1;
    public static final short WHITE_BALANCE_AUTO = 0;
    public static final short WHITE_BALANCE_MANUAL = 1;
    public static final short Y_CB_CR_POSITIONING_CENTERED = 1;
    public static final short Y_CB_CR_POSITIONING_CO_SITED = 2;
    public static SimpleDateFormat v;
    public static final e[] z;
    public final String a;
    public final AssetManager.AssetInputStream b;
    public int c;
    public final HashMap<String, d>[] d;
    public ByteOrder e;
    public boolean f;
    public int g;
    public int h;
    public byte[] i;
    public int j;
    public int k;
    public int l;
    public int m;
    public int n;
    public int o;
    public boolean p;
    public static final List<Integer> q = Arrays.asList(1, 6, 3, 8);
    public static final List<Integer> r = Arrays.asList(2, 7, 4, 5);
    public static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    public static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    public static final byte[] s = {-1, -40, -1};
    public static final byte[] t = {79, 76, 89, 77, 80, 0};
    public static final byte[] u = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    public static final String[] w = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE"};
    public static final int[] x = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    public static final byte[] y = {65, 83, 67, 73, 73, 0, 0, 0};

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public @interface IfdType {
    }

    public static class c extends FilterOutputStream {
        public final OutputStream a;
        public ByteOrder b;

        public c(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.a = outputStream;
            this.b = byteOrder;
        }

        public void a(short s) throws IOException {
            ByteOrder byteOrder = this.b;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.a.write((s >>> 0) & 255);
                this.a.write((s >>> 8) & 255);
            } else if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.a.write((s >>> 8) & 255);
                this.a.write((s >>> 0) & 255);
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr) throws IOException {
            this.a.write(bArr);
        }

        public void writeInt(int i) throws IOException {
            ByteOrder byteOrder = this.b;
            if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.a.write((i >>> 0) & 255);
                this.a.write((i >>> 8) & 255);
                this.a.write((i >>> 16) & 255);
                this.a.write((i >>> 24) & 255);
                return;
            }
            if (byteOrder == ByteOrder.BIG_ENDIAN) {
                this.a.write((i >>> 24) & 255);
                this.a.write((i >>> 16) & 255);
                this.a.write((i >>> 8) & 255);
                this.a.write((i >>> 0) & 255);
            }
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            this.a.write(bArr, i, i2);
        }
    }

    public static class f {
        public final long a;
        public final long b;

        public String toString() {
            return this.a + "/" + this.b;
        }

        public f(long j, long j2) {
            if (j2 == 0) {
                this.a = 0L;
                this.b = 1L;
            } else {
                this.a = j;
                this.b = j2;
            }
        }
    }

    static {
        int i = 4;
        int i2 = 3;
        int i3 = 2;
        a aVar = null;
        int i4 = 3;
        int i5 = 4;
        a aVar2 = null;
        int i6 = 3;
        int i7 = 4;
        a aVar3 = null;
        int i8 = 5;
        int i9 = 5;
        int i10 = 23;
        int i11 = 7;
        z = new e[]{new e(TAG_NEW_SUBFILE_TYPE, 254, i, aVar), new e(TAG_SUBFILE_TYPE, 255, i, aVar), new e(TAG_IMAGE_WIDTH, 256, i4, i5, aVar2), new e(TAG_IMAGE_LENGTH, InputDeviceCompat.SOURCE_KEYBOARD, 3, 4, null), new e(TAG_BITS_PER_SAMPLE, 258, i2, aVar), new e(TAG_COMPRESSION, 259, i2, aVar), new e(TAG_PHOTOMETRIC_INTERPRETATION, 262, i2, aVar), new e(TAG_IMAGE_DESCRIPTION, 270, i3, aVar), new e(TAG_MAKE, 271, i3, aVar), new e(TAG_MODEL, 272, i3, aVar), new e(TAG_STRIP_OFFSETS, 273, i4, i5, aVar2), new e(TAG_ORIENTATION, 274, i2, aVar), new e(TAG_SAMPLES_PER_PIXEL, 277, i2, aVar), new e(TAG_ROWS_PER_STRIP, 278, i6, i7, aVar3), new e(TAG_STRIP_BYTE_COUNTS, 279, i6, i7, aVar3), new e(TAG_X_RESOLUTION, 282, i8, aVar), new e(TAG_Y_RESOLUTION, 283, i8, aVar), new e(TAG_PLANAR_CONFIGURATION, 284, i2, aVar), new e(TAG_RESOLUTION_UNIT, 296, i2, aVar), new e(TAG_TRANSFER_FUNCTION, 301, i2, aVar), new e(TAG_SOFTWARE, 305, i3, aVar), new e(TAG_DATETIME, 306, i3, aVar), new e(TAG_ARTIST, 315, i3, aVar), new e(TAG_WHITE_POINT, 318, i9, aVar), new e(TAG_PRIMARY_CHROMATICITIES, 319, i9, aVar), new e("SubIFDPointer", 330, i, aVar), new e(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, i, aVar), new e(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i, aVar), new e(TAG_Y_CB_CR_COEFFICIENTS, 529, 5, aVar), new e(TAG_Y_CB_CR_SUB_SAMPLING, 530, i2, aVar), new e(TAG_Y_CB_CR_POSITIONING, 531, i2, aVar), new e(TAG_REFERENCE_BLACK_WHITE, 532, 5, aVar), new e(TAG_COPYRIGHT, 33432, i3, aVar), new e("ExifIFDPointer", 34665, i, aVar), new e("GPSInfoIFDPointer", 34853, i, aVar), new e(TAG_RW2_SENSOR_TOP_BORDER, i, i, aVar), new e(TAG_RW2_SENSOR_LEFT_BORDER, 5, i, aVar), new e(TAG_RW2_SENSOR_BOTTOM_BORDER, 6, i, aVar), new e(TAG_RW2_SENSOR_RIGHT_BORDER, i11, i, aVar), new e(TAG_RW2_ISO, i10, i2, aVar), new e(TAG_RW2_JPG_FROM_RAW, 46, i11, aVar)};
        int i12 = 5;
        int i13 = 7;
        int i14 = 5;
        int i15 = 10;
        int i16 = 5;
        int i17 = 7;
        int i18 = 3;
        int i19 = 4;
        a aVar4 = null;
        int i20 = 5;
        int i21 = 7;
        A = new e[]{new e(TAG_EXPOSURE_TIME, 33434, i12, aVar), new e(TAG_F_NUMBER, 33437, i12, aVar), new e(TAG_EXPOSURE_PROGRAM, 34850, i2, aVar), new e(TAG_SPECTRAL_SENSITIVITY, 34852, i3, aVar), new e(TAG_PHOTOGRAPHIC_SENSITIVITY, 34855, i2, aVar), new e(TAG_OECF, 34856, i13, aVar), new e(TAG_EXIF_VERSION, 36864, i3, aVar), new e(TAG_DATETIME_ORIGINAL, 36867, i3, aVar), new e(TAG_DATETIME_DIGITIZED, 36868, i3, aVar), new e(TAG_COMPONENTS_CONFIGURATION, 37121, i13, aVar), new e(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, i14, aVar), new e(TAG_SHUTTER_SPEED_VALUE, 37377, i15, aVar), new e(TAG_APERTURE_VALUE, 37378, i14, aVar), new e(TAG_BRIGHTNESS_VALUE, 37379, i15, aVar), new e(TAG_EXPOSURE_BIAS_VALUE, 37380, i15, aVar), new e(TAG_MAX_APERTURE_VALUE, 37381, i16, aVar), new e(TAG_SUBJECT_DISTANCE, 37382, i16, aVar), new e(TAG_METERING_MODE, 37383, i2, aVar), new e(TAG_LIGHT_SOURCE, 37384, i2, aVar), new e(TAG_FLASH, 37385, i2, aVar), new e(TAG_FOCAL_LENGTH, 37386, 5, aVar), new e(TAG_SUBJECT_AREA, 37396, i2, aVar), new e(TAG_MAKER_NOTE, 37500, i17, aVar), new e(TAG_USER_COMMENT, 37510, i17, aVar), new e(TAG_SUBSEC_TIME, 37520, i3, aVar), new e(TAG_SUBSEC_TIME_ORIGINAL, 37521, i3, aVar), new e(TAG_SUBSEC_TIME_DIGITIZED, 37522, i3, aVar), new e(TAG_FLASHPIX_VERSION, 40960, 7, aVar), new e(TAG_COLOR_SPACE, 40961, i2, aVar), new e(TAG_PIXEL_X_DIMENSION, 40962, i18, i19, aVar4), new e(TAG_PIXEL_Y_DIMENSION, 40963, i18, i19, aVar4), new e(TAG_RELATED_SOUND_FILE, 40964, i3, aVar), new e("InteroperabilityIFDPointer", 40965, 4, aVar), new e(TAG_FLASH_ENERGY, 41483, 5, aVar), new e(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, 7, aVar), new e(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, i20, aVar), new e(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, i20, aVar), new e(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, i2, aVar), new e(TAG_SUBJECT_LOCATION, 41492, i2, aVar), new e(TAG_EXPOSURE_INDEX, 41493, 5, aVar), new e(TAG_SENSING_METHOD, 41495, i2, aVar), new e(TAG_FILE_SOURCE, 41728, i21, aVar), new e(TAG_SCENE_TYPE, 41729, i21, aVar), new e(TAG_CFA_PATTERN, 41730, i21, aVar), new e(TAG_CUSTOM_RENDERED, 41985, i2, aVar), new e(TAG_EXPOSURE_MODE, 41986, i2, aVar), new e(TAG_WHITE_BALANCE, 41987, i2, aVar), new e(TAG_DIGITAL_ZOOM_RATIO, 41988, 5, aVar), new e(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, i2, aVar), new e(TAG_SCENE_CAPTURE_TYPE, 41990, i2, aVar), new e(TAG_GAIN_CONTROL, 41991, i2, aVar), new e(TAG_CONTRAST, 41992, i2, aVar), new e(TAG_SATURATION, 41993, i2, aVar), new e(TAG_SHARPNESS, 41994, i2, aVar), new e(TAG_DEVICE_SETTING_DESCRIPTION, 41995, 7, aVar), new e(TAG_SUBJECT_DISTANCE_RANGE, 41996, i2, aVar), new e(TAG_IMAGE_UNIQUE_ID, 42016, i3, aVar), new e(TAG_DNG_VERSION, 50706, 1, aVar), new e(TAG_DEFAULT_CROP_SIZE, 50720, i18, i19, aVar4)};
        int i22 = 1;
        int i23 = 5;
        int i24 = 5;
        int i25 = 5;
        int i26 = 7;
        B = new e[]{new e(TAG_GPS_VERSION_ID, 0, i22, aVar), new e(TAG_GPS_LATITUDE_REF, i22, i3, aVar), new e(TAG_GPS_LATITUDE, i3, i23, aVar), new e(TAG_GPS_LONGITUDE_REF, i2, i3, aVar), new e(TAG_GPS_LONGITUDE, 4, i23, aVar), new e(TAG_GPS_ALTITUDE_REF, i23, 1, aVar), new e(TAG_GPS_ALTITUDE, 6, i23, aVar), new e(TAG_GPS_TIMESTAMP, 7, i23, aVar), new e(TAG_GPS_SATELLITES, 8, i3, aVar), new e(TAG_GPS_STATUS, 9, i3, aVar), new e(TAG_GPS_MEASURE_MODE, 10, i3, aVar), new e(TAG_GPS_DOP, 11, i24, aVar), new e(TAG_GPS_SPEED_REF, 12, i3, aVar), new e(TAG_GPS_SPEED, 13, i24, aVar), new e(TAG_GPS_TRACK_REF, 14, i3, aVar), new e(TAG_GPS_TRACK, 15, i24, aVar), new e(TAG_GPS_IMG_DIRECTION_REF, 16, i3, aVar), new e(TAG_GPS_IMG_DIRECTION, 17, i24, aVar), new e(TAG_GPS_MAP_DATUM, 18, i3, aVar), new e(TAG_GPS_DEST_LATITUDE_REF, 19, i3, aVar), new e(TAG_GPS_DEST_LATITUDE, 20, 5, aVar), new e(TAG_GPS_DEST_LONGITUDE_REF, 21, i3, aVar), new e(TAG_GPS_DEST_LONGITUDE, 22, i25, aVar), new e(TAG_GPS_DEST_BEARING_REF, i10, i3, aVar), new e(TAG_GPS_DEST_BEARING, 24, i25, aVar), new e(TAG_GPS_DEST_DISTANCE_REF, 25, i3, aVar), new e(TAG_GPS_DEST_DISTANCE, 26, 5, aVar), new e(TAG_GPS_PROCESSING_METHOD, 27, i26, aVar), new e(TAG_GPS_AREA_INFORMATION, 28, i26, aVar), new e(TAG_GPS_DATESTAMP, 29, i3, aVar), new e(TAG_GPS_DIFFERENTIAL, 30, i2, aVar)};
        C = new e[]{new e(TAG_INTEROPERABILITY_INDEX, 1, i3, aVar)};
        int i27 = 4;
        int i28 = 5;
        int i29 = 5;
        int i30 = 4;
        int i31 = 4;
        D = new e[]{new e(TAG_NEW_SUBFILE_TYPE, 254, i27, aVar), new e(TAG_SUBFILE_TYPE, 255, i27, aVar), new e(TAG_THUMBNAIL_IMAGE_WIDTH, 256, i18, i19, aVar4), new e(TAG_THUMBNAIL_IMAGE_LENGTH, InputDeviceCompat.SOURCE_KEYBOARD, 3, 4, null), new e(TAG_BITS_PER_SAMPLE, 258, i2, aVar), new e(TAG_COMPRESSION, 259, i2, aVar), new e(TAG_PHOTOMETRIC_INTERPRETATION, 262, i2, aVar), new e(TAG_IMAGE_DESCRIPTION, 270, i3, aVar), new e(TAG_MAKE, 271, i3, aVar), new e(TAG_MODEL, 272, i3, aVar), new e(TAG_STRIP_OFFSETS, 273, i18, i19, aVar4), new e(TAG_ORIENTATION, 274, i2, aVar), new e(TAG_SAMPLES_PER_PIXEL, 277, i2, aVar), new e(TAG_ROWS_PER_STRIP, 278, i18, i19, aVar4), new e(TAG_STRIP_BYTE_COUNTS, 279, i18, i19, aVar4), new e(TAG_X_RESOLUTION, 282, i28, aVar), new e(TAG_Y_RESOLUTION, 283, i28, aVar), new e(TAG_PLANAR_CONFIGURATION, 284, i2, aVar), new e(TAG_RESOLUTION_UNIT, 296, i2, aVar), new e(TAG_TRANSFER_FUNCTION, 301, i2, aVar), new e(TAG_SOFTWARE, 305, i3, aVar), new e(TAG_DATETIME, 306, i3, aVar), new e(TAG_ARTIST, 315, i3, aVar), new e(TAG_WHITE_POINT, 318, i29, aVar), new e(TAG_PRIMARY_CHROMATICITIES, 319, i29, aVar), new e("SubIFDPointer", 330, i30, aVar), new e(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, i30, aVar), new e(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i30, aVar), new e(TAG_Y_CB_CR_COEFFICIENTS, 529, 5, aVar), new e(TAG_Y_CB_CR_SUB_SAMPLING, 530, i2, aVar), new e(TAG_Y_CB_CR_POSITIONING, 531, i2, aVar), new e(TAG_REFERENCE_BLACK_WHITE, 532, 5, aVar), new e(TAG_COPYRIGHT, 33432, i3, aVar), new e("ExifIFDPointer", 34665, i31, aVar), new e("GPSInfoIFDPointer", 34853, i31, aVar), new e(TAG_DNG_VERSION, 50706, 1, aVar), new e(TAG_DEFAULT_CROP_SIZE, 50720, i18, i19, aVar4)};
        E = new e(TAG_STRIP_OFFSETS, 273, i2, aVar);
        int i32 = 4;
        F = new e[]{new e(TAG_ORF_THUMBNAIL_IMAGE, 256, 7, aVar), new e("CameraSettingsIFDPointer", 8224, i32, aVar), new e("ImageProcessingIFDPointer", 8256, i32, aVar)};
        G = new e[]{new e(TAG_ORF_PREVIEW_IMAGE_START, InputDeviceCompat.SOURCE_KEYBOARD, i32, aVar), new e(TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, i32, aVar)};
        H = new e[]{new e(TAG_ORF_ASPECT_FRAME, 4371, i2, aVar)};
        e[] eVarArr = {new e(TAG_COLOR_SPACE, 55, i2, aVar)};
        I = eVarArr;
        e[] eVarArr2 = z;
        J = new e[][]{eVarArr2, A, B, C, D, eVarArr2, F, G, H, eVarArr};
        int i33 = 4;
        int i34 = 1;
        K = new e[]{new e("SubIFDPointer", 330, i33, aVar), new e("ExifIFDPointer", 34665, i33, aVar), new e("GPSInfoIFDPointer", 34853, i33, aVar), new e("InteroperabilityIFDPointer", 40965, i33, aVar), new e("CameraSettingsIFDPointer", 8224, i34, aVar), new e("ImageProcessingIFDPointer", 8256, i34, aVar)};
        L = new e(TAG_JPEG_INTERCHANGE_FORMAT, InputDeviceCompat.SOURCE_DPAD, i33, aVar);
        M = new e(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i33, aVar);
        e[][] eVarArr3 = J;
        N = new HashMap[eVarArr3.length];
        O = new HashMap[eVarArr3.length];
        P = new HashSet<>(Arrays.asList(TAG_F_NUMBER, TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP));
        Q = new HashMap<>();
        Charset charsetForName = Charset.forName(CharEncoding.US_ASCII);
        R = charsetForName;
        S = "Exif\u0000\u0000".getBytes(charsetForName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        v = simpleDateFormat;
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (int i35 = 0; i35 < J.length; i35++) {
            N[i35] = new HashMap<>();
            O[i35] = new HashMap<>();
            for (e eVar : J[i35]) {
                N[i35].put(Integer.valueOf(eVar.a), eVar);
                O[i35].put(eVar.b, eVar);
            }
        }
        Q.put(Integer.valueOf(K[0].a), 5);
        Q.put(Integer.valueOf(K[1].a), 1);
        Q.put(Integer.valueOf(K[2].a), 2);
        Q.put(Integer.valueOf(K[3].a), 3);
        Q.put(Integer.valueOf(K[4].a), 7);
        Q.put(Integer.valueOf(K[5].a), 8);
        T = Pattern.compile(".*[1-9].*");
        U = Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
    }

    public ExifInterface(@NonNull String str) throws Throwable {
        FileInputStream fileInputStream;
        this.d = new HashMap[J.length];
        this.e = ByteOrder.BIG_ENDIAN;
        if (str == null) {
            throw new IllegalArgumentException("filename cannot be null");
        }
        FileInputStream fileInputStream2 = null;
        this.b = null;
        this.a = str;
        try {
            fileInputStream = new FileInputStream(str);
        } catch (Throwable th) {
            th = th;
        }
        try {
            a((InputStream) fileInputStream);
            a((Closeable) fileInputStream);
        } catch (Throwable th2) {
            th = th2;
            fileInputStream2 = fileInputStream;
            a((Closeable) fileInputStream2);
            throw th;
        }
    }

    @Nullable
    public final d a(@NonNull String str) {
        if (TAG_ISO_SPEED_RATINGS.equals(str)) {
            str = TAG_PHOTOGRAPHIC_SENSITIVITY;
        }
        for (int i = 0; i < J.length; i++) {
            d dVar = this.d[i].get(str);
            if (dVar != null) {
                return dVar;
            }
        }
        return null;
    }

    public final void b(String str) {
        for (int i = 0; i < J.length; i++) {
            this.d[i].remove(str);
        }
    }

    public final void c(b bVar) throws Throwable {
        d dVar;
        a(bVar, bVar.available());
        b(bVar, 0);
        c(bVar, 0);
        c(bVar, 5);
        c(bVar, 4);
        a(0, 5);
        a(0, 4);
        a(5, 4);
        d dVar2 = this.d[1].get(TAG_PIXEL_X_DIMENSION);
        d dVar3 = this.d[1].get(TAG_PIXEL_Y_DIMENSION);
        if (dVar2 != null && dVar3 != null) {
            this.d[0].put(TAG_IMAGE_WIDTH, dVar2);
            this.d[0].put(TAG_IMAGE_LENGTH, dVar3);
        }
        if (this.d[4].isEmpty() && a((HashMap) this.d[5])) {
            HashMap<String, d>[] mapArr = this.d;
            mapArr[4] = mapArr[5];
            mapArr[5] = new HashMap<>();
        }
        if (!a((HashMap) this.d[4])) {
            Log.d("ExifInterface", "No image meets the size requirements of a thumbnail image.");
        }
        if (this.c != 8 || (dVar = this.d[1].get(TAG_MAKER_NOTE)) == null) {
            return;
        }
        b bVar2 = new b(dVar.c);
        bVar2.b = this.e;
        bVar2.a(6L);
        b(bVar2, 9);
        d dVar4 = this.d[9].get(TAG_COLOR_SPACE);
        if (dVar4 != null) {
            this.d[1].put(TAG_COLOR_SPACE, dVar4);
        }
    }

    public final void d(b bVar) throws Throwable {
        c(bVar);
        if (this.d[0].get(TAG_RW2_JPG_FROM_RAW) != null) {
            a(bVar, this.o, 5);
        }
        d dVar = this.d[0].get(TAG_RW2_ISO);
        d dVar2 = this.d[1].get(TAG_PHOTOGRAPHIC_SENSITIVITY);
        if (dVar == null || dVar2 != null) {
            return;
        }
        this.d[1].put(TAG_PHOTOGRAPHIC_SENSITIVITY, dVar);
    }

    public final ByteOrder e(b bVar) throws IOException {
        short s2 = bVar.readShort();
        if (s2 == 18761) {
            return ByteOrder.LITTLE_ENDIAN;
        }
        if (s2 == 19789) {
            return ByteOrder.BIG_ENDIAN;
        }
        StringBuilder sbA = g9.a("Invalid byte order: ");
        sbA.append(Integer.toHexString(s2));
        throw new IOException(sbA.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x006e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void f(android.support.media.ExifInterface.b r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 237
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.f(android.support.media.ExifInterface$b):void");
    }

    public void flipHorizontally() throws NumberFormatException {
        int i = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i = 2;
                break;
            case 2:
                break;
            case 3:
                i = 4;
                break;
            case 4:
                i = 3;
                break;
            case 5:
                i = 6;
                break;
            case 6:
                i = 5;
                break;
            case 7:
                i = 8;
                break;
            case 8:
                i = 7;
                break;
            default:
                i = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i));
    }

    public void flipVertically() throws NumberFormatException {
        int i = 1;
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 1:
                i = 4;
                break;
            case 2:
                i = 3;
                break;
            case 3:
                i = 2;
                break;
            case 4:
                break;
            case 5:
                i = 8;
                break;
            case 6:
                i = 7;
                break;
            case 7:
                i = 6;
                break;
            case 8:
                i = 5;
                break;
            default:
                i = 0;
                break;
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(i));
    }

    public double getAltitude(double d2) {
        double attributeDouble = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int attributeInt = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (attributeDouble < 0.0d || attributeInt < 0) {
            return d2;
        }
        return attributeDouble * (attributeInt != 1 ? 1 : -1);
    }

    @Nullable
    public String getAttribute(@NonNull String str) {
        d dVarA = a(str);
        if (dVarA != null) {
            if (!P.contains(str)) {
                return dVarA.c(this.e);
            }
            if (str.equals(TAG_GPS_TIMESTAMP)) {
                int i = dVarA.a;
                if (i != 5 && i != 10) {
                    StringBuilder sbA = g9.a("GPS Timestamp format is not rational. format=");
                    sbA.append(dVarA.a);
                    Log.w("ExifInterface", sbA.toString());
                    return null;
                }
                f[] fVarArr = (f[]) dVarA.d(this.e);
                if (fVarArr != null && fVarArr.length == 3) {
                    return String.format("%02d:%02d:%02d", Integer.valueOf((int) (fVarArr[0].a / fVarArr[0].b)), Integer.valueOf((int) (fVarArr[1].a / fVarArr[1].b)), Integer.valueOf((int) (fVarArr[2].a / fVarArr[2].b)));
                }
                StringBuilder sbA2 = g9.a("Invalid GPS Timestamp array. array=");
                sbA2.append(Arrays.toString(fVarArr));
                Log.w("ExifInterface", sbA2.toString());
                return null;
            }
            try {
                return Double.toString(dVarA.a(this.e));
            } catch (NumberFormatException unused) {
            }
        }
        return null;
    }

    public double getAttributeDouble(@NonNull String str, double d2) {
        d dVarA = a(str);
        if (dVarA == null) {
            return d2;
        }
        try {
            return dVarA.a(this.e);
        } catch (NumberFormatException unused) {
            return d2;
        }
    }

    public int getAttributeInt(@NonNull String str, int i) {
        d dVarA = a(str);
        if (dVarA == null) {
            return i;
        }
        try {
            return dVarA.b(this.e);
        } catch (NumberFormatException unused) {
            return i;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public long getDateTime() throws NumberFormatException {
        String attribute = getAttribute(TAG_DATETIME);
        if (attribute != null && T.matcher(attribute).matches()) {
            try {
                Date date = v.parse(attribute, new ParsePosition(0));
                if (date == null) {
                    return -1L;
                }
                long time = date.getTime();
                String attribute2 = getAttribute(TAG_SUBSEC_TIME);
                if (attribute2 == null) {
                    return time;
                }
                try {
                    long j = Long.parseLong(attribute2);
                    while (j > 1000) {
                        j /= 10;
                    }
                    return time + j;
                } catch (NumberFormatException unused) {
                    return time;
                }
            } catch (IllegalArgumentException unused2) {
            }
        }
        return -1L;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public long getGpsDateTime() {
        String attribute = getAttribute(TAG_GPS_DATESTAMP);
        String attribute2 = getAttribute(TAG_GPS_TIMESTAMP);
        if (attribute != null && attribute2 != null && (T.matcher(attribute).matches() || T.matcher(attribute2).matches())) {
            try {
                Date date = v.parse(attribute + ' ' + attribute2, new ParsePosition(0));
                if (date == null) {
                    return -1L;
                }
                return date.getTime();
            } catch (IllegalArgumentException unused) {
            }
        }
        return -1L;
    }

    @Deprecated
    public boolean getLatLong(float[] fArr) {
        double[] latLong = getLatLong();
        if (latLong == null) {
            return false;
        }
        fArr[0] = (float) latLong[0];
        fArr[1] = (float) latLong[1];
        return true;
    }

    public int getRotationDegrees() {
        switch (getAttributeInt(TAG_ORIENTATION, 1)) {
            case 3:
            case 4:
                return 180;
            case 5:
            case 8:
                return 270;
            case 6:
            case 7:
                return 90;
            default:
                return 0;
        }
    }

    @Nullable
    public byte[] getThumbnail() {
        int i = this.j;
        if (i == 6 || i == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    @Nullable
    public Bitmap getThumbnailBitmap() throws Throwable {
        if (!this.f) {
            return null;
        }
        if (this.i == null) {
            this.i = getThumbnailBytes();
        }
        int i = this.j;
        if (i == 6 || i == 7) {
            return BitmapFactory.decodeByteArray(this.i, 0, this.h);
        }
        if (i == 1) {
            int length = this.i.length / 3;
            int[] iArr = new int[length];
            for (int i2 = 0; i2 < length; i2++) {
                byte[] bArr = this.i;
                int i3 = i2 * 3;
                iArr[i2] = (bArr[i3] << 16) + 0 + (bArr[i3 + 1] << 8) + bArr[i3 + 2];
            }
            d dVar = this.d[4].get(TAG_IMAGE_LENGTH);
            d dVar2 = this.d[4].get(TAG_IMAGE_WIDTH);
            if (dVar != null && dVar2 != null) {
                return Bitmap.createBitmap(iArr, dVar2.b(this.e), dVar.b(this.e), Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Nullable
    public byte[] getThumbnailBytes() throws Throwable {
        InputStream fileInputStream;
        Closeable closeable = null;
        if (!this.f) {
            return null;
        }
        byte[] bArr = this.i;
        try {
            if (bArr != 0) {
                return bArr;
            }
            try {
                if (this.b != null) {
                    fileInputStream = this.b;
                    try {
                        if (!fileInputStream.markSupported()) {
                            Log.d("ExifInterface", "Cannot read thumbnail from inputstream without mark/reset support");
                            a((Closeable) fileInputStream);
                            return null;
                        }
                        fileInputStream.reset();
                    } catch (IOException e2) {
                        e = e2;
                        Log.d("ExifInterface", "Encountered exception while getting thumbnail", e);
                        a((Closeable) fileInputStream);
                        return null;
                    }
                } else {
                    fileInputStream = this.a != null ? new FileInputStream(this.a) : null;
                }
                if (fileInputStream == null) {
                    throw new FileNotFoundException();
                }
                if (fileInputStream.skip(this.g) != this.g) {
                    throw new IOException("Corrupted image");
                }
                byte[] bArr2 = new byte[this.h];
                if (fileInputStream.read(bArr2) != this.h) {
                    throw new IOException("Corrupted image");
                }
                this.i = bArr2;
                a((Closeable) fileInputStream);
                return bArr2;
            } catch (IOException e3) {
                e = e3;
                fileInputStream = null;
            } catch (Throwable th) {
                th = th;
                a(closeable);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            closeable = bArr;
        }
    }

    @Nullable
    public long[] getThumbnailRange() {
        if (this.f) {
            return new long[]{this.g, this.h};
        }
        return null;
    }

    public boolean hasThumbnail() {
        return this.f;
    }

    public boolean isFlipped() {
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        return attributeInt == 2 || attributeInt == 7 || attributeInt == 4 || attributeInt == 5;
    }

    public boolean isThumbnailCompressed() {
        int i = this.j;
        return i == 6 || i == 7;
    }

    public void resetOrientation() throws NumberFormatException {
        setAttribute(TAG_ORIENTATION, Integer.toString(1));
    }

    public void rotate(int i) throws NumberFormatException {
        if (i % 90 != 0) {
            throw new IllegalArgumentException("degree should be a multiple of 90");
        }
        int attributeInt = getAttributeInt(TAG_ORIENTATION, 1);
        if (q.contains(Integer.valueOf(attributeInt))) {
            int iIndexOf = ((i / 90) + q.indexOf(Integer.valueOf(attributeInt))) % 4;
            iIntValue = q.get(iIndexOf + (iIndexOf < 0 ? 4 : 0)).intValue();
        } else if (r.contains(Integer.valueOf(attributeInt))) {
            int iIndexOf2 = ((i / 90) + r.indexOf(Integer.valueOf(attributeInt))) % 4;
            iIntValue = r.get(iIndexOf2 + (iIndexOf2 < 0 ? 4 : 0)).intValue();
        }
        setAttribute(TAG_ORIENTATION, Integer.toString(iIntValue));
    }

    public void saveAttributes() throws Throwable {
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream;
        if (!this.p || this.c != 4) {
            throw new IOException("ExifInterface only supports saving attributes on JPEG formats.");
        }
        if (this.a == null) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        }
        this.i = getThumbnail();
        File file = new File(this.a + ".tmp");
        if (!new File(this.a).renameTo(file)) {
            StringBuilder sbA = g9.a("Could not rename to ");
            sbA.append(file.getAbsolutePath());
            throw new IOException(sbA.toString());
        }
        FileInputStream fileInputStream2 = null;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                fileOutputStream = new FileOutputStream(this.a);
            } catch (Throwable th) {
                fileOutputStream = null;
                fileInputStream2 = fileInputStream;
                th = th;
            }
        } catch (Throwable th2) {
            th = th2;
            fileOutputStream = null;
        }
        try {
            a(fileInputStream, fileOutputStream);
            a((Closeable) fileInputStream);
            a((Closeable) fileOutputStream);
            file.delete();
            this.i = null;
        } catch (Throwable th3) {
            th = th3;
            fileInputStream2 = fileInputStream;
            a((Closeable) fileInputStream2);
            a((Closeable) fileOutputStream);
            file.delete();
            throw th;
        }
    }

    public void setAltitude(double d2) throws NumberFormatException {
        String str = d2 >= 0.0d ? "0" : "1";
        setAttribute(TAG_GPS_ALTITUDE, new f(Math.abs(d2)).toString());
        setAttribute(TAG_GPS_ALTITUDE_REF, str);
    }

    public void setAttribute(@NonNull String str, @Nullable String str2) throws NumberFormatException {
        e eVar;
        int i;
        int i2;
        d dVar;
        String string;
        String string2 = str2;
        String str3 = TAG_ISO_SPEED_RATINGS.equals(str) ? TAG_PHOTOGRAPHIC_SENSITIVITY : str;
        int i3 = 2;
        int i4 = 1;
        if (string2 != null && P.contains(str3)) {
            if (str3.equals(TAG_GPS_TIMESTAMP)) {
                Matcher matcher = U.matcher(string2);
                if (!matcher.find()) {
                    Log.w("ExifInterface", "Invalid value for " + str3 + " : " + string2);
                    return;
                }
                string2 = Integer.parseInt(matcher.group(1)) + "/1," + Integer.parseInt(matcher.group(2)) + "/1," + Integer.parseInt(matcher.group(3)) + "/1";
            } else {
                try {
                    string2 = new f(Double.parseDouble(str2)).toString();
                } catch (NumberFormatException unused) {
                    Log.w("ExifInterface", "Invalid value for " + str3 + " : " + string2);
                    return;
                }
            }
        }
        int i5 = 0;
        int i6 = 0;
        while (i5 < J.length) {
            if ((i5 != 4 || this.f) && (eVar = O[i5].get(str3)) != null) {
                if (string2 == null) {
                    this.d[i5].remove(str3);
                } else {
                    Pair<Integer, Integer> pairC = c(string2);
                    if (eVar.c == ((Integer) pairC.first).intValue() || eVar.c == ((Integer) pairC.second).intValue()) {
                        i = eVar.c;
                    } else {
                        int i7 = eVar.d;
                        if (i7 == -1 || !(i7 == ((Integer) pairC.first).intValue() || eVar.d == ((Integer) pairC.second).intValue())) {
                            int i8 = eVar.c;
                            if (i8 == i4 || i8 == 7 || i8 == i3) {
                                i = eVar.c;
                            } else {
                                StringBuilder sb = new StringBuilder();
                                sb.append("Given tag (");
                                sb.append(str3);
                                sb.append(") value didn't match with one of expected ");
                                sb.append("formats: ");
                                sb.append(w[eVar.c]);
                                String string3 = "";
                                if (eVar.d == -1) {
                                    string = "";
                                } else {
                                    StringBuilder sbA = g9.a(", ");
                                    sbA.append(w[eVar.d]);
                                    string = sbA.toString();
                                }
                                sb.append(string);
                                sb.append(" (guess: ");
                                sb.append(w[((Integer) pairC.first).intValue()]);
                                if (((Integer) pairC.second).intValue() != -1) {
                                    StringBuilder sbA2 = g9.a(", ");
                                    sbA2.append(w[((Integer) pairC.second).intValue()]);
                                    string3 = sbA2.toString();
                                }
                                sb.append(string3);
                                sb.append(")");
                                Log.w("ExifInterface", sb.toString());
                            }
                        } else {
                            i = eVar.d;
                        }
                    }
                    switch (i) {
                        case 1:
                            HashMap<String, d> map = this.d[i5];
                            if (string2.length() == 1) {
                                i2 = 0;
                                if (string2.charAt(0) >= '0' && string2.charAt(0) <= '1') {
                                    dVar = new d(1, 1, new byte[]{(byte) (string2.charAt(0) - '0')});
                                }
                                map.put(str3, dVar);
                                i6 = i2;
                                i4 = 1;
                                continue;
                            } else {
                                i2 = 0;
                            }
                            byte[] bytes = string2.getBytes(R);
                            dVar = new d(1, bytes.length, bytes);
                            map.put(str3, dVar);
                            i6 = i2;
                            i4 = 1;
                            continue;
                        case 2:
                        case 7:
                            this.d[i5].put(str3, d.a(string2));
                            break;
                        case 3:
                            String[] strArrSplit = string2.split(",");
                            int[] iArr = new int[strArrSplit.length];
                            for (int i9 = 0; i9 < strArrSplit.length; i9++) {
                                iArr[i9] = Integer.parseInt(strArrSplit[i9]);
                            }
                            this.d[i5].put(str3, d.a(iArr, this.e));
                            break;
                        case 4:
                            String[] strArrSplit2 = string2.split(",");
                            long[] jArr = new long[strArrSplit2.length];
                            for (int i10 = 0; i10 < strArrSplit2.length; i10++) {
                                jArr[i10] = Long.parseLong(strArrSplit2[i10]);
                            }
                            this.d[i5].put(str3, d.a(jArr, this.e));
                            break;
                        case 5:
                            String[] strArrSplit3 = string2.split(",");
                            f[] fVarArr = new f[strArrSplit3.length];
                            for (int i11 = 0; i11 < strArrSplit3.length; i11++) {
                                String[] strArrSplit4 = strArrSplit3[i11].split("/");
                                fVarArr[i11] = new f((long) Double.parseDouble(strArrSplit4[0]), (long) Double.parseDouble(strArrSplit4[1]));
                            }
                            this.d[i5].put(str3, d.a(fVarArr, this.e));
                            break;
                        case 6:
                        case 8:
                        case 11:
                        default:
                            Log.w("ExifInterface", "Data format isn't one of expected formats: " + i);
                            continue;
                        case 9:
                            String[] strArrSplit5 = string2.split(",");
                            int length = strArrSplit5.length;
                            int[] iArr2 = new int[length];
                            for (int i12 = 0; i12 < strArrSplit5.length; i12++) {
                                iArr2[i12] = Integer.parseInt(strArrSplit5[i12]);
                            }
                            HashMap<String, d> map2 = this.d[i5];
                            ByteOrder byteOrder = this.e;
                            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[x[9] * length]);
                            byteBufferWrap.order(byteOrder);
                            for (int i13 = 0; i13 < length; i13++) {
                                byteBufferWrap.putInt(iArr2[i13]);
                            }
                            map2.put(str3, new d(9, length, byteBufferWrap.array()));
                            break;
                        case 10:
                            String[] strArrSplit6 = string2.split(",");
                            int length2 = strArrSplit6.length;
                            f[] fVarArr2 = new f[length2];
                            int i14 = i6;
                            while (i6 < strArrSplit6.length) {
                                String[] strArrSplit7 = strArrSplit6[i6].split("/");
                                fVarArr2[i6] = new f((long) Double.parseDouble(strArrSplit7[i14]), (long) Double.parseDouble(strArrSplit7[i4]));
                                i6++;
                                i4 = 1;
                                i14 = 0;
                            }
                            HashMap<String, d> map3 = this.d[i5];
                            ByteOrder byteOrder2 = this.e;
                            ByteBuffer byteBufferWrap2 = ByteBuffer.wrap(new byte[x[10] * length2]);
                            byteBufferWrap2.order(byteOrder2);
                            for (int i15 = 0; i15 < length2; i15++) {
                                f fVar = fVarArr2[i15];
                                byteBufferWrap2.putInt((int) fVar.a);
                                byteBufferWrap2.putInt((int) fVar.b);
                            }
                            map3.put(str3, new d(10, length2, byteBufferWrap2.array()));
                            break;
                        case 12:
                            String[] strArrSplit8 = string2.split(",");
                            int length3 = strArrSplit8.length;
                            double[] dArr = new double[length3];
                            for (int i16 = i6; i16 < strArrSplit8.length; i16++) {
                                dArr[i16] = Double.parseDouble(strArrSplit8[i16]);
                            }
                            HashMap<String, d> map4 = this.d[i5];
                            ByteOrder byteOrder3 = this.e;
                            ByteBuffer byteBufferWrap3 = ByteBuffer.wrap(new byte[x[12] * length3]);
                            byteBufferWrap3.order(byteOrder3);
                            for (int i17 = i6; i17 < length3; i17++) {
                                byteBufferWrap3.putDouble(dArr[i17]);
                            }
                            map4.put(str3, new d(12, length3, byteBufferWrap3.array()));
                            continue;
                    }
                    i4 = 1;
                    i6 = 0;
                }
            }
            i5++;
            i3 = 2;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void setDateTime(long j) throws NumberFormatException {
        setAttribute(TAG_DATETIME, v.format(new Date(j)));
        setAttribute(TAG_SUBSEC_TIME, Long.toString(j % 1000));
    }

    public void setGpsInfo(Location location) throws NumberFormatException {
        if (location == null) {
            return;
        }
        setAttribute(TAG_GPS_PROCESSING_METHOD, location.getProvider());
        setLatLong(location.getLatitude(), location.getLongitude());
        setAltitude(location.getAltitude());
        setAttribute(TAG_GPS_SPEED_REF, "K");
        setAttribute(TAG_GPS_SPEED, new f((location.getSpeed() * TimeUnit.HOURS.toSeconds(1L)) / 1000.0f).toString());
        String[] strArrSplit = v.format(new Date(location.getTime())).split("\\s+");
        setAttribute(TAG_GPS_DATESTAMP, strArrSplit[0]);
        setAttribute(TAG_GPS_TIMESTAMP, strArrSplit[1]);
    }

    public void setLatLong(double d2, double d3) throws NumberFormatException {
        if (d2 < -90.0d || d2 > 90.0d || Double.isNaN(d2)) {
            throw new IllegalArgumentException("Latitude value " + d2 + " is not valid.");
        }
        if (d3 < -180.0d || d3 > 180.0d || Double.isNaN(d3)) {
            throw new IllegalArgumentException("Longitude value " + d3 + " is not valid.");
        }
        setAttribute(TAG_GPS_LATITUDE_REF, d2 >= 0.0d ? "N" : LATITUDE_SOUTH);
        setAttribute(TAG_GPS_LATITUDE, a(Math.abs(d2)));
        setAttribute(TAG_GPS_LONGITUDE_REF, d3 >= 0.0d ? LONGITUDE_EAST : LONGITUDE_WEST);
        setAttribute(TAG_GPS_LONGITUDE, a(Math.abs(d3)));
    }

    public static class b extends InputStream implements DataInput {
        public static final ByteOrder e = ByteOrder.LITTLE_ENDIAN;
        public static final ByteOrder f = ByteOrder.BIG_ENDIAN;
        public DataInputStream a;
        public ByteOrder b;
        public final int c;
        public int d;

        public b(InputStream inputStream) throws IOException {
            this.b = ByteOrder.BIG_ENDIAN;
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            this.a = dataInputStream;
            int iAvailable = dataInputStream.available();
            this.c = iAvailable;
            this.d = 0;
            this.a.mark(iAvailable);
        }

        public void a(long j) throws IOException {
            int i = this.d;
            if (i > j) {
                this.d = 0;
                this.a.reset();
                this.a.mark(this.c);
            } else {
                j -= i;
            }
            int i2 = (int) j;
            if (skipBytes(i2) != i2) {
                throw new IOException("Couldn't seek up to the byteCount");
            }
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return this.a.available();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            this.d++;
            return this.a.read();
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            this.d++;
            return this.a.readBoolean();
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            int i = this.d + 1;
            this.d = i;
            if (i > this.c) {
                throw new EOFException();
            }
            int i2 = this.a.read();
            if (i2 >= 0) {
                return (byte) i2;
            }
            throw new EOFException();
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            this.d += 2;
            return this.a.readChar();
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr, int i, int i2) throws IOException {
            int i3 = this.d + i2;
            this.d = i3;
            if (i3 > this.c) {
                throw new EOFException();
            }
            if (this.a.read(bArr, i, i2) != i2) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            int i = this.d + 4;
            this.d = i;
            if (i > this.c) {
                throw new EOFException();
            }
            int i2 = this.a.read();
            int i3 = this.a.read();
            int i4 = this.a.read();
            int i5 = this.a.read();
            if ((i2 | i3 | i4 | i5) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.b;
            if (byteOrder == e) {
                return (i5 << 24) + (i4 << 16) + (i3 << 8) + i2;
            }
            if (byteOrder == f) {
                return (i2 << 24) + (i3 << 16) + (i4 << 8) + i5;
            }
            StringBuilder sbA = g9.a("Invalid byte order: ");
            sbA.append(this.b);
            throw new IOException(sbA.toString());
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            Log.d("ExifInterface", "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            int i = this.d + 8;
            this.d = i;
            if (i > this.c) {
                throw new EOFException();
            }
            int i2 = this.a.read();
            int i3 = this.a.read();
            int i4 = this.a.read();
            int i5 = this.a.read();
            int i6 = this.a.read();
            int i7 = this.a.read();
            int i8 = this.a.read();
            int i9 = this.a.read();
            if ((i2 | i3 | i4 | i5 | i6 | i7 | i8 | i9) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.b;
            if (byteOrder == e) {
                return (i9 << 56) + (i8 << 48) + (i7 << 40) + (i6 << 32) + (i5 << 24) + (i4 << 16) + (i3 << 8) + i2;
            }
            if (byteOrder == f) {
                return (i2 << 56) + (i3 << 48) + (i4 << 40) + (i5 << 32) + (i6 << 24) + (i7 << 16) + (i8 << 8) + i9;
            }
            StringBuilder sbA = g9.a("Invalid byte order: ");
            sbA.append(this.b);
            throw new IOException(sbA.toString());
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            int i = this.d + 2;
            this.d = i;
            if (i > this.c) {
                throw new EOFException();
            }
            int i2 = this.a.read();
            int i3 = this.a.read();
            if ((i2 | i3) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.b;
            if (byteOrder == e) {
                return (short) ((i3 << 8) + i2);
            }
            if (byteOrder == f) {
                return (short) ((i2 << 8) + i3);
            }
            StringBuilder sbA = g9.a("Invalid byte order: ");
            sbA.append(this.b);
            throw new IOException(sbA.toString());
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            this.d += 2;
            return this.a.readUTF();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            this.d++;
            return this.a.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            int i = this.d + 2;
            this.d = i;
            if (i > this.c) {
                throw new EOFException();
            }
            int i2 = this.a.read();
            int i3 = this.a.read();
            if ((i2 | i3) < 0) {
                throw new EOFException();
            }
            ByteOrder byteOrder = this.b;
            if (byteOrder == e) {
                return (i3 << 8) + i2;
            }
            if (byteOrder == f) {
                return (i2 << 8) + i3;
            }
            StringBuilder sbA = g9.a("Invalid byte order: ");
            sbA.append(this.b);
            throw new IOException(sbA.toString());
        }

        @Override // java.io.DataInput
        public int skipBytes(int i) throws IOException {
            int iMin = Math.min(i, this.c - this.d);
            int iSkipBytes = 0;
            while (iSkipBytes < iMin) {
                iSkipBytes += this.a.skipBytes(iMin - iSkipBytes);
            }
            this.d += iSkipBytes;
            return iSkipBytes;
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            int i3 = this.a.read(bArr, i, i2);
            this.d += i3;
            return i3;
        }

        @Override // java.io.DataInput
        public void readFully(byte[] bArr) throws IOException {
            int length = this.d + bArr.length;
            this.d = length;
            if (length <= this.c) {
                if (this.a.read(bArr, 0, bArr.length) != bArr.length) {
                    throw new IOException("Couldn't read up to the length of buffer");
                }
                return;
            }
            throw new EOFException();
        }

        public b(byte[] bArr) throws IOException {
            this(new ByteArrayInputStream(bArr));
        }

        public long a() throws IOException {
            return readInt() & 4294967295L;
        }
    }

    public final void b(b bVar) throws IOException {
        bVar.skipBytes(84);
        byte[] bArr = new byte[4];
        byte[] bArr2 = new byte[4];
        bVar.read(bArr);
        bVar.skipBytes(4);
        bVar.read(bArr2);
        int i = ByteBuffer.wrap(bArr).getInt();
        int i2 = ByteBuffer.wrap(bArr2).getInt();
        a(bVar, i, 5);
        bVar.a(i2);
        bVar.b = ByteOrder.BIG_ENDIAN;
        int i3 = bVar.readInt();
        for (int i4 = 0; i4 < i3; i4++) {
            int unsignedShort = bVar.readUnsignedShort();
            int unsignedShort2 = bVar.readUnsignedShort();
            if (unsignedShort == E.a) {
                short s2 = bVar.readShort();
                short s3 = bVar.readShort();
                d dVarA = d.a((int) s2, this.e);
                d dVarA2 = d.a((int) s3, this.e);
                this.d[0].put(TAG_IMAGE_LENGTH, dVarA);
                this.d[0].put(TAG_IMAGE_WIDTH, dVarA2);
                return;
            }
            bVar.skipBytes(unsignedShort2);
        }
    }

    public static class d {
        public final int a;
        public final int b;
        public final byte[] c;

        public d(int i, int i2, byte[] bArr) {
            this.a = i;
            this.b = i2;
            this.c = bArr;
        }

        public static d a(int[] iArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.x[3] * iArr.length]);
            byteBufferWrap.order(byteOrder);
            for (int i : iArr) {
                byteBufferWrap.putShort((short) i);
            }
            return new d(3, iArr.length, byteBufferWrap.array());
        }

        public int b(ByteOrder byteOrder) throws Throwable {
            Object objD = d(byteOrder);
            if (objD == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            }
            if (objD instanceof String) {
                return Integer.parseInt((String) objD);
            }
            if (objD instanceof long[]) {
                long[] jArr = (long[]) objD;
                if (jArr.length == 1) {
                    return (int) jArr[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (!(objD instanceof int[])) {
                throw new NumberFormatException("Couldn't find a integer value");
            }
            int[] iArr = (int[]) objD;
            if (iArr.length == 1) {
                return iArr[0];
            }
            throw new NumberFormatException("There are more than one component");
        }

        public String c(ByteOrder byteOrder) throws Throwable {
            Object objD = d(byteOrder);
            if (objD == null) {
                return null;
            }
            if (objD instanceof String) {
                return (String) objD;
            }
            StringBuilder sb = new StringBuilder();
            int i = 0;
            if (objD instanceof long[]) {
                long[] jArr = (long[]) objD;
                while (i < jArr.length) {
                    sb.append(jArr[i]);
                    i++;
                    if (i != jArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (objD instanceof int[]) {
                int[] iArr = (int[]) objD;
                while (i < iArr.length) {
                    sb.append(iArr[i]);
                    i++;
                    if (i != iArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (objD instanceof double[]) {
                double[] dArr = (double[]) objD;
                while (i < dArr.length) {
                    sb.append(dArr[i]);
                    i++;
                    if (i != dArr.length) {
                        sb.append(",");
                    }
                }
                return sb.toString();
            }
            if (!(objD instanceof f[])) {
                return null;
            }
            f[] fVarArr = (f[]) objD;
            while (i < fVarArr.length) {
                sb.append(fVarArr[i].a);
                sb.append('/');
                sb.append(fVarArr[i].b);
                i++;
                if (i != fVarArr.length) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }

        /* JADX WARN: Not initialized variable reg: 3, insn: 0x01a8: MOVE (r2 I:??[OBJECT, ARRAY]) = (r3 I:??[OBJECT, ARRAY]), block:B:152:0x01a8 */
        /* JADX WARN: Removed duplicated region for block: B:164:0x01ab A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final java.lang.Object d(java.nio.ByteOrder r11) throws java.lang.Throwable {
            /*
                Method dump skipped, instructions count: 464
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.d.d(java.nio.ByteOrder):java.lang.Object");
        }

        public String toString() {
            StringBuilder sbA = g9.a("(");
            sbA.append(ExifInterface.w[this.a]);
            sbA.append(", data length:");
            sbA.append(this.c.length);
            sbA.append(")");
            return sbA.toString();
        }

        public /* synthetic */ d(int i, int i2, byte[] bArr, a aVar) {
            this.a = i;
            this.b = i2;
            this.c = bArr;
        }

        public static d a(int i, ByteOrder byteOrder) {
            return a(new int[]{i}, byteOrder);
        }

        public static d a(long[] jArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.x[4] * jArr.length]);
            byteBufferWrap.order(byteOrder);
            for (long j : jArr) {
                byteBufferWrap.putInt((int) j);
            }
            return new d(4, jArr.length, byteBufferWrap.array());
        }

        public static d a(long j, ByteOrder byteOrder) {
            return a(new long[]{j}, byteOrder);
        }

        public static d a(String str) {
            byte[] bytes = (str + (char) 0).getBytes(ExifInterface.R);
            return new d(2, bytes.length, bytes);
        }

        public static d a(f[] fVarArr, ByteOrder byteOrder) {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(new byte[ExifInterface.x[5] * fVarArr.length]);
            byteBufferWrap.order(byteOrder);
            for (f fVar : fVarArr) {
                byteBufferWrap.putInt((int) fVar.a);
                byteBufferWrap.putInt((int) fVar.b);
            }
            return new d(5, fVarArr.length, byteBufferWrap.array());
        }

        public double a(ByteOrder byteOrder) throws Throwable {
            Object objD = d(byteOrder);
            if (objD != null) {
                if (objD instanceof String) {
                    return Double.parseDouble((String) objD);
                }
                if (objD instanceof long[]) {
                    if (((long[]) objD).length == 1) {
                        return r5[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                if (objD instanceof int[]) {
                    if (((int[]) objD).length == 1) {
                        return r5[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                if (objD instanceof double[]) {
                    double[] dArr = (double[]) objD;
                    if (dArr.length == 1) {
                        return dArr[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                if (objD instanceof f[]) {
                    f[] fVarArr = (f[]) objD;
                    if (fVarArr.length == 1) {
                        f fVar = fVarArr[0];
                        return fVar.a / fVar.b;
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                throw new NumberFormatException("Couldn't find a double value");
            }
            throw new NumberFormatException("NULL can't be converted to a double value");
        }
    }

    public final void a(@NonNull InputStream inputStream) throws IOException {
        for (int i = 0; i < J.length; i++) {
            try {
                try {
                    this.d[i] = new HashMap<>();
                } catch (IOException unused) {
                    this.p = false;
                }
            } finally {
                a();
            }
        }
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, Configuration.JOG_DIAL_BOOT_PRESS_TIME);
        this.c = a(bufferedInputStream);
        b bVar = new b(bufferedInputStream);
        switch (this.c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 5:
            case 6:
            case 8:
            case 11:
                c(bVar);
                break;
            case 4:
                a(bVar, 0, 0);
                break;
            case 7:
                a(bVar);
                break;
            case 9:
                b(bVar);
                break;
            case 10:
                d(bVar);
                break;
        }
        f(bVar);
        this.p = true;
    }

    @Nullable
    public double[] getLatLong() {
        String attribute = getAttribute(TAG_GPS_LATITUDE);
        String attribute2 = getAttribute(TAG_GPS_LATITUDE_REF);
        String attribute3 = getAttribute(TAG_GPS_LONGITUDE);
        String attribute4 = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (attribute == null || attribute2 == null || attribute3 == null || attribute4 == null) {
            return null;
        }
        try {
            return new double[]{a(attribute, attribute2), a(attribute3, attribute4)};
        } catch (IllegalArgumentException unused) {
            StringBuilder sbA = g9.a("Latitude/longitude values are not parseable. ");
            sbA.append(String.format("latValue=%s, latRef=%s, lngValue=%s, lngRef=%s", attribute, attribute2, attribute3, attribute4));
            Log.w("ExifInterface", sbA.toString());
            return null;
        }
    }

    public static class e {
        public final int a;
        public final String b;
        public final int c;
        public final int d;

        public /* synthetic */ e(String str, int i, int i2, a aVar) {
            this.b = str;
            this.a = i;
            this.c = i2;
            this.d = -1;
        }

        public /* synthetic */ e(String str, int i, int i2, int i3, a aVar) {
            this.b = str;
            this.a = i;
            this.c = i2;
            this.d = i3;
        }
    }

    public ExifInterface(@NonNull InputStream inputStream) throws IOException {
        this.d = new HashMap[J.length];
        this.e = ByteOrder.BIG_ENDIAN;
        if (inputStream != null) {
            this.a = null;
            if (inputStream instanceof AssetManager.AssetInputStream) {
                this.b = (AssetManager.AssetInputStream) inputStream;
            } else {
                this.b = null;
            }
            a(inputStream);
            return;
        }
        throw new IllegalArgumentException("inputStream cannot be null");
    }

    public static double a(String str, String str2) {
        try {
            String[] strArrSplit = str.split(",");
            String[] strArrSplit2 = strArrSplit[0].split("/");
            double d2 = Double.parseDouble(strArrSplit2[0].trim()) / Double.parseDouble(strArrSplit2[1].trim());
            String[] strArrSplit3 = strArrSplit[1].split("/");
            double d3 = Double.parseDouble(strArrSplit3[0].trim()) / Double.parseDouble(strArrSplit3[1].trim());
            String[] strArrSplit4 = strArrSplit[2].split("/");
            double d4 = ((Double.parseDouble(strArrSplit4[0].trim()) / Double.parseDouble(strArrSplit4[1].trim())) / 3600.0d) + (d3 / 60.0d) + d2;
            if (!str2.equals(LATITUDE_SOUTH) && !str2.equals(LONGITUDE_WEST)) {
                if (!str2.equals("N") && !str2.equals(LONGITUDE_EAST)) {
                    throw new IllegalArgumentException();
                }
                return d4;
            }
            return -d4;
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException unused) {
            throw new IllegalArgumentException();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0297  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02a2  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x02a5 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0117  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void b(android.support.media.ExifInterface.b r23, int r24) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 737
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.b(android.support.media.ExifInterface$b, int):void");
    }

    public final void c(b bVar, int i) throws Throwable {
        d dVar;
        d dVarA;
        d dVarA2;
        d dVar2 = this.d[i].get(TAG_DEFAULT_CROP_SIZE);
        d dVar3 = this.d[i].get(TAG_RW2_SENSOR_TOP_BORDER);
        d dVar4 = this.d[i].get(TAG_RW2_SENSOR_LEFT_BORDER);
        d dVar5 = this.d[i].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        d dVar6 = this.d[i].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (dVar2 != null) {
            if (dVar2.a == 5) {
                f[] fVarArr = (f[]) dVar2.d(this.e);
                if (fVarArr != null && fVarArr.length == 2) {
                    dVarA = d.a(new f[]{fVarArr[0]}, this.e);
                    dVarA2 = d.a(new f[]{fVarArr[1]}, this.e);
                } else {
                    StringBuilder sbA = g9.a("Invalid crop size values. cropSize=");
                    sbA.append(Arrays.toString(fVarArr));
                    Log.w("ExifInterface", sbA.toString());
                    return;
                }
            } else {
                int[] iArr = (int[]) dVar2.d(this.e);
                if (iArr != null && iArr.length == 2) {
                    dVarA = d.a(iArr[0], this.e);
                    dVarA2 = d.a(iArr[1], this.e);
                } else {
                    StringBuilder sbA2 = g9.a("Invalid crop size values. cropSize=");
                    sbA2.append(Arrays.toString(iArr));
                    Log.w("ExifInterface", sbA2.toString());
                    return;
                }
            }
            this.d[i].put(TAG_IMAGE_WIDTH, dVarA);
            this.d[i].put(TAG_IMAGE_LENGTH, dVarA2);
            return;
        }
        if (dVar3 != null && dVar4 != null && dVar5 != null && dVar6 != null) {
            int iB = dVar3.b(this.e);
            int iB2 = dVar5.b(this.e);
            int iB3 = dVar6.b(this.e);
            int iB4 = dVar4.b(this.e);
            if (iB2 <= iB || iB3 <= iB4) {
                return;
            }
            d dVarA3 = d.a(iB2 - iB, this.e);
            d dVarA4 = d.a(iB3 - iB4, this.e);
            this.d[i].put(TAG_IMAGE_LENGTH, dVarA3);
            this.d[i].put(TAG_IMAGE_WIDTH, dVarA4);
            return;
        }
        d dVar7 = this.d[i].get(TAG_IMAGE_LENGTH);
        d dVar8 = this.d[i].get(TAG_IMAGE_WIDTH);
        if ((dVar7 == null || dVar8 == null) && (dVar = this.d[i].get(TAG_JPEG_INTERCHANGE_FORMAT)) != null) {
            a(bVar, dVar.b(this.e), i);
        }
    }

    public final String a(double d2) {
        long j = (long) d2;
        double d3 = d2 - j;
        long j2 = (long) (d3 * 60.0d);
        return j + "/1," + j2 + "/1," + Math.round((d3 - (j2 / 60.0d)) * 3600.0d * 1.0E7d) + "/10000000";
    }

    public final int a(BufferedInputStream bufferedInputStream) throws IOException {
        boolean z2;
        boolean z3;
        bufferedInputStream.mark(Configuration.JOG_DIAL_BOOT_PRESS_TIME);
        byte[] bArr = new byte[Configuration.JOG_DIAL_BOOT_PRESS_TIME];
        bufferedInputStream.read(bArr);
        bufferedInputStream.reset();
        int i = 0;
        while (true) {
            byte[] bArr2 = s;
            if (i >= bArr2.length) {
                z2 = true;
                break;
            }
            if (bArr[i] != bArr2[i]) {
                z2 = false;
                break;
            }
            i++;
        }
        if (z2) {
            return 4;
        }
        byte[] bytes = "FUJIFILMCCD-RAW".getBytes(Charset.defaultCharset());
        int i2 = 0;
        while (true) {
            if (i2 >= bytes.length) {
                z3 = true;
                break;
            }
            if (bArr[i2] != bytes[i2]) {
                z3 = false;
                break;
            }
            i2++;
        }
        if (z3) {
            return 9;
        }
        b bVar = new b(bArr);
        ByteOrder byteOrderE = e(bVar);
        this.e = byteOrderE;
        bVar.b = byteOrderE;
        short s2 = bVar.readShort();
        bVar.close();
        if (s2 == 20306 || s2 == 21330) {
            return 7;
        }
        b bVar2 = new b(bArr);
        ByteOrder byteOrderE2 = e(bVar2);
        this.e = byteOrderE2;
        bVar2.b = byteOrderE2;
        short s3 = bVar2.readShort();
        bVar2.close();
        return s3 == 85 ? 10 : 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:66:0x011b, code lost:
    
        r10.b = r9.e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x011f, code lost:
    
        return;
     */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0056 A[FALL_THROUGH] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(android.support.media.ExifInterface.b r10, int r11, int r12) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 402
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.media.ExifInterface.a(android.support.media.ExifInterface$b, int, int):void");
    }

    public static Pair<Integer, Integer> c(String str) throws NumberFormatException {
        if (str.contains(",")) {
            String[] strArrSplit = str.split(",");
            Pair<Integer, Integer> pairC = c(strArrSplit[0]);
            if (((Integer) pairC.first).intValue() == 2) {
                return pairC;
            }
            for (int i = 1; i < strArrSplit.length; i++) {
                Pair<Integer, Integer> pairC2 = c(strArrSplit[i]);
                int iIntValue = (((Integer) pairC2.first).equals(pairC.first) || ((Integer) pairC2.second).equals(pairC.first)) ? ((Integer) pairC.first).intValue() : -1;
                int iIntValue2 = (((Integer) pairC.second).intValue() == -1 || !(((Integer) pairC2.first).equals(pairC.second) || ((Integer) pairC2.second).equals(pairC.second))) ? -1 : ((Integer) pairC.second).intValue();
                if (iIntValue == -1 && iIntValue2 == -1) {
                    return new Pair<>(2, -1);
                }
                if (iIntValue == -1) {
                    pairC = new Pair<>(Integer.valueOf(iIntValue2), -1);
                } else if (iIntValue2 == -1) {
                    pairC = new Pair<>(Integer.valueOf(iIntValue), -1);
                }
            }
            return pairC;
        }
        if (str.contains("/")) {
            String[] strArrSplit2 = str.split("/");
            if (strArrSplit2.length == 2) {
                try {
                    long j = (long) Double.parseDouble(strArrSplit2[0]);
                    long j2 = (long) Double.parseDouble(strArrSplit2[1]);
                    if (j >= 0 && j2 >= 0) {
                        if (j <= 2147483647L && j2 <= 2147483647L) {
                            return new Pair<>(10, 5);
                        }
                        return new Pair<>(5, -1);
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException unused) {
                }
            }
            return new Pair<>(2, -1);
        }
        try {
            try {
                Long lValueOf = Long.valueOf(Long.parseLong(str));
                if (lValueOf.longValue() >= 0 && lValueOf.longValue() <= 65535) {
                    return new Pair<>(3, 4);
                }
                if (lValueOf.longValue() < 0) {
                    return new Pair<>(9, -1);
                }
                return new Pair<>(4, -1);
            } catch (NumberFormatException unused2) {
                Double.parseDouble(str);
                return new Pair<>(12, -1);
            }
        } catch (NumberFormatException unused3) {
            return new Pair<>(2, -1);
        }
    }

    public final void a(b bVar) throws Throwable {
        c(bVar);
        d dVar = this.d[1].get(TAG_MAKER_NOTE);
        if (dVar != null) {
            b bVar2 = new b(dVar.c);
            bVar2.b = this.e;
            byte[] bArr = new byte[t.length];
            bVar2.readFully(bArr);
            bVar2.a(0L);
            byte[] bArr2 = new byte[u.length];
            bVar2.readFully(bArr2);
            if (Arrays.equals(bArr, t)) {
                bVar2.a(8L);
            } else if (Arrays.equals(bArr2, u)) {
                bVar2.a(12L);
            }
            b(bVar2, 6);
            d dVar2 = this.d[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            d dVar3 = this.d[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (dVar2 != null && dVar3 != null) {
                this.d[5].put(TAG_JPEG_INTERCHANGE_FORMAT, dVar2);
                this.d[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, dVar3);
            }
            d dVar4 = this.d[8].get(TAG_ORF_ASPECT_FRAME);
            if (dVar4 != null) {
                int[] iArr = (int[]) dVar4.d(this.e);
                if (iArr != null && iArr.length == 4) {
                    if (iArr[2] <= iArr[0] || iArr[3] <= iArr[1]) {
                        return;
                    }
                    int i = (iArr[2] - iArr[0]) + 1;
                    int i2 = (iArr[3] - iArr[1]) + 1;
                    if (i < i2) {
                        int i3 = i + i2;
                        i2 = i3 - i2;
                        i = i3 - i2;
                    }
                    d dVarA = d.a(i, this.e);
                    d dVarA2 = d.a(i2, this.e);
                    this.d[0].put(TAG_IMAGE_WIDTH, dVarA);
                    this.d[0].put(TAG_IMAGE_LENGTH, dVarA2);
                    return;
                }
                StringBuilder sbA = g9.a("Invalid aspect frame values. frame=");
                sbA.append(Arrays.toString(iArr));
                Log.w("ExifInterface", sbA.toString());
            }
        }
    }

    public final void a(InputStream inputStream, OutputStream outputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        c cVar = new c(outputStream, ByteOrder.BIG_ENDIAN);
        if (dataInputStream.readByte() == -1) {
            cVar.a.write(-1);
            if (dataInputStream.readByte() == -40) {
                cVar.a.write(-40);
                cVar.a.write(-1);
                cVar.a.write(-31);
                e[][] eVarArr = J;
                int[] iArr = new int[eVarArr.length];
                int[] iArr2 = new int[eVarArr.length];
                for (e eVar : K) {
                    b(eVar.b);
                }
                b(L.b);
                b(M.b);
                for (int i = 0; i < J.length; i++) {
                    for (Object obj : this.d[i].entrySet().toArray()) {
                        Map.Entry entry = (Map.Entry) obj;
                        if (entry.getValue() == null) {
                            this.d[i].remove(entry.getKey());
                        }
                    }
                }
                if (!this.d[1].isEmpty()) {
                    this.d[0].put(K[1].b, d.a(0L, this.e));
                }
                if (!this.d[2].isEmpty()) {
                    this.d[0].put(K[2].b, d.a(0L, this.e));
                }
                if (!this.d[3].isEmpty()) {
                    this.d[1].put(K[3].b, d.a(0L, this.e));
                }
                int i2 = 4;
                if (this.f) {
                    this.d[4].put(L.b, d.a(0L, this.e));
                    this.d[4].put(M.b, d.a(this.h, this.e));
                }
                for (int i3 = 0; i3 < J.length; i3++) {
                    Iterator<Map.Entry<String, d>> it = this.d[i3].entrySet().iterator();
                    int i4 = 0;
                    while (it.hasNext()) {
                        d value = it.next().getValue();
                        if (value == null) {
                            throw null;
                        }
                        int i5 = x[value.a] * value.b;
                        if (i5 > 4) {
                            i4 += i5;
                        }
                    }
                    iArr2[i3] = iArr2[i3] + i4;
                }
                int size = 8;
                for (int i6 = 0; i6 < J.length; i6++) {
                    if (!this.d[i6].isEmpty()) {
                        iArr[i6] = size;
                        size += (this.d[i6].size() * 12) + 2 + 4 + iArr2[i6];
                    }
                }
                if (this.f) {
                    this.d[4].put(L.b, d.a(size, this.e));
                    this.g = 6 + size;
                    size += this.h;
                }
                int i7 = size + 8;
                if (!this.d[1].isEmpty()) {
                    this.d[0].put(K[1].b, d.a(iArr[1], this.e));
                }
                if (!this.d[2].isEmpty()) {
                    this.d[0].put(K[2].b, d.a(iArr[2], this.e));
                }
                if (!this.d[3].isEmpty()) {
                    this.d[1].put(K[3].b, d.a(iArr[3], this.e));
                }
                cVar.a((short) i7);
                cVar.a.write(S);
                cVar.a(this.e == ByteOrder.BIG_ENDIAN ? (short) 19789 : (short) 18761);
                cVar.b = this.e;
                cVar.a((short) 42);
                cVar.writeInt((int) 8);
                int i8 = 0;
                while (i8 < J.length) {
                    if (!this.d[i8].isEmpty()) {
                        cVar.a((short) this.d[i8].size());
                        int size2 = (this.d[i8].size() * 12) + iArr[i8] + 2 + i2;
                        for (Map.Entry<String, d> entry2 : this.d[i8].entrySet()) {
                            int i9 = O[i8].get(entry2.getKey()).a;
                            d value2 = entry2.getValue();
                            if (value2 != null) {
                                int i10 = x[value2.a] * value2.b;
                                cVar.a((short) i9);
                                cVar.a((short) value2.a);
                                cVar.writeInt(value2.b);
                                if (i10 > i2) {
                                    cVar.writeInt(size2);
                                    size2 += i10;
                                } else {
                                    cVar.a.write(value2.c);
                                    if (i10 < i2) {
                                        while (i10 < i2) {
                                            cVar.a.write(0);
                                            i10++;
                                        }
                                    }
                                }
                            } else {
                                throw null;
                            }
                        }
                        if (i8 == 0 && !this.d[i2].isEmpty()) {
                            cVar.writeInt(iArr[i2]);
                        } else {
                            cVar.writeInt((int) 0);
                        }
                        Iterator<Map.Entry<String, d>> it2 = this.d[i8].entrySet().iterator();
                        while (it2.hasNext()) {
                            byte[] bArr = it2.next().getValue().c;
                            if (bArr.length > i2) {
                                cVar.a.write(bArr, 0, bArr.length);
                                i2 = 4;
                            }
                        }
                    }
                    i8++;
                    i2 = 4;
                }
                if (this.f) {
                    cVar.a.write(getThumbnailBytes());
                }
                cVar.b = ByteOrder.BIG_ENDIAN;
                byte[] bArr2 = new byte[4096];
                while (dataInputStream.readByte() == -1) {
                    byte b2 = dataInputStream.readByte();
                    if (b2 == -39 || b2 == -38) {
                        cVar.a.write(-1);
                        cVar.a.write(b2);
                        byte[] bArr3 = new byte[8192];
                        while (true) {
                            int i11 = dataInputStream.read(bArr3);
                            if (i11 == -1) {
                                return;
                            } else {
                                cVar.write(bArr3, 0, i11);
                            }
                        }
                    } else if (b2 != -31) {
                        cVar.a.write(-1);
                        cVar.a.write(b2);
                        int unsignedShort = dataInputStream.readUnsignedShort();
                        cVar.a((short) unsignedShort);
                        int i12 = unsignedShort - 2;
                        if (i12 < 0) {
                            throw new IOException("Invalid length");
                        }
                        while (i12 > 0) {
                            int i13 = dataInputStream.read(bArr2, 0, Math.min(i12, 4096));
                            if (i13 >= 0) {
                                cVar.a.write(bArr2, 0, i13);
                                i12 -= i13;
                            }
                        }
                    } else {
                        int unsignedShort2 = dataInputStream.readUnsignedShort() - 2;
                        if (unsignedShort2 >= 0) {
                            byte[] bArr4 = new byte[6];
                            if (unsignedShort2 >= 6) {
                                if (dataInputStream.read(bArr4) == 6) {
                                    if (Arrays.equals(bArr4, S)) {
                                        int i14 = unsignedShort2 - 6;
                                        if (dataInputStream.skipBytes(i14) != i14) {
                                            throw new IOException("Invalid length");
                                        }
                                    }
                                } else {
                                    throw new IOException("Invalid exif");
                                }
                            }
                            cVar.a.write(-1);
                            cVar.a.write(b2);
                            cVar.a((short) (unsignedShort2 + 2));
                            if (unsignedShort2 >= 6) {
                                unsignedShort2 -= 6;
                                cVar.a.write(bArr4);
                            }
                            while (unsignedShort2 > 0) {
                                int i15 = dataInputStream.read(bArr2, 0, Math.min(unsignedShort2, 4096));
                                if (i15 >= 0) {
                                    cVar.a.write(bArr2, 0, i15);
                                    unsignedShort2 -= i15;
                                }
                            }
                        } else {
                            throw new IOException("Invalid length");
                        }
                    }
                }
                throw new IOException("Invalid marker");
            }
            throw new IOException("Invalid marker");
        }
        throw new IOException("Invalid marker");
    }

    public final void a() {
        String attribute = getAttribute(TAG_DATETIME_ORIGINAL);
        if (attribute != null && getAttribute(TAG_DATETIME) == null) {
            this.d[0].put(TAG_DATETIME, d.a(attribute));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.d[0].put(TAG_IMAGE_WIDTH, d.a(0L, this.e));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.d[0].put(TAG_IMAGE_LENGTH, d.a(0L, this.e));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.d[0].put(TAG_ORIENTATION, d.a(0L, this.e));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.d[1].put(TAG_LIGHT_SOURCE, d.a(0L, this.e));
        }
    }

    public final void a(b bVar, int i) throws IOException {
        ByteOrder byteOrderE = e(bVar);
        this.e = byteOrderE;
        bVar.b = byteOrderE;
        int unsignedShort = bVar.readUnsignedShort();
        int i2 = this.c;
        if (i2 != 7 && i2 != 10 && unsignedShort != 42) {
            StringBuilder sbA = g9.a("Invalid start code: ");
            sbA.append(Integer.toHexString(unsignedShort));
            throw new IOException(sbA.toString());
        }
        int i3 = bVar.readInt();
        if (i3 >= 8 && i3 < i) {
            int i4 = i3 - 8;
            if (i4 > 0 && bVar.skipBytes(i4) != i4) {
                throw new IOException(g9.b("Couldn't jump to first Ifd: ", i4));
            }
            return;
        }
        throw new IOException(g9.b("Invalid first Ifd offset: ", i3));
    }

    public final void a(b bVar, HashMap map) throws Throwable {
        int i;
        d dVar = (d) map.get(TAG_JPEG_INTERCHANGE_FORMAT);
        d dVar2 = (d) map.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (dVar == null || dVar2 == null) {
            return;
        }
        int iB = dVar.b(this.e);
        int iMin = Math.min(dVar2.b(this.e), bVar.available() - iB);
        int i2 = this.c;
        if (i2 != 4 && i2 != 9 && i2 != 10) {
            if (i2 == 7) {
                i = this.l;
            }
            if (iB > 0 || iMin <= 0) {
            }
            this.f = true;
            this.g = iB;
            this.h = iMin;
            if (this.a == null && this.b == null) {
                byte[] bArr = new byte[iMin];
                bVar.a(iB);
                bVar.readFully(bArr);
                this.i = bArr;
                return;
            }
            return;
        }
        i = this.k;
        iB += i;
        if (iB > 0) {
        }
    }

    public final boolean a(HashMap map) throws IOException {
        d dVar = (d) map.get(TAG_IMAGE_LENGTH);
        d dVar2 = (d) map.get(TAG_IMAGE_WIDTH);
        if (dVar == null || dVar2 == null) {
            return false;
        }
        return dVar.b(this.e) <= 512 && dVar2.b(this.e) <= 512;
    }

    public final void a(int i, int i2) throws Throwable {
        if (this.d[i].isEmpty() || this.d[i2].isEmpty()) {
            return;
        }
        d dVar = this.d[i].get(TAG_IMAGE_LENGTH);
        d dVar2 = this.d[i].get(TAG_IMAGE_WIDTH);
        d dVar3 = this.d[i2].get(TAG_IMAGE_LENGTH);
        d dVar4 = this.d[i2].get(TAG_IMAGE_WIDTH);
        if (dVar == null || dVar2 == null || dVar3 == null || dVar4 == null) {
            return;
        }
        int iB = dVar.b(this.e);
        int iB2 = dVar2.b(this.e);
        int iB3 = dVar3.b(this.e);
        int iB4 = dVar4.b(this.e);
        if (iB >= iB3 || iB2 >= iB4) {
            return;
        }
        HashMap<String, d>[] mapArr = this.d;
        HashMap<String, d> map = mapArr[i];
        mapArr[i] = mapArr[i2];
        mapArr[i2] = map;
    }

    public static void a(Closeable closeable) throws IOException {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
    }

    public static long[] a(Object obj) {
        if (obj instanceof int[]) {
            int[] iArr = (int[]) obj;
            long[] jArr = new long[iArr.length];
            for (int i = 0; i < iArr.length; i++) {
                jArr[i] = iArr[i];
            }
            return jArr;
        }
        if (obj instanceof long[]) {
            return (long[]) obj;
        }
        return null;
    }
}
