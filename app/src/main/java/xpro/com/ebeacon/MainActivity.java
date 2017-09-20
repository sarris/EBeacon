package xpro.com.ebeacon;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    BluetoothManager bluetoothManager;
    //蓝牙
    private BluetoothAdapter mBluetoothAdapter;
    ListView listview;

    BlueToothAdapterText blueToothAdapterText;

    static private String[] buscodeArray={"5141","5170","暂无","5166","5140","5142","5139","5168","5169","5165","金","5137","5162","5163","5164","5161","5167","5138","5143","暂无","测试1","测试2"};
    static private String[] addressArray={"98:84:E3:38:D5:A3","98:84:E3:38:D9:9B","98:84:E3:38:D6:81","98:84:E3:38:D1:94","98:84:E3:2C:46:EC","98:84:E3:38:D1:92","98:84:E3:38:D0:EE","98:84:E3:38:D7:A0","98:84:E3:38:D5:D5","98:84:E3:38:D9:ED","98:84:E3:38:D5:F4","98:84:E3:38:D9:C8","98:84:E3:38:CF:CE","50:8C:B1:70:8B:4B","98:84:E3:38:DA:88","98:84:E3:38:D9:DF","98:84:E3:2C:4A:D5","98:84:E3:38:D4:56","98:84:E3:38:D9:84","98:84:E3:38:D1:E6","50:65:83:14:1B:47","50:65:83:14:26:AB"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listview = (ListView) findViewById(R.id.listview);

        blueToothAdapterText = new BlueToothAdapterText(this, list);

        listview.setAdapter(blueToothAdapterText);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xxxx();
            }
        });
    }


    public void getBlueTooth() {
        bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            mBluetoothAdapter = bluetoothManager.getAdapter();
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }
    }


    public void xxxx() {
        // 检查设备是否支持蓝牙
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // 设备不支持蓝牙
        }
        // 打开蓝牙
        if (!mBluetoothAdapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            // 设置蓝牙可见性，最多300秒
            intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(intent);
        }


        // 设置广播信息过滤
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothDevice.ACTION_FOUND);
        intentFilter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        // 注册广播接收器，接收并处理搜索结果
        registerReceiver(receiver, intentFilter);
        // 寻找蓝牙设备，android会将查找到的设备以广播形式发出去
        mBluetoothAdapter.startDiscovery();
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 获取查找到的蓝牙设备
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                System.out.println(device.getName());

                    Log.e("sarris","sarris enter now");
                    entity = new Entity();
                    entity.setName(device.getName());
                    entity.setAddress(device.getAddress());
                    entity.setBuscode("暂无");
                    for(int j=0;j<addressArray.length;j++){
                        String tempAddress=addressArray[j];
                        if(tempAddress.equals(device.getAddress())){
                            entity.setBuscode(buscodeArray[j]);
                            break;
                        }
                    }
                    list.add(entity);
                    blueToothAdapterText.notifyDataSetChanged();
                Log.e("bluetoothAddress", "name:" + device.getName() + ",address:" + device.getAddress());


            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();


    }

    ArrayList<iBeaconClass.iBeacon> mLeDevices = new ArrayList<iBeaconClass.iBeacon>();

    List<Entity> list = new ArrayList<>();
    Entity entity;

    private void addDevice(iBeaconClass.iBeacon device) { //更新beacon信息
        if (device == null) {
            Log.d("DeviceScanActivity ", "device==null ");
            return;
        }

        for (int i = 0; i < mLeDevices.size(); i++) {
            String btAddress = mLeDevices.get(i).bluetoothAddress;
            if (btAddress.equals(device.bluetoothAddress)) {
                mLeDevices.add(i + 1, device);
                mLeDevices.remove(i);
                break;
            }
        }
        mLeDevices.add(device);


        for (int i = 0; i < mLeDevices.size(); i++) {
            Log.e("mLeDevicesText", "size:" + mLeDevices.size() + ",proximityUuid:" + mLeDevices.get(i).proximityUuid + ",name:" + mLeDevices.get(i).name + ",address:" + mLeDevices.get(i).bluetoothAddress);
            entity = new Entity();
            entity.setName(mLeDevices.get(i).name);
            entity.setAddress(mLeDevices.get(i).bluetoothAddress);
            //比对address和车号绑定



            list.add(entity);
        }
        listview.setAdapter(blueToothAdapterText);

    }

    BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {
            final iBeaconClass.iBeacon ibeacon = iBeaconClass.fromScanData(bluetoothDevice, i, bytes);
            addDevice(ibeacon);
            Log.e("bluetoothAddress", "size:" + ibeacon.bluetoothAddress);
            Collections.sort(mLeDevices, new Comparator<iBeaconClass.iBeacon>() {
                @Override
                public int compare(iBeaconClass.iBeacon h1, iBeaconClass.iBeacon h2) {
                    return h2.rssi - h1.rssi;
                }
            });
        }
    };
}
