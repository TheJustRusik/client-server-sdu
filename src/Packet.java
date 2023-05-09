import java.io.Serializable;

public class Packet implements Serializable{
    String data;
    int serialNo;

    public Packet(String data, int serialNo){
        this.data = data;
        this.serialNo = serialNo;
    }
    public String getData() {
        return data;
    }
    public int getSerialNo() {
        return serialNo;
    }
}
