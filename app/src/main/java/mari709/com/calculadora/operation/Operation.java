package mari709.com.calculadora.operation;

public enum Operation {
    NONE(""),
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String KEY;

    Operation(String key) {
        this.KEY = key;
    }


    /*public String getKEY() {
        return KEY;
    }*/

    /*@Override
    public String toString() {
        return  this.KEY;
    }*/


    public static Operation operationKey(String value) {
        for (Operation op : values()) {
            if (op.KEY.equals(value)) {
                return op;
            }
        }
        return NONE;
    }

}