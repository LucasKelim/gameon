package gameon.models.BO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gameon.factories.MetodoPagamentoFactory;
import gameon.models.Endereco;
import gameon.models.Ordem;
import gameon.models.DAO.OrdemDAO;
import gameon.models.DTO.OrdemDTO;
import gameon.models.enums.OrdemStatus;
import gameon.models.interfaces.MetodoPagamento;
import gameon.services.asaas.Asaas;

public class OrdemBO {
	
	private OrdemDAO ordemDAO = new OrdemDAO();
	private EnderecoBO enderecoBO = new EnderecoBO();
	
	public Ordem inserir(Ordem ordem) {
		System.out.println("1: " + ordem);
		if (!existe(ordem)) {
			OrdemDTO ordemDTO = toDTO(ordem);
			
			System.out.println("2: " + ordemDTO);
			
			Map<String, Object> res = Asaas.inserir(
				"payments",
				buildAsaasPayload(ordem)
			);
			
			System.out.println("3: " + res);
			
	        if (res == null || !res.containsKey("id")) {
	            return null;
	        }
		        
	        String asaasOrdem = res.get("id").toString();
	        ordemDTO.setAsaasOrdem(asaasOrdem);
	        
	        System.out.println("4: " + ordemDTO);
			
			ordemDTO = ordemDAO.inserir(ordemDTO);
			
			System.out.println("5: " + ordemDTO);
			
			return toModel(ordemDTO);
		}
		
		return null;
	}
	
	public Ordem alterar(Ordem ordem) {
		OrdemDTO ordemDTO = toDTO(ordem);
		
		ordemDTO = ordemDAO.alterar(ordemDTO);
		
		return toModel(ordemDTO);
	}
	
	public boolean excluir(Ordem ordem) {
		OrdemDTO ordemDTO = toDTO(ordem);
		
		return ordemDAO.excluir(ordemDTO.getId());
	}
	
    public Ordem procurarPorId(int ordemId) {
        OrdemDTO ordemDTO = ordemDAO.procurarPorId(ordemId);
        
        return toModel(ordemDTO);
    }
	
	public boolean existe(Ordem ordem) {
		OrdemDTO ordemDTO = toDTO(ordem);
		
		return ordemDAO.existe(ordemDTO);
	}
	
	public List<Ordem> pesquisarTodos() {
		List<OrdemDTO> ordensDTO = ordemDAO.pesquisarTodos();
		
		return toModelList(ordensDTO);
	}
	
	private String buildAsaasUrl(Ordem ordem) {
		return "payments/" + ordem.getAsaasOrdem();
	}
	
	private Map<String, Object> buildAsaasPayload(Ordem ordem) {
        Map<String, Object> dadosAsaas = new HashMap<>();
        
        String dueDate = LocalDate.now().toString(); 
        
        dadosAsaas.put("customer", ordem.getEndereco().getCliente().getAsaasCliente());
        dadosAsaas.put("billingType", ordem.getMetodoPagamento().descricao());
        dadosAsaas.put("value", ordem.getValorTotal());
        dadosAsaas.put("dueDate", dueDate);
        
        return dadosAsaas;
    }
	
	private Ordem toModel(OrdemDTO ordemDTO) {
		Endereco endereco = enderecoBO.procurarPorId(ordemDTO.getEnderecoId());
		
		OrdemStatus ordemStatus = OrdemStatus.valueOf(ordemDTO.getStatus());
		MetodoPagamento metodoPagamento = MetodoPagamentoFactory.criar(ordemDTO.getMetodoPagamento());
		
		Ordem ordem = new Ordem();
		
		ordem.setId(ordemDTO.getId());
		ordem.setStatus(ordemStatus);
		ordem.setMetodoPagamento(metodoPagamento);
		ordem.setValorTotal(ordemDTO.getValorTotal());
		ordem.setEndereco(endereco);
		ordem.setAsaasOrdem(ordemDTO.getAsaasOrdem());
		ordem.setCriadoEm(ordemDTO.getCriadoEm());
		
		System.out.println("6: " + ordem);
		
        return ordem;
    }
	    
    private List<Ordem> toModelList(List<OrdemDTO> ordensDTO) {
    	List<Ordem> ordens = new ArrayList<Ordem>();
    	
    	for (OrdemDTO ordemDTO : ordensDTO) {
    		ordens.add(toModel(ordemDTO));
    	}
    	
    	return ordens;
    }

    private OrdemDTO toDTO(Ordem ordem) {
        OrdemDTO ordemDTO = new OrdemDTO();
        
        ordemDTO.setId(ordem.getId());
        ordemDTO.setMetodoPagamento(ordem.getMetodoPagamento().descricao());
        ordemDTO.setValorTotal(ordem.getValorTotal());
        ordemDTO.setEnderecoId(ordem.getEndereco().getId());
        ordemDTO.setAsaasOrdem(ordem.getAsaasOrdem());
        ordemDTO.setCriadoEm(ordem.getCriadoEm());
        
        return ordemDTO;
    }
}
