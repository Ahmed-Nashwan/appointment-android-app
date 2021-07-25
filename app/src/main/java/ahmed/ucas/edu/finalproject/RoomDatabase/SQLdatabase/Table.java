package ahmed.ucas.edu.finalproject.RoomDatabase.SQLdatabase;

public class Table {

    private int id;
    private String object;

    public Table(String object) {
        this.object = object;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
}
