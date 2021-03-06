package cn.zhangspace.springbootjdbc.webflux;


import cn.zhangspace.springbootjdbc.domain.User;
import cn.zhangspace.springbootjdbc.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class UserHandler {

    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<ServerResponse> save(ServerRequest serverRequest){
        // 在 Spring Web MVC 中使用 @RequestBody
        // 在 Spring Web Flux 使用 ServerRequest.bodyToMono()
        // Mono<User> 类似于 Optional<User>
        System.out.printf("[webFlux Thread : %s ] start saving user....\n",Thread.currentThread().getName());
        Mono<User> userMono = serverRequest.bodyToMono(User.class);
        //相当于转换工作
        Mono<Boolean> booleanMono = userMono.map(userRepository::save);
        return ServerResponse.ok().body(booleanMono,Boolean.class);


    }


}
