package cache;/*
 * Copyright (c) 2017 Andrey Antipov. All Rights Reserved.
 */

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2017-04-03)
 */
public final class Memos {
    private Memos() {
    }

    public static <T> Supplier<T> memo(Supplier<? extends T> source) {
        class Memo implements Supplier<T> {
            private Memo impl = this;

            @Override
            public T get() {
                return impl.bind();
            }

            protected T bind() {
                final T bound = source.get();
                impl = new Memo() {
                    @Override
                    protected T bind() {
                        return bound;
                    }
                };
                return bound;
            }
        }

        return new Memo();
    }

    public static <T, R> Function<T, R> memo(Function<? super T, ? extends R> source) {
        final Map<T, R> cache = new HashMap<>();
        return
                t -> {
                    if (!cache.containsKey(t)) {
                        cache.put(t, source.apply(t));
                    }
                    return cache.get(t);
                };
    }
}