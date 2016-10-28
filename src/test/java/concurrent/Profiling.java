package concurrent;

import static data.Pair.tup;

import concurrent.agent.ConsumerAgent;
import concurrent.agent.ProducerAgent;
import concurrent.port.BufferedPortFactory;
import concurrent.port.OptimizedBufferedPort;
import concurrent.port.OptionalBufferedPort;
import concurrent.port.Port;
import concurrent.port.WrappedBufferedPort;
import data.Pair;
import org.testng.annotations.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-26 18:28)
 */
public class Profiling {
    private static final int NUMBER_OF_SAMPLES = 200;
    private static final int NUMBER_OF_ITERATIONS = 100000;
    private static final int START = Integer.MIN_VALUE;
    private static final int END = START + NUMBER_OF_ITERATIONS;

    private static long profiledExec(Runnable toProfile) {
        final long start = System.currentTimeMillis();
        toProfile.run();
        return (System.currentTimeMillis() - start) * 1000;
    }

    private static void emptyLoop() {
        final Thread producer = new Thread(
                () -> IntStream.range(START, END).count()
        );
        producer.start();
        try {
            producer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static void producerConsumer(BufferedPortFactory factory) {
        final ConsumerAgent<Integer> consumer = new ConsumerAgent<>(null, __ -> {
        }, null, 10, factory);

        final ProducerAgent<Integer> producer = new ProducerAgent<>(
                consumer.port(),
                port -> IntStream.rangeClosed(START, END)
                        .forEach(
                                x -> {
                                    try {
                                        port.send(x);
                                    } catch (InterruptedException e) {
                                        Thread.currentThread().interrupt();
                                    }
                                }));

        producer.start();
        consumer.start();

        try {
            consumer.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Test
    public void comparePortsTest() {
        final double averageEmptyLoopNanos = measureAverageExecutionTime(Profiling::emptyLoop);

        Stream
                .<Pair<Class<? extends Port>, BufferedPortFactory>>of(
                        tup(OptimizedBufferedPort.class, OptimizedBufferedPort::createPortWithStream),
                        tup(OptionalBufferedPort.class, OptionalBufferedPort::createPortWithStream),
                        tup(WrappedBufferedPort.class, WrappedBufferedPort::createPortWithStream))
                .map(
                        pair -> tup(
                                pair.fst().getSimpleName(),
                                measureAverageExecutionTime(() -> producerConsumer(pair.snd())) - averageEmptyLoopNanos))
                .sorted((pair1, pair2) -> pair1.getValue().compareTo(pair2.getValue()))
                .forEach(pair -> System.out.printf("%30s -> %s\n", pair.fst(), pair.snd()));
    }

    private static double measureAverageExecutionTime(Runnable toProfile) {
        return IntStream.range(0, NUMBER_OF_SAMPLES)
                .mapToObj(__ -> profiledExec(toProfile))
                .reduce(0L, (a, b) -> a + b)
                / (double) NUMBER_OF_SAMPLES
                / (double) NUMBER_OF_ITERATIONS;
    }

}
