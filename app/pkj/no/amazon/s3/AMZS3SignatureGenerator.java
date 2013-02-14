package pkj.no.amazon.s3;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;


public class AMZS3SignatureGenerator {

	String secretKey, accessKey, bucket, acl;
	PolicyDocument policy;
	
	
	public AMZS3SignatureGenerator(String secretKey, String accessKey, String bucket){
		this.secretKey = secretKey;
		this.accessKey = accessKey;	
		this.bucket = bucket;
		
		policy = new PolicyDocument();
		policy.addObjectCondition("bucket", bucket);
		// Default 2 days expiery.
		setExpiery(DateTime.now().plusDays(2).withZone(DateTimeZone.UTC).withTime(0, 0, 0, 0));
	}
	
	
	public AMZS3SignatureGenerator setExpiery(DateTime expiery){
		policy.setExpiery(expiery);
		return this;
	}
	
	public AMZS3SignatureGenerator setContentSizeRange(Long minSize, Long maxSize){
		policy.addContentLengthRange(minSize, maxSize);
		return this;
	}
	
	
	public AMZS3SignatureGenerator setAcl(String acl){
		policy.addObjectCondition("acl", acl);
		this.acl = acl;
		return this;
	}
	
	public AMZS3SignatureGenerator setFilenamePrefix(String prefix){
		policy.addArrayCondition("starts-with","$key",prefix);
		return this;
	}
	public AMZS3SignatureGenerator setContentType (String contentType) {
		policy.addArrayCondition("starts-with", "$Content-Type", contentType);
		return this;
	}
	
	public String getAccessKey(){
		return accessKey;
	}
	
	public String getSecretKey(){
		return secretKey;
	}
	
	
	
	public String getEncodedSignature() throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		String pol = policy.checksum();
		Mac hmac = Mac.getInstance("HmacSHA1");
		hmac.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA1"));
		String signature = new String(
				Base64.encodeBase64(hmac.doFinal(pol.getBytes("UTF-8")))
				,"ASCII").replaceAll("\n", "");
		return signature;
	}
	
	public String getEncodedPolicy(){
		return policy.checksum();
	}
	
	
	// Helpers
	
	public String normalizeFilename(String iName){
		iName = iName.replace(" ", "-");
		iName = iName.replace("+","-");
		iName = iName.replaceAll("[^A-Za-z0-9-_.]", "");
		return iName;
	}
	
	
	public String getFileUrlFor(String fileName){
		return String.format("http://%s.s3.amazonaws.com/%s", bucket, fileName);
	}
	
	public String getUploadUrl(){
		return String.format("http://%s.s3.amazonaws.com", bucket);
	}
	

	public String getAcl(){
		return acl;
	}
}
