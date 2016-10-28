package concurrent;

import concurrent.agent.ConsumerAgent;
import concurrent.agent.MapperAgent;
import concurrent.agent.ProducerAgent;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-15)
 */
@SuppressWarnings("WeakerAccess")
public class Sample {
    public static void main(String[] args) {
        final ConsumerAgent<String> consumer = new ConsumerAgent<>(
                () -> System.out.println("Consumer started"),
                x -> {
                    System.out.printf("Consumer receives '%s'\n", x);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // remove if you don't want to interrupt on demand
                    }
                },
                () -> System.out.println("Consumer finished"),
                1);

        MapperAgent<Integer, String> mapper = new MapperAgent<>(
                consumer.port(),
                () -> System.out.println("Mapper started"),
                x -> x + " processed by mapper",
                () -> System.out.println("Mapper finished"),
                1
        );

        final ProducerAgent<Integer> producer = new ProducerAgent<>(
                mapper.port(),
                () -> System.out.println("Producer started"),
                port -> IntStream.rangeClosed(1, 20).forEach(
                        x -> {
                            try {
                                System.out.printf("Producer sending %s\n", x);
                                port.send(x);
                                System.out.printf("Producer sends %s\n", x);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt(); // remove if you don't want to interrupt on demand
                            }
                        }),
                () -> System.out.println("Producer finished"));

        Stream.of(mapper, producer, consumer).forEach(Thread::start);
    }
}