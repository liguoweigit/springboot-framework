package com.yongche.xmlbean.db;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
* @author ash
*/
@Root(name = "database")
public class DatabaseConfig {

    @ElementList(entry = "database", inline = true)
    private List<Database> databases;

    public List<Database> getDatabases() {
        return databases;
    }

    public void setDatabases(List<Database> databases) {
        this.databases = databases;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.PrettyFormat,SerializerFeature.DisableCircularReferenceDetect);

    }
}
