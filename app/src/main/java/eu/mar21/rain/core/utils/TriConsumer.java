package eu.mar21.rain.core.utils;

public interface TriConsumer<T, U, V> {

    void accept(T t, U u, V v);

}