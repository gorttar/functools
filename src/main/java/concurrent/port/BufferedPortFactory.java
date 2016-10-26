package concurrent.port;

import data.Pair;

import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-25 20:32)
 */
@FunctionalInterface
public interface BufferedPortFactory {
    <A> Pair<Port<A>, Stream<A>> createPortWithStream(int bufferSize);
}
