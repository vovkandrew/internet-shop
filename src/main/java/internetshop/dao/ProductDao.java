package internetshop.dao;

import internetshop.model.Product;

public interface ProductDao {

    Product create(Product product);

    Product get(Long id);

    Product update(Product product);

    void delete(Long id);
}
