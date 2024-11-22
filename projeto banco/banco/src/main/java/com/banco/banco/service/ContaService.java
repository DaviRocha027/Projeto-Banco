package com.banco.banco.service;

import com.banco.banco.Conta;
import com.banco.banco.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    private ContaRepository contaRepository;

    public Conta criarConta(Conta conta) {
        return contaRepository.save(conta);
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
