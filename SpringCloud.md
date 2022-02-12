# 1.SpringCloud简介

> **微服务架构是一种架构模式，它提倡将单一应用程序划分成一组小的服务，服务之间互相协调、互相配合，为用户提供最终价值。每个假务运行在其独立的进程中，服务与服务间采用轻量级的通信机制互相协作(通常是基于HTTP协议的RESTful API)。每个服务都围绕着具**
> **本业务进行构建，并且能够被独立的部署到生产环境、类生产环境等。另外，应当尽量避免统一的、集中式的服务管理机制，对具体的**
> **一个服务而言，应根据业务上下文，选择合适的语言、工具对其进行构建**

1. **分布式微服务架构的一站式解决方案，是多种微服务架构落地技术的集合体，俗称微服务全家桶**

# 2.SpringCloud和SpringBoot的版本选型

![image-20220122151331831](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220122151331831.png)

# 3.关于SpringCloud组件移除和替换

![image-20220122152719437](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220122152719437.png)

# 4.SpringCloud的搭建

## 4.1Maven的dependencyManagement

1. **Maven使用`denpendencyManagement`元素来提供了一种管理依赖版本号的方式**
2. **通常会在一个组织或者项目的最顶层的父`POM`文件中可以看到`dependencyManagement`元素**
3. **使用`POM`中`dependencyManagement`元素能让所有在子项目中引用一个依赖而不显示的列出版本号，Maven会沿着父子层次向上走，直到找到一个拥有`dependencyManagement`元素的项目，然后它就会使用这个`dependencyManagement`元素中指定的版本号。**
4. **这样做的好处就是：如果有多个子项目都引用同样的依赖，则可以避免在每个使用的子项目里都声明一个版本号，这样当想升级或切换到另一个版本时，自需要在顶层的父容器中进行更新，而不需要一个一个子项目的修改；另外如果某个子项目需要另外的一个版本，自需要声明`Versin`即可**
5. **`dependencyManagement`里只是声明依赖，并不是实现引入，因此子项目需要用的依赖。**

```xml
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
        </dependencies>
    </dependencyManagement>
```

## 4.2Pay模块（提供者）

### 4.2.1父类Maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.spring.cloud</groupId>
    <artifactId>SpringCloud</artifactId>
    <version>1.0-SNAPSHOT</version>
    <modules>
        <module>springcluod-payMricservice</module>
    </modules>
    <packaging>pom</packaging>

    <properties>
        <mysql.version>5.1.25</mysql.version>
        <druid-starter.version>1.1.17</druid-starter.version>
        <springboot.version>2.6.1</springboot.version>
        <springcloud-alibabba.version>2.2.7.RELEASE</springcloud-alibabba.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
        <spring.cloud-version>Hoxton.SR8</spring.cloud-version>
        <mybatis-starter.verion>2.2.1</mybatis-starter.verion>
        <lombok.version>1.18.22</lombok.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>${lombok.version}</version>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>${mybatis-starter.verion}</version>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>${mysql.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring.cloud-version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid-spring-boot-starter</artifactId>
                <version>${druid-starter.version}</version>
            </dependency>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>${springboot.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>${springcloud-alibabba.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>
```

### 4.2.2子类Maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloud</artifactId>
        <groupId>org.spring.cloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>springcluod-payMricservice</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-logging</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
    </dependencies>

</project>
```

### 4.2.3配置文件

```yml
server:
  port: 8080

spring:
  application:
    name: pay-mricserivce
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/web?useUnicode=true&useSSL=true
    username: root
    password: root
    druid:
      filters: stat,wall
      stat-view-servlet:
        login-username: admin
        login-password: admin
        reset-enable: false
        url-pattern: "/druid/*"
        enabled: true
      filter:
        wall:
          enabled: true
          config:
            drop-table-allow: true
        stat:
          slow-sql-millis: 10000
          log-slow-sql: true
          enabled: true
      web-stat-filter:
        exclusions: "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
        enabled: true
        profile-enable: "true"
        url-pattern: "/*"
      aop-patterns: com.springcluod.*




mybatis:
  mapper-locations: classpath:mapper/*

management:
  endpoints:
    enabled-by-default: true

logging:
  file:
    name: F:\\JavaCode\\SpringCloud\\springcluod-payMricservice\\log\\pay.log
  pattern:
    console: "%d{yyyy/MM/dd-HH:mm:ss} [%thread] %-5level %logger- %msg%n"
  level:
    com.springcluod.pay: info
    org.springframework.jdbc.core.JdbcTemplate: info
    con.springcluod.mapper: info

```

### 4.2.4Mapper文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.springcluod.pay.mapper.PayMapper">
    <delete id="delete">
        delete from `pay` where `id` = #{id}
    </delete>
</mapper>
```

### 4.2.5目录结构

![image-20220123162956809](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220123162956809.png)

## 4.3热部署(devtools)

### 4.3.1依赖

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <addResources>true</addResources>
                </configuration>
            </plugin>
        </plugins>
    </build>


        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
```

### 4.3.2开启自动编译

![image-20220123163512589](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220123163512589.png)

![image-20220123163619662](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220123163619662.png)

### 4.3.3IDea添加Run Dashboard

**在workspace.xml添加**

```xml
  <component name="RunDashboard">
    <option name="configurationTypes">
      <set>
        <option value="SpringBootApplicationConfigurationType" />
      </set>
    </option>
    <option name="ruleStates">
      <list>
        <RuleState>
          <option name="name" value="ConfigurationTypeDashboardGroupingRule" />
        </RuleState>
        <RuleState>
          <option name="name" value="StatusDashboardGroupingRule" />
        </RuleState>
      </list>
    </option>
  </component>
```

## 4.4Order(消费者)

### 4.4.1配置类

```java
package com.springcluod.order.config.restConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

### 4.4.2RestTemplate

```java
// GET

@Override
@Nullable
public <T> T getForObject(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
   RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
   HttpMessageConverterExtractor<T> responseExtractor =
         new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
   return execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
}

@Override
@Nullable
public <T> T getForObject(String url, Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {
   RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
   HttpMessageConverterExtractor<T> responseExtractor =
         new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
   return execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables);
}

@Override
@Nullable
public <T> T getForObject(URI url, Class<T> responseType) throws RestClientException {
   RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
   HttpMessageConverterExtractor<T> responseExtractor =
         new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
   return execute(url, HttpMethod.GET, requestCallback, responseExtractor);
}

@Override
public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Object... uriVariables)
      throws RestClientException {

   RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
   ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
   return nonNull(execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables));
}

@Override
public <T> ResponseEntity<T> getForEntity(String url, Class<T> responseType, Map<String, ?> uriVariables)
      throws RestClientException {

   RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
   ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
   return nonNull(execute(url, HttpMethod.GET, requestCallback, responseExtractor, uriVariables));
}

@Override
public <T> ResponseEntity<T> getForEntity(URI url, Class<T> responseType) throws RestClientException {
   RequestCallback requestCallback = acceptHeaderRequestCallback(responseType);
   ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
   return nonNull(execute(url, HttpMethod.GET, requestCallback, responseExtractor));
}

	// POST

	@Override
	@Nullable
	public URI postForLocation(String url, @Nullable Object request, Object... uriVariables)
			throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request);
		HttpHeaders headers = execute(url, HttpMethod.POST, requestCallback, headersExtractor(), uriVariables);
		return (headers != null ? headers.getLocation() : null);
	}

	@Override
	@Nullable
	public URI postForLocation(String url, @Nullable Object request, Map<String, ?> uriVariables)
			throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request);
		HttpHeaders headers = execute(url, HttpMethod.POST, requestCallback, headersExtractor(), uriVariables);
		return (headers != null ? headers.getLocation() : null);
	}

	@Override
	@Nullable
	public URI postForLocation(URI url, @Nullable Object request) throws RestClientException {
		RequestCallback requestCallback = httpEntityCallback(request);
		HttpHeaders headers = execute(url, HttpMethod.POST, requestCallback, headersExtractor());
		return (headers != null ? headers.getLocation() : null);
	}

	@Override
	@Nullable
	public <T> T postForObject(String url, @Nullable Object request, Class<T> responseType,
			Object... uriVariables) throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request, responseType);
		HttpMessageConverterExtractor<T> responseExtractor =
				new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
		return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
	}

	@Override
	@Nullable
	public <T> T postForObject(String url, @Nullable Object request, Class<T> responseType,
			Map<String, ?> uriVariables) throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request, responseType);
		HttpMessageConverterExtractor<T> responseExtractor =
				new HttpMessageConverterExtractor<>(responseType, getMessageConverters(), logger);
		return execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables);
	}

	@Override
	@Nullable
	public <T> T postForObject(URI url, @Nullable Object request, Class<T> responseType)
			throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request, responseType);
		HttpMessageConverterExtractor<T> responseExtractor =
				new HttpMessageConverterExtractor<>(responseType, getMessageConverters());
		return execute(url, HttpMethod.POST, requestCallback, responseExtractor);
	}

	@Override
	public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request,
			Class<T> responseType, Object... uriVariables) throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request, responseType);
		ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
		return nonNull(execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables));
	}

	@Override
	public <T> ResponseEntity<T> postForEntity(String url, @Nullable Object request,
			Class<T> responseType, Map<String, ?> uriVariables) throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request, responseType);
		ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
		return nonNull(execute(url, HttpMethod.POST, requestCallback, responseExtractor, uriVariables));
	}

	@Override
	public <T> ResponseEntity<T> postForEntity(URI url, @Nullable Object request, Class<T> responseType)
			throws RestClientException {

		RequestCallback requestCallback = httpEntityCallback(request, responseType);
		ResponseExtractor<ResponseEntity<T>> responseExtractor = responseEntityExtractor(responseType);
		return nonNull(execute(url, HttpMethod.POST, requestCallback, responseExtractor));
	}
```

### 4.4.3控制层

```java
package com.springcluod.order.controller;

import com.springcluod.order.entity.CommonResult;
import com.springcluod.order.entity.Pay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    private static final String HTTP_URL="http://localhost:8080/pay/";

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/order/save")
    public CommonResult save(Pay pay){
        return restTemplate.postForObject(HTTP_URL+"save",pay,CommonResult.class);
    }

    @GetMapping("/order/delete/{id}")
    public CommonResult delete(@PathVariable("id") int id){
        return restTemplate.getForObject(HTTP_URL+"delete/"+id, CommonResult.class);
    }
}
```

## 4.5重构Order-Pay工程

### 4.5.1Hutool

> 1. **Hutool是一个小而全的Java工具类库，通过静态方法封装，降低相关API的学习成本，提高工作效率，使Java拥有函数式语言般的优雅，让Java语言也可以“甜甜的”。**
> 2. **Hutool中的工具方法来自每个用户的精雕细琢，它涵盖了Java开发底层代码中的方方面面，它既是大型项目开发中解决小问题的利器，也是小型项目中的效率担当；**
> 3. **Hutool是项目中“util”包友好的替代，它节省了开发人员对项目中公用类和公用工具方法的封装时间，使开发专注于业务，同时可以最大限度的避免封装不完善带来的bug。**
> 4. **Hutool = Hu + tool，是原公司项目底层代码剥离后的开源库，“Hu”是公司名称的表示，tool表示工具。Hutool谐音“糊涂”，一方面简洁易懂，一方面寓意“难得糊涂”。**

|        模块        |                             介绍                             |
| :----------------: | :----------------------------------------------------------: |
|     hutool-aop     |            JDK动态代理封装，提供非IOC下的切面支持            |
| hutool-bloomFilter |             布隆过滤，提供一些Hash算法的布隆过滤             |
|    hutool-cache    |                         简单缓存实现                         |
|    hutool-core     |             核心，包括Bean操作、日期、各种Util等             |
|    hutool-cron     |         定时任务模块，提供类Crontab表达式的定时任务          |
|   hutool-crypto    |         加密解密模块，提供对称、非对称和摘要算法封装         |
|     hutool-db      |          JDBC封装后的数据操作，基于ActiveRecord思想          |
|     hutool-dfa     |                  基于DFA模型的多关键字查找                   |
|    hutool-extra    | 扩展模块，对第三方封装（模板引擎、邮件、Servlet、二维码、Emoji、FTP、分词等） |
|    hutool-http     |            基于HttpUrlConnection的Http客户端封装             |
|     hutool-log     |                  自动识别日志实现的日志门面                  |
|   hutool-script    |                 脚本执行封装，例如Javascript                 |
|   hutool-setting   |         功能更强大的Setting配置文件和Properties封装          |
|   hutool-system    |                系统参数调用封装（JVM信息等）                 |
|    hutool-json     |                           JSON实现                           |
|   hutool-captcha   |                        图片验证码实现                        |
|     hutool-poi     |                  针对POI中Excel和Word的封装                  |
|   hutool-socket    |                基于Java的NIO和AIO的Socket封装                |
|     hutool-jwt     |                 JSON Web Token (JWT)封装实现                 |

```xml
<dependency>
    <groupId>cn.hutool</groupId>
    <artifactId>hutool-all</artifactId>
    <version>5.7.20</version>
</dependency>
```

### 4.5.2通过Maven的命令进行打包上传本地库

![image-20220123214224106](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220123214224106.png)

### 4.5.3使用依赖

```xml
   <dependency>
                <groupId>org.spring.cloud</groupId>
                <artifactId>Common-api</artifactId>
                <version>${common-api.version}</version>
   </dependency>
```

# 5.Eureka服务注册与发现

## 5.1什么是服务治理

> 1. **`SpringCluod`封装了`Netflx`公司开发的`Eureka`模块来实现服务治理**
> 2. **在传统的`RPC`远程调用框架中，管理每个服务与服务之间依赖关系比较复杂，管理比较复杂，所以需要使用服务治理，管理服务与服务之间依赖关系，可以实现服务调用、负载均衡、容错等，实现服务发现与注册**

## 5.2什么是服务注册和发现

> 1. **`Eureka`采用了CS的设计架构，`Fureka Server`作为服务注册功能的服务器，它是服务注册中心。而系统中的其他微服务，使用`Eureka`的客户端连接到`Eureka Server`并维持心跳连接，这样的系统的维护人员就可以通过`Eureka Server`来监控系统中各个微服务是否正常运行。**
> 2. **在服务注册与发现中，有一个注册中心。当服务器启动的时候，会把当前自己服务器的信息。比如：服务地址、通讯地址等以别名的方式注册到注册中心上。另一方(消费者|服务提供者)，以该i别名去注册中心上获取到实际的服务通讯地址，然后再实现本地`RPC`远程调用框架核心设计思想：在于注册中心，因为使用注册中心管理每个服务于服务之间的一个依赖关系(服务治理概念)，再任何`RPC`远程框架中，都会有一个注册中心(存放服务地址相关信息(接口文档))。**

![image-20220123215936254](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220123215936254.png)

## 5.3Eureka二个组件

### 5.3.1Eureka Server

> 1. **`Eureka Server`提供服务注册服务**
> 2. **各个微服务节点通过配置启动后，会再`Eureka Server`中进行注册，这样`Eureka Server`中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以再界面中看到。**

### 5.3.2Eureka Client

> 1. **`Eureka Client`通过注册中心进行访问**
> 2. **是一个Java客户端，用于简化`Eureka Server`的交互，客户端同时也具备一个内置的、使用轮询负载算法的负载均衡器。再应用启动后，将会向`Eureka Server`发送心跳(默认周期为30秒)。如果`Eureka Server`在多个心跳周期内没有接受到某个节点的心跳。`Eureka Server`将会从服务注册表中把这个服务节点进行移除(默认为90秒)**

## 5.4单机版Eureka

### 5.4.1Idea生成EurekaServer端服务注册中心

#### 5.4.1.1Maven依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloud</artifactId>
        <groupId>org.spring.cloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>Cluod-Eureka</artifactId>

    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

</project>
```

#### 5.4.1.2配置文件

```yaml
server:
  port: 7000

eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

```

#### 5.4.1.3启动类

```java
package com.erueka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class EurekaApplicationServer {
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplicationServer.class, args);
    }
}

```

### 5.4.2Idea生成EurekaClient提供者

#### 5.4.2.1Maven依赖

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
```

#### 5.4.2.2配置文件

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:7000/eureka/
    register-with-eureka: true
    fetch-registry: true
```

#### 5.4.2.3启动类

