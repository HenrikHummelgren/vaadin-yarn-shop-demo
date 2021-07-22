package io.github.yarnshop.demo.backend.entity;


public class BoxInStore {

    private Long id;
    private String name;
    private String description;

    public BoxInStore() {
        this.id = null;
        this.name = null;
        this.description = null;
    }

    public BoxInStore(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return name + " " + description;
    }

}
