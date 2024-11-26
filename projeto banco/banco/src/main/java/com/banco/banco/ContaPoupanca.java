package com.banco.banco;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Poupanca")
public class ContaPoupanca extends Conta {

    private Double rendimento;

    // Construtor
    public ContaPoupanca(String numero, double saldo, Double rendimento) {
        this.setNumero(numero);
        this.setSaldo(saldo);
        this.setTipo("ContaPoupanca");
        this.rendimento = rendimento;
    }

    public ContaPoupanca() {
        // Construtor padrão
    }

    // Getter e Setter
    public Double getRendimento() {
        return rendimento;
    }

    public void setRendimento(Double rendimento) {
        this.rendimento = rendimento;
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" +
                "numero='" + this.getNumero() + '\'' +
                ", saldo=" + this.getSaldo() +
                ", tipo='" + this.getTipo() + '\'' +
                ", rendimento=" + rendimento +
                '}';
    }
}