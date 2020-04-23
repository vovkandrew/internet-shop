package internetshop.service.impl;

import internetshop.dao.ProductDao;
import internetshop.lib.Inject;
import internetshop.lib.Service;
import internetshop.model.Product;
import internetshop.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

    @Inject
    private ProductDao productDao;

    @Override
    public Product create(Product product) {
        return productDao.create(product);
    }

    @Override
    public Product get(Long id) {
        return productDao.get(id);
    }

    @Override
    public Product update(Product product) {
        return productDao.update(product);
    }

    @Override
    public void delete(Long id) {
        productDao.delete(id);
    }
}
