import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanCallback;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanResult;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BluetoothManager mBluetoothManager;
    private Button btnConnect;
    private Button btnLaunch1;
    private Button btnLaunch2;
    private boolean isConnected = false;
    private String mDeviceAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothManager = new BluetoothManager(this);
        btnConnect = findViewById(R.id.btn_connect);
        btnLaunch1 = findViewById(R.id.btn_launch1);
        btnLaunch2 = findViewById(R.id.btn_launch2);

        btnConnect.setOnClickListener(v -> {
            if (!isConnected) {
                startScanning();
            } else {
                disconnectDevice();
            }
        });

        btnLaunch1.setOnClickListener(v -> {
            mBluetoothManager.sendFireworkCommand(1);
            Toast.makeText(MainActivity.this, "Launching Firework 1", Toast.LENGTH_SHORT).show();
        });

        btnLaunch2.setOnClickListener(v -> {
            mBluetoothManager.sendFireworkCommand(2);
            Toast.makeText(MainActivity.this, "Launching Firework 2", Toast.LENGTH_SHORT).show();
        });
    }

    private void startScanning() {
        BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        ScanSettings settings = new ScanSettings.Builder()
                .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                .setReportDelay(0)
                .setUseHardwareBatchingIfSupported(false)
                .build();
        List<ScanFilter> filters = new ArrayList<>();
        scanner.startScan(filters, settings, scanCallback);
    }

    private void stopScanning() {
        BluetoothLeScannerCompat scanner = BluetoothLeScannerCompat.getScanner();
        scanner.stopScan(scanCallback);
    }

    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, @NonNull ScanResult result) {
            super.onScanResult(callbackType, result);
            // TODO: Identify the ESP32 device based on its name, MAC address, or advertisement data
            if (/* ESP32 device is detected */) {
                stopScanning();
                mDeviceAddress = result.getDevice().getAddress();
                connectDevice(mDeviceAddress);
            }
        }
    };

    private void connectDevice(String deviceAddress) {
        mBluetoothManager.connect(deviceAddress)
            .done(device -> {
                isConnected = true;
                btnConnect.setText(R.string.disconnect);
                Toast.makeText(MainActivity.this, "Connected", Toast.LENGTH_SHORT).show();
            })
            .fail((device, status) -> {
                Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
            })
            .enqueue();
    }

    private void disconnectDevice() {
        mBluetoothManager.disconnect().enqueue();
        isConnected = false;
        btnConnect.setText(R.string.connect);
        Toast.makeText(MainActivity.this, "Disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Start scanning for ESP32 devices when the activity is resumed
        if (!isConnected) {
            startScanning();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Stop scanning for ESP32 devices and disconnect when the activity is paused
        if (!isConnected) {
            stopScanning();
        } else {
            disconnectDevice();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources related to the BluetoothManager when the activity is destroyed
        mBluetoothManager.close();
    }
}
