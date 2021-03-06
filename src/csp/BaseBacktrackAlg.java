package csp;

import java.util.ArrayList;
import java.util.List;


class BaseBacktrackAlg {
    Variable selectUnassignedVariable(ConstraintSatisfactionProblem csp, Assignment assignment) {
        for (Variable var : csp.variables()) {
            if (assignment.get(var) == null)
                return var;
        }
        throw new IllegalAccessError();
    }

    List<Value> orderDomainValues(Assignment assignment, Variable var) {
        return new ArrayList<Value>(assignment.domain(var));
    }

    List<Inference> findInferences(ConstraintSatisfactionProblem csp, Assignment assignment) {
        for (Arc arc : csp.allArcs()) {
            if (assignment.sameColor(arc.first, arc.second))
                return null;
        }
        return new ArrayList<Inference>();
    }

    Assignment backtrack(ConstraintSatisfactionProblem csp, Assignment assignment) {
        // System.out.println(assignment);
        if (assignment.isComplete())
            return assignment;
        Variable var = selectUnassignedVariable(csp, assignment);
        for (Value value : orderDomainValues(assignment, var)) {
            System.out.println(assignment);
            assignment.add(var, value);
            List<Inference> inferences = findInferences(csp, assignment);
            if (inferences != null) {
                assignment.add(inferences);
                Assignment result = backtrack(csp, assignment);
                if (result != null)
                    return result;
                assignment.remove(inferences);
            }
            assignment.remove(var, value);
        }
        return null; // failure.
    }

    Assignment execute(ConstraintSatisfactionProblem csp) {
        Assignment result = backtrack(csp, new Assignment(csp));
        System.out.println(result);
        return result;
    }
}