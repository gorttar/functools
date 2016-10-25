package concurrent;

import concurrent.agent.ConsumerAgent;
import concurrent.port.Port;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-15)
 */
@SuppressWarnings("WeakerAccess")
public class Sample {
    public static void main(String[] args) {
        final ConsumerAgent<Integer> consumer = new ConsumerAgent<>(
                x -> {
                    System.out.printf("Consumer receives %s\n", x);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // remove if you don't want to interrupt on demand
                    }
                },
                () -> System.out.println("Consumer finished"),
                10);


        final Thread producer = new Thread(
                () -> {
                    final Port<Integer> consumerPort = consumer.port();
                    IntStream.rangeClosed(1, 20).forEach(
                            x -> {
                                try {
                                    System.out.printf("Producer sending %s\n", x);
                                    consumerPort.send(x);
                                    System.out.printf("Producer sends %s\n", x);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt(); // remove if you don't want to interrupt on demand
                                }
                            }
                    );
                    try {
                        consumerPort.close();
                    } catch (IOException e) {
                        //
                    }
                    System.out.println("Producer finished");
                });

        producer.start();
        consumer.start();

    }
}