package io.eoshos.eos4j.ese;

/**
 * DataType
 * 
 * @author espritblock http://eblock.io
 *
 */
public enum DataType {

	name("name"), asset("asset"), string("string"), key("key"), uint16("uint16"), uint32("uint32"), varint32(
			"varint32"),uint64("uint64");

	private DataType(String code) {
		this.code = code;
	}

	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
