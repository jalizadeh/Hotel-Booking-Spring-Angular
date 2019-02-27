package com.linkedin.learning.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import com.linkedin.learning.converter.REtRRConverter;
import com.linkedin.learning.converter.REtRRRConverter;
import com.linkedin.learning.converter.RRtREConverter;

@Configuration
public class ConversionConfig {
	
	public Set<Converter> getConverters(){
		Set<Converter> converters = new HashSet<>();
		converters.add(new REtRRRConverter());
		converters.add(new REtRRConverter());
		converters.add(new RRtREConverter());
		
		return converters;
	}
	
	@Bean
	public ConversionService conversionService() {
		ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
		bean.setConverters(getConverters());
		bean.afterPropertiesSet();
		
		return bean.getObject();
	}
}
