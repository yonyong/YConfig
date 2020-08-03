package top.yonyong.yconfig.config;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import top.yonyong.yconfig.utils.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Describtion YConfig interface
 * @Author yonyong
 **/
@Component
public class DefaultYConfigHandlerFactory extends AbstractYConfigHandler {

    private List<Config> v;

    @Resource
    private ConfigContext applicationConfigContext;

    @Override
    public String add(String key, String val) {
        return this.doAdd(key, val);
    }

    @Override
    public int setVals(List<Config> vals) {
        return this.doSetVals(vals);
    }

    @Override
    public String del(String key) {
        return this.doDel(key);
    }

    @Override
    public String get(String key) {
        return this.doGet(key);
    }

    @Override
    public List<Config> get() {
        return this.doGet();
    }

    @Override
    public String setGroup(String val) {
        return this.doSetGroup(val);
    }

    @Override
    public String getGroup() {
        return this.doGetGroup();
    }

    private String doAdd(String key, String val){
        initStatus();
        if (StringUtils.isBlank(key))
            throw new RuntimeException("key can not be null while adding a value");
        if (StringUtils.isBlank(val))
            throw new RuntimeException("val can not be null while adding a value");
        if (0 < applicationConfigContext.getVals().stream().filter(
                tblConfig -> tblConfig.getKeyName().equals(key)).count())
            throw new RuntimeException("the key you want to add already exists in the config container");
        final List<Config> vals = applicationConfigContext.getVals();
        final Config build = Config.builder().keyName(key).keyValue(val).build();
        vals.add(build);
        applicationConfigContext.setVals(vals);
        return key;
    }

    private String doDel(String key) {
        initStatus();
        if (StringUtils.isBlank(key))
            throw new RuntimeException("key can not be null while deleting a value");
        if (0 == applicationConfigContext.getVals().stream().filter(
                tblConfig -> tblConfig.getKeyName().equals(key)).count())
            throw new RuntimeException("the key you want to delete is not contained");
        final List<Config> vals = applicationConfigContext.getVals().stream()
                .filter(config -> !config.getKeyName().equals(key)).collect(Collectors.toList());
        applicationConfigContext.setVals(vals);
        return key;
    }

    @Override
    public String set(String key, String val) {
        initStatus();
        if (StringUtils.isBlank(key))
            throw new RuntimeException("key can not be null while setting a value");
        if (StringUtils.isBlank(val))
            throw new RuntimeException("val can not be null while setting a value");
        if (0 == applicationConfigContext.getVals().stream().filter(
                tblConfig -> tblConfig.getKeyName().equals(key)).count())
            throw new RuntimeException("the key you want to update is not contained");

        applicationConfigContext.getVals().forEach(config -> {
            if (config.getKeyName().equals(key))
                config.setKeyValue(val);
        });
        return key;
    }

    private String doGet(String key) {
        initStatus();
        if (StringUtils.isBlank(key))
            throw new RuntimeException("key can not be null");

        if (0 == applicationConfigContext.getVals().stream().filter(
                tblConfig -> tblConfig.getKeyName().equals(key)).count())
            throw new RuntimeException("key is not contained");

        return applicationConfigContext.getValue(key);
    }

    private List<Config> doGet() {
        initStatus();
        return applicationConfigContext.getVals();
    }

    private String doSetGroup(String val) {
        if (StringUtils.isBlank(val))
            throw new RuntimeException("val can not be null");

        applicationConfigContext.setGroup(val);
        return val;
    }

    private int doSetVals(List<Config> vals) {
        if (ObjectUtils.isEmpty(vals))
            throw new RuntimeException("vals can not be null");

        if (null!= applicationConfigContext.getVals() && applicationConfigContext.getVals().size() > 0)
            throw new RuntimeException("config container can only be initialized once time");

        applicationConfigContext.setVals(vals);
        return vals.size();
    }

    private String doGetGroup() {
        initStatus();
        return applicationConfigContext.getGroup();
    }

    //check if init
    private boolean initStatus() {
        boolean rs = null != applicationConfigContext.getVals() && applicationConfigContext.getVals().size() != 0;
        if (!rs)
            throw new RuntimeException("you must need to init config container" +
                    ":you can implement the method 'int setVals(List<Config> vals)' " +
                    "in class AbstractYConfigHandler to init config container");
        return true;
    }

    @Override
    public void setV(List<Config> vals) {
        this.v = vals;
    }

    @Override
    public void commit() {
        this.setVals(v);
    }
}
