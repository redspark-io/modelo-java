package io.redspark.autoconfigure.sesc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import br.org.sesc.administrativo.client.CCEClientService;
import br.org.sesc.administrativo.client.CEPClientService;
import br.org.sesc.administrativo.client.CategoriaClientService;
import br.org.sesc.administrativo.client.CatracaClientService;
import br.org.sesc.administrativo.client.ConselhoClientService;
import br.org.sesc.administrativo.client.ContaClientService;
import br.org.sesc.administrativo.client.ContabilizacaoClientService;
import br.org.sesc.administrativo.client.ContratoClientService;
import br.org.sesc.administrativo.client.FornecedorClientService;
import br.org.sesc.administrativo.client.FuncionarioClientService;
import br.org.sesc.administrativo.client.LocalClientService;
import br.org.sesc.administrativo.client.MatriculaClientService;
import br.org.sesc.administrativo.client.MensagemClientService;
import br.org.sesc.administrativo.client.NRIClientService;
import br.org.sesc.administrativo.client.NotaFiscalClientService;
import br.org.sesc.administrativo.client.OpcaoMenuClientService;
import br.org.sesc.administrativo.client.SAAPClientService;
import br.org.sesc.administrativo.client.SMSClientService;
import br.org.sesc.administrativo.client.TecnicoClientService;
import br.org.sesc.administrativo.client.UnidadeOrcamentariaClientService;
import br.org.sesc.administrativo.client.UsuarioClientService;
import br.org.sesc.administrativo.client.rest.security.AuthenticatedRestTemplate;
import br.org.sesc.administrativo.client.service.impl.CCEClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.CEPClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.CategoriaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.CatracaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.ConselhoClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.ContaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.ContabilizacaoClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.ContratoClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.FornecedorClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.FuncionarioClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.LocalClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.MatriculaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.MensagemClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.NRIClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.NotaFiscalClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.OpcaoMenuClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.SAAPClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.SMSClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.TecnicoClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.UnidadeOrcamentariaClientServiceImpl;
import br.org.sesc.administrativo.client.service.impl.UsuarioClientServiceImpl;

@Configuration
@Lazy
@ConditionalOnClass(value = AuthenticatedRestTemplate.class)
@ConditionalOnProperty(prefix = "sesc.administrativo", name = {"user", "password", "host"})
public class SescAdministrativoAutoConfiguration {
	@Value("${sesc.administrativo.user}")
	private String user;
	
	@Value("${sesc.administrativo.password}")
	private String password;
	
	@Value("${sesc.administrativo.host}")
	private String host;
	
	@Bean
	@ConditionalOnMissingBean
	public CCEClientService cceClientService() {
		return new CCEClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public CEPClientService cepClientService() {
		return new CEPClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public CategoriaClientService categoriaClientService() {
		return new CategoriaClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public CatracaClientService catracaClientService() {
		return new CatracaClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ConselhoClientService conselhoClientService() {
		return new ConselhoClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ContaClientService contaClientService() {
		return new ContaClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ContabilizacaoClientService contabilizacaoClientService() {
		return new ContabilizacaoClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public ContratoClientService contratoClientService() {
		return new ContratoClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public FornecedorClientService fornecedorClientService() {
		return new FornecedorClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public FuncionarioClientService funcionarioClientService() {
		return new FuncionarioClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public LocalClientService localClientService() {
		return new LocalClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MatriculaClientService matriculaClientService() {
		return new MatriculaClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public MensagemClientService mensagemClientService() {
		return new MensagemClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public NRIClientService nriClientService() {
		return new NRIClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public NotaFiscalClientService notaFiscalClientService() {
		return new NotaFiscalClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public OpcaoMenuClientService opcaoMenuClientService() {
		return new OpcaoMenuClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SAAPClientService saaPClientService() {
		return new SAAPClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public SMSClientService smsClientService() {
		return new SMSClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public TecnicoClientService tecnicoClientService() {
		return new TecnicoClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public UnidadeOrcamentariaClientService unidadeOrcamentariaClientService() {
		return new UnidadeOrcamentariaClientServiceImpl(user, password, host);
	}
	
	@Bean
	@ConditionalOnMissingBean
	public UsuarioClientService usuarioClientService() {
		return new UsuarioClientServiceImpl(user, password, host);
	}
}
