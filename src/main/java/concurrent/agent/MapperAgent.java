package concurrent.agent;

import concurrent.port.BufferedPortFactory;
import concurrent.port.Port;
import concurrent.port.WrappedBufferedPort;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-28 21:50)
 */
public class MapperAgent<A, B> extends ConsumerAgent<A> implements Actor<A> {
    /**
     * @param outPort       port to send processed messages
     * @param preProcess    will be executed before processing first message
     * @param messageMapper is used to process messages
     * @param postProcess   will be executed after processing all messages
     * @param bufferSize    size of agent's port buffer
     * @param portFactory   factory to create agent's port
     */
    public MapperAgent(@Nonnull Port<B> outPort, @Nullable Runnable preProcess, @Nonnull Function<? super A, ? extends B> messageMapper, @Nullable Runnable postProcess, int bufferSize,
                       @Nonnull BufferedPortFactory portFactory) {
        super(
                preProcess,
                a -> {
                    try {
                        outPort.send(messageMapper.apply(a));
                    } catch (InterruptedException e) {
                        try {
                            outPort.close();
                        } catch (IOException e1) {
                            //
                        }
                        Thread.currentThread().interrupt();
                    }
                },
                () -> {
                    try {
                        outPort.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Optional.ofNullable(postProcess).ifPresent(Runnable::run);
                },
                bufferSize,
                portFactory
        );
    }

    /**
     * create agent based on {@link WrappedBufferedPort} as agent's port
     *
     * @param outPort       port to send processed messages
     * @param preProcess    will be executed before processing first message
     * @param messageMapper is used to process messages
     * @param postProcess   will be executed after processing all messages
     * @param bufferSize    size of agent's port buffer
     */
    public MapperAgent(@Nonnull Port<B> outPort, @Nullable Runnable preProcess, @Nonnull Function<? super A, ? extends B> messageMapper, @Nullable Runnable postProcess, int bufferSize) {
        this(outPort, preProcess, messageMapper, postProcess, bufferSize, WrappedBufferedPort::createPortWithStream);
    }

    /**
     * @param outPort       port to send processed messages
     * @param messageMapper is used to process messages
     * @param bufferSize    size of agent's port buffer
     * @param portFactory   factory to create agent's port
     */
    public MapperAgent(@Nonnull Port<B> outPort, @Nonnull Function<? super A, ? extends B> messageMapper, int bufferSize, @Nonnull BufferedPortFactory portFactory) {
        this(outPort, null, messageMapper, null, bufferSize, portFactory);
    }

    /**
     * create agent based on {@link WrappedBufferedPort} as agent's port
     *
     * @param outPort       port to send processed messages
     * @param messageMapper is used to process messages
     * @param bufferSize    size of agent's port buffer
     */
    public MapperAgent(@Nonnull Port<B> outPort, @Nonnull Function<? super A, ? extends B> messageMapper, int bufferSize) {
        this(outPort, null, messageMapper, null, bufferSize, WrappedBufferedPort::createPortWithStream);
    }
}
