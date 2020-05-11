package com.mooctest.exception;

import com.mooctest.data.response.ServerCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created with IntelliJ IDEA.
 * User: qgan(qgan@v5.cn)
 * Date: 14-3-11
 * Time: 下午7:13
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServerException extends RuntimeException {
    private int errorCode;
    private String error;

    public ServerException(ServerCode serverCode){
        this.errorCode = serverCode.getCode();
        this.error = serverCode.getMsg();
    }
}
