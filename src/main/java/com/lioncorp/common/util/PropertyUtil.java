package com.lioncorp.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertyUtil {
	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);
	
	private static final String DEFAULT_PROPERTY = "config";
	private static final String PROPERTY_SUFIX = ".properties";
	private static final Map<String, PropertiesConfiguration> CONFIGURATIONS = new HashMap<String, PropertiesConfiguration>();
	
	public static PropertiesConfiguration getInstance(String name) {
		String fileName = getPropertyName(name);
		PropertiesConfiguration config = CONFIGURATIONS.get(fileName);
		if (config == null) {
			try {
				config = new PropertiesConfiguration(fileName);
				config.setReloadingStrategy(new FileChangedReloadingStrategy());
				CONFIGURATIONS.put(fileName, config);
			} catch (ConfigurationException e) {
				logger.error(e.getMessage(),e);
				e.printStackTrace();
			}
		}
		return config;
	}
	
	private static String getPropertyName(String name) {
		if (StringUtils.isBlank(name)) {
			name = DEFAULT_PROPERTY;
		}
		return name.endsWith(PROPERTY_SUFIX) ? name : (name += PROPERTY_SUFIX);
	}



	public static PropertiesConfiguration getInstance() {
		return getInstance(null);
	}
	
	public static String get(String key) {
		return getInstance(DEFAULT_PROPERTY).getString(key);
	}
	
	public static String get(String key, String propertyInstance) {
		return getInstance(propertyInstance).getString(key);
	}
	
	public static String[] getValues(String key, String propertyInstance){
		String value = getInstance(propertyInstance).getString(key);
		if( StringUtils.isBlank(value) )
			return null;
		
		return value.split(";");
	}
	
	public static String[] getList(String key, String propertyInstance) {
		return getInstance(propertyInstance).getStringArray(key);
	}
	public static String[] getList(String key) {
		return getInstance(null).getStringArray(key);
	}
    
    public static void main(String[] args) {
        System.out.println(PropertyUtil.get("target_env"));
        System.out.println(PropertyUtil.get("jdbc.password","config-db"));
    }
}
