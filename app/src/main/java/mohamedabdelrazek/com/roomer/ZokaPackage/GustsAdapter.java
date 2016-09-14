package mohamedabdelrazek.com.roomer.ZokaPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mohamedabdelrazek.com.roomer.GuestsData.GuestModel;
import mohamedabdelrazek.com.roomer.GuestsData.GuestsContract;
import mohamedabdelrazek.com.roomer.R;

/**
 * Created by Mohamed on 9/10/2016.
 */
public class GustsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<GuestModel> GuestList;

    private class ViewHolder {
        private TextView name;
        private TextView phone;
        private ImageView genderImage;

        private ViewHolder(View view) {
            name = (TextView) view.findViewById(R.id.mName);
            phone = (TextView) view.findViewById(R.id.mPhone);
            genderImage = (ImageView) view.findViewById(R.id.mGenderImage);
        }
    }

    public GustsAdapter(Context context, ArrayList<GuestModel> GuestList) {
        inflater = LayoutInflater.from(context);
        this.GuestList = GuestList;
    }

    public int getCount() {
        return GuestList.size();
    }

    public GuestModel getItem(int position) {
        return GuestList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.name.setText(GuestList.get(position).getName());
        holder.phone.setText(GuestList.get(position).getPhone());
        if (GuestList.get(position).getGender() == GuestsContract.GuestsEntry.GENDER_MALE) {
            holder.genderImage.setImageResource(R.drawable.male_pic);
        } else {
            holder.genderImage.setImageResource(R.drawable.female_pic);
        }
        return view;
    }
}
