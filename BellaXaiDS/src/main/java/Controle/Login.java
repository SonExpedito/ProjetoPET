/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Controle;

import Conexao.Conexao;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author alexe
 */
public class Login extends JFrame {

    Conexao con_cliente;

    JLabel logozin, admin, senha,rTitulo;
    JTextField Usuario;
    JPasswordField Senha;
    JButton logar;
    int tentativas = 3;

    public Login() {
        Container tela = getContentPane();

        con_cliente = new Conexao();
        con_cliente.conecta();

        setTitle("Login");
        setResizable(false);
        tela.setLayout(null);

        ImageIcon icone = new ImageIcon("src/imagens/logo.png"); // Substitua pelo caminho correto do ícone
        setIconImage(icone.getImage());

        ImageIcon logo = createResizedImageIcon("src/imagens/logo.png", 500, 400);
        ImageIcon user = new ImageIcon("src/imagens/usuario.png");
        ImageIcon senhas = new ImageIcon("src/imagens/senha.png");

        logozin = new JLabel(logo);
        admin = new JLabel(user);
        senha = new JLabel(senhas);
        rTitulo = new JLabel("Login");

        Usuario = new JTextField();
        Senha = new JPasswordField();
        logar = new JButton("Login");

        admin.setBounds(3, 105, 220, 30);
        senha.setBounds(3, 205, 220, 30);
        logozin.setBounds(400, 60, 400, 300);
        
        rTitulo.setBounds(200, 5, 150, 100);
        rTitulo.setForeground(new Color(162, 210, 255));
        rTitulo.setFont(new Font("Tahoma", Font.BOLD, 30));

        Usuario.setBounds(130, 105, 220, 30);
        Senha.setBounds(130, 205, 220, 30);
        Usuario.setBackground(new Color(255,200,221));
        Senha.setBackground(new Color(255,200,221));
        
        logar.setBounds(165, 305, 150, 30);
        logar.setBackground(new Color(162,210,255)); // Define a cor de fundo do botão como azul
        logar.setForeground(new Color(21,21,21));
        
        logar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String pesquisa = "select * from administrador where Usuario like '" + Usuario.getText() + "' AND Senha = '" + Senha.getText() + "'";
                con_cliente.executaSQL(pesquisa);
        
                try {
                    if (con_cliente.resultset != null && con_cliente.resultset.first()) {
                        dispose();
                        CrudADM adm = new CrudADM();
                        adm.setVisible(true);
                    } else {
                        tentativas--;
                        JOptionPane.showMessageDialog(null, "Usuário ou Senha incorreta \n" + tentativas + "  tentativas restantes.");
                        Usuario.setText("");
                        Senha.setText("");
        
                        if (tentativas <= 0) {
                            JOptionPane.showMessageDialog(null, "Você já realizou todas tentativas, fechando o programa. ");
                            con_cliente.desconecta();
                            System.exit(0);
                        }
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Um erro ocorreu. Por favor, tente novamente.");
                }
            }
        });

        add(logozin);
        add(admin);
        add(senha);
        add(rTitulo);
        add(Usuario);
        add(Senha);
        add(logar);
        
        ImagePanel backgroundPanel = new ImagePanel("src/imagens/backgroundlogin.png");
        tela.add(backgroundPanel);
        backgroundPanel.setBounds(0, -20, backgroundPanel.getPreferredSize().width, backgroundPanel.getPreferredSize().height);

        setSize(800, 450);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        Login pet = new Login();
        pet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {

            System.out.println("Erro: " + e.getMessage());

        }
    }

    private ImageIcon createResizedImageIcon(String imagePath, int width, int height) {
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(resizedImage);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public class ImagePanel extends JPanel {

        private final Image backgroundImage;

        public ImagePanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(backgroundImage.getWidth(this), backgroundImage.getHeight(this));
        }
    }
}
