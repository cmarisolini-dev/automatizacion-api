package org.example.utils;
import java.util.concurrent.ThreadLocalRandom;
public final class TestIds {
    private TestIds() {}
    public static long unique() { return ThreadLocalRandom.current().nextLong(100_000_000_000L, 999_999_999_999L); }
}
