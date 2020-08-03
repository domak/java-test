package org.dmk.spring;

import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 */
@Named
public class SpringTestService {

    @Inject
    protected SpringTestDao dao;

    @Transactional
    public String getMessage() {
        return dao.getMessage();
    }
}
