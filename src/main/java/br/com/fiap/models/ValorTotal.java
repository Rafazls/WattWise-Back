package br.com.fiap.models;

public class ValorTotal implements ValorCalculado {
    private double consumo;
    private double valor_tarifa;


    public ValorTotal(double consumo,double valor_tarifa) {
        this.consumo = consumo;
        this.valor_tarifa = valor_tarifa;
    }


    public double getConsumo() {
        return consumo;
    }

    //Remover
    public void setConsumo(double consumo) {
        this.consumo = consumo;
    }

    public double getValor_tarifa() {
        return valor_tarifa;
    }

    public void setValor_tarifa(double valor_tarifa) {
        this.valor_tarifa = valor_tarifa;
    }


    @Override
    public double valor(double valorDasPecas, double valor_reparo) {
        double valor_total = valorDasPecas * valor_reparo;
        return valor_total;
    }
}
