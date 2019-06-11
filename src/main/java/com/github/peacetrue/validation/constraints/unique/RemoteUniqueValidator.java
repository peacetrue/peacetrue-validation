package com.github.peacetrue.validation.constraints.unique;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * validator for {@link UniqueRemote}
 *
 * @author xiayx
 */
public class RemoteUniqueValidator extends UniqueValidator<UniqueRemote> {

    @PersistenceContext
    protected EntityManager entityManager;
    protected String value;

    @Override
    protected void initializeAnnotation(UniqueRemote unique) {
        beanClass = unique.beanClass();
        properties = unique.properties();
        propertyValues = unique.propertyValues();
        delimiter = unique.delimiter();
        value = unique.value();
    }

    public boolean isValid(List<?> value, ConstraintValidatorContext context) {
        if (value == null || entityManager == null) return true;

        Set<?> local = value.stream().filter(Objects::nonNull).map(this::getValues)
                .filter(Objects::nonNull).map(this::concatValues).collect(Collectors.toSet());
        if (local.size() == 0) return true;//all null

        List<?> remote = entityManager.createQuery(this.value).setParameter(1, local).getResultList();//Object[] length 2
        List<?> duplication = remote.stream().filter(o -> countGtZero((Object[]) o)).collect(Collectors.toList());
        if (duplication.isEmpty()) return true;

        buildConstraintViolation(context, duplication);
        return false;
    }

    private static boolean countGtZero(Object[] properties) {
        return ((Long) properties[1]) > 0;
    }

    @Override
    protected void buildConstraintViolation(ConstraintValidatorContext context, List<?> duplication) {
        duplication = duplication.stream().map(o -> ((Object[]) o)[0]).collect(Collectors.toList());
        super.buildConstraintViolation(context, duplication);
    }

}