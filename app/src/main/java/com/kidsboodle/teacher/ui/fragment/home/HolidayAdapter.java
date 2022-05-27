package com.kidsboodle.teacher.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.kidsboodle.teacher.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<HolidayModel> holidayModelArrayList;
    Context ctx;


    public HolidayAdapter(Context ctx, ArrayList<HolidayModel> holidayModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.holidayModelArrayList = holidayModelArrayList;
        this.ctx = ctx;
    }



    @NonNull
    @Override
    public HolidayAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_holiday, parent, false);
        HolidayAdapter.MyViewHolder holder = new HolidayAdapter.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayAdapter.MyViewHolder holder, int position) {

        holder.tvDate.setText(holidayModelArrayList.get(position).getDate());
        holder.tvHoliday.setText(holidayModelArrayList.get(position).getLeavetype());


    }

    @Override
    public int getItemCount() {
        return holidayModelArrayList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvDate, tvHoliday;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvHoliday = itemView.findViewById(R.id.tvHoliday);

        }
    }
}
