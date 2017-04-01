package com.fox.rpc.remoting.enums;

/**
 * Created by shenwenbo on 2017/4/1.
 */
public enum ReturnEnum {
    SERVICE,/**正常服务返回**/
    TIMEOUT_EXCEPTION,/**超时返回**/
    AUTHORITY_EXCEPTION,/**权限异常返回**/
    SERVER_EXCEPTION
}
