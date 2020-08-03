package top.yonyong.yconfig.api;

import top.yonyong.yconfig.config.Config;

import java.util.List;

public interface YConfigHandleAction {

    int setVals(List<Config> vals);

    String add(String key,String val);

    String del(String key);

    String set(String key,String val);

    String get(String key);

    List<Config> get();

    String setGroup(String val);

    String getGroup();
}
