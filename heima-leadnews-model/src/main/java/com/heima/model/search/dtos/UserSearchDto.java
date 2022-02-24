package com.heima.model.search.dtos;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.heima.model.common.annotation.IdEncrypt;
import lombok.Data;
import java.util.Date;
/**
 * @author king
 */
@Data
public class UserSearchDto {

    /**
     * 设备ID
     */
    @IdEncrypt
    Integer equipmentId;
    /**
    * 搜索关键字
    */
    @JsonAlias("search_words")
    String searchWords;
    /**
    * 当前页
    */
    @JsonAlias("page_num")
    int pageNum;
    /**
    * 分页条数
    */
    @JsonAlias("page_size")
    int pageSize;
    /**
    * 最小时间
    */
    Date minBehotTime;

    /**
     *
     */
    Integer id;
    public int getFromIndex(){
        if(this.pageNum<1)return 0;
        if(this.pageSize<1) this.pageSize = 10;
        return this.pageSize * (pageNum-1);
    }
}