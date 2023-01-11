package study.core.spring.security.studycorespringsecurity;

import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.method.MapBasedMethodSecurityMetadataSource;

@SpringBootApplication
public class StudyCoreSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyCoreSpringSecurityApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
}
