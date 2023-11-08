/*
 * Click nbfs://nbhost/SystemFileSystem/Tclientelates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Tclientelates/GUIForms/JPanel.java to edit this tclientelate
 */
package view.admin;

import controllerDAO.ClienteJpaController;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Cliente;

/**
 *
 * @author helio
 */
public class ClientesPanel extends javax.swing.JPanel {

    private ClienteJpaController clienteDAO;
    private int idClin;
    
    /**
     * Creates new form ClientesPanel
     */
    public ClientesPanel() {
        initComponents();
        clienteDAO = new ClienteJpaController(connection.ConnectionFactory.getEmf());
        this.preencherTabela();
        this.habilitarActualizar(false);
    }
    
    private void limparCampos(){
        txtPrimeiroNome.setText("");
        txtApelido.setText("");
        txtEmail.setText("");
        txtBilheteIdentidade.setText("");
        txtNuit.setText("");
        txtEndereco.setText("");
        cbxDataNascimento.setDate(null);
        cbxEstado.setSelectedIndex(0);
        cbxSexo.setSelectedIndex(0);
    }
    
    /**
     *  Habilita o batao  actualizar e desabilita o batao salvar  
     * caso a opcao seja false, faz o contrario.
     * @param option 
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
     * 
     */
    private void preencherTabela(){
        List<Cliente> listClientes = this.clienteDAO.findClienteEntities();
        DefaultTableModel tabela = (DefaultTableModel)tblClientes.getModel();
        
        tabela.setNumRows(0);
        for(Cliente cliente: listClientes){
            Object[] obj = new Object[]{
                cliente.getIdCliente(),
                cliente.getPrimeiroNome(),
                cliente.getApelido(),
                cliente.getSexo(),
                cliente.getDataNascimento(),
                cliente.getEmail(),
                cliente.getEnderecoResidencia(),
                cliente.getEstado()
            };
            tabela.addRow(obj);
        }
    }
    
