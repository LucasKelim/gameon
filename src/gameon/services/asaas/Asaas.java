package gameon.services.asaas;

import java.util.Map;

public class Asaas {

    public static Map<String, Object> inserir(String modelo, Map<String, Object> dados) {
        return new AsaasClient().post(modelo, dados);
    }
    
    public static Map<String, Object> procurarPorId(String modelo) {
    	return new AsaasClient().get(modelo);
    }
    
    public static Map<String, Object> alterar(String modelo, Map<String, Object> dados) {
    	return new AsaasClient().put(modelo, dados);
    }
    
    public static Map<String, Object> excluir(String modelo) {
    	return new AsaasClient().delete(modelo);
    }
    
    public static Map<String, Object> procurarPix(String modelo) {
    	String m = modelo + "/pixQrCode";
    	return new AsaasClient().get(m);
    }
    
}
