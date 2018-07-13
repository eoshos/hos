package io.eoshos.eos4j.ese;

import io.eoshos.eos4j.utils.ByteUtils;
import io.eoshos.eos4j.utils.Hex;

/**
 * Ese
 * 
 * @author espritblock http://eblock.io
 *
 */
public class Ese {

	/**
	 * parseTransferData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseTransferData(String from, String to, String quantity, String memo) {
		DataParam[] datas = new DataParam[] { 
				new DataParam(from, DataType.name, Action.transfer),
				new DataParam(to, DataType.name, Action.transfer),
				new DataParam(quantity, DataType.asset, Action.transfer),
				new DataParam(memo, DataType.string, Action.transfer), };
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		// final byte [] b = allbyte.clone();
		// int[] a = IntStream.range(0, b.length).map(i -> b[i] & 0xff).toArray();
		// for(int i=1;i<=a.length;i++) {
		// System.out.print(a[i-1]+","+((i%8==0)?"\n":""));
		// }
		return Hex.bytesToHexString(allbyte);
	}
	
	/**
	 * parseTransferlockData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseTransferlockData(String from, String to, String quantity,String lockQuantity,String lockTime, String memo) {
		DataParam[] datas = new DataParam[] { 
				new DataParam(from, DataType.name, Action.hosTransfer),
				new DataParam(to, DataType.name, Action.hosTransfer),
				new DataParam(quantity, DataType.asset, Action.hosTransfer),
				new DataParam(lockQuantity, DataType.asset, Action.hosTransfer),
				new DataParam(lockTime, DataType.uint64, Action.hosTransfer),
				new DataParam(memo, DataType.string, Action.hosTransfer), };
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}

	/**
	 * parseTransferData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseAccountData(String creator, String name, String onwer, String active) {

		DataParam[] datas = new DataParam[] {
				// creator
				new DataParam(creator, DataType.name, Action.account),
				// name
				new DataParam(name, DataType.name, Action.account),
				// owner
				new DataParam(onwer, DataType.key, Action.account),
				// active
				new DataParam(active, DataType.key, Action.account),

		};
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}

	/**
	 * parseBuyRamData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseDelegateData(String from, String receiver, String stakeNetQuantity,
			String stakeCpuQuantity, int transfer) {

		DataParam[] datas = new DataParam[] { new DataParam(from, DataType.name, Action.delegate),
				new DataParam(receiver, DataType.name, Action.delegate),
				new DataParam(stakeNetQuantity, DataType.asset, Action.delegate),
				new DataParam(stakeCpuQuantity, DataType.asset, Action.delegate),
				new DataParam(String.valueOf(transfer), DataType.varint32, Action.delegate)

		};
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}

	/**
	 * parseTransferData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseBuyRamData(String payer, String receiver, Long bytes) {

		DataParam[] datas = new DataParam[] { new DataParam(payer, DataType.name, Action.ram),
				new DataParam(receiver, DataType.name, Action.ram),
				new DataParam(String.valueOf(bytes), DataType.uint32, Action.ram)

		};
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}
	
	/**
	 * parseLockData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseLockData(String owner,String user) {
		DataParam[] datas = new DataParam[] { 
				new DataParam(owner, DataType.name, Action.account),
				new DataParam(user, DataType.name, Action.account), };
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}
	
	/**
	 * parseLockData
	 * 
	 * @param datas
	 * @return
	 */
	public static String parseUnLockData(String owner,String user) {
		DataParam[] datas = new DataParam[] { 
				new DataParam(owner, DataType.name, Action.account),
				new DataParam(user, DataType.name, Action.account), };
		byte[] allbyte = new byte[] {};
		for (DataParam value : datas) {
			allbyte = ByteUtils.concat(allbyte, value.seria());
		}
		return Hex.bytesToHexString(allbyte);
	}
}
