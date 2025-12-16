package MyFinalLib.FnGtree;

import MyFinalLib.FnList.*;

public interface FnGtree<T> {
    T value();
    FnList<FnGtree<T>> children();
}
