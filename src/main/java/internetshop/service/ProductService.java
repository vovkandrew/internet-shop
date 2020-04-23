package internetshop.service;

import internetshop.model.Product;

public interface ProductService {

    Product create(Product product);

    Product get(Long id);

    Product update(Product product);

    void delete(Long id);
}
