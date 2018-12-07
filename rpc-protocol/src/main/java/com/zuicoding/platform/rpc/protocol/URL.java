package com.zuicoding.platform.rpc.protocol;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.zuicoding.platform.rpc.common.exception.Constant;
import com.zuicoding.platform.rpc.common.exception.RpcException;
import com.zuicoding.platform.rpc.common.utils.CollectionUtils;
import com.zuicoding.platform.rpc.common.utils.StringTools;

/**
 * Created by Stephen.lin on 2017/11/16
 * <p>
 * <p></p>
 */
public class URL {

    private String protocol = Constant.PROTOCOL;
    private String host;
    private int port;
    private String path;
    private Map<String,String> parameters = new LinkedHashMap<>();

    public URL() {
    }

    public URL(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public URL(String host, int port, String path) {
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public URL(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public URL(String protocol, String host, int port, String path) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
    }

    public URL(String host, int port, String path, Map<String, String> parameters) {
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    public URL(String protocol, String host, int port, String path,
               Map<String, String> parameters) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
        this.path = path;
        this.parameters = parameters;
    }

    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder("");
        builder.append(this.protocol)
                .append(Constant.COLON_SYMBOL)
                .append(Constant.DOUBLE_SLASH_SYMBOL)
                .append(this.host)
                .append(Constant.COLON_SYMBOL)
                .append(this.port)
                .append(Constant.SLASH_SYMBOL)
                .append(StringTools.getValue(this.path));
                String paramStr = builParameterStr(this.parameters);
                if (StringTools.isNotBank(paramStr)) {
                    builder.append(Constant.QUESTION_SYMBOL).append(paramStr);
                }

        return builder.toString();
    }

    public static URL valueOf(String urlStr){
        if (StringTools.isBank(urlStr)) {
            throw new RpcException("url is empty");
        }
        String url = urlStr;
        int index =  url.indexOf(Constant.COLON_SYMBOL);
        if (index < 0) {
            throw new RpcException("protocol is missing," + urlStr );
        }
        String protocol = url.substring(0,index);
        index = url.indexOf(Constant.DOUBLE_SLASH_SYMBOL);
        if (index < 0 ) {
            throw new RpcException("the url format is error :" + urlStr);
        }
        url = url.substring(index + 2);
        index = url.indexOf(Constant.SLASH_SYMBOL);
        if (index < 0 ) {
            throw new RpcException("the url host or port is missing,"+ urlStr);
        }
        String address = url.substring(0,index);
        index = address.indexOf(Constant.COLON_SYMBOL);
        if (index < 0 ) {
            throw new RpcException("the url host and port formt is error," + urlStr);
        }
        String[] hostAndPort = address.split(":");
        String host = hostAndPort[0];
        if (StringTools.isBank(host)) {
            throw new RpcException("the url host is empty," + urlStr);
        }
        int port  = 0;
        try {
            port = Integer.valueOf(hostAndPort[1]);
        }catch ( Exception e) {
            throw new RpcException("the url port is not number," + urlStr);
        }

        String path = null;
        Map<String,String> params = null;
        index = url.indexOf(Constant.SLASH_SYMBOL);

        int _i = url.indexOf(Constant.QUESTION_SYMBOL);
        if (_i < 0 ) {
           path = url.substring(index + 1);
        }else {
            path = url.substring(index + 1,_i);
            params = new LinkedHashMap<>();
            fixParameters(url.substring(_i + 1),params);
        }

        return new URL(protocol,host,port,path,params);
    }

    private static void fixParameters(String paramStr,
                                      Map<String,String> parameters) {
        if (StringTools.isBank(paramStr)) {
            return;
        }
        String[] params =  paramStr.split("&");
        if (CollectionUtils.isEmpty(params)) {
            return;
        }

        for (String param : params) {
            String[] ps = param.split("=");
            if (ps.length < 2 ) {
                parameters.put(ps[0],StringTools.EMPTY);
                continue;
            }
            parameters.put(ps[0],ps[1]);
        }
    }


    public URL addParameter(String name,String value) {
        if (StringTools.isBank(name)) {
            throw new RpcException("parameter name is empty");
        }
        if (parameters == null) {
            parameters = new HashMap<>();
        }
        parameters.put(name,StringTools.getValue(value));
        return this;
    }


    private String builParameterStr(Map<String,String> parameters){
        if (CollectionUtils.isEmpty(parameters)) {
            return StringTools.EMPTY;
        }
        StringBuilder builder = new StringBuilder();
        Iterator<Map.Entry<String,String>> iterator = parameters.entrySet().iterator();
        for (;iterator.hasNext();) {
            Map.Entry<String,String> entry = iterator.next();
            builder.append(entry.getKey())
                    .append(Constant.EQUAL_SYMBOL)
                    .append(entry.getValue());
            if (iterator.hasNext()) {
                builder.append(Constant.AND_SYMBOL);
            }
        }
        return builder.toString();
    }
    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    private void checkValue(String name, String value){
        if (value == null || value.trim().length() == 0){
            throw new RpcException(String.format("IllegalArgument %s value : %s", name,value));
        }
    }

    public String getStringParameter(String name) {
        if (this.parameters == null) {
            return null;
        }

        return this.parameters.get(name);
    }

    public String getStringParameter(String name, String defVal) {
        if (this.parameters == null) {
            return defVal;
        }

        return this.parameters.getOrDefault(name, defVal);

    }

    public Integer getIntParameter(String name) {
        String v = getStringParameter(name);
        return v == null ? null : Integer.valueOf(v);
    }

    public Integer getIntParameter(String name, int defVal) {
        String v = getStringParameter(name);
        return v == null ? defVal : Integer.valueOf(v);
    }

    public Long getLongParameter(String name) {
        String v = getStringParameter(name);

        return v == null ? null : Long.valueOf(v);
    }

    public Long getLongParameter(String name, long defVal) {
        String v = getStringParameter(name);

        return v == null ? defVal : Long.valueOf(v);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        URL url = (URL) o;

        if (port != url.port) {
            return false;
        }
        if (!protocol.equals(url.protocol)) {
            return false;
        }
        if (!host.equals(url.host)) {
            return false;
        }
        if (!path.equals(url.path)) {
            return false;
        }
        return parameters.equals(url.parameters);
    }

    @Override
    public int hashCode() {
        int result = protocol.hashCode();
        result = 31 * result + host.hashCode();
        result = 31 * result + port;
        result = 31 * result + path.hashCode();
        result = 31 * result + parameters.hashCode();
        return result;
    }
}
