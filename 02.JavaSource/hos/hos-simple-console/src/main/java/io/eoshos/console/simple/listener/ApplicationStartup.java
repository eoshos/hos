package io.eoshos.console.simple.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Order(value=1)
@Component
public class ApplicationStartup implements CommandLineRunner {
	/*
	private static String DBNAME = "dbname";
	private static String TABLE_NAME_VALID_ITEM = "valid_item";
	private static String INIT_SQL = "valid-init.sql";
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private ISchemaTableService schemaTableService;*/


	@Override
	public void run(String... args) throws Exception {
		/*String dbname = "";
		for (String e : args){
			String[] arr = e.split("=");
			if (StringUtils.equals(DBNAME, arr[0])){
				dbname = arr[1];
			}
		}
		
		SchemaTableDto schemaTableDto = new SchemaTableDto();
		schemaTableDto.setTableSchema(dbname);
		schemaTableDto.setTableName(TABLE_NAME_VALID_ITEM);
		List<SchemaTableVo> list = schemaTableService.listAllObjects(schemaTableDto);
		if (list.size() > 0){
			return;
		}
		
		Connection conn = dataSource.getConnection();
		ScriptRunner runner = new ScriptRunner(conn);
        Resources.setCharset(Charset.forName("UTF-8")); //设置字符集,不然中文乱码插入错误
        runner.setLogWriter(null);//设置是否输出日志
        runner.runScript(Resources.getResourceAsReader(INIT_SQL));
        runner.closeConnection();
        conn.close();*/
	}

	
}
