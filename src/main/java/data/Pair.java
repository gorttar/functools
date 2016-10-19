package data;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;

/**
 * @author Andrey Antipov (andrey.antipov@maxifier.com) (2016-10-15)
 */
public class Pair<F, S> extends SimpleImmutableEntry<F, S> {

    private Pair(F fst, S snd) {
        super(fst, snd);
    }

    private Pair(Entry<? extends F, ? extends S> entry) {
        super(entry);
    }

    public static <F, S> Pair<F, S> tup(F fst, S snd) {
        return new Pair<>(fst, snd);
    }

    public static <F, S> Pair<F, S> tup(Entry<? extends F, ? extends S> entry) {
        return new Pair<>(entry);
    }

    public F fst() {
        return getKey();
    }

    public S snd() {
        return getValue();
    }

}
