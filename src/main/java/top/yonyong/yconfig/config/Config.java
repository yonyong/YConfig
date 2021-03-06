package top.yonyong.yconfig.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author  yonyong
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Config {
    private Integer id;

    /**
     * 配置名称
     */
    private String keyName;

    /**
     * 默认配置值
     */
    private String keyValue;

    /**
     * 分类
     */
    private String keyGroup;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建时间
     */
    private Date insertTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String creator;

    private Integer start;

    private Integer rows;

    /**
     * 是否是系统自带
     */
    private String type;

    /**
     * 修改人
     */
    private String modifier;
}
