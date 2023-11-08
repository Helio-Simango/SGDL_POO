/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.admin;

import controllerDAO.CategoriaprodutoJpaController;
import controllerDAO.ProdutoJpaController;
import java.awt.Color;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Categoriaproduto;
import model.Produto;


/**
 *
 * @author helio
 */
public class ProdutosPanel extends javax.swing.JPanel {

    private Categoriaproduto categoria;
    private ProdutoJpaController produtoDAO;
    private CategoriaprodutoJpaController categoriaDAO;
    private int idProd;
   
    
    /**
     * Creates new form produtosPanel
     */
    public ProdutosPanel() {
        initComponents();
        produtoDAO = new ProdutoJpaController(connection.ConnectionFactory.getEmf());
        categoriaDAO = new CategoriaprodutoJpaController(connection.ConnectionFactory.getEmf());
        this.preencherTabela();
        this.preencherTabelaCategoria();
        this.habilitarActualizar(false);
    }
    
    /**
     * 
     * @param prod
     * @return 
     */
    private boolean salvarProduto(Produto prod){
        try {
            this.produtoDAO.create(prod);
            JOptionPane.showMessageDialog(this, "Produto Cadastrado com sucesso!");
            this.limparCampos();
            this.preencherTabela();
            return true;
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar o produto"+e);
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    
    /**
     *  Eliminar a categoria atravez do item selecionado na tabela
     *  busca o id, e depois elemina atravez do id do mesmo.
     * 
     * @return true se a categoria for eliminada com sucesso, false caso contrario
     */
    private boolean excluirCategoria(){
        
        int linhaSelecionada = tblCategoria.getSelectedRow();
        if(linhaSelecionada == -1){
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela");
        } else {
            int idCategoria = Integer.parseInt(tblCategoria.getValueAt(linhaSelecionada, 0).toString());
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja Realmente eliminar a Categoria?");
            // se a respostar for 0, quer dizer sim.
            if(resposta == 0){
                try {
                    this.categoriaDAO.destroy(idCategoria);
                    JOptionPane.showMessageDialog(this, "Categoria Elimimada com sucesso!");
                    this.preencherTabelaCategoria();
                    return true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, " Erro ao eliminar a categoria \n"+e.getMessage());
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return  false;
    }
    
    
    /**
     * 
     */
    private void preencherTabela(){
        List<Produto>  listaProd = this.produtoDAO.findProdutoEntities();
        DefaultTableModel tabela = (DefaultTableModel) tblProdutos.getModel();
        //SimpleDateFormat formatDate = new SimpleDateFormat("dd /MM /yyyy");    
        // Zera as linhas da table, para nao duplicar os dados
        tabela.setNumRows(0);
        for(Produto prod: listaProd){
            
            Object[] obj = new Object[]{
                prod.getIdProduto(),
                prod.getNomeProduto(),
                prod.getPrecoUnitarioProduto(),
                prod.getPrecoCompraProduto(),
                prod.getDataValidadeProduto(),
                prod.getDescricao(),
                prod.getEstado(),
                prod.getQuantidadeProduto(),             
            };
            // adiciona a nova linhha na tabela
            tabela.addRow(obj);
        }
    }

    
    /**
     * Recebe a categoria por parametro!
     * @param categoria 
     */
    public void receberCategoria(Categoriaproduto categoria){
        this.categoria = categoria;
    }
    
    /**
     * 
     */
    public void preencherTabelaCategoria(){
        List<Categoriaproduto>  listCategorias = this.categoriaDAO.findCategoriaprodutoEntities();
        DefaultTableModel tabela = (DefaultTableModel) tblCategoria.getModel();
        
        tabela.setNumRows(0);
        for(Categoriaproduto categoria: listCategorias){
            Object[] obj = new Object[]{
                categoria.getIdCategoriaProduto(),
                categoria.getCategoria(),
                categoria.getDescricao()
            };
             tabela.addRow(obj);
        }
    }
    
    private boolean setCategoriaActualizar(){
        int linhaSelecionada = tblCategoria.getSelectedRow();
        if(linhaSelecionada == -1){
            JOptionPane.showMessageDialog(this, "Selecione uma categoria na tabela");
        } else {
            int idCategoria = Integer.parseInt(tblCategoria.getValueAt(linhaSelecionada, 0).toString());
            
                try {
                    this.categoria = categoriaDAO.findCategoriaproduto(idCategoria);
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
        }
        return  false;
    }
  
     
    /**
     * @Metdo  preenche o os campos de texto apartir do id selecionado
     *         Na tabela
     * 
     * @return o id do cliente selecionado na tabela,
     *         caso nao seja selecionado um cliente retorna -1
     */
    private int editarPreencherCampos(){
        
        int LinhaSelecionada = tblProdutos.getSelectedRow();
        if(LinhaSelecionada == -1){
            JOptionPane.showMessageDialog(null, "Selecione um produto na tabela!");
        } else {   
            // pegar o ID na tabella (1 coluna) e buscar od dados do funcionario no BD
            int id = Integer.parseInt(tblProdutos.getValueAt(LinhaSelecionada, 0).toString());
            Produto prod = this.produtoDAO.findProduto(id);
            
            // Setar os Campos de textos!!
            txtNomeProduto.setText(prod.getNomeProduto());
            txtPreCompra.setText(String.valueOf(prod.getPrecoCompraProduto()));
            txtPrecoUnitario.setText(String.valueOf(prod.getPrecoUnitarioProduto()));
            txtDescricaoProduto.setText(prod.getDescricao());
            spQuantidade.setValue(prod.getQuantidadeProduto());
            cbxDataAquisao.setDate(prod.getDataCompraProduto());
            cbxDataValidade.setDate(prod.getDataValidadeProduto());
            cbxEstado.setSelectedItem(prod.getEstado());
            cbxCategoria.setSelectedItem(prod.getCategoria());
            
            /*
            if(prod.getEstado() == "Disponivel"){
                cbxEstado.setSelectedIndex(0);
            } else {
                cbxEstado.setSelectedIndex(1);
            }
            
            if(prod.getCategoria() == "Bebidas"){
                cbxCategoria.setSelectedIndex();
            } else {
               
            }
            */
            // setar o id do empregado a idEmp para uso geral
            //idEmp = emp.getIdEmpregado();
            habilitarActualizar(true);
            tabbedPaneProdutos.setSelectedIndex(0);
            
            return prod.getIdProduto();
        }
        
        return -1;
    }
    
    
    /**
     * 
     * @param idProd
     * @return 
     */
    private boolean actualizarProdutos(int idProd){
        Produto prod = this.produtoDAO.findProduto(idProd);
        
        prod.setNomeProduto(txtNomeProduto.getText());
        prod.setPrecoUnitarioProduto(Double.parseDouble(txtPrecoUnitario.getText()));
        prod.setPrecoCompraProduto(Double.parseDouble(txtPreCompra.getText()));
        prod.setQuantidadeProduto(Integer.parseInt(spQuantidade.getValue().toString()));
        prod.setDescricao(txtDescricaoProduto.getText());
        prod.setEstado(String.valueOf(cbxEstado.getSelectedItem()));
        prod.setCategoria(String.valueOf(cbxCategoria.getSelectedItem()));
        prod.setDataValidadeProduto(cbxDataAquisao.getDate());
        
        try {
            this.produtoDAO.edit(prod);
            this.limparCampos();
            this.preencherTabela();
            JOptionPane.showMessageDialog(this, "Produto Actualizado com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Erro ao Actualizar o produto"+e);
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean excluirProduto(){
        // 2 - se clicou em "Cancelar"
        int LinhaSelecionada = tblProdutos.getSelectedRow();
        if(LinhaSelecionada == -1){
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
        } else {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja Realmente Remover o Produto?");
            if(resposta == 0){
                // Sim pode Excluir
                int id = Integer.parseInt(tblProdutos.getValueAt(LinhaSelecionada, 0).toString());
                Produto prod = this.produtoDAO.findProduto(id);
                // se o estado do for Activo
                if(prod.getEstado().equals("Disponivel")){
                    try {
                        //this.empregadoDAO.destroy(id);
                        prod.setEstado("Indisponivel"); //Setar o estado Inactivo para remover o funcionario
                        this.produtoDAO.edit(prod);
                        JOptionPane.showMessageDialog(this, "Produto Removido com sucesso");
                        limparCampos();
                        preencherTabela();
                        return true;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Erro ao Remover o Beira "+ e);
                        e.printStackTrace();
                        //return false;
                    }
                } else {
                    // Se o estado for Inactivo
                    JOptionPane.showMessageDialog(this, "O estado do funcionario já é Inactivo, não pode removido!");
                }
                
            }
        }
        return false;
    }
   
    /**
     * 
     */
    private void limparCampos(){
        txtNomeProduto.setText("");
        txtPrecoUnitario.setText("");
        txtPreCompra.setText("");
        txtDescricaoProduto.setText("");
        cbxCategoria.setSelectedIndex(0);
        spQuantidade.setValue(0);
        cbxEstado.setSelectedIndex(0);
        cbxDataAquisao.setDate(null);
        cbxDataValidade.setDate(null);
        
    }
    
     /**
     *  Habilita o batao  actualizar e desabilita o batao salvar  
     *  caso a opcao seja false, faz o contrario.
     *  @param option 
     */
    private void habilitarActualizar(boolean option){
        if(option == true){
           btnCancelar.setEnabled(true);
           btnActualizar.setEnabled(true);
           btnSalvar.setEnabled(false);
           
           // Mudar A cor d Botao Salvar para uma cor opaca
           // mostrando que o btao esta desativado
           btnSalvar.setkEndColor(new Color(199,236,199));
           btnSalvar.setkStartColor(new Color(199,236,199));
           
           // Mudar A cor do botao Actualizar cor viva
           // mostrando que o botao esta activo
           btnActualizar.setkEndColor(new Color(0,153,255));
           btnActualizar.setkStartColor(new Color(0,153,255));
        } else {
           btnCancelar.setEnabled(true);
           btnSalvar.setEnabled(true);
           btnActualizar.setEnabled(false); 
           
           // Mudar A cor d Botao Salvar para uma cor opaca
           // mostrando que o btao esta desativado
           btnActualizar.setkEndColor(new Color(175,223,255));
           btnActualizar.setkStartColor(new Color(175,223,255));
           
           // Mudar A cor do botao Actualizar cor viva
           // mostrando que o botao esta activo
           btnSalvar.setkEndColor(new Color(106,192,106));
           btnSalvar.setkStartColor(new Color(106,192,106));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        produtosBackgraund = new view.extras.PanelRound();
        jLabel19 = new javax.swing.JLabel();
        panelRound7 = new view.extras.PanelRound();
        tabbedPaneProdutos = new javax.swing.JTabbedPane();
        actualizarPanel = new javax.swing.JPanel();
        panelRound4 = new view.extras.PanelRound();
        btnSalvar = new com.k33ptoo.components.KButton();
        btnCancelar = new com.k33ptoo.components.KButton();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtNomeProduto = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxDataAquisao = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        cbxDataValidade = new com.toedter.calendar.JDateChooser();
        jLabel15 = new javax.swing.JLabel();
        cbxEstado = new javax.swing.JComboBox<>();
        cbxCategoria = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricaoProduto = new javax.swing.JTextArea();
        txtPrecoUnitario = new javax.swing.JTextField();
        txtPreCompra = new javax.swing.JTextField();
        spQuantidade = new javax.swing.JSpinner();
        btnActualizar = new com.k33ptoo.components.KButton();
        listarPanel = new javax.swing.JPanel();
        panelRound3 = new view.extras.PanelRound();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        btnEditar = new com.k33ptoo.components.KButton();
        btnEliminarProduto = new com.k33ptoo.components.KButton();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        panelRound2 = new view.extras.PanelRound();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblCategoria = new javax.swing.JTable();
        btnCriarCategoria = new com.k33ptoo.components.KButton();
        btnActualizarCategoria = new com.k33ptoo.components.KButton();
        btnEliminar = new com.k33ptoo.components.KButton();
        jLabel21 = new javax.swing.JLabel();
        panelRound1 = new view.extras.PanelRound();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblCategoria1 = new javax.swing.JTable();
        btnCriarCategoria1 = new com.k33ptoo.components.KButton();
        btnActualizarCategoria1 = new com.k33ptoo.components.KButton();
        btnEliminar1 = new com.k33ptoo.components.KButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        kButton12 = new com.k33ptoo.components.KButton();
        kButton13 = new com.k33ptoo.components.KButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable5 = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        kButton14 = new com.k33ptoo.components.KButton();
        kButton15 = new com.k33ptoo.components.KButton();
        kButton16 = new com.k33ptoo.components.KButton();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        kButton17 = new com.k33ptoo.components.KButton();
        kButton18 = new com.k33ptoo.components.KButton();
        panelRound8 = new view.extras.PanelRound();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(840, 568));

        produtosBackgraund.setBackground(new java.awt.Color(246, 215, 215));
        produtosBackgraund.setRoundBottomLeft(60);
        produtosBackgraund.setRoundBottomRight(60);
        produtosBackgraund.setRoundTopLeft(60);
        produtosBackgraund.setRoundTopRight(60);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("Produtos");

        panelRound7.setBackground(new java.awt.Color(255, 255, 255));
        panelRound7.setRoundBottomLeft(60);
        panelRound7.setRoundBottomRight(60);
        panelRound7.setRoundTopLeft(60);
        panelRound7.setRoundTopRight(60);

        tabbedPaneProdutos.setBackground(new java.awt.Color(255, 255, 255));

        actualizarPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setRoundBottomLeft(60);
        panelRound4.setRoundBottomRight(60);

        btnSalvar.setText("Salvar");
        btnSalvar.setkBorderRadius(30);
        btnSalvar.setkEndColor(new java.awt.Color(106, 192, 106));
        btnSalvar.setkStartColor(new java.awt.Color(106, 192, 106));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setkBorderRadius(30);
        btnCancelar.setkEndColor(new java.awt.Color(255, 51, 102));
        btnCancelar.setkStartColor(new java.awt.Color(255, 51, 102));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel9.setText("Preço Unitario");

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel10.setText("Preço de compra");

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel11.setText("Categoria");

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel12.setText("Descrição do produto");

        txtNomeProduto.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtNomeProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtNomeProduto.setMinimumSize(new java.awt.Dimension(64, 35));
        txtNomeProduto.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("Nome do Produto");

        cbxDataAquisao.setBackground(new java.awt.Color(255, 255, 255));
        cbxDataAquisao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        cbxDataAquisao.setMinimumSize(new java.awt.Dimension(82, 35));
        cbxDataAquisao.setPreferredSize(new java.awt.Dimension(82, 35));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel14.setText("Data de Aquisição");

        jLabel5.setText("Quantidade");

        jLabel7.setText("Estado");

        cbxDataValidade.setBackground(new java.awt.Color(255, 255, 255));
        cbxDataValidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        cbxDataValidade.setMinimumSize(new java.awt.Dimension(82, 35));
        cbxDataValidade.setPreferredSize(new java.awt.Dimension(82, 35));

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel15.setText("Data de Validade");

        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Disponivel", "Indisponivel" }));
        cbxEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        cbxEstado.setMinimumSize(new java.awt.Dimension(98, 35));
        cbxEstado.setPreferredSize(new java.awt.Dimension(98, 35));

        cbxCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bebidas" }));
        cbxCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));

        txtDescricaoProduto.setColumns(20);
        txtDescricaoProduto.setLineWrap(true);
        txtDescricaoProduto.setRows(5);
        txtDescricaoProduto.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        jScrollPane1.setViewportView(txtDescricaoProduto);

        txtPrecoUnitario.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtPrecoUnitario.setMinimumSize(new java.awt.Dimension(64, 35));

        txtPreCompra.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtPreCompra.setMinimumSize(new java.awt.Dimension(64, 35));
        txtPreCompra.setName(""); // NOI18N
        txtPreCompra.setPreferredSize(new java.awt.Dimension(64, 35));
        txtPreCompra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPreCompraActionPerformed(evt);
            }
        });

        spQuantidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        spQuantidade.setMinimumSize(new java.awt.Dimension(64, 35));
        spQuantidade.setPreferredSize(new java.awt.Dimension(64, 35));

        btnActualizar.setText("Actualizar");
        btnActualizar.setkBorderRadius(30);
        btnActualizar.setkEndColor(new java.awt.Color(0, 153, 255));
        btnActualizar.setkStartColor(new java.awt.Color(0, 153, 255));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound4Layout = new javax.swing.GroupLayout(panelRound4);
        panelRound4.setLayout(panelRound4Layout);
        panelRound4Layout.setHorizontalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14)
                            .addComponent(cbxDataAquisao, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(cbxDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txtPrecoUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPreCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelRound4Layout.createSequentialGroup()
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound4Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(jLabel5))
                                    .addComponent(spQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(39, 39, 39)
                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))))
                .addContainerGap(161, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel11))
                .addGap(0, 0, 0)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                        .addGap(46, 46, 46))
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel10)
                        .addGap(0, 0, 0)
                        .addComponent(txtPreCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addGap(0, 0, 0)
                        .addComponent(txtPrecoUnitario, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(17, 17, 17)
                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelRound4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(2, 2, 2)
                                .addComponent(cbxEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelRound4Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(2, 2, 2)
                                .addComponent(spQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14))
                .addGap(0, 0, 0)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxDataAquisao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cbxDataValidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                .addGap(17, 17, 17))
        );

        javax.swing.GroupLayout actualizarPanelLayout = new javax.swing.GroupLayout(actualizarPanel);
        actualizarPanel.setLayout(actualizarPanelLayout);
        actualizarPanelLayout.setHorizontalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        actualizarPanelLayout.setVerticalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(actualizarPanelLayout.createSequentialGroup()
                .addComponent(panelRound4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneProdutos.addTab("Cadastrar Produtos", actualizarPanel);

        listarPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(60);
        panelRound3.setRoundBottomRight(60);

        tblProdutos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#ID", "Nome", "Preço Unitario", "Preço de compra", "Data de Validade", "Descrição", "Estado", "Quantidade"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblProdutos);

        jLabel1.setText("Pesquisar");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Activos", "Inactivos" }));

        jLabel2.setText("Por Estado");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Total:");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(153, 153, 153));
        jTextField2.setText("20000");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.setkBorderRadius(30);
        btnEditar.setkEndColor(new java.awt.Color(106, 192, 106));
        btnEditar.setkStartColor(new java.awt.Color(106, 192, 106));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminarProduto.setText("Eliminar");
        btnEliminarProduto.setkBorderRadius(30);
        btnEliminarProduto.setkEndColor(new java.awt.Color(255, 51, 102));
        btnEliminarProduto.setkStartColor(new java.awt.Color(255, 51, 102));
        btnEliminarProduto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarProdutoActionPerformed(evt);
            }
        });

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bebidas", "Carnes", "Lanches" }));

        jLabel3.setText("Por Categoria");

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(222, 222, 222)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44))
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound3Layout.createSequentialGroup()
                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jComboBox2)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)))
                    .addGroup(panelRound3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField1)))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEliminarProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField2)
                        .addComponent(jLabel4)))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout listarPanelLayout = new javax.swing.GroupLayout(listarPanel);
        listarPanel.setLayout(listarPanelLayout);
        listarPanelLayout.setHorizontalGroup(
            listarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listarPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        listarPanelLayout.setVerticalGroup(
            listarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPaneProdutos.addTab("Listar / Actualizar Produtos", listarPanel);

        panelRound2.setBackground(new java.awt.Color(255, 255, 255));

        tblCategoria.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#ID", "Nome da Categoria", "Descrição"
            }
        ));
        jScrollPane5.setViewportView(tblCategoria);

        btnCriarCategoria.setText("Criar Categoria");
        btnCriarCategoria.setkBorderRadius(30);
        btnCriarCategoria.setkEndColor(new java.awt.Color(106, 192, 106));
        btnCriarCategoria.setkStartColor(new java.awt.Color(106, 192, 106));
        btnCriarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarCategoriaActionPerformed(evt);
            }
        });

        btnActualizarCategoria.setText("Actualizar");
        btnActualizarCategoria.setkBorderRadius(30);
        btnActualizarCategoria.setkEndColor(new java.awt.Color(106, 192, 106));
        btnActualizarCategoria.setkStartColor(new java.awt.Color(106, 192, 106));
        btnActualizarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCategoriaActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.setkBorderRadius(30);
        btnEliminar.setkEndColor(new java.awt.Color(255, 51, 102));
        btnEliminar.setkStartColor(new java.awt.Color(255, 51, 102));
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(153, 153, 153));
        jLabel21.setText("Categoria");

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addGroup(panelRound2Layout.createSequentialGroup()
                        .addComponent(btnCriarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCriarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        tabbedPaneProdutos.addTab("Categoria", panelRound2);

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));

        tblCategoria1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#ID", "Nome do cardapio", "Descrição"
            }
        ));
        jScrollPane9.setViewportView(tblCategoria1);

        btnCriarCategoria1.setText("Criar Cardapio");
        btnCriarCategoria1.setkBorderRadius(30);
        btnCriarCategoria1.setkEndColor(new java.awt.Color(106, 192, 106));
        btnCriarCategoria1.setkStartColor(new java.awt.Color(106, 192, 106));
        btnCriarCategoria1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCriarCategoria1ActionPerformed(evt);
            }
        });

        btnActualizarCategoria1.setText("Actualizar");
        btnActualizarCategoria1.setkBorderRadius(30);
        btnActualizarCategoria1.setkEndColor(new java.awt.Color(106, 192, 106));
        btnActualizarCategoria1.setkStartColor(new java.awt.Color(106, 192, 106));
        btnActualizarCategoria1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCategoria1ActionPerformed(evt);
            }
        });

        btnEliminar1.setText("Eliminar");
        btnEliminar1.setkBorderRadius(30);
        btnEliminar1.setkEndColor(new java.awt.Color(255, 51, 102));
        btnEliminar1.setkStartColor(new java.awt.Color(255, 51, 102));
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(153, 153, 153));
        jLabel20.setText("Cadapio");

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, panelRound1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(btnCriarCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnActualizarCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 706, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCriarCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizarCategoria1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEliminar1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        tabbedPaneProdutos.addTab("Cardapio", panelRound1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "#ID", "Nome do Produto", "Qtd", "Preço"
            }
        ));
        jScrollPane7.setViewportView(jTable4);

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(153, 153, 153));
        jLabel17.setText("Produtos");

        kButton12.setText("Adicionar Produto");
        kButton12.setkBorderRadius(30);
        kButton12.setkEndColor(new java.awt.Color(106, 192, 106));
        kButton12.setkStartColor(new java.awt.Color(106, 192, 106));
        kButton12.setMinimumSize(new java.awt.Dimension(167, 35));
        kButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton12ActionPerformed(evt);
            }
        });

        kButton13.setText("Remover Produto");
        kButton13.setkBorderRadius(30);
        kButton13.setkEndColor(new java.awt.Color(255, 51, 102));
        kButton13.setkStartColor(new java.awt.Color(255, 51, 102));
        kButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton13ActionPerformed(evt);
            }
        });

        jTable5.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "#ID", "Nome de Item", "Preço"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTable5);
        if (jTable5.getColumnModel().getColumnCount() > 0) {
            jTable5.getColumnModel().getColumn(0).setResizable(false);
        }

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(153, 153, 153));
        jLabel18.setText("Itens do Cardapio");

        kButton14.setText("Novo Item");
        kButton14.setkBorderRadius(30);
        kButton14.setkEndColor(new java.awt.Color(106, 192, 106));
        kButton14.setkStartColor(new java.awt.Color(106, 192, 106));
        kButton14.setPreferredSize(new java.awt.Dimension(185, 22));

        kButton15.setText("Actualizar ");
        kButton15.setkBorderRadius(30);
        kButton15.setkEndColor(new java.awt.Color(106, 192, 106));
        kButton15.setkStartColor(new java.awt.Color(106, 192, 106));
        kButton15.setPreferredSize(new java.awt.Dimension(185, 22));

        kButton16.setText("Buscar Cardapio");
        kButton16.setkBorderRadius(30);
        kButton16.setkEndColor(new java.awt.Color(107, 107, 122));
        kButton16.setkPressedColor(new java.awt.Color(107, 107, 122));
        kButton16.setkStartColor(new java.awt.Color(107, 107, 122));
        kButton16.setPreferredSize(new java.awt.Dimension(185, 22));

        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jLabel6.setText("Nome do Cardapio");

        jLabel8.setText("Nome do Item");

        kButton17.setText("Criar Item de Cardapio");
        kButton17.setkBorderRadius(30);
        kButton17.setkEndColor(new java.awt.Color(106, 192, 106));
        kButton17.setkStartColor(new java.awt.Color(106, 192, 106));
        kButton17.setPreferredSize(new java.awt.Dimension(185, 22));

        kButton18.setText("Eliminar Item");
        kButton18.setkBorderRadius(30);
        kButton18.setkEndColor(new java.awt.Color(255, 51, 102));
        kButton18.setkStartColor(new java.awt.Color(255, 51, 102));
        kButton18.setPreferredSize(new java.awt.Dimension(185, 22));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 342, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel6)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(kButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(kButton18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jTextField4)
                                        .addGap(18, 18, 18))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(kButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(kButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE)
                                    .addComponent(kButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(kButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(kButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel8)
                        .addGap(0, 0, 0)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(1, 1, 1)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(kButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(kButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(kButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(kButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabbedPaneProdutos.addTab("Itens Do Cardapio", jPanel1);

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(tabbedPaneProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPaneProdutos)
                .addContainerGap())
        );

        panelRound8.setBackground(new java.awt.Color(255, 255, 255));
        panelRound8.setRoundBottomLeft(60);
        panelRound8.setRoundBottomRight(60);
        panelRound8.setRoundTopLeft(60);
        panelRound8.setRoundTopRight(60);

        javax.swing.GroupLayout panelRound8Layout = new javax.swing.GroupLayout(panelRound8);
        panelRound8.setLayout(panelRound8Layout);
        panelRound8Layout.setHorizontalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelRound8Layout.setVerticalGroup(
            panelRound8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout produtosBackgraundLayout = new javax.swing.GroupLayout(produtosBackgraund);
        produtosBackgraund.setLayout(produtosBackgraundLayout);
        produtosBackgraundLayout.setHorizontalGroup(
            produtosBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produtosBackgraundLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addGroup(produtosBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(produtosBackgraundLayout.createSequentialGroup()
                        .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        produtosBackgraundLayout.setVerticalGroup(
            produtosBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(produtosBackgraundLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addGap(18, 18, 18)
                .addGroup(produtosBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(produtosBackgraundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelRound7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(produtosBackgraund, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(produtosBackgraund, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void txtPreCompraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPreCompraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPreCompraActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limparCampos();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCriarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarCategoriaActionPerformed
        // TODO add your handling code here:
        DashboardAdmin formmAdmin = new DashboardAdmin();
        RegistarCategoriaForm registarCategoria = new RegistarCategoriaForm(formmAdmin, true);
        // Passarr a instancia do painel produtos ao formulario
        // de cadastro de categoria. para executar o metedo preencherTabelaCategiras
        registarCategoria.setProdutoPanel(this);
        registarCategoria.setVisible(true);
    }//GEN-LAST:event_btnCriarCategoriaActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        idProd = editarPreencherCampos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed

        Produto prod = new Produto();
        Date dataActual = new Date(System.currentTimeMillis());
        
        prod.setNomeProduto(txtNomeProduto.getText());
        prod.setPrecoUnitarioProduto(Double.parseDouble(txtPrecoUnitario.getText()));
        prod.setPrecoCompraProduto(Double.parseDouble(txtPreCompra.getText()));
        prod.setQuantidadeProduto(Integer.parseInt(spQuantidade.getValue().toString()));
        prod.setDescricao(txtDescricaoProduto.getText());
        prod.setEstado(String.valueOf(cbxEstado.getSelectedItem()));
        prod.setCategoria(String.valueOf(cbxCategoria.getSelectedItem()));
        prod.setDataCompraProduto(dataActual);
        prod.setDataValidadeProduto(cbxDataAquisao.getDate());
        
        if(salvarProduto(prod)){
         this.habilitarActualizar(false);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        
        if(actualizarProdutos(idProd)){
            this.habilitarActualizar(false);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarProdutoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarProdutoActionPerformed
        // TODO add your handling code here:
        excluirProduto();
    }//GEN-LAST:event_btnEliminarProdutoActionPerformed

    private void btnActualizarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCategoriaActionPerformed
        // TODO add your handling code here:
        DashboardAdmin formmAdmin = new DashboardAdmin();
        ActualizarCategoriaForm actualizarCategoria = new ActualizarCategoriaForm(formmAdmin, true);
        // Passarr a instancia do painel produtos ao formulario
        // de cadastro de categoria. para executar o metedo preencherTabelaCategiras
        //buscar a categoria na tebela e depois setar o valor na variavel "categoria" 
        if(setCategoriaActualizar()){
           actualizarCategoria.setCategoriaProdutos(categoria); // o mesmo que e depois enviado para esse formulario
           actualizarCategoria.setProdutoPanel(this);
           actualizarCategoria.setVisible(true);
        } else{
            System.out.println("Nenhuma categoria foi selecionada na tabela");
        }
        
       
    }//GEN-LAST:event_btnActualizarCategoriaActionPerformed

   
    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
         if(excluirCategoria()){
            System.out.println("Tudo deu certo");
        } else {
            System.out.println(" Hoo hoo ho  deu merda!");
        }
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void kButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton12ActionPerformed

    private void kButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton13ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_kButton13ActionPerformed

    private void btnCriarCategoria1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCriarCategoria1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnCriarCategoria1ActionPerformed

    private void btnActualizarCategoria1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCategoria1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarCategoria1ActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualizarPanel;
    private com.k33ptoo.components.KButton btnActualizar;
    private com.k33ptoo.components.KButton btnActualizarCategoria;
    private com.k33ptoo.components.KButton btnActualizarCategoria1;
    private com.k33ptoo.components.KButton btnCancelar;
    private com.k33ptoo.components.KButton btnCriarCategoria;
    private com.k33ptoo.components.KButton btnCriarCategoria1;
    private com.k33ptoo.components.KButton btnEditar;
    private com.k33ptoo.components.KButton btnEliminar;
    private com.k33ptoo.components.KButton btnEliminar1;
    private com.k33ptoo.components.KButton btnEliminarProduto;
    private com.k33ptoo.components.KButton btnSalvar;
    private javax.swing.JComboBox<String> cbxCategoria;
    private com.toedter.calendar.JDateChooser cbxDataAquisao;
    private com.toedter.calendar.JDateChooser cbxDataValidade;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private com.k33ptoo.components.KButton kButton12;
    private com.k33ptoo.components.KButton kButton13;
    private com.k33ptoo.components.KButton kButton14;
    private com.k33ptoo.components.KButton kButton15;
    private com.k33ptoo.components.KButton kButton16;
    private com.k33ptoo.components.KButton kButton17;
    private com.k33ptoo.components.KButton kButton18;
    private javax.swing.JPanel listarPanel;
    private view.extras.PanelRound panelRound1;
    private view.extras.PanelRound panelRound2;
    private view.extras.PanelRound panelRound3;
    private view.extras.PanelRound panelRound4;
    private view.extras.PanelRound panelRound7;
    private view.extras.PanelRound panelRound8;
    private view.extras.PanelRound produtosBackgraund;
    private javax.swing.JSpinner spQuantidade;
    private javax.swing.JTabbedPane tabbedPaneProdutos;
    private javax.swing.JTable tblCategoria;
    private javax.swing.JTable tblCategoria1;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextArea txtDescricaoProduto;
    private javax.swing.JTextField txtNomeProduto;
    private javax.swing.JTextField txtPreCompra;
    private javax.swing.JTextField txtPrecoUnitario;
    // End of variables declaration//GEN-END:variables
}
