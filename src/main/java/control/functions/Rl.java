package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-21 21:35)
 */
@FunctionalInterface
public interface Rl extends Runnable, Sr<Void> {
    void uRun() throws Throwable;

    @Override
    default Void uApply() throws Throwable {
        uRun();
        return null;
    }

    @Override
    default void run() {
        a();
    }
}
