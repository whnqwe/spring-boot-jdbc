package cn.zhangspace.controller;


import cn.zhangspace.domain.User;
import cn.zhangspace.repository.UserRepository;
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
