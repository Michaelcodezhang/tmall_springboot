package com.how2java.tmall.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    private String name;
    private String password;
    private String salt;

    @Transient
    private String anonymousName;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getAnonymousName() {
        if(anonymousName!=null){
            return anonymousName;
        }
        if(name==null){
            anonymousName=null;
        }else if(name.length()<=1){
            anonymousName="*";
        }else if(name.length()==2){
            anonymousName=name.substring(0,1)+"*";
        }else {
            char[] cs=name.toCharArray();
            for(int i=1;i<cs.length-1;i++){
                cs[i]='*';
            }
            anonymousName=new String(cs);
        }
        return anonymousName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }
}