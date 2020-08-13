package com.xagu.studio.studiosystem.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;

/**
 * @author xagu
 * Created on 2020/8/2
 * Email:xagu_qc@foxmail.com
 * Describe: TODO
 */
public class UniqueNameGenerator extends AnnotationBeanNameGenerator {


    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        return definition.getBeanClassName();
    }
}


