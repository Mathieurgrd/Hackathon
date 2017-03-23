package com.example.mathieu.hackathon;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.mathieu.hackathon.usbserial.driver.UsbSerialDriver;
import com.example.mathieu.hackathon.usbserial.driver.UsbSerialPort;
import com.example.mathieu.hackathon.usbserial.driver.UsbSerialProber;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements UsbSerialDriver, UsbSerialPort {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(manager);


        if (availableDrivers.isEmpty()) {
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        UsbDeviceConnection connection = manager.openDevice(driver.getDevice());
        if (connection == null) {
            // You probably need to call UsbManager.requestPermission(driver.getDevice(), ..)
            return;
        }

        // Read some data! Most have just one port (port 0).
        UsbSerialPort port = driver.getPorts().get(0);
        try {
            port.open(connection);
            port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);

            byte buffer[] = new byte[16];
            int numBytesRead = port.read(buffer, 1000);
            Log.d(TAG, "Read " + numBytesRead + " bytes.");
        } catch (IOException e) {
            // Deal with error.
        } finally {
            try {
                port.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

        @Override
        public UsbDevice getDevice () {
            return null;
        }

        @Override
        public List<UsbSerialPort> getPorts () {
            return null;
        }

        @Override
        public UsbSerialDriver getDriver () {
            return null;
        }

        @Override
        public int getPortNumber () {
            return 0;
        }

        @Override
        public String getSerial () {
            return null;
        }

        @Override
        public void open (UsbDeviceConnection connection)throws IOException {

        }

        @Override
        public void close ()throws IOException {

        }

        @Override
        public int read ( byte[] dest, int timeoutMillis)throws IOException {
            return 0;
        }

        @Override
        public int write ( byte[] src, int timeoutMillis)throws IOException {
            return 0;
        }

        @Override
        public void setParameters ( int baudRate, int dataBits, int stopBits, int parity)throws
        IOException {

        }

        @Override
        public boolean getCD ()throws IOException {
            return false;
        }

        @Override
        public boolean getCTS ()throws IOException {
            return false;
        }

        @Override
        public boolean getDSR ()throws IOException {
            return false;
        }

        @Override
        public boolean getDTR ()throws IOException {
            return false;
        }

        @Override
        public void setDTR ( boolean value)throws IOException {

        }

        @Override
        public boolean getRI ()throws IOException {
            return false;
        }

        @Override
        public boolean getRTS ()throws IOException {
            return false;
        }

        @Override
        public void setRTS ( boolean value)throws IOException {

        }

        @Override
        public boolean purgeHwBuffers ( boolean flushRX, boolean flushTX)throws IOException {
            return false;
        }


}
