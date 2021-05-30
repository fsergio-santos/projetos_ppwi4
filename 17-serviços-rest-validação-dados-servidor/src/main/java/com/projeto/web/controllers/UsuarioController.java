package com.projeto.web.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.projeto.models.config.ConfigProjeto;
import com.projeto.models.model.Role;
import com.projeto.models.model.Usuario;
import com.projeto.models.repository.filtros.UsuarioFiltro;
import com.projeto.models.repository.pagination.Pagina;
import com.projeto.models.service.DepartamentoService;
import com.projeto.models.service.RoleService;
import com.projeto.models.service.UsuarioService;
import com.projeto.models.service.componentes.PrintJasperReport;
import com.projeto.models.service.exception.ConfirmeSenhaNaoInformadoException;
import com.projeto.models.service.exception.EmailCadastradoException;
import com.projeto.models.service.exception.NegocioException;
import com.projeto.models.service.reports.JasperReportsService;
import com.projeto.models.service.reports.UsuarioReportPdf;
import com.projeto.web.response.ResponseSelect2Data;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;

@Controller
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired 
	private DepartamentoService departamentoService;
	
	@Autowired
	private UsuarioReportPdf usuarioReportPdf;

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private JasperReportsService jasperReportService;
	
	@Autowired
	private PrintJasperReport printJasperReport;
	
	
	@GetMapping(value = "/listar")
	public String listarUsuario(UsuarioFiltro usuarioFiltro, 
			HttpServletRequest request, Model model, 
			@RequestParam(value="size", required = false) Optional<Integer> size,
			@RequestParam(value="page", required = false) Optional<Integer> page,
			@RequestParam(value="sort", required = false) Optional<String> sort,
			@RequestParam(value="dir", required = false) Optional<String> dir ){
		
		 Pageable pageable = gerarPagina( dir.orElse("asc"), 
				 					      sort.orElse("id"),
				 					      page.orElse(ConfigProjeto.INITIAL_PAGE),
				 					      size.orElse(ConfigProjeto.SIZE));
		 
		
		
		Page<Usuario> listaUsuario = usuarioService.listUsuarioByPage(usuarioFiltro, pageable);
		
		Pagina<Usuario> pagina = new Pagina<>(listaUsuario, 
											  size.orElse(ConfigProjeto.SIZE), 
											  request);
		
		
		model.addAttribute("usuarioFiltro", usuarioFiltro);
		model.addAttribute("pageSizes", ConfigProjeto.PAGE_SIZES);
		model.addAttribute("size", size.orElse(ConfigProjeto.SIZE));
		model.addAttribute("dir", dir.orElse("asc"));
		model.addAttribute("sort", sort.orElse("id"));
		model.addAttribute("pagina", pagina);
		
		return "/usuario/listar";
	}

	

	@RequestMapping(value = "/cadastro", method = RequestMethod.GET)
	public String cadastroUsuario(Usuario usuario, Model model) {
		model.addAttribute("titulo", "Inclusão de Usuário");
		return "/usuario/cadastro";
	}

	@PostMapping(value = "/incluir")
	public String inserirUsuario(@Valid Usuario usuario, BindingResult result, RedirectAttributes flash) {

		if (result.hasErrors()) {
			return "/usuario/cadastro";
		}
		try {
			 usuarioService.save(usuario);
			 flash.addFlashAttribute("success","Usuário cadastrado com sucesso!");
		} catch (EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		} catch (ConfirmeSenhaNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		}
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/alterar/{id}")
	public String buscarUsuarioParaAlteracao(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Usuario usuario = usuarioService.findUserById(id);
 		model.addAttribute("departamentos", usuario.getDepartamento());
		System.out.println(usuario.getRoles());
		model.addAttribute("usuario", usuario);
		return "/usuario/cadastro";
	}

	@PostMapping(value = "/alterar")
	public String salvarEdicaoUsuario(@Valid Usuario usuario, BindingResult result, Model model, RedirectAttributes flash ) {
		if (result.hasErrors()) {
			model.addAttribute("usuario", usuario);
			return "/usuario/cadastro";
		}
		try {
			usuario = usuarioService.update(usuario);
			flash.addFlashAttribute("success","Usuário alterado com sucesso!");
		} catch (EmailCadastradoException e) {
			result.rejectValue("email", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		} catch (ConfirmeSenhaNaoInformadoException e) {
			result.rejectValue("confirmPassword", e.getMessage(), e.getMessage());
			return "/usuario/cadastro";
		} 
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/excluir/{id}")
	public String buscarUsuarioParaExclusao(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Usuario usuario =  usuarioService.findUserById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/excluir";
	}

	@PostMapping(value = "/excluir")
	public String excluirEdicaoUsuario(Usuario usuario, RedirectAttributes flash) {
	   	usuarioService.deleteById(usuario.getId());
		flash.addFlashAttribute("success", "Usuário excluído com sucesso");
		return "redirect:/usuario/listar";
	}

	@GetMapping(value = "/consultar/{id}")
	public String buscarUsuarioParaConsulta(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Usuario usuario =  usuarioService.findUserById(id);
		model.addAttribute("usuario", usuario);
		return "/usuario/consultar";
	}
	
	
	@RequestMapping(value="/relatorio", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> imprimirRelatorioPdf(HttpServletRequest request){
		
		List<Usuario> listaUsuario = usuarioService.findAll();
		ByteArrayInputStream pdf = usuarioReportPdf.generateReport(request, listaUsuario); 
		
		return ResponseEntity.ok()
				             .header("Content-Disposition", "inline; filename=report.pdf")
				             .contentType(MediaType.APPLICATION_PDF)
				             .body(new InputStreamResource(pdf));
		
	}
	
	@ModelAttribute("roles")
	public List<Role> listaRoles(){
		return roleService.findAll();
	}
	
	
	@ResponseBody
	@GetMapping(value="/buscaDepartamento")
	public List<ResponseSelect2Data> selectDepartamento(@RequestParam(value="q", required = false) String query){
		return StringUtils.isEmpty(query)
				       ? departamentoService.buscaSemParamentro() 
				       : departamentoService.buscaPorParametroDepartamento(query);
	}
	

	
	@ExceptionHandler(NegocioException.class)
	public String handlerException( NegocioException ex, RedirectAttributes flash ){
		 flash.addFlashAttribute("error", ex.getMessage());
		 return "redirect:/usuario/listar";
	}
	
	
	@GetMapping(value="/pdfLista")
	public ResponseEntity<byte[]> imprimeRelatorioPdfFromLista(){
		
		List<Usuario> listaUsuario = usuarioService.findAll();

		printJasperReport.setFile("rel_usuarios");
	    printJasperReport.setCollection(listaUsuario);
	    
	    byte[] relatorio = jasperReportService.generateListReport(printJasperReport);
		
	    return ResponseEntity.ok()
	    					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
	    					.body(relatorio);
	}
	
	
	@GetMapping(value="/pdfsql")
	public ResponseEntity<byte[]> imprimeRelatorioPdfSql(){
	    
		printJasperReport.addParams("CODIGO_INICIAL", 200L);
		printJasperReport.addParams("CODIGO_FINAL", 300L);
	    
	    printJasperReport.setFile("consulta_usuario");
	    
	    byte[] relatorio = jasperReportService.generateNativeSqlReport(printJasperReport);
		
	    return ResponseEntity.ok()
	    					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
	    					.body(relatorio);
	}
	
	
	
	@GetMapping(value="/download")
	public void downloadRelatorioPdf(HttpServletResponse response){
		
		printJasperReport.addParams("CODIGO_INICIAL", 200L);
		printJasperReport.addParams("CODIGO_FINAL", 300L);
	    printJasperReport.setFile("consulta_usuario");
	 
	    response.setContentType("application/x-download");
	    response.setHeader("Content-Disposition", String.format("attachment; filename=\"usuario.pdf\""));
	    
	    try {
	        JasperPrint jasperPrint = jasperReportService.downloadReportPdf(printJasperReport);
			OutputStream out = response.getOutputStream();
	     	JasperExportManager.exportReportToPdfStream(jasperPrint, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JRException e) {
			e.printStackTrace();
		}
	    
	}
	
	
	private Pageable gerarPagina(String direcao, String atributo, Integer pag, Integer limite) {
        Pageable pageable = null;
          
        if ( direcao.equals("asc")) {
        	pageable = PageRequest.of(pag, limite, Sort.Direction.ASC, atributo);
        } else {
        	pageable = PageRequest.of(pag, limite, Sort.Direction.DESC, atributo);
        }
		return pageable;
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
