package top.yonyong.yconfig.utils;

/**
 * @Describtion StringUtils
 * @Author yonyong
 * @Date 2020/8/3 14:13
 * @Version 1.0.0
 **/
public class StringUtils {
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs != null && (strLen = cs.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }
}
