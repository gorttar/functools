package control.functions;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:13)
 */
public class Sample {
    public static void main(String[] args) {
        Fn4<Integer, Integer, Integer, Integer, Integer> fn4 = (a, b, c, x) -> a * x * x + b * x + c;
        final Fn1<Integer, Integer> fn1 = fn4.a(1).a(-3).a(2);
        System.out.println(fn1.a(0));
        System.out.println(fn1.a(1));
        System.out.println(fn1.a(2));
    }
}
