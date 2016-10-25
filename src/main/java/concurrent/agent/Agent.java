package concurrent.agent;

import concurrent.port.OptimizedBufferedPort;
import concurrent.port.Port;
import data.Pair;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-15)
 */
public class Agent<T> extends Thread implements Actor<T> {
    private final Port<T> port;
    private final Stream<T> stream;

    private final Consumer<? super T> payload;
    private final Runnable postProcess;

    public Agent(Consumer<? super T> payload, Runnable postProcess, int bufferSize) {
        this.payload = payload;
        this.postProcess = postProcess;
        final Pair<Port<T>, Stream<T>> portWithStream = OptimizedBufferedPort.createPortWithStream(bufferSize);
        port = portWithStream.fst();
        stream = portWithStream.snd();
    }

    @Override
    public void run() {
        stream.forEach(payload);
        postProcess.run();
    }

    @Override
    public Port<T> port() {
        return port;
    }
}