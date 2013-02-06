package controllers.api;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;
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
		
		
		String iName, iContentType;
		
		iName = input.get("fileName").asText();
		iName = iName.replace(" ", "-");
		iName = iName.replace("+","-");
		iName = iName.replaceAll("[^A-Za-z0-9-_.]", "");
		
		iContentType = input.get("contentType").asText();
		
		// Check here for supported content types. eg. only allow "image/png" ... etc.. return badRequest() if fail.
		
		
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
		
		// Remove this line to use https:
		signedUrl = "http" + signedUrl.substring(5);
		
		// For path style uncomment:
		// signedUrl = signedUrl.replace(S3Plugin.s3Bucket + ".", "");
		// signedUrl = signedUrl.replace("amazonaws.com/", "amazonaws.com/"+S3Plugin.s3Bucket.replace(".","/")+"/");
		
        String uploadUrl = String.format("http://%s.s3.amazonaws.com/%s", S3Plugin.s3Bucket, fileName);
		String accessKey = Play.application().configuration().getString(S3Plugin.AWS_ACCESS_KEY);
		

		
		o.put("url", uploadUrl);
		o.put("signed_request", signedUrl);
		o.put("expires", expDate.getTime());
		o.put("access_key", accessKey);
		
		return ok(o);
	}
	
	
}
