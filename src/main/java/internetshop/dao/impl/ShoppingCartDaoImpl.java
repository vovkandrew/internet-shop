package internetshop.dao.impl;

import internetshop.dao.ShoppingCartDao;
import internetshop.db.Storage;
import internetshop.model.ShoppingCart;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

public class ShoppingCartDaoImpl implements ShoppingCartDao {

    @Override
    public ShoppingCart create(ShoppingCart shoppingcart) {
        Storage.addShoppingCart(shoppingcart);
        return shoppingcart;
    }

    @Override
    public Optional<ShoppingCart> get(Long shoppoingcartId) {
        return Storage.shoppingCarts
                .stream()
                .filter(shoppingcart -> shoppingcart.getId().equals(shoppoingcartId))
                .findFirst();
    }

    @Override
    public List<ShoppingCart> getAll() {
        return Storage.shoppingCarts;
    }

    @Override
    public ShoppingCart update(ShoppingCart shoppingcart) {
        IntStream.range(0, Storage.shoppingCarts.size())
                .filter(number ->
                        Storage.shoppingCarts.get(number).getId()
                                .equals(shoppingcart.getId()))
                .forEach(number ->
                        Storage.shoppingCarts.set(number, shoppingcart));
        return shoppingcart;
    }

    @Override
    public void delete(Long shoppingcartId) {
        Storage.shoppingCarts.removeIf(shoppingcart -> shoppingcart.getId().equals(shoppingcartId));
    }
}
