//package study.core.spring.security.studycorespringsecurity.sercurity.config;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.access.intercept.RunAsManager;
//import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;
//import org.springframework.security.access.method.MethodSecurityMetadataSource;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
//import study.core.spring.security.studycorespringsecurity.intercepter.CustomMethodSecurityInterceptor;
//import study.core.spring.security.studycorespringsecurity.service.SecurityResourceService;
//
//@EnableGlobalMethodSecurity
//@RequiredArgsConstructor
//public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
//    private final SecurityResourceService securityResourceService;
//
//    @Override
//    protected MethodSecurityMetadataSource customMethodSecurityMetadataSource() {
//        return mapBasedMethodSecurityMetadataSource();
//    }
//
//    @Bean
//    public MapBasedMethodSecurityMetadataSource mapBasedMethodSecurityMetadataSource() {
//        return new MapBasedMethodSecurityMetadataSource(methodResourcesMapFactoryBean().getObject());
//    }
//
//    @Bean
//    public MethodResourcesMapFactoryBean methodResourcesMapFactoryBean(){
//        MethodResourcesMapFactoryBean methodResourcesMapFactoryBean = new MethodResourcesMapFactoryBean();
//        methodResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
//        methodResourcesMapFactoryBean.setResourceType("method");
//        return methodResourcesMapFactoryBean;
//    }
//
//    @Bean
//    @Profile("pointcut")
//    public MethodResourcesMapFactoryBean pointcutResourcesMapFactoryBean(){
//        MethodResourcesMapFactoryBean methodResourcesMapFactoryBean = new MethodResourcesMapFactoryBean();
//        methodResourcesMapFactoryBean.setSecurityResourceService(securityResourceService);
//        methodResourcesMapFactoryBean.setResourceType("pointcut");
//        return methodResourcesMapFactoryBean;
//    }
//
//    @Bean
//    @Profile("pointcut")
//    public ProtectPointcutPostProcessor protectPointcutPostProcessor(){
//        ProtectPointcutPostProcessor protectPointcutPostProcessor = new ProtectPointcutPostProcessor(mapBasedMethodSecurityMetadataSource());
//        protectPointcutPostProcessor.setPointcutMap(pointcutResourcesMapFactoryBean().getObject());
//        return protectPointcutPostProcessor;
//    }
//
//    @Bean
//    public CustomMethodSecurityInterceptor customMethodSecurityInterceptor(MapBasedMethodSecurityMetadataSource methodSecurityMetadataSource) {
//        CustomMethodSecurityInterceptor customMethodSecurityInterceptor =  new CustomMethodSecurityInterceptor();
//        customMethodSecurityInterceptor.setAccessDecisionManager(accessDecisionManager());
//        customMethodSecurityInterceptor.setAfterInvocationManager(afterInvocationManager());
//        customMethodSecurityInterceptor.setSecurityMetadataSource(methodSecurityMetadataSource);
//        RunAsManager runAsManager = runAsManager();
//        if (runAsManager != null) {
//            customMethodSecurityInterceptor.setRunAsManager(runAsManager);
//        }
//
//        return customMethodSecurityInterceptor;
//    }
//
////    @Bean
////    @Profile("pointcut")
////    BeanPostProcessor protectPointcutPostProcessor() throws Exception {
////
////        Class<?> clazz = Class.forName("org.springframework.security.config.method.ProtectPointcutPostProcessor");
////        Constructor<?> declaredConstructor = clazz.getDeclaredConstructor(MapBasedMethodSecurityMetadataSource.class);
////        declaredConstructor.setAccessible(true);
////        Object instance = declaredConstructor.newInstance(mapBasedMethodSecurityMetadataSource());
////        Method setPointcutMap = instance.getClass().getMethod("setPointcutMap", Map.class);
////        setPointcutMap.setAccessible(true);
////        setPointcutMap.invoke(instance, pointcutResourcesMapFactoryBean().getObject());
////
////        return (BeanPostProcessor)instance;
////    }
//}
