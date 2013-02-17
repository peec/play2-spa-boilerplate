package pkj.no.amazon.tests;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.codehaus.jackson.node.ObjectNode;
import org.fluentlenium.core.Fluent;
import org.junit.*;

import pkj.no.amazon.s3.AMZS3SignatureGenerator;
import play.Logger;
import play.Play;
import play.mvc.*;
import play.test.*;
import play.libs.Json;
import play.libs.WS;
import play.libs.F.*;
import plugins.S3Plugin;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;



public class AMZS3SignatureGeneratorTest {


	
	@Test
	public void testCreateSignature() throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException{
		
		AMZS3SignatureGenerator gen = new AMZS3SignatureGenerator("secret","access", "bucketname");
		
		// Note, these must be changed if more things is added.
		
		assertThat(gen.getAccessKey())
			.isEqualTo("access");
		assertThat(gen.getSecretKey())
			.isEqualTo("secret");
		
		gen.setAcl("public-read");
		gen.setContentSizeRange(100L, 1000L);
		gen.setFilenamePrefix("upload/");
		
		
		// TODO more tests.
	}
	
	
	
}
