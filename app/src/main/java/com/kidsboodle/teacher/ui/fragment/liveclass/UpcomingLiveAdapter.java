package com.kidsboodle.teacher.ui.fragment.liveclass;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kidsboodle.teacher.R;

import java.util.ArrayList;

public class UpcomingLiveAdapter extends RecyclerView.Adapter<UpcomingLiveAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<UpcomingModel> upcomingModelArrayList;
    Context ctx;

    public UpcomingLiveAdapter(Context ctx, ArrayList<UpcomingModel> upcomingModelArrayList) {
        inflater = LayoutInflater.from(ctx);
        this.upcomingModelArrayList = upcomingModelArrayList;
        this.ctx = ctx;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.teacher_liveclass_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.tvupcomingdateclass.setText("Date:- " + upcomingModelArrayList.get(position).getDate());
        holder.tvlvtime.setText("Time:- " + upcomingModelArrayList.get(position).getStartdate()
                + " - " + upcomingModelArrayList.get(position).getEnddate());
        holder.tvupcomingsub.setText("Subject:- " + upcomingModelArrayList.get(position).getSubjectname());

        holder.lvjoinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent browserIntent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(upcomingModelArrayList.get(position).getZoomlink()));
                ctx.startActivity(browserIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return upcomingModelArrayList.size();

    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvupcomingdateclass, tvlvtime, tvupcomingsub;
        Button lvjoinbtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvupcomingdateclass = itemView.findViewById(R.id.tvupcomingdateclass);
            tvlvtime = itemView.findViewById(R.id.tvlvtime);
            tvupcomingsub = itemView.findViewById(R.id.tvupcomingsub);
            lvjoinbtn = itemView.findViewById(R.id.lvjoinbtn);

        }
    }
}
