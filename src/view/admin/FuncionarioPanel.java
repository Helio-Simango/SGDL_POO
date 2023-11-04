/*s
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package view.admin;

import controllerDAO.EmpregadoJpaController;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Empregado;

/**
 *
 * @author helio
 */
public class FuncionarioPanel extends javax.swing.JPanel {

    private EmpregadoJpaController empregadoDAO;
    private int idEmp;
    
    /**
     * Creates new form FuncionarioPanel
     */
    public FuncionarioPanel() {
        initComponents();
        //inicializar o controller!!
        this.empregadoDAO = new EmpregadoJpaController(connection.ConnectionFactory.getEmf());
        //Formatar a Data para o padrao nacional
        this.cbxDataNascimento.setDateFormatString("dd /MM /yyyy");
        this.preencherTabela();
        this.btnSalvar.setEnabled(false);
        this.btnActualizar.setEnabled(false);
        this.habilitarActualizar(false);
    }
    
    /**
     * 
     * @param emp
     * @return 
     */
    private boolean salvarEmpregado(Empregado emp){ 
          try {
            //Salvar od dados no banco de dados
            this.empregadoDAO.create(emp);
            JOptionPane.showMessageDialog(null, "Funcionario Cadastrado com sucesso! ");
            return true;  // retorne verdadeiro se o funcionario for gravado com sucesso!!
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar o funcionario");
            e.printStackTrace(); 
            
            return false; // retorne falso caso o funcionario nao seja gravado!!
        }
    }
    
    /**
     * 
     * @param emp
     * @return 
     */
    private boolean actualizarEmpregado(){
        // Buscar o funcionario pelo id
        Empregado emp = this.empregadoDAO.findEmpregado(idEmp);
        String sexo = String.valueOf(cbxSexo.getSelectedItem());
        
        emp.setPrimeiroNome(txtPrimeiroNome.getText());
        emp.setApelido(txtApelido.getText());
        emp.setPrimeiroNome(txtPrimeiroNome.getText());
        emp.setBilheteIdentidade(txtBilheteIdentidade.getText());
        emp.setEmail(txtEmail.getText());
        emp.setNuit(txtNuit.getText());
        emp.setEstado(String.valueOf(cbxEstado.getSelectedItem()));
        emp.setFuncao(String.valueOf(cbxFuncao.getSelectedItem()));
        emp.setDataNascimento(cbxDataNascimento.getDate());
        emp.setEnderecoResidencia(txtEndereco.getText());
        emp.setSexo(sexo.charAt(0)); // Converter uma string "M" em um char 'M'
        //emp.setSenha("Vendedor"); O admin nao pode actualizar a senha do funcionario
         
        try {
            this.empregadoDAO.edit(emp);
            JOptionPane.showMessageDialog(null, "Dados do funcionario Actualizados com sucesso");
            this.limparCampos();
            this.preencherTabela();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao actualizar os dados do funcionario "+e);
            e.printStackTrace();
            return false;
        }
    }
    
