package com.example.animalcrossingfront.CrittersActivities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalcrossingfront.R;
import com.example.animalcrossingfront.database.Critters;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CritterAdapter extends RecyclerView.Adapter<CritterAdapter.CritterViewHolder> {
    private List<Critters> crittersList = new ArrayList<>();

    public static class CritterViewHolder extends RecyclerView.ViewHolder {

        public TextView crittersName;
        public TextView crittersLocation;
        public TextView crittersMonths;
        public TextView crittersPrice;
        public TextView crittersTime;
        //        public TextView crittersDonated;
        public ImageView crittersImage;
//        public Button updateDonated;

        public CritterViewHolder(@NonNull View v) {
            super(v);
            crittersName = v.findViewById(R.id.name_text);
            crittersImage = v.findViewById(R.id.imageView8);
            crittersLocation = v.findViewById(R.id.location_text);
            crittersMonths = v.findViewById(R.id.month_text);
            crittersPrice = v.findViewById(R.id.price_text);
            crittersTime = v.findViewById(R.id.time_text);
//            crittersDonated = v.findViewById(R.id.donatedTextView);
//            updateDonated= v.findViewById(R.id.viewDonated);


        }
    }

    @NonNull
    @Override
    public CritterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent, false);
        return new CritterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CritterViewHolder holder, int position) {
        final Critters crittersList2 = crittersList.get(position);
        holder.crittersName.setText(crittersList2.getName());
        Picasso.get().load(crittersList2.getImage()).into(holder.crittersImage);
        holder.crittersLocation.setText(crittersList2.getLocation());
        holder.crittersPrice.setText(crittersList2.getPrice());
        holder.crittersTime.setText(crittersList2.getTime());
        holder.crittersMonths.setText(crittersList2.getMonth());
//        holder.crittersDonated.setText(crittersList2.getDonated());
//        Picasso.get().load("http://i.imgur.com/DvpvklR.png").into(holder.crittersImage);

    }
    @Override
    public int getItemCount() {
        return crittersList.size();
    }

    public void setCritter(List<Critters> crittersList){
        this.crittersList = crittersList;
        notifyDataSetChanged();
    }


}
