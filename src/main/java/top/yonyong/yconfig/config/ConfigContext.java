package top.yonyong.yconfig.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author  yonyong
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
