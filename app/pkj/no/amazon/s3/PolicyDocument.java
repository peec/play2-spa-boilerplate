package pkj.no.amazon.s3;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import play.libs.Json;

public class PolicyDocument {
	ObjectNode node;
	public ArrayNode conditions;
	public PolicyDocument(){
		node = Json.newObject();
		conditions = Json.newObject().arrayNode();
	}
	public void setExpiery(DateTime expiery) {
		node.put("expiration", expiery.toString(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z")));
	}
	
	public void addArrayCondition(String... conds){
		ArrayNode o = Json.newObject().arrayNode();
		for(String cond : conds){
			o.add(cond);
		}
		conditions.add(o);
	}
	
	public void addObjectCondition(String key, String value){
		ObjectNode o = Json.newObject();
		o.put(key, value);
		conditions.add(o);
	}
	
	public void addContentLengthRange(long min, long max){
		ArrayNode o = Json.newObject().arrayNode();
		o.add("content-length-range");
		o.add(min);
		o.add(max);
		conditions.add(o);
	}
	
	@Override
	public String toString(){
		node.put("conditions", conditions);
		return node.toString();
	}
	
	public String checksum(){
		try {
			String policy = new String(
					Base64.encodeBase64(toString().getBytes("UTF-8")), "ASCII")
					.replaceAll("\n", "").replaceAll("\r", "");
			return policy;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
}
