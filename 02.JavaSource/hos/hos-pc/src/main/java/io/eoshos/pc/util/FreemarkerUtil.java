package io.eoshos.pc.util;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;
/*import com.chuangke18.cfs.freemarker.method.DateMethodModel;
import com.chuangke18.cfs.freemarker.method.ParserDoubleMethodModel;*/
//import com.chuangke18.cfs.freemarker.method.ParserLongMethodModel;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * freemarker的模板处理工具类
 *
 */
public class FreemarkerUtil {

	
	public static Configuration CONFIG;

	public static String renderTemplate(String s,Map<String,Object> data) throws IOException, TemplateException{
		// v1.6.7.1-u3 RDPROJECT-698 zza 2014-03-03 start
		if (CONFIG == null) {
			getConfiguration();
		}
		// v1.6.7.1-u3 RDPROJECT-698 zza 2014-03-03 end
 		Template t = new Template(null, new StringReader(s), CONFIG);
        //执行插值，并输出到指定的输出流中
		StringWriter w = new StringWriter();
		t.getConfiguration();
		t.process(data, w);
        return w.getBuffer().toString();
	}
	
	public static String renderFileTemplate(String file,Map<String,Object> data) throws IOException, TemplateException{
		if (CONFIG == null) {
			getConfiguration();
		}
		Configuration cfg = CONFIG;
		cfg.setDefaultEncoding("UTF-8");
        // 取得模板文件  
        Template t = cfg.getTemplate(file);
        //执行插值，并输出到指定的输出流中
		StringWriter w = new StringWriter();
		t.getConfiguration();
        t.process(data, w); 
        return w.getBuffer().toString();
	}
	
	// v1.6.7.1-u3 RDPROJECT-698 zza 2014-03-03 start
	// CONFIG为null时进行初始化
	@SuppressWarnings("deprecation")
	public static void getConfiguration(){
		Configuration cfg = new Configuration();
        //计算利息的自定义方法 
      /*  cfg.setSharedVariable("dateformat", new DateMethodModel() );  
        cfg.setSharedVariable("parseDouble", new ParserDoubleMethodModel());*/
       // cfg.setSharedVariable("parseLong", new ParserLongMethodModel());      
        CONFIG = cfg;
	}
	// v1.6.7.1-u3 RDPROJECT-698 zza 2014-03-03 end
}