```java
@SpringBootApplication
@EnableEurekaClient
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
```

## 5.5Eureka集群

### 5.5.1Eureka集群原理

> 1. **Eureka 集群的工作原理。我们假设有三台 Eureka Server 组成的集群，第一台 Eureka Server 在北京机房，另外两台 Eureka Server 在深圳和西安机房。这样三台 Eureka Server 就组建成了一个跨区域的高可用集群，只要三个地方的任意一个机房不出现问题，都不会影响整个架构的稳定性。**
> 2. **如果某台 Eureka Server 宕机，Eureka Client 的请求会自动切换到新的 Eureka Server 节点。当宕机的服务器重新恢复后，Eureka 会再次将其纳入到服务器集群管理之中。当节点开始接受客户端请求时，所有的操作都会进行节点间复制，将请求复制到其它 Eureka Server 当前所知的所有节点中。**
> 3. **另外 Eureka Server 的同步遵循着一个非常简单的原则：只要有一条边将节点连接，就可以进行信息传播与同步。所以，如果存在多个节点，只需要将节点之间两两连接起来形成通路，那么其它注册中心都可以共享信息。每个 Eureka Server 同时也是 Eureka Client，多个 Eureka Server 之间通过 P2P 的方式完成服务注册表的同步。**
> 4. **Eureka Server 集群之间的状态是采用异步方式同步的，所以不保证节点间的状态一定是一致的，不过基本能保证最终状态是一致的。**
> 5. **Eureka Server 各个节点都是平等的，几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而 Eureka Client 在向某个 Eureka 注册时，如果发现连接失败，则会自动切换至其它节点。只要有一台 Eureka Server 还在，就能保证注册服务可用(保证可用性)，只不过查到的信息可能不是最新的(不保证强一致性)。**
> 6. **Eurka 保证 AP（*CAP*原则又称*CAP*定理，指的是在一个分布式系统中，一致性（Consistency）、可用性（Availability）、分区容错性（Partition tolerance））**

![image-20220124142926310](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220124142926310.png)

### 5.5.2Eureka集群配置环境

#### 5.5.2.1需要二部Eureka Server

![image-20220124145650430](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220124145650430.png)

#### 5.5.2.2配置本地window的host文件的映射

```tex
127.0.0.1 eureka7000.com
127.0.0.1 eureka7001.com
```

#### 5.5.2.3配置文件

**7000**

```yaml
server:
  port: 7000

eureka:
  instance:
    hostname: eureka7000.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/
```

**7001**

```yaml
server:
  port: 7001

eureka:
  instance:
    hostname: eureka7001.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://eureka7000.com:7000/eureka/
```

### 5.5.3配置EurekaClient发布到集群中

```yaml
eureka:
  client:
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7000.com:7000/eureka/
    register-with-eureka: true
    fetch-registry: true
```

## 5.6Eureka的负载均衡

### 5.6.1准备二个提供者

![image-20220124163043139](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220124163043139.png)

#### 5.6.2配置RestTemplate的负载均衡

```java
@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

#### 5.6.3控制层的请求URL改变

```java
 private static final String HTTP_URL="http://PAY-8080-MRICSERIVCE/pay/";
```

## 5.7Eureka的Actuator相关配置

```xml
eureka:
  instance:
    # 设置当前实例别名
    instance-id: "pay-8081"
    # 开启IP连接
    prefer-ip-address: true
```

## 5.8Eureka的Discovery

> **对于注册进`Eureka`里面的微服务，可以通过服务发现来获取该服务的信息**

```java
import org.springframework.cloud.client.discovery.DiscoveryClient;
   
@Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/pay/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        services.forEach(x -> {
            log.info(x.toString());
        });

        List<ServiceInstance> instances = discoveryClient.getInstances("PAY-8080-MRICSERIVCE");
        instances.forEach(x -> {
            log.info("serviceID"+x.getServiceId()+"instanceID"+x.getInstanceId()+"port"+x.getPort()+"Host"+x.getHost());
        });
        return this.discoveryClient;
    }
```

```java
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class PayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayApplication.class, args);
    }
}
```

## 5.9Eureka自我保护

> **保护模式主要用于一组客户端和`Eureka Server`之间存在网络分区场景下的保护。一旦进入保护模式，`Eureka Server`将会尝试保护其他服务注册表中的信息，不再删除服务注册表中的数据，也就是不会注销任何微服务。**

### 5.9.1为什么会产生Eureka自我保护机制？

> 1. **为了防止`Eureka Client`可以正常运行，但是与`Eureka Server`网络不通情况下，`Eureka Server`不会立刻将`Eureka Client`服务移除**
> 2. **默认情况下，如果`Eureka Server`在一定事件内没有接受到某个微服务实例的心跳。`Eureka Server`将会注销该实例(默认90秒)。但是当网络分区故障发生(延迟、卡卡顿、拥挤)时，微服务与`Eureka Server`之间无法正常通信，以上行为可能变得非常危险了。因为微服务本身其实是健康的。此时本不应该注销这个微服务的。`Eureka`通过自我保护模式，来解决这个问题。当`eureka`节点在短时间丢失过多的客户端时，那么这个节点就会进入自我保护模式。**
> 3. **在自我保护模式中，`Eureka Server`会保护服务注册表中的信息，不再注销任何服务实例。它的设计哲学就是宁可保留也不愿盲目注销有可能健康的微服务**

### 5.9.2EurekaServer关闭自我保护配置

![image-20220124193040202](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220124193040202.png)

```yaml
server:
  port: 7000

eureka:
  server:
    # 关闭自我保护机制，保证不可用的服务被及时剔除
    enable-self-preservation: false
    # 如果2秒内没有收到某个微服务的心跳，那就剔除该微服务，单位为毫秒
    eviction-interval-timer-in-ms: 2000
  instance:
    hostname: eureka7000.com
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
#      集群模式
#      defaultZone: http://eureka7001.com:7001/eureka/
#       单机模式
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

```

### 5.9.3EurekaClient配置

```yaml
eureka:
  instance:
    # 设置当前实例别名
    instance-id: "pay-8081"
    # 开启IP连接
    prefer-ip-address: true
    # 表示eureka服务器从收到消息后等待的时间(单位为秒)
    lease-expiration-duration-in-seconds: 2
    # 表示eureka客户端需要发送心跳的频率(以秒为单位)
    lease-renewal-interval-in-seconds: 1
  client:
    service-url:
#      集群模式
#      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7000.com:7000/eureka/
#   单机模式
      defaultZone: http://eureka7000.com:7000/eureka/
    register-with-eureka: true
    fetch-registry: true
```

# 6.Zookeeper服务注册与发现

![image-20220124194107580](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220124194107580.png)

## 6.1准备一台虚拟机

![image-20220124195118114](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220124195118114.png)

## 6.2在虚拟机准备环境

**Java的环境，可以使用rpm安装，可以直接省略。**

## 6.3Zookeeper官网

> https://zookeeper.apache.org/doc/r3.6.3/zookeeperStarted.html

1. **放在Linux中，解压进行进入bin目录启动**
2. **放行端口防火墙**







## 6.4Maven工程

### 6.4.1依赖

```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    </dependency>
```

### 6.4.2配置文件

```yaml
  cloud:
    zookeeper:
      connect-string: 192.168.234.135:2181
```

## 6.5Zookeeper的临时节点吗？

> **是临时节点，因为当提供者停用的话Zookeeper会直接注销。**



# 7.Consul服务与发现

## 7.1Consul简介

> 1. **`Consul`是一套开源的分布式服务发现和配置管理系统，由`HashiCorp`公司用GO语言开发**
> 2. **提供了微服务系统中的服务治、配置中心、控制总线等功能。这些功能中的每一个都可以根据需要单独使用，也可以一起使用以构建全方位的服务网格，总之`Consul`提供了一种完整的服务网格解决方案**
> 3. **它具有很有有点。包括：基于raft协议、比较简洁；支持健康检测，同时支持HTTP和DNS协议支持跨数据中心的集群，提供图形界面。支持Linux、Mac、Windows**

## 7.2Consul安装

### 7.2.1Consul官网

> https://www.consul.io/downloads

### 7.2.2设置Consul环境变量

![image-20220126132645714](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220126132645714.png)

![image-20220126132654297](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220126132654297.png)

### 7.2.3Consul启动

> **consul agent -dev**

![image-20220126132901976](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220126132901976.png)

![image-20220126133103760](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220126133103760.png)

## 7.3提供者

### 7.3.1依赖

```xml
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-consul-discovery</artifactId>
    </dependency>
```

### 7.3.2配置文件

```yaml
spring:
  application:
    name: pay-consul-Mircservice
  #consul 信息配置
  cloud:
    consul:
      host: localhost #consul注册中心的ip地址
      port: 8500 #consul注册中心端口
      discovery:
        service-name: ${spring.application.name} #服务的名称
        instance-id: ${spring.application.name}-${server.port}
        hostname: 127.0.0.1
#        register: true #是否需要注册
#        prefer-ip-address: true #开启ip地址注册
#        ip-address: ${spring.cloud.client.ip-ad
```

### 7.3.3配置类

```java
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsulServiceApplication.class, args);
    }
}

```

## 7.4CAP

> 1. **Consistency（强一致性）**
> 2. **Availability（可用性）**
> 3. **Partition tolerance（分区容错性）**

## 7.5CP-CA-CP

> 1. **CA-单点集群，满足一致性，可用性的系统，通常在可扩展性上不太强大。**
> 2. **CP-满足一致性，分区容错的系统，通常性能不是特别高**
> 3. **AP-满足可用性，分区容错的系统，通常对一致性要求略低一些。**

# 8.三个注册中心的区别

|  组件名   | 语言 | CAP  | 服务健康检查 | 对外暴露接口 | Spring Cloud集成 |
| :-------: | ---- | :--: | :----------: | :----------: | :--------------: |
|  Eureka   | Java |  AP  |   可配支持   |     HTTP     |      已集成      |
| Zookeeper | Java |  CP  |     支持     |    客户端    |      已集成      |
|  Consul   | Go   |  CP  |     支持     |   HTTP/DNS   |      已集成      |

# 9.Ribbon负载均衡-服务调用

## 9.1概述

1. **SpringCloudRibbon是基于Netfix Ribbon实现的一套客户端负载均衡的工具**
2. **简单的来说，Ribbon是Netfix发布的开源项目，主要功能是提供客户端的软件负载均衡算法和服务调用。Ribbon客户端组件提供一系列完善的配置如连接超时、重试等。简单的来说，就是配置文件中列出Load Banlancer简称LB后面所有的机器，Ribbon会自动地帮助你基于简单轮询或者随机连接，去连接这些机器。我们很容易使用Ribbon实现自定义地负载均衡算法。**

![image-20220126164740690](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220126164740690.png)

## 9.2LB负载均衡-作用

1. **简单的来说就是将用户地请求平摊地分配到多个服务上，从而达到系统地HA(高可用)**
2. **常见的负载均衡有软件Nginx、LVS、硬件F5等**

## 9.3RibbonVSNginx区别

1. **Nginx是服务负载均衡，客户端所有的请求都会交给Nginx，然后由Nginx实现转发请求。即负载均衡是由服务端实现的**
2. **Ribbon本地负载均衡，在调用微服务接口时候。会在注册中心上获取注册信息列表之后缓存在JVM本地，从而在本地实现RPC远程调用技术**、

## 9.4集中式LB

> **即在服务的消费方和提供方之间使用独立的LB设施(可以是硬件，如F5,也可以是软件，如nginx),由该设施负责把访问请求通过某种策略转发至服务的提供方;**

## 9.5进程式LB

> 1. **将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。**
> 2. **Ribbon就属于进程内LB，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址**

## 9.6Ribbon演示

### 9.6.1依赖

```xml
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-netflix-ribbon</artifactId>
  <version>2.2.5.RELEASE</version>
  <scope>compile</scope>
  <optional>true</optional>
</dependency>
```

### 9.6.2消费者-控制层

```java
  public StringBuffer rest(){
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(HTTP_URL + "rest", String.class);
        String body = responseEntity.getBody();
        HttpStatus statusCode = responseEntity.getStatusCode();
        int statusCodeValue = responseEntity.getStatusCodeValue();
        HttpHeaders headers = responseEntity.getHeaders();
        StringBuffer result = new StringBuffer();
        result.append("responseEntity.getBody()：").append(body).append("<hr>")
                .append("responseEntity.getStatusCode()：").append(statusCode).append("<hr>")
                .append("responseEntity.getStatusCodeValue()：").append(statusCodeValue).append("<hr>")
                .append("responseEntity.getHeaders()：").append(headers).append("<hr>");
        return result;
    }
```

### 9.6.3提供者-控制层

```java
 @GetMapping("/pay/rest")
    public StringBuffer rest(){
        ResponseEntity<String> responseEntity = new ResponseEntity<String>(HttpStatus.OK);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("nihao");
        return stringBuffer;
    }
```

## 9.7核心组件Ribbon组件-IRule

![image-20220126202117307](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220126202117307.png)

## 9.8替换Ribbon负载均衡算法

![image-20220126202945996](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220126202945996.png)

```java
@Configuration
public class RibbonConfig {
    @Bean
    public IRule iRule(){
        return new RandomRule();
    }
}
```

```java
@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "PAY-8080-MRICSERIVCE",configuration = {RibbonConfig.class})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
    }
}
```

## 9.9Ribbon负载均衡-轮询原理

**负载均衡算法: rest接口第几次请求数%服务器集群总数量=实际调用服务器位置下标，每次服务重启动后rest接口计数从1开始。**

## 9.10自定义轮询算法

```java
public interface LoadBalancer<T> {

    public T getInstance(T t);

}
```

```java
package com.springcluod.order.lb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
public class LoadBalancerImpl implements LoadBalancer{

    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Object getInstance(Object intances) {
        int increment = atomicInteger.get();
        int next;
        List<ServiceInstance> listintances = (List<ServiceInstance>) intances;
        do {
            next = increment >= Integer.MAX_VALUE ? 0 :increment + 1;
        }while (!atomicInteger.compareAndSet(increment, next));
        log.info(String.valueOf(next));
        return next % listintances.size();
    }

}
```

```java
@GetMapping("/order/lb")
public String lb(){
    List<ServiceInstance> instances = discoveryClient.getInstances("PAY-8080-MRICSERIVCE");
    int index = (int)balancer.getInstance(instances);
    ServiceInstance serviceInstance = instances.get(index);
    System.out.println("serviceInstance.toString() = " +serviceInstance.toString());
    String url = "http://127.0.0.1"+":"+serviceInstance.getPort()+"/pay/lb";
    return restTemplate.getForObject(url,String.class);
}
```

# 10.OpenFeign-服务调用

## 10.1OpenFegin简介

1. **Fegin是一个声明WebService客户端。使用Fegin能让编写WebService客户端更加简单。**
2. **它的使用方法是定义一个服务接口然后在上面添加注解。Fegin也支持可拔插式的编码器和解码器。SpringCloud对Feign进行了封装。使其支持SpringMVC标准注解和HttpMessageConverters。Fegin可以在Eureka和Ribbon组合使用支持负载均衡**

## 10.2OpenFegin作用

> 1. **Feign旨在使编写Java Http客户端变得更容易。**
> 2. **前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。但是在实际开发中，由于对服务依赖的调用可能不止一处,往往一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。在Feign的实现下，我们只需创建一个接口并使用注解的方式来配置它(以前是Dao接口上面标注Mapper注解,现在是一个微服务接口上面标注一个Feign注解即可)，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量。**

### 10.2.1OpenFeign集成

> **利用Ribbon维护了Payment的服务列表信息，并且通过轮询实现了客户端的负载均衡。而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用**

## 10.3openFeign和Feign区别

|                            Fegin                             |                          openFegin                           |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| Feign是Spring Cloud组件中的一个轻量级RESTful的HTTP服务客户端Feign内置了Ribbon，用来做客户端负载均衡，去调用服务注册中心的服务。Feign的使用方式是:使用Feign的注解定义接口，调用这个接口就可以语用服务注册中心的服务 | OpenFeign是Spring Cloud在Feign的基础上支持了SpringMVC的注解，如@RequesMapping等等。OpenFeign的@FeignClient可以解析SpringMVc的@RequestMapping注解下的接口，并通过动态代理的方式产生实现类，实现类中做负载均衡并调用其他服务。 |
| <dependency><groupId>org.springframework.cloud</groupId><artifactId>spring-cloud-starter-feign< / artifactId></dependency> | <dependency><groupId>org.springframework.cloud</groupId><artifactId>spring-cloud-starter-openfeign</artifactId></dependency> |

## 10.4OpenFegin使用

### 10.4.1依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

### 10.4.2启动类

```java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class OderOpfeignClient {
    public static void main(String[] args) {
        SpringApplication.run(OderOpfeignClient.class, args);
    }
}
```

### 10.4.3服务接口

```java
@Service
@FeignClient(value = "PAY-8080-MRICSERIVCE")
public interface OpfeignService {

