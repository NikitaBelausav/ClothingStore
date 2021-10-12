package edu.rutgers.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Utility class to encode GET queries
 */
public class URLQuery {
    private static final String CHARSET = "UTF-8";

    /**
     * Creates a query string with values encoded for URLs.
     * 
     * @param  pairs the keys and values to encode, 
     *               with every value following its associated key
     * @return an encoded query string safe for use in URLs
     */
    public static String encode(String... pairs) {
        StringBuilder ret = new StringBuilder();

        if (pairs.length % 2 != 0) // Not pairs
            throw new IllegalArgumentException("Not in pairs.");

        // Fail gracefully if it does throw an UnsupportedEncodingException, 
        // it means we did something wrong.
        // However, it should still fail for other things, such as an invalid or null map
        for(int i = 0; i < pairs.length; i += 2) {
            String key = pairs[i];
            String value = pairs[i + 1];

            try {
                ret.append(String.format("%s=%s", key, URLEncoder.encode(value, CHARSET)));
                ret.append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        // Remove the extra &
        if (ret.length() > 0)
            ret.setLength(ret.length() - 1);

        return ret.toString();
    }
}
