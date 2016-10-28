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
 * {@link Thread} based implementation for {@link Actor} which process messages one by one
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-15)
 */
public class ConsumerAgent<A> extends Thread implements Actor<A> {
    private final Port<A> inPort;
    private final Stream<A> stream;

    @Nullable
    private final Runnable preProcess;

    @Nullable
    private final Consumer<? super A> messageConsumer;

    @Nullable
    private final Runnable postProcess;

    /**
     * @param preProcess      will be executed before processing first message
     * @param messageConsumer is used to process messages
     * @param postProcess     will be executed after processing all messages
     * @param bufferSize      size of agent's port buffer
     * @param portFactory     factory to create agent's port
     */
    public ConsumerAgent(@Nullable Runnable preProcess, @Nullable Consumer<? super A> messageConsumer, @Nullable Runnable postProcess, int bufferSize,
                         @Nonnull BufferedPortFactory portFactory) {
        this.preProcess = preProcess;
        this.messageConsumer = messageConsumer;
        this.postProcess = postProcess;
        final Pair<Port<A>, Stream<A>> portWithStream = portFactory.createPortWithStream(bufferSize);
        inPort = portWithStream.fst();
        stream = portWithStream.snd();
    }

    /**
     * create agent based on {@link WrappedBufferedPort} as agent's port
     *
     * @param preProcess      will be executed before processing first message
     * @param messageConsumer is used to process messages
     * @param postProcess     will be executed after processing all messages
     * @param bufferSize      size of agent's port buffer
     */
    public ConsumerAgent(@Nullable Runnable preProcess, @Nullable Consumer<? super A> messageConsumer, @Nullable Runnable postProcess, int bufferSize) {
        this(preProcess, messageConsumer, postProcess, bufferSize, WrappedBufferedPort::createPortWithStream);
    }

    /**
     * @param messageConsumer is used to process messages
     * @param bufferSize      size of agent's port buffer
     * @param portFactory     factory to create agent's port
     */
    public ConsumerAgent(@Nullable Consumer<? super A> messageConsumer, int bufferSize, @Nonnull BufferedPortFactory portFactory) {
        this(null, messageConsumer, null, bufferSize, portFactory);
    }

    /**
     * create agent based on {@link WrappedBufferedPort} as agent's port
     *
     * @param messageConsumer is used to process messages
     * @param bufferSize      size of agent's port buffer
     */
    public ConsumerAgent(@Nullable Consumer<? super A> messageConsumer, int bufferSize) {
        this(messageConsumer, bufferSize, WrappedBufferedPort::createPortWithStream);
    }

    @Override
    public void run() {
        Optional.ofNullable(preProcess).ifPresent(Runnable::run);
        Optional.ofNullable(messageConsumer).ifPresent(stream::forEach);
        Optional.ofNullable(postProcess).ifPresent(Runnable::run);
    }

    @Override
    public Port<A> port() {
        return inPort;
    }
}