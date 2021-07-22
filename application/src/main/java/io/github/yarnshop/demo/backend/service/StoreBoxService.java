package io.github.yarnshop.demo.backend.service;


import io.github.yarnshop.demo.backend.entity.BoxInStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class StoreBoxService {
    private static final Logger LOGGER = Logger.getLogger(StoreBoxService.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BoxInStore> findAll() {
        try {
            return jdbcTemplate.query("SELECT id, name, description FROM BoxInStore",
                    (rs, rowNum) -> new BoxInStore(rs.getLong("id"),
                    rs.getString("name"), rs.getString("description")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<BoxInStore> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return jdbcTemplate.query("SELECT id, name, description FROM BoxInStore",
                    (rs, rowNum) -> new BoxInStore(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
        } else  {
            return jdbcTemplate.query("SELECT id, name, description FROM BoxInStore where name like '%"+ filterText +"%'" ,
                    (rs, rowNum) -> new BoxInStore(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
        }
    }

    public BoxInStore get(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name, description FROM BoxInStore where id = " + id,
                    (rs, rowNum) -> new BoxInStore(rs.getLong("id"),
                            rs.getString("name"), rs.getString("description")));
        } catch (Exception e) {
            return null;
        }
    }

    public void update(BoxInStore boxInStore) {
        jdbcTemplate.update("UPDATE BoxInStore SET name=?, description=? WHERE id=?",
                boxInStore.getName(), boxInStore.getDescription(),  boxInStore.getId());
    }

    public void delete(BoxInStore boxInStore) {
        jdbcTemplate.update("DELETE from BoxInStore WHERE id=?",
                boxInStore.getId());
    }

    public void save(BoxInStore boxInStore) {
        if (boxInStore == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (boxInStore.getName() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (boxInStore.getId() != null) {
            jdbcTemplate.update("UPDATE BoxInStore SET name=?, description=? WHERE id=?",
                    boxInStore.getName(), boxInStore.getDescription(),  boxInStore.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("insert into BoxInStore (name, description) values " +
                            "( ?, ?); ",
                    boxInStore.getName(), boxInStore.getDescription());
        }
    }
}
