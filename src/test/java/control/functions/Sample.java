package control.functions;

import control.functions.consumers.Co1;
import control.functions.consumers.Co2;
import control.functions.consumers.Co3;
import control.functions.consumers.Co4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:13)
 */
public class Sample {
    public static void main(String[] args) {
        Fn4<Integer, Integer, Integer, Integer, Integer> fn4 = (a, b, c, x) -> a * x * x + b * x + c;
        final Fn3<Integer, Integer, Integer, Integer> fn3 = fn4.a(1);
        final Fn2<Integer, Integer, Integer> fn2 = fn3.a(-3);
        final Fn1<Integer, Integer> fn1 = fn2.a(2);
        System.out.println(fn1.a(0));
        System.out.println(fn1.a(1));
        System.out.println(fn1.a(2));

        Co4<Integer, Integer, Integer, Integer> co4 = (a, b, c, x) -> System.out.println(fn4.a(a, b, c, x));
        final Co3<Integer, Integer, Integer> co3 = co4.a(1);
        final Co2<Integer, Integer> co2 = co3.a(-3);
        final Co1<Integer> co1 = co2.a(2);
        co1.a(0);
        co1.a(1);
        co1.a(2);
    }
}
