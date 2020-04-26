package internetshop.dao.impl;

import internetshop.dao.ProductDao;
import internetshop.db.Storage;
import internetshop.lib.Dao;
import internetshop.model.Product;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.addProduct(product);
        return product;
    }

    @Override
    public Optional<Product> get(Long id) {
        return Optional.of(Storage.products
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new));
    }

    @Override
    public List<Product> getAll() {
        return Storage.products;
    }

    @Override
    public Product update(Product product) {
        IntStream.range(0, Storage.products.size())
                .filter(number -> product.getId().equals(Storage.products.get(number).getId()))
                .forEach(number -> Storage.products.set(number, product));
        return product;
    }

    @Override
    public void delete(Long id) {
        Storage.products.removeIf(item -> item.getId().equals(id));
    }
}
