package io.redspark;

import static org.springframework.ws.test.client.RequestMatchers.connectionTo;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;
import io.redspark.security.UserAuthentication;

import javax.xml.transform.Source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.StringSource;

import br.org.sesc.commons.security.SescAuthConst;

public abstract class SescApplicationTest extends ApplicationTest {

	@Value("${sesc.authentication.app.codigo}")
	private Long codigo;

	@Value("${sesc.authentication.url}")
	private String authenticationUrl;

	@Autowired
	private WebServiceTemplate webServiceTemplate;

	@Override
	public void setUp() {
		super.setUp();
	}

	protected void signIn(UserAuthentication user) {
		MockWebServiceServer mockWebServiceServer = MockWebServiceServer.createServer(webServiceTemplate);
		Source responsePayload = new StringSource(
		    "<fncValidarResponse>"
		        + "<fncValidarReturn>"
		        + "<resultado>"
		        + "<uo>15</uo>"
		        + "<chapa>9999</chapa>"
		        + "<digito/>"
		        + "<usu_codigo>9999</usu_codigo>"
		        + "<usu_id>"
		        + user.getId()
		        + "</usu_id>"
		        + "<nome>"
		        + user.getLogin()
		        + "</nome>"
		        + "<banco/>"
		        + "<login/>"
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

		mockWebServiceServer.expect(connectionTo(authenticationUrl))
		    .andRespond(withPayload(responsePayload));

		ResponseEntity<Object> response = super.get("/me")
		    .header(SescAuthConst.HASH_KEY, "hash")
		    .header(SescAuthConst.OPC_CODIGO_KEY, codigo.toString())
		    .header(SescAuthConst.PERMISSAO, "permissao")
		    .expectedStatus(HttpStatus.OK)
		    .getResponse();

		authentication = response.getHeaders().getFirst("Set-Cookie");
	}

	protected void signOut(UserAuthentication user) {
		this.authentication = null;
	}
}
