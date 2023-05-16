package br.com.storti.service;

import br.com.storti.model.TransactionModel;

public interface TransactionPaymentAsyncService {

    void pay(TransactionModel transaction);
}
