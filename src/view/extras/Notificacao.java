package view.extras;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTargetAdapter;



public final class Notificacao extends javax.swing.JDialog {

    private JFrame fram = null;
    private Animator animator;
    private Glass glass;
    private boolean show;    
    private String icone;
    
    public static final int NADA = -1;
    public static final int BOTAO_SIM = 0;
    public static final int BOTAO_NAO = 1;
    public static final int BOTAO_OK = 2;  
    
    public static final TipoOpcao OPCAO_UNICA = TipoOpcao.OPCAO_UNICA;
    public static final TipoOpcao OPCAO_DUPLA = TipoOpcao.OPCAO_DUPLA;
    
    public static final Icone ICONE_INFORMACAO = Icone.INFORMACAO;
    public static final Icone ICONE_SUCESSO = Icone.SUCESSO;
    public static final Icone ICONE_AVISO = Icone.AVISO;
    public static final Icone ICONE_QUESTAO = Icone.QUESTAO;
    public static final Icone ICONE_ERRO = Icone.ERRO;
    
    private static int opcaoSelecionada = NADA;       
    
    private enum Icone {
        INFORMACAO, SUCESSO, AVISO, QUESTAO, ERRO
    }
    
    private enum TipoOpcao {
        OPCAO_UNICA, OPCAO_DUPLA
    }
    
    private Notificacao(JFrame frame, Icone icone, TipoOpcao tipoOpcao, String mensagem) {
        super(frame, true);
        this.fram = frame;  
        init(icone, tipoOpcao);
        showMessage(mensagem);
    }
    
    private Notificacao(java.awt.Dialog dialog, Icone icone, TipoOpcao tipoOpcao, String mensagem) {
        super(dialog, true);
        //this.dialog = dialog;  
        init(icone, tipoOpcao);
        showMessage(mensagem);
    }
    
