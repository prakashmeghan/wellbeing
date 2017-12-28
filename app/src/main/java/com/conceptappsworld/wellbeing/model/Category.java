package com.conceptappsworld.wellbeing.model;

/**
 * Created by prakash.meghani on 12/28/2017.
 */

public class Category {
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    private int categoryId;
    private String categoryName;


}
