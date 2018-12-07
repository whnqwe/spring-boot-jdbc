# spring-boot-jdbc

> 从 Spring Boot 1.4 支持 FailureAnalysisReporter 实现（错误分析的接口）



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
# 数据源

#### 数据源相关类
javax.sql.DataSource

#### 数据源库连接池

DataSourceConfiguration

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

## webflux 与 webmvc的比较

#### WebFlux

>  Mono : 0 - 1 Publisher（类似于Java 8 中的 Optional）
  
>  Flux:     0 - N Publisher（类似于Java 中的 List）

 1. WebFlux 采用：ServerRequest、ServerResponse（不再限制于 Servlet 容器，可以选择自定义实现，比如 Netty Web Server
 
 2. 在 Spring Web Flux 使用 ServerRequest.bodyToMono()
 
 3. webflux请求是异步的
 
     > 演示代码见项目
     
     ```java
      System.out.printf("[webFlux Thread : %s ] start saving user....\n",Thread.currentThread().getName());

     ```
  
  
#### webmvc  

 1. 传统的 Servlet 采用 HttpServletRequest、HttpServletResponse
  
 2. 在 Spring Web MVC 中使用 @RequestBody

 3. webmvc 请求是同步的
 
    > 演示代码见项目
       ```java
       System.out.printf("[webMvc controller Thread : %s ] start saving user....\n",Thread.currentThread().getName());
       ```
          
   >  修改为异步代码见项目代码
    
       System.out.printf("[webMvc asyn controller Thread : %s ] start saving user....\n",Thread.currentThread().getName());

## JDBC 多数据源配置

> MultipleDataSourceConfiguration 

```java
class MultipleDataSourceConfiguration{
    ...
}
```


> UserController 
```java
@Autowired
public UserRepository(DataSource dataSource,
                      @Qualifier("masterDataSource") DataSource masterDataSource,
                      @Qualifier("salveDataSource") DataSource salveDataSource
                      ){
    this.dataSource = dataSource;
    this.masterDataSource = masterDataSource;
    this.salveDataSource = salveDataSource;
}
```

# 事务

> 事务用于提供数据完整性，并发访问下保证数据视图一致性

## 自动提交模式

```java
 connection.commit(); 
 connection.setAutoCommit(false);
```




      