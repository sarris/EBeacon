package xpro.com.ebeacon;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by houyang on 2017/7/28.
 */
public class BlueToothAdapterText extends BaseAdapter {
    Context context;
    List<Entity> list;

    public BlueToothAdapterText(Context context, List<Entity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHoler holer;
        if (view == null) {
            holer = new ViewHoler();
            view = inflater.inflate(R.layout.adapter_listview, null);

            holer.address = view.findViewById(R.id.address);
            holer.name = view.findViewById(R.id.name);

            view.setTag(holer);
        } else {
            holer = (ViewHoler) view.getTag();
        }


//        holer.name.setText(list.get(i).getName());
        holer.name.setText(list.get(i).getBuscode());
        holer.address.setText(list.get(i).getAddress());
        return view;
    }


    class ViewHoler {
        TextView name, address;
    }
}
