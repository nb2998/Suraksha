package com.codingblocks.suraksha.Models;

/**
 * Created by anant bansal on 6/4/2017.
 */

public class AddedContacts {

    String nameAdded;
    String numberAdded;

    public AddedContacts(String numberAdded, String nameAdded) {
        this.nameAdded = nameAdded;
        this.numberAdded = numberAdded;
    }

    public String getNameAdded() {
        return nameAdded;
    }

    public void setNameAdded(String nameAdded) {
        this.nameAdded = nameAdded;
    }

    public String getNumberAdded() {
        return numberAdded;
    }

    public void setNumberAdded(String numberAdded) {
        this.numberAdded = numberAdded;
    }
}
