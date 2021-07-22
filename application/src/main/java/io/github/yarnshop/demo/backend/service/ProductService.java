package io.github.yarnshop.demo.backend.service;


import io.github.yarnshop.demo.backend.entity.ProductYarn;
import io.github.yarnshop.demo.backend.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class ProductService {
    private static final Logger LOGGER = Logger.getLogger(ProductService.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private YarnService yarnService;

    private List<Product> productNoList = new ArrayList<>();

    public  List<Product> getProductList() {
        if (productNoList.size() == 0) {
            productNoList = findAll();
        }
        return productNoList;
    }

    /*****************
     *  PRODUCT
     *****************/
    public List<Product> findAll() {
        try {
            return jdbcTemplate.query("SELECT p.id, p.product_no, p.name, count(product_id) as yarn_count " +
                            "FROM Product p " +
                            "LEFT JOIN Product_Yarn py ON py.product_id = p.id " +
                            "GROUP BY p.id " +
                            "ORDER BY p.product_no ASC",
                    (rs, rowNum) -> new Product(rs.getLong("id"), rs.getInt("product_no"),
                            rs.getString("name"), rs.getInt("yarn_count")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<Product> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return jdbcTemplate.query("SELECT p.id, p.product_no, p.name, count(product_id) as yarn_count " +
                            "FROM Product p " +
                            "LEFT JOIN Product_Yarn py ON py.product_id = p.id " +
                            "GROUP BY p.id " +
                            "ORDER BY p.product_no ASC ",
                    (rs, rowNum) -> new Product(rs.getLong("id"), rs.getInt("product_no"), rs.getString("name"),
                            rs.getInt("yarn_count")));
        } else  {
            return jdbcTemplate.query("SELECT p.id, p.product_no, p.name, count(product_id) as yarn_count FROM Product p " +
                            "LEFT JOIN Product_Yarn py ON py.product_id = p.id " +
                            "where p.product_no like '"+ filterText +"%'" +
                            "GROUP BY p.id " +
                            "ORDER BY p.product_no ASC ",
                    (rs, rowNum) -> new Product(rs.getLong("id"), rs.getInt("product_no"),
                            rs.getString("name"),
                            rs.getInt("yarn_count")));
        }
    }

    public Product get(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT p.id, p.product_no, p.name, count(product_id) as yarn_count " +
                            "FROM Product p " +
                            "LEFT JOIN Product_Yarn py ON py.product_id = p.id " +
                            "where p.id = " + id,
                    (rs, rowNum) -> new Product(rs.getLong("id"), rs.getInt("product_no"),
                            rs.getString("name"),rs.getInt("yarn_count")));
        } catch (Exception e) {
            return null;
        }
    }

    public void update(Product product) {
        jdbcTemplate.update("UPDATE Product SET product_no=?, name=? WHERE id=?",
                product.getProductNo(), product.getName(), product.getId());
        productNoList.clear();
    }

    public void delete(Product product) {
        jdbcTemplate.update("DELETE from Product WHERE id=?",
                product.getId());
        productNoList.clear();
    }

    public void save(Product boxInStore) {
        if (boxInStore == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (boxInStore.getProductNo() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (boxInStore.getId() != null) {
            jdbcTemplate.update("UPDATE Product SET product_no=?, name=? WHERE id=?",
                    boxInStore.getProductNo(), boxInStore.getName(),  boxInStore.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("insert into Product (product_no, name) values " +
                            "( ?, ?); ",
                    boxInStore.getProductNo(), boxInStore.getName());
        }
        productNoList.clear();
    }

    /*****************
     *  PRODUCT YARN
     *****************/
    public List<ProductYarn> findAllProductYarns(Long productId) {
        try {
            return jdbcTemplate.query("SELECT id, product_id, yarn_id, numbers_used FROM Product_Yarn  " +
                            "WHERE product_id = " + productId,
                    (rs, rowNum) -> new ProductYarn(rs.getLong("id"),
                            rs.getLong("product_id"),
                            yarnService.get(rs.getLong("yarn_id")),
                            rs.getInt("numbers_used"),
                            yarnService.getBallsAtHome(rs.getLong("yarn_id"))));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public void delete(ProductYarn productYarn) {
        jdbcTemplate.update("DELETE from Product_Yarn WHERE id=?",
                productYarn.getId());
    }

    public void save(ProductYarn productYarn) {
        if (productYarn == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (productYarn.getProductId() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (productYarn.getId() != null) {
            jdbcTemplate.update("UPDATE Product_Yarn SET yarn_id=?, numbers_used =? WHERE id=?",
                     productYarn.getYarn().getId(),  productYarn.getNumberUsed(), productYarn.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) values " +
                            "( ?, ?, ?); ",
                    productYarn.getProductId(), productYarn.getYarn().getId(), productYarn.getNumberUsed());
        }
    }
}
