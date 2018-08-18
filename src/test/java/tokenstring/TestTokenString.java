package tokenstring;


import org.junit.Test;
import org.spoofer.tokenstring.TokenString;
import static org.junit.Assert.*;

public class TestTokenString {

    public static final String TEST_FIXED = "Hello World";

    public static final String TEST_ONE_TOKEN_NAME = "testname";
    public static final String TEST_ONE_TOKEN_VALUE = "john";
    public static final String TEST_ONE_TOKEN = "Hello ${" + TEST_ONE_TOKEN_NAME + "}'s world";
    public static final String TEST_START_TOKEN = "${" + TEST_ONE_TOKEN_NAME + "}, how's your world?";
    public static final String TEST_END_TOKEN = "Hello ${" + TEST_ONE_TOKEN_NAME + "}";

    public static final String TEST_TWO_TOKEN_NAME = "testsurname";
    public static final String TEST_TWO_TOKEN_VALUE = "doe";
    public static final String TEST_TWO_TOKEN = "Hello ${" + TEST_ONE_TOKEN_NAME + "} ${" + TEST_TWO_TOKEN_NAME + "}'s world";


    @Test
    public void testEmptyTokenString() {
        TokenString tokenString = new TokenString();
        String value = tokenString.toString();

        assertNotNull("Checking empty value is not null", value);
        assertEquals("Checking empty string creates empty string", "", value);
    }

    @Test
    public void testNonTokenString() {
        TokenString tokenString = new TokenString(TEST_FIXED);
        String value = tokenString.toString();

        assertNotNull("Checking fixed value is not null", value);
        assertEquals("Checking fixed string as expected", TEST_FIXED, value);
    }

    @Test
    public void testSingleTokenString() {
        TokenString tokenString = new TokenString(TEST_ONE_TOKEN);
        tokenString.setTokenValue(TEST_ONE_TOKEN_NAME, TEST_ONE_TOKEN_VALUE);
        String value = tokenString.toString();

        String result = TEST_ONE_TOKEN.replace("${" + TEST_ONE_TOKEN_NAME + "}", TEST_ONE_TOKEN_VALUE);
        assertNotNull("Checking value is not null", value);
        assertEquals("Checking string as expected", result, value);
    }

    @Test
    public void testSingleTokenNoValue() {
        TokenString tokenString = new TokenString(TEST_ONE_TOKEN);
        String value = tokenString.toString();

        String result = TEST_ONE_TOKEN.replace("${" + TEST_ONE_TOKEN_NAME + "}", "");
        assertNotNull("Checking value is not null", value);
        assertEquals("Checking string as expected", result, value);
    }

    @Test
    public void testSingleTokenWrongNameValue() {
        TokenString tokenString = new TokenString(TEST_ONE_TOKEN);
        tokenString.setTokenValue(TEST_ONE_TOKEN_NAME + "__", TEST_ONE_TOKEN_VALUE);
        String value = tokenString.toString();

        String result = TEST_ONE_TOKEN.replace("${" + TEST_ONE_TOKEN_NAME + "}", "");
        assertNotNull("Checking value is not null", value);
        assertEquals("Checking string as expected", result, value);
    }

    @Test
    public void testTwoTokens() {
        TokenString tokenString = new TokenString(TEST_TWO_TOKEN);
        tokenString.setTokenValue(TEST_ONE_TOKEN_NAME, TEST_ONE_TOKEN_VALUE);
        tokenString.setTokenValue(TEST_TWO_TOKEN_NAME, TEST_TWO_TOKEN_VALUE);
        String value = tokenString.toString();

        String result = TEST_TWO_TOKEN.replace("${" + TEST_ONE_TOKEN_NAME + "}", TEST_ONE_TOKEN_VALUE)
                .replace("${" + TEST_TWO_TOKEN_NAME + "}", TEST_TWO_TOKEN_VALUE);
        assertNotNull("Checking value is not null", value);
        assertEquals("Checking string as expected", result, value);
    }

    @Test
    public void testTwoTokensOneValue() {
        TokenString tokenString = new TokenString(TEST_TWO_TOKEN);
        //tokenString.setTokenValue(TEST_ONE_TOKEN_NAME, TEST_ONE_TOKEN_VALUE);
        tokenString.setTokenValue(TEST_TWO_TOKEN_NAME, TEST_TWO_TOKEN_VALUE);
        String value = tokenString.toString();

        String result = TEST_TWO_TOKEN.replace("${" + TEST_ONE_TOKEN_NAME + "}", "")
                .replace("${" + TEST_TWO_TOKEN_NAME + "}", TEST_TWO_TOKEN_VALUE);
        assertNotNull("Checking value is not null", value);
        assertEquals("Checking string as expected", result, value);
    }

    @Test
    public void testEndTokens() {
        TokenString tokenString = new TokenString(TEST_END_TOKEN);
        tokenString.setTokenValue(TEST_ONE_TOKEN_NAME, TEST_ONE_TOKEN_VALUE);
        String value = tokenString.toString();

        String result = TEST_END_TOKEN.replace("${" + TEST_ONE_TOKEN_NAME + "}", TEST_ONE_TOKEN_VALUE);

        assertNotNull("Checking value is not null", value);
        assertEquals("Checking string as expected", result, value);
    }

    @Test
    public void testStartToken() {
        TokenString tokenString = new TokenString(TEST_START_TOKEN);
        tokenString.setTokenValue(TEST_ONE_TOKEN_NAME, TEST_ONE_TOKEN_VALUE);
        String value = tokenString.toString();

        String result = TEST_START_TOKEN.replace("${" + TEST_ONE_TOKEN_NAME + "}", TEST_ONE_TOKEN_VALUE);

        assertNotNull("Checking value is not null", value);
        assertEquals("Checking string as expected", result, value);
    }
}
