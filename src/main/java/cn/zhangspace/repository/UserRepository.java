package cn.zhangspace.repository;


import cn.zhangspace.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;


@Repository
public class UserRepository {

    private DataSource dataSource;

    @Autowired
    private UserRepository(DataSource dataSource){
        this.dataSource = dataSource;
     }


    public boolean save(User user){

        return  true;
    }

    public Collection<User> findAll(){
        return Collections.emptyList();
    }

}
