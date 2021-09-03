package com.nyw.pets.activity.util;

import java.io.Serializable;

public class GetPetsDataListUtil implements Serializable {
    String petsName;
    String isSelectPets;
    String id;
    boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPetsName() {
        return petsName;
    }

    public void setPetsName(String petsName) {
        this.petsName = petsName;
    }

    public String getIsSelectPets() {
        return isSelectPets;
    }

    public void setIsSelectPets(String isSelectPets) {
        this.isSelectPets = isSelectPets;
    }
}
