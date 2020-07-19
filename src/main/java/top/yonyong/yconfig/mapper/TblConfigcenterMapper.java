package top.yonyong.yconfig.mapper;

import top.yonyong.yconfig.config.TblConfig;

import java.util.List;

public interface TblConfigcenterMapper {
    List<TblConfig> selectByExample(TblConfig TblConfig);
}