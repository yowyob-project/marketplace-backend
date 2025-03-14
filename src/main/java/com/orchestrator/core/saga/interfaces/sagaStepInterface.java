package com.orchestrator.core.saga.interfaces;

public interface sagaStepInterface <T extends sagaContextInterface> {
    void execute(T context) throws Exception;
    void compensate(T context) throws Exception;
}
