package gameon.controllers;

import gameon.models.CarrinhoProduto;
import gameon.models.Cliente;
import gameon.models.Endereco;
import gameon.models.Ordem;
import gameon.models.Pix;
import gameon.models.Usuario;
import gameon.models.BO.CarrinhoProdutoBO;
import gameon.models.BO.OrdemBO;
import gameon.models.BO.OrdemProdutoBO;
import gameon.models.enums.OrdemStatus;
import gameon.utils.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PagamentoPixController {

    @FXML private ImageView imgQrCode;
    @FXML private Label lblChavePix;
    @FXML private Label lblValor;
    @FXML private Label lblMensagem;
    @FXML private TextArea txtDetalhes;
    @FXML private Button btnConfirmarPagamento;
    
    private OrdemBO ordemBO = new OrdemBO();
    private CarrinhoProdutoBO carrinhoProdutoBO = new CarrinhoProdutoBO();
    private OrdemProdutoBO ordemProdutoBO = new OrdemProdutoBO();
    private DecimalFormat df = new DecimalFormat("R$ #,##0.00");
    
    private Ordem ordem;

    @FXML
    public void initialize() {
        System.out.println("PagamentoPixController inicializado");
    }
    
    public void setOrdem(Ordem ordem) {
    	this.ordem = ordem;
    	carregarInformacoesPagamento();
    }
    
    private void carregarInformacoesPagamento() {
        try {
            // Carrega informações da sessão
            SessaoUsuario sessao = SessaoUsuario.getInstancia();
            double total = sessao.getTotalCarrinho();
            Endereco endereco = sessao.getEnderecoSelecionado();
            Usuario usuario = sessao.getUsuarioLogado();
            
            System.out.println("Total carrinho: " + total);
            System.out.println("Endereço selecionado: " + (endereco != null ? endereco.getLogradouro() : "null"));
            System.out.println("Usuário logado: " + (usuario != null ? usuario.getNome() : "null"));
            
            if (endereco == null) {
                lblMensagem.setText("Erro: Endereço não selecionado");
                lblMensagem.setStyle("-fx-text-fill: red;");
                return;
            }
            
            if (usuario == null || !(usuario instanceof Cliente)) {
                lblMensagem.setText("Erro: Usuário não é cliente");
                lblMensagem.setStyle("-fx-text-fill: red;");
                return;
            }
            
            Cliente cliente = (Cliente) usuario;
            
            // Mostra valor
            lblValor.setText(df.format(total));
            
            OrdemBO ordemBO = new OrdemBO();
            System.out.println(ordem);
            Pix pix = ordemBO.procurarPix(ordem.getId());
            
            System.out.println("Pix gerado: " + pix);
            
            // Gera chave PIX (simulação)
            String chavePix = gerarChavePix(cliente, total);
            lblChavePix.setText(chavePix);
            
            // Tenta carregar QR Code (opcional)
            try {
            	
                // Cria um QR code simples programaticamente (ou use uma imagem placeholder)
                // Se tiver uma imagem em resources, use:
                // Image qrCodeImage = new Image(getClass().getResourceAsStream("/gameon/images/qrcode.png"));
                // imgQrCode.setImage(qrCodeImage);
            } catch (Exception e) {
                System.out.println("QR Code não carregado: " + e.getMessage());
            }
            
            // Detalhes do pedido
            StringBuilder detalhes = new StringBuilder();
            detalhes.append("=== DETALHES DO PEDIDO ===\n\n");
            detalhes.append("Cliente: ").append(cliente.getNome()).append("\n");
            detalhes.append("E-mail: ").append(cliente.getEmail().getEmail()).append("\n\n");
            
            detalhes.append("Endereço de entrega:\n");
            detalhes.append(endereco.getLogradouro()).append(", ");
            detalhes.append(endereco.getNumero() > 0 ? endereco.getNumero() : "S/N").append("\n");
            detalhes.append(endereco.getBairro()).append(" - ");
            detalhes.append(endereco.getCidade()).append("/").append(endereco.getEstado()).append("\n");
            detalhes.append("CEP: ").append(endereco.getCep()).append("\n\n");
            
            detalhes.append("Método de pagamento: PIX\n");
            detalhes.append("Valor total: ").append(df.format(total)).append("\n\n");
            
            // Adiciona itens do carrinho
            detalhes.append("Itens do pedido:\n");
            List<CarrinhoProduto> itens = getItensCarrinhoCliente(cliente.getId());
            if (itens != null && !itens.isEmpty()) {
                for (CarrinhoProduto item : itens) {
                    if (item.getProduto() != null) {
                        detalhes.append("• ")
                               .append(item.getProduto().getNome())
                               .append(" x").append(item.getQuantidade())
                               .append(" = ").append(df.format(item.getProduto().getPreco() * item.getQuantidade()))
                               .append("\n");
                    }
                }
            }
            
            txtDetalhes.setText(detalhes.toString());
            txtDetalhes.setEditable(false);
            
            lblMensagem.setText("Use a chave PIX acima para pagar");
            lblMensagem.setStyle("-fx-text-fill: green;");
            
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao carregar informações: " + e.getMessage());
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }
    
    private String gerarChavePix(Cliente cliente, double valor) {
        // Simulação de chave PIX aleatória
        // Na prática, você integraria com uma API de pagamento
        String timestamp = String.valueOf(System.currentTimeMillis());
        String valorFormatado = String.format("%.2f", valor).replace(".", "").replace(",", "");
        
        // Gera uma chave PIX simulada
        return "gameon+" + cliente.getCpf().getCpf().replace(".", "").replace("-", "") + 
               "@pix.com.br?amount=" + valorFormatado + "&id=" + timestamp;
    }
    
    private List<CarrinhoProduto> getItensCarrinhoCliente(int clienteId) {
        try {
            // Use o método listarPorCliente se existir
            // Se não existir, filtre manualmente
            List<CarrinhoProduto> todosItens = carrinhoProdutoBO.pesquisarTodos();
            if (todosItens == null) return new ArrayList<>();
            
            List<CarrinhoProduto> itensCliente = new ArrayList<>();
            for (CarrinhoProduto item : todosItens) {
                if (item.getCliente() != null && item.getCliente().getId() == clienteId) {
                    itensCliente.add(item);
                }
            }
            return itensCliente;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @FXML
    private void confirmarPagamento() {
        try {
            System.out.println("Confirmando pagamento PIX...");
            
            // Cria a ordem
            Ordem ordem = criarOrdem();
            
            if (ordem == null) {
                mostrarAlerta("Erro", "Não foi possível criar o pedido.");
                return;
            }
            
            System.out.println("Ordem criada com ID: " + ordem.getId());
            
            // Simula pagamento bem-sucedido (em produção, isso viria da API PIX)
            boolean pagamentoSucesso = true;
            
            if (pagamentoSucesso) {
                // Atualiza status da ordem para PAGO
                ordem.setStatus(OrdemStatus.PAGO);
                Ordem ordemAtualizada = ordemBO.alterar(ordem);
                
                if (ordemAtualizada == null) {
                    System.out.println("Aviso: Não foi possível atualizar status da ordem");
                }
                
                // Limpa carrinho do cliente
                limparCarrinhoCliente();
                
                // Salva ordem na sessão para a tela de confirmação
                SessaoUsuario.getInstancia().setOrdemAtual(ordem);
                
                // Vai para tela de confirmação
                irParaConfirmacao();
            } else {
                mostrarAlerta("Erro no Pagamento", "Não foi possível processar o pagamento PIX.");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Falha ao processar pagamento: " + e.getMessage());
        }
    }
    
    private Ordem criarOrdem() {
        try {
//            SessaoUsuario sessao = SessaoUsuario.getInstancia();
//            
//            Ordem ordem = new Ordem();
//            ordem.setStatus(OrdemStatus.PENDENTE);
//            ordem.setMetodoPagamento(new Pix());
//            ordem.setValorTotal(sessao.getTotalCarrinho());
//            ordem.setEndereco(sessao.getEnderecoSelecionado());
//            
//            // Salva no banco
            Ordem ordemSalva = ordemBO.inserir(ordem);
//            
//            if (ordemSalva != null) {
//                System.out.println("Ordem salva no banco: ID=" + ordemSalva.getId());
//                
//                // Pega itens do carrinho do cliente atual
//                Usuario usuario = sessao.getUsuarioLogado();
//                if (usuario instanceof Cliente) {
//                    Cliente cliente = (Cliente) usuario;
//                    List<CarrinhoProduto> itensCarrinho = getItensCarrinhoCliente(cliente.getId());
//                    
//                    if (itensCarrinho != null && !itensCarrinho.isEmpty()) {
//                        // Salva os itens da ordem
//                        boolean itensSalvos = ordemProdutoBO.inserirItensDaOrdem(
//                            ordemSalva.getId(), itensCarrinho);
//                        
//                        if (itensSalvos) {
//                            System.out.println(itensCarrinho.size() + " itens salvos na ordem");
//                        } else {
//                            System.out.println("Aviso: Não foi possível salvar todos os itens da ordem");
//                        }
//                    } else {
//                        System.out.println("Aviso: Carrinho vazio ao criar ordem");
//                    }
//                }
//            }
            
            return ordemSalva;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    private void limparCarrinhoCliente() {
        try {
            Usuario usuario = SessaoUsuario.getInstancia().getUsuarioLogado();
            if (usuario instanceof Cliente) {
                Cliente cliente = (Cliente) usuario;
                
                // Busca itens do cliente
                List<CarrinhoProduto> itensCliente = getItensCarrinhoCliente(cliente.getId());
                
                // Remove cada item
                for (CarrinhoProduto item : itensCliente) {
                    carrinhoProdutoBO.excluir(item);
                }
                
                System.out.println("Carrinho do cliente " + cliente.getNome() + " limpo (" + 
                                 itensCliente.size() + " itens removidos)");
                
                // Também limpa da sessão
                SessaoUsuario.getInstancia().getCarrinhoAtual().getProdutos().clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Aviso: Erro ao limpar carrinho: " + e.getMessage());
        }
    }

    @FXML
    private void voltarParaEndereco() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/SelecionarEndereco.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) imgQrCode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Selecionar Endereço");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao voltar: " + e.getMessage());
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }
    
    private void irParaConfirmacao() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameon/views/ConfirmacaoCompra.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) imgQrCode.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("GameOn - Compra Confirmada");
            stage.centerOnScreen();
        } catch (Exception e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao confirmar: " + e.getMessage());
            lblMensagem.setStyle("-fx-text-fill: red;");
        }
    }
    
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}