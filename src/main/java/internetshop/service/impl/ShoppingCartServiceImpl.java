package internetshop.service.impl;

import internetshop.dao.ShoppingCartDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Product;
import internetshop.model.ShoppingCart;
import internetshop.service.ShoppingCartService;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Inject
    ShoppingCartDao shoppingCartDao;

    @Override
    public ShoppingCart addProduct(ShoppingCart shoppingCart, Product product) {
        shoppingCart.addProduct(product);
        return shoppingCartDao.update(shoppingCart);
    }

    @Override
    public boolean deleteProduct(ShoppingCart shoppingCart, Product product) {
        boolean removed = shoppingCart.getProducts()
                .removeIf(product1 -> product1.getId().equals(product.getId()));
        shoppingCartDao.update(shoppingCart);
        return removed;
    }

    @Override
    public void clear(ShoppingCart shoppingCart) {
        shoppingCart.getProducts().clear();
        shoppingCartDao.update(shoppingCart);
    }

    @Override
    public ShoppingCart getByUserId(Long userId) {
        return shoppingCartDao.getAll()
                .stream()
                .filter(shoppingCart -> shoppingCart.getUser().getId().equals(userId))
                .findAny()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<Product> getAllProducts(ShoppingCart shoppingCart) {
        return shoppingCartDao.getAll()
                .stream()
                .filter(shoppingCart1 -> shoppingCart1.getId().equals(shoppingCart.getId()))
                .findAny()
                .map(ShoppingCart::getProducts)
                .get();
    }
}
