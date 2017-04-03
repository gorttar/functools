package cache;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2017-04-04)
 */
public class MemosTest {
    @Test
    public void testMemo_function() throws Exception {
        final Map<Integer, Integer> counters = new HashMap<>();

        final Function<Integer, String> source = i -> {
            counters.compute(i, (k, v) -> v == null ? 1 : v + 1);
            return "" + (i * i);
        };
        final Function<Integer, String> decorated = Memos.memo(source);
        assertEquals(counters.size(), 0, "Source function shouldn't be invoked during memoization\n");

        IntStream
                .range(1, 10)
                .forEach(
                        trial -> IntStream
                                .range(1, 10)
                                .forEach(arg -> assertEquals(decorated.apply(arg), "" + (arg * arg))));

        assertEquals(
                counters,
                IntStream
                        .range(1, 10)
                        .boxed()
                        .collect(Collectors.toMap(i -> i, __ -> 1)));
    }

    @Test
    public void testMemo_supplier() throws Exception {
        int[] counter = {0};
        final Supplier<Integer> source = () -> {
            counter[0]++;
            return 1;
        };
        final Supplier<Integer> decorated = Memos.memo(source);
        assertEquals(counter[0], 0, "Source supplier shouldn't be invoked during memoization\n");
        IntStream.range(0, 10)
                .forEach(__ -> assertEquals(decorated.get(), Integer.valueOf(1)));
        assertEquals(counter[0], 1, "Source supplier should be invoked once for all invocations of decorated one\n");
    }

}