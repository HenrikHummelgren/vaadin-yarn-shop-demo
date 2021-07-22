package io.github.yarnshop.demo.backend.entity;


public class Product {

    private Long id;
    private Integer productNo;
    private String name;
    private Integer noOfYarn = 0;
    private Boolean allYarnInStore;
    private Boolean allYarnInStudio;

    public Product() {
        this.id = null;
        this.productNo = null;
        this.name = null;
        this.noOfYarn = 0;
        this.allYarnInStore = Boolean.FALSE;
        this.allYarnInStudio = Boolean.FALSE;
    }

    public Product(Long id, Integer productNo, String name, Integer noOfYarn) {
        this.id = id;
        this.productNo = productNo;
        this.name = name;
        this.noOfYarn = noOfYarn;
        this.allYarnInStore = Boolean.FALSE;
        this.allYarnInStudio = Boolean.FALSE;
    }

    public Product(Long id, Integer productNo, String name,
                   Boolean allYarnInStore, Boolean allYarnInStudio) {
        this.id = id;
        this.productNo = productNo;
        this.name = name;
        this.noOfYarn = 0;
        this.allYarnInStore = allYarnInStore;
        this.allYarnInStudio = allYarnInStudio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getProductNo() {
        return productNo;
    }

    public void setProductNo(Integer productNo) {
        this.productNo = productNo;
    }

    public String getProductNoString() {
        return productNo.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAllYarnInStore() {
        return allYarnInStore;
    }

    public void setAllYarnInStore(Boolean allYarnInStore) {
        this.allYarnInStore = allYarnInStore;
    }

    public Boolean getAllYarnInStudio() {
        return allYarnInStudio;
    }

    public void setAllYarnInStudio(Boolean allYarnInStudio) {
        this.allYarnInStudio = allYarnInStudio;
    }

    public Integer getNoOfYarn() {
        return noOfYarn;
    }

    public void setNoOfYarn(Integer noOfYarn) {
        this.noOfYarn = noOfYarn;
    }

    @Override
    public String toString() {
        return productNo + " " + name;
    }

}
