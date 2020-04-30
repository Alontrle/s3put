package com.tobiassteely.s3put.api.config;

import com.tobiassteely.s3put.api.manager.ManagerParent;

public class ConfigManager extends ManagerParent {

    public Config getConfig(String configName) {
        if(getObjectWithKey(configName) == null)
            addConfig(new Config(configName));
        return (Config)getObjectWithKey(configName);
    }

    public void addConfig(Config config) {
        addObject(config);
    }

}
