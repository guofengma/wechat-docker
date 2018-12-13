package com.mihoyo.hk4e.wechat.repository;

import com.mihoyo.hk4e.wechat.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Transactional
public interface TokenRepository extends JpaRepository<Token, Long>{

    @Modifying(clearAutomatically = true)
    @Query("update Token set content = ?1, expireDate = ?2 where id = ?3")
    int updateToken(String content, Date expireDate, Long id);
}
