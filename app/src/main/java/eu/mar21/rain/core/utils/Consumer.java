package eu.mar21.rain.core.utils;

public interface Consumer<T, U, V> {

    void accept(T t, U u, V v);

}