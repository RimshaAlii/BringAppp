package com.example.bringappfinal;

public class SubCategoryItem {

    private String categoryId;
    private String subId;
    private String subCategoryName;
    private String subCategoryPrice;
    private String isChecked;

    public String getSubCategoryPrice() {
        return subCategoryPrice;
    }

    public void setSubCategoryPrice(String subCategoryPrice) {
        this.subCategoryPrice = subCategoryPrice;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }

    public String getSubCategoryName() {
        return subCategoryName;
    }

    public void setSubCategoryName(String subCategoryName) {
        this.subCategoryName = subCategoryName;
    }

    public String getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(String isChecked) {
        this.isChecked = isChecked;
    }
}
