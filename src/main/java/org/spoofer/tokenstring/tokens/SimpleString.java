package org.spoofer.tokenstring.tokens;

public class SimpleString implements StringToken {
    private final String value;

    public SimpleString(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
