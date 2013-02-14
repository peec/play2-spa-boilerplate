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

import pkj.no.amazon.s3.AMZS3SignatureGenerator;
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

		AMZS3SignatureGenerator gen = new AMZS3SignatureGenerator(
				Play.application().configuration().getString(S3Plugin.AWS_SECRET_KEY),
				Play.application().configuration().getString(S3Plugin.AWS_ACCESS_KEY),
				S3Plugin.s3Bucket);
		
		
		String iName = gen.normalizeFilename(input.get("fileName").asText());
		String iContentType = input.get("contentType").asText();
		
		
		// Generate when this request expires.
		String fileName = "uploads/" + UUID.randomUUID().toString() + "/" + iName;
		
		// Basic check for content types.
		String allowedContentTypes[] = {"image/png","image/jpeg","image/gif", "image/png","image/bmp", "image/jpg"};		
		if (!Arrays.asList(allowedContentTypes).contains(iContentType)){
			return badRequest(JsonResp.error("Content type of that file is not allowed."));
		}
				
		
		gen
			.setAcl("public-read")
			.setContentSizeRange(1L, 7340032L) // Max 7 megabyte upload.
			.setContentType(iContentType)
			.setFilenamePrefix("uploads/");
		
		try {
			
			ObjectNode o = Json.newObject();
			o.put("fileUrl", gen.getFileUrlFor(fileName));
			o.put("uploadUrl", gen.getUploadUrl());
			o.put("signature", gen.getEncodedSignature());
			o.put("policy", gen.getEncodedPolicy());
			o.put("accessKey", gen.getAccessKey());
			o.put("acl", gen.getAcl());
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

}
