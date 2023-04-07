import android.content.Context;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.data.Data;

public class BluetoothManager extends BleManager {

    // Replace these UUIDs with the ones used by your ESP32 device
    private static final UUID FIREWORK_SERVICE_UUID = UUID.fromString("0000XXXX-0000-1000-8000-00805f9b34fb");
    private static final UUID FIREWORK_COMMAND_CHARACTERISTIC_UUID = UUID.fromString("0000YYYY-0000-1000-8000-00805f9b34fb");

    private BluetoothGattCharacteristic mFireworkCommandCharacteristic;

    public BluetoothManager(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    protected BleManagerGattCallback getGattCallback() {
        return new FireworkBleManagerGattCallback();
    }

    public void sendFireworkCommand(int fireworkNumber) {
        if (mFireworkCommandCharacteristic != null) {
            writeCharacteristic(mFireworkCommandCharacteristic, new Data(new byte[]{(byte) fireworkNumber}))
                .with((device, data) -> {
                    // Handle the response from the ESP32 device, if needed
                })
                .fail((device, status) -> {
                    // Handle the error when writing to the ESP32 device
                })
                .enqueue();
        }
    }

    private class FireworkBleManagerGattCallback extends BleManagerGattCallback {

        @Override
        protected void initialize() {
            setNotificationCallback(mFireworkCommandCharacteristic).with((device, data) -> {
                // Handle notifications from the ESP32 device, if needed
            });

            requestMtu(MTU_REQUESTED).enqueue();
            enableNotifications(mFireworkCommandCharacteristic).enqueue();
        }

        @Override
        protected boolean isRequiredServiceSupported(@NonNull BluetoothGatt gatt) {
            final BluetoothGattService fireworkService = gatt.getService(FIREWORK_SERVICE_UUID);
            if (fireworkService == null) return false;

            mFireworkCommandCharacteristic = fireworkService.getCharacteristic(FIREWORK_COMMAND_CHARACTERISTIC_UUID);
            return mFireworkCommandCharacteristic != null;
        }

        @Override
        protected void onDeviceDisconnected() {
            mFireworkCommandCharacteristic = null;
        }
    }
}
