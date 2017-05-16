package io.redspark;

import javax.xml.transform.Source;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.ws.test.client.RequestMatchers;
import org.springframework.ws.test.client.ResponseCreators;
import org.springframework.xml.transform.StringSource;

import br.org.sesc.commons.security.SescAuthConst;
import io.redspark.SampleApplication;
import io.redspark.commons.security.UserAuthentication;
import io.redspark.sampleapp.client.RequestBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, classes = {ApplicationTestConfig.class, SampleApplication.class}, properties = "server.port=10001")
public abstract class ApplicationTest {
	@Value("${sesc.authentication.app.codigo}")
	private Long codigo;

	@Value("${sesc.authentication.url}")
	private String authenticationUrl;

	@Autowired
	@Qualifier("securityWebService")
	private WebServiceTemplate webServiceTemplate;
	
  private final String server;
  protected String authentication;
	
	protected void signInWs(UserAuthentication user) {
		MockWebServiceServer mockWebServiceServer = MockWebServiceServer.createServer(webServiceTemplate);
		Source responsePayload = createMock(user);

		mockWebServiceServer.expect(RequestMatchers.connectionTo(authenticationUrl))
		.andRespond(ResponseCreators.withPayload(responsePayload));

		ResponseEntity<Object> response = get("/me")
				.header(SescAuthConst.HASH_KEY, "hash")
				.header(SescAuthConst.OPC_CODIGO_KEY, codigo.toString())
				.header(SescAuthConst.PERMISSAO, "permissao").getResponse();

		Assert.assertThat(response.getStatusCode(), Matchers.equalTo(HttpStatus.OK));
		authentication = response.getHeaders().getFirst("Set-Cookie");
	}

	private StringSource createMock(UserAuthentication user) {
		return new StringSource(
				"<fncValidarResponse>"
						+ "<fncValidarReturn>"
						+ "<resultado>"
						+ "<uo>15</uo>"
						+ "<chapa>9999</chapa>"
						+ "<digito/>"
						+ "<usu_codigo>"
						+ user.getId()
						+ "</usu_codigo>"
						+ "<usu_id>"
						+ user.getId()
						+ "</usu_id>"
						+ "<nome>"
						+ user.getLogin()
						+ "</nome>"
						+ "<banco/>"
						+ "<login>"
						+ user.getLogin()
						+ "</login>"
						+ "<parametro/>"
						+ "<path_inicial>http://www.intranet2homologacao.sescsp.org.br:9090/ssti/</path_inicial>"
						+ "<ip>192.168.8.197</ip>"
						+ "<data_login>{ts '2015-03-18 00:00:00'}</data_login>"
						+ "<sis_codigo>"
						+ codigo
						+ "</sis_codigo>"
						+ "<erro>0</erro>"
						+ "<erro_mensagem>Consulta executada com sucesso!</erro_mensagem>"
						+ "</resultado>" + "</fncValidarReturn>"
						+ "</fncValidarResponse>");
	}

  public ApplicationTest() {
    this.server = "http://localhost:10001";
  }

  protected void signOut(UserAuthentication user) {
    this.authentication = null;
  }

  protected RequestBuilder get(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.GET).header("Cookie", authentication);
  }

  protected RequestBuilder put(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.PUT).header("Cookie", authentication);
  }

  protected RequestBuilder post(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.POST).header("Cookie", authentication);
  }

  protected RequestBuilder delete(String uri) {
    return new RequestBuilder(server, uri, HttpMethod.DELETE).header("Cookie", authentication);
  }

  // With path variables
  protected RequestBuilder get(String uri, Object... path) {
    return get(String.format(uri, path));
  }

  protected RequestBuilder post(String uri, Object... path) {
    return post(String.format(uri, path));
  }

  protected RequestBuilder put(String uri, Object... path) {
    return put(String.format(uri, path));
  }

  protected RequestBuilder delete(String uri, Object... path) {
    return delete(String.format(uri, path));
  }

  protected TestRestTemplate template() {
    return new TestRestTemplate();
  }
}
