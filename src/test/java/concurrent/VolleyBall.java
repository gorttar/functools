package concurrent;

import concurrent.agent.ConsumerAgent;
import concurrent.port.Port;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-15)
 */
@SuppressWarnings("WeakerAccess")
public class VolleyBall {
    public static void main(String[] args) throws InterruptedException {
        @SuppressWarnings("unchecked")
        final ConsumerAgent<Integer>[] players = new ConsumerAgent[5];
        @SuppressWarnings("unchecked")
        final Port<Integer>[] playersPorts = new Port[5];

        IntStream.range(0, 5).forEach(
                playerIndex -> {
                    final int playerNumber = playerIndex + 1;
                    final int nextPlayerIndex = playerNumber % 5;
                    final int nextPlayerNumber = nextPlayerIndex + 1;
                    final ConsumerAgent<Integer> player = new ConsumerAgent<>(
                            () -> System.out.printf("Player #%s started\n", playerNumber),
                            x -> {
                                System.out.printf("Player #%s got ball #%s\n", playerNumber, x);
                                try {
                                    Thread.sleep(1000);
                                    System.out.printf("Player #%s passes ball #%s to player #%s\n", playerNumber, x,
                                            nextPlayerNumber);
                                    if (playersPorts[nextPlayerIndex].sendIfOpen(x) == Port.Response.CLOSED) {
                                        System.out.printf("Seems like player %s already gone\n", nextPlayerNumber);
                                    }
                                } catch (InterruptedException e) {
                                    //
                                }
                            },
                            () -> System.out.printf("Player #%s finished\n", playerNumber),
                            1);

                    players[playerIndex] = player;
                    playersPorts[playerIndex] = player.port();
                }
        );

        Arrays.stream(players).forEach(Thread::start);

        Stream.of(1, 2, 3, 4).forEach(ball -> {
            try {
                playersPorts[0].send(ball);
            } catch (InterruptedException e) {
                //
            }
        });

        Thread.sleep(10000);

        Arrays.stream(playersPorts).forEach(
                playerPort -> {
                    try {
                        playerPort.close();
                    } catch (IOException e) {
                        //
                    }
                });

    }
}