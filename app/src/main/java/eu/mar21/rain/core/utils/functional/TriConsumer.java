package eu.mar21.rain.core.utils.functional;

public interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);

}