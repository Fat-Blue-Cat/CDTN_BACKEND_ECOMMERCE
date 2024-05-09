package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    @Query("select p from Product p join p.productSkus ps on p.id=ps.product.id where p.title like %:title% and (:brandName is null or p.brand.name like %:brandName%) and (:categoryName is null or p.category.name like %:categoryName%) and ps.price between :minPrice and :maxPrice "
            + " order by "
            + " case when :sort = 'price_low' then ps.price end asc, "
            + " case when :sort = 'price_high' then ps.price end desc")
    Page<Product> getAllProductByFilter(@Param("title") String title, @Param("brandName") String brandName, @Param("categoryName") String categoryName, @Param("sort") String sort, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);


//    Page<Product> getAllProductByFilter(@Param("title") String title, @Param("brandName") String brandName, @Param("categoryName") String categoryName, @Param("sort") String sort, @Param("minPrice") int minPrice, @Param("maxPrice") int maxPrice, Pageable pageable);

//    @Query("select p from Product p join p.variations v where p.title like %:title% and (:brandName is null or p.brand.name like %:brandName%) and (:categoryName is null or p.category.name like %:categoryName%) and v.price between :minPrice and :maxPrice")
//    Page<Product> findAllByTitleContaining(String title,String brandName,String categoryName,int minPrice, int maxPrice, Pageable pageable);

}