    private void init(Icone icone, TipoOpcao tipoOpcao) {
        initComponents();
        setBackground(new Color(0, 0, 0, 0));
        StyledDocument doc = txt.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        txt.setOpaque(false);
        txt.setBackground(new Color(0, 0, 0, 0));
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        switch(icone) {
            case INFORMACAO : this.icone = "/icons/info.png";break;
            case SUCESSO    : this.icone = "/icons/sucesso.png";break;
            case AVISO      : this.icone = "/icons/alerta.png";break;
            case QUESTAO    : this.icone = "/icons/pergunta.png";break;
            case ERRO       : this.icone = "/icons/erro.png";break;
        }
        painelBotoes.removeAll();
        painelBotoes.repaint();
        painelBotoes.revalidate();
        lblIcone.setIcon(new ImageIcon(getClass().getResource(this.icone)));
        if(tipoOpcao == OPCAO_UNICA)
            painelBotoes.add(botoes1);
        else if(tipoOpcao == OPCAO_DUPLA)
            painelBotoes.add(botoes2);        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeMessage();
            }
        });
        animator = new Animator(350, new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                float f = show ? fraction : 1f - fraction;
                glass.setAlpha(f - f * 0.3f);
                setOpacity(f);
            }

            @Override
            public void end() {
                if (show == false) {
                    dispose();
                    glass.setVisible(false);
                }
            }
        });
        animator.setResolution(0);
        animator.setAcceleration(.5f);
        animator.setDeceleration(.5f);
        setOpacity(0f);
        glass = new Glass();
    }
    
    public static int mostrarDialogoDeOpcaoDupla(JFrame frame, String mensagem, Icone icone) {
        notificacao = new Notificacao(frame, icone, OPCAO_DUPLA, mensagem);
        
        return getOption();
    }
    
    public static int mostrarDialogoDeOpcaoSingular(JFrame frame, String mensagem, Icone icone) {
        notificacao = new Notificacao(frame, icone, OPCAO_UNICA, mensagem);
        
        return getOption();
    }
    
    private void showMessage(String message) {
        fram.setGlassPane(glass);
        glass.setVisible(true);
        txt.setText(message);
        setLocationRelativeTo(fram);
        startAnimator(true);
        setVisible(true);
    }
    
    private void closeMessage() {
        startAnimator(false);
    }
    
    private static int getOption() {
        return opcaoSelecionada;
    }
    
    private void startAnimator(boolean show) {
        if (animator.isRunning()) {
            float f = animator.getTimingFraction();
            animator.stop();
            animator.setStartFraction(1f - f);
        } else {
            animator.setStartFraction(0f);
        }
        this.show = show;
        animator.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblIcone = new javax.swing.JLabel();
        painelBotoes = new javax.swing.JPanel();
        botoes1 = new javax.swing.JPanel();
        buttonCustom1 = new view.extras.ButtonCustom();
        botoes2 = new javax.swing.JPanel();
        btnNao = new view.extras.ButtonCustom();
        btnSim = new view.extras.ButtonCustom();
        txt = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        lblIcone.setBackground(new java.awt.Color(255, 255, 255));
        lblIcone.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblIcone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/info.png"))); // NOI18N

        painelBotoes.setBackground(new java.awt.Color(51, 51, 255));
        painelBotoes.setMinimumSize(new java.awt.Dimension(330, 35));
        painelBotoes.setOpaque(false);
        painelBotoes.setPreferredSize(new java.awt.Dimension(330, 35));
        painelBotoes.setLayout(new java.awt.CardLayout());

        botoes1.setBackground(new java.awt.Color(51, 51, 255));
        botoes1.setMinimumSize(new java.awt.Dimension(330, 35));
        botoes1.setOpaque(false);
        botoes1.setPreferredSize(new java.awt.Dimension(330, 35));

        buttonCustom1.setBackground(new java.awt.Color(153, 153, 153));
        buttonCustom1.setText("Ok");
        buttonCustom1.setColorHover(new java.awt.Color(204, 204, 255));
        buttonCustom1.setColorPressed(new java.awt.Color(153, 153, 255));
        buttonCustom1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        buttonCustom1.setMaximumSize(new java.awt.Dimension(134, 35));
        buttonCustom1.setMinimumSize(new java.awt.Dimension(134, 35));
        buttonCustom1.setPreferredSize(new java.awt.Dimension(134, 35));
        buttonCustom1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCustom1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout botoes1Layout = new javax.swing.GroupLayout(botoes1);
        botoes1.setLayout(botoes1Layout);
        botoes1Layout.setHorizontalGroup(
            botoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoes1Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(98, Short.MAX_VALUE))
        );
        botoes1Layout.setVerticalGroup(
            botoes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoes1Layout.createSequentialGroup()
                .addComponent(buttonCustom1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        painelBotoes.add(botoes1, "card2");

        botoes2.setBackground(new java.awt.Color(51, 51, 255));
        botoes2.setMinimumSize(new java.awt.Dimension(330, 35));
        botoes2.setOpaque(false);
        botoes2.setPreferredSize(new java.awt.Dimension(330, 35));

        btnNao.setBackground(new java.awt.Color(245, 71, 71));
        btnNao.setText("Não");
        btnNao.setColorHover(new java.awt.Color(255, 74, 74));
        btnNao.setColorPressed(new java.awt.Color(235, 61, 61));
        btnNao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnNao.setMaximumSize(new java.awt.Dimension(163, 35));
        btnNao.setMinimumSize(new java.awt.Dimension(163, 35));
        btnNao.setPreferredSize(new java.awt.Dimension(163, 35));
        btnNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNaoActionPerformed(evt);
            }
        });

        btnSim.setText("Sim");
        btnSim.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSim.setMaximumSize(new java.awt.Dimension(163, 35));
        btnSim.setMinimumSize(new java.awt.Dimension(163, 35));
        btnSim.setPreferredSize(new java.awt.Dimension(163, 35));
        btnSim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout botoes2Layout = new javax.swing.GroupLayout(botoes2);
        botoes2.setLayout(botoes2Layout);
        botoes2Layout.setHorizontalGroup(
            botoes2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoes2Layout.createSequentialGroup()
                .addComponent(btnNao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSim, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        botoes2Layout.setVerticalGroup(
            botoes2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(botoes2Layout.createSequentialGroup()
                .addGroup(botoes2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNao, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSim, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        painelBotoes.add(botoes2, "card3");

        txt.setEditable(false);
        txt.setFont(new java.awt.Font("sansserif", 0, 14)); // NOI18N
        txt.setForeground(new java.awt.Color(76, 76, 76));
        txt.setText("Message Text\nSimple");
        txt.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIcone, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIcone, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(txt, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(painelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNaoActionPerformed
        opcaoSelecionada = 1;
        closeMessage();
    }//GEN-LAST:event_btnNaoActionPerformed

    private void btnSimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimActionPerformed
        opcaoSelecionada = 0;
        closeMessage();
    }//GEN-LAST:event_btnSimActionPerformed

    private void buttonCustom1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCustom1ActionPerformed
        opcaoSelecionada = 2;
        closeMessage();
    }//GEN-LAST:event_buttonCustom1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel botoes1;
    private javax.swing.JPanel botoes2;
    private view.extras.ButtonCustom btnNao;
    private view.extras.ButtonCustom btnSim;
    private view.extras.ButtonCustom buttonCustom1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblIcone;
    private javax.swing.JPanel painelBotoes;
    private javax.swing.JTextPane txt;
    // End of variables declaration//GEN-END:variables
    private static Notificacao notificacao;
}
