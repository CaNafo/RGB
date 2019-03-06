package com.example.ca.rgb;

public class DomainName {
    static DomainName domainName = null;
    static String domain = "http://uvod.dx.am/";
    private DomainName(){

    }
    public static String getIstance(){
        if(domainName == null){
            domainName = new DomainName();
        }
        return domain;
    };

}
