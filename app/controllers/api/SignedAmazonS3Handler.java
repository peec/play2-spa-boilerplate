package controllers.api;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import be.objectify.deadbolt.java.actions.SubjectPresent;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import play.Logger;
import play.Play;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import plugins.S3Plugin;
import utils.JsonResp;
import controllers.API;

@SubjectPresent
public class SignedAmazonS3Handler extends API{

	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	static public Result sign() {
		if (S3Plugin.amazonS3 == null){
			return internalServerError(JsonResp.error("Amazon S3 is not configured, can not presign upload."));
		}
		JsonNode input = jsonBody();


		String iName, iContentType;

		iName = input.get("fileName").asText();
		iName = iName.replace(" ", "-");
		iName = iName.replace("+","-");
		iName = iName.replaceAll("[^A-Za-z0-9-_.]", "");

		iContentType = input.get("contentType").asText();

		// Basic check for content types.
		String allowedContentTypes[] = {"image/png","image/jpeg","image/gif", "image/png","image/bmp", "image/jpg"};
		String acl = "public-read";
		Long maxSize = 7340032L; // 7 mb max size.
		
		if (!Arrays.asList(allowedContentTypes).contains(iContentType)){
			return badRequest(JsonResp.error("Content type of that file is not allowed."));
		}
		
		
		
		UUID container = UUID.randomUUID();
		String fileName = "uploads/" + container.toString() + "/" + iName;

		// Generate when this request expires.
		DateTime expiery = DateTime.now().plusDays(2).withZone(DateTimeZone.UTC).withTime(0, 0, 0, 0);

		try {
			Policy policy = generatePolicy(fileName, acl, expiery, iContentType, maxSize);
		
			String fileUrl = String.format("http://%s.s3.amazonaws.com/%s", S3Plugin.s3Bucket, fileName);
			String accessKey = Play.application().configuration().getString(S3Plugin.AWS_ACCESS_KEY);
			// String signedUrl = generateSignedUrl(policy, fileName, iContentType, expiery);
			String signature = generateSignature(policy);
			String uplUrl = String.format("http://%s.s3.amazonaws.com", S3Plugin.s3Bucket);
			
			
			ObjectNode o = Json.newObject();
			o.put("fileUrl", fileUrl);
			o.put("uploadUrl", uplUrl);
			//o.put("signed_request", signedUrl);
			o.put("signature", signature);
			o.put("policy", policy.checksum());
			o.put("accessKey", accessKey);
			o.put("acl", acl);
			o.put("key", fileName);
			o.put("contentType", iContentType);
			
			return ok(o);
		} catch (UnsupportedEncodingException e) {
			return internalServerError(JsonResp.error("Encoding not supported."));
		} catch (InvalidKeyException e) {
			return internalServerError(JsonResp.error("InvalidKeyException"));
		} catch (NoSuchAlgorithmException e) {
			return internalServerError(JsonResp.error("NoSuchAlgorithmException"));
		}
	}

	public static Policy generatePolicy(
			String fileName, 
			String acl, 
			DateTime expiery, 
			String contentType,
			Long maxSize
			) throws UnsupportedEncodingException{
		Policy policy = new Policy();
		policy.setExpiery(expiery);
		policy.addObjectCondition("bucket", S3Plugin.s3Bucket);
		policy.addObjectCondition("acl", acl);
		policy.addArrayCondition("starts-with","$key","uploads/");
		policy.addArrayCondition("starts-with", "$Content-Type", contentType);
		policy.addContentLengthRange(1, maxSize); // 7 mb max.
		
		return policy;
	}
	private static String generateSignature(Policy pol) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		String secretKey = Play.application().configuration().getString(S3Plugin.AWS_SECRET_KEY);
		String policy = pol.checksum();
		Mac hmac = Mac.getInstance("HmacSHA1");
		hmac.init(new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA1"));
		String signature = new String(
				Base64.encodeBase64(hmac.doFinal(policy.getBytes("UTF-8")))
				,"ASCII").replaceAll("\n", "");
		return signature;
	}


	public static class Policy {
		ObjectNode node;
		public ArrayNode conditions;
		public Policy(){
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
	
}
