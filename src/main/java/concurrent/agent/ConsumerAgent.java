package concurrent.agent;

import concurrent.port.OptimizedBufferedPort;
import concurrent.port.Port;
import data.Pair;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-15)
 */
public class ConsumerAgent<A> extends Thread implements Actor<A> {
    private final Port<A> port;
    private final Stream<A> stream;

    @Nullable
    private final Runnable preProcess;

    @Nullable
    private final Consumer<? super A> messageConsumer;

    @Nullable
    private final Runnable postProcess;

    public ConsumerAgent(@Nullable Runnable preProcess, @Nullable Consumer<? super A> messageConsumer, @Nullable Runnable postProcess, int bufferSize) {
        this.preProcess = preProcess;
        this.messageConsumer = messageConsumer;
        this.postProcess = postProcess;
        final Pair<Port<A>, Stream<A>> portWithStream = OptimizedBufferedPort.createPortWithStream(bufferSize);
        port = portWithStream.fst();
        stream = portWithStream.snd();
    }

    @Override
    public void run() {
        Optional.ofNullable(preProcess).ifPresent(Runnable::run);
        Optional.ofNullable(messageConsumer).ifPresent(stream::forEach);
        Optional.ofNullable(postProcess).ifPresent(Runnable::run);
    }

    @Override
    public Port<A> port() {
        return port;
    }
}