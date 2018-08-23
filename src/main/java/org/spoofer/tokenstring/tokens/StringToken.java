package org.spoofer.tokenstring.tokens;

public class StringToken implements Token {
    private final String value;

    public StringToken(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
