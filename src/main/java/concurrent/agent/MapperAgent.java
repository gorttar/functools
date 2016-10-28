package concurrent.agent;

import concurrent.port.BufferedPortFactory;
import concurrent.port.Port;
import concurrent.port.WrappedBufferedPort;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.function.Function;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-28 21:50)
 */
public class MapperAgent<A, B> extends ConsumerAgent<A> implements Actor<A> {
    /**
     * @param preProcess    will be executed before processing first message
     * @param messageMapper is used to process messages
     * @param postProcess   will be executed after processing all messages
     * @param outPort       port to send processed messages
     * @param bufferSize    size of agent's port buffer
     * @param portFactory   factory to create agent's port
     */
    public MapperAgent(@Nullable Runnable preProcess, @Nonnull Function<? super A, ? extends B> messageMapper, @Nullable Runnable postProcess, @Nonnull Port<B> outPort, int bufferSize,
                       @Nonnull BufferedPortFactory portFactory) {
        super(
                preProcess,
                a -> {
                    try (final Port<B> port = outPort) {
                        port.send(messageMapper.apply(a));
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } catch (IOException e) {
                        //
                    }
                },
                postProcess,
                bufferSize,
                portFactory
        );
    }

    /**
     * create agent based on {@link WrappedBufferedPort} as agent's port
     *
     * @param preProcess    will be executed before processing first message
     * @param messageMapper is used to process messages
     * @param postProcess   will be executed after processing all messages
     * @param outPort       port to send processed messages
     * @param bufferSize    size of agent's port buffer
     */
    public MapperAgent(@Nullable Runnable preProcess, @Nonnull Function<? super A, ? extends B> messageMapper, @Nullable Runnable postProcess, @Nonnull Port<B> outPort, int bufferSize) {
        this(preProcess, messageMapper, postProcess, outPort, bufferSize, WrappedBufferedPort::createPortWithStream);
    }

    /**
     * @param messageMapper is used to process messages
     * @param outPort       port to send processed messages
     * @param bufferSize    size of agent's port buffer
     * @param portFactory   factory to create agent's port
     */
    public MapperAgent(@Nonnull Function<? super A, ? extends B> messageMapper, @Nonnull Port<B> outPort, int bufferSize, @Nonnull BufferedPortFactory portFactory) {
        this(null, messageMapper, null, outPort, bufferSize, portFactory);
    }

    /**
     * create agent based on {@link WrappedBufferedPort} as agent's port
     *
     * @param messageMapper is used to process messages
     * @param outPort       port to send processed messages
     * @param bufferSize    size of agent's port buffer
     */
    public MapperAgent(@Nonnull Function<? super A, ? extends B> messageMapper, @Nonnull Port<B> outPort, int bufferSize) {
        this(null, messageMapper, null, outPort, bufferSize, WrappedBufferedPort::createPortWithStream);
    }
}
