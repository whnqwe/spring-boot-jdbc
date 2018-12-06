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


    

