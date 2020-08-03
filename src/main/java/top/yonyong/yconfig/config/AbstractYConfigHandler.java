package top.yonyong.yconfig.config;

import top.yonyong.yconfig.api.YConfigHandleAction;

import java.util.List;

/**
 * @Author yonyong
 * @Date 2020/8/3 15:22
 * @Version 1.0.0
 **/
public abstract class AbstractYConfigHandler implements YConfigHandleAction {
    private List<Config> v;

    public List<Config> getV() {
        return v;
    }

    public void setV(List<Config> vals) {
        this.v = vals;
    }

    public void commit(){
        this.setVals(v);
    }
}
