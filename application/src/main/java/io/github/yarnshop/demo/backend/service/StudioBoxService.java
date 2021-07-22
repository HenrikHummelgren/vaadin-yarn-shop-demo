package io.github.yarnshop.demo.backend.service;


import io.github.yarnshop.demo.backend.entity.BoxInStudio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class StudioBoxService {
    private static final Logger LOGGER = Logger.getLogger(StudioBoxService.class.getName());

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BoxInStudio> findAll() {
        try {
            return jdbcTemplate.query("SELECT id, name, description FROM BoxInStudio",
                    (rs, rowNum) -> new BoxInStudio(rs.getLong("id"),
                    rs.getString("name"), rs.getString("description")));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public List<BoxInStudio> findAll(String filterText) {
        if(filterText == null || filterText.isEmpty()) {
            return jdbcTemplate.query("SELECT id, name, description FROM BoxInStudio",
                    (rs, rowNum) -> new BoxInStudio(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
        } else  {
            return jdbcTemplate.query("SELECT id, name, description FROM BoxInStudio where name like '%"+ filterText +"%'" ,
                    (rs, rowNum) -> new BoxInStudio(rs.getLong("id"), rs.getString("name"), rs.getString("description")));
        }
    }

    public BoxInStudio get(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, name, description FROM BoxInStudio where id = " + id,
                    (rs, rowNum) -> new BoxInStudio(rs.getLong("id"),
                            rs.getString("name"), rs.getString("description")));
        } catch (Exception e) {
            return null;
        }
    }

    public void update(BoxInStudio boxInStudio) {
        jdbcTemplate.update("UPDATE BoxInStudio SET name=?, description=? WHERE id=?",
                boxInStudio.getName(), boxInStudio.getDescription(),  boxInStudio.getId());
    }

    public void delete(BoxInStudio boxInStudio) {
        jdbcTemplate.update("DELETE from BoxInStudio WHERE id=?",
                boxInStudio.getId());
    }

    public void save(BoxInStudio boxInStudio) {
        if (boxInStudio == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact is null. Are you sure you have connected your form to the application?");
            return;
        }
        else if (boxInStudio.getName() == null) {
            LOGGER.log(Level.SEVERE,
                    "Contact name is null. Are you sure you have connected your form to the application?");
            return;
        }

        // Update of existing item in DB
        if (boxInStudio.getId() != null) {
            jdbcTemplate.update("UPDATE BoxInStudio SET name=?, description=? WHERE id=?",
                    boxInStudio.getName(), boxInStudio.getDescription(),  boxInStudio.getId());
        }
        // Inserting new item in DB
        else {
            jdbcTemplate.update("insert into BoxInStudio (name, description) values " +
                            "( ?, ?); ",
                    boxInStudio.getName(), boxInStudio.getDescription());
        }
    }
}
