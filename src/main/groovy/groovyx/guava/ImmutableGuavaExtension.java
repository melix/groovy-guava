package groovyx.guava;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

import java.util.Comparator;
import java.util.List;

public class ImmutableGuavaExtension {
    public static <E> ImmutableList<E> asImmutableList(Iterable<E> elements) {
        return new ImmutableList.Builder<E>().
                addAll(elements).
                build();
    }

    public static <E> ImmutableSet<E> asImmutableSet(Iterable<E> elements) {
        return new ImmutableSet.Builder<E>().
                addAll(elements).
                build();
    }

    public static <E> ImmutableSortedSet<E> asImmutableSortedSet(Iterable<E> elements, Comparator<? super E> comparator) {
        return new ImmutableSortedSet.Builder<E>(comparator).
                addAll(elements).
                build();
    }

    // concatenations
    @SuppressWarnings("unchecked")
    public static <U,V> ImmutableList<U> plus(ImmutableList<U> elements, List<V> more) {
        return new ImmutableList.Builder().
                addAll(elements).
                addAll(more).
                build();
    }
}
