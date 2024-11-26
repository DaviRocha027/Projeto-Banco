package com.banco.banco.view;
import com.google.gson.Gson;

import com.banco.banco.Conta;
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
	    private JTextField numeroField, saldoField, tipoContaField, limiteField, rendimentoField;

	    public BancoFrame() {
	        setTitle("Sistema Bancário");
	        setSize(900, 600);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout(10, 10));

	        // Painel Superior - Cadastro de Conta
	        JPanel cadastroPanel = new JPanel(new GridBagLayout());
	        cadastroPanel.setBorder(BorderFactory.createTitledBorder("Cadastro de Conta"));
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5, 5, 5, 5);
	        gbc.fill = GridBagConstraints.HORIZONTAL;

	        // Linha 1 - Número e Saldo Inicial
	        gbc.gridx = 0; gbc.gridy = 0;
	        cadastroPanel.add(new JLabel("Número da Conta:"), gbc);
	        numeroField = new JTextField(10);
	        gbc.gridx = 1;
	        cadastroPanel.add(numeroField, gbc);

	        gbc.gridx = 2;
	        cadastroPanel.add(new JLabel("Saldo Inicial:"), gbc);
	        saldoField = new JTextField(10);
	        gbc.gridx = 3;
	        cadastroPanel.add(saldoField, gbc);

	        // Linha 2 - Tipo da Conta
	        gbc.gridx = 0; gbc.gridy = 1;
	        cadastroPanel.add(new JLabel("Tipo da Conta:"), gbc);
	        tipoContaField = new JTextField(15);
	        gbc.gridx = 1; gbc.gridwidth = 3;
	        cadastroPanel.add(tipoContaField, gbc);

	        // Linha 3 - Limite e Rendimento
	        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
	        cadastroPanel.add(new JLabel("Limite:"), gbc);
	        limiteField = new JTextField(10);
	        gbc.gridx = 1;
	        cadastroPanel.add(limiteField, gbc);

	        gbc.gridx = 2;
	        cadastroPanel.add(new JLabel("Rendimento:"), gbc);
	        rendimentoField = new JTextField(10);
	        gbc.gridx = 3;
	        cadastroPanel.add(rendimentoField, gbc);

	        // Linha 4 - Botões
	        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 1;
	        JButton criarContaButton = new JButton("Criar Conta");
	        cadastroPanel.add(criarContaButton, gbc);

	        gbc.gridx = 2;
	        JButton atualizarTabelaButton = new JButton("Atualizar Tabela");
	        cadastroPanel.add(atualizarTabelaButton, gbc);

	        add(cadastroPanel, BorderLayout.NORTH);

	        // Painel Central - Tabela de Contas
	        JPanel tabelaPanel = new JPanel(new BorderLayout());
	        tabelaPanel.setBorder(BorderFactory.createTitledBorder("Contas Cadastradas"));

	        tableModel = new DefaultTableModel(new String[]{"ID", "Número", "Saldo", "Tipo", "Limite", "Rendimento"}, 0);
	        table = new JTable(tableModel);
	        tabelaPanel.add(new JScrollPane(table), BorderLayout.CENTER);

	        add(tabelaPanel, BorderLayout.CENTER);

	        // Painel Inferior - Operações
	        JPanel operacoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	        operacoesPanel.setBorder(BorderFactory.createTitledBorder("Operações"));

	        JButton consultarSaldoButton = new JButton("Consultar Saldo");
	        JButton sacarButton = new JButton("Sacar");
	        JButton depositarButton = new JButton("Depositar");

	        operacoesPanel.add(consultarSaldoButton);
	        operacoesPanel.add(sacarButton);
	        operacoesPanel.add(depositarButton);

	        add(operacoesPanel, BorderLayout.SOUTH);


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
        Double limite = limiteField.getText().isEmpty() ? null : Double.parseDouble(limiteField.getText());
        Double rendimento = rendimentoField.getText().isEmpty() ? null : Double.parseDouble(rendimentoField.getText());

        // Configurar JSON para envio
        String json = new Gson().toJson(new ContaRequest(numero, saldo, tipo, limite, rendimento));

        // Chamada à API REST
        try {
            APIClient.post("http://localhost:8081/api/contas/criar", json);
            JOptionPane.showMessageDialog(this, "Conta criada com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao criar conta: " + e.getMessage());
        }
    }

    private void carregarContas() {
        try {
            String response = APIClient.get("http://localhost:8081/api/contas");
            Gson gson = new Gson();

            Conta[] contas = gson.fromJson(response, Conta[].class);

            tableModel.setRowCount(0);

            for (Conta conta : contas) {
                String tipo = conta.getTipo();
                Double limite = conta.getLimite() != null ? conta.getLimite() : 0.0;
                Double rendimento = conta.getRendimento() != null ? conta.getRendimento() : 0.0;

                tableModel.addRow(new Object[]{
                    conta.getId(),
                    conta.getNumero(),
                    conta.getSaldo(),
                    tipo,
                    tipo.equals("ContaEspecial") ? limite : rendimento
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar contas: " + e.getMessage());
        }
    }
    private void consultarSaldo() {
        try {
            String numero = numeroField.getText();
            String response = APIClient.get("http://localhost:8081/api/contas/saldo/" + numero);
            JOptionPane.showMessageDialog(this, "Saldo da conta: " + response);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao consultar saldo: " + e.getMessage());
        }
    }

    private void realizarSaque() {
        try {
            String numero = numeroField.getText();
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor do saque:"));
            Gson gson = new Gson();
            JsonObject json = new JsonObject();
            json.addProperty("numero", numero);
            json.addProperty("valor", valor);

            APIClient.post("http://localhost:8081/api/contas/sacar", gson.toJson(json));
            JOptionPane.showMessageDialog(this, "Saque realizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar saque: " + e.getMessage());
        }
    }

    private void realizarDeposito() {
        try {
            String numero = numeroField.getText();
            double valor = Double.parseDouble(JOptionPane.showInputDialog("Informe o valor do depósito:"));
            Gson gson = new Gson();
            JsonObject json = new JsonObject();
            json.addProperty("numero", numero);
            json.addProperty("valor", valor);

            APIClient.post("http://localhost:8081/api/contas/depositar", gson.toJson(json));
            JOptionPane.showMessageDialog(this, "Depósito realizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar depósito: " + e.getMessage());
        }
    }
}
