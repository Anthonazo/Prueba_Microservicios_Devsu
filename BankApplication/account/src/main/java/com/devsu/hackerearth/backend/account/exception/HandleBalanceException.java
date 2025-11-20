package com.devsu.hackerearth.backend.account.exception;

public class HandleBalanceException extends RuntimeException {

    public HandleBalanceException(){
        super("Saldo Insuficiente");
    }

}
