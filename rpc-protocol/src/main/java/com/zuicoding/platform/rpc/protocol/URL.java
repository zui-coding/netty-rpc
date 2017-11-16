package com.zuicoding.platform.rpc.protocol;

import com.zuicoding.platform.rpc.common.Constant;
import com.zuicoding.platform.rpc.common.exception.RpcException;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by Stephen.lin on 2017/11/16
 * <p>
 * <p></p>
 */
public class URL {

    /**
     * 默认协议rpc
     */
    private String protocol = "rpc";

    private String host = "127.0.0.1";

    /**
     * 默认端口
     */
    private int port = 8099;
    /**
     * interfaceName
     */
    private String queryStr;

    private Map<String,String> params;


    public String getProtocol() {
        return protocol;
    }

    public URL setProtocol(String protocol) {
        checkValue("protocol",protocol);
        this.protocol = protocol;
        return this;
    }

    public String getHost() {
        return host;
    }

    public URL setHost(String host) {
        checkValue("host",host);
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public URL setPort(int port) {
        if(port <=0){
            throw new RpcException("IllegalArgument port value:" + port);
        }
        this.port = port;
        return this;
    }

    public String getQueryStr() {
        return queryStr;
    }

    public URL setQueryStr(String queryStr) {
        checkValue("queryStr",queryStr);
        this.queryStr = queryStr;
        return this;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public URL setParams(Map<String, String> params) {
        this.params = params;
        return this;
    }

    public URL addParam(String name,String value){
        if (this.params == null){
            this.params = new LinkedHashMap<>();
        }
        checkValue("param name",name);
        this.params.put(name,value);
        return this;
    }

    @Override
    public String toString() {

        String paramStr = toParamStr(this.params);
        String uriStr = getUri();
        if (paramStr == null) return uriStr;
        return uriStr + Constant.FIRST_PARAM_SEPARATOR + paramStr;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof URL)) return  false;
        URL url = (URL)obj;
        if(!Objects.equals(this.getProtocol(),url.getProtocol())){
            return false;
        }
        if (!Objects.equals(this.host,url.getHost())){
            return false;
        }
        if(!Objects.equals(this.port,url.getPort())){
            return false;
        }
        if(!Objects.equals(this.queryStr,url.getQueryStr())){
            return false;
        }
        return this.toString().equals(url.toString());
    }

    private String toParamStr(Map<String,String> params){
        if (!params.isEmpty()){
            StringBuilder paramStr = new StringBuilder();
            int index = 0,size = params.size();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                paramStr.append(entry.getKey())
                        .append(Constant.PARAM_VALUE_SEPARATOR)
                        .append(entry.getValue());
                if (index != size - 1){
                    paramStr.append(Constant.PARAM_SEPARATOR);
                }
                index ++;
            }
            return paramStr.toString();
        }
        return null;
    }

    public String getUri(){
        StringBuilder url = new StringBuilder();
        url.append(this.protocol)
                .append(Constant.PROTOCOL_SEPARATOR)
                .append(this.host)
                .append(":")
                .append(this.port)
                .append(Constant.QUERYSTR_SEPARATOR)
                .append(this.queryStr);
        return url.toString();
    }

    private void checkValue(String name, String value){
        if (value == null || value.trim().length() == 0){
            throw new RpcException(String.format("IllegalArgument %s value : %s", name,value));
        }
    }
}
