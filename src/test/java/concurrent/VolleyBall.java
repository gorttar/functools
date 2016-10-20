package concurrent;

import concurrent.agent.Agent;
import concurrent.port.Port;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Andrey Antipov (andrey.antipov@maxifier.com) (2016-10-15)
 */
@SuppressWarnings("WeakerAccess")
public class VolleyBall {
    public static void main(String[] args) throws InterruptedException {
        @SuppressWarnings("unchecked")
        final Agent<Integer>[] players = new Agent[5];
        @SuppressWarnings("unchecked")
        final Port<Integer>[] playersPorts = new Port[5];
        final int closeValue = Integer.MIN_VALUE;

        IntStream.range(0, 5).forEach(
                playerIndex -> {
                    final int playerNumber = playerIndex + 1;
                    final int nextPlayerIndex = playerNumber % 5;
                    final int nextPlayerNumber = nextPlayerIndex + 1;
                    final Agent<Integer> player = new Agent<>(
                            x -> {
                                System.out.printf("Player #%s got ball #%s\n", playerNumber, x);
                                try {
                                    Thread.sleep(1000);
                                    System.out.printf("Player #%s passes ball #%s to player #%s\n", playerNumber, x,
                                            nextPlayerNumber);
                                    if (!playersPorts[nextPlayerIndex].sendIfOpen(x)) {
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