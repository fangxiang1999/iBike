package com.yc.config;

import com.mongodb.MongoClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.ldap.Rdn;

/**
 * @author wys
 * @date 2020/6/12 - 20:14
 */
@EnableTransactionManagement    //启动事务  会查找Bean上是否有@Transactional注解，若有，则启动事务管理器
@Configuration      //配置
@ComponentScan(basePackages="com.yc")
public class AppConfig {
    private Logger log= LogManager.getLogger(AppConfig.class);

    //操作key为String,value为对象
    @Bean
    public RedisTemplate redisTemplate(){
        JedisConnectionFactory conn=new JedisConnectionFactory();
        conn.setDatabase(0);
        conn.setHostName("node1");
        conn.setPort(6381);
        conn.setPassword("");
        conn.setUsePool(true);
        conn.afterPropertiesSet();
        RedisTemplate<byte[],byte[]>template=new RedisTemplate<>();
        template.setConnectionFactory(conn);
        template.afterPropertiesSet();
        return template;
    }
                //操作key,value都为String
    @Bean
    public StringRedisTemplate stringRedisTemplate(){
        JedisConnectionFactory conn=new JedisConnectionFactory();
        conn.setDatabase(0);
        conn.setHostName("node1");
        conn.setPort(6381);
        conn.setPassword("");
        conn.setUsePool(true);
        conn.afterPropertiesSet();
        StringRedisTemplate template=new StringRedisTemplate();
        template.setConnectionFactory(conn);
        template.afterPropertiesSet();
        return template;
    }


    @Bean    //  MongoTemplate由spring 托管
    @Primary
    public MongoTemplate template() {
        return new MongoTemplate(factory());
    }

    /**
     * 功能描述: 创建数据库名称对应的工厂，数据库名称可以通过配置文件导入
     * @param
     * @return:org.springframework.data.mongodb.MongoDbFactory
     * @since: v1.0
     */
    @Bean("mongoDbFactory")
    public MongoDbFactory factory() {
        return new SimpleMongoDbFactory(client(), "yc74ibike");
    }

    /**
     * 功能描述: 配置client，client中传入的ip和端口可以通过配置文件读入
     *
     * @param
     * @return:com.mongodb.MongoClient
     */
    @Bean("mongoClient")
    public MongoClient client() {
        return new MongoClient("node1", 27017);
    }



    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/bike");
        dataSource.setUsername("root");
        dataSource.setPassword("a");
        log.info("创建数据源"+dataSource);
        return dataSource;
    }
    //事务管理器
    @Bean
    @Autowired
    public DataSourceTransactionManager tx( DriverManagerDataSource ds ){
        DataSourceTransactionManager dtm=new DataSourceTransactionManager();
        dtm.setDataSource(ds);
        log.info("创建事务管理器"+ds);
        return dtm;
    }
}