    private boolean excluirFisico(){
        // pegar o 'id' que esta preenchido no campo
        // Antes de excluir fazer uma confirmação
        // 0 - se clicou em "sim"
        // 1 - se clicou em "nao"
        // 2 - se clicou em "Cancelar"
        int LinhaSelecionada = tblEmpregados.getSelectedRow();
        if(LinhaSelecionada == -1){
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
        } else {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja Realmente Excluir");
            if(resposta == 0){
                // Sim pode Excluir
                int id = Integer.parseInt(tblEmpregados.getValueAt(LinhaSelecionada, 0).toString());
                try {
                    this.empregadoDAO.destroy(id);
                    JOptionPane.showMessageDialog(this, "Funcionario Excluido com sucesso");
                    limparCampos();
                    preencherTabela();
                    return true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Erro ao excluir o Funcionario "+ e);
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return false;
    }
    
    private boolean excluirEmpregado(){
        // 0 - se clicou em "sim"
        // 1 - se clicou em "nao"
        // 2 - se clicou em "Cancelar"
        int LinhaSelecionada = tblEmpregados.getSelectedRow();
        if(LinhaSelecionada == -1){
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
        } else {
            int resposta = JOptionPane.showConfirmDialog(this, "Deseja Realmente Remover o funcionario?");
            if(resposta == 0){
                // Sim pode Excluir
                int id = Integer.parseInt(tblEmpregados.getValueAt(LinhaSelecionada, 0).toString());
                Empregado emp = this.empregadoDAO.findEmpregado(id);
                // se o estado do for Activo
                if(emp.getEstado().equals("Activo")){
                    try {
                        //this.empregadoDAO.destroy(id);
                        emp.setEstado("Inactivo"); //Setar o estado Inactivo para remover o funcionario
                        this.empregadoDAO.edit(emp);
                        JOptionPane.showMessageDialog(this, "Funcionario Removido com sucesso");
                        limparCampos();
                        preencherTabela();
                        return true;
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(this, "Erro ao Remover o Funcionario "+ e);
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
    
    private void preencherTabela(){
        List<Empregado>  listaEmp = this.empregadoDAO.findEmpregadoEntities();
        DefaultTableModel tabela = (DefaultTableModel) tblEmpregados.getModel();
        SimpleDateFormat formatDate = new SimpleDateFormat("dd /MM /yyyy");
        
        // Zera as linhas da table, para nao duplicar os dados
        tabela.setNumRows(0);
        for(Empregado emp: listaEmp){
            //para inserir uma nova linha na tabela, temos que passar um array
            //Object[] como parametro. A ordem dos valores no array, respeita a
            //ordem das colunas na tabela
            Object[] obj = new Object[]{
                emp.getIdEmpregado(),
                emp.getPrimeiroNome(),
                emp.getApelido(),
                emp.getSexo(),
                formatDate.format(emp.getDataNascimento()), // Data Formatada No padra nacional!!
                emp.getEmail(),
                emp.getFuncao(),
                emp.getEstado()
            };
            // adiciona a nova linhha na tabela
            tabela.addRow(obj);
        }
    }
    
    /**
     * 
     */
    private void limparCampos(){
        txtPrimeiroNome.setText("");
        txtApelido.setText("");
        txtEmail.setText("");
        txtBilheteIdentidade.setText("");
        txtNuit.setText("");
        txtEndereco.setText("");
        cbxDataNascimento.setDate(null);
        //cbxDataNascimento.setDateFormatString("dd/MM/yyyy");
        cbxEstado.setSelectedIndex(0);
        cbxFuncao.setSelectedIndex(0);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        funcionarioBackgraund = new view.extras.PanelRound();
        jLabel19 = new javax.swing.JLabel();
        panelRound7 = new view.extras.PanelRound();
        tabbedPaneFuncionario = new javax.swing.JTabbedPane();
        actualizarPanel = new javax.swing.JPanel();
        panelRound4 = new view.extras.PanelRound();
        btnSalvar = new com.k33ptoo.components.KButton();
        btnCancelar = new com.k33ptoo.components.KButton();
        cbxFuncao = new javax.swing.JComboBox<>();
        cbxEstado = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtBilheteIdentidade = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtApelido = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtPrimeiroNome = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        cbxDataNascimento = new com.toedter.calendar.JDateChooser();
        jLabel14 = new javax.swing.JLabel();
        panelRound6 = new view.extras.PanelRound();
        jLabel16 = new javax.swing.JLabel();
        txtNuit = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEndereco = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbxSexo = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        btnActualizar = new com.k33ptoo.components.KButton();
        kButton2 = new com.k33ptoo.components.KButton();
        listarPanel = new javax.swing.JPanel();
        panelRound3 = new view.extras.PanelRound();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEmpregados = new javax.swing.JTable();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        btnEditar = new com.k33ptoo.components.KButton();
        btnEminar = new com.k33ptoo.components.KButton();
        panelRound8 = new view.extras.PanelRound();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        funcionarioBackgraund.setBackground(new java.awt.Color(246, 215, 215));
        funcionarioBackgraund.setRoundBottomLeft(60);
        funcionarioBackgraund.setRoundBottomRight(60);
        funcionarioBackgraund.setRoundTopLeft(60);
        funcionarioBackgraund.setRoundTopRight(60);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("Funcionario");

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
        btnSalvar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSalvar.setkEndColor(new java.awt.Color(106, 192, 106));
        btnSalvar.setkStartColor(new java.awt.Color(106, 192, 106));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnCancelar.setkEndColor(new java.awt.Color(255, 51, 102));
        btnCancelar.setkStartColor(new java.awt.Color(255, 51, 102));
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        cbxFuncao.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        cbxFuncao.setForeground(new java.awt.Color(102, 102, 102));
        cbxFuncao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vendedor", "Cozinheiro", "Faxineiro", "Entregador" }));
        cbxFuncao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));

        cbxEstado.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        cbxEstado.setForeground(new java.awt.Color(102, 102, 102));
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

        jLabel10.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("Função");

        txtBilheteIdentidade.setForeground(new java.awt.Color(102, 102, 102));
        txtBilheteIdentidade.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(153, 153, 153));
        jLabel11.setText("Bilhete e Identidade");

        txtApelido.setForeground(new java.awt.Color(102, 102, 102));
        txtApelido.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtApelido.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel12.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(153, 153, 153));
        jLabel12.setText("Apelido");

        txtPrimeiroNome.setForeground(new java.awt.Color(102, 102, 102));
        txtPrimeiroNome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));

        jLabel13.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(153, 153, 153));
        jLabel13.setText("Primeiro Nome");

        cbxDataNascimento.setBackground(new java.awt.Color(255, 255, 255));
        cbxDataNascimento.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        cbxDataNascimento.setForeground(new java.awt.Color(102, 102, 102));
        cbxDataNascimento.setPreferredSize(new java.awt.Dimension(82, 35));

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("Data de Nascimento");

        panelRound6.setBackground(new java.awt.Color(246, 215, 215));
        panelRound6.setPreferredSize(new java.awt.Dimension(175, 175));
        panelRound6.setRoundBottomLeft(200);
        panelRound6.setRoundBottomRight(200);
        panelRound6.setRoundTopLeft(200);
        panelRound6.setRoundTopRight(200);

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/icons_Male_User.png"))); // NOI18N

        javax.swing.GroupLayout panelRound6Layout = new javax.swing.GroupLayout(panelRound6);
        panelRound6.setLayout(panelRound6Layout);
        panelRound6Layout.setHorizontalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound6Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel16)
                .addContainerGap(25, Short.MAX_VALUE))
        );
        panelRound6Layout.setVerticalGroup(
            panelRound6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound6Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabel16)
                .addGap(21, 21, 21))
        );

        txtNuit.setForeground(new java.awt.Color(102, 102, 102));
        txtNuit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtNuit.setPreferredSize(new java.awt.Dimension(64, 30));

        txtEndereco.setColumns(20);
        txtEndereco.setForeground(new java.awt.Color(102, 102, 102));
        txtEndereco.setRows(5);
        txtEndereco.setTabSize(4);
        txtEndereco.setWrapStyleWord(true);
        txtEndereco.setAutoscrolls(false);
        txtEndereco.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        jScrollPane1.setViewportView(txtEndereco);

        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("Nuit");

        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Endereço");

        txtEmail.setForeground(new java.awt.Color(102, 102, 102));
        txtEmail.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtEmail.setPreferredSize(new java.awt.Dimension(64, 35));

        jLabel7.setForeground(new java.awt.Color(153, 153, 153));
        jLabel7.setText("Email");

        cbxSexo.setForeground(new java.awt.Color(102, 102, 102));
        cbxSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "M", "F" }));

        jLabel8.setForeground(new java.awt.Color(153, 153, 153));
        jLabel8.setText("Sexo");

        btnActualizar.setText("Actualizar");
        btnActualizar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizar.setkEndColor(new java.awt.Color(0, 153, 255));
        btnActualizar.setkStartColor(new java.awt.Color(0, 153, 255));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        kButton2.setText("Adicionar Foto");
        kButton2.setkEndColor(new java.awt.Color(107, 107, 122));
        kButton2.setkStartColor(new java.awt.Color(107, 107, 122));
        kButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                kButton2ActionPerformed(evt);
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
                        .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                    .addComponent(jLabel9)
                                    .addComponent(cbxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbxFuncao, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(cbxSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                        .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(panelRound6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))))
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
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelRound4Layout.createSequentialGroup()
                        .addComponent(panelRound6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(kButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelRound4Layout.createSequentialGroup()
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
                            .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(16, 16, 16)
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel8))
                .addGroup(panelRound4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cbxSexo)
                    .addComponent(cbxEstado)
                    .addComponent(cbxFuncao, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
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

        tabbedPaneFuncionario.addTab("Cadastrar", actualizarPanel);

        listarPanel.setBackground(new java.awt.Color(255, 255, 255));

        panelRound3.setBackground(new java.awt.Color(255, 255, 255));
        panelRound3.setRoundBottomLeft(60);
        panelRound3.setRoundBottomRight(60);

        tblEmpregados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#ID", "Primeiro Nome", "Apelido", "Sexo", "Data de Nascimento", "Email", "Função", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblEmpregados);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Activos", "Inactivos" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todas", "Vendedor", "Cozinheiro", "Faxineiro", "Entregador" }));

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Por Estado");

        jLabel3.setForeground(new java.awt.Color(153, 153, 153));
        jLabel3.setText("Por Função");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Total:");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(153, 153, 153));
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
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

        btnEminar.setText("Remover");
        btnEminar.setkEndColor(new java.awt.Color(255, 51, 102));
        btnEminar.setkStartColor(new java.awt.Color(255, 51, 102));
        btnEminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound3Layout = new javax.swing.GroupLayout(panelRound3);
        panelRound3.setLayout(panelRound3Layout);
        panelRound3Layout.setHorizontalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(panelRound3Layout.createSequentialGroup()
                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelRound3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
                        .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEminar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelRound3Layout.setVerticalGroup(
            panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(0, 0, 0)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                    .addComponent(jComboBox1))
                .addGap(8, 8, 8)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelRound3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel4))
                    .addComponent(btnEminar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
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
                .addComponent(panelRound3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabbedPaneFuncionario.addTab("Listar / Actualizar", listarPanel);

        javax.swing.GroupLayout panelRound7Layout = new javax.swing.GroupLayout(panelRound7);
        panelRound7.setLayout(panelRound7Layout);
        panelRound7Layout.setHorizontalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(tabbedPaneFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 752, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        panelRound7Layout.setVerticalGroup(
            panelRound7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound7Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(tabbedPaneFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
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

        javax.swing.GroupLayout funcionarioBackgraundLayout = new javax.swing.GroupLayout(funcionarioBackgraund);
        funcionarioBackgraund.setLayout(funcionarioBackgraundLayout);
        funcionarioBackgraundLayout.setHorizontalGroup(
            funcionarioBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(funcionarioBackgraundLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(funcionarioBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel19)
                    .addGroup(funcionarioBackgraundLayout.createSequentialGroup()
                        .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        funcionarioBackgraundLayout.setVerticalGroup(
            funcionarioBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(funcionarioBackgraundLayout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(funcionarioBackgraundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelRound8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelRound7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 826, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(funcionarioBackgraund, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 563, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(funcionarioBackgraund, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void cbxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxEstadoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        Empregado emp = new Empregado();
        Date date = new Date(System.currentTimeMillis());  
        String sexo = String.valueOf(cbxSexo.getSelectedItem());
        
        emp.setPrimeiroNome(txtPrimeiroNome.getText());
        emp.setApelido(txtApelido.getText());
        emp.setPrimeiroNome(txtPrimeiroNome.getText());
        emp.setBilheteIdentidade(txtBilheteIdentidade.getText());
        emp.setEmail(txtEmail.getText());
        emp.setNuit(txtNuit.getText());
        emp.setEstado(String.valueOf(cbxEstado.getSelectedItem()));
        emp.setFuncao(String.valueOf(cbxFuncao.getSelectedItem()));
        emp.setDataCadastro(date);
        emp.setDataNascimento(cbxDataNascimento.getDate());
        emp.setEnderecoResidencia(txtEndereco.getText());
        emp.setSexo(sexo.charAt(0)); // Converter uma string "M" em um char 'M'
        emp.setSenha("Vendedor");
        
        if(salvarEmpregado(emp)){
            this.habilitarActualizar(false);
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        limparCampos();
        habilitarActualizar(false);
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        
        int LinhaSelecionada = tblEmpregados.getSelectedRow();
        if(LinhaSelecionada == -1){
            JOptionPane.showMessageDialog(null, "Selecione uma linha na tabela!");
        } else {   
            // pegar o ID na tabella (1 coluna) e buscar od dados do funcionario no BD
            int id = Integer.parseInt(tblEmpregados.getValueAt(LinhaSelecionada, 0).toString());
            Empregado emp = this.empregadoDAO.findEmpregado(id);
            
            txtPrimeiroNome.setText(emp.getPrimeiroNome());
            txtApelido.setText(emp.getApelido());
            txtBilheteIdentidade.setText(emp.getBilheteIdentidade());
            txtNuit.setText(emp.getNuit());
            txtEmail.setText(emp.getEmail());
            txtEndereco.setText(emp.getEnderecoResidencia());
            cbxDataNascimento.setDate(emp.getDataNascimento());
            
            if(emp.getEstado() == "Activo"){
                cbxEstado.setSelectedIndex(0);
            } else {
                cbxEstado.setSelectedIndex(1);
            }
            
            if(emp.getSexo() == 'M'){
                cbxSexo.setSelectedIndex(0);
            } else {
                cbxSexo.setSelectedIndex(1);
            }
            // setar o id do empregado a idEmp para uso geral
            idEmp = emp.getIdEmpregado();
            habilitarActualizar(true);
            tabbedPaneFuncionario.setSelectedIndex(0);
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed

        System.out.println("Cheguei aqui!!!");
        // depois de actualizar os dados desactivar os dados!!
        if(actualizarEmpregado()){
            habilitarActualizar(false);
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEminarActionPerformed
      
        // if(excluirFisico()){
       //     limparCampos();
       //     preencherTabela();
       // }
        if(excluirEmpregado()){
            limparCampos();
            preencherTabela();
        }
    }//GEN-LAST:event_btnEminarActionPerformed

    private void kButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_kButton2ActionPerformed
        // TODO add your handling code here:
        // adicionarImagen();
    }//GEN-LAST:event_kButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel actualizarPanel;
    private com.k33ptoo.components.KButton btnActualizar;
    private com.k33ptoo.components.KButton btnCancelar;
    private com.k33ptoo.components.KButton btnEditar;
    private com.k33ptoo.components.KButton btnEminar;
    private com.k33ptoo.components.KButton btnSalvar;
    private com.toedter.calendar.JDateChooser cbxDataNascimento;
    private javax.swing.JComboBox<String> cbxEstado;
    private javax.swing.JComboBox<String> cbxFuncao;
    private javax.swing.JComboBox<String> cbxSexo;
    private view.extras.PanelRound funcionarioBackgraund;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField2;
    private com.k33ptoo.components.KButton kButton2;
    private javax.swing.JPanel listarPanel;
    private view.extras.PanelRound panelRound3;
    private view.extras.PanelRound panelRound4;
    private view.extras.PanelRound panelRound6;
    private view.extras.PanelRound panelRound7;
    private view.extras.PanelRound panelRound8;
    private javax.swing.JTabbedPane tabbedPaneFuncionario;
    private javax.swing.JTable tblEmpregados;
    private javax.swing.JTextField txtApelido;
    private javax.swing.JTextField txtBilheteIdentidade;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextArea txtEndereco;
    private javax.swing.JTextField txtNuit;
    private javax.swing.JTextField txtPrimeiroNome;
    // End of variables declaration//GEN-END:variables
}
