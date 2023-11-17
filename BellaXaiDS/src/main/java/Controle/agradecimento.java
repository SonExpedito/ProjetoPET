/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controle;

import Conexao.Conexao;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author alexe
 */
public class agradecimento extends JFrame {
        Conexao con_cliente;
    JButton voltar;
    public agradecimento(){
        Container tela = getContentPane();

        ImageIcon icone = new ImageIcon("src/imagens/icone.png"); // Substitua pelo caminho correto do ícone
        setIconImage(icone.getImage());

        con_cliente = new Conexao();
        con_cliente.conecta();

        setTitle("Sobre Nós");
        setResizable(false);
        tela.setLayout(null);

        voltar = new JButton("Voltar");
        voltar.setBounds(360, 330, 100, 30);

        voltar.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        voltar.setForeground(new Color(21, 21, 21));

        voltar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    CrudADM adm = new CrudADM();
                    adm.setVisible(true);
                    dispose();
                } catch (SQLException ex) {
                    Logger.getLogger(agradecimento.class.getName()).log(Level.SEVERE, null, ex);
                }
             
                    
            }
        });

        tela.add(voltar);
        ImagePanel backgroundPanel = new ImagePanel("src/imagens/agradecimentos.png");
        tela.add(backgroundPanel);
        backgroundPanel.setBounds(0, -20, backgroundPanel.getPreferredSize().width, backgroundPanel.getPreferredSize().height);

        setSize(800, 450);
        setVisible(true);
        setLocationRelativeTo(null);
    
    }
    
     public static void main(String[] args, int chamada) {
        agradecimento menu = new agradecimento();
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (UnsupportedLookAndFeelException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();

        } catch (ClassNotFoundException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();

        } catch (InstantiationException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();

        } catch (IllegalAccessException e) {

            System.out.println("Erro: " + e.getMessage());
            e.printStackTrace();
        }

    }

    public class ImagePanel extends JPanel {

        private Image backgroundImage;

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
