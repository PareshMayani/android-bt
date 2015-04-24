package li.vin.bt;

import android.bluetooth.BluetoothGattCharacteristic;

import java.util.UUID;

/*package*/ abstract class ParamPlain<T> extends Param<T, String> {
  public ParamPlain(UUID uuid) {
    super(uuid);
  }

  public ParamPlain(UUID uuid, boolean hasNotifications, boolean shouldRead) {
    super(uuid, hasNotifications, shouldRead);
  }

  @Override public String parseCharacteristic(BluetoothGattCharacteristic characteristic) {
    final byte[] val = characteristic.getValue();
    if (val == null) {
      throw new RuntimeException("val == null");
    }

    int valLen = 0;
    for (int i = 0; i < val.length; i++) {
      final byte c = val[i];
      if (c == '\r' || c == 0) {
        break;
      }
      ++valLen;
    }

    if (valLen == 0) {
      return null;
    }

    return new String(val, 0, valLen, ASCII);
  }
}
