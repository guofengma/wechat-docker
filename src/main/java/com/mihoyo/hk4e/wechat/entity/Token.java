package com.mihoyo.hk4e.wechat.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Token {
    /**
     * 因为Token只有一个 所有操作都针对这个ID
     */
    public static final Long ID = 1L;

    @Id
    private Long id;
    @Column
    private String content;
    @Column
    private Date expireDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public boolean valid(){
        return this.expireDate != null && this.expireDate.after(new Date());
    }

    public static Token createOne(String content, Date expireDate){
        Token token = new Token();
        token.setId(ID);
        token.setContent(content);
        token.setExpireDate(expireDate);
        return token;
    }
}
