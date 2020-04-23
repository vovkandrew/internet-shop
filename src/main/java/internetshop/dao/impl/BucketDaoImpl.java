package internetshop.dao.impl;

import internetshop.dao.BucketDao;
import internetshop.dao.Storage;
import internetshop.lib.Dao;
import internetshop.model.Bucket;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        Storage.addBucket(bucket);
        return bucket;
    }

    @Override
    public Bucket get(Long id) {
        return Storage.buckets
                .stream()
                .filter(bucket -> bucket.getId().equals(id))
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Bucket update(Bucket bucket) {
        IntStream.range(0, Storage.buckets.size())
                .filter(number -> bucket.getId().equals(Storage.buckets.get(number).getId()))
                .forEach(number -> Storage.buckets.set(number, bucket));
        return bucket;
    }

    @Override
    public void delete(Long id) {
        Storage.buckets.removeIf(bucket -> bucket.getId().equals(id));
    }
}
