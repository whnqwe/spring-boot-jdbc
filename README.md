# spring-boot-jdbc

> 从 Spring Boot 1.4 支持 FailureAnalysisReporter 实现（错误分析的接口）

> WebFlux

> Mono : 0 - 1 Publisher（类似于Java 8 中的 Optional）
  
>  Flux:     0 - N Publisher（类似于Java 中的 List）
  
>  传统的 Servlet 采用 HttpServletRequest、HttpServletResponse
  
>  WebFlux 采用：ServerRequest、ServerResponse（不再限制于 Servlet 容器，可以选择自定义实现，比如 Netty Web Server

## dependency
```properties

	<dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
	</dependency>


    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
```
## 数据源

#### 数据源相关类
javax.sql.DataSource

#### 数据源库连接池

##### [commons-dbcp](http://commons.apache.org/proper/commons-dbcp/)
   commons-dbcp2 
   
        依赖：commons-pool2
   
   
   commons-dbcp（老版本）
   
        依赖：commons-pool 

##### tomcat dbcp
http://tomcat.apache.org/tomcat-8.5-doc/jndi-datasource-examples-howto.html

## 配置

```properties
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/ddd
spring.datasource.username=root
spring.datasource.password=root
```

    

