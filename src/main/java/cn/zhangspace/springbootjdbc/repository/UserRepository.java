package cn.zhangspace.springbootjdbc.repository;


import cn.zhangspace.springbootjdbc.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;


@Repository
public class UserRepository {


    private DataSource dataSource;

    private final DataSource masterDataSource;

    private final DataSource salveDataSource;


    @Autowired
    public UserRepository(DataSource dataSource,
                          @Qualifier("masterDataSource") DataSource masterDataSource,
                          @Qualifier("salveDataSource") DataSource salveDataSource
                          ){
        this.dataSource = dataSource;
        this.masterDataSource = masterDataSource;
        this.salveDataSource = salveDataSource;
    }


    public boolean save(User user){
        System.out.printf("[Thread : %s ] starting saving user....\n",Thread.currentThread().getName());
        return  true;
    }

    public Collection<User> findAll(){
        return Collections.emptyList();
    }

}
