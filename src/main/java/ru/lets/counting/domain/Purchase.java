package ru.lets.counting.domain;

/**
 *
 * @author Евгений
 */
public class Purchase {
    
    private Integer id;

    private Integer amount;

    private String descriprtion;
    
    private Category category;
    
    //TODO add local_date

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDescriprtion() {
        return descriprtion;
    }

    public void setDescriprtion(String descriprtion) {
        this.descriprtion = descriprtion;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
}
