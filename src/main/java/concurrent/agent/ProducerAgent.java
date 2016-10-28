package concurrent.agent;

import concurrent.port.Port;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * agent which sends messages to provided outPort using {@link #sender} for it
 * ensures that outPort is closed after agent's execution is finished
 *
 * @author Andrey Antipov (gorttar@gmail.com) (2016-10-28 13:26)
 */
public class ProducerAgent<A> extends Thread implements Runnable {
    @Nonnull
    private final Port<A> outPort;

    @Nullable
    private final Runnable preProcess;

    @Nonnull
    private final Consumer<? super Port<A>> sender;

    @Nullable
    private final Runnable postProcess;

    public ProducerAgent(@Nonnull Port<A> outPort, @Nullable Runnable preProcess, @Nonnull Consumer<? super Port<A>> sender, @Nullable Runnable postProcess) {
        this.outPort = outPort;
        this.preProcess = preProcess;
        this.sender = sender;
        this.postProcess = postProcess;
    }

    public ProducerAgent(@Nonnull Port<A> outPort, @Nonnull Consumer<? super Port<A>> sender) {
        this(outPort, null, sender, null);
    }

    @Override
    public void run() {
        try (final Port<A> port = this.outPort) {
            Optional.ofNullable(preProcess).ifPresent(Runnable::run);
            sender.accept(port);
            Optional.ofNullable(postProcess).ifPresent(Runnable::run);
        } catch (IOException e) {
            //
        }
    }

}
