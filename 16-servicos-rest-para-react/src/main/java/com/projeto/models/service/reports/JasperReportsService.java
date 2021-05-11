package com.projeto.models.service.reports;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projeto.models.config.ConfigProjeto;
import com.projeto.models.service.componentes.PrintJasperReport;
import com.projeto.models.service.exception.NegocioException;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportsService {

	@Autowired
	private DataSource dataSource;
	
	
	public JasperPrint downloadReportPdf(PrintJasperReport printJasperReport) {
		addParamDefault(printJasperReport);	
		try {
			JasperReport fileOut = openCompileReports(printJasperReport.getFile());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(fileOut, printJasperReport.getParams(), dataSource.getConnection());
			return jasperPrint;
		} catch (JRException e) {
			throw new NegocioException("Erro na criação do relatório em PDF ", e.getCause());
		} catch(  SQLException e) {
			throw new NegocioException("Erro na conexão com o banco de dados  ", e.getCause());
		}
	}
	
	
	public byte[] generateNativeSqlReport(PrintJasperReport printJasperReport) {
		addParamDefault(printJasperReport);	
		try {
			JasperReport fileOut = openCompileReports(printJasperReport.getFile());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(fileOut, printJasperReport.getParams(), dataSource.getConnection());
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			throw new NegocioException("Erro na criação do relatório em PDF ", e.getCause());
		} catch(  SQLException e) {
			throw new NegocioException("Erro na conexão com o banco de dados  ", e.getCause());
		}
	}
	
		
	
	public byte[] generateListReport(PrintJasperReport printJasperReport) {
		addParamDefault(printJasperReport);	
		try {
			JasperReport fileOut = openCompileReports(printJasperReport.getFile());
			JasperPrint jasperPrint = JasperFillManager
					.fillReport(fileOut, printJasperReport.getParams(),
								new JRBeanCollectionDataSource(printJasperReport.getCollection()));
			return JasperExportManager.exportReportToPdf(jasperPrint);
		} catch (JRException e) {
			throw new NegocioException("Erro na criação do relatório em PDF ", e.getCause());
		}
	}


	private void addParamDefault(PrintJasperReport printJasperReport) {
		printJasperReport.addParams(JRParameter.REPORT_LOCALE, new Locale("pt", "BR"));
	}
	
	
	private JasperReport openCompileReports(String file) {

		try {
			InputStream fileIn = this.getClass()
					 .getResourceAsStream(
							 ConfigProjeto.DIR_RELATORIOS + file +".jrxml");
			JasperReport fileOut = JasperCompileManager.compileReport(fileIn);
			return fileOut;
		} catch (JRException e) {
			e.printStackTrace();
			throw new NegocioException("Erro na criação do relatório em PDF ", e.getCause());
		}
		
	}
	
	
}
