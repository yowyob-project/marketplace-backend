package com.orchestrator.core.chain.absClasses;


import com.orchestrator.core.chain.interfaces.chainHandlerInterface;

public abstract class AbstractChainOrchestrator <T> {
    private AbstractHandler <T> handler = null;


    public void addStep(AbstractHandler<T> newHandler) {
        newHandler.setNext(this.handler);
        this.handler = newHandler;
    }

    public void execute (T request){
        chainHandlerInterface<T> _handler = this.handler;

        while (_handler != null){
            _handler.execute(request);
            _handler = _handler.getNext();
        }
    }
}
