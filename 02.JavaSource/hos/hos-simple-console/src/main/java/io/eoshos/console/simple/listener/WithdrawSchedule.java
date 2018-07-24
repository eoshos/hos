package io.eoshos.console.simple.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**  
* 
* @ClassName: WithdrawSchedule
* @Description: TODO
* @author hehongjian
* @date 2018年7月23日 上午10:37:04
*
*/

@Component
@Configurable
@EnableScheduling
public class WithdrawSchedule {
	
    //每15分钟执行一次
    @Scheduled(cron = "0 */15 *  * * * ")
    public void WithdrawToChain(){
    	SimpleDateFormat  df =  new SimpleDateFormat ("HH:mm:ss");
        System.out.println ("The time is now " + df.format(new Date ()));
        
        //1.提币正常上链
        //遍历hos_trade_coin_to_chain符合下列条件的对象：stat=9、tochain_stat=0
        //置对象的tochain_stat=1
        //调用API,执行上链操作
        //如果API实时返回，置tochain_stat=8或9、置chain_transaction_id,否则下一个对象
        
        //2.提币异常上链
        //处理异常上链的对象
        //遍历hos_trade_coin_to_chain符合下列条件的对象：stat=9、tochain_stat=1
        //链上查询有没有上链结果
        //如果链上已有结果，置tochain_stat=8或9,下一个对象
        //如果链上没有结果，调用API,执行上链操作
        //调用API实时返回，置tochain_stat=8或9、置chain_transaction_id,否则下一个对象
        
        //3.上链结果保存到本地
        //遍历hos_trade_coin_to_chain符合下列条件的对象：stat=9、tochain_stat=19、tochain_query_stat=0
        //调用API上链查询,如果查到,等查询结果保存到本地后置tochain_query_stat=9
        //调用API上链查询,如果未查到tochain_query_stat=8,这种情况 理论上不应该存在
        
    }	
    
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
