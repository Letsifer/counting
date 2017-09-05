package ru.lets.counting.domain;

/**
 *
 * @author Евгений
 */
public class CategoryFilter {

    private String entrance;

    private Integer parentCategoryId;

    public CategoryFilter() {
    }

    public CategoryFilter(String entrance, Integer parentCategoryId) {
        this.entrance = entrance;
        this.parentCategoryId = parentCategoryId;
    }

    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

}
