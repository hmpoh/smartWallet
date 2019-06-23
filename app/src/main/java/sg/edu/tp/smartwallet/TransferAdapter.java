package sg.edu.tp.smartwallet;

import android.content.Context;
import android.graphics.Movie;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TransferAdapter extends ArrayAdapter<TransferWithName>
{
    private Context mContext;
    private List<TransferWithName> transfersList = new ArrayList<>();

    public TransferAdapter(@NonNull Context context, ArrayList<TransferWithName> list) {
        super(context, 0 , list);
        mContext = context;
        transfersList = list;
    }

    @NonNull
    @Override
    public View getView(int position,  View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.history_layout_item,parent,false);

        TransferWithName currentTransfer = transfersList.get(position);

        TextView dateTime = (TextView) listItem.findViewById(R.id.dateTimes);
        dateTime.setText(currentTransfer.getDateTime());

        TextView name = (TextView) listItem.findViewById(R.id.Name);
        name.setText(currentTransfer.getName());

        TextView amount = (TextView) listItem.findViewById(R.id.Paid);
        amount.setText(currentTransfer.getAmount());


        return listItem;
    }

}
