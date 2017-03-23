package com.example.mathieu.hackathon;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.example.mathieu.hackathon.usbserial.driver.UsbSerialDriver;
import com.example.mathieu.hackathon.usbserial.driver.UsbSerialPort;
import com.example.mathieu.hackathon.usbserial.driver.UsbSerialProber;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.io.IOException;
import java.util.List;

import static android.content.ContentValues.TAG;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,UsbSerialDriver, UsbSerialPort {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


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


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}




    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */




