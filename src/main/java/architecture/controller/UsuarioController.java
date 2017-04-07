package architecture.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import architecture.service.UsuarioService;
import br.com.ronan.model.Usuario;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
		
	@RequestMapping(value = "/findAll", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Usuario>> findAll(){
		return new ResponseEntity<List<Usuario>>(usuarioService.listar(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(HttpServletResponse response){
		
		String csv = "TESTE;ESCRITA;CSV";

		response.setContentType("text/csv");
		response.setHeader("Content-Disposition", "attachment; filename=\"teste.csv\"");
		
		try {
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write(csv.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
