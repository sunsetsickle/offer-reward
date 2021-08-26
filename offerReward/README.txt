SSM整合开发
SpringMVC：视图层，界面层，负责接受请求，显示处理结果
spring：业务层：管理service，dao，工具类对象的
MyBatis：持久层，访问数据库的

用户发起请求--->SpringMVC接收--->spring中的service对象---》mybatis处理数据

1、第一个容器SpringMVC容器，管理Controller控制器对象的
2、第二个容器Spring容器，管理Service，Dao，工具类对象的
我们要做的把使用的对象交给合适的容器创建，管理。把Controller和web开发的相关对象
交给springMVC容器，这些web用的对象写在springMVC配置文件中

service，dao对象定义在spring配置文件中，让spring管理这些对象。

0、数据库 mysql表名book（id,name,author,price）
1、新建maven web项目
2、加入依赖
        springMVC、spring、mybatis、jackon、mysql驱动、druid连接池
        jsp，servlet依赖
3、写web.xml
    1）注册DispatcherServlet，目的：1、创建springMVC容器对象，才能创建Controller对象
                                  2、创建的是Servlet，才能接收用户请求
    2）注册spring的监听器：ContextLoaderListener，目的：创建spring容器对象，才能创建service和dao对象
    3）注册字符集过滤器，解决post请求乱码

4、创建包，controller，service，dao，实体类包
5）写springmvc,spring,mybatis 配置文件
    1）springMVC配置文件
    2）spring配置文件
    3）mybatis配置文件
    4）数据库的属性配置文件
6、写代码，dao接口和mapper文件，service和实现类，controller，实体类

7、写jsp文件