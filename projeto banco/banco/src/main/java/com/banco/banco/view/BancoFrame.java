package com.banco.banco.view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BancoFrame extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField numeroField;
    private JTextField saldoField;
    private JTextField tipoContaField;
    private JTextField limiteOuRendimentoField;

    private Gson gson;

    public BancoFrame() {
        gson = new Gson(); // Inicializa o Gson

        setTitle("Sistema Bancário");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel superior (para entrada de dados)
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Número da Conta:"));
        numeroField = new JTextField();
        inputPanel.add(numeroField);

        inputPanel.add(new JLabel("Saldo Inicial:"));
        saldoField = new JTextField();
        inputPanel.add(saldoField);

        inputPanel.add(new JLabel("Tipo da Conta (Corrente/Poupança/Especial):"));
        tipoContaField = new JTextField();
        inputPanel.add(tipoContaField);

        inputPanel.add(new JLabel("Limite ou Rendimento:"));
        limiteOuRendimentoField = new JTextField();
        inputPanel.add(limiteOuRendimentoField);

        JButton criarContaButton = new JButton("Criar Conta");
        inputPanel.add(criarContaButton);

        JButton atualizarTabelaButton = new JButton("Atualizar Tabela");
        inputPanel.add(atualizarTabelaButton);

        add(inputPanel, BorderLayout.NORTH);

        // Painel central (Tabela)
        tableModel = new DefaultTableModel(new String[]{"ID", "Número", "Saldo", "Tipo", "Limite/Rendimento"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Painel inferior (Operações)
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton consultarSaldoButton = new JButton("Consultar Saldo");
        JButton sacarButton = new JButton("Sacar");
        JButton depositarButton = new JButton("Depositar");

        buttonPanel.add(consultarSaldoButton);
        buttonPanel.add(sacarButton);
        buttonPanel.add(depositarButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Configurar ações dos botões
        criarContaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarConta();
            }
        });

        atualizarTabelaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarContas();
            }
        });

        consultarSaldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarSaldo();
            }
        });

        sacarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarSaque();
            }
        });

        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarDeposito();
            }
        });

        setVisible(true);
    }

    private void criarConta() {
        String numero = numeroField.getText();
        double saldo = Double.parseDouble(saldoField.getText());
        String tipo = tipoContaField.getText().toLowerCase();
        double limiteOuRendimento = Double.parseDouble(limiteOuRendimentoField.getText());

        try {
            // Criar JSON com Gson
            JsonObject json = new JsonObject();
            json.addProperty("numero", numero);
            json.addProperty("saldo", saldo);

            if (tipo.equals("poupanca")) {
                json.addProperty("rendimento", limiteOuRendimento);
            } else if (tipo.equals("especial")) {
                json.addProperty("limite", limiteOuRendimento);
            }

            APIClient.post("http://localhost:8080/api/contas/criar", gson.toJson(json));
            JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar conta: " + e.getMessage());
        }
    }

    private void carregarContas() {
        try {
            String response = APIClient.get("http://localhost:8080/api/contas");
            tableModel.setRowCount(0); // Limpar tabela
            JsonObject[] contas = gson.fromJson(response, JsonObject[].class);

            for (JsonObject conta : contas) {
                tableModel.addRow(new Object[]{
                        conta.get("id").getAsInt(),
                        conta.get("numero").getAsString(),
                        conta.get("saldo").getAsDouble(),
                        conta.get("tipo").getAsString(),
                        conta.has("limite") ? conta.get("limite").getAsDouble() : conta.has("rendimento") ? conta.get("rendimento").getAsDouble() : null
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar contas: " + e.getMessage());
        }
    }

    private void consultarSaldo() {
        try {
            String numero = numeroField.getText();
            String response = APIClient.get("http://localhost:8080/api/contas/saldo/" + numero);
            JOptionPane.showMessageDialog(this, "Saldo da conta: " + response);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar saldo: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        try {
            String numero = numeroField.getText();
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor do saque:"));

            JsonObject json = new JsonObject();
            json.addProperty("numero", numero);
            json.addProperty("valor", valor);

            APIClient.post("http://localhost:8080/api/contas/sacar", gson.toJson(json));
            JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar saque: " + e.getMessage());
        }
    }

    private void realizarDeposito() {
        try {
            String numero = numeroField.getText();
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor do depósito:"));

            JsonObject json = new JsonObject();
            json.addProperty("numero", numero);
            json.addProperty("valor", valor);

            APIClient.post("http://localhost:8080/api/contas/depositar", gson.toJson(json));
            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar depósito: " + e.getMessage());
        }
    }
}
