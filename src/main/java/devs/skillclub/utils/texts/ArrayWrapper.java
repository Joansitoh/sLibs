package devs.skillclub.utils.texts;

import org.apache.commons.lang.Validate;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

public final class ArrayWrapper<E> {

    private E[] _array;

    @SafeVarargs
    public ArrayWrapper(E... elements) {
        setArray(elements);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Iterable<? extends T> list, Class<T> c) {
        int size = -1;
        if (list instanceof Collection<?>) {
            @SuppressWarnings("rawtypes")
            Collection coll = (Collection) list;
            size = coll.size();
        }

        if (size < 0) {
            size = 0;
            for (@SuppressWarnings("unused") T element : list) {
                size++;
            }
        }

        T[] result = (T[]) Array.newInstance(c, size);
        int i = 0;
        for (T element : list) {
            result[i++] = element;
        }
        return result;
    }

    public E[] getArray() {
        return _array;
    }

    public void setArray(E[] array) {
        Validate.notNull(array, "The array must not be null.");
        _array = array;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof ArrayWrapper)) {
            return false;
        }
        return Arrays.equals(_array, ((ArrayWrapper) other)._array);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(_array);
    }

}