    @GetMapping("/pay/lb")
    public String lb();
}
```

### 10.4.4控制层

```java
@RestController
public class OpfeignController {

    @Resource
    private OpfeignService opfeignService;

    @GetMapping("/order/openfeign")
    public String openfeign(){
        return opfeignService.lb();
    }
}
```

## 10.5OpenFeign-超时控制

### 10.5.1OpenFeign超时控制是什么

1. **默认Feign客户端只等待一秒钟，但是服务端处理需要超过1秒钟，导致Feign客户端不想等待了，直接返回报错。为了避免这样的情况，有时候我们需要设置Feign客户端的超时控制。**

2. **yml文件中开启配置**

### 10.5.2演示

![image-20220128145450877](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220128145450877.png)

### 10.5.3配置消费者和提供者等待时间

```yaml
feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
```

## 10.6OpenFeign-日志打印功能

> **Feign 提供了日志打印功能，我们可以通过配置来调整日志级别，从而了解Feign中Http请求的细节.说白了就是对Feign接口的调用情况进行监控和输出**

### 10.6.1日志级别

1. **NONE:默认的，不显示任何日志**
2. **BASIC：仅记录请求方法、URL、响应状态码与执行时间**
3. **HEADERS：除了BASIC中定义的信息之外，还有请求和响应的头信息**
4. **FULL：除了HEADERS中定义的信息之外，还有请求和响应的正文和元数据**

### 10.6.2配置类

```java
@Configuration
public class FooConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
```

### 10.6.3配置文件

```yaml
logging.level.project.user.UserClient: DEBUG
```

### 10.6.4后台打印

```tex
2022-01-28 15:10:33.690  INFO 14688 --- [erListUpdater-0] c.netflix.config.ChainedDynamicProperty  : Flipping property: PAY-8080-MRICSERIVCE.ribbon.ActiveConnectionsLimit to use NEXT property: niws.loadbalancer.availabilityFilteringRule.activeConnectionsLimit = 2147483647
2022-01-28 15:10:35.703 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] <--- HTTP/1.1 200 (3063ms)
2022-01-28 15:10:35.703 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] connection: keep-alive
2022-01-28 15:10:35.703 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] content-length: 4
2022-01-28 15:10:35.703 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] content-type: text/plain;charset=UTF-8
2022-01-28 15:10:35.703 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] date: Fri, 28 Jan 2022 07:10:35 GMT
2022-01-28 15:10:35.704 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] keep-alive: timeout=60
2022-01-28 15:10:35.704 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] 
2022-01-28 15:10:35.704 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] 8080
2022-01-28 15:10:35.704 DEBUG 14688 --- [p-nio-80-exec-4] com.opfeign.service.OpfeignService       : [OpfeignService#timeOut] <--- END HTTP (4-byte body)

```

# 11.Hystrix-服务降级(熔断器)

## 11.1分布式面临的问题

> **复杂的分布式体系结构中的应用程序有数十种依赖关系。每个依赖关系在某些时候不可避免地失败**

#### 11.1.1服务雪崩

1. **多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其它的微服务，这就是所谓的r“扇出”。如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应”。**
2. **对于高流量的应用来说，单一的后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和。比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。**
   **所以，通常当你发现一个模块下的某个实例失败后，这时候这个模块依然还会接收流量，然后这个有问题的模块还调用了其他的模块，这样就会发生级联故障，或者叫雪崩。**

## 11.2Hystrix是什么

1. **Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性。**
2. **"断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝)，向调用方返回一个符合预期的、可处理的备选响应(FallBack)，而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。**

## 11.3Hystrix能干什么

1. **服务降低**
2. **服务熔断**
3. **实时监控**

## 11.4Hystrix官方停更

![image-20220128152955285](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220128152955285.png)

## 11.5Hystrix重要概念

### 11.5.1Hystrix服务降级

1. **服务器忙，请稍后再试的温馨信息**
2. **哪些情况会出发降级**
   1. **程序运行异常**
   2. **超时**
   3. **服务熔断触发服务降级**
   4. **线程池/信号量打满也会导致降级**

### 11.5.2Hystrix服务熔断

1. **类似保险丝达到最大服务访问后，直接拒绝访问，拉闸，然后调用服务降级的方法返回良好的信息。**
2. **就是保险丝**
2. **服务降级到熔断到调用链路**

### 11.5.3Hystrix服务限流

1. **秒杀、高并发**
2. **以上的操作会导致服务限流，禁止拥挤，产生有顺序的排队**

## 11.6Hystrix-案例

### 11.6.1Hystrix-构建

#### 11.6.1.1Maven依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

### 11.6.2Jmeter测压

> https://www.baidu.com/link?url=edqrYdI6H6FBI8P2IxyKHMCAevHamDJikpn2taLGFUb9Cvx7iT0HAY0Si_o1Ruky&wd=&eqid=9f9d79a00016c1610000000661f4f2ac

![image-20220129155541542](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220129155541542.png)

### 11.6.3故障现象和原因

1. **由于大部分的线程去抢占同个请求会导致服务器的资源消耗巨大**
2. **从而请求别的请求也会有延迟**

### 11.6.4Hystrix-服务降级

#### 11.6.4.1启动类

```java
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class PayHystrixServer {
    public static void main(String[] args) {
        SpringApplication.run(PayHystrixServer.class, args);
    }
}
```

#### 11.6.4.2控制层

```java
@HystrixCommand(fallbackMethod = "str_fal1backMethod",
        groupKey = "strGroupCommand",
        commandKey = "strCommand",
        threadPoolKey = "strThreadPoo1",
        commandProperties = {
                //设置隔离策略，THREAD表示线程地SEMAPHORE:信号池隔离
                @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                //当隔离策略选择信号池隔离的时候，用来设置信号池的大小(最大并发数)
                @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value = "10"),
                //配置命令执行的超时时间
                @HystrixProperty(name = "execution.isolation.thread.timeoutinMilliseconds", value = "10"),
                //是否启用超时时间
                @HystrixProperty(name = "execution.timeout.enabled", value = "true"),
                //执行超时的时候是否中断
                @HystrixProperty(name = "execution.isolation.thread.interruptOnTimeout", value = "true"),
                //执行被取消的时候是否中断
                @HystrixProperty(name = "execution.isolation.thread.interruptOnCancel", value = "true"),
                //允许回调方法执行的最大并发数
                @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "10"),
                //服务降级是否启用，是否执行回调函数
                @HystrixProperty(name = "fallback.enabled", value = "true"),
                @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
                //该属性用来没置在滚动时间窗中，断路器熔断的最小请求数。例如，默认该值为20的时候
                //，如果滚动时间窗（默认10秒）内仅收到了19个请求，即使这19个请求都失败了，断路器也不会打开。
                @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "20"),
                //该属性用来没置在滚动时间窗中，表示在滚动时间窗中，在请求数量超过
                //circuitBreaker.requestVolumeThreshold的情况下，如果错误
                //请求数的百分比超过50,就把断路器设置为“打开”状态，否则就设置为“关闭”状态。
                @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                //该属性用来设置当断路器打开之后的休眠时间窗。休眠时间窗结束之后，
                //会将断路器置为“半开”状态，尝试熔断的请求命令，如果依然失败就将断路器继续设置为“打开”状态，
                //如果成功就没置为"关闭”状态。
                @HystrixProperty(name = "circuitBreaker.sleepWindowinMilliseconds", value = "5000"),
                //断路器强制打开
                @HystrixProperty(name = "circuitBreaker.forceOpen", value = "false"),
                //断路器强制关闭
                @HystrixProperty(name = "circuitBreaker.forceClosed", value = "false"),
                //滚动时间窗设置，该时间用于断路器判断健康度时需要收集信息的持续时间
                @HystrixProperty(name = "metrics.rollingStats.timeinMilliseconds", value = "10000"),
                //该属性用来设置滚动时间窗统计指标信息时划分"桶"的数量，断路器在收集指标信息的时候会根据
                //设置的时间窗长度拆分成多个“桶”来累计各度量值，每个”桶"记录了一段时间内的采集指标。
                //比如10秒内拆分成10个"桶"收集这样，所以 timeinMilliseconds必须能被numBuckets整除。否则会抛异常@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
                @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "10"),
                //该属性用来设置对命令执行的延迟是否使用百分位数来跟踪和计算。如果没置为false，那么所有的概要统计都将返回-1。
                @HystrixProperty(name = "metrics.rollingPercentile.enabled", value = "false"),
                //该属性用来没置百分位统计的滚动窗口的持续时间，单位为毫秒。
                @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "60000"),
                // 该属性用来没置百分位统计滚动窗口中使用“桶”的数量。
                @HystrixProperty(name = "metrics.rollingPercentile.numBuckets", value = "60000"),
                //该属性用来没置在执行过程中每个“桶”中保留的最大执行次数。如果在添动时间窗内发生超过该设定值的执行次数，
                //就从最初的位置开始重写。例如，将该值设置为100，滚动窗口为10秒，若在10秒内一个“桶”中发生了500次执行，
                //那么该“桶”中只保留最后的100次执行的统计。另外，增加该值的大小将会增加内存量的消耗，并增加排序百分位数所需的计算时间。
                @HystrixProperty(name = "metrics.rollingPercentile.bucketSize", value = "100"),
                //该属性用来设置采集影响断路器状态的健康快照（请求的成功、错误百分比）的间隔等待时间。
                @HystrixProperty(name = "metrics.healthSnapshot.intervalinMilliseconds", value = "500"),
                //是否开启请求缓存
                @HystrixProperty(name = "requestCache.enabled", value = "true"),
                //HystrixCommand的执行和事件是否打印日志到 HystrixRequestLog 中
                @HystrixProperty(name = "requestLog.enabled", value = "true"),},
        threadPoolProperties = {
                //该参数用来设置执行命令线程池的核心线程数，该值也就是命令执行的最大并发量
                @HystrixProperty(name = "coreSize", value = "10"),
                //该参数用来没置线程池的最大队列大小。当设置为-1时，线程池将使用SynchronousQueue实现的队列，
                //否则将使用LinkedBLockingQueue实现的队列。
                @HystrixProperty(name = "maxQueueSize", value = "-1"),
                //该参数用来为队列设置拒绝阈值。通过该参数，即使队列没有达到最大值也能拒绝请求。
                //该参数主要是对LinkedBLockingQueue 队列的补充,因为LinkedBLockingQueue
                //队列不能动态修改它的对象大小，而通过该属性就可以调整拒绝请求的队列大小了。
                @HystrixProperty(name = "queueSizeRejectionThreshold", value = "5")})

```

```java
@GetMapping("/pay/timeout/hystrix")
@HystrixCommand(fallbackMethod = "timeoutError",commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3")
})
public String timeout() throws InterruptedException {
    Thread.sleep(5000);
    return server_port+Thread.currentThread().getName();
}

public String timeoutError(){
    return "系统繁忙"+server_port;
}
```

#### 11.6.4.3配置全局降级

```java
@RestController
@DefaultProperties(defaultFallback = "timeoutError")
public class PayControllerHystrix{

}
```

#### 11.6.4.4Client端配置-Hystrix

```yml
feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 5000
  hystrix:
    enabled: true
```

```java
@Service
@FeignClient(value = "PAY-HYSTRIX-8083",fallback = OrderServiceImpl.class)
public interface OrderService {
```

```java
public class OrderServiceImpl implements OrderService{
    @Override
    public String hystrix() {
        return "------------OrderServiceImpl------------------";
    }

