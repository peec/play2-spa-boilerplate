package controllers.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;

import be.objectify.deadbolt.java.actions.SubjectPresent;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.Statement.Effect;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import play.Play;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Result;
import plugins.S3Plugin;
import utils.JsonResp;
import controllers.API;
import sun.misc.BASE64Encoder;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
@SubjectPresent
public class SignedAmazonS3Handler extends API{
	
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	static public Result sign() {
		if (S3Plugin.amazonS3 == null){
			return internalServerError(JsonResp.error("Amazon S3 is not configured, can not presign upload."));
		}
		JsonNode input = jsonBody();
		
		
		String iName, iContentType;
		
		// Check here for supported content types. eg. only allow "image/png" ... etc.. return badRequest() if fail.
		
		try {	
			iName = URLEncoder.encode(input.get("fileName").asText(),"utf-8");
			iContentType = input.get("contentType").asText();
		} catch (UnsupportedEncodingException e) {
			return internalServerError(JsonResp.error("Unsupported encoding."));
		}
		
		
		ObjectNode o = Json.newObject();
		
		UUID container = UUID.randomUUID();
		String fileName = container.toString() + "/" + iName;
		
		// Generate when this request expires.
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 24);
        Date expDate = cal.getTime();

        
        GeneratePresignedUrlRequest r = new GeneratePresignedUrlRequest(S3Plugin.s3Bucket, fileName, HttpMethod.PUT);
        r.setContentType(iContentType);
		r.setExpiration(expDate);
		r.addRequestParameter("x-amz-acl", "public-read");
		String signedUrl = S3Plugin.amazonS3.generatePresignedUrl(r).toString();
		
        String uploadUrl = String.format("https://%s.s3.amazonaws.com/%s", S3Plugin.s3Bucket, fileName);
		String accessKey = Play.application().configuration().getString(S3Plugin.AWS_ACCESS_KEY);
		

		
		o.put("url", uploadUrl);
		o.put("signed_request", signedUrl);
		o.put("expires", expDate.getTime());
		o.put("access_key", accessKey);
		
		return ok(o);
	}
	
	
}
