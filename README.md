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

> 事务将自动提交取消，进行一系列操作之后，在commit()

## 自动提交模式

```java
 connection.commit(); 
 connection.setAutoCommit(false);
```

## 事务（Transaction）

#### @Transaction
>  @Transactional 代理执行 <b>TransactionInterceptor</b>
    
> TransactionInterceptor#invoke-> TransactionAspectSupport#invokeWithinTransaction

```java
   protected Object invokeWithinTransaction{
   ...
      		final TransactionAttribute txAttr = (tas != null ? tas.getTransactionAttribute(method, targetClass) : null);
   ...
   }

   TransactionAttribute{
      isolationLevel; //隔离级别
      propagationBehavior;  //事务传播
   }
   ```
   
> DataSourceTransactionManager#doCommit

```java
 protected void doCommit(DefaultTransactionStatus status) {
    ...
    Connection con = txObject.getConnectionHolder().getConnection();
    ...
    con.commit();
    ...
    
 }
```
#### 事务的隔离级别

> Connection

```java
Connection{
    int TRANSACTION_READ_UNCOMMITTED = 1;
    int TRANSACTION_READ_COMMITTED   = 2;
    int TRANSACTION_REPEATABLE_READ  = 4;
    int TRANSACTION_SERIALIZABLE     = 8;
}
```

从上至下，级别越高，性能越差

Spring Transaction 实现重用了 JDBC API：

Isolation -> TransactionDefinition

> Isolation

```java
public enum Isolation {
    DEFAULT(TransactionDefinition.ISOLATION_DEFAULT),
    READ_UNCOMMITTED(TransactionDefinition.ISOLATION_READ_UNCOMMITTED),
    READ_COMMITTED(TransactionDefinition.ISOLATION_READ_COMMITTED),
    REPEATABLE_READ(TransactionDefinition.ISOLATION_REPEATABLE_READ),
    SERIALIZABLE(TransactionDefinition.ISOLATION_SERIALIZABLE);
}
```
> TransactionDefinition
```java
TransactionDefinition{
    int ISOLATION_READ_UNCOMMITTED = Connection.TRANSACTION_READ_UNCOMMITTED;
	int ISOLATION_READ_COMMITTED = Connection.TRANSACTION_READ_COMMITTED;
	int ISOLATION_REPEATABLE_READ = Connection.TRANSACTION_REPEATABLE_READ;
	int ISOLATION_SERIALIZABLE = Connection.TRANSACTION_SERIALIZABLE;
}
```

### 通过 API 方式进行事务处理 - PlatformTransactionManager

> 详细见代码

### 如何选择两种不同的方式

> @Transactional 涉及到了事务需不需要嵌套的问题

#### 事务的传播
> Propagation.java

```java
//需要事务传播
 REQUIRED(TransactionDefinition.PROPAGATION_REQUIRED), 
 
 //支持事务传播
 SUPPORTS(TransactionDefinition.PROPAGATION_SUPPORTS),
 
 //强制事务传播
MANDATORY(TransactionDefinition.PROPAGATION_MANDATORY),
 	
 //创建新的事务传播
REQUIRES_NEW(TransactionDefinition.PROPAGATION_REQUIRES_NEW),

//嵌套事务(涉及到了还原点的概念)
NESTED(TransactionDefinition.PROPAGATION_NESTED);
...

```

#### 保护点
> 事务是嵌套的，允许某个事务失败，当其中一个事务失败时，另一个正常提交。

> 建立保护点 -->  rollback保护点 ---> 释放还原点
```java
method(){
    Savepoint savepoint = connection.setSavepoint("t1");//Transactional-1            
    try{
        transactionalSave(); //Transactional-2
    }catch (){
        connection.rollback(savepoint);
    }
   connection.commit();
   connection.releaseSavepoint("t1");
   
}   
```


假设一个service方法给了@Transaction标签，在这个方法中还有其他service 的某个方法，这个方法没有加@Transaction，那么如果内部方法报错，会回滚吗？

答：会的，当然可以过滤掉一些不关紧要的异常noRollbackFor()

# 总结

1. JDBC单数据源配置

2. JDBC多数据源配置

3. webmvc webflux的区别

4. 事务（与自动提交有关）
    1. 注解模式  API模式
    
    2. 两个选择（选择与事务的传播有关）
    
    3. 隔离级别
    
    4. 事务的传播
    
    5. 保护点 （与嵌套事务有关）
    
5. 自动提交


