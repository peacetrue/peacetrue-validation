package com.github.peacetrue.validation.constraints.mapbean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * validator for {@link MapBean}
 *
 * @author xiayx
 */
public class MapBeanValidator implements ConstraintValidator<MapBean, Map<String, Object>> {


    private final static Map<String, RestraintValidator> VALIDATORS = new HashMap<>(4);

    static {
        VALIDATORS.put("required", new Required());
        VALIDATORS.put("length", new Length());
        VALIDATORS.put("range", new Range());
        VALIDATORS.put("precision", new Precision());
    }

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private String moduleCode;
    @Autowired(required = false)
    private RestraintProvider restraintProvider;

    @Override
    public void initialize(MapBean annotation) {
        moduleCode = annotation.value();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean isValid(Map<String, Object> value, ConstraintValidatorContext context) {
        logger.info("check the map bean is valid");
        logger.debug("map bean: {}", value);

        if (value == null || value.isEmpty()) {
            logger.trace("empty value means valid");
            return true;
        }

        if (restraintProvider == null) {
            logger.warn("can't found RestraintProvider for service");
            return true;
        }

        boolean isValid = true;
        for (Map.Entry<String, Object> entry : value.entrySet()) {
            String propertyName = entry.getKey();
            logger.debug("check the property '{}' in map bean", propertyName);
            List<Restraint> restraints = restraintProvider.getRestraints(moduleCode, propertyName);
            logger.trace("get restraints {} for property '{}'", restraints, propertyName);
            if (restraints == null || restraints.isEmpty()) {
                logger.trace("empty restraints means valid for property '{}'", propertyName);
                continue;
            }

            for (Restraint restraint : restraints) {
                RestraintValidator validator = VALIDATORS.get(restraint.getName());
                logger.trace("check the {}={} use restraint {}", propertyName, entry.getValue(), restraint.getName());
                if (validator.isValid(entry.getValue(), restraint.getValue())) {
                    logger.trace("check {} valid", propertyName);
                    continue;
                }
                logger.trace("check {} invalid", propertyName);

                if (isValid) {
                    isValid = false;
                    context.disableDefaultConstraintViolation();
                }
                validator.buildConstraintViolation(context, propertyName, restraint);
            }
        }

        return isValid;
    }


}