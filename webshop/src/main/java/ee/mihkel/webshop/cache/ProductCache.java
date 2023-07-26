package ee.mihkel.webshop.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ee.mihkel.webshop.entity.Product;
import ee.mihkel.webshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Component
public class ProductCache {

    @Autowired
    ProductRepository productRepository;

    LoadingCache<Long, Product> loadingCache =
            CacheBuilder.newBuilder()
                    .expireAfterWrite(15, TimeUnit.MINUTES)
                    .build(new CacheLoader<>() {
                        @Override
                        public Product load(Long id) {
                            return productRepository.findById(id).get();
                        }
                    });

    // igale poole kus on: productRepository.findById(id).get()
    // kirjutame edaspidi  productCache.getProduct(id)
    // cache otsustab, kas võtab cache seest või läheb andmebaasi
    public Product getProduct(Long id) throws ExecutionException {
        return loadingCache.get(id);
    }

    public void refreshProduct(Long id, Product updatedProduct) throws ExecutionException {
        loadingCache.put(id, updatedProduct);
    }

    public void deleteFromCache(Long id) {
        loadingCache.invalidate(id); // kustutab cachest
        //         .refresh -->         kustutab cachest ja võtab andmebaasist
    }
}
