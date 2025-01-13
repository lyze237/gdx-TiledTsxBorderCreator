package dev.lyze.tiledtsxbordercreator.utils;

public class StringUtils {
    public static String substringFromRight(String input, int maxLength) {
        if (input == null || maxLength <= 0) {
            return "";
        }

        if (input.length() <= maxLength) {
            return input; // No truncation needed
        }

        // Reserve space for "..."
        int substringLength = maxLength - 3;

        // Ensure the substring length isn't negative
        if (substringLength <= 0) {
            return "...";
        }

        // Return the right-most part of the string, followed by "..."
        return "..." + input.substring(input.length() - substringLength);
    }
}
