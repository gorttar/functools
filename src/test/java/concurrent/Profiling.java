package concurrent;

import concurrent.agent.ConsumerAgent;
import concurrent.port.BufferedPortFactory;
import concurrent.port.OptimizedBufferedPort;
import concurrent.port.OptionalBufferedPort;
import concurrent.port.Port;
import concurrent.port.WrappedBufferedPort;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-26 18:28)
 */
public class Profiling {
    private static final int NUMBER_OF_SAMPLES = 20;
    private static final int NUMBER_OF_ITERATIONS = 10000;
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
        final Thread producer = new Thread(
                () -> {
                    try (Port<Integer> port = consumer.port()) {
                        IntStream.rangeClosed(START, END)
                                .forEach(
                                        x -> {
                                            try {
                                                port.send(x);
                                            } catch (InterruptedException e) {
                                                Thread.currentThread().interrupt();
                                            }
                                        });
                    } catch (IOException e) {
                        //
                    }
                }
        );
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
        System.out.println(averageEmptyLoopNanos);

        final double averageOptimizedNanos = measureAverageExecutionTime(() -> producerConsumer(OptimizedBufferedPort::createPortWithStream));
        System.out.println(averageOptimizedNanos);

        final double averageOptionalNanos = measureAverageExecutionTime(() -> producerConsumer(OptionalBufferedPort::createPortWithStream));
        System.out.println(averageOptionalNanos);

        final double averageWrappedNanos = measureAverageExecutionTime(() -> producerConsumer(WrappedBufferedPort::createPortWithStream));
        System.out.println(averageWrappedNanos);
    }

    private static double measureAverageExecutionTime(Runnable toProfile) {
        return IntStream.range(0, NUMBER_OF_SAMPLES)
                .mapToObj(__ -> profiledExec(toProfile))
                .reduce(0L, (a, b) -> a + b)
                / (double) NUMBER_OF_SAMPLES
                / (double) NUMBER_OF_ITERATIONS;
    }

}
