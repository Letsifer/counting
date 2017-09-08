package ru.lets.counting.domain;

/**
 *
 * @author Евгений
 */
public class PurchaseFilter {

    private Integer minAmount;

    private Integer maxAmount;

    private Integer categoryId;

    public PurchaseFilter(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public PurchaseFilter() {
    }

    public Integer getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Integer minAmount) {
        this.minAmount = minAmount;
    }

    public Integer getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

}
