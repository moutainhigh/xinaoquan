package cn.enn.common.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 全角、半角转换工具
 * @author Administrator
 */
public class FullHalfAngleConverter {
    /**
     * 全角转半角
     * @param str 字符串
     * @return 字符串
     */
    public static String ToDBC(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            //中文全角空格
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }
        String returnString = new String(c);
        return returnString;
    }

    /**
     * 半角转全角
     * @param str 字符串
     * @return 字符串
     */
    public static String ToSBC(String str) {
        if(StringUtils.isEmpty(str)){
            return null;
        }
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    public static void main(String[] args) {
        String str="我是，中国人";
        String dbc = ToDBC(str);
        System.out.println("半角："+dbc);
        System.out.println(str);
    }
}
