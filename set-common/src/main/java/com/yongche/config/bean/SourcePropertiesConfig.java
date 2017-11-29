package com.yongche.config.bean;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

import java.util.Map;

/**
 * Created by xieweibo on 2016/10/24.
 */
@Root(name = "sourcePropertie")
public class SourcePropertiesConfig{

    @ElementMap(entry = "property", key = "key", attribute = true, inline = true)
    private Map<String, String> properties;

    public String getProperty(String key) {
        return properties.get(key);
    }

    public boolean hasProperty(String key) {
        return properties.containsKey(key);
    }

    public String getProperty(String key, String defaultVal) {
        return properties.getOrDefault(key,defaultVal);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public String toString(){
        return properties.toString();
    }
}
