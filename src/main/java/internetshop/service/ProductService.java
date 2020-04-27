package internetshop.service;

import internetshop.model.Product;
import java.util.Optional;

public interface ProductService extends GenericService<Product, Long> {

    Optional<Product> getOptional(Long id);
}
