package gameon.models.BO;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCrypt;

import gameon.models.Usuario;         
import gameon.models.DAO.UsuarioDAO;  
import gameon.models.DTO.UsuarioDTO;  
import gameon.models.valuesobjects.Email;
import gameon.models.valuesobjects.Senha;

public class UsuarioBO {
	
	// INSERIR: Recebe Model -> Valida -> Hash -> Vira DTO -> DAO
	public Usuario inserir(Usuario usuario) {
		
		// 1. Validações de regra de negócio
		validar(usuario);
		
		// 2. Criptografa a senha antes de salvar
		// Pega a string pura dentro do objeto Senha
		String senhaPura = usuario.getSenha().getSenha();
		String senhaHash = hashSenha(senhaPura);
		
		// 3. Converte Model para DTO
		UsuarioDTO dto = new UsuarioDTO();
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail().getEmail()); // Extrai String do Email
		dto.setSenha(senhaHash); // Salva o Hash, não a senha pura
		
		UsuarioDAO dao = new UsuarioDAO();
		
		// 4. Verifica se já existe (usando o DTO)
		if (dao.existe(dto)) {
			System.out.println("Erro: Usuário já existe.");
			return null;
		}
		
		// 5. Salva no banco
		UsuarioDTO dtoSalvo = dao.inserir(dto);
		
		// 6. Converte de volta para Model para devolver ao Controller
		return converterParaModel(dtoSalvo);
	}
	
	public Usuario alterar(Usuario usuario) {
		validar(usuario);
		
		UsuarioDTO dto = new UsuarioDTO();
		dto.setId(usuario.getId());
		dto.setNome(usuario.getNome());
		dto.setEmail(usuario.getEmail().getEmail());
		dto.setSenha(usuario.getSenha().getSenha());
		
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioDTO dtoAlterado = dao.alterar(dto);
		
		return converterParaModel(dtoAlterado);
	}
	
	public boolean excluir(Usuario usuario) {
		UsuarioDAO dao = new UsuarioDAO();
		// O DAO só precisa do ID (int)
		return dao.excluir(usuario.getId());
	}
	
	public Usuario procurarPorId(int usuarioId){
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioDTO dto = dao.procurarPorId(usuarioId);
		
		return converterParaModel(dto);
	}
	
	public Usuario procurarPorEmail(String email){
		UsuarioDAO dao = new UsuarioDAO();
		UsuarioDTO dto = dao.procurarPorEmail(email);
		
		return converterParaModel(dto);
	}
	
	public List<Usuario> pesquisarTodos() {
		UsuarioDAO dao = new UsuarioDAO();
		List<UsuarioDTO> listaDTO = dao.pesquisarTodos();
		
		List<Usuario> listaModel = new ArrayList<>();
		
		if (listaDTO != null) {
			for (UsuarioDTO dto : listaDTO) {
				listaModel.add(converterParaModel(dto));
			}
		}

		return listaModel;
	}
	
	
	private void validar(Usuario usuario) {
		if (usuario == null) throw new IllegalArgumentException("Usuário não pode ser nulo");
		
		// O Email e Senha já se validam nos seus construtores (Value Objects),
		// aqui validamos o que sobrou.
		if (usuario.getNome() == null || usuario.getNome().trim().isEmpty()) {
			throw new IllegalArgumentException("Nome é obrigatório.");
		}
	}
	
	// Método centralizado para converter DTO -> Model
	// Isso evita repetir código em todos os métodos acima
	private Usuario converterParaModel(UsuarioDTO dto) {
		if (dto == null) return null;
		
		Usuario model = new Usuario();
		model.setId(dto.getId());
		model.setNome(dto.getNome());
		model.setCriadoEm(dto.getCriadoEm());
		model.setEmail(new Email(dto.getEmail()));
		model.setSenha(new Senha(dto.getSenha()));
		
		return model;
	}
	
	private String hashSenha(String senha) {
		return BCrypt.hashpw(senha, BCrypt.gensalt());
	}
	
	public boolean verificarSenha(String senhaDigitada, String senhaHashNoBanco) {
		return BCrypt.checkpw(senhaDigitada, senhaHashNoBanco);
	}
}