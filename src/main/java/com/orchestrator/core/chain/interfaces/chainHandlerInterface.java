package com.orchestrator.core.chain.interfaces;

public interface chainHandlerInterface <T> {
    void execute(T request);
    void setNext(chainHandlerInterface<T> next);
    public chainHandlerInterface getNext ();
}
