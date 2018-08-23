package org.spoofer.tokenstring.tokens;

public class NamedToken extends StringToken {

    private Object value;

    public NamedToken(String name, Object value) {
        super(name);
        this.value = value;
    }

    public String getName() { return super.toString(); }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value != null ? this.value.toString() : "";
    }
}
