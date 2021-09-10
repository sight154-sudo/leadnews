package com.heima.model.wemedia.enumstatus;

/**
 * @author: tang
 * @date: Create in 11:21 2021/9/9
 * @description:
 */
public enum WmNewsStatusEnum {
    NORMAL((short)0),SUBMIT((short)1),FAIL((short)2),ADMIN_AUTH((short)3),ADMIN_SUCCESS((short)4),SUCCESS((short)8),PUBLISHED((short)9);
    short code;
    WmNewsStatusEnum(short code){
        this.code = code;
    }
    public short getCode(){
        return this.code;
    }
}
