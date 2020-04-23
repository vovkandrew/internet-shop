package internetshop.dao.impl;

import internetshop.dao.ProductDao;
import internetshop.dao.Storage;
import internetshop.lib.Dao;
import internetshop.model.Product;

import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Dao
public class ProductDaoImpl implements ProductDao {

    @Override
    public Product create(Product product) {
        Storage.addItem(product);
        return product;
    }

    @Override
    public Product get(Long id) {
        return Storage.products
                .stream()
                .filter(item -> item.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
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
