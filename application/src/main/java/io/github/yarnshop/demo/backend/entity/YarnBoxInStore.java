package io.github.yarnshop.demo.backend.entity;


public class YarnBoxInStore {

    private Long id;
    private Long yarnId;
    private BoxInStore boxInStore;
    private Integer numberOfBalls;

    public YarnBoxInStore() {
        this.id = null;
        this.yarnId = null;
        this.boxInStore = null;
        this.numberOfBalls = null;
    }

    public YarnBoxInStore(Long id, Long yarnId, BoxInStore boxInStore, Integer numberOfBalls) {
        this.id = id;
        this.yarnId = yarnId;
        this.boxInStore = boxInStore;
        this.numberOfBalls = numberOfBalls;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getYarnId() {
        return yarnId;
    }

    public void setYarnId(Long yarnId) {
        this.yarnId = yarnId;
    }

    public BoxInStore getBoxInStore() {
        return boxInStore;
    }

    public void setBoxInStore(BoxInStore boxInStore) {
        this.boxInStore = boxInStore;
    }

    public Integer getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(Integer numberOfBalls) {
        this.numberOfBalls = numberOfBalls;
    }

    @Override
    public String toString() {
        return yarnId + " " + boxInStore;
    }

}
