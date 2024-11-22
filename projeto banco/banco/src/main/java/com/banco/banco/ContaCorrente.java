package com.banco.banco;

import jakarta.persistence.Entity;

@Entity
public class ContaCorrente extends Conta {
    public ContaCorrente() {}

    public ContaCorrente(String numero, double saldo) {
        super();
    }
}
