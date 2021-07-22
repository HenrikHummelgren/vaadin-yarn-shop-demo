package io.github.yarnshop.demo.backend.service;


import io.github.yarnshop.demo.backend.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class SupplierService {
    private static final Logger LOGGER = Logger.getLogger(SupplierService.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Supplier> findAll() {
        try {
            return jdbcTemplate.query("SELECT id, name, description FROM Supplier",
                    (rs, rowNum) -> new Supplier(rs.getLong("id"),
                    rs.getString("name"), rs.getString("description")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<Supplier> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return jdbcTemplate.query("SELECT id, name, description FROM Supplier",
                    (rs, rowNum) -> new Supplier(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
        } else  {
            return jdbcTemplate.query("SELECT id, name, description FROM Supplier where name like '%"+ filterText +"%'" ,
                    (rs, rowNum) -> new Supplier(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
        }
    }

    public Supplier get(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name, description FROM Supplier where id = " + id,
                    (rs, rowNum) -> new Supplier(rs.getLong("id"),
                            rs.getString("name"), rs.getString("description")));
        } catch (Exception e) {
            return null;
        }
    }

    public void update(Supplier supplier) {
        jdbcTemplate.update("UPDATE Supplier SET name=?, description=? WHERE id=?",
                supplier.getName(), supplier.getDescription(),  supplier.getId());
    }

    public void delete(Supplier supplier) {
        jdbcTemplate.update("DELETE FROM Supplier WHERE id=?",
                supplier.getId());
    }

    public void save(Supplier supplier) {
        if (supplier == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (supplier.getName() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (supplier.getId() != null) {
            jdbcTemplate.update("UPDATE Supplier SET name=?, description=? WHERE id=?",
                    supplier.getName(), supplier.getDescription(),  supplier.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("INSERT INTO Supplier (name, description) values " +
                            "( ?, ?); ",
                    supplier.getName(), supplier.getDescription());
        }
    }
}
