
# Spring 配置 Mybatis-Plus


> 依赖包

```
<dependency>
    <groupId>com.baomidou</groupId>
    <artifactId>mybatis-plus</artifactId>
    <version>最新版本 Maven 为准</version>
</dependency>
```

> spring 配置

```
<!-- MyBatis SqlSessionFactoryBean 配置 -->
<bean id="sqlSessionFactory" class="com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean">
	<property name="dataSource" ref="dataSource" />
	<property name="configLocation" value="classpath:xml/mybatis-config.xml" />
	<property name="typeAliasesPackage" value="xxx.entity" />
	<property name="mapperLocations" value="classpath:com/xx/mapper/xml/*Mapper.xml" />
    <property name="plugins">
        <array>
            <!-- 分页插件配置 -->
            <bean id="paginationInterceptor" class="com.baomidou.mybatisplus.plugins.PaginationInterceptor">
		        <property name="dialectType" value="mysql" />
		    </bean>
        </array>
    </property>
    <!-- oracle 添加
    <property name="dbType" value="oracle" />
    -->
    <!-- 全局表为下划线命名设置 true
    <property name="dbColumnUnderline" value="true" />
    -->
    <!-- 全局字段验证 0、ignored  1、not null 2、not empty
    <property name="fieldStrategy" value="2" />
    -->
</bean>

<!-- 加载 mapper.xml 接口 配置文件 -->
<bean id="mapperScannerConfigurer" class="org.mybatis.spring.mapper.MapperScannerConfigurer">
	<property name="basePackage" value="xxx.mapper" />
</bean>
```

> 开启动态加载 mapper

```

    参数说明：
        sqlSessionFactory:session工厂
        mapperLocations:mapper匹配路径
        enabled:是否开启动态加载  默认:false
        delaySeconds:项目启动延迟加载时间  单位：秒  默认:10s
        sleepSeconds:刷新时间间隔  单位：秒 默认:20s
    提供了两个构造,挑选一个配置进入spring配置文件即可：

	构造1:
	    <bean class="com.baomidou.mybatisplus.spring.MybatisMapperRefresh">
	        <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"/>
	        <constructor-arg name="mapperLocations" value="classpath*:mybatis/mappers/*/*.xml"/>
	        <constructor-arg name="enabled" value="true"/>
	    </bean>
	
	构造2:
		<bean class="com.baomidou.mybatisplus.spring.MybatisMapperRefresh">
	        <constructor-arg name="sqlSessionFactory" ref="sqlSessionFactory"/>
	        <constructor-arg name="mapperLocations" value="classpath*:mybatis/mappers/*/*.xml"/>
	        <constructor-arg name="delaySeconds" value="10"/>
	        <constructor-arg name="sleepSeconds" value="20"/>
	        <constructor-arg name="enabled" value="true"/>
	    </bean>
```



> mybatis-config.xml

```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <!-- 
     | 全局配置设置
     |
     | 可配置选项                                        默认值, 描述
     |
     | aggressiveLazyLoading     true, 当设置为‘true’的时候，懒加载的对象可能被任何懒属性全部加载。否则，每个属性都按需加载。
     | multipleResultSetsEnabled true, 允许和不允许单条语句返回多个数据集（取决于驱动需求）
     | useColumnLabel            true, 使用列标签代替列名称。不同的驱动器有不同的作法。参考一下驱动器文档，或者用这两个不同的选项进行测试一下。
     | useGeneratedKeys          false, 允许JDBC 生成主键。需要驱动器支持。如果设为了true，这个设置将强制使用被生成的主键，有一些驱动器不兼容不过仍然可以执行。
     | autoMappingBehavior       PARTIAL, 指定MyBatis 是否并且如何来自动映射数据表字段与对象的属性。PARTIAL将只自动映射简单的，没有嵌套的结果。FULL 将自动映射所有复杂的结果。
     | defaultExecutorType       SIMPLE, 配置和设定执行器，SIMPLE 执行器执行其它语句。REUSE 执行器可能重复使用prepared statements 语句，BATCH执行器可以重复执行语句和批量更新。
     | defaultStatementTimeout   null, 设置一个时限，以决定让驱动器等待数据库回应的多长时间为超时
     | -->
    <settings>
        <!-- 这个配置使全局的映射器启用或禁用缓存 -->
        <setting name="cacheEnabled" value="true" />
        <!-- 全局启用或禁用延迟加载。当禁用时，所有关联对象都会即时加载 -->
        <setting name="lazyLoadingEnabled" value="true" />
		<setting name="multipleResultSetsEnabled" value="true" />
		<setting name="useColumnLabel" value="true" />
		<setting name="defaultExecutorType" value="REUSE" />
		<setting name="defaultStatementTimeout" value="25000" />
	</settings>
	<!-- 查询对象别名配置 -->
	<typeAliases>
		<!-- 
		<typeAlias alias="taskVo" type="xxx.vo.TaskVo" />
		 -->
	</typeAliases>
	<!-- 插件配置, spring 中配置，此处就可以不用配置。 -->
    <!-- 
	<plugins>
	     | 分页插件配置 
	     | 插件提供二种方言选择：1、默认方言 2、自定义方言实现类，两者均未配置则抛出异常！
	     | dialectType 数据库方言  
	     |             默认支持  mysql  oracle  hsql  sqlite  postgre  sqlserver
	     | dialectClazz 方言实现类
	     |              自定义需要实现 com.baomidou.mybatisplus.plugins.pagination.IDialect 接口
	    1、 配置方式一、使用 MybatisPlus 提供方言实现类
	    <plugin interceptor="com.baomidou.mybatisplus.plugins.PaginationInterceptor">
	        <property name="dialectType" value="mysql" />
	    </plugin>
	    2、配置方式二、使用自定义方言实现类 
	    <plugin interceptor="com.baomidou.mybatisplus.plugins.PaginationInterceptor">
	        <property name="dialectClazz" value="xxx.dialect.XXDialect" />
	    </plugin>
        <!-- SQL 执行性能分析，开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长 -->
        <plugin interceptor="com.baomidou.mybatisplus.plugins.PerformanceInterceptor">
            <property name="maxTime" value="100" />
        </plugin>
        <!-- SQL 执行分析拦截器 stopProceed 发现全表执行 delete update 是否停止运行 -->
        <plugin interceptor="com.baomidou.mybatisplus.plugins.SqlExplainInterceptor">
            <property name="stopProceed" value="false" />
        </plugin>
	</plugins>
	| -->
</configuration>
```



