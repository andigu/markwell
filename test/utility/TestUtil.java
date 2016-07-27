package utility;

/**
 * Utility for tests - raises errors if assertions fail (i.e. two values that should match don't actually match in the
 * testing)
 *
 * @author Andi Gu
 * @course ICS4U
 * @date 6/21/2016
 */
public class TestUtil {
    public static void assertEquals(Object o1, Object o2) {
        if (!o1.equals(o2)) {
            throw new RuntimeException("Assertion failed");
        }
    }

    public static void assertTrue(boolean b) {
        if (!b) {
            throw new RuntimeException("Assertion failed");
        }
    }

    public static void assertFalse(boolean b) {
        if (b) {
            throw new RuntimeException("Assertion failed");
        }
    }
}
