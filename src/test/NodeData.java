package test;/**
 * Created by zgs on 2016/4/17.
 */

/**
 * Function:  ADD FUNCTION. <br/>
 * Reason:  ADD REASON(¿ÉÑ¡). <br/>
 * date:   <br/>
 *
 * @author
 * @since JDK 1.8
 */
public class NodeData {
    protected  String value;
    protected boolean booleanValue;

    public NodeData(String quest) {
        value = quest;
    }

    public String getQuestion() {
        return value;
    }

    public boolean getAnswer() {
        return booleanValue;
    }

    public void setAnswer(boolean ans) {
        booleanValue = ans;
    }

    public String toString() {
        return value + " = " + booleanValue;
    }
    public void setValue(String value){
    	this.value=value;
    }
}
