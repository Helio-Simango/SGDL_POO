/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package view.admin;

import controllerDAO.CategoriaprodutoJpaController;
import javax.swing.JOptionPane;
import model.Categoriaproduto;

/**
 *
 * @author helio
 */
public class RegistarCategoriaForm extends javax.swing.JDialog {

    private CategoriaprodutoJpaController categoriaDAO;
    private ProdutosPanel produtosPanel;
    
    /**
     * Creates new form RegistarCategoria
     */
    public RegistarCategoriaForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        categoriaDAO = new CategoriaprodutoJpaController(connection.ConnectionFactory.getEmf());
    }
    
    private void limparCampos(){
        txtNomeCategoria.setText("");
        txtDescricaoCategoria.setText("");
    }
     
    /**
     *  O metedo recebe uma instancia do paneil produtosPanel
     *  de modo a usar a o metedo para actualizar a a tabela de categoria
     *  apos efectuar o registo.
     */
    public void setProdutoPanel(ProdutosPanel produtosPanel){
        this.produtosPanel = produtosPanel;
    }
    
    /**
     * 
     * @param categoria
     * @return 
     */
    private boolean salvarCategoria(Categoriaproduto categoria){
        try {
            this.categoriaDAO.create(categoria);
            JOptionPane.showMessageDialog(this, "Categoria cadastrada com sucesso!");
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar a categoria" +e.getMessage());
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

        backgraundCategoria = new javax.swing.JPanel();
        txtNomeCategoria = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescricaoCategoria = new javax.swing.JTextArea();
        btnSalvar = new com.k33ptoo.components.KButton();
        jLabel3 = new javax.swing.JLabel();
        btnCancelar1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        backgraundCategoria.setBackground(new java.awt.Color(255, 255, 255));

        txtNomeCategoria.setForeground(new java.awt.Color(102, 102, 102));
        txtNomeCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        txtNomeCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeCategoriaActionPerformed(evt);
            }
        });

        jLabel1.setForeground(new java.awt.Color(153, 153, 153));
        jLabel1.setText("Nome da Categoria");

        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Descrição da Categoria");

        txtDescricaoCategoria.setColumns(20);
        txtDescricaoCategoria.setForeground(new java.awt.Color(102, 102, 102));
        txtDescricaoCategoria.setLineWrap(true);
        txtDescricaoCategoria.setRows(5);
        txtDescricaoCategoria.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(202, 202, 202)));
        jScrollPane1.setViewportView(txtDescricaoCategoria);

        btnSalvar.setText("Salvar");
        btnSalvar.setkBorderRadius(30);
        btnSalvar.setkEndColor(new java.awt.Color(106, 192, 106));
        btnSalvar.setkStartColor(new java.awt.Color(106, 192, 106));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI Emoji", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(125, 121, 121));
        jLabel3.setText("Registar Categoria");

        btnCancelar1.setForeground(new java.awt.Color(102, 102, 102));
        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/imagens/icons_back.png"))); // NOI18N
        btnCancelar1.setText("Cancelar");
        btnCancelar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnCancelar1MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout backgraundCategoriaLayout = new javax.swing.GroupLayout(backgraundCategoria);
        backgraundCategoria.setLayout(backgraundCategoriaLayout);
        backgraundCategoriaLayout.setHorizontalGroup(
            backgraundCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgraundCategoriaLayout.createSequentialGroup()
                .addGroup(backgraundCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgraundCategoriaLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(btnCancelar1))
                    .addGroup(backgraundCategoriaLayout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jLabel3))
                    .addGroup(backgraundCategoriaLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(backgraundCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1)
                            .addComponent(txtNomeCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                            .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        backgraundCategoriaLayout.setVerticalGroup(
            backgraundCategoriaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgraundCategoriaLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(btnCancelar1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgraundCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgraundCategoria, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeCategoriaActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
       
        if(txtNomeCategoria.getText().equals("")){
            System.out.println("Preencha o campo nome da categoria");
            
        } else {
           if(txtDescricaoCategoria.getText().equals("")){
                 System.out.println("Preencha o campo descriacao da categoria!!");
            } else {
                Categoriaproduto categoria = new Categoriaproduto();
                categoria.setCategoria(txtNomeCategoria.getText());
                categoria.setDescricao(txtDescricaoCategoria.getText());
                this.salvarCategoria(categoria);
                System.out.println("Categoria registada com sucesso!!");
                // a usaro metdo da classe produtosPanel, da inicializacao
                // da instancia recebida por parametro!
                this.produtosPanel.preencherTabelaCategoria();
                limparCampos();
            }
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelar1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCancelar1MouseClicked
         if(txtNomeCategoria.getText().equals("") && txtDescricaoCategoria.getText().equals("")){
            this.dispose();
        } else{
            limparCampos();
        }  
    }//GEN-LAST:event_btnCancelar1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(RegistarCategoriaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistarCategoriaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistarCategoriaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistarCategoriaForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                RegistarCategoriaForm dialog = new RegistarCategoriaForm(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgraundCategoria;
    private javax.swing.JLabel btnCancelar1;
    private com.k33ptoo.components.KButton btnSalvar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea txtDescricaoCategoria;
    private javax.swing.JTextField txtNomeCategoria;
    // End of variables declaration//GEN-END:variables
}
