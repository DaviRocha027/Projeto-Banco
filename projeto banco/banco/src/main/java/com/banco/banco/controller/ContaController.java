package com.banco.banco.controller;

import com.banco.banco.Conta;
import com.banco.banco.dto.OperacaoRequest;
import com.banco.banco.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/criar")
    public ResponseEntity<?> criarConta(@RequestBody Conta conta) {
        try {
            Conta novaConta = contaService.criarConta(conta);
            return ResponseEntity.ok(novaConta);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarContas() {
        return ResponseEntity.ok(contaService.listarContas());
    }

    @GetMapping("/saldo/{numero}")
    public ResponseEntity<?> consultarSaldo(@PathVariable String numero) {
        try {
            double saldo = contaService.consultarSaldo(numero);
            return ResponseEntity.ok(saldo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/sacar")
    public ResponseEntity<?> realizarSaque(@RequestBody OperacaoRequest request) {
        try {
            contaService.realizarSaque(request.getNumero(), request.getValor());
            return ResponseEntity.ok("Saque realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/depositar")
    public ResponseEntity<?> realizarDeposito(@RequestBody OperacaoRequest request) {
        try {
            contaService.realizarDeposito(request.getNumero(), request.getValor());
            return ResponseEntity.ok("Depósito realizado com sucesso.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
