package internetshop.dao;

import internetshop.model.Bucket;

public interface BucketDao {

    Bucket create(Bucket bucket);

    Bucket get(Long id);

    Bucket update(Bucket bucket);

    void delete(Long id);
}
