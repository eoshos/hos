package io.eoshos.eos4j.ese;

import io.eoshos.eos4j.utils.ByteUtils;
import io.eoshos.eos4j.utils.EException;

/**
 * DataParam
 * 
 * @author espritblock http://eblock.io
 *
 */
public class DataParam {

	public DataParam(String value, DataType type, Action action) {
		this.value = value;
		this.type = type;
		if (type == DataType.asset) {
			if (action == action.transfer ||action == action.hosTransfer || action == action.delegate) {
				String vs[] = value.split(" ");
				if (vs.length < 2) {
					throw new EException("error", "quantity error");
				}
				this.value = vs[0] + " " + action.getCode().replace("${quantity}", vs[1]);
			} else {
				this.value = value;
			}
		}
	}

	private String value;

	private DataType type;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public DataType getType() {
		return type;
	}

	public void setType(DataType type) {
		this.type = type;
	}

	public byte[] seria() {
		if (this.type == DataType.name) {
			return ByteUtils.writeName(this.value);
		} else if (this.type == DataType.asset) {
			return ByteUtils.writerAsset(this.value);
		} else if (this.type == DataType.uint32) {
			return ByteUtils.writerUnit32(this.value);
		} else if (this.type == DataType.uint16) {
			return ByteUtils.writerUnit16(this.value);
		} else if (this.type == DataType.key) {
			return ByteUtils.writerKey(this.value);
		} else if (this.type == DataType.varint32) {
			return ByteUtils.writerVarint32(this.value);
		} else if (this.type == DataType.uint64) {
			return ByteUtils.writeUint64(this.value);
		} else {
			return ByteUtils.writerString(this.value);
		}
	}
}
