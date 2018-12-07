package cn.zhangspace.springbootjdbc.webflux;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebFluxConfiguration {


    @Bean
    public RouterFunction<ServerResponse> saveUser(UserHandler userHandler){
        System.out.printf("[webFlux Thread : %s ] start saving user....\n",Thread.currentThread().getName());

        return route(POST("/web/flux/user/save"),userHandler::save);
    }


}
