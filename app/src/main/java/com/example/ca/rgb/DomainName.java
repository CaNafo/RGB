package com.example.ca.rgb;

public class DomainName {
    static DomainName domainName = null;
    static String domain = "http://192.168.0.104/";
    private DomainName(){

    }
    public static String getIstance(){
        if(domainName == null){
            domainName = new DomainName();
        }
        return domain;
    };

}
