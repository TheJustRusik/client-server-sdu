public class Packet {
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
