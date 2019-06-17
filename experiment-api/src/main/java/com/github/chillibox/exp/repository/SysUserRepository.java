package com.github.chillibox.exp.repository;

import com.github.chillibox.exp.entity.SysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>Created on 2017/7/15.</p>
 *
 * @author Gonster
 */

@Repository
public interface SysUserRepository extends CrudRepository<SysUser, Long> {

    @Query("from SysUser u where u.username = :name or u.email = :name")
    SysUser findByUsernameOrEmail(@Param("name") String name);
}
