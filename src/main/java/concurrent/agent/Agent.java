package concurrent.agent;

import data.Pair;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (andrey.antipov@maxifier.com) (2016-10-15)
 */
public class Agent<T> extends Thread {
    private final Port<T> port;

    protected final Stream<T> stream;
    private final Consumer<? super T> payload;
    private final Runnable postProcess;

    public Agent(Consumer<? super T> payload, Runnable postProcess, int bufferSize) {
        this.payload = payload;
        this.postProcess = postProcess;
        final Pair<Port<T>, Stream<T>> portWithStream = BufferedPort.createPortWithStream(bufferSize);
        port = portWithStream.fst();
        stream = portWithStream.snd();
    }

    @Override
    public void run() {
        stream.forEach(payload);
        postProcess.run();
    }

    public Port<T> port() {
        return port;
    }
}