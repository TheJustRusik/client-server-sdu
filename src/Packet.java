import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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

    public byte[] toBytes() {
        byte[] strBytes = data.getBytes(StandardCharsets.UTF_8);
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES + strBytes.length);
        buffer.putInt(serialNo);
        buffer.put(strBytes);
        return buffer.array();
    }

    public Packet(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        serialNo = buffer.getInt();
        byte[] strBytes = new byte[bytes.length - Integer.BYTES];
        buffer.get(strBytes);
        data = new String(strBytes, StandardCharsets.UTF_8);
    }
}
