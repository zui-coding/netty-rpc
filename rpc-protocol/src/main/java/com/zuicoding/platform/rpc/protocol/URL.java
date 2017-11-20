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
    private String protocol = Constant.DEFAULT_PROTOCOL;

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

    public URL() {
    }

    public URL(String host, int port, String queryStr) {
        this.host = host;
        this.port = port;
        this.queryStr = queryStr;
    }

    public URL(String protocol, String host, int port, String queryStr) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.queryStr = queryStr;
    }

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
    public URL addParam(String name,Number value){
        if (this.params == null){
            this.params = new LinkedHashMap<>();
        }
        checkValue("param name",name);
        this.params.put(name,String.valueOf(value));
        return this;
    }

    public URL addParams(Map<String,String> params){
        if (params != null){
            for (Map.Entry<String, String> entry : params.entrySet()) {
                addParam(entry.getKey(),entry.getValue());
            }
        }
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
        if (params == null || params.isEmpty()){
            return  null;
        }
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

    public static URL parse(String url){
        if (url == null || url.trim().length() == 0){
            throw new NullPointerException("url is empty");
        }
        String[] values = url.split("\\"+Constant.FIRST_PARAM_SEPARATOR);
        if (values.length > 2){
            throw new RpcException("unknow url:"+url);
        }
        String uri = values[0];
        String paramStr = null;
        if (values.length == 2){
            paramStr = values[1];
        }
        int i = uri.indexOf(Constant.PROTOCOL_SEPARATOR);
        if (i < 0){
            throw new RpcException("url missing protocol "+ url);
        }
        String protocol = uri.substring(0,i);
        //"://"
        uri = uri.substring(i + 3);
        i = uri.indexOf(Constant.QUERYSTR_SEPARATOR);
        if(i < 0){
            throw new RpcException("url missing host or query string " + url);
        }
        values = uri.split(Constant.QUERYSTR_SEPARATOR);
        if (values.length != 2){
            throw new RpcException("url missing host or query string" + url);
        }
        if (values[0].trim().length() == 0){
            throw new RpcException("url missing host and port" + url);
        }
        if (values[1].trim().length() == 0){
            throw new RpcException("url missing query string" + url);
        }
        String queryStr = values[1];

        i = values[0].indexOf(":");
        if (i < 0 ){
            throw new RpcException("url missing host or port " + url);
        }
        values = values[0].split(":");
        if (values.length != 2){
            throw new RpcException("url IllegalArgument host or port " + url);
        }
        if (values[0].trim().length() == 0){
            throw new RpcException("url missing host " + url);
        }
        if (values[1].trim().length() == 0){
            throw new RpcException("url missing port " + url);
        }
        String host = values[0].trim();
        URL _url = null;
        try {
            int port = Integer.valueOf(values[1].trim());
            _url = new URL(host,port,queryStr);
        }catch (Exception e){
            throw new RpcException("IllegalArgument port "+values[1],e);
        }

        if (_url == null){
            throw new RpcException("IllegalArgument url "+url);
        }

        if (paramStr != null){
            values = paramStr.split("\\"+Constant.PARAM_SEPARATOR);
            for (String value : values) {
                String[] params = value.split(Constant.PARAM_VALUE_SEPARATOR);
                _url.addParam(params[0],params[1]);
            }
        }

        return _url;


    }

    public String getParamVal(String name){
        if(this.params == null) return null;
        return this.params.get(name);
    }
    public Integer getIntParamVal(String name){
        if(this.params == null) return null;
        return Integer.valueOf(this.params.get(name));
    }

    public Long getLongParamVal(String name){
        if(this.params == null) return null;
        return Long.valueOf(this.params.get(name));
    }

    private void checkValue(String name, String value){
        if (value == null || value.trim().length() == 0){
            throw new RpcException(String.format("IllegalArgument %s value : %s", name,value));
        }
    }
}
