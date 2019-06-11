package com.github.peacetrue.validation.constraints.in;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * validator for {@link InRemote}
 *
 * @author xiayx
 */
public class RemoteInValidator extends InValidator<InRemote> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    protected void initializeAnnotation(InRemote in) {
        delimiter = in.delimiter();
        if (in.value().isEmpty()) {
            logger.trace("no remote value found, skip remote validate");
        } else {
            values = entityManager.createQuery(in.value()).getResultList();
            logger.debug("set remote value {} to this", values);
        }
    }

}
