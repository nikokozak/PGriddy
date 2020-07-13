
// TUPLE3 ( a: ANY, b: ANY, c: ANY)
// used to hold three values.
// use when POINT is not strictly necessary.

public class Tuple3<X, Y, Z> {

    public  X a;
    public  Y b;
    public  Z c;

    public Tuple3(X a, Y b, Z c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

}
