package com.orchestrator.core.saga.absClasses;

import com.orchestrator.core.saga.interfaces.sagaContextInterface;
import com.orchestrator.core.saga.interfaces.sagaStepInterface;

import java.util.ArrayList;
import java.util.List;


public class AbstractSagaOrchestrator < T extends sagaContextInterface>{
    private final List<sagaStepInterface<T>> steps = new ArrayList<>();
    private int currentStep = 0;

    public void addStep(sagaStepInterface <T> step) {
        steps.add(step);
    }

    public final void execute(T context) {
        try {
            for (currentStep = 0; currentStep < steps.size(); currentStep++) {
                steps.get(currentStep).execute(context);
            }
        } catch (Exception e) {
            compensate(context);
        }
    }

    private void compensate(T context) {
        for (int i = currentStep; i >= 0; i--) {
            try {
                steps.get(i).compensate(context);
            } catch (Exception e) {
                // Log ou gestion d'erreur
                System.out.println(e.getMessage());
            }
        }
    }
}
