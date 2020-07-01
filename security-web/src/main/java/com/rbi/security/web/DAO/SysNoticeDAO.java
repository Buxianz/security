package com.rbi.security.web.DAO;

import com.rbi.security.entity.web.health.OccRegularMonitoring;
import com.rbi.security.entity.web.notice.SysNotice;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: SXD
 * @Description:
 * @Date: create in 2020/6/26 19:28
 */
@Mapper
public interface SysNoticeDAO {
    @Insert("insert into sys_notice (title,content,annex,idt,operating_staff)values" +
            "(#{title},#{content},#{annex},#{idt},#{operatingStaff})")
    void add(SysNotice sysNotice);

    @Select("select * from sys_notice order by id DESC limit #{pageNo},#{pageSize} ")
    List<SysNotice> findByPage(int pageNo, int pageSize);

    @Select("select count(*) from sys_notice")
    int findByPageNum();
}
