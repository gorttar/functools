package concurrent.agent;

import concurrent.port.BufferedPortFactory;
import concurrent.port.Port;
import concurrent.port.WrappedBufferedPort;
import data.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * {@link Thread} based implementation for {@link Actor} which process entire stream of messages
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-24 17:42)
 */
public class StreamConsumerAgent<A> extends Thread implements Actor<A> {
    private final Port<A> port;
    private final Stream<A> stream;

    @Nullable
    private final Runnable preProcess;

    @Nullable
    private final Consumer<? super Stream<A>> messageStreamConsumer;

    @Nullable
    private final Runnable postProcess;

    /**
     * @param preProcess            will be executed before messages stream processing
     * @param messageStreamConsumer is used to process stream of messages
     * @param postProcess           will be executed after messages stream processing
     * @param bufferSize            size of agent's port buffer
     * @param portFactory           factory to create agent's port
     */
    public StreamConsumerAgent(@Nullable Runnable preProcess, @Nullable Consumer<? super Stream<A>> messageStreamConsumer, @Nullable Runnable postProcess,
                               int bufferSize, @Nonnull BufferedPortFactory portFactory) {
        this.preProcess = preProcess;
        this.messageStreamConsumer = messageStreamConsumer;
        this.postProcess = postProcess;
        final Pair<Port<A>, Stream<A>> portWithStream = portFactory.createPortWithStream(bufferSize);
        port = portWithStream.fst();
        stream = portWithStream.snd();
    }

    /**
     * create agent based on {@link concurrent.port.WrappedBufferedPort} as agent's port
     *
     * @param preProcess            will be executed before messages stream processing
     * @param messageStreamConsumer is used to process stream of messages
     * @param postProcess           will be executed after messages stream processing
     * @param bufferSize            size of agent's port buffer
     */
    public StreamConsumerAgent(@Nullable Runnable preProcess, @Nullable Consumer<? super Stream<A>> messageStreamConsumer, @Nullable Runnable postProcess,
                               int bufferSize) {
        this(preProcess, messageStreamConsumer, postProcess, bufferSize, WrappedBufferedPort::createPortWithStream);
    }

    /**
     * @param messageStreamConsumer is used to process stream of messages
     * @param bufferSize            size of agent's port buffer
     * @param portFactory           factory to create agent's port
     */
    public StreamConsumerAgent(@Nullable Consumer<? super Stream<A>> messageStreamConsumer, int bufferSize, @Nonnull BufferedPortFactory portFactory) {
        this(null, messageStreamConsumer, null, bufferSize, portFactory);
    }

    /**
     * create agent based on {@link concurrent.port.WrappedBufferedPort} as agent's port
     *
     * @param messageStreamConsumer is used to process stream of messages
     * @param bufferSize            size of agent's port buffer
     */
    public StreamConsumerAgent(@Nullable Consumer<? super Stream<A>> messageStreamConsumer, int bufferSize) {
        this(messageStreamConsumer, bufferSize, WrappedBufferedPort::createPortWithStream);
    }

    @Override
    public void run() {
        Optional.ofNullable(preProcess).ifPresent(Runnable::run);
        Optional.ofNullable(messageStreamConsumer).ifPresent(consumer -> consumer.accept(stream));
        Optional.ofNullable(postProcess).ifPresent(Runnable::run);
    }

    public Port<A> port() {
        return port;
    }

}