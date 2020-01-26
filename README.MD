![image](https://github.com/SkyJourney/juicer/raw/master/resources/logo.png)
# Spring boot starter for juicer
juicer框架的spring boot组件starter。  
application.properties配置项：
```properties
#包扫描位置
juicer.data.scan-packages=com.braggart
#开启数据储存，默认为false
juicer.data.persistence=false
#开启中断续爬，默认为false
juicer.data.interrupt-save=false
#数据保存位置
juicer.data.save-path=./juicer.jdt
#独立线程池线程数，开启独立线程池后有效
juicer.data.pool-thread=8
#开启独立线程池，默认为false
juicer.data.standalone-pool=false
```
载入该starter后，只需设定好配置项，即可访问http://host:port/juicer 查看当前被注册的handler对象。调用API http://host:port/juicer/{handler} 其中参数为handler名称，即可返回数据对象。  
当前默认开发API如下：
```http request
### 显示模块主界面
GET http://host:port/juicer

### 返回所有handler对象名称的数组
POST http://host:port/juicer/handlers

### 填入handler名称返回解析结果集
GET http://host:port/juicer/{handler}
### 填入handler名称返回解析结果集
POST http://host:port/juicer/{handler}

### 填入handler名称刷新结果集，返回Success字符串
GET http://host:port/juicer/refresh/{handler}
### 填入handler名称刷新结果集，返回Success字符串
POST http://host:port/juicer/refresh/{handler}

```