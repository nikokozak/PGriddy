
// TUPLE2 ( a: ANY, b: ANY)
// used to hold two values.
// use when POINT is not strictly necessary.

public class Tuple2<X, Y> {

    public X a;
    public Y b;

    public Tuple2(X a, Y b) {
        this.a = a;
        this.b = b;
    }

}

