package com.mooctest.data.response;

import com.mooctest.exception.ServerException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVO<T> {
    private int code;
    private String msg;
    private T data;

    public ResponseVO(ServerCode serverCode){
        this.code = serverCode.getCode();
        this.msg = serverCode.getMsg();
    }

    public ResponseVO(ServerCode serverCode,T data){
        this.code = serverCode.getCode();
        this.msg = serverCode.getMsg();
        this.data = data;
    }

    public ResponseVO(ServerException e) {
        this.code = e.getErrorCode();
        this.msg = e.getError();
    }

    public ResponseVO(ServerException e, T data) {
        this.code = e.getErrorCode();
        this.msg = e.getError();
        this.data = data;
    }


}
