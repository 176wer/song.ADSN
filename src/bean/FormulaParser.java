package bean;

/**
 * Created by Administrator on 2015/12/26.
 */
import bsh.EvalError;
import bsh.Interpreter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class FormulaParser {

    private String formula;
    private Set<String> params = new HashSet<String>();
    private Set<String> relParams = new HashSet<String>();
    private Map<String, String> oldValues = new HashMap<String, String>();

    private Interpreter interpreter;

    public FormulaParser(String formula) {
        this.formula = formula;
        interpreter = new Interpreter();

        //cerco nella formula le variabili
        int finpos=0;
        int pos=0;
        while(true){
            pos = formula.indexOf("[", finpos);
            if(pos==-1) break;
            finpos = formula.indexOf("]", pos);
            if(finpos==-1) break;
            String var = formula.substring(pos+1, finpos);
            if(pos!=0 && formula.toCharArray()[pos-1]=='^')
                relParams.add(var);
            else
                params.add(var);
        }
    }

    public Double eval(Map<String, String> values){
        String parsed = getParsedFormula(values);

        if(parsed==null){
            return null;
        }


        try {
            interpreter.eval("datum = ("+parsed+")*1.0");
            Object res = interpreter.get("datum");
            return (Double) res;
        } catch (EvalError e) {
            e.printStackTrace();
            return null;
        }

    }

    private String getParsedFormula(Map<String, String> values){

        String parsed = new String(formula);
        boolean procede = true;

        //sostituisco i parametri relativi
        for (String key : relParams){
            String oldval = oldValues.get(key);
            String newVal = values.get(key);
            if(oldval==null)
                procede = false;
            if(newVal==null)
                newVal = "0";
            if(procede){
                try{
                    double oldDouble = Double.parseDouble(oldval);
                    double newDouble = Double.parseDouble(newVal);
                    parsed = parsed.replace("^["+key+"]", ""+(newDouble-oldDouble));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            oldValues.put(key, newVal);
        }


        //sostituisco i parametri assoluti
        for (String key : params) {
            String newVal = values.get(key);
            if(newVal==null)
                newVal = "0";
            parsed = parsed.replace("["+key+"]", newVal);
        }


        if(procede)
            return parsed;
        return null;
    }



}
