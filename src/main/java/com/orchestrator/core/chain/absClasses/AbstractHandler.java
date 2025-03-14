package com.orchestrator.core.chain.absClasses;


import com.orchestrator.core.chain.interfaces.chainHandlerInterface;

public abstract class AbstractHandler <T> implements chainHandlerInterface<T> {
    public chainHandlerInterface<T> next;

    @Override
    public void setNext(chainHandlerInterface<T> next) {
        this.next = next;
    }

    public chainHandlerInterface getNext (){
        return this.next;
    }

    protected void passToNext(T request) {
        if (next != null) {
            next.execute(request);
        }
    }
}
