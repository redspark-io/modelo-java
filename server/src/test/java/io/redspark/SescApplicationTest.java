package io.redspark;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
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

    private Source responsePayload;
    private Source errorPayload = new StringSource("<Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:xsd='http://www.w3.org/2001/XMLSchema' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'>" +
                        	    "<Body>" +
                        	    "<fncValidarResponse soapenv:encodingStyle='http://schemas.xmlsoap.org/soap/encoding/' xmlns:ns1='http://auth.web_service.sesc'>" +
                        	    "<fncValidarReturn xsi:type='ns2:Document' xmlns:ns2='http://xml.apache.org/xml-soap'>" +
                        	    "<resultado>" +
                        	    "<erro>3</erro>" +
                        	    "<erro_mensagem>Hash/Opção não encontrado</erro_mensagem>" +
                        	    "</resultado>" +
                        	    "</fncValidarReturn>" +
                        	    "<fncValidarResponse>" +
                        	    "<Body>" +
                        	    "<Envelope>");

    private MockWebServiceServer mockWebServiceServer;
    
    @Override
    public void setUp() {
	super.setUp();
    }

    private void initializeWS() {
	mockWebServiceServer = MockWebServiceServer.createServer(webServiceTemplate);
    }

    protected void createMockResponse(UserAuthentication user) {
	responsePayload = new StringSource(
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
    }

    protected void signIn(UserAuthentication user) {

	// cria um mock a mais na pilha pois ele autentica e depois valida o
	// usuário
	createMockResponse(user);
	criaMockWS(2);

	ResponseEntity<Object> response = super.get("/me")
		.header(SescAuthConst.HASH_KEY, "hash")
		.header(SescAuthConst.OPC_CODIGO_KEY, codigo.toString())
		.header(SescAuthConst.PERMISSAO, "permissao").getResponse();

	assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	authentication = response.getHeaders().getFirst("Set-Cookie");
    }

    private void criaMockWS() {
	this.criaMockWS(2);
    }
    
    private void criaMockWS(int numberOfCalls) {
	initializeWS();
	for (int i = 0; i < numberOfCalls; i++) {
        	if (responsePayload == null) {
        	    mockWebServiceServer.expect(connectionTo(authenticationUrl))
        	    	.andRespond(withPayload(errorPayload));
        	} else {
        	    mockWebServiceServer.expect(connectionTo(authenticationUrl))
        	    	.andRespond(withPayload(responsePayload));
        	}
	}
    }

    protected void signOut(UserAuthentication user) {
	this.authentication = null;
    }

    @Override
    protected RequestBuilder get(String uri) {
	criaMockWS();
	return super.get(uri);
    }

    @Override
    protected RequestBuilder put(String uri) {
	criaMockWS();
	return super.put(uri);
    }

    @Override
    protected RequestBuilder post(String uri) {
	criaMockWS();
	return super.post(uri);
    }

    @Override
    protected RequestBuilder delete(String uri) {
	criaMockWS();
	return super.delete(uri);
    }
}
