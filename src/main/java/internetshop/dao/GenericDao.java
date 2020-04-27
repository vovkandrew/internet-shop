package internetshop.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, L> {

    T create(T element);

    Optional<T> get(L id);

    List<T> getAll();

    T update(T element);

    void delete(L id);
}
