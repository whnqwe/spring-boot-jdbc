package cn.zhangspace.springbootjdbc.controller;


import cn.zhangspace.springbootjdbc.domain.User;
import cn.zhangspace.springbootjdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping("/web/mvc/user/save")
    public Boolean save(@RequestBody User user){
        return  userRepository.save(user);
    }
}
