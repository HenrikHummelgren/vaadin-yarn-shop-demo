package io.github.yarnshop.demo.backend.entity;


public class Yarn {

    private Long id;
    private Integer yarnNo;
    private Integer numberInStore;
    private Integer numberInStudio;
    private BoxInStore boxInStore;
    private BoxInStudio boxInStudio;
    private String colorCodeString;


    public Yarn() {
        this.id = null;
        this.yarnNo = null;
        this.numberInStore = null;
        this.numberInStudio = null;
        this.boxInStore = null;
        this.boxInStudio = null;
        this.colorCodeString = null;
    }

    public Yarn(Long id, Integer yarnNo, Integer numberInStore,
                Integer numberInStudio, BoxInStore boxInStore, BoxInStudio boxInStudio, String colorCodeString) {
        this.id = id;
        this.yarnNo = yarnNo;
        this.numberInStore = numberInStore;
        this.numberInStudio = numberInStudio;
        this.boxInStore = boxInStore;
        this.boxInStudio = boxInStudio;
        this.colorCodeString = colorCodeString;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYarnNo() {
        return yarnNo;
    }

    public String getYarnNoString() {
        return yarnNo.toString();
    }

    public void setYarnNo(Integer yarnNo) {
        this.yarnNo = yarnNo;
    }

    public Integer getNumberInStore() {
        return numberInStore;
    }

    public void setNumberInStore(Integer numberInStore) {
        this.numberInStore = numberInStore;
    }

    public Integer getNumberInStudio() {
        return numberInStudio;
    }

    public void setNumberInStudio(Integer lastName) {
        this.numberInStudio = lastName;
    }

    public Integer getNumberTotal() {
        return numberInStudio+ numberInStore;
    }

    public BoxInStore getBoxInStore() {
        return boxInStore;
    }

    public void setBoxInStore(BoxInStore boxInStore) {
        this.boxInStore = boxInStore;
    }

    public BoxInStudio getBoxInStudio() {
        return boxInStudio;
    }

    public void setBoxInStudio(BoxInStudio boxInStudio) {
        this.boxInStudio = boxInStudio;
    }

    public String getColorCodeString() {
        return colorCodeString;
    }

    public void setColorCodeString(String colorCodeString) {
        this.colorCodeString = colorCodeString;
    }

    public String getFullName() {
        return yarnNo.toString();
    }

    @Override
    public String toString() {
        return yarnNo.toString();
    }

}
