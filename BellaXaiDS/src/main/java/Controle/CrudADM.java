/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author alexe
 */
public class CrudADM extends JFrame {

    Conexao con_cliente;
    JLabel rRegistro, rNome, rEspecie, rRaca, rCorPredo, rData, rSexo,
            rPesquisar, rTitulo, rimagem;
    JTextField tRegistro, tNome, tEspecie, tRaca, tCorPredo, tPesquisar, tSexo;
    JFormattedTextField tData;
    JButton primeiro, anterior, proximo, ultimo, registro, gravar, alterar, excluir, pesquisar, sair;

    JTable tblClientes;
    JScrollPane scp_tabela;

    public CrudADM() throws SQLException {
        Container tela = getContentPane();

        rRegistro = new JLabel("Registro");
        rNome = new JLabel("Nome");
        rEspecie = new JLabel("Espécie");
        rRaca = new JLabel("Raça");
        rCorPredo = new JLabel("Cor");
        rSexo = new JLabel("Sexo");
        rData = new JLabel("Data de Nascimento");
        rPesquisar = new JLabel("Pesquisar");

        tRegistro = new JTextField();
        tNome = new JTextField();
        tEspecie = new JTextField();
        tRaca = new JTextField();
        tCorPredo = new JTextField();
        tPesquisar = new JTextField();
        tSexo = new JTextField();

        criarMenu();

        try {

            MaskFormatter mData = new MaskFormatter("####/##/##");

            tData = new JFormattedTextField(mData);
            tData.setBounds(100, 140, 50, 20);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        rTitulo = new JLabel("PET");
        rTitulo.setBounds(450, 3, 150, 50);
        rTitulo.setForeground(new Color(162, 210, 255));
        rTitulo.setFont(new Font("Tahoma", Font.BOLD, 20));

        ImageIcon imagemcrud = createResizedImageIcon("src/imagens/artcrud.png", 420, 320);
        rimagem = new JLabel(imagemcrud);
        rimagem.setBounds(480, 20, 500, 400);

        tela.setBackground(new Color(237, 241, 243));

        ImageIcon icone = new ImageIcon("src/imagens/icone.png"); // Substitua pelo caminho correto do ícone
        setIconImage(icone.getImage());

        con_cliente = new Conexao();
        con_cliente.conecta();

        setTitle("Pet-ADM");
        setResizable(false);
        tela.setLayout(null);

        primeiro = new JButton("Primeiro");
        anterior = new JButton("Anterior");
        proximo = new JButton("Próximo");
        ultimo = new JButton("Último");

        registro = new JButton("Nova Registro");
        gravar = new JButton("Gravar");
        alterar = new JButton("Alterar");
        excluir = new JButton("Excluir");
        pesquisar = new JButton("Pesquisar-Nome");
        sair = new JButton("Voltar");

        sair.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                Login log = new Login();
                log.setVisible(true);

            }
        });

        primeiro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    con_cliente.resultset.first();
                    mostrar_Dados();
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possivel acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        anterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (con_cliente.resultset.isFirst()) {
                        JOptionPane.showMessageDialog(null, "Ja esta no primeiro registro");
                    } else {
                        con_cliente.resultset.previous();
                        mostrar_Dados();
                    }
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possivel acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        proximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (con_cliente.resultset.isLast()) {
                        JOptionPane.showMessageDialog(null, "Ja esta no ultimo registro");
                    } else {
                        con_cliente.resultset.next();
                        mostrar_Dados();
                    }
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possivel acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        ultimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    con_cliente.resultset.last();
                    mostrar_Dados();
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possivel acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        registro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tRegistro.setText("");
                tCorPredo.setText("");
                tEspecie.setText("");
                tNome.setText("");
                tRaca.setText("");
                tSexo.setText("");
                tData.setText("");
            }
        });

        gravar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = tNome.getText();
                String especie = tEspecie.getText();
                String raca = tRaca.getText();
                String corPredo = tCorPredo.getText();
                String sexo = tSexo.getText();
                String dataNasc = tData.getText();
                try {
                    String insert_sql = "INSERT INTO animais (Nome_Pet, Especie, Raca, Cor_Predominante, Sexo, Data_Nasc) VALUES ('" + nome + "', '" + especie + "', '" + raca + "', '" + corPredo + "', '" + sexo + "', '" + dataNasc + "')";
                    con_cliente.statement.executeUpdate(insert_sql);
                    JOptionPane.showMessageDialog(null, "Gravado com sucesso");

                    con_cliente.executaSQL("SELECT * FROM animais ORDER BY Num_Registro");
                    preencherTabela();
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possível gravar registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        alterar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = tNome.getText();
                String especie = tEspecie.getText();
                String raca = tRaca.getText();
                String corPredo = tCorPredo.getText();
                String sexo = tSexo.getText();
                String dataNasc = tData.getText();
                String sql;
                String msg = "";

                try {
                    if (tRegistro.getText().equals("")) {
                        sql = "INSERT INTO animais (Nome_Pet, Especie, Raca, Cor_Predominante, Sexo, Data_Nasc) VALUES ('" + nome + "', '" + especie + "', '" + raca + "', '" + corPredo + "', '" + sexo + "', '" + dataNasc + "')";
                        msg = "Gravado com sucesso";
                    } else {
                        sql = "UPDATE animais SET Nome_Pet='" + nome + "', Especie='" + especie + "', Raca='" + raca + "', Cor_Predominante='" + corPredo + "', Sexo='" + sexo + "', Data_Nasc='" + dataNasc + "' WHERE Num_Registro=" + tRegistro.getText();
                        msg = "Alterado com sucesso";
                    }

                    con_cliente.statement.executeUpdate(sql);
                    JOptionPane.showMessageDialog(null, "Gravado com sucesso");
                    con_cliente.executaSQL("SELECT * FROM animais ORDER BY Num_Registro");
                    preencherTabela();

                } catch (SQLException errosql) {
                    JOptionPane.showMessageDialog(null, "Erro ao atualizar");
                }
            }
        });

        excluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String sql;

                try {
                    int resposta = JOptionPane.showConfirmDialog(rootPane, "Deseja excluir?");
                    if (resposta == 0) {
                        sql = "DELETE FROM animais WHERE Num_Registro = " + tRegistro.getText();
                        int excluir = con_cliente.statement.executeUpdate(sql);
                        if (excluir == 1) {
                            JOptionPane.showMessageDialog(null, "Excluído com sucesso");
                            con_cliente.executaSQL("SELECT * FROM animais ORDER BY Num_Registro");
                            con_cliente.resultset.first();
                            preencherTabela();
                            posicionarRegistro();
                        } else {
                            JOptionPane.showMessageDialog(null, "Operação cancelada pelo usuário");
                        }
                    }

                } catch (SQLException errosql) {
                    JOptionPane.showMessageDialog(null, "Erro ao deletar");
                }
            }
        });

        pesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String pesquisa = "SELECT * FROM animais WHERE Nome_Pet LIKE '" + tPesquisar.getText() + "%'";
                    con_cliente.executaSQL(pesquisa);
                    if (con_cliente.resultset.first()) {
                        preencherTabela();
                    } else {
                        JOptionPane.showMessageDialog(null, "Não existe");
                    }
                } catch (SQLException errosql) {
                    JOptionPane.showMessageDialog(null, "Dados não encontrados");
                }
            }
        });

        primeiro.setBounds(60, 320, 100, 30);
        tela.add(primeiro);
        anterior.setBounds(150, 320, 100, 30);
        tela.add(anterior);
        proximo.setBounds(240, 320, 100, 30);
        tela.add(proximo);
        ultimo.setBounds(330, 320, 100, 30);
        tela.add(ultimo);

        registro.setBounds(500, 320, 130, 30);
        tela.add(registro);
        gravar.setBounds(635, 320, 100, 30);
        tela.add(gravar);
        alterar.setBounds(730, 320, 100, 30);
        tela.add(alterar);
        excluir.setBounds(830, 320, 100, 30);
        tela.add(excluir);

        pesquisar.setBounds(60, 355, 150, 30);
        tela.add(pesquisar);

        sair.setBounds(750, 355, 150, 30);
        tela.add(sair);

        primeiro.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        primeiro.setForeground(new Color(21, 21, 21));

        anterior.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        anterior.setForeground(new Color(21, 21, 21));

        proximo.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        proximo.setForeground(new Color(21, 21, 21));

        ultimo.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        ultimo.setForeground(new Color(21, 21, 21));

        registro.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        registro.setForeground(new Color(21, 21, 21));

        gravar.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        gravar.setForeground(new Color(21, 21, 21));

        alterar.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        alterar.setForeground(new Color(21, 21, 21));

        excluir.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        excluir.setForeground(new Color(21, 21, 21));

        pesquisar.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        pesquisar.setForeground(new Color(21, 21, 21));

        sair.setBackground(new Color(162, 210, 255)); // Define a cor de fundo do botão como azul
        sair.setForeground(new Color(21, 21, 21));

        rPesquisar.setBounds(50, 320, 200, 50);
        tPesquisar.setBounds(220, 355, 250, 30);

        tblClientes = new javax.swing.JTable();
        scp_tabela = new javax.swing.JScrollPane();

        tblClientes.setBounds(50, 400, 900, 200);
        scp_tabela.setBounds(50, 400, 900, 200);

        tela.add(tblClientes);

        tela.add(scp_tabela);

        tblClientes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        tblClientes.setFont(new java.awt.Font("Arial", 1, 12));

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},
                    {null, null, null, null, null, null, null},},
                new String[]{"Num_Registro", "Nome_Pet", "Especie", "Raca", "Cor_Predominante", "Sexo", "Data_Nasc"}) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        scp_tabela.setViewportView(tblClientes);
        tblClientes.setAutoCreateRowSorter(true);

        rRegistro.setBounds(50, 40, 150, 50);
        rNome.setBounds(50, 80, 150, 50);
        rEspecie.setBounds(50, 120, 150, 50);
        rRaca.setBounds(50, 160, 150, 50);
        rCorPredo.setBounds(50, 200, 150, 50);
        rSexo.setBounds(50, 240, 150, 50);
        rData.setBounds(500, 40, 150, 50);

        rRegistro.setForeground(new Color(21, 21, 21));
        rNome.setForeground(new Color(21, 21, 21));
        rEspecie.setForeground(new Color(21, 21, 21));
        rRaca.setForeground(new Color(21, 21, 21));
        rCorPredo.setForeground(new Color(21, 21, 21));
        rPesquisar.setForeground(new Color(21, 21, 21));
        rSexo.setForeground(new Color(21, 21, 21));
        rData.setForeground(new Color(21, 21, 21));

        rRegistro.setFont(new Font("Tahoma", Font.BOLD, 15));
        rNome.setFont(new Font("Tahoma", Font.BOLD, 15));
        rEspecie.setFont(new Font("Tahoma", Font.BOLD, 15));
        rRaca.setFont(new Font("Tahoma", Font.BOLD, 15));
        rCorPredo.setFont(new Font("Tahoma", Font.BOLD, 15));
        rPesquisar.setFont(new Font("Tahoma", Font.BOLD, 15));
        rSexo.setFont(new Font("Tahoma", Font.BOLD, 15));
        rData.setFont(new Font("Tahoma", Font.BOLD, 15));

        tRegistro.setBounds(130, 50, 80, 30);
        tNome.setBounds(130, 90, 220, 30);
        tEspecie.setBounds(130, 130, 200, 30);
        tRaca.setBounds(130, 170, 150, 30);
        tCorPredo.setBounds(130, 210, 80, 30);
        tSexo.setBounds(130, 250, 80, 30);
        tData.setBounds(650, 50, 80, 30);

        tela.add(rTitulo);
        tela.add(tEspecie);
        tela.add(tRegistro);
        tela.add(tNome);
        tela.add(tRaca);
        tela.add(tCorPredo);
        tela.add(rRegistro);
        tela.add(rNome);
        tela.add(rEspecie);
        tela.add(rRaca);
        tela.add(rCorPredo);
        tela.add(tSexo);
        tela.add(rSexo);
        tela.add(tPesquisar);
        tela.add(tData);
        tela.add(rData);
        tela.add(rimagem);

        ImagePanel backgroundPanel = new ImagePanel("src/imagens/backgroundcrud.png");
        tela.add(backgroundPanel);
        backgroundPanel.setBounds(0, -20, backgroundPanel.getPreferredSize().width, backgroundPanel.getPreferredSize().height);

        setSize(1000, 650);
        setVisible(true);
        setLocationRelativeTo(null);

        con_cliente.executaSQL("select * from animais order by Num_Registro");
        preencherTabela();
        posicionarRegistro();
    }

    //método posicionarRegistro
    public void posicionarRegistro() {
        try {
            con_cliente.resultset.first(); // posiciona no 1° registro da tabela
            mostrar_Dados(); // chama o método que irá buscar o dado da tabela
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possível posicionar no primeiro registro: " + erro, "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void mostrar_Dados() {
        try {
            tRegistro.setText(con_cliente.resultset.getString("Num_Registro"));
            tNome.setText(con_cliente.resultset.getString("Nome_Pet"));
            tEspecie.setText(con_cliente.resultset.getString("Especie"));
            tRaca.setText(con_cliente.resultset.getString("Raca"));
            tCorPredo.setText(con_cliente.resultset.getString("Cor_Predominante"));
            tSexo.setText(con_cliente.resultset.getString("Sexo"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dataFormatada = sdf.format(con_cliente.resultset.getDate("Data_Nasc"));
            tData.setText(dataFormatada);
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não localizou dados: " + erro, "Mensagem do prograna", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void preencherTabela() throws SQLException {
        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(30);
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tblClientes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tblClientes.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblClientes.getColumnModel().getColumn(6).setPreferredWidth(100);

        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setNumRows(0);

        try {
            con_cliente.resultset.beforeFirst();
            while (con_cliente.resultset.next()) {
                modelo.addRow(new Object[]{
                    con_cliente.resultset.getString("Num_Registro"),
                    con_cliente.resultset.getString("Nome_Pet"),
                    con_cliente.resultset.getString("Especie"),
                    con_cliente.resultset.getString("Raca"),
                    con_cliente.resultset.getString("Cor_Predominante"),
                    con_cliente.resultset.getString("Sexo"),
                    con_cliente.resultset.getString("Data_Nasc"),});
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "erro ao listar dados da tabela!! \n " + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
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

    public static void main(String[] args) throws SQLException {
        CrudADM adm = new CrudADM();
        adm.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    private void criarMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuNavegacao = new JMenu("Navegação");
        menuBar.add(menuNavegacao);

        JMenuItem primeiroItem = new JMenuItem("Primeiro");
        JMenuItem ultimoItem = new JMenuItem("Último");
        JMenuItem anteriorItem = new JMenuItem("Anterior");
        JMenuItem proximoItem = new JMenuItem("Próximo");
        JMenuItem agradecimentos = new JMenuItem("Agradecimentos");

        menuNavegacao.add(primeiroItem);
        menuNavegacao.add(anteriorItem);
        menuNavegacao.add(proximoItem);
        menuNavegacao.add(ultimoItem);
        menuNavegacao.add(agradecimentos);

        primeiroItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                primeiroActionPerformed();
            }
        });

        anteriorItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                anteriorActionPerformed();
            }
        });

        proximoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                proximoActionPerformed();
            }
        });

        ultimoItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ultimoActionPerformed();
            }
        });

        agradecimentos.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                agradecimentosActionPerformed();
            }
        });
    }

    private void primeiroActionPerformed() {
        try {
            con_cliente.resultset.first();
            mostrar_Dados();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possível acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void anteriorActionPerformed() {
        try {
            if (con_cliente.resultset.isFirst()) {
                JOptionPane.showMessageDialog(null, "Ja esta no primeiro registro");
            } else {
                con_cliente.resultset.previous();
                mostrar_Dados();
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possivel acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void proximoActionPerformed() {
        try {
            if (con_cliente.resultset.isLast()) {
                JOptionPane.showMessageDialog(null, "Ja esta no ultimo registro");
            } else {
                con_cliente.resultset.next();
                mostrar_Dados();
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possivel acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void ultimoActionPerformed() {
        try {
            con_cliente.resultset.last();
            mostrar_Dados();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não foi possivel acessar o primeiro registro" + erro, "Mensagem do programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void agradecimentosActionPerformed() {
        agradecimento agr = new agradecimento();
        agr.setVisible(true);
        dispose();
    }

}
