/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dmk.serialization;

import java.io.Serializable;

/**
 *
 * @author domak
 */
public class Address implements Serializable {

    private String number;
    private String street;
    private String town;

    public Address(String number, String street, String town) {
        this.number = number;
        this.street = street;
        this.town = town;
    }

    public String getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public String getTown() {
        return town;
    }
}
