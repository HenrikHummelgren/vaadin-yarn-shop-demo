package io.github.yarnshop.demo.backend.service;

import io.github.yarnshop.demo.backend.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import io.github.yarnshop.demo.backend.entity.BoxInStudio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class YarnService {
    private static final Logger LOGGER = Logger.getLogger(YarnService.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StoreBoxService storeBoxService;

    @Autowired
    private StudioBoxService studioBoxService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private ProductService productService;

    private HashMap<Long, BoxInStore> boxInStoreHashMap = new HashMap<Long, BoxInStore>();

    private HashMap<Long, BoxInStudio> boxInStudioHashMap = new HashMap<Long, BoxInStudio>();

    BoxInStore getBoxInStore(long key) {
        if (key == 0) {
            return null;
        }

        BoxInStore result = boxInStoreHashMap.get(key);
        if (result != null) {
            return result;
        }

        result = storeBoxService.get(key);
        if (result != null) {
            boxInStoreHashMap.put(key, result);
        }
        return result;
    }

    BoxInStudio getBoxInStudio(long key) {
        if (key == 0) {
            return null;
        }

        BoxInStudio result = boxInStudioHashMap.get(key);
        if (result != null) {
            return result;
        }

        result = studioBoxService.get(key);
        if (result != null) {
            boxInStudioHashMap.put(key, result);
        }
        return result;
    }

    /*****************
     *  YARN
     *****************/
    public List<Yarn> findAll() {
        try {
            return jdbcTemplate.query("SELECT id, yarn_no, numbers_in_store, " +
                            "numbers_in_studio, box_in_store_id, box_in_studio_id, yarn_color FROM Yarn " +
                            "ORDER BY yarn_no ASC",
                    (rs, rowNum) -> new Yarn(rs.getLong("id"),
                            rs.getInt("yarn_no"),
                            rs.getInt("numbers_in_store"),
                            rs.getInt("numbers_in_studio"),
                            getBoxInStore(rs.getLong("box_in_store_id")),
                            getBoxInStudio(rs.getLong("box_in_studio_id")),
                            rs.getString("yarn_color")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<Yarn> findAll(String filterText) {
        try {
            if (filterText == null || filterText.isEmpty()) {
                return jdbcTemplate.query("SELECT id, yarn_no, numbers_in_store, " +
                                "numbers_in_studio, box_in_store_id, box_in_studio_id, yarn_color FROM Yarn " +
                                "ORDER BY yarn_no ASC",
                        (rs, rowNum) -> new Yarn(rs.getLong("id"),
                                rs.getInt("yarn_no"),
                                rs.getInt("numbers_in_store"),
                                rs.getInt("numbers_in_studio"),
                                getBoxInStore(rs.getLong("box_in_store_id")),
                                getBoxInStudio(rs.getLong("box_in_studio_id")),
                                rs.getString("yarn_color")));
            } else {
                return jdbcTemplate.query("SELECT id, yarn_no, numbers_in_store, " +
                                "numbers_in_studio, box_in_store_id, box_in_studio_id, yarn_color FROM Yarn " +
                                "where yarn_no like '" + filterText + "%' ",
                        (rs, rowNum) -> new Yarn(rs.getLong("id"),
                                rs.getInt("yarn_no"),
                                rs.getInt("numbers_in_store"),
                                rs.getInt("numbers_in_studio"),
                                getBoxInStore(rs.getLong("box_in_store_id")),
                                getBoxInStudio(rs.getLong("box_in_studio_id")),
                                rs.getString("yarn_color")));
            }
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public Yarn get(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, yarn_no, numbers_in_store, " +
                            "numbers_in_studio, box_in_store_id, box_in_studio_id, yarn_color FROM Yarn WHERE id = " + id,
                    (rs, rowNum) -> new Yarn(rs.getLong("id"),
                            rs.getInt("yarn_no"),
                            rs.getInt("numbers_in_store"),
                            rs.getInt("numbers_in_studio"),
                            getBoxInStore(rs.getLong("box_in_store_id")),
                            getBoxInStudio(rs.getLong("box_in_studio_id")),
                            rs.getString("yarn_color")));
        } catch (Exception e) {
            return null;
        }
    }

    public Integer getBallsAtHome(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT  numbers_in_store, " +
                            "numbers_in_studio FROM Yarn WHERE id = " + id,
                    (rs, rowNum) ->
                            rs.getInt("numbers_in_store") +
                            rs.getInt("numbers_in_studio"));
        } catch (Exception e) {
            return null;
        }
    }

    public void update(Yarn person) {
        jdbcTemplate.update("UPDATE Yarn SET yarn_no=?, numbers_in_store=?, numbers_in_studio=?, " +
                        "box_in_store_id=?, box_in_studio_id=?, yarn_color=? WHERE id = ?",
                person.getYarnNo(),
                person.getNumberInStore(),
                person.getNumberInStudio(),
                (person.getBoxInStore() == null || person.getBoxInStore().getId() == 0L) ? null : person.getBoxInStore().getId(),
                (person.getBoxInStudio() == null || person.getBoxInStudio().getId() == 0L) ? null : person.getBoxInStudio().getId(),
                person.getColorCodeString(),
                person.getId());
    }

    public void delete(Yarn person) {
        jdbcTemplate.update("DELETE from Yarn WHERE id=?",
                person.getId());
    }

    public void save(Yarn person) {
        if (person == null) {
            LOGGER.log(Level.SEVERE,
                    "Person is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (person.getYarnNo() == null) {
            LOGGER.log(Level.SEVERE,
                    "Person name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (person.getId() != null) {
            update(person);
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("INSERT INTO Yarn (yarn_no, numbers_at_work, numbers_in_store, " +
                            "numbers_in_studio, box_in_store_id, box_in_studio_id, yarn_color) values " +
                            "( ?, ?, ?, ?, ?, ?, ?); ",
                    person.getYarnNo(),
                    person.getNumberInStore(), person.getNumberInStudio(),
                    person.getBoxInStore() != null ? person.getBoxInStore().getId() : null,
                    person.getBoxInStudio() != null ? person.getBoxInStudio().getId() : null,
                    person.getColorCodeString());
        }
    }

    /*****************
     *  YARN PRODUCT
     *****************/
    public List<YarnProduct> findAllYarnProducts(Long yarnId) {
        try {
            return jdbcTemplate.query("SELECT id, product_id, yarn_id, numbers_used FROM Product_Yarn  " +
                            "WHERE yarn_Id = " + yarnId,
                    (rs, rowNum) -> new YarnProduct(rs.getLong("id"),
                            rs.getLong("yarn_id"),
                            productService.get(rs.getLong("product_id")),
                            rs.getInt("numbers_used")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public void delete(YarnProduct yarnProduct) {
        jdbcTemplate.update("DELETE from Product_Yarn WHERE id=?",
                yarnProduct.getId());
    }

    public void save(YarnProduct yarnProduct) {
        if (yarnProduct == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (yarnProduct.getYarnId() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (yarnProduct.getId() != null) {
            jdbcTemplate.update("UPDATE Product_Yarn SET product_id=?, numbers_used =? WHERE id=?",
                    yarnProduct.getProduct().getId(), yarnProduct.getNumberUsed(), yarnProduct.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("INSERT INTO Product_Yarn (product_id, yarn_id, numbers_used) values " +
                            "( ?, ?, ?); ",
                    yarnProduct.getProduct().getId(), yarnProduct.getYarnId(), yarnProduct.getNumberUsed());
        }
    }

    /*********************
     *  YARN BOX IN STORE
     *********************/
    public List<YarnBoxInStore> findAllYarnBoxInStore(Long yarnId) {
        try {
            return jdbcTemplate.query("SELECT id, yarn_id, box_id, numbers_used FROM Yarn_BoxInStore  " +
                            "WHERE yarn_Id = " + yarnId,
                    (rs, rowNum) -> new YarnBoxInStore(rs.getLong("id"),
                            rs.getLong("yarn_id"),
                            storeBoxService.get(rs.getLong("box_id")),
                            rs.getInt("numbers_used")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public void delete(YarnBoxInStore yarnBoxInStore) {
        jdbcTemplate.update("DELETE from Yarn_BoxInStore WHERE id=?",
                yarnBoxInStore.getId());
    }

    public void save(YarnBoxInStore yarnBoxInStore) {
        if (yarnBoxInStore == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (yarnBoxInStore.getYarnId() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (yarnBoxInStore.getId() != null) {
            jdbcTemplate.update("UPDATE Yarn_BoxInStore SET box_id=?, numbers_used =? WHERE id=?",
                    yarnBoxInStore.getBoxInStore().getId(), yarnBoxInStore.getNumberOfBalls(), yarnBoxInStore.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("INSERT INTO Yarn_BoxInStore (yarn_id, box_id, numbers_used) values " +
                            "( ?, ?, ?); ",
                    yarnBoxInStore.getBoxInStore().getId(), yarnBoxInStore.getYarnId(), yarnBoxInStore.getNumberOfBalls());
        }
    }

    /**********************
     *  YARN BOX IN STUDIO
     **********************/
    public List<YarnBoxInStudio> findAllYarnBoxInStudio(Long yarnId) {
        try {
            return jdbcTemplate.query("SELECT id, yarn_id, box_id, numbers_used FROM Yarn_BoxInStudio  " +
                            "WHERE yarn_Id = " + yarnId,
                    (rs, rowNum) -> new YarnBoxInStudio(rs.getLong("id"),
                            rs.getLong("yarn_id"),
                            studioBoxService.get(rs.getLong("box_id")),
                            rs.getInt("numbers_used")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public void delete(YarnBoxInStudio yarnBoxInStudio) {
        jdbcTemplate.update("DELETE from Yarn_BoxInStudio WHERE id=?",
                yarnBoxInStudio.getId());
    }

    public void save(YarnBoxInStudio yarnBoxInStudio) {
        if (yarnBoxInStudio == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (yarnBoxInStudio.getYarnId() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (yarnBoxInStudio.getId() != null) {
            jdbcTemplate.update("UPDATE Yarn_BoxInStudio SET box_id=?, numbers_used =? WHERE id=?",
                    yarnBoxInStudio.getBoxInStudio().getId(), yarnBoxInStudio.getNumberOfBalls(), yarnBoxInStudio.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("INSERT INTO Yarn_BoxInStudio (yarn_id, box_id, numbers_used) values " +
                            "( ?, ?, ?); ",
                    yarnBoxInStudio.getBoxInStudio().getId(), yarnBoxInStudio.getYarnId(), yarnBoxInStudio.getNumberOfBalls());
        }
    }

    /******************************
     *  YARN ALTERNATIVE SUPPLIER
     ******************************/
    public List<YarnAlternative> findAllYarnAlternative(Long yarnId) {
        try {
            return jdbcTemplate.query("SELECT yaa.id, yaa.yarn_id, yaa.supplier_id, yaa.alt_yarn_id, " +
                            "yaa.description FROM Yarn_Alternative yaa " +
                            "INNER JOIN Yarn ya ON yaa.yarn_id = ya.id  " +
                            "WHERE yarn_id = " + yarnId,
                    (rs, rowNum) -> new YarnAlternative(rs.getLong("id"),
 //                           rs.getLong("yarn_id"),
  //                          rs.getInt("yarn_no"),
                            get(rs.getLong("yarn_id")),
                            supplierService.get(rs.getLong("supplier_id")),
                            rs.getString("alt_yarn_id"),
                            rs.getString("description")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<YarnAlternative> findAllYarnAlternatives(String filterText) {
        try {
            if (filterText == null || filterText.isEmpty()) {
                return jdbcTemplate.query("SELECT yaa.id, yaa.yarn_id, ya.yarn_no, yaa.supplier_id, yaa.yarn_alt_id, " +
                        "yaa.description FROM Yarn_Alternative yaa " +
                                "INNER JOIN Yarn ya ON yaa.yarn_id = ya.id  ",
                        (rs, rowNum) -> new YarnAlternative(rs.getLong("id"),
//                                rs.getLong("yarn_id"),
//                                rs.getInt("yarn_no"),
                                get(rs.getLong("yarn_id")),
                                supplierService.get(rs.getLong("supplier_id")),
                                rs.getString("yarn_alt_id"),
                                rs.getString("description")));
            } else {
                return jdbcTemplate.query("SELECT yaa.id, yaa.yarn_id, ya.yarn_no, yaa.supplier_id, yaa.yarn_alt_id, " +
                                "description FROM Yarn_Alternative yaa " +
                                "INNER JOIN Yarn ya ON yaa.yarn_id = ya.id " +
                                "WHERE ya.yarn_no LIKE '" + filterText + "%' ",
                        (rs, rowNum) -> new YarnAlternative(rs.getLong("id"),
//                                rs.getLong("yarn_id"),
//                                rs.getInt("yarn_no"),
                                get(rs.getLong("yarn_id")),
                                supplierService.get(rs.getLong("supplier_id")),
                                rs.getString("yarn_alt_id"),
                                rs.getString("description")));
            }
        } catch (Exception e) {
            return new ArrayList();
        }
    }



    public void delete(YarnAlternative yarnAlternative) {
        jdbcTemplate.update("DELETE from Yarn_Alternative WHERE id=?",
                yarnAlternative.getId());
    }

    public void save(YarnAlternative yarnAlternative) {
        if (yarnAlternative == null) {
            LOGGER.log(Level.SEVERE,
                    "Yarn alternative is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (yarnAlternative.getYarn() == null) {
            LOGGER.log(Level.SEVERE,
                    "Yarn ID is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (yarnAlternative.getId() != null) {
            jdbcTemplate.update("UPDATE Yarn_Alternative SET yarn_id=?, supplier_id=?, yarn_alt_id =?, " +
                            "description = ? WHERE id=?",
                    yarnAlternative.getYarn().getId(), yarnAlternative.getSupplier().getId(), yarnAlternative.getAltYarnId(),
                    yarnAlternative.getDescription(), yarnAlternative.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("INSERT INTO Yarn_Alternative (yarn_id, supplier_id, yarn_alt_id, " +
                            "description) values ( ?, ?, ?, ?); ",
                    yarnAlternative.getYarn().getId(), yarnAlternative.getSupplier().getId(),
                    yarnAlternative.getAltYarnId(), yarnAlternative.getDescription());
        }
    }
}
