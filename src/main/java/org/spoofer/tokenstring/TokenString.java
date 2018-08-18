package org.spoofer.tokenstring;

import org.spoofer.tokenstring.tokens.NamedToken;
import org.spoofer.tokenstring.tokens.SimpleString;
import org.spoofer.tokenstring.tokens.StringToken;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link TokenString} may contain tokenised values, named segments in the string, which are replaced with actual values
 * when the string is read throguh the {@link #toString()} method.
 *
 *
 */
public class TokenString {

    /**
     * Default token opener.  Tokens are wrappen in an opener and closer, with the token name in the center.
     * e.g. ${mytoken}
     *
     */
    public static final String DEFAULT_TOKEN_OPEN = "${";
    public static final String DEFAULT_TOKEN_CLOSE = "}";


    private String tokenOpen = DEFAULT_TOKEN_OPEN;
    private String tokenClose = DEFAULT_TOKEN_CLOSE;

    private String rawString;
    private List<StringToken> stringTokens = new ArrayList<StringToken>();

    public TokenString() {
        setTokenString(null);
    }

    public TokenString(String tokenString) {
        setTokenString(rawString);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (StringToken token : stringTokens) {
            s.append(token.toString());
        }
        return s.toString();
    }

    public String getTokenOpen() {
        return tokenOpen;
    }

    public String getTokenClose() {
        return tokenClose;
    }

    /**
     * Gets the raw tokenised string, containing the named tokens and NOT the values.
     * @return
     */
    public String getTokenString() {
        return rawString;
    }

    /**
     * Sets the token string, containing the named tokens.
     * This resets all other values assigned to the token string already
     * @param rawString
     */
    public void setTokenString(String rawString) {
        this.rawString = rawString;
        scanString();
    }

    /**
     * Gets the value, if any, that has been assigned to t a named token.
     * By default, the tokens have no value, so produce an empty string.
     * @see #setTokenValue(String, Object)
     * @param tokenName the token name
     * @return the value object, if any or null if no such token or no value assigned.
     */
    public Object getTokenValue(String tokenName) {
        NamedToken token = getToken(tokenName);
        return null != token ? token.getValue() : null;
    }

    /**
     * Assignes the given value to the named token.
     * If the named token exists in the token string, the values toString is used to populate the token value.
     * @param tokenName
     * @param value
     */
    public void setTokenValue(String tokenName, Object value) {
        NamedToken token = getToken(tokenName);
        if (null != token)
            token.setValue(value);
    }

    /**
     * Checks if the token name is known within the current tokenised string.
     * @param tokenName
     * @return
     */
    public boolean containsToken(String tokenName) {
        return getToken(tokenName) != null;
    }


    /**
     * Gets the internal NamedToken or null if unknown name,
     * @param name
     * @return
     */
    private NamedToken getToken(String name) {
        NamedToken token = null;
        for (StringToken stringToken : stringTokens) {
            if (stringToken instanceof NamedToken) {
                NamedToken namedToken = (NamedToken) stringToken;
                if (namedToken.getName().equals(name)) {
                    token = namedToken;
                    break;
                }
            }
        }
        return token;
    }

    private void scanString() {
        stringTokens.clear();

        int index = 0;

        if (null == rawString || rawString.length() == 0)
            return;

        int startPos = rawString.indexOf(getTokenOpen());

        while (index < rawString.length() && startPos >= 0) {
            int endPos = rawString.indexOf(getTokenClose(), index + getTokenOpen().length());
            if (endPos > startPos) {
                int leadLen = startPos - index;  // Capture any fixed stringleading upto first token.
                if (leadLen > 0)
                    stringTokens.add(new SimpleString(rawString.substring(index, index + leadLen)));

                String name = rawString.substring(startPos + getTokenOpen().length(), endPos).trim();
                stringTokens.add(new NamedToken(name, null));

                index = endPos + getTokenClose().length();
                startPos = rawString.indexOf(getTokenOpen(), index);

            } else { // no end marker to open token, ignore and abort.
                break;
            }

        }

        if (index < rawString.length())
            stringTokens.add(new SimpleString(rawString.substring(index, rawString.length())));
    }

    public static String tokenise(String value) {
        return new StringBuilder().append(DEFAULT_TOKEN_OPEN)
                .append(value).append(DEFAULT_TOKEN_CLOSE).toString();
    }

}
