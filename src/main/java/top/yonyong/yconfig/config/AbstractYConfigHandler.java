package top.yonyong.yconfig.config;

import top.yonyong.yconfig.api.YConfigHandleAction;
import top.yonyong.yconfig.config.DefaultYConfigHandlerFactory;
import top.yonyong.yconfig.config.Config;

import java.util.List;

/**
 * @author  yonyong
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
