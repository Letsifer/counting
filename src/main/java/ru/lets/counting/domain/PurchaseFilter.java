package ru.lets.counting.domain;

import java.time.LocalDate;

/**
 *
 * @author Евгений
 */
public class PurchaseFilter {

    private Integer minAmount;

    private Integer maxAmount;

    private Integer categoryId;

    private LocalDate minBuyDate;

    private LocalDate maxBuyDate;

    public LocalDate getMinBuyDate() {
        return minBuyDate;
    }

    public void setMinBuyDate(LocalDate minBuyDate) {
        this.minBuyDate = minBuyDate;
    }

    public LocalDate getMaxBuyDate() {
        return maxBuyDate;
    }

    public void setMaxBuyDate(LocalDate maxBuyDate) {
        this.maxBuyDate = maxBuyDate;
    }

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
