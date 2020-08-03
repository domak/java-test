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
public class Personn implements Serializable {

    private String name;
    private Address address;

    public Personn(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }
}
