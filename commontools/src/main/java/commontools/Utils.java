package commontools;

import android.text.TextUtils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by AndyChang on 15/11/23.
 */
public class Utils {
    private static final String TAG = "Utils";

    public static <T extends Number> T valueOf(String value, @NotNull T def) {
        try {
            Class<T> clazz = (Class<T>) def.getClass();
            Method method = clazz.getMethod("valueOf", String.class);
            method.setAccessible(true);
            return (T) method.invoke(null, value);
        } catch (Exception e) {
            LogUtils.e(TAG, e);
            return def;
        }
    }

    public static int listSize(List list) {
        if (list != null) return list.size();
        else return -1;
    }

    public static boolean isEmpty(String str) {
        if (str == null) {
            return true;
        } else {
            String string = str.trim();
            return TextUtils.isEmpty(string);
        }
    }

    public static boolean notEmpty(String string) {
        return (string != null) && (!("".equals(string)));
    }

    public static String replacePara(String url, String name, String accessToken) {
        if (url.contains(name)) {
            url = replaceAccessToken(url, name, accessToken);
        } else {
            if (!url.contains("?")) {
                url += ("?" + name + "=" + accessToken);
            } else {
                url += ("&" + name + "=" + accessToken);
            }
        }
        return url;
    }

    public static String replaceAccessToken(String url, String name, String accessToken) {
        if (Utils.notEmpty(url) && Utils.notEmpty(accessToken)) {
            int index = url.indexOf(name + "=");
            if (index != -1) {
                StringBuilder sb = new StringBuilder();
                sb.append(url.substring(0, index)).append(name + "=")
                        .append(accessToken);
                int idx = url.indexOf("&", index);
                if (idx != -1) {
                    sb.append(url.substring(idx));
                }
                url = sb.toString();
            }

        }
        return url;
    }

    public static String replaceAccessTokenReg(String url, String name, String accessToken) {
        if (Utils.notEmpty(url) && Utils.notEmpty(accessToken)) {
            url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + accessToken);
        }
        return url;
    }

    public static void ensureFileDir(String path) {
        LogUtils.d(TAG, "ensureFileDir : " + path);
        File file = new File(path);
        if (!file.exists()) {
            boolean isOk = file.mkdirs();
        }
    }


    public static boolean ignore(File file) {
        if (file == null) return true;
        if (file.getName().startsWith(".")) {
//            LogUtils.d(TAG, "ignore() called with: " + "file = [" + file + "]");
            return true;
        }
        if (file.isDirectory()) {
            if (new File(file.getAbsolutePath() + File.separator + ".nomedia").exists()) {
//                LogUtils.d(TAG, "ignore() called with: " + "file = [" + file + "]");
                return true;
            }
        }
        return false;
    }
}
