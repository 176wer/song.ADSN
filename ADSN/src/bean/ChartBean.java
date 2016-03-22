package bean;

/**
 * Created by Administrator on 2015/12/25.
 */
public class ChartBean {

    private long id;
    private String name;
    private String formula;
    private String minFormula;
    private String maxFormula;



    public ChartBean(long id, String name, String formula) {
        super();
        this.id = id;
        this.name = name;
        this.formula = formula;
    }


    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormula() {
        return formula;
    }
    public void setFormula(String formula) {
        this.formula = formula;
    }


    public void setMinFormula(String minFormula) {
        this.minFormula = minFormula;
    }


    public String getMinFormula() {
        return minFormula;
    }


    public void setMaxFormula(String maxFormula) {
        this.maxFormula = maxFormula;
    }


    public String getMaxFormula() {
        return maxFormula;
    }


}
