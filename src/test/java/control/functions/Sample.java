package control.functions;

import control.functions.consumers.C1;
import control.functions.consumers.C2;
import control.functions.consumers.C3;
import control.functions.consumers.C4;

/**
 * @author Andrey Antipov (gorttar@gmail.com) (2016-11-18 19:13)
 */
public class Sample {
    public static void main(String[] args) {
        F4<Integer, Integer, Integer, Integer, Integer> f4 = (a, b, c, x) -> a * x * x + b * x + c;
        final F3<Integer, Integer, Integer, Integer> f3 = f4.a(1);
        final F2<Integer, Integer, Integer> f2 = f3.a(-3);
        final F1<Integer, Integer> f1 = f2.a(2);
        System.out.println(f1.a(0));
        System.out.println(f1.a(1));
        System.out.println(f1.a(2));

        C4<Integer, Integer, Integer, Integer> c4 = (a, b, c, x) -> System.out.println(f4.a(a, b, c, x));
        final C3<Integer, Integer, Integer> c3 = c4.a(1);
        final C2<Integer, Integer> c2 = c3.a(-3);
        final C1<Integer> c1 = c2.a(2);
        c1.a(0);
        c1.a(1);
        c1.a(2);
    }
}
