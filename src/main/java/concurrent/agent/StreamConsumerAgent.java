package concurrent.agent;

import concurrent.port.OptimizedBufferedPort;
import concurrent.port.Port;
import data.Pair;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-24 17:42)
 */
public class StreamConsumerAgent<A> extends Thread implements Actor<A> {
    private final Port<A> port;

    private final Stream<A> stream;
    private final Consumer<? super Stream<A>> payload;
    private final Runnable postProcess;

    public StreamConsumerAgent(Consumer<? super Stream<A>> payload, Runnable postProcess, int bufferSize) {
        this.payload = payload;
        this.postProcess = postProcess;
        final Pair<Port<A>, Stream<A>> portWithStream = OptimizedBufferedPort.createPortWithStream(bufferSize);
        port = portWithStream.fst();
        stream = portWithStream.snd();
    }

    @Override
    public void run() {
        payload.accept(stream);
        postProcess.run();
    }

    public Port<A> port() {
        return port;
    }

}