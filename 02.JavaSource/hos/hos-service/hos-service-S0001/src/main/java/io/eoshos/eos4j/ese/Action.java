package io.eoshos.eos4j.ese;

/**
 * Action
 * 
 * @author espritblock http://eblock.io
 *
 */
public enum Action {

	transfer("4,${quantity}@eosio.token"),
	hosTransfer("4,${quantity}@hos.token"), 
	account("account"), 
	ram("ram"), 
	delegate("4,${quantity}@eosio.token"),
	lock("lock"), 
	unlock("unlock");

	private String code;

	private Action(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}