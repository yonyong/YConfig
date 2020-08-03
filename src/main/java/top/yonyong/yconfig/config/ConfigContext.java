package top.yonyong.yconfig.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Describtion config container
 * @Author yonyong
 * @Date 2020/7/13 15:40
 * @Version 1.0.0
 **/
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class ConfigContext {

    /**
     * config key-val map
     */
    private List<Config> vals;

    /**
     * env type
     */
    private String group;

    /**
     * get config
     * @param key
     * @return
     */
    public String getValue(String key){
        final List<Config> collect = vals.stream()
                .filter(tblConfig -> tblConfig.getKeyName().equals(key))
                .collect(Collectors.toList());
        if (null == collect || collect.size() == 0)
            return null;
        return collect.get(0).getKeyValue();
    }

    public List<Config> getVals() {
        return vals;
    }

    protected void setVals(List<Config> vals) {
        this.vals = vals;
    }

    public String getGroup() {
        return group;
    }

    protected void setGroup(String group) {
        this.group = group;
    }
}
