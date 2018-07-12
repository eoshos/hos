package io.eoshos.console.simple;

import java.io.File;
import java.io.FileNotFoundException;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.ResourceUtils;

@MapperScan({"io.eoshos.console.simple.dao.mapper"})
@EntityScan("com.chuangke18.framework.validation.bean, io.eoshos.console.simple.bean")
@EnableCaching
@SpringBootApplication
@ImportResource(locations={"classpath:spring-context.xml"})
public class Application {
	
	//@Autowired
	//private DataSource  dataSource;
	
	
    //DataSource配置
    @Bean(name="dataSource")
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource dataSource() {
        return new org.apache.tomcat.jdbc.pool.DataSource(); 

    }
    
    //提供SqlSession
    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);//properties的驼峰也需要为true
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:io/eoshos/console/simple/dao/mapper/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }
 
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

	
	public static void main(String[] args) throws FileNotFoundException {
		//获取根目录
		File classFolder = new File(ResourceUtils.getURL("classpath:application.properties").getPath());
		if(!classFolder.exists()) classFolder = new File("");
		//如果上传目录为jar同级目录的simple_console_upload，则可以如下获取：
		//File uploadFolder = new File(path.getAbsolutePath(),"static/images/upload/");
		File uploadFolder = new File(classFolder.getParent()+"/simple_console_upload");
		if(!uploadFolder.exists()) uploadFolder.mkdirs();
		System.out.println("upload url:" + uploadFolder.getAbsolutePath());
		System.setProperty("uploadFolder", uploadFolder.getAbsolutePath());
		
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}

}