    @Override
    public String timeout() {
        return "------------OrderServiceImpl------------------";
    }
}
```

### 11.6.5Hystrix-服务熔断

#### 11.6.5.1服务熔断-机制

1. **熔断机制是应对雪崩效应的一种微服务链路保护机制，当扇出链路的某个微服务出错不可用或者响应时间太长。就会进行服务的降级，进而熔断该节点微服务的调用。快速返回错误的响应信息。**
2. **当检测到该节点微服务调用响应正常后，恢复调用链路**
3. **在SpringCloud框架里，熔断机制通过Hystrix实现。Hystrix会监控微服务间调用的状况。**
4. **当失败的调用到一定峰值，缺省是5秒内20次调用失败，就会启动熔断机制，熔断机制的注解是`@HystrixCommand`**

#### 11.6.5.2配置熔断

```java
    @HystrixCommand(fallbackMethod = "timeoutError",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3")
        // 断路器是否使能。
        @HystrixProperty(name = "circuitBreaker.enabled" ,value = "true"),
        // 在使用统计数据打开/关闭决策之前，一个统计窗口内必须发出的请求数
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
        // default => sleepWindow: 5000 = 5秒，我们将在跳闸电路再次尝试之前睡觉
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "6000"),
        // default => errorThresholdPercentage = 50 =如果10秒内50%+的请求是失败或潜在的，那么我们将触发电路      
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
```

#### 11.6.5.3断路器什么时候触发

**涉及到断路器的三个重要参数：快照时间窗、请求总数峰值、错误百分比峰值**

1. **快照时间窗：断路器确定是否打开需要统计一些请求和错误数据 ，而统计的时间范围就是快照时间窗。默认为最近的10秒**
2. **请求总数峰值：在快照时间窗内，必须满足请求总数峰值才有资格熔断，默认为20。意味着在10秒内。如果该hystrix命令的调用哪个次数不到20次。断路器不会打开**
3. **错误百分比：当请求总数在快照时间窗内超过了峰值，比如发生了30次调用，如果在30次调用中。有15次发生错误。那么这个时候断路器会打开**

### 11.6.6Hystrix-服务限流















## 11.7服务监控(dashboard)-Hystrix

> **除了隔离依赖服务的调用以外，Hystrix还提供了准实时的调用监控(Hystrix Dashboard)，Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，并以统计报表和图形的形式展示给用户，包括每秒执行多少请求多少成功，多少失败等。Netflix通过**
> **hystrix-metrics-event-stream项目实现了对以上指标的监控。Spring Cloud也提供了Hystrix Dashboard的整合，对监控内容转化成可视化界面。**

### 11.7.1配置仪表盘-Hystrix

#### 11.7.1.1依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
```

#### 11.7.1.2启动类

```java
@SpringBootApplication
@EnableHystrixDashboard
public class DashBoardSever {
    public static void main(String[] args) {
        SpringApplication.run(DashBoardSever.class, args);
    }
}
```

![image-20220131180906746](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220131180906746.png)

### 11.7.2监控其他微服务

#### 11.7.2.1被监控微服务-依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### 11.7.2.2需要开启的注解

```java
@EnableCircuitBreaker
```

```java
   @GetMapping("/pay/timeout/hystrix/{id}")
    @HystrixCommand(fallbackMethod = "timeoutError",commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3")
        // 断路器是否使能。
        @HystrixProperty(name = "circuitBreaker.enabled" ,value = "true"),
        // 在使用统计数据打开/关闭决策之前，一个统计窗口内必须发出的请求数
        @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
        // default => sleepWindow: 5000 = 5秒，我们将在跳闸电路再次尝试之前睡觉
        @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "6000"),
        // default => errorThresholdPercentage = 50 =如果10秒内50%+的请求是失败或潜在的，那么我们将触发电路
        @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
    public String timeout(@PathVariable("id") int id) throws InterruptedException {
//        Thread.sleep(5000);
        if (id <= 0 ){
            throw new RuntimeException("负数");
        }
        return server_port+Thread.currentThread().getName();
    }

    public String timeoutError(int id){
        return "系统繁忙"+server_port+":"+id;
    }
```

#### 11.7.2.3添加配置

```yaml
hystrix:
  dashboard:
    proxy-stream-allow-list: "localhost"
```

```java
    /**
     * 注意：新版本Hystrix需要在主启动类中指定监控路径
     * 此配置是为了服务监控而配置，与服务容错本身无关，spring cloud升级后的坑
     * ServletRegistrationBean因为springboot的默认路径不是"/hystrix.stream"，
     * 只要在自己的项目里配置上下面的servlet就可以了
     *
     * @return ServletRegistrationBean
     */
    @Bean
    public ServletRegistrationBean<HystrixMetricsStreamServlet> getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean<HystrixMetricsStreamServlet> registrationBean = new ServletRegistrationBean<>(streamServlet);

// 一启动就加载
        registrationBean.setLoadOnStartup(1);
// 添加url
        registrationBean.addUrlMappings("/hystrix.stream");
// 设置名称
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
```

![image-20220131182754963](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220131182754963.png)

# 12.gateway-新服务网关

## 12.1Gateway-简介

1. **SpringCloud Gateway是Spring Cloud的一个全新项目，基于Spring 5.0+Spring Boot 2.0和Project Reactor等技术开发的网关，它旨在为微服务架构提供一种简单有效的统一的API路由管理方式。**
2. **SpringCloud Gateway作为 Spring Cloud生态系统中的网关，目标是替代Zuul，在Spring Cloud 2.0以上版本中，没有对新版本的Zuul 2.0以上最新高性能版本进行集成，仍然还是使用的Zui 1.x非Reactor模式的老版本。而为了提升网关的性能，SpringCloud Gateway是基于WebFlux框架实现的，而WebFlux框架底层则使用了高性能的Reactor模式通信框架Netty.**
3. **Spring Cloud Gateway的目标提供统一的路由方式且基于Filter链的方式提供了网关基本的功能，例如:安全，监控/指标，和限流。**

## 12.2Gateway-能干什么

1. **反向代理**
2. **鉴权**
3. **流量控制**
4. **熔断**
5. **日志监控**

## 12.2Gateway-出现原因

1. **一方面因为Zuul1.0已经进入了维护阶段，而且Gateway是SpringCloud团队研发的，是亲儿子产品，值得信赖。而且很多功能Zuul都没有用起来也非常的简单便捷。**
2. **Gateway是基于异步非阻塞模型上进行开发的，性能方面不需要担心。虽然Netflix早就发布了最新的Zuul 2.x,但Spring Cloud貌似没有整合计划。而且Netflix相关组件都宣布进入维护期;不知前景如何?**
3. **多方面综合考虑Gateway是很理想的网关选择。**

## 12.3Gateway-特性

1. **基于Spring Framework 5, Project Reactor和Spring Boot 2.0进行构建动态路由:能够匹配任何请求属性;**
2. **可以对路由指定 Predicate(断言）和Filter(过滤器);集成Hystrix的断路器功能;**
   **集成Spring Cloud服务发现功能;、易于编写的 Predicate(断言）和Filter (过滤器);、支持路径重写。**                                                                   

## 12.4Gateway-Zuul-区别

> **在SpringCloud Finchley正式版之前，Spring Cloud推荐的网关是Netflix提供的Zuul:**
>
> 1. **Zuul 1.x，是一个基于阻塞V/O的API Gateway**
> 2. **Zuul 1.x基于Servlet 2.5使用阻塞架构它不支持任何长连接(如WebSocket)Zuul 的设计模式和Nginx较像，每次VО操作都是从工作线程中选择一个执行，请求线程被阻塞到工作线程完成，但是差别是Nginx用C++实现，Zuu1用Java实现，而JVM本身会有第一次加载较慢的情况，使得Zuul 的性能相对较差。**
> 3. **Zuul 2.x理念更先进，想基于Netty非阻塞和支持长连接，但SpringCloud目前还没有整合。Zuul 2.x的性能较Zuul 1.x有较大提升。在性能方面，根据官方提供的基准测试，Spring Cloud Gateway的RPS(每秒请求数)是Zuul的1.6倍。**
> 4. **Spring Cloud Gateway建立在Spring Framework 5、Project Reactor和Spring Boot 2之上，使用非阻塞APl。5、Spring Cloud Gateway还支持WebSocket，并且与Spring紧密集成拥有更好的开发体验**

## 12.5Gateway-三大核心概念

### 12.5.1Route(路由)

> **路由是构建网关的基本模板，它由ID，目标URL，一系列的断言和过滤器组成。如果断言为true则匹配成功**

### 12.5.2Predicate(断言)

> **开发人员可以匹配HTTP请求中的所有内容。例如：请求头、请求参数。如果请求和断言相匹配则进行路由跳转。**

### 12.5.3Filter(过滤)

> **指的是Spring框架中的GatewayFilter的实例，使用过滤器，可以在请求被路由前或者之后对请求进行修改等业务操作。**

### 12.5.4Gateway工作

![image-20220203134512082](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220203134512082.png)

> **客户端向 Spring Cloud Gateway 发出请求。  如果网关处理程序映射确定请求与路由匹配，则将其发送到网关 Web 处理程序。 此处理程序通过特定于请求的过滤器链运行请求。 过滤器被虚线划分的原因是过滤器可以在发送代理请求之前和之后运行逻辑。 执行所有“预”过滤器逻辑。  然后发出代理请求。  发出代理请求后，将运行“发布”过滤器逻辑。** 

## 12.6Gateway入门配置

### 12.6.1依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloud</artifactId>
        <groupId>org.spring.cloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>springcloud-gateway-9527</artifactId>

    <dependencies>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-gateway</artifactId>
        </dependency>

        <dependency>
            <groupId>org.spring.cloud</groupId>
            <artifactId>Common-api</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        </dependency>
    </dependencies>
</project>
```

### 12.6.2配置

```yaml
server:
  port: 9527

spring:
  application:
    name: gateway-9527
  cloud:
    gateway:
      routes:
        - id: pay-8080-mricserivce
          uri: http://localhost:8080
          predicates:
            - Path=/pay/delete/**
eureka:
  instance:
    # 设置当前实例别名
    instance-id: "getaway-9527"
    # 开启IP连接
    prefer-ip-address: true
    # 表示eureka服务器从收到消息后等待的时间(单位为秒)
    lease-expiration-duration-in-seconds: 2
    # 表示eureka客户端需要发送心跳的频率(以秒为单位)
    lease-renewal-interval-in-seconds: 1
  client:
    service-url:
      #      集群模式
#      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7000.com:7000/eureka/
    #   单机模式
          defaultZone: http://eureka7000.com:7000/eureka/
    register-with-eureka: true
    fetch-registry: true
```

### 12.6.3启动类

```java
package com.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class GateWayServer {
    public static void main(String[] args) {
        SpringApplication.run(GateWayServer.class, args);
    }
}
```



### 12.6.4代码注入路由配置

```java
package com.springcloud.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GetawayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder routeLocatorBuilder){
        return routeLocatorBuilder.routes().route("pay-mis",
                r -> r.path("/baidu").uri("https://www.baidu.com")).build();
    }
}
```

### 12.6.5动态配置路由

```yaml
spring:
  application:
    name: gateway-9527
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
#        - id: pay-8080-mricserivce
        - id: PAY-8080-MRICSERIVCE
          uri: lb://PAY-8080-MRICSERIVCE
          predicates:
            - Path=/pay/lb/**

        - id: PAY-8080-MRICSERIVCE2
          uri: lb://PAY-8080-MRICSERIVCE
          predicates:
            - Path=/pay/lb/**
```

## 12.7Predicate的使用

> **Spring Cloud Gateway匹配路由，作为Spring WebFlux HandlerMapping基础设施的一部分。 Spring Cloud Gateway包括许多内置的路由谓词工厂。 所有这些谓词都匹配HTTP请求的不同属性。 您可以将多个路由谓词工厂与逻辑和语句组合在一起。**  

### 12.7.1After Route Predicate Factory

> **After路由谓词工厂接受一个参数，一个datetime(它是一个java ZonedDateTime)。 此谓词匹配发生在指定日期时间之后的请求。 配置after路由谓词的示例如下:**  

```yaml
spring:
  application:
    name: gateway-9527
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
#        - id: pay-8080-mricserivce
        - id: PAY-8080-MRICSERIVCE
          uri: lb://PAY-8080-MRICSERIVCE
          predicates:
            - Path=/pay/lb/**
            - After=2022-02-04T12:44:12.364+08:00[Asia/Shanghai]
```

### 12.7.2Before Route Predicate Factory

> **Before路由谓词工厂接受一个参数，一个datetime(它是一个java ZonedDateTime)。 此谓词匹配发生在指定日期时间之前的请求。 配置前置路由谓词的示例如下:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: before_route
        uri: https://example.org
        predicates:
        - Before=2017-01-20T17:42:47.789-07:00[America/Denver]
```

### 12.7.3Between Route Predicate Factory

> **Between路由谓词工厂接受两个参数，datetime1和datetime2，它们是java ZonedDateTime对象。 这个谓词匹配发生在datetime1之后和datetime2之前的请求。 datetime2必须在datetime1之后。 下面的例子配置了一个路由谓词:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: between_route
        uri: https://example.org
        predicates:
        - Between=2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]
```

### 12.7.4Cookie Route Predicate Factory

> **Cookie路由谓词工厂接受两个参数，Cookie名称和regexp(这是一个Java正则表达式)。 此谓词匹配具有给定名称且其值与正则表达式匹配的cookie。 下面的例子配置了一个cookie路由谓词工厂:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: cookie_route
        uri: https://example.org
        predicates:
        - Cookie=username,zzhh
```

### 12.7.5Header Route Predicate Factory

> **Header路由谓词工厂接受两个参数，头和regexp(它是一个Java正则表达式)。 该谓词与具有给定名称的头匹配，该名称的值与正则表达式匹配。 下面的例子配置了报头路由谓词:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: header_route
        uri: https://example.org
        predicates:
        - Header=X-Request-Id, \d+
```

### 12.7.6Host Route Predicate Factory

> **主机路由谓词工厂接受一个参数:主机名模式列表。 该模式是一个ant样式的模式。 作为分隔符。 这个谓词匹配匹配模式的Host头。 配置主机路由谓词的示例如下:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: host_route
        uri: https://example.org
        predicates:
        - Host=**.somehost.org,**.anotherhost.org
```

### 12.7.7Method Route Predicate Factory

> **方法路由谓词工厂接受一个方法参数，它是一个或多个参数:要匹配的HTTP方法。 下面的例子配置了一个方法路由谓词:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: method_route
        uri: https://example.org
        predicates:
        - Method=GET,POST
```

### 12.7.8Path Route Predicate Factory

> **Path Route Predicate Factory接受两个参数:一个Spring PathMatcher模式列表和一个名为matchTrailingSlash的可选标志(默认为true)。 下面的例子配置了一个路径路由谓词:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: path_route
        uri: https://example.org
        predicates:
        - Path=/red/{segment},/blue/{segment}
```

### 12.7.9Query Route Predicate Factory

> **Query路由谓词工厂接受两个参数:一个必需的参数和一个可选的regexp(它是一个Java正则表达式)。 下面的例子配置了一个查询路由谓词:**  

```yaml
spring:
  cloud:
    gateway:
      routes:
      - id: query_route
        uri: https://example.org
        predicates:
        - Query=green
```

## 12.8Curl使用

![image-20220204125622247](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204125622247.png)

## 12.8Filter

> **路由过滤器允许以某种方式修改传入的HTTP请求或传出的HTTP响应。 路由过滤器的作用域是特定的路由。 Spring Cloud Gateway包括许多内置的GatewayFilter factory。**  

### 12.8.1GatewayFilter Factories

> **路由过滤器允许以某种方式修改传入的HTTP请求或传出的HTTP响应。 路由过滤器的作用域是特定的路由。 Spring Cloud Gateway包括许多内置的GatewayFilter factory。**  

### 12.8.2Global Filters

> **GlobalFilter接口具有与GatewayFilter相同的签名。 这些是特殊的过滤器，有条件地应用于所有路由。**

```java
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("custom global filter");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}
```

# 13.Springcloud-config(服务配置)

## 13.1出现的解决的问题

> 1. **微服务意味着要将单体应用中的业务拆分成一个个子服务，每个服务的粒度相对较小，因此系统中会出现大量的服务。由于每个服务都需要必要的配置信息才能运行，所以一套集中式的、动态的配置管理设施是必不可少的。**
> 2. **SpringCloud提供了ConfigServer来解决这个问题，我们每一个微服务自己带着一个application.yml，上百个配置文件的管理...../八(ToT)/~~**

## 13.2是什么

![image-20220204133105022](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204133105022.png)

> **SpringCloud Config为微服务架构中的微服务提供集中化的外部配置支持，配置服务器为各个不同微服务应用的所有环境提供了一个中心化的外部配置。**

> 1. **SpringCloud Config分为服务端和客户端两部分。**
> 2. **服务端也称为分布式配置中心，它是一个独立的微服务应用，用来连接配置服务**
> 3. **器并为客户端提供获取配置信息，加密/解密信息等访问接口**
> 4. **客户端则是通过指定的配置中心来管理应用资源，以及与业务相关的配置内容，并在启动的时候从配置中心获取和加载配置信息配置服务器默认采用git来存储配置信息，这样就有助于对环境配置进行版本管理，并且可以通过git客户端工具来方便的管理和访问配置内容**

## 13.3能干嘛

1. **集中管理配置i文件**
2. **不同环境不同配置，动态化的配置更新，分环境部署**
3. **运行期间动态调正配置，不在需要在每个微服务部署的机器上编写配置文件，服务会向配置中心统一拉取配置自己的信息**
4. **当配置发生变动时，服务不需要重启即可感知到配置的变化并应用新的配置**
5. **将配置信息以REST接口的形式暴露**

## 13.4服务端配置和测试

![image-20220204141030537](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204141030537.png)

### 13.4.1依赖

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-config-server</artifactId>
        </dependency>
```

### 13.4.2文件文件

```yml
spring:
  application:
    name: cloud-config-3344
  cloud:
    config:
      server:
        git:
          uri: https://github.com/UserManZhou/springcloud-springconfig.git
          search-paths:
            - springcloud-springconfig
        label: master
```

### 13.4.3启动类

```java
package com.springcloud.confid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class ConfigServerS {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerS.class, args);
    }
}
```

## 13.5客户端配置和测试

### 13.5.1boostrap.yml是什么

> 1. **applicaiton.ym1是用户级的资源配置项**
> 2. **bootstrap.ym1是系统级的，优先级更加高**
> 3. **Spring Cloud会创建一个“Bootstrap Context”，作为Spring应用的`Application Context的父上下文。初始化的时候，`BootstrapContext`负责从外部源加载配置属性并解析配置。这两个上下文共享一个从外部获取的`Environment'。**
> 4. **`Bootstrap`属性有高优先级，默认情况下，它们不会被本地配置覆盖。'Bootstrap context和Application Context有着不同的约定，所以新增了一个bootstrap.yml'文件，保证`Bootstrap Context和`Application Context'`配置的分离。**
> 5. **要将Client模块下的application.yml文件改为bootstrap.yml,这是很关键的，**
> 6. **因为bootstrap.yml是比application.yml先加载的。bootstrap.yml优先级高于application.yml**

### 13.5.2依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

### 13.5.2配置文件

```yaml
spring:
  application:
    name: cloud-config-3366
  cloud:
    config:
      uri: http://localhost:3344
      name: application
      profile: dev
      label: master
```

### 13.5.3控制层

```java
package com.springcloud.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigClientController {

    @Value("${config.info}")
    private String info;

    @GetMapping("configClient")
    public String configClient(){
        return info;
    }

}
```

### 13.5.4问题

**由于Github文件更新客户端这边的数据没有更新和同步，服务端没事**

### 13.5.5动态解决问题

```yaml
spring:
  application:
    name: cloud-config-3366
  cloud:
    config:
      uri: http://localhost:3344
      name: application
      profile: dev
      label: master

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

```java
package com.springcloud.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {

    @Value("${config.info}")
    private String info;

    @GetMapping("configClient")
    public String configClient(){
        return info;
    }

}
```

![image-20220204174540942](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204174540942.png)

# 14.Springcloud-Bus(消息总线)

## 14.1Springcloud-Bus是什么

1. **支持RabbitMQ**
2. **支持Kafka**

## 14.2什么是总线

> **在微服务架构的系统中，通常会使用轻量级的消息代理来构建一个共用的消息主题，并让系统中所有微服务实例都连接上来。由于该主题中产生的消息会被所有实例监听和消费，所以称它为消息总线。在总线上的各个实例，都可以方便地广播─些需要让其他连接在该主题上的实例都知道的消息。**

## 14.3RabbitMQ环境配置

### 14.3.1安装Erlang

> [Downloads - Erlang/OTP](https://www.erlang.org/downloads)

**直接把bin目录配置到Path里面即可**

![image-20220204190602951](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204190602951.png)

### 14.3.2安装RabbitMQ

> [Downloading and Installing RabbitMQ — RabbitMQ](https://www.rabbitmq.com/download.html)

![image-20220204191713741](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204191713741.png)

![image-20220204191728571](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204191728571.png)

> [RabbitMQ Management](http://localhost:15672/)

![image-20220204191753695](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204191753695.png)

> **默认账号和密码: guest**

![image-20220204191823393](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204191823393.png)

> **使用命令添加用户并授权**
>
> 1. **添加用户**
>    **rabbitmqctl add_user admin admin**
> 2. **设置permissions**
>    **rabbitmqctl set_permissions -p "/" admin ".*" ".*" ".*"**
> 3. **设置用户角色**
>    **rabbitmqctl set_user_tags admin administrator**
> 4. **查看新添加的admin**
>    **rabbitmqctl list_users**
> 5. **查看用于的权限**
>    **rabbitmqctl list_permissions -p /**

## 14.4Springcloud-bus 动态刷新全局广播

### 14.4.1动态通知客户端-动态通知服务端-通用

#### 14.4.1.1依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

#### 14.4.1.2客户端配置

```yaml
spring:
  application:
    name: cloud-config-3366
  cloud:
    config:
      uri: http://localhost:3344
      name: application
      profile: dev
      label: master
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

#### 14.4.1.3服务端配置

```yaml
spring:
  application:
    name: cloud-config-3344
  cloud:
    config:
      server:
        git:
          uri: https://github.com/UserManZhou/springcloud-springconfig.git
          search-paths:
            - springcloud-springconfig
        label: master
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    
    management:
  endpoints:
    web:
      exposure:
        include: "bus-refresh"
```

#### 14.4.14测试

![image-20220204195812973](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204195812973.png)

![image-20220204195817766](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204195817766.png)

![image-20220204195938415](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204195938415.png)

## 14.5Spring-cloud-bus 动态定点通知

![image-20220204201033003](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204201033003.png)

# 15.Spring-cloud-Stream(消息驱动)

## 15.1SpringCloud-Stream概述

> **屏蔽底层消息中间件的差异。降低切换成本，统一消息的编程模型**

![image-20220204202534394](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220204202534394.png)

## 15.2SpringCloud-Stream-简介

> 1. **官方定义Spring Cloud Stream是一个构建消息驱动微服务的框架。**
> 2. **应用程序通过 inputs或者outputs来与Spring Cloud Stream中binder对象交互。**
>    **通过我们配置来binding(绑定)，而Spring Cloud Stream的 binder对象负责与消息中间件交互。所以，我们只需要搞清楚如何与Spring Cloud Stream交互就可以方便使用消息驱动的方式。**
> 3. **通过使用Spring Integration来连接消息代理中间件以实现消息事件驱动。**
> 4. **Spring Cloud Stream为一些供应商的消息中间件产品提供了个性化的自动化配置实现,**
>    **引用了发布-订阅、消费组、分区的三个核心概念。**

## 15.3SpringCloud-Stream-设计思想

### 15.3.1标准的MQ

1. **生产者/消费者之间靠消息媒介传递信息内容**
   1. **Message**
2. **消息必须走特定的通道**
   1. **消息通道MessageChannel**
3. **消息通道里的消息如何被消费呢？谁负责收发处理**
   1. **消息通道MessageChannel的子接口SubscribableChannel，由MessageHandler消息处理器所订阅**

## 15.4SpringCloud-Stream-三大流程

### 15.4.1Binder

**连接中间件，屏蔽差异**

### 15.4.2Channel

**通道，，队列Queue的一种抽象，在消息通讯系统中就是实现存储和转发的媒介。通过Channel对队列进行配置**

### 15.4.3Source和Sink

**从Stream发布消息就是输出，接受信息就是输入**

## 15.5SpringCloud-Stream-API

|      组成       |                             说明                             |
| :-------------: | :----------------------------------------------------------: |
|   Middleware    |              中间件，目前只支持RabbitMQ、Kafka               |
|     Binder      | Binder是应用与消息中间件之间的封装，目前实行了Kafka和RabbitMQ的Binder。通过Binder可以很方便的连接中间件，可以动态的改变消息类型，这些都可以通过配置i文件来实现。(RabbitMQ：Exchange。Kafka：Topic) |
|     @input      |  注解标识输入通道，通过该输入通道渠道接受到信息进入应用程序  |
|     @output     |     注解标识输出通道，发布的消息将通过该通道离开应用程序     |
| @StreamListener |             监听队列，用于消费者的队列的消息接受             |
| @EnableBinding  |              指通道Channel和Exchange绑定到一起               |

## 15.6发出案例

### 15.6.1依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

### 15.6.2配置文件

```yaml
server:
  port: 8801

spring:
  application:
    name: cloud-stream
  cloud:
    stream:
      binders:
        defaultRabbit:
          type: rabbit
          enviroment:
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings:
        output:
          destination: partitioned.destination
          contentType: application/json
          binder: defaultRabbit



eureka:
  instance:
    # 设置当前实例别名
    instance-id: "stream-8801-output"
    # 开启IP连接
    prefer-ip-address: true
    # 表示eureka服务器从收到消息后等待的时间(单位为秒)
    lease-expiration-duration-in-seconds: 2
    # 表示eureka客户端需要发送心跳的频率(以秒为单位)
    lease-renewal-interval-in-seconds: 1
  client:
    service-url:
      #      集群模式
      #      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7000.com:7000/eureka/
      #   单机模式
      defaultZone: http://eureka7001.com:7001/eureka/
    register-with-eureka: true
    fetch-registry: true
```

### 15.6.3消息发送

```java
package com.springcloud.stream.service.impl;

import com.springcloud.stream.service.ProviderService;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(Source.class)
public class ProviderServiceImpl implements ProviderService {

    @Resource
    MessageChannel output;

    @Override
    public void send() {
        String s = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(s).build());
        System.out.println("s = " + s);
    }
}
```

### 15.6.4控制层

```java
package com.springcloud.stream.controller;

import com.springcloud.stream.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {

    @Autowired
    private ProviderService providerService;


    @GetMapping("/stream")
    public void stream(){
        providerService.send();
    }
}
```

## 15.7接收案例

### 15.7.1依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```

### 15.7.2配置文件

```yaml
server:
  port: 8802

spring:
  application:
    name: cloud-stream
  cloud:
    stream:
      binders:
        defaultRabbit:
          type: rabbit
          enviroment:
            spring:
              rabbitmq:
                host: localhost
                port: 5672
                username: guest
                password: guest
      bindings:
        input:
          destination: partitioned.destination
          contentType: application/json
          binder: defaultRabbit



eureka:
  instance:
    # 设置当前实例别名
    instance-id: "stream-8802-input"
    # 开启IP连接
    prefer-ip-address: true
    # 表示eureka服务器从收到消息后等待的时间(单位为秒)
    lease-expiration-duration-in-seconds: 2
    # 表示eureka客户端需要发送心跳的频率(以秒为单位)
    lease-renewal-interval-in-seconds: 1
  client:
    service-url:
      #      集群模式
      #      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7000.com:7000/eureka/
      #   单机模式
      defaultZone: http://eureka7001.com:7001/eureka/
    register-with-eureka: true
    fetch-registry: true
```

### 15.7.3业务层

```java
package com.springcloud.stream.serviceimpl;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@EnableBinding(Sink.class)
public class ComsumeInputService {

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
        System.out.println("message.getPayload().toString() = " + message.getPayload().toString());
    }

}
```

## 15.8分组消费和持久化

### 15.8.1防止重复消费

#### 15.8.1.1自定义分组

```yaml
group: consume1
group: consume2
```

![image-20220205151559880](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205151559880.png)

#### 15.8.1.2分同组

```yaml
group: consume1
```

> **分在同组会进行轮询的情况分别每个消费者进行接收。避免了重复消费**

### 15.8.2注意

1. **在配置分组如有一个消费者没有配置分组则服务端发送的信息。它是不会去RabbitMQ拿未消费的**

# 16.SpringCloud-Sleuth(分布式请求链路追踪)

## 16.1是什么

> **Spring Cloud Sleuth能够跟踪您的请求和消息，这样您就可以将通信与相应的日志条目关联起来。 您还可以将跟踪信息导出到外部系统，以可视化延迟。 Spring Cloud Sleuth直接支持OpenZipkin兼容的系统。**  

## 16.2搭建链路监控步骤

### 16.2.1ZipKin

> https://www.baidu.com/link?url=f8r1fw7BsGrrqKa_PQUEx9St3Mo8BlJq8eyjUnU3TZ_&wd=&eqid=cf9497fd000a03510000000361fe29f1

![image-20220205154847715](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205154847715.png)

![image-20220205154928942](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205154928942.png)

## 16.3案例

### 16.3.1依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

### 16.3.2配置文件

```yaml
spring:
  zipkin:
    base-url: http://localhost:9411/
  sleuth:
    sampler:
      probability: 1
```

![image-20220205160510424](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205160510424.png)

![image-20220205160528518](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205160528518.png)

# 17.SpringCloud-Alibaba

## 17.1简介

> 1. **Spring Cloud Alibaba为分布式应用开发提供一站式解决方案。 它包含开发分布式应用程序所需的所有组件，使您可以轻松地使用Spring Cloud开发应用程序。**  
> 2. **使用Spring Cloud Alibaba，您只需要添加一些注释和少量配置，就可以将Spring Cloud应用连接到阿里巴巴的分布式解决方案上，使用阿里巴巴中间件构建分布式应用系统。**  
> 3. **流量控制与服务退化:流量控制、断路、系统自适应保护**  
> 4. **服务注册和发现:实例可以注册到Alibaba Nacos，客户端可以使用spring管理的bean发现实例。 通过Spring Cloud Netflix支持客户端负载均衡器Ribbon**  
> 5. **分布式配置:使用阿里巴巴Nacos作为数据存储**  
> 6. **事件驱动:构建高度可伸缩的事件驱动微服务，连接Spring Cloud Stream RocketMQ Binder**  
> 7. **消息总线:使用Spring Cloud Bus RocketMQ的分布式系统的链接节点**  
> 8. **分布式事务:通过Seata支持高性能和易用性的分布式事务解决方案**  
> 9. **Dubbo RPC:通过Apache Dubbo RPC扩展Spring Cloud service-to-service调用的通信协议**  

# 18.SpringCloud-Alibaba-Nacos(服务注册和配置中心)

## 18.1Nacos简介

> 1. **动态配置服务让您能够以中心化、外部化和动态化的方式管理所有环境的配置。动态配置消除了配置变更时重新部署应用和服务的需要。配置中心化管理让实现无状态服务更简单，也让按需弹性扩展服务更容易。**
> 2. **动态服务发现对以服务为中心的（例如微服务和云原生）应用架构方式非常关键。Nacos支持DNS-Based和RPC-Based（Dubbo、gRPC）模式的服务发现。Nacos也提供实时健康检查，以防止将请求发往不健康的主机或服务实例。借助Nacos，您可以更容易地为您的服务实现断路器。**
> 3. **通过支持权重路由，动态DNS服务能让您轻松实现中间层负载均衡、更灵活的路由策略、流量控制以及简单数据中心内网的简单DNS解析服务。动态DNS服务还能让您更容易地实现以DNS协议为基础的服务发现，以消除耦合到厂商私有服务发现API上的风险。**

## 18.2Nacos官网

> [home (nacos.io)](https://nacos.io/zh-cn/index.html)

## 18.3使用Nacos

### 18.3.1下载Nacos

> [Releases · alibaba/nacos (github.com)](https://github.com/alibaba/nacos/releases)

### 18.3.2运行Nacos

![image-20220205170330049](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205170330049.png)

### 18.3.3Nacos界面

> http://192.168.183.90:8848/nacos/index.html

1. **密码：nacos**
2. **账号：nacos**

![image-20220205170257675](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205170257675.png)

## 18.4提供者-案例

### 18.4.1依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

### 18.4.2配置文件

```yaml
server:
  port: 9001

spring:
  application:
    name: alibaba-provider-9001
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

### 18.4.3启动类

```java
package com.alibaba.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class NacosServerProvider {
    public static void main(String[] args) {
        SpringApplication.run(NacosServerProvider.class, args);
    }
}
```

![image-20220205190722186](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220205190722186.png)

## 18.5消费者-案例

### 18.5.1依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
```

### 18.5.2启动类

```java
package com.alibaba.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerServer {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerServer.class, args);
    }
}
```

### 18.5.3配置类

```java
package com.alibaba.consumer.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ConsumerConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

### 18.5.4控制层

```java
package com.alibaba.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    private final String URL = "http://nacos-provider/nacos/server";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/consumer/server")
    public String server(){
        return restTemplate.getForObject(URL,String.class);
    }

}
```

## 18.6Nacos支持AP/CP的切换

> 1. **C是所有节点在同一时间看到的数据是一致的;而A的定义是所有的请求都会收到响应。**
> 2. **何时选择使用何种模式?**
>    1. **一般来说，**
>    2. **如果不需要存储服务级别的信息且服务实例是通过nacos-client注册，并能够保持心跳上报，那么就可以选择AP模式。当前主流的服务如Spring cloud和Dubbo服务，都适用于AP模式，AP模式为了服务的可能性而减弱了一致性，因此AP模式下只支持注册临时实例。**
>    3. **如果需要在服务级别编辑或者存储配置信息，那么CP是必须，K8S服务和DNS服务则适用于CP模式。**
>       **CP模式下则支持注册持久化实例，此时则是以Raft 协议为集群运行模式，该模式下注册实例之前必须先注册服务，如果服务不存在，则会返回错误。**
> 3. **curl -x PUT '$NACOS_SERVER:8848/nacos/v1/ns/operator/switches?entry=serverMode&value=CP**

## 18.7Nacos服务配置中心

### 18.7.1依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
```

### 18.7.2Bootstrap.yml

```yaml
server:
  port: 91

spring:
  application:
    name: nacos-config-client
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848
        file-extension: yml
#        namespace: 9f255654-541f-4ff1-a14e-9cdab139c3bf
# nacos-config-client-dev.yaml
# nacos-config-client-dev.yaml
# config-client-90-dev.yaml
```

### 18.7.3Application.yml

```yaml
spring:
  profiles:
    active: dev
```

### 18.7.4启动类

```java
package com.alibaba.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConfigServer {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer.class,args );
    }
}
```

### 18.7.5控制层

```java
package com.alibaba.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigController {

    @Value("${config.info}")
    public String info;


    @GetMapping("/config/yml")
    public String yml(){
        return info;
    }
}
```

![image-20220207124150855](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207124150855.png)

![image-20220207124158077](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207124158077.png)

## 18.8Nacos服务配置-分类配置

![image-20220207135431569](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207135431569.png)

**默认情况**

> **Namespace=public,Group=Defalut_group,默认的Cluster=Default**

1. **namespace：命名空间**
2. **Group：分组**
3. **Cluster：集群环境**

### 18.8.1DataID-Case

![image-20220207140603047](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207140603047.png)

![image-20220207140629050](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207140629050.png)

![image-20220207140746690](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207140746690.png)

### 18.8.2Group-Case

![image-20220207141338315](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207141338315.png)

![image-20220207141354626](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207141354626.png)

![image-20220207141414048](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207141414048.png)

![image-20220207141441969](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207141441969.png)

### 18.8.3NameSpace-Case

![image-20220207142449539](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207142449539.png)

![image-20220207142541892](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207142541892.png)

![image-20220207142613913](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207142613913.png)

![image-20220207142637663](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207142637663.png)

## 18.9Nacos-集群和持久化配置

> **默认Nacos使用嵌入式数据库实现数据的存储。所以，如果启动多个默认配置下的Nacos节点，数据存储是存在一致性问题的.为了解决这个问题，Nacos采用了集中式存储的方式来支持集群化部署，目前只支持MySQL的存储。**

### 18.9.1Nacos持久化集中式配置-切换Derby成Mysql

![image-20220207144849274](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207144849274.png)

![image-20220207145258589](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207145258589.png)

![image-20220207145648841](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207145648841.png)

![image-20220207145946949](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207145946949.png)

![image-20220207145959349](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207145959349.png)

### 18.9.2Linux-Nacos

![image-20220207154245425](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207154245425.png)

![image-20220207154348274](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207154348274.png)

#### 18.9.2.1下载Mysql

> [完美解决CentOS8 yum安装AppStream报错，更新yum后无法makecache的问题 - 白_胖_子 - 博客园 (cnblogs.com)](https://www.cnblogs.com/bpzblog/p/13918199.html)

![image-20220207161410813](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207161410813.png)

![image-20220207161543755](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207161543755.png)

#### 18.9.2.2配置Nacos

![image-20220207175442996](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207175442996.png)

![image-20220207175636827](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207175636827.png)

![image-20220207175827528](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207175827528.png)

![image-20220207194434013](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207194434013.png)

![image-20220207194702634](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207194702634.png)

#### 18.9.2.3运行Nacos集群

![image-20220207194941873](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207194941873.png)

#### 18.9.2.4配置Nginx

![image-20220207202320900](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207202320900.png)

![image-20220207202332378](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220207202332378.png)

#### 18.9.2.5启动Nginx

![image-20220208115050693](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208115050693.png)

#### 18.9.2.6访问Nacos

![image-20220208124128994](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208124128994.png)

![image-20220208124331367](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208124331367.png)

![image-20220208124809747](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208124809747.png)

![image-20220208124925662](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208124925662.png)

# 19.SpringCloudAlibaba-Sentinel(熔断和限流)

## 19.1Sentinel是什么

> - **丰富的应用场景**：Sentinel 承接了阿里巴巴近 10 年的双十一大促流量的核心场景，例如秒杀（即突发流量控制在系统容量可以承受的范围）、消息削峰填谷、集群流量控制、实时熔断下游不可用应用等。
> - **完备的实时监控**：Sentinel 同时提供实时的监控功能。您可以在控制台中看到接入应用的单台机器秒级数据，甚至 500 台以下规模的集群的汇总运行情况。
> - **广泛的开源生态**：Sentinel 提供开箱即用的与其它开源框架/库的整合模块，例如与 Spring Cloud、Apache Dubbo、gRPC、Quarkus 的整合。您只需要引入相应的依赖并进行简单的配置即可快速地接入 Sentinel。同时 Sentinel 提供 Java/Go/C++ 等多语言的原生实现。
> - **完善的 SPI 扩展机制**：Sentinel 提供简单易用、完善的 SPI 扩展接口。您可以通过实现扩展接口来快速地定制逻辑。例如定制规则管理、适配动态数据源等。

![image-20220208131031503](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208131031503.png)![image-20220208131038512](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208131038512.png)

## 19.2Sentinel下载

> [Releases · alibaba/Sentinel (github.com)](https://github.com/alibaba/Sentinel/releases)

## 19.3启动Sentinel

![image-20220208132250189](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208132250189.png)

## 19.4访问Sentinel

1. **账号：sentinel**
2. **密码：sentinel**

![image-20220208132349627](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208132349627.png)

## 19.5初始化工程

### 19.5.1依赖

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>SpringCloud</artifactId>
        <groupId>org.spring.cloud</groupId>
        <version>1.0-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>sprincloud-alibaba-sentinel-9003</artifactId>

    <dependencies>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
    </dependencies>


</project>
```

### 19.5.2配置i文件

```yaml
server:
  port: 9003

spring:
  application:
    name: sentinel-nacos-9003
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    sentinel:
      transport:
        port: 8719
        dashboard: localhost:8080

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

### 19.5.3Nacos和Sentinel界面

![image-20220208144451426](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208144451426.png)

![image-20220208144518762](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208144518762.png)

## 19.6流控规则

### 19.6.1介绍

![image-20220208144939752](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208144939752.png)

> - **资源名:唯一名称，默认请求路径**
>   **·针对来源: Sentinel可以针对调用者进行限流，填写微服务名，默认default (不区分来源)·阈值类型/单机阈值:**
>   - **QPS(每秒钟的请求数量)∶当调用该api的QPS达到阈值的时候，进行限流。**
>   - **线程数:当调用该api的线程数达到阈值的时候，进行限流**
> - **是否集群:不需要集群**
>   **心**
> - **流控模式:**
>   - **直接: api达到限流条件时，直接限流**
>   - **关联:当关联的资源达到阈值时，就限流自己**
>   - **链路:只记录指定链路上的流量（指定资源从入口资源进来的流量，如果达到阈值，就进行限流)【api级别的针对来源】**
> - **·流控效果:**
>   - **快速失败:直接失败，抛异常**
>   - **Warm Up:根据codeFactor (冷加载因子，默认3)的值，从阈值/codeFactor，经过预热时长，才达到设置的QPS阈值**
>   - **排队等待:匀速排队，让请求以匀速的速度通过，阈值类型必须设置为QPS，否则无效**

### 19.6.2流控模式

#### 19.6.2.1直接(默认)

![image-20220208145734696](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208145734696.png)

![image-20220208145751837](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208145751837.png)

#### 19.6.2.2关联

> **当与A关联的资源B达到峰值后，就会限流A自己**

![image-20220208154441665](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208154441665.png)

![image-20220208154731957](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208154731957.png)

![image-20220208154745769](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208154745769.png)

![image-20220208154805082](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208154805082.png)

#### 19.6.2.3链路

> **多个请求调用同一个服务**
>
> [(70条消息) SpringCloud学习-part54 sentinel流控之链路_Ezerbel的博客-CSDN博客_sentinel链路流控](https://blog.csdn.net/Ezerbel/article/details/107919230?ops_request_misc=%7B%22request%5Fid%22%3A%22164430666216780269834334%22%2C%22scm%22%3A%2220140713.130102334.pc%5Fblog.%22%7D&request_id=164430666216780269834334&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_ecpm_v1~rank_v31_ecpm-6-107919230.nonecase&utm_term=++++++++++++++++sentinel链路流控++++++++++++++++++++++++++++++&spm=1018.2226.3001.4450)

```java
    @GetMapping("/sentinel/test")
    public String test(){
        return testA();
    }   
//  指定资源名，并指定降级方法
    @SentinelResource(value = "testA", blockHandler = "testAFallback")
    public String testA() {
        return "testA";
    }

    public String testAFallback(){
        return "testAFallback";
    }
```

![image-20220208155526427](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208155526427.png)

## 19.7流控效果

### 19.7.1预热

![image-20220208161021514](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208161021514.png)

### 19.7.2排队等待

> **匀速排队（RuleConstant.CONTROL_BEHAVIOR_RATE_LIMITER）方式会严格控制请求通过的间隔时间，也即是让请求以均匀的速度通过，对应的是漏桶算法。阈值必须设置为QPS。**
>
> **这种方式主要用于处理间隔性突发的流量，例如消息队列。想象一下这样的场景，在某一秒有大量的请求到来，而接下来的几秒则处于空闲状态，我们希望系统能够在接下来的空闲期间逐渐处理这些请求，而不是在第一秒直接拒绝多余的请求。**

![image-20220208163347077](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208163347077.png)

## 19.8降级规则

### 19.8.1熔断降级

> **除了流量控制以外，对调用链路中不稳定的资源进行熔断降级也是保障高可用的重要措施之一。一个服务常常会调用别的模块，可能是另外的一个远程服务、数据库，或者第三方 API 等。例如，支付的时候，可能需要远程调用银联提供的 API；查询某个商品的价格，可能需要进行数据库查询。然而，这个被依赖服务的稳定性是不能保证的。如果依赖的服务出现了不稳定的情况，请求的响应时间变长，那么调用服务的方法的响应时间也会变长，线程会产生堆积，最终可能耗尽业务自身的线程池，服务本身也变得不可用。**

![image-20220208163417007](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208163417007.png)

![image-20220208163924207](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208163924207.png)

> 1. **RT(平均响应时间，秒级)**
>    1. **平均响应时间超出阈值且在时间窗口内通过的清求>=5。两个条件同时满足后触发降级窗口期过后关闭断路器**
>       **RT最大4900(更大的需要通过-Dcsp.sentinel.statistic.max.rt=XXXX才能生效)**
> 2. **异常比列(秒级)**
>    1. **QPs >=5且异常比例（秒级统计）超过阈值时，触发降级;时间窗口结束后，关闭降级**
> 3. **异常数(分钟级)**
>    1. **异常数(分钟统计）都过阈值时，触发降级;时间窗口结束后，关闭降级**

### 19.8.2降级实战-RT

> **慢调用比例 (`SLOW_REQUEST_RATIO`)：选择以慢调用比例作为阈值，需要设置允许的慢调用 RT（即最大的响应时间），请求的响应时间大于该值则统计为慢调用。当单位统计时长（`statIntervalMs`）内请求数目大于设置的最小请求数目，并且慢调用的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求响应时间小于设置的慢调用 RT 则结束熔断，若大于设置的慢调用 RT 则会再次被熔断。**

```java
@GetMapping("/sentinel/test3")
public String test3() throws InterruptedException {
    Thread.sleep(2000);
    return "test3";
}
```

![image-20220208201622565](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208201622565.png)

![image-20220208201802549](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208201802549.png)

![image-20220208202103331](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208202103331.png)

![image-20220208202158394](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208202158394.png)

![image-20220208202211442](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208202211442.png)

### 19.8.3降级实战-异常比例

> **异常比例 (`ERROR_RATIO`)：当单位统计时长（`statIntervalMs`）内请求数目大于设置的最小请求数目，并且异常的比例大于阈值，则接下来的熔断时长内请求会自动被熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。异常比率的阈值范围是 `[0.0, 1.0]`，代表 0% - 100%。**

```java
@GetMapping("/sentinel/test4")
public String test4(){
    int in = 10/0;
    return "Test4";
}
```

![image-20220208203140150](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208203140150.png)

![image-20220208203213850](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208203213850.png)

![image-20220208203242396](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208203242396.png)

![image-20220208203253063](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208203253063.png)

### 19.8.4降级实战-异常数

> **异常数 (`ERROR_COUNT`)：当单位统计时长内的异常数目超过阈值之后会自动进行熔断。经过熔断时长后熔断器会进入探测恢复状态（HALF-OPEN 状态），若接下来的一个请求成功完成（没有错误）则结束熔断，否则会再次被熔断。**

```java
@GetMapping("/sentinel/test4")
public String test4(){
    int in = 10/0;
    return "Test4";
}
```

![image-20220208203805977](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208203805977.png)

![image-20220208203821125](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208203821125.png)

## 19.9热点KEY限流

> **何为热点？热点即经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的 Top K 数据，并对其访问进行限制。比如：**
>
> - **商品 ID 为参数，统计一段时间内最常购买的商品 ID 并进行限制**
> - **用户 ID 为参数，针对一段时间内频繁访问的用户 ID 进行限制**
>
> **热点参数限流会统计传入参数中的热点参数，并根据配置的限流阈值与模式，对包含热点参数的资源调用进行限流。热点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效。**
>
> **Sentinel 利用 LRU 策略统计最近最常访问的热点参数，结合令牌桶算法来进行参数级别的流控。热点参数限流支持集群模式。**

![image-20220208204013205](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208204013205.png)

![image-20220208204045035](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208204045035.png)

### 19.9.1实战

```java
@GetMapping("/sentinel/test5")
@SentinelResource(value = "test5",blockHandler = "dealHandler")
public String test5(
        @RequestParam(value = "p1",required = false) String p1,
        @RequestParam(value = "p2",required = false) String p2
){
    return "test5";
}

public String dealHandler(String p1,String p2,BlockException blockException){
    return "dealHandler";
}
```

![image-20220208210649646](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208210649646.png)

![image-20220208210656319](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208210656319.png)

### 19.9.1参数例外项

![image-20220208211939546](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208211939546.png)

![image-20220208212007213](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220208212007213.png)

## 20.系统规则

### 20.1介绍

> **Sentinel系统自适应限流从整体维度对应用入口流量进行控制，结合应用的Logd、CPU使用率、总体平均RT、入口QPS和并发线程数等几个维度的监控指标，通过自适应的流控策略，让系统的入口流量和系统的负载达到一个平衡，让系统尽可能跑在最大吞吐量的同时保证系统整体的稳定性。**

### 20.2支持的模式

> - **Load自适应**（仅对Linux/Unix-like机器生效):系统的 load1作为启发指标，进行自适应系统保护。当系统 load1超过设定的启发值，且系统当前的并发线程数超过估算的系统容量时才会触发系统保护（BBR阶段)。系统容量由系统的 maxQps " minRt估算得出。设定参考值一般是cpu cores " 2.5。
> - **cpu usage (1.5.0+版本)**︰当系统CPU使用率超过阈值即触发系统保护（取值范围0.0-1.0)，比较灵敏。
> - **平均RT**:当单台机器上所有入口流量的平均RT达到阈值即触发系统保护。单位是毫秒。并发线程数:当单台机器上所有入口流量的
> - **并发线程数达到阈值即触发系统保护。**
> - **入口QPS**:当单台机器上所有入口流量的 QPS达到阈值即触发系统保护。

### 20.3测试

![image-20220209135454590](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209135454590.png)

![image-20220209135514858](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209135514858.png)

## 20.1@SentinelResoucre

### 20.1.1按资源名称限流和处理

```java
@GetMapping("/sentinel/test5")
@SentinelResource(value = "test5",blockHandler = "dealHandler")
public String test5(
        @RequestParam(value = "p1",required = false) String p1,
        @RequestParam(value = "p2",required = false) String p2
){
    return "test5";
}

public String dealHandler(String p1,String p2,BlockException blockException){
    return "dealHandler";
}
```

![image-20220209141741642](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209141741642.png)

![image-20220209141757231](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209141757231.png)

### 20.1.2按URL限流

```java
@GetMapping("/sentinel/test6")
@SentinelResource(value = "test6")
public String test6(){
    return "test6";
}
```

![image-20220209141924613](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209141924613.png)

![image-20220209142012567](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209142012567.png)

### 20.1.3面临的问题

> 1. **系统默认的，没有体现我们自己的业务要求。**
> 2. **依照现有条件，我们自定义的处理方法又和业务代码耦合在一块，不直观。**
> 3. **每个业务方法都添加一个兜底的，那代码膨胀加剧。**
> 4. **全局统—的处理方法没有体现.**

### 20.1.4客户自定义

```java
@GetMapping("/sentinel/test7")
@SentinelResource(
        value = "customerHandler",
        blockHandlerClass = CustomerHandler.class,
        blockHandler = "customerMethod2"
)
public String test7(){
    return "test7";
}
```

```java
package com.alibaba.sentinel.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public class CustomerHandler {

    public static String customerMethod(BlockException e){
        return "customerMethod 1";
    }

    public static String customerMethod2(BlockException e){
        return "customerMethod 2";
    }
}

```

![image-20220209143501060](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209143501060.png)![image-20220209143508315](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209143508315.png)

### 20.1.5更多注解的使用

1. **@SentinelResource 注解**

> **注意：注解方式埋点不支持 private 方法。**

> `@SentinelResource` 用于定义资源，并提供可选的异常处理和 fallback 配置项。 `@SentinelResource` 注解包含以下属性：
>
> - `value`：资源名称，必需项（不能为空）
> - `entryType`：entry 类型，可选项（默认为 `EntryType.OUT`）
> - `blockHandler` / `blockHandlerClass`: `blockHandler` 对应处理 `BlockException` 的函数名称，可选项。blockHandler 函数访问范围需要是 `public`，返回类型需要与原方法相匹配，参数类型需要和原方法相匹配并且最后加一个额外的参数，类型为 `BlockException`。blockHandler 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `blockHandlerClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。
> - fallback/fallbackClass：fallback 函数名称，可选项，用于在抛出异常的时候提供 fallback 处理逻辑。fallback 函数可以针对所有类型的异常（除了exceptionsToIgnore里面排除掉的异常类型）进行处理。fallback 函数签名和位置要求：
>   - 返回值类型必须与原函数返回值类型一致；
>   - 方法参数列表需要和原函数一致，或者可以额外多一个 `Throwable` 类型的参数用于接收对应的异常。
>   - fallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `fallbackClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。
> - defaultFallback（since 1.6.0）：默认的 fallback 函数名称，可选项，通常用于通用的 fallback 逻辑（即可以用于很多服务或方法）。默认 fallback 函数可以针对所有类型的异常（除了exceptionsToIgnore里面排除掉的异常类型）进行处理。若同时配置了 fallback 和 defaultFallback，则只有 fallback 会生效。defaultFallback 函数签名要求：
>   - 返回值类型必须与原函数返回值类型一致；
>   - 方法参数列表需要为空，或者可以额外多一个 `Throwable` 类型的参数用于接收对应的异常。
>   - defaultFallback 函数默认需要和原方法在同一个类中。若希望使用其他类的函数，则可以指定 `fallbackClass` 为对应的类的 `Class` 对象，注意对应的函数必需为 static 函数，否则无法解析。
> - `exceptionsToIgnore`（since 1.6.0）：用于指定哪些异常被排除掉，不会计入异常统计中，也不会进入 fallback 逻辑中，而是会原样抛出。

> 1.8.0 版本开始，`defaultFallback` 支持在类级别进行配置。
>
> > 注：1.6.0 之前的版本 fallback 函数只针对降级异常（`DegradeException`）进行处理，**不能针对业务异常进行处理**。
>
> 特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 `BlockException` 时只会进入 `blockHandler` 处理逻辑。若未配置 `blockHandler`、`fallback` 和 `defaultFallback`，则被限流降级时会将 `BlockException` **直接抛出**（若方法本身未定义 throws BlockException 则会被 JVM 包装一层 `UndeclaredThrowableException`）。

**Sentinel三个核心API**

1. **Sphu：定义资源**
2. **Tracer：定义统计**
3. **ContextUtil：定义上下文**

## 20.2服务熔断功能

### 20.2.1整合Ribbon、OpenFeign、fallback

### 20.2.2Ribbon系列

#### 20.2.2.1依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### 20.2.2.2配置

```java
package com.alibaba.sentinel.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SentinelConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
```

```yaml
server:
  port: 9004

spring:
  application:
    name: sentinel-nacos-privoder
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    sentinel:
      transport:
        port: 8719
        dashboard: localhost:8080

management:
  endpoints:
    web:
      exposure:
        include: "*"
```

#### 20.2.2.3消费端控制层

```java
package com.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.IllegalFormatException;

@RestController
public class SentinelRibbonController {

    @Autowired
    private RestTemplate restTemplate;

    private static String URL="http://sentinel-nacos-privoder/sentinel/client/test";


    @GetMapping("/sentinel/ribbon/test")
    @SentinelResource(value = "coustomerException")
    public String test(@RequestParam(value = "p1",required = false) String p1){
        if (p1 != null){
            if (p1.equals("1")){
                throw new IllegalArgumentException("参数异常");
            }
        }
        return restTemplate.getForObject(URL, String.class);
    }
}
```

#### 20.2.2.4注册服务

![image-20220209151301135](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209151301135.png)

![image-20220209151513132](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209151513132.png)

#### 20.2.2.5测试

![image-20220209151617782](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209151617782.png)

![image-20220209151626971](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209151626971.png)

![image-20220209153014178](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209153014178.png)

#### 20.2.2.6修改消费端代码-处理Java业务逻辑异常

```java
package com.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.IllegalFormatException;

@RestController
public class SentinelRibbonController {

    @Autowired
    private RestTemplate restTemplate;

    private static String URL="http://sentinel-nacos-privoder/sentinel/client/test";


    @GetMapping("/sentinel/ribbon/test")
    @SentinelResource(value = "fallbackException",fallback = "fallbackHandler")
    public String test(@RequestParam(value = "p1",required = false) String p1){
        if (p1 != null){
            if (p1.equals("1")){
                throw new IllegalArgumentException("参数异常");
            }
        }
        return restTemplate.getForObject(URL, String.class);
    }


    public String fallbackHandler(String p1,Throwable e){
       return "参数异常";
    }
}
```

![image-20220209153832038](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209153832038.png)

#### 20.2.2.7修改消费者代码2

```java
@GetMapping("/sentinel/ribbon/test")
@SentinelResource(
        value = "fallbackException",
        fallback = "fallbackHandler",
        blockHandler = "blockHandler"
)
public String test(@RequestParam(value = "p1",required = false) String p1){
    if (p1 != null){
        if (p1.equals("1")){
            throw new IllegalArgumentException("参数异常");
        }
    }
    return restTemplate.getForObject(URL, String.class);
}


public String fallbackHandler(String p1,Throwable e){
   return "参数异常";
}

public String blockHandler(String p1, BlockException e){
    return "blockHandler";
}
```

![image-20220209155537873](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209155537873.png)![image-20220209155549801](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209155549801.png)

> **FallBack:负责Java运行异常的兜底的方法**
>
> **BlockHandler：负责sentinel规则异常的兜底方法**

#### 20.2.2.8exceptionsToIgnore属性

![image-20220209160123892](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209160123892.png)

### 20.2.3Feign系列

#### 20.2.3.1依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### 20.2.3.2配置

```yaml
feign:
  sentinel:
    enabled: true
```

#### 20.2.3.3启动类

```java
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SentinelRibbonServer {
    public static void main(String[] args) {
        SpringApplication.run(SentinelRibbonServer.class, args);
    }
}
```

#### 20.2.3.4service

```java
package com.alibaba.sentinel.openfeign;

import com.alibaba.sentinel.openfeign.service.OpenFeignServiceimpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "sentinel-nacos-privoder",fallback = OpenFeignServiceimpl.class)
public interface OpenFeignService {

    @GetMapping("/sentinel/client/test")
    public String test();
}

@Component
public class OpenFeignServiceimpl implements OpenFeignService {
    @Override
    public String test() {
        return "error";
    }
}

```

#### 20.2.3.5控制层

```java
package com.alibaba.sentinel.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.sentinel.openfeign.OpenFeignService;
import com.sun.deploy.security.BlockedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.IllegalFormatException;

@RestController
public class SentinelRibbonController {

//      OpenFeign
    @Autowired
    private OpenFeignService openFeignService;


    @GetMapping("/openFeign/test")
    public String test(){
      return  openFeignService.test();
    }
}
```

#### 20.2.3.6测试

![image-20220209161944755](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209161944755.png)

![image-20220209161958962](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209161958962.png)

## 20.3规则持久化

### 20.3.1介绍

> **一旦重启应用，sentinel的规则全部消失**
>
> **上述 `loadRules()` 方法只接受内存态的规则对象，但更多时候规则存储在文件、数据库或者配置中心当中。`DataSource` 接口给我们提供了对接任意配置源的能力。相比直接通过 API 修改规则，实现 `DataSource` 接口是更加可靠的做法。**

### 20.3.2实操

#### 20.3.2.1依赖

```xml
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-nacos</artifactId>
</dependency>
```

#### 20.3.2.2配置文件

```yaml
spring:
  application:
    name: sentinel-nacos-consumer
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
    sentinel:
      transport:
        port: 8719
        dashboard: localhost:8080
      datasource:
        ds1:
          nacos:
            serverAddr: localhost:8848
            dataId: ${spring.application.name}
            groupId: DEFAULT_GROUP
            dataType: json
            ruleType: flow
```

#### 20.3.2.3nacos配置

![image-20220209171906004](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209171906004.png)

```json
[
    {
        "resource": "fallbackException",
        "limitApp": "default",
        "grade": 1,
        "count": 5,
        "strategy": 0,
        "controlBehavior": 0,
        "clusterMode": false
    }
]
```

# 20.Springcloud-alibaba-Seata(处理分布式事务)

## 20.1问题

> 1. **单机单库**
>    1. **N对一**
>    2. **一对N**
>    3. **N对N**

## 20.2介绍

> **[分布式事务](https://so.csdn.net/so/search?q=分布式事务&spm=1001.2101.3001.7020)的产生，是由于数据库的拆分和分布式架构(微服务)带来的，在常规情况下，我们在一个进程中操作一个数据库，这属于本地事务，如果在一个进程中操作多个数据库，或者在多个进程中操作一个或多个数据库，就产生了分布式事务；**
>
> **Seata 是一款开源的分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务。Seata 将为用户提供了 AT、TCC、SAGA 和 XA 事务模式，为用户打造一站式的分布式解决方案**

## 20.3官网

> [Seata 是什么](http://seata.io/zh-cn/docs/overview/what-is-seata.html)

## 20.4术语

> 1. **TC (Transaction Coordinator) - 事务协调者**
>
>    1. 维护全局和分支事务的状态，驱动全局事务提交或回滚。
>
> 2. **TM (Transaction Manager) - 事务管理器**
>
>    ​	1.定义全局事务的范围：开始全局事务、提交或回滚全局事务。
>
> 3. **RM (Resource Manager) - 资源管理器**
>
>    1. 管理分支事务处理的资源，与TC交谈以注册分支事务和报告分支事务的状态，并驱动分支事务提交或回滚。  

![image-20220209173919158](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209173919158.png)

> - **TM向TC申请开启一个全局事务，全局事务创建成功并生成一个全局唯一的XID;**
> - **XID在微服务调用链路的上下文中传播;**
> - **RM向TC注册分支事务，将其纳入XID对应全局事务的管辖;TM向**
> - **TC发起针对XID的全局提交或回滚决议;**
> - **TC调度XID下管辖的全部分支事务完成提交或回滚请求。**

## 20.5下载

> [下载中心 (seata.io)](http://seata.io/zh-cn/blog/download.html)

## 20.6更改Seata配置文件

![image-20220209180224473](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209180224473.png)

![image-20220209180406223](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209180406223.png)

![image-20220209180419750](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209180419750.png)

```sql
-- -------------------------------- The script used when storeMode is 'db' --------------------------------
-- the table to store GlobalSession data
CREATE TABLE IF NOT EXISTS `global_table`
(
    `xid`                       VARCHAR(128) NOT NULL,
    `transaction_id`            BIGINT,
    `status`                    TINYINT      NOT NULL,
    `application_id`            VARCHAR(32),
    `transaction_service_group` VARCHAR(32),
    `transaction_name`          VARCHAR(128),
    `timeout`                   INT,
    `begin_time`                BIGINT,
    `application_data`          VARCHAR(2000),
    `gmt_create`                DATETIME,
    `gmt_modified`              DATETIME,
    PRIMARY KEY (`xid`),
    KEY `idx_gmt_modified_status` (`gmt_modified`, `status`),
    KEY `idx_transaction_id` (`transaction_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- the table to store BranchSession data
CREATE TABLE IF NOT EXISTS `branch_table`
(
    `branch_id`         BIGINT       NOT NULL,
    `xid`               VARCHAR(128) NOT NULL,
    `transaction_id`    BIGINT,
    `resource_group_id` VARCHAR(32),
    `resource_id`       VARCHAR(256),
    `branch_type`       VARCHAR(8),
    `status`            TINYINT,
    `client_id`         VARCHAR(64),
    `application_data`  VARCHAR(2000),
    `gmt_create`        DATETIME,
    `gmt_modified`      DATETIME,
    PRIMARY KEY (`branch_id`),
    KEY `idx_xid` (`xid`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
-- the table to store lock data
CREATE TABLE IF NOT EXISTS `lock_table`
(
    `row_key`        VARCHAR(128) NOT NULL,
    `xid`            VARCHAR(96),
    `transaction_id` BIGINT,
    `branch_id`      BIGINT       NOT NULL,
    `resource_id`    VARCHAR(256),
    `table_name`     VARCHAR(32),
    `pk`             VARCHAR(36),
    `gmt_create`     DATETIME,
    `gmt_modified`   DATETIME,
    PRIMARY KEY (`row_key`),
    KEY `idx_branch_id` (`branch_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8;
```

![image-20220209181344000](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209181344000.png)

![image-20220209182144971](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209182144971.png)

![image-20220209182455451](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209182455451.png)

## 20.7运行Seata

![image-20220209182832179](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209182832179.png)

## 20.8项目前配置

> - **仓储服务：对给定的商品扣除仓储数量。**
> - **订单服务：根据采购需求创建订单。**
> - **帐户服务：从用户帐户中扣除余额。**

![image-20220209183041265](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209183041265.png)

### 20.8.1建立业务表

```sql
Create DATABASE seata_order;
CREATE DATABASE seata_storage;
CREATE DATABASE seata_account;
```

```sql
CREATE TABLE t_order(
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` BIGINT(11) DEFAULT NULL COMMENT '用户id',
    `product_id` BIGINT(11) DEFAULT NULL COMMENT '产品id',
    `count` INT(11) DEFAULT NULL COMMENT '数量',
    `money` DECIMAL(11,0) DEFAULT NULL COMMENT '金额',
    `status` INT(1) DEFAULT NULL COMMENT '订单状态：0：创建中; 1：已完结'
) ENGINE=INNODB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
 
SELECT * FROM t_order;
```

```sql
CREATE TABLE t_storage(
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `product_id` BIGINT(11) DEFAULT NULL COMMENT '产品id',
    `total` INT(11) DEFAULT NULL COMMENT '总库存',
    `used` INT(11) DEFAULT NULL COMMENT '已用库存',
    `residue` INT(11) DEFAULT NULL COMMENT '剩余库存'
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
 
INSERT INTO seata_storage.t_storage(`id`,`product_id`,`total`,`used`,`residue`)
VALUES('1','1','100','0','100');
 
SELECT * FROM t_storage;
```

```sql
CREATE TABLE t_account(
    `id` BIGINT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'id',
    `user_id` BIGINT(11) DEFAULT NULL COMMENT '用户id',
    `total` DECIMAL(10,0) DEFAULT NULL COMMENT '总额度',
    `used` DECIMAL(10,0) DEFAULT NULL COMMENT '已用余额',
    `residue` DECIMAL(10,0) DEFAULT '0' COMMENT '剩余可用额度'
) ENGINE=INNODB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
 
INSERT INTO seata_account.t_account(`id`,`user_id`,`total`,`used`,`residue`) VALUES('1','1','1000','0','1000')

SELECT * FROM t_account;
```

![image-20220209183356612](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209183356612.png)

![image-20220209183646759](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209183646759.png)

### 20.8.2创建日志表

```sql
-- 注意此处0.3.0+ 增加唯一索引 ux_undo_log
CREATE TABLE `undo_log`
(
    `id`            bigint(20) NOT NULL AUTO_INCREMENT,
    `branch_id`     bigint(20) NOT NULL,
    `xid`           varchar(100) NOT NULL,
    `context`       varchar(128) NOT NULL,
    `rollback_info` longblob     NOT NULL,
    `log_status`    int(11) NOT NULL,
    `log_created`   datetime     NOT NULL,
    `log_modified`  datetime     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `ux_undo_log` (`xid`,`branch_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
```

![image-20220209184349239](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209184349239.png)

### 20.8.3创建config.txt

```tex
#For details about configuration items, see https://seata.io/zh-cn/docs/user/configurations.html
#Transport configuration, for client and server
transport.type=TCP
transport.server=NIO
transport.heartbeat=true
transport.enableTmClientBatchSendRequest=false
transport.enableRmClientBatchSendRequest=true
transport.enableTcServerBatchSendResponse=false
transport.rpcRmRequestTimeout=30000
transport.rpcTmRequestTimeout=30000
transport.rpcTcRequestTimeout=30000
transport.threadFactory.bossThreadPrefix=NettyBoss
transport.threadFactory.workerThreadPrefix=NettyServerNIOWorker
transport.threadFactory.serverExecutorThreadPrefix=NettyServerBizHandler
transport.threadFactory.shareBossWorker=false
transport.threadFactory.clientSelectorThreadPrefix=NettyClientSelector
transport.threadFactory.clientSelectorThreadSize=1
transport.threadFactory.clientWorkerThreadPrefix=NettyClientWorkerThread
transport.threadFactory.bossThreadSize=1
transport.threadFactory.workerThreadSize=default
transport.shutdown.wait=3
transport.serialization=seata
transport.compressor=none

#Transaction routing rules configuration, only for the client
service.vgroupMapping.default_tx_group=default
#If you use a registry, you can ignore it
service.default.grouplist=127.0.0.1:8091
service.enableDegrade=false
service.disableGlobalTransaction=false

#Transaction rule configuration, only for the client
client.rm.asyncCommitBufferLimit=10000
client.rm.lock.retryInterval=10
client.rm.lock.retryTimes=30
client.rm.lock.retryPolicyBranchRollbackOnConflict=true
client.rm.reportRetryCount=5
client.rm.tableMetaCheckEnable=false
client.rm.tableMetaCheckerInterval=60000
client.rm.sqlParserType=druid
client.rm.reportSuccessEnable=false
client.rm.sagaBranchRegisterEnable=false
client.rm.sagaJsonParser=fastjson
client.rm.tccActionInterceptorOrder=-2147482648
client.rm.sqlParserType=druid
client.tm.commitRetryCount=5
client.tm.rollbackRetryCount=5
client.tm.defaultGlobalTransactionTimeout=60000
client.tm.degradeCheck=false
client.tm.degradeCheckAllowTimes=10
client.tm.degradeCheckPeriod=2000
client.tm.interceptorOrder=-2147482648
client.undo.dataValidation=true
client.undo.logSerialization=jackson
client.undo.onlyCareUpdateColumns=true
server.undo.logSaveDays=7
server.undo.logDeletePeriod=86400000
client.undo.logTable=undo_log
client.undo.compress.enable=true
client.undo.compress.type=zip
client.undo.compress.threshold=64k
#For TCC transaction mode
tcc.fence.logTableName=tcc_fence_log
tcc.fence.cleanPeriod=1h

#Log rule configuration, for client and server
log.exceptionRate=100

#Transaction storage configuration, only for the server. The file, DB, and redis configuration values are optional.
store.mode=file
store.lock.mode=file
store.session.mode=file
#Used for password encryption
store.publicKey=

#If `store.mode,store.lock.mode,store.session.mode` are not equal to `file`, you can remove the configuration block.
store.file.dir=file_store/data
store.file.maxBranchSessionSize=16384
store.file.maxGlobalSessionSize=512
store.file.fileWriteBufferCacheSize=16384
store.file.flushDiskMode=async
store.file.sessionReloadReadSize=100

#These configurations are required if the `store mode` is `db`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `db`, you can remove the configuration block.
store.db.datasource=druid
store.db.dbType=mysql
store.db.driverClassName=com.mysql.jdbc.Driver
store.db.url=jdbc:mysql://127.0.0.1:3306/seata?useUnicode=true&rewriteBatchedStatements=true
store.db.user=username
store.db.password=password
store.db.minConn=5
store.db.maxConn=30
store.db.globalTable=global_table
store.db.branchTable=branch_table
store.db.distributedLockTable=distributed_lock
store.db.queryLimit=100
store.db.lockTable=lock_table
store.db.maxWait=5000

#These configurations are required if the `store mode` is `redis`. If `store.mode,store.lock.mode,store.session.mode` are not equal to `redis`, you can remove the configuration block.
store.redis.mode=single
store.redis.single.host=127.0.0.1
store.redis.single.port=6379
store.redis.sentinel.masterName=
store.redis.sentinel.sentinelHosts=
store.redis.maxConn=10
store.redis.minConn=1
store.redis.maxTotal=100
store.redis.database=0
store.redis.password=
store.redis.queryLimit=100

#Transaction rule configuration, only for the server
server.recovery.committingRetryPeriod=1000
server.recovery.asynCommittingRetryPeriod=1000
server.recovery.rollbackingRetryPeriod=1000
server.recovery.timeoutRetryPeriod=1000
server.maxCommitRetryTimeout=-1
server.maxRollbackRetryTimeout=-1
server.rollbackRetryTimeoutUnlockEnable=false
server.distributedLockExpireTime=10000
server.session.branchAsyncQueueSize=5000
server.session.enableBranchAsyncRemove=true

#Metrics configuration, only for the server
metrics.enabled=false
metrics.registryType=compact
metrics.exporterList=prometheus
metrics.exporterPrometheusPort=9898
```

![image-20220209190221819](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209190221819.png)

### 20.8.4创建config.py

```python
#!/usr/bin/env python3
#  -*- coding: UTF-8 -*-

import http.client
import sys
import getopt as opts
import urllib.parse
import re


def get_params() -> dict:
    params = {
        '-h': '127.0.0.1',
        '-p': '8848',
        '-t': '',
        '-g': 'SEATA_GROUP',
        '-u': '',
        '-w': ''
    }
    inputs, args = opts.getopt(sys.argv[1:], shortopts='h:p:t:g:u:w:')
    for k, v in inputs:
        params[k] = v
    print(params)
    return params

def error_exit():
    print('python nacos-config.py [-h host] [-p port] [-t tenant] [-g group] [-u username] [-w password]')
    exit()

def get_pair(line: str) -> tuple:
    res = re.match(r"([\.\w]+)=(.*)",line)
    return res.groups() if res is not None else ['','']


headers = {
    'content-type': "application/x-www-form-urlencoded"
}

hasError = False

params = get_params()

url_prefix = f"{params['-h']}:{params['-p']}"
tenant = params['-t']
username = params['-u']
password = params['-w']
group = params['-g']
url_postfix_base = f'/nacos/v1/cs/configs?group={group}&tenant={tenant}'

if username != '' and password != '':
    url_postfix_base += f'&username={username}&password={password}'

if url_prefix == ':':
    error_exit()

for line in open('F:/SpringCloudResources/seata/config.txt'):
    pair = get_pair(line.rstrip("\n"))
    if len(pair) < 2 or pair[0] == '' or pair[0].startswith("#") or pair[1] == '':
        continue
    url_postfix = url_postfix_base + f'&dataId={urllib.parse.quote(str(pair[0]))}&content={urllib.parse.quote(str(pair[1])).strip()}'
    conn = http.client.HTTPConnection(url_prefix)
    conn.request("POST", url_postfix, headers=headers)
    res = conn.getresponse()
    data = res.read().decode("utf-8")
    if data != "true":
        hasError = True
    print(f"{pair[0]}={pair[1]} {data if hasError else 'success'}")

if hasError:
    print("init nacos config fail.")
else:
    print("init nacos config finished, please start seata-server.")
```

![image-20220209191701185](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209191701185.png)

### 20.8.5查看Nacos

![image-20220209191730603](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220209191730603.png)

## 20.9Order-Stroage-Account模块-代码实战

### 20.9.1依赖

```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
</dependency>
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
    <exclusions>
        <exclusion>
            <groupId>io.seata</groupId>
            <artifactId>seata-all</artifactId>
        </exclusion>
        <exclusion>
            <groupId>io.seata</groupId>
            <artifactId>seata-spring-boot-starter</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-all</artifactId>
    <version>1.4.1</version>
</dependency>
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-spring-boot-starter</artifactId>
    <version>1.4.1</version>
</dependency>
```

### 20.9.2配置文件

```yaml
server:
  port: 2001
spring:
  application:
    name: seata-order
  cloud:
    nacos:
      discovery:
        server-addr: localhost:8848
      config:
        server-addr: localhost:8848
    alibaba:
      seata:
        tx-service-group: default_tx_group
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url: jdbc:mysql://192.168.0.100:3306/seata_order?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
    username: root
    password: root


mybatis:
  mapper-locations: classpath:mapper/**.xml
```

### 20.9.3启动类

```java
package com.alibaba.seata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class SeataClient {
    public static void main(String[] args) {
        SpringApplication.run(SeataClient.class, args);
    }
}
```

### 20.9.4控制层

```java
package com.alibaba.seata.controller;

import com.alibaba.seata.domain.CommonResult;
import com.alibaba.seata.domain.Order;
import com.alibaba.seata.service.OrderService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/create")
    @GlobalTransactional
    @Transactional
    public CommonResult create(Order order){
        orderService.create(order);
        return new CommonResult(200,"OKay", order);
    }

}
```

### 20.9.5Mapper

```java
package com.alibaba.seata.mapper;

import com.alibaba.seata.domain.Order;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderMapper {

    @Insert(
            value = "INSERT INTO t_order(`user_id`,`product_id`,`count`,`money`,`status`) VALUES (#{userId},#{productId},#{count},#{money},#{status})"
    )
    @Options(useGeneratedKeys = true)
    void create(Order order);

    @Update(value = "UPDATE `t_order` SET `status` = 1 WHERE `user_id` = #{userId} AND `status` = #{status}")
    @Options(useGeneratedKeys = true)
    void update(@Param("userId") Long userId,@Param("status") Integer status);


}
```

### 20.9.6OpenFeign

```java
package com.alibaba.seata.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("seata-account")
public interface AccountService {

    @PostMapping("/account/decrease")
    public void decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);
}
```

```java
package com.alibaba.seata.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage")
public interface StorageService {

    @GetMapping("/storage/decrease")
    public void decrease(@RequestParam("productId") Long productId,@RequestParam("count") Integer count);
}
```

### 20.9.7file.conf

```tex
transport {
  # tcp udt unix-domain-socket
  type = "TCP"
  #NIO NATIVE
  server = "NIO"
  #enable heartbeat
  heartbeat = true
  #thread factory for netty
  thread-factory {
    boss-thread-prefix = "NettyBoss"
    worker-thread-prefix = "NettyServerNIOWorker"
    server-executor-thread-prefix = "NettyServerBizHandler"
    share-boss-worker = false
    client-selector-thread-prefix = "NettyClientSelector"
    client-selector-thread-size = 1
    client-worker-thread-prefix = "NettyClientWorkerThread"
    # netty boss thread size,will not be used for UDT
    boss-thread-size = 1
    #auto default pin or 8
    worker-thread-size = 8
  }
  shutdown {
    # when destroy server, wait seconds
    wait = 3
  }
  serialization = "seata"
  compressor = "none"
}

service {

  vgroup_mapping.default_tx_group = "default"

  default.grouplist = "127.0.0.1:8091"
  enableDegrade = false
  disable = false
  max.commit.retry.timeout = "-1"
  max.rollback.retry.timeout = "-1"
  disableGlobalTransaction = false
}


client {
  async.commit.buffer.limit = 10000
  lock {
    retry.internal = 10
    retry.times = 30
  }
  report.retry.count = 5
  tm.commit.retry.count = 1
  tm.rollback.retry.count = 1
}

## transaction log store
store {
  ## store mode: file、db
  mode = "db"

  ## file store
  file {
    dir = "sessionStore"

    # branch session size , if exceeded first try compress lockkey, still exceeded throws exceptions
    max-branch-session-size = 16384
    # globe session size , if exceeded throws exceptions
    max-global-session-size = 512
    # file buffer size , if exceeded allocate new buffer
    file-write-buffer-cache-size = 16384
    # when recover batch read size
    session.reload.read_size = 100
    # async, sync
    flush-disk-mode = async
  }

  ## database store
  db {
    ## the implement of javax.sql.DataSource, such as DruidDataSource(druid)/BasicDataSource(dbcp) etc.
    datasource = "dbcp"
    ## mysql/oracle/h2/oceanbase etc.
    db-type = "mysql"
    driver-class-name = "com.mysql.jdbc.Driver"
    url = "jdbc:mysql://127.0.0.1:3306/seata"
    user = "root"
    password = "root"
    min-conn = 1
    max-conn = 3
    global.table = "global_table"
    branch.table = "branch_table"
    lock-table = "lock_table"
    query-limit = 100
  }
}
lock {
  ## the lock store mode: local、remote
  mode = "remote"

  local {
    ## store locks in user's database
  }

  remote {
    ## store locks in the seata's server
  }
}
recovery {
  #schedule committing retry period in milliseconds
  committing-retry-period = 1000
  #schedule asyn committing retry period in milliseconds
  asyn-committing-retry-period = 1000
  #schedule rollbacking retry period in milliseconds
  rollbacking-retry-period = 1000
  #schedule timeout retry period in milliseconds
  timeout-retry-period = 1000
}

transaction {
  undo.data.validation = true
  undo.log.serialization = "jackson"
  undo.log.save.days = 7
  #schedule delete expired undo_log in milliseconds
  undo.log.delete.period = 86400000
  undo.log.table = "undo_log"
}

## metrics settings
metrics {
  enabled = false
  registry-type = "compact"
  # multi exporters use comma divided
  exporter-list = "prometheus"
  exporter-prometheus-port = 9898
}

support {
  ## spring
  spring {
    # auto proxy the DataSource bean
    datasource.autoproxy = false
  }
}
```

### 20.9.8registry.conf

```tex
registry {
  # file 、nacos 、eureka、redis、zk、consul、etcd3、sofa
  type = "nacos"

  nacos {
    serverAddr = "localhost:8848"
    namespace = ""
    cluster = "default"
  }
  eureka {
    serviceUrl = "http://localhost:8761/eureka"
    application = "default"
    weight = "1"
  }
  redis {
    serverAddr = "localhost:6379"
    db = "0"
  }
  zk {
    cluster = "default"
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  consul {
    cluster = "default"
    serverAddr = "127.0.0.1:8500"
  }
  etcd3 {
    cluster = "default"
    serverAddr = "http://localhost:2379"
  }
  sofa {
    serverAddr = "127.0.0.1:9603"
    application = "default"
    region = "DEFAULT_ZONE"
    datacenter = "DefaultDataCenter"
    cluster = "default"
    group = "SEATA_GROUP"
    addressWaitTime = "3000"
  }
  file {
    name = "file.conf"
  }
}

config {
  # file、nacos 、apollo、zk、consul、etcd3
  type = "file"

  nacos {
    serverAddr = "localhost"
    namespace = ""
  }
  consul {
    serverAddr = "127.0.0.1:8500"
  }
  apollo {
    app.id = "seata-server"
    apollo.meta = "http://192.168.1.204:8801"
  }
  zk {
    serverAddr = "127.0.0.1:2181"
    session.timeout = 6000
    connect.timeout = 2000
  }
  etcd3 {
    serverAddr = "http://localhost:2379"
  }
  file {
    name = "file.conf"
  }
}
```

### 20.9.9StroageMapper

```java
package com.alibaba.seata.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface StroageMapper {

    @Update(value = "UPDATE `s_stroage` SET `used` = `used` + #{count},`residue` = `residue` - #{count} WHERE `product_id` = #{produceId}")
    @Options(useGeneratedKeys = true)
    void decrease(@Param("productId") Long productId, @Param("count") Integer count);
}
```

### 21.AccountMapper

```java
package com.alibaba.seata.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

@Mapper
public interface AccountMapper {

    @Update(value = "UPDATE `t_account` SET `residue` = `residue` - #{money} , `used` = `used` + #{money} WHERE `user_id` = #{userId}")
    @Options(useGeneratedKeys = true)
    void decease(@Param("userId") Long userId, @Param("money")BigDecimal money);
}
```

### 21.2Config

```java
package com.atguigu.springcloud.alibaba.config;
 
import com.alibaba.druid.pool.DruidDataSource;
import io.seata.rm.datasource.DataSourceProxy;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
 
/**
 * Created by IntelliJ IDEA.
 * User: zhuangzibing
 * Date: 2020/7/20
 * 使用Seata对数据源进行代理
 */
@Configuration
public class DataSourceProxyConfig {
 
    @Value("${mybatis.mapperLocations}")
    private String mapperLocations;
 
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }
 
    @Bean
    public DataSourceProxy dataSourceProxy(DataSource dataSource) {
        return new DataSourceProxy(dataSource);
    }
 
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSourceProxy dataSourceProxy) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSourceProxy);
        sqlSessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));
        sqlSessionFactoryBean.setTransactionFactory(new SpringManagedTransactionFactory());
        return sqlSessionFactoryBean.getObject();
    }
}
```



## 21.测试

### 21.1正常下单

![image-20220210104339343](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220210104339343.png)

### 21.2超时

```java
@PostMapping("/account/decrease")
public CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money) throws InterruptedException {
    Thread.sleep(2000);
    accountMapper.decease(userId,money);
    return new CommonResult(200, "扣除账户余额成功", null);
};
```

![image-20220210104924676](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220210104924676.png)

![image-20220210104946569](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220210104946569.png)

![image-20220210105022556](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220210105022556.png)

### 21.3加上@GlobalTransactional

```java
@Override
@GlobalTransactional(name = "default_tx_group",rollbackFor = Exception.class)
public void create(Order order) {

    // 添加商品

    orderMapper.create(order);

    // 减少商品库存

    storageService.decrease(order.getProductId(), order.getCount());

    // 扣钱

    accountService.decrease(order.getUserId(),order.getMoney());

    // 改用户状态

    orderMapper.update(order.getUserId(),0);

}
```

![image-20220210105347774](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220210105347774.png)

## 22.补充

![image-20220211113713038](C:\Users\28306\AppData\Roaming\Typora\typora-user-images\image-20220211113713038.png)
