package com.ctx.lighting.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

    public static String underline2Camel(String line, boolean... smallCamel) {
        if (line != null && !"".equals(line)) {
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
            Matcher matcher = pattern.matcher(line);

            while(matcher.find()) {
                String word = matcher.group();
                if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
                    sb.append(Character.toLowerCase(word.charAt(0)));
                } else {
                    sb.append(Character.toUpperCase(word.charAt(0)));
                }

                int index = word.lastIndexOf(95);
                if (index > 0) {
                    sb.append(word.substring(1, index).toLowerCase());
                } else {
                    sb.append(word.substring(1).toLowerCase());
                }
            }

            return sb.toString();
        } else {
            return "";
        }
    }

    public static String capitalFirst(String key){
        if(key == null || key.trim().length()==0){
            return "";
        }
        return key.trim().substring(0,1).toUpperCase() + key.trim().substring(1);
    }

    public static String camel2Underline(String line) {
        if (line != null && !"".equals(line)) {
            line = String.valueOf(line.charAt(0)).toUpperCase().concat(line.substring(1));
            StringBuffer sb = new StringBuffer();
            Pattern pattern = Pattern.compile("[A-Z]([a-z\\d]+)?");
            Matcher matcher = pattern.matcher(line);

            while(matcher.find()) {
                String word = matcher.group();
                sb.append(word.toUpperCase());
                sb.append(matcher.end() == line.length() ? "" : "_");
            }

            return sb.toString();
        } else {
            return "";
        }
    }
}
