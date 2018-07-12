/**
 * 
 */
package io.eoshos.eos4j;

import io.eoshos.eos4j.api.vo.transaction.Transaction;

public class Test {

	static final String eosjs_transfer_seriz = "00f2d4142123e95d0000c85353840ccdb486010000000000045359530000000019e6b58be8af95313233616263646f2e2f2c2e2f214023232425";

	static final String eosjs_account_seriz = "0000000000ea30550002a2f164772b5601000000010003ee4221c9c3f4f62646e3c758dbb8abaae506a559f67148a76968fa6b0f0868140100000001000000010003ba8de2f029cae85e7ca5c9f591bb17b86d750c5116cec30d94100e16e446d41501000000";

	public static void main(String[] args){

		System.out.println("******************* Ecc Start *******************\n");
		
		
		/**System.out.println("============= 通过种子生成私钥 ===============");
		String pk = Ecc.seedPrivate("!@#$%^&*(lajdlkjaksjdlkjaskldM<>?87126162kajsdjlaksd kajdlkaslkd heiuheijpe f[a- si0ausd9asd ahsdvcyasdcasdc ajhsdg8ca"
				+ "we asds JHDKAHDKKASDKJALSKDKA ooidjajsdua09sid0asdo[paksdajsdlklasdmlk FJKLIKNLK;B/;LP[P'NC;PO'; OOPO;L0["
				+ "XP'C'[FG["
				+ "19218728909107328972309289832098012");
		System.out.println("private key :" + pk +"\n");

		System.out.println("============= 通过私钥生成公钥 ===============");
		String pu = Ecc.privateToPublic(pk);
		System.out.println("public key :" + pu + " \n ");

		System.out.println("============= 自定义数据签名 ===============");
		String sign = Ecc.sign(pk,"is京東價as看到可可是是是@#￥%……&*（CVBNM《d ");
		System.out.println("sign :" + sign + " \n ");
		
		System.out.println("============= 转账数据序列化 ===============");
		String data = Ecc.parseTransferData("fromaccount", "toaccount", "10.0020 SYS", "测试123abcdo./,./!@##$%");
		System.out.println("seriz data :" + data);
		System.out.println("transfer eq eosjs seriz " + data.equals(eosjs_transfer_seriz)+" \n ");

		System.out.println("============= 创建账户数据序列化 ===============");
		String data1 = Ecc.parseAccountData("eosio", "espritbloc1.","EOS8eAX54cJtAngV2V22WZhRCW7e4sTAZz1mC5U22vp8mAGuFdMXx","EOS8FPooohZiiCAYXahWCQRxgXXzUbS2gNELAeYCUgGdDMbd2FHQT");
		System.out.println("seriz data :" + data1);
		System.out.println("account eq eosjs seriz " + data1.equals(eosjs_account_seriz));

		
		System.out.println("\n******************* Ecc End *******************\n\n\n");*/
		
		
		System.out.println("******************* Rpc Start *******************\n");
		
		//
		//http://193.93.219.219:8888
		Rpc rpc = new Rpc("http://47.75.75.17:8888");
		
		/**System.out.println("============= 转账 ===============");
		try {
			Transaction t1 = rpc.transfer("5KEv3h2tJns5oznEVM7h4VYBPZFtx48JeNhAHhosUTHguDCi3Jd","eoshostokens", "eoshostokens","eoshostest11", "10.0000 HOS", "hello 1");
			System.out.println("转账成功 = " + t1.getTransactionId()+" \n ");
		}catch(Exception ex) {
			ex.printStackTrace();
		}*/
		/**
		 * eoshostest11
		 * Private key: 5KJwsxCQWSDKmPiiWNM1qZBnjN9WcDZF1SecgQDctBq6tkGdFBo
		   Public key: EOS4uGWSVdCt1LuhgEoxKvEx55HAGwV9e5nN7YLXVuxkFJNNgm31p
		 */
		/**System.out.println("============= 锁定账户 ===============");
		try {
			Transaction t1 = rpc.lock("5KEv3h2tJns5oznEVM7h4VYBPZFtx48JeNhAHhosUTHguDCi3Jd","eoshostokens","eoshostest11");
			System.out.println("锁定账户成功 = " + t1.getTransactionId()+" \n ");
		}catch(Exception ex) {
			System.out.println("锁定账户失败 ： " + ex.getMessage()+" \n ");
			ex.printStackTrace();
		}*/
		
		System.out.println("============= 解锁账户 ===============");
		try {
			Transaction t1 = rpc.unlock("5KEv3h2tJns5oznEVM7h4VYBPZFtx48JeNhAHhosUTHguDCi3Jd","eoshostokens","eoshostest11");
			System.out.println("解锁账户成功 = " + t1.getTransactionId()+" \n ");
		}catch(Exception ex) {
			System.out.println("解锁账户失败 ： " + ex.getMessage()+" \n ");
			ex.printStackTrace();
		}
		
		/**System.out.println("============= 转账后锁定期 ===============");
		try {
			Transaction t1 = rpc.transferlock("5KdrbBfxABPEESgJEEDihSgWhbjWzR6TKYK51RHrKZuwdGs5byA","eoshostokens", "eoshostokens","eoshostest11", "2.0000 COS","2.0000 COS","3600", "hello 20 cos");
			System.out.println("转账成功 = " + t1.getTransactionId()+" \n ");
		}catch(Exception ex) {
			System.out.println("转账失败 ： " + ex.getMessage()+" \n ");
			ex.printStackTrace();
		}
		
		*/
		
		/**System.out.println("============= 创建账户并且抵押 ===============");
		try {	
			Transaction t2 = rpc.createAccount("5KEv3h2tJns5oznEVM7h4VYBPZFtx48JeNhAHhosUTHguDCi3Jd","eosio","eoshostokens", "EOS6tNjsgafMcn3NfN562J83zFjq6tuMUZYmtAdTMx9QnNCz7NVX4","EOS6tNjsgafMcn3NfN562J83zFjq6tuMUZYmtAdTMx9QnNCz7NVX4", 8192l, "0.01 SYS","0.01 SYS", 0l);
			System.out.println("创建成功 = " + t2.getTransactionId()+" \n ");
		}catch(Exception ex) {
			ex.printStackTrace();
		}*/
		/**
		 * Private key: 5KEv3h2tJns5oznEVM7h4VYBPZFtx48JeNhAHhosUTHguDCi3Jd
           Public key: EOS6tNjsgafMcn3NfN562J83zFjq6tuMUZYmtAdTMx9QnNCz7NVX4
		 */
		
		/**System.out.println("============= 创建账户不抵押 ===============");
		try {	
			Transaction t3 = rpc.createAccount("5KEv3h2tJns5oznEVM7h4VYBPZFtx48JeNhAHhosUTHguDCi3Jd","eoshostokens","eoshostest11", "EOS4uGWSVdCt1LuhgEoxKvEx55HAGwV9e5nN7YLXVuxkFJNNgm31p","EOS4uGWSVdCt1LuhgEoxKvEx55HAGwV9e5nN7YLXVuxkFJNNgm31p", 81920l);
			System.out.println("创建成功 = " + t3.getTransactionId()+" \n ");
		}catch(Exception ex) {
			ex.printStackTrace();
		}*/
		
		System.out.println("\n******************* Rpc End *******************");
	}
}