    /**
     * @Metdo  preenche o os campos de texto apartir do id selecionado
     *         Na tabela
     * 
     * @return o id do cliente selecionado na tabela,
     *         caso nao seja selecionado um cliente retorna -1
     */
    private int editarPreencherCampos(){
        
        int LinhaSelecionada = tblClientes.getSelectedRow();
        if(LinhaSelecionada == -1){
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
        } else {   
            // pegar o ID na tabella (1 coluna) e buscar od dados do funcionario no BD
            int id = Integer.parseInt(tblClientes.getValueAt(LinhaSelecionada, 0).toString());
            Cliente cliente = this.clienteDAO.findCliente(id);
            
            txtPrimeiroNome.setText(cliente.getPrimeiroNome());
            txtApelido.setText(cliente.getApelido());
            txtBilheteIdentidade.setText(cliente.getBilheteIdentidade());
            txtNuit.setText(cliente.getNuit());
            txtEmail.setText(cliente.getEmail());
            txtEndereco.setText(cliente.getEnderecoResidencia());
            cbxDataNascimento.setDate(cliente.getDataNascimento());
                    
            if(cliente.getEstado() == "Activo"){
                cbxEstado.setSelectedIndex(0);
            } else {
                cbxEstado.setSelectedIndex(1);
            }
            
            if(cliente.getSexo() == 'M'){
                cbxSexo.setSelectedIndex(0);
            } else {
                cbxSexo.setSelectedIndex(1);
            }
            // setar o id do clienteregado a idEmp para uso geral
            //idEmp = cliente.getIdCliente();
            habilitarActualizar(true);
            tabbedPaneClientes.setSelectedIndex(0);
            
            return cliente.getIdCliente();
        }
        return -1;
    }
    
    
    /**
     * 
     * @param idCliente
     * @param cliente
     * @return true se o cliente for cadastrado com sucesso, false caso contrario 
     */
    private boolean actualizarCliente(int idCliente){
        // Buscar o funcionario pelo id
        Cliente cliente = this.clienteDAO.findCliente(idCliente);
        String sexo = String.valueOf(cbxSexo.getSelectedItem());
        
        cliente.setPrimeiroNome(txtPrimeiroNome.getText());
        cliente.setApelido(txtApelido.getText());
        cliente.setPrimeiroNome(txtPrimeiroNome.getText());
        cliente.setBilheteIdentidade(txtBilheteIdentidade.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setNuit(txtNuit.getText());
        cliente.setEstado(String.valueOf(cbxEstado.getSelectedItem()));
        cliente.setDataNascimento(cbxDataNascimento.getDate());
        cliente.setEnderecoResidencia(txtEndereco.getText());
        cliente.setSexo(sexo.charAt(0)); // Converter uma string "M" em um char 'M'
        //cliente.setSenha("Vendedor"); O admin nao pode actualizar a senha do funcionario
         
        try {
            this.clienteDAO.edit(cliente);
            JOptionPane.showMessageDialog(null, "Dados do Cliente Actualizados com sucesso");
            this.limparCampos();
            this.preencherTabela();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao actualizar os dados do Cliente "+e);
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean excluirEmpregado(){
        // 0 - se clicou em "sim"
        // 1 - se clicou em "nao"
        // 2 - se clicou em "Cancelar"
        int LinhaSelecionada = tblClientes.getSelectedRow();
        if(LinhaSelecionada == -1){
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
        } else {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja Realmente Remover o Cliente?");
            if(resposta == 0){
                // Sim pode Excluir
                int id = Integer.parseInt(tblClientes.getValueAt(LinhaSelecionada, 0).toString());
                Cliente emp = this.clienteDAO.findCliente(id);
                // se o estado do for Activo
                if(emp.getEstado().equals("Activo")){
                    try {
                        //this.empregadoDAO.destroy(id);
                        emp.setEstado("Inactivo"); //Setar o estado Inactivo para remover o funcionario
                        this.clienteDAO.edit(emp);
                        JOptionPane.showMessageDialog(this, "Cliente Removido com sucesso");
                        limparCampos();
                        preencherTabela();
                        return true;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Erro ao Remover o Cliente "+ e);
                        e.printStackTrace();
                        //return false;
                    }
                } else {
                    // Se o estado for Inactivo
                    JOptionPane.showMessageDialog(this, "O estado do Cliente já é Inactivo, não pode removido!");
                }
                
            }
        }
        return false;
    }
    
    
    /**
     * 
     * @param cliente
     * @return 
     */
    private boolean salvarCliente(Cliente cliente){
        try {
            // salvar os dados do cliente na Base Dados
            this.clienteDAO.create(cliente);
            JOptionPane.showMessageDialog(null, "Cliente Cadastrado com sucesso! ");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o cliente! ");
            e.printStackTrace();
            return false;
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

        clientesBackgraund = new view.extras.PanelRound();
        jLabel19 = new javax.swing.JLabel();
        panelRound7 = new view.extras.PanelRound();
        tabbedPaneClientes = new javax.swing.JTabbedPane();
        actualizarPanel = new javax.swing.JPanel();
        panelRound4 = new view.extras.PanelRound();
        btnSalvar = new com.k33ptoo.components.KButton();
        btnCancelar = new com.k33ptoo.components.KButton();
        cbxEstado = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        txtBilheteIdentidade = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApelido = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPrimeiroNome = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxDataNascimento = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        txtNuit = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEndereco = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        btnActualizar = new com.k33ptoo.components.KButton();
        listarPanel = new javax.swing.JPanel();
        panelRound3 = new view.extras.PanelRound();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField8 = new javax.swing.JTextField();
        btnEditar = new com.k33ptoo.components.KButton();
        btnRemover = new com.k33ptoo.components.KButton();
        panelRound8 = new view.extras.PanelRound();

        setBackground(new java.awt.Color(255, 255, 255));

        clientesBackgraund.setBackground(new java.awt.Color(246, 215, 215));
        clientesBackgraund.setRoundBottomLeft(60);
        clientesBackgraund.setRoundBottomRight(60);
        clientesBackgraund.setRoundTopLeft(60);
        clientesBackgraund.setRoundTopRight(60);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("Clientes");

        panelRound7.setBackground(new java.awt.Color(255, 255, 255));
        panelRound7.setRoundBottomLeft(60);
        panelRound7.setRoundBottomRight(60);
        panelRound7.setRoundTopLeft(60);
        panelRound7.setRoundTopRight(60);

        actualizarPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelRound4.setBackground(new java.awt.Color(255, 255, 255));
        panelRound4.setRoundBottomLeft(60);
        panelRound4.setRoundBottomRight(60);

        btnSalvar.setText("Salvar");
        btnSalvar.setkEndColor(new java.awt.Color(106, 192, 106));
        btnSalvar.setkStartColor(new java.awt.Color(106, 192, 106));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setkEndColor(new java.awt.Color(255, 51, 102));
        btnCancelar.setkStartColor(new java.awt.Color(255, 51, 102));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cbxEstado.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        cbxEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));
        cbxEstado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        cbxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxEstadoActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(153, 153, 153));
        jLabel9.setText("Estado");

        txtBilheteIdentidade.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtBilheteIdentidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Bilhete e Identidade");

        txtApelido.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtApelido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtApelido.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("Apelido");

        txtPrimeiroNome.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtPrimeiroNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Primeiro Nome");

        cbxDataNascimento.setBackground(new java.awt.Color(255, 255, 255));
        cbxDataNascimento.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbxDataNascimento.setPreferredSize(new java.awt.Dimension(82, 35));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("Data de Nascimento");

        txtNuit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtNuit.setPreferredSize(new java.awt.Dimension(64, 30));

        txtEndereco.setColumns(20);
        txtEndereco.setLineWrap(true);
        txtEndereco.setRows(5);
        txtEndereco.setTabSize(4);
        txtEndereco.setAutoscrolls(false);
        txtEndereco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        jScrollPane1.setViewportView(txtEndereco);

        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Nuit");

        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Endereço");

        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtEmail.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Email");

        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Sexo");

        btnActualizar.setText("Actualizar");
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound4Layout.createSequentialGroup()
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelRound4Layout.createSequentialGroup()
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(txtNuit, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panelRound4Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(210, 210, 210)
                                .addComponent(jLabel14))
                            .addGroup(panelRound4Layout.createSequentialGroup()
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtPrimeiroNome, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtBilheteIdentidade, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtApelido, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(panelRound4Layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(cbxDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(panelRound4Layout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                            .addGroup(panelRound4Layout.createSequentialGroup()
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 241, Short.MAX_VALUE)))
                .addGap(32, 32, 32))
        );
        panelRound4Layout.setVerticalGroup(
            panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound4Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13))
                .addGap(0, 0, 0)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtPrimeiroNome)
                    .addComponent(cbxDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel6))
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addComponent(txtApelido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtBilheteIdentidade, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5))
                .addGap(2, 2, 2)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNuit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel3))
                .addGap(0, 0, 0)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(cbxSexo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout actualizarPanelLayout = new javax.swing.GroupLayout(actualizarPanel);
        actualizarPanel.setLayout(actualizarPanelLayout);
        actualizarPanelLayout.setHorizontalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        actualizarPanelLayout.setVerticalGroup(
            actualizarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        tabbedPaneClientes.addTab("Cadastrar", actualizarPanel);

        listarPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(60);
        panelRound3.setRoundBottomRight(60);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#ID", "Primeiro Nome", "Apelido", "Sexo", "Data de Nascimento", "Email", "Endereço", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblClientes);

        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Pesquisar");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Activos", "Inactivos" }));

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Por Estado");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Total:");

        jTextField8.setEditable(false);
        jTextField8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField8.setForeground(new java.awt.Color(153, 153, 153));
        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.setkEndColor(new java.awt.Color(106, 192, 106));
        btnEditar.setkStartColor(new java.awt.Color(106, 192, 106));
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnRemover.setText("Remover");
        btnRemover.setkEndColor(new java.awt.Color(255, 51, 102));
        btnRemover.setkStartColor(new java.awt.Color(255, 51, 102));
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 254, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 114, Short.MAX_VALUE)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(237, 237, 237))
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(0, 0, 0)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );

        javax.swing.GroupLayout listarPanelLayout = new javax.swing.GroupLayout(listarPanel);
        listarPanel.setLayout(listarPanelLayout);
        listarPanelLayout.setHorizontalGroup(
            listarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        listarPanelLayout.setVerticalGroup(
            listarPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(listarPanelLayout.createSequentialGroup()
                .addComponent(panelRound3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabbedPaneClientes.addTab("Listar / Actualizar", listarPanel);

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabbedPaneClientes)
                .addContainerGap())
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(tabbedPaneClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 449, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        javax.swing.GroupLayout clientesBackgraundLayout = new javax.swing.GroupLayout(clientesBackgraund);
        clientesBackgraund.setLayout(clientesBackgraundLayout);
        clientesBackgraundLayout.setHorizontalGroup(
            clientesBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesBackgraundLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(clientesBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(clientesBackgraundLayout.createSequentialGroup()
                        .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        clientesBackgraundLayout.setVerticalGroup(
            clientesBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(clientesBackgraundLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(clientesBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(clientesBackgraundLayout.createSequentialGroup()
                        .addGap(0, 215, Short.MAX_VALUE)
                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panelRound7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(clientesBackgraund, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 574, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(clientesBackgraund, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cbxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEstadoActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        Cliente cliente = new Cliente();
        String sexo = String.valueOf(cbxSexo.getSelectedItem());
        
        cliente.setPrimeiroNome(txtPrimeiroNome.getText());
        cliente.setApelido(txtApelido.getText());
        cliente.setDataNascimento(cbxDataNascimento.getDate());
        cliente.setEnderecoResidencia(txtEndereco.getText());
        cliente.setBilheteIdentidade(txtBilheteIdentidade.getText());
        cliente.setNuit(txtNuit.getText());
        cliente.setEmail(txtEmail.getText());
        cliente.setEstado(String.valueOf(cbxEstado.getSelectedItem()));
        cliente.setSexo(sexo.charAt(0));
        
        // salvado
        salvarCliente(cliente);
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.limparCampos();
        this.habilitarActualizar(false);  
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        //pesquisar por id, set Campo para serem Actualizados
        idClin = editarPreencherCampos();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        System.out.println("Cheguei aqui");
        if(actualizarCliente(idClin)){
            habilitarActualizar(false);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // if(excluirFisico()){
       //     limparCampos();
       //     preencherTabela();
       // }
        if(excluirEmpregado()){
            limparCampos();
            preencherTabela();
        }
    }//GEN-LAST:event_btnRemoverActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualizarPanel;
    private com.k33ptoo.components.KButton btnActualizar;
    private com.k33ptoo.components.KButton btnCancelar;
    private com.k33ptoo.components.KButton btnEditar;
    private com.k33ptoo.components.KButton btnRemover;
    private com.k33ptoo.components.KButton btnSalvar;
    private com.toedter.calendar.JDateChooser cbxDataNascimento;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxSexo;
    private view.extras.PanelRound clientesBackgraund;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JPanel listarPanel;
    private view.extras.PanelRound panelRound3;
    private view.extras.PanelRound panelRound4;
    private view.extras.PanelRound panelRound7;
    private view.extras.PanelRound panelRound8;
    private javax.swing.JTabbedPane tabbedPaneClientes;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtApelido;
    private javax.swing.JTextField txtBilheteIdentidade;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtEndereco;
    private javax.swing.JTextField txtNuit;
    private javax.swing.JTextField txtPrimeiroNome;
    // End of variables declaration//GEN-END:variables
}
