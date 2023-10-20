/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view.login.forms;

import javax.swing.JPanel;

/**
 *
 * @author helio
 */
public class LoginForm extends javax.swing.JFrame {

    /**
     * Creates new form Main
     */
    public LoginForm() {
        initComponents();
        
        showPanel(new IniciarSessao());
        lblIniciarSessão.setVisible(false);
        lblTensConta.setVisible(false);
    }

    /**
     *  Mostra o Painel recebido por parametro no frame
     *  usando o cardlayout.
     * @param panel 
     */
    private void showPanel(JPanel panel) {
        containerPanel.removeAll();
        containerPanel.repaint();
        containerPanel.revalidate();
        containerPanel.add(panel);
    }
    
    /*
    /  Metedo a ser executado no Painel IniciarSessao
    /  De modo a chamar o Panel CriarConta no LoginFom
    */
    public void MetedoExecutadoEmOutroForm(){
        showPanel(new CriarConta());
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgraundPanel = new javax.swing.JPanel();
        containerPanel = new javax.swing.JPanel();
        cardalayoutPanel = new javax.swing.JPanel();
        rigthRoundedPanel = new view.extras.PanelRound();
        jPanel1 = new javax.swing.JPanel();
        lblCriaConta = new javax.swing.JLabel();
        lblUmaConta = new javax.swing.JLabel();
        lblIniciarSessão = new javax.swing.JLabel();
        lblTensConta = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        backgraundPanel.setBackground(new java.awt.Color(255, 255, 255));
        backgraundPanel.setForeground(new java.awt.Color(255, 255, 255));

        containerPanel.setBackground(new java.awt.Color(255, 255, 255));
        containerPanel.setLayout(new java.awt.CardLayout());

        cardalayoutPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout cardalayoutPanelLayout = new javax.swing.GroupLayout(cardalayoutPanel);
        cardalayoutPanel.setLayout(cardalayoutPanelLayout);
        cardalayoutPanelLayout.setHorizontalGroup(
            cardalayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 527, Short.MAX_VALUE)
        );
        cardalayoutPanelLayout.setVerticalGroup(
            cardalayoutPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 519, Short.MAX_VALUE)
        );

        containerPanel.add(cardalayoutPanel, "card2");

        rigthRoundedPanel.setBackground(new java.awt.Color(229, 85, 85));
        rigthRoundedPanel.setRoundBottomLeft(60);
        rigthRoundedPanel.setRoundBottomRight(60);
        rigthRoundedPanel.setRoundTopLeft(60);
        rigthRoundedPanel.setRoundTopRight(60);

        javax.swing.GroupLayout rigthRoundedPanelLayout = new javax.swing.GroupLayout(rigthRoundedPanel);
        rigthRoundedPanel.setLayout(rigthRoundedPanelLayout);
        rigthRoundedPanelLayout.setHorizontalGroup(
            rigthRoundedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 516, Short.MAX_VALUE)
        );
        rigthRoundedPanelLayout.setVerticalGroup(
            rigthRoundedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblCriaConta.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblCriaConta.setForeground(new java.awt.Color(0, 153, 255));
        lblCriaConta.setText("Crie uma Conta!");
        lblCriaConta.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lblCriaConta.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblCriaContaMouseClicked(evt);
            }
        });

        lblUmaConta.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        lblUmaConta.setForeground(new java.awt.Color(153, 153, 153));
        lblUmaConta.setText("Não tens uma conta?");

        lblIniciarSessão.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblIniciarSessão.setForeground(new java.awt.Color(0, 153, 255));
        lblIniciarSessão.setText("Inicie a sessão!");
        lblIniciarSessão.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblIniciarSessãoMouseClicked(evt);
            }
        });

        lblTensConta.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        lblTensConta.setForeground(new java.awt.Color(153, 153, 153));
        lblTensConta.setText("Já tens uma conta?");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblTensConta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblIniciarSessão))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblUmaConta)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCriaConta)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUmaConta)
                    .addComponent(lblCriaConta))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTensConta)
                    .addComponent(lblIniciarSessão))
                .addContainerGap(95, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout backgraundPanelLayout = new javax.swing.GroupLayout(backgraundPanel);
        backgraundPanel.setLayout(backgraundPanelLayout);
        backgraundPanelLayout.setHorizontalGroup(
            backgraundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgraundPanelLayout.createSequentialGroup()
                .addGroup(backgraundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rigthRoundedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        backgraundPanelLayout.setVerticalGroup(
            backgraundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgraundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rigthRoundedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(backgraundPanelLayout.createSequentialGroup()
                .addComponent(containerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 519, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgraundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgraundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void lblCriaContaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblCriaContaMouseClicked
        // TODO add your handling code here:
        
        // Ao se acioanar o metedo primeiro vamos desativar os componentes
        // que pertencem a tela IniciaSessao, activar os componentes que
        // pertencem a tela criarConta e chamar a mesma tela.
        lblCriaConta.setVisible(false); // desativar a label
        lblUmaConta.setVisible(false); // desativar a label
        
        //Chama o Painel CriarConta
        showPanel(new CriarConta());
        lblIniciarSessão.setVisible(true);
        lblTensConta.setVisible(true);
    }//GEN-LAST:event_lblCriaContaMouseClicked

    private void lblIniciarSessãoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblIniciarSessãoMouseClicked
        // TODO add your handling code here:
          
        // Ao se acioanar o metedo primeiro vamos desativar os componentes
        // que pertencem a tela CriarConta, activar os componentes que
        // pertencem a tela Iniciaressao e chamar a mesma tela.
       
        lblIniciarSessão.setVisible(false);// desativar a label
        lblTensConta.setVisible(false); // desativar a label
        
        // Chama o painei IniciarSessao
        showPanel(new IniciarSessao());
        lblCriaConta.setVisible(true); 
        lblUmaConta.setVisible(true); 
        
        
    }//GEN-LAST:event_lblIniciarSessãoMouseClicked

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgraundPanel;
    private javax.swing.JPanel cardalayoutPanel;
    private javax.swing.JPanel containerPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblCriaConta;
    private javax.swing.JLabel lblIniciarSessão;
    private javax.swing.JLabel lblTensConta;
    private javax.swing.JLabel lblUmaConta;
    private view.extras.PanelRound rigthRoundedPanel;
    // End of variables declaration//GEN-END:variables

}
