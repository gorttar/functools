package concurrent.port;

import data.Pair;

import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-25 20:32)
 */
public interface BufferedPortFactory<T> {
    Pair<Port<T>, Stream<T>> createPortWithStream(int bufferSize);
}
