package internetshop.service;

import java.util.List;

public interface GenericService<T, L> {

    T create(T element);

    T get(L id);

    List<T> getAll();

    T update(T element);

    void delete(L id);

}
