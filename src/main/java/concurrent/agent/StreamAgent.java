package concurrent.agent;

import concurrent.port.OptimizedBufferedPort;
import concurrent.port.Port;
import data.Pair;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-24 17:42)
 */
public class StreamAgent<T> extends Thread implements Actor<T> {
    private final Port<T> port;

    private final Stream<T> stream;
    private final Consumer<? super Stream<T>> payload;
    private final Runnable postProcess;

    public StreamAgent(Consumer<? super Stream<T>> payload, Runnable postProcess, int bufferSize) {
        this.payload = payload;
        this.postProcess = postProcess;
        final Pair<Port<T>, Stream<T>> portWithStream = OptimizedBufferedPort.createPortWithStream(bufferSize);
        port = portWithStream.fst();
        stream = portWithStream.snd();
    }

    @Override
    public void run() {
        payload.accept(stream);
        postProcess.run();
    }

    public Port<T> port() {
        return port;
    }

}