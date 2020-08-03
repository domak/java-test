package org.dmk.spring;

import javax.inject.Named;

/**
 * Created by domak on 01/02/15.
 */
@Named
public class SpringTestDao {
    public String getMessage() {
        return "toto";
    }
}
