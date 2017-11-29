package com.yongche.xmlbean.db;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

/**
* @author ash
*/
public class Database {

    @Attribute(required = true)
    private String name;

    @Element(required = true)
    private DataSourceInstance master;

    @ElementList(required = false, name = "slaves", entry = "slave", inline = true)
    private List<DataSourceInstance> slaves;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DataSourceInstance getMaster() {
        return master;
    }

    public void setMaster(DataSourceInstance master) {
        this.master = master;
    }

    public List<DataSourceInstance> getSlaves() {
        return slaves;
    }

    public void setSlaves(List<DataSourceInstance> slaves) {
        this.slaves = slaves;
    }

}
