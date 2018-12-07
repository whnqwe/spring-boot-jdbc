package cn.zhangspace.webflux;


import cn.zhangspace.domain.User;
import cn.zhangspace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebFluxConfiguration {


    @Bean
    public RouterFunction<ServerResponse> saveUser(UserHandler userHandler){
        return route(POST("/web/flux/user/save"),userHandler::save);
    }


}
