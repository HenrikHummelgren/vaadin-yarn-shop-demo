package io.github.yarnshop.demo.backend.entity;


public class YarnBoxInStudio {

    private Long id;
    private Long yarnId;
    private BoxInStudio boxInStudio;
    private Integer numberOfBalls;

    public YarnBoxInStudio() {
        this.id = null;
        this.yarnId = null;
        this.boxInStudio = null;
        this.numberOfBalls = null;
    }

    public YarnBoxInStudio(Long id, Long yarnId, BoxInStudio boxInStudio, Integer numberOfBalls) {
        this.id = id;
        this.yarnId = yarnId;
        this.boxInStudio = boxInStudio;
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

    public BoxInStudio getBoxInStudio() {
        return boxInStudio;
    }

    public void setBoxInStudio(BoxInStudio boxInStudio) {
        this.boxInStudio = boxInStudio;
    }

    public Integer getNumberOfBalls() {
        return numberOfBalls;
    }

    public void setNumberOfBalls(Integer numberOfBalls) {
        this.numberOfBalls = numberOfBalls;
    }

    @Override
    public String toString() {
        return yarnId + " " + boxInStudio;
    }

}
