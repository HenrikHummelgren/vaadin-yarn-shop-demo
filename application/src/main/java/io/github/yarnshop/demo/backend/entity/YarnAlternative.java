package io.github.yarnshop.demo.backend.entity;


public class YarnAlternative {

    private Long id;
    private Long yarnId;
    private Integer yarnNo;
    private Yarn yarn;
    private Supplier supplier;
    private String altYarnId;
    private String description;

    public YarnAlternative() {
        this.id = null;
        this.yarnId = null;
        this.yarnNo = null;
        this.supplier = null;
        this.altYarnId = null;
        this.description = null;
    }

    public YarnAlternative(Long id, Long yarnId, Integer yarnNo, Supplier supplier, String altYarnId,
                           String description) {
        this.id = id;
        this.yarnId = yarnId;
        this.yarnNo = yarnNo;
        this.supplier = supplier;
        this.altYarnId = altYarnId;
        this.description = description;
    }

    public YarnAlternative(Long id, Yarn yarn, Supplier supplier, String altYarnId,
                           String description) {
        this.id = id;
        this.yarn = yarn;
        this.supplier = supplier;
        this.altYarnId = altYarnId;
        this.description = description;
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

    public Integer getYarnNo() {
        return yarnNo;
    }

    public void setYarnNo(Integer yarnNo) {
        this.yarnNo = yarnNo;
    }

    public Yarn getYarn() {
        return yarn;
    }

    public void setYarn(Yarn yarn) {
        this.yarn = yarn;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public String getAltYarnId() {
        return altYarnId;
    }

    public void setAltYarnId(String altYarnId) {
        this.altYarnId = altYarnId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return yarnId + " " + supplier;
    }

}
