package com.github.peacetrue.validation.constraints.mapbean;

import java.util.List;

/**
 * get restraints
 *
 * @author xiayx
 */
public interface RestraintProvider {

    List<Restraint> getRestraints(String beanName, String propertyName);

}
