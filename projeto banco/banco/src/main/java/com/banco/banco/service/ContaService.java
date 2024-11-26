package com.banco.banco.service;

import com.banco.banco.Conta;
import com.banco.banco.ContaCorrente;
import com.banco.banco.ContaEspecial;
import com.banco.banco.ContaPoupanca;
import com.banco.banco.repository.ContaRepository;
import com.banco.banco.view.ContaRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta criarConta(ContaRequest contaRequest) {
        System.out.println("ContaRequest recebido no service: " + contaRequest);
        Conta novaConta;

        if (contaRequest.getTipo().equalsIgnoreCase("poupanca")) {
            if (contaRequest.getRendimento() == null) {
                throw new IllegalArgumentException("O rendimento deve ser informado para conta poupança.");
            }
            novaConta = new ContaPoupanca(contaRequest.getNumero(), contaRequest.getSaldo(), contaRequest.getRendimento());
        } else if (contaRequest.getTipo().equalsIgnoreCase("especial")) {
            if (contaRequest.getLimite() == null) {
                throw new IllegalArgumentException("O limite deve ser informado para conta especial.");
            }
            novaConta = new ContaEspecial(contaRequest.getNumero(), contaRequest.getSaldo(), contaRequest.getLimite());
        } else {
            novaConta = new ContaCorrente(contaRequest.getNumero(), contaRequest.getSaldo());
        }

        System.out.println("Conta criada no service antes de salvar: " + novaConta);
        return contaRepository.save(novaConta);
    }


    public List<Conta> listarContas() {
        return contaRepository.findAll();
    }

    public double consultarSaldo(String numero) throws Exception {
        Conta conta = contaRepository.findByNumero(numero)
                .orElseThrow(() -> new Exception("Conta não encontrada."));
        return conta.getSaldo();
    }

    public void realizarSaque(String numero, double valor) throws Exception {
        Conta conta = contaRepository.findByNumero(numero)
                .orElseThrow(() -> new Exception("Conta não encontrada."));
        if (conta.getSaldo() < valor) {
            throw new Exception("Saldo insuficiente.");
        }
        conta.setSaldo(conta.getSaldo() - valor);
        contaRepository.save(conta);
    }

    public void realizarDeposito(String numero, double valor) throws Exception {
        Conta conta = contaRepository.findByNumero(numero)
                .orElseThrow(() -> new Exception("Conta não encontrada."));
        conta.setSaldo(conta.getSaldo() + valor);
        contaRepository.save(conta);
    }
}
