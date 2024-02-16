package com.seuapp;

import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.BluetoothStateException;
import java.util.Vector;

public class BluetoothManager {

    private final Vector<RemoteDevice> devicesDiscovered = new Vector<>();

    private final DiscoveryListener listener = new DiscoveryListener() {

        @Override
        public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
            devicesDiscovered.addElement(btDevice);
            try {
                String name = btDevice.getFriendlyName(false);
                System.out.println("Dispositivo encontrado: " + name + " (" + btDevice.getBluetoothAddress() + ")");
            } catch (Exception e) {
                System.out.println("Dispositivo encontrado: " + btDevice.getBluetoothAddress());
            }
        }

        @Override
        public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
        }

        @Override
        public void serviceSearchCompleted(int transID, int respCode) {
        }

        @Override
        public void inquiryCompleted(int discType) {
            System.out.println("Busca por dispositivos concluída!");
            synchronized (BluetoothManager.this) {
                BluetoothManager.this.notifyAll();
            }
        }
    };

    public void startDiscovery() throws BluetoothStateException {
        synchronized (this) {
            boolean started = LocalDevice.getLocalDevice().getDiscoveryAgent().startInquiry(DiscoveryAgent.GIAC,
                    listener);
            if (started) {
                System.out.println("Aguardando dispositivos serem descobertos...");
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Vector<RemoteDevice> getDevicesDiscovered() {
        return devicesDiscovered;
    }
}