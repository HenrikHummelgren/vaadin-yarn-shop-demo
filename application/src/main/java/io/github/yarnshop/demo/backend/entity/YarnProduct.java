package io.github.yarnshop.demo.backend.entity;


public class YarnProduct {

    private Long id;
    private Long yarnId;
    private Product product;
    private Integer numberUsed;

    public YarnProduct() {
        this.id = null;
        this.yarnId = null;
        this.product = null;
        this.numberUsed = null;
    }

    public YarnProduct(Long id, Long yarnId, Product product, Integer numberUsed) {
        this.id = id;
        this.yarnId = yarnId;
        this.product = product;
        this.numberUsed = numberUsed;
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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getNumberUsed() {
        return numberUsed;
    }

    public void setNumberUsed(Integer numberUsed) {
        this.numberUsed = numberUsed;
    }

    @Override
    public String toString() {
        return yarnId + " " + product;
    }

}
