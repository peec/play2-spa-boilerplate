package controllers.api;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
import org.joda.time.DateTime;

import be.objectify.deadbolt.java.actions.SubjectPresent;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

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
		
		String 
			iName = input.get("fileName").asText(),
			iContentType = input.get("contentType").asText();
		
		
		ObjectNode o = Json.newObject();
		
		UUID container = UUID.randomUUID();
		String fileName = container.toString() + "/" + iName;
		
		// Generate when this request expires.
		Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 4);
        Date expDate = cal.getTime();
        
        
        GeneratePresignedUrlRequest r = new GeneratePresignedUrlRequest(S3Plugin.s3Bucket, fileName, HttpMethod.PUT);
        r.setContentType(iContentType);
		r.setExpiration(expDate);
		
		
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
