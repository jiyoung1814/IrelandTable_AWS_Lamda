package Serial;

import Packet.DataPacket;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.InputStream;
import java.io.OutputStream;


public class Serial {
    DataPacket dataPacket;
    public SerialReader sr;
    public SerialWriter sw;

    public Serial(DataPacket dataPacket) {
        super();
        this.dataPacket = dataPacket;
    }

    public void connect(String portName) throws Exception {
        //실제 포트가 존재 하는지 확인
        CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
        if (portIdentifier.isCurrentlyOwned()) {
            System.out.println("Error: Port is currently in use");
        } else {
            //만약 선점되어 있지 않는다면 open 하는데 2초의 타임아웃
            CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

            if (commPort instanceof SerialPort) {//만약 받은게 시리얼 포트라면
                SerialPort serialPort = (SerialPort) commPort;
                //보드레이트 9600, 데이터교환 8비트, 스톱비트 1비트, 패리티비트X
                serialPort.setSerialPortParams(19200, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                InputStream in = serialPort.getInputStream();
                OutputStream out = serialPort.getOutputStream();

                sr = new SerialReader(in,dataPacket);
                sw = new SerialWriter(out);
                sr.start();

            } else {
                System.out.println("Error: Only serial ports are handled by this example.");
            }
        }
    }
}
