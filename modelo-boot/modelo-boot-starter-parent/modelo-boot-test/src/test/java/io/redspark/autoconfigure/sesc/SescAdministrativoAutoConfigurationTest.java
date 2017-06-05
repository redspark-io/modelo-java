package io.redspark.autoconfigure.sesc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

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
import io.redspark.ApplicationTestConfig;
import io.redspark.SampleApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ApplicationTestConfig.class, SampleApplication.class})
public class SescAdministrativoAutoConfigurationTest {
	
  @Autowired
	private ApplicationContext context;
	
	@Test
	public void mustLoadSCCEClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("cceClientService");
		Assert.assertTrue(wasLoadBean);

		CCEClientService bean = context.getBean(CCEClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadcepClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("cepClientService");
		Assert.assertTrue(wasLoadBean);

		CEPClientService bean = context.getBean(CEPClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadCategoriaClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("categoriaClientService");
		Assert.assertTrue(wasLoadBean);

		CategoriaClientService bean = context.getBean(CategoriaClientService.class);
		Assert.assertNotNull(bean);
	}

	@Test
	public void mustLoadCatracaClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("catracaClientService");
		Assert.assertTrue(wasLoadBean);

		CatracaClientService bean = context.getBean(CatracaClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadConselhoClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("conselhoClientService");
		Assert.assertTrue(wasLoadBean);

		ConselhoClientService bean = context.getBean(ConselhoClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadCcontaClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("conselhoClientService");
		Assert.assertTrue(wasLoadBean);

		ContaClientService bean = context.getBean(ContaClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadContabilizacaoClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("contabilizacaoClientService");
		Assert.assertTrue(wasLoadBean);

		ContabilizacaoClientService bean = context.getBean(ContabilizacaoClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadContratoClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("contratoClientService");
		Assert.assertTrue(wasLoadBean);

		ContratoClientService bean = context.getBean(ContratoClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadFornecedorClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("fornecedorClientService");
		Assert.assertTrue(wasLoadBean);

		FornecedorClientService bean = context.getBean(FornecedorClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadFuncionarioClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("funcionarioClientService");
		Assert.assertTrue(wasLoadBean);

		FuncionarioClientService bean = context.getBean(FuncionarioClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadLocalClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("localClientService");
		Assert.assertTrue(wasLoadBean);

		LocalClientService bean = context.getBean(LocalClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadMatriculaClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("matriculaClientService");
		Assert.assertTrue(wasLoadBean);

		MatriculaClientService bean = context.getBean(MatriculaClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadMensagemClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("mensagemClientService");
		Assert.assertTrue(wasLoadBean);

		MensagemClientService bean = context.getBean(MensagemClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadNRIClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("nriClientService");
		Assert.assertTrue(wasLoadBean);

		NRIClientService bean = context.getBean(NRIClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadnotaFiscalClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("notaFiscalClientService");
		Assert.assertTrue(wasLoadBean);

		NotaFiscalClientService bean = context.getBean(NotaFiscalClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadOpcaoMenuClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("opcaoMenuClientService");
		Assert.assertTrue(wasLoadBean);

		OpcaoMenuClientService bean = context.getBean(OpcaoMenuClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadSAAPClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("saaPClientService");
		Assert.assertTrue(wasLoadBean);

		SAAPClientService bean = context.getBean(SAAPClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadSMSClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("smsClientService");
		Assert.assertTrue(wasLoadBean);

		SMSClientService bean = context.getBean(SMSClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadTecnicoClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("tecnicoClientService");
		Assert.assertTrue(wasLoadBean);

		TecnicoClientService bean = context.getBean(TecnicoClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadUnidadeOrcamentariaClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("unidadeOrcamentariaClientService");
		Assert.assertTrue(wasLoadBean);

		UnidadeOrcamentariaClientService bean = context.getBean(UnidadeOrcamentariaClientService.class);
		Assert.assertNotNull(bean);
	}
	
	@Test
	public void mustLoadUsuarioClientService() throws Exception {
		boolean wasLoadBean = context.containsBean("usuarioClientService");
		Assert.assertTrue(wasLoadBean);

		UsuarioClientService bean = context.getBean(UsuarioClientService.class);
		Assert.assertNotNull(bean);
	}
	
	
	
	
}
