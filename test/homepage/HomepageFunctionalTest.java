package homepage;

import static org.fest.assertions.Assertions.*;
import static play.mvc.Http.Status.*;
import static play.test.Helpers.*;

import java.util.Map;

import org.junit.*;

import play.mvc.*;
import play.test.FakeApplication;
import play.test.Helpers;
import utils.AppHelpers;

public class HomepageFunctionalTest {
	FakeApplication app;
	
	@Before
	public void startApp() throws Exception {
		Map<String,String> conf = AppHelpers.conf();
		app = fakeApplication(conf);
		Helpers.start(app);
	}
	
	@After
	public void stopApp() throws Exception {
		Helpers.stop(app);
	}
	
	@Test
	public void callIndexHomePage() {
		Result result = callAction(controllers.routes.ref.Application.index());
		assertThat(status(result)).isEqualTo(OK);
		assertThat(contentType(result)).isEqualTo("text/html");
		assertThat(charset(result)).isEqualTo("utf-8");
	}

}
