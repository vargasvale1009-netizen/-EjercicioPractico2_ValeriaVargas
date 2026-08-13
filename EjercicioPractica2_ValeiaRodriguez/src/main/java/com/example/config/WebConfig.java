package com.example.config;

import com.example.domain.Role;
import com.example.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Role>() {
            @Override
            public Role convert(String id) {
                if (id == null || id.isEmpty()) return null;
                return roleRepository.findById(Long.parseLong(id)).orElse(null);
            }
        });
    }
}
