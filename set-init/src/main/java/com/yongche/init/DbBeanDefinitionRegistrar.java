package com.yongche.init;

import com.yongche.anno.MangoScan;
import org.jfaster.mango.annotation.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * register
 * by yongche.com
 *
 * @author mma
 * @since 2017-11-26 下午9:50
 */
public class DbBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    private static final Logger logger = LoggerFactory.getLogger(DbBeanDefinitionRegistrar.class);

    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        logger.info("register DAO class....");
        List<Class<?>> basePackages = findMangoDaoClasses(importingClassMetadata);
        Class<?> factory = getFactoryMango(importingClassMetadata);
        for (Class<?> daoClass : basePackages) {
            addPostProcessor(registry, daoClass,factory);
        }
    }

    private void addPostProcessor(BeanDefinitionRegistry registry,Class<?> clasName) {
        GenericBeanDefinition bf = new GenericBeanDefinition();
        bf.setBeanClassName(clasName.getName());
        MutablePropertyValues pvs = bf.getPropertyValues();
        bf.setBeanClass(clasName);
        //bf.setPropertyValues(pvs);
        bf.setLazyInit(false);
        registry.registerBeanDefinition(clasName.getName(), bf);
    }

    private void addPostProcessor(BeanDefinitionRegistry registry,Class<?> clasName,Class<?> factoryMango) {
        GenericBeanDefinition bf = new GenericBeanDefinition();
        bf.setBeanClassName(clasName.getName());
        MutablePropertyValues pvs = bf.getPropertyValues();
        pvs.addPropertyValue("daoClass", clasName);
        bf.setBeanClass(factoryMango);
        bf.setPropertyValues(pvs);
        bf.setLazyInit(false);
        registry.registerBeanDefinition(clasName.getName(), bf);
    }


    private List<String> getPackagesToScan(AnnotationMetadata metadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(MangoScan.class.getName()));
        String[] basePackages = attributes.getStringArray("packages");
        return Arrays.asList(basePackages);
    }


    private Class<?> getFactoryMango(AnnotationMetadata metadata){
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                metadata.getAnnotationAttributes(MangoScan.class.getName()));
        return attributes.getClass("factoryClass");
    }

    private List<Class<?>> findMangoDaoClasses(AnnotationMetadata importingClassMetadata) {
        try {
            List<Class<?>> daos = new ArrayList<Class<?>>();
            ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
            MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
            List<String> locationPatterns = getPackagesToScan(importingClassMetadata);
            for (String locationPattern : locationPatterns) {
                String typeAliasesPackage = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
                        + ClassUtils.convertClassNameToResourcePath(locationPattern)
                        + "/" + DEFAULT_RESOURCE_PATTERN;
                Resource[] rs = resourcePatternResolver.getResources(typeAliasesPackage);
                for (Resource r : rs) {
                    MetadataReader reader = metadataReaderFactory.getMetadataReader(r);
                    AnnotationMetadata annotationMD = reader.getAnnotationMetadata();
                    if (annotationMD.hasAnnotation(DB.class.getName())) {
                        ClassMetadata clazzMD = reader.getClassMetadata();
                        daos.add(Class.forName(clazzMD.getClassName()));
                    }
                }
            }
            return daos;
        } catch (Exception e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
