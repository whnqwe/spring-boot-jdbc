package cn.zhangspace.springbootjdbc.controller;


import cn.zhangspace.springbootjdbc.domain.User;
import cn.zhangspace.springbootjdbc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    @RequestMapping("/web/mvc/user/save")
    public Boolean save(@RequestBody User user){

        System.out.printf("[webMvc controller Thread : %s ] start saving user....\n",Thread.currentThread().getName());

        return  userRepository.save(user);
    }


    @RequestMapping("/web/mvc/user/save/asyn")
    public Boolean saveAsyn(@RequestBody User user) throws ExecutionException, InterruptedException {

        System.out.printf("[webMvc asyn controller Thread : %s ] start saving user....\n",Thread.currentThread().getName());

        Future<Boolean> future = executorService.submit(()->{
            return userRepository.save(user);
        });
        return future.get() ;
    }
}
