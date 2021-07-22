package io.github.yarnshop.demo.backend.entity;


public class ProductYarn {

    private Long id;
    private Long productId;
    private Yarn yarn;
    private Integer numberUsed;
    private Integer numberOfBallsAtHome;

    public ProductYarn() {
        this.id = null;
        this.productId = null;
        this.yarn = null;
        this.numberUsed = null;
    }

    public ProductYarn(Long id, Long productId, Yarn yarn, Integer numberUsed,
                       Integer numberOfBallsAtHome) {
        this.id = id;
        this.productId = productId;
        this.yarn = yarn;
        this.numberUsed = numberUsed;
        this.numberOfBallsAtHome = numberOfBallsAtHome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Yarn getYarn() {
        return yarn;
    }

    public void setYarn(Yarn yarn) {
        this.yarn = yarn;
    }

    public Integer getNumberUsed() {
        return numberUsed;
    }

    public void setNumberUsed(Integer numberUsed) {
        this.numberUsed = numberUsed;
    }

    public Integer getNumberOfBallsAtHome() {
        return numberOfBallsAtHome;
    }

    public void setNumberOfBallsAtHome(Integer numberOfBallsAtHome) {
        this.numberOfBallsAtHome = numberOfBallsAtHome;
    }

    @Override
    public String toString() {
        return productId + " " + yarn ;
    }

}
