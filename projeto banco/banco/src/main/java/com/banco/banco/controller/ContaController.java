package com.banco.banco.controller;

import com.banco.banco.Conta;
import com.banco.banco.ContaCorrente;
import com.banco.banco.ContaEspecial;
import com.banco.banco.ContaPoupanca;
import com.banco.banco.dto.OperacaoRequest;
import com.banco.banco.repository.ContaRepository;
import com.banco.banco.service.ContaService;
import com.banco.banco.view.ContaRequest;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contas")
public class ContaController {

    @Autowired
    private ContaService contaService;

    @PostMapping("/criar")
    public ResponseEntity<Conta> criarConta(@RequestBody @Valid ContaRequest contaRequest) {
        System.out.println("JSON recebido: " + contaRequest);
        Conta novaConta = contaService.criarConta(contaRequest);
        System.out.println("Conta criada: " + novaConta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConta);
    }


    @GetMapping
    public ResponseEntity<List<Conta>> listarContas() {
        List<Conta> contas = contaService.listarContas(); // Certifique-se de que esse método funciona corretamente.
        return ResponseEntity.ok(contas);
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
