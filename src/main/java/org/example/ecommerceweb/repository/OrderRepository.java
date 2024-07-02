package org.example.ecommerceweb.repository;

import org.example.ecommerceweb.domains.Order;
import org.example.ecommerceweb.dto.response.analytic.IBestSaleProduct;
import org.example.ecommerceweb.dto.response.analytic.IOrderRecent;
import org.example.ecommerceweb.dto.response.analytic.IRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND " +
//            "(o.orderStatus = 'PLACED' OR o.orderStatus = 'CONFIRMED' OR " +
//            "o.orderStatus = 'SHIPPED' OR o.orderStatus = 'DELIVERED' OR o.orderStatus = 'PENDING')")
//    List<Order> getUserOrders(@Param("userId") Long userId, @Param("filter") String[] filter);

    @Query("SELECT o FROM Order o WHERE o.user.id = :userId AND " +
            "('DUMMYVALUE' IN :filter OR o.orderStatus IN :filter) "+
            "ORDER BY o.id DESC"
            )
    List<Order> getUserOrders(@Param("userId") Long userId, @Param("filter") List<String> filter);


    @Query(value = "SELECT count(id) " +
            "FROM orders " +
            "WHERE order_date >= current_date - 30 " +
            "AND order_date <= current_date", nativeQuery = true)
    Long getAllOrderIn30Days();

    @Query(value = "SELECT sum(total_discouted_price) " +
            "FROM orders " +
            "WHERE order_date >= current_date - 30 " +
            "AND order_date <= current_date", nativeQuery = true)
    BigDecimal getTotalRevenueIn30Days();


    @Query(value = "SELECT count(id) " +
            "FROM orders " +
            "WHERE order_date >= current_date - 30 " +
            "AND order_date <= current_date " +
            "AND order_status = 'CANCELLED'",nativeQuery = true)
    Long getTotalOrderCancelIn30Days();

    @Query(value = "SELECT count(id) " +
            "FROM users " +
            "WHERE created_at >= current_date - 30 " +
            "AND created_at <= current_date", nativeQuery = true)
    Long getTotalUserIn30Days();


    @Query(value = "WITH months AS (\n" +
            "    SELECT generate_series(1, 12) AS month\n" +
            ")\n" +
            "SELECT \n" +
            "    m.month,\n" +
            "    COALESCE(COUNT(o.id), 0) AS totalOrder\n" +
            "FROM \n" +
            "    months m\n" +
            "LEFT JOIN \n" +
            "    orders o\n" +
            "ON \n" +
            "    m.month = DATE_PART('month', o.order_date) \n" +
            "    AND DATE_PART('year', o.order_date) = DATE_PART('year', CURRENT_DATE)\n" +
            "GROUP BY \n" +
            "    m.month\n" +
            "ORDER BY \n" +
            "    m.month;\n", nativeQuery = true)
    List<IOrderRecent> getTotalOrderEveryMonth();


    @Query(value = "WITH months AS (\n" +
            "    SELECT generate_series(1, 12) AS month\n" +
            ")\n" +
            "SELECT \n" +
            "    m.month,\n" +
            "      COALESCE(SUM(o.total_discouted_price), 0) AS revenue\n" +
            "FROM \n" +
            "    months m\n" +
            "LEFT JOIN \n" +
            "    orders o\n" +
            "ON \n" +
            "    m.month = DATE_PART('month', o.order_date) \n" +
            "    AND DATE_PART('year', o.order_date) = DATE_PART('year', CURRENT_DATE)\n" +
            "GROUP BY \n" +
            "    m.month\n" +
            "ORDER BY \n" +
            "    m.month;",nativeQuery = true)
    List<IRevenue> getTotalRevenueEveryMonth();

    @Query(value = "SELECT SUM(oi.quantity) as totalSold,p.product_id as productId \n" +
            "from order_items oi LEFT join product_skus p on oi.product_skus_id = p.id \n" +
            "group by p.product_id \n" +
            "order by totalSold desc ",nativeQuery = true)
    List<IBestSaleProduct> getBestSaleProduct();





}
