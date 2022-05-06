package com.kidsboodle.teacher.ui.fragment.home.students;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.kidsboodle.teacher.R;

import java.util.ArrayList;

public class StudentAdapter2 extends RecyclerView.Adapter<StudentAdapter2.MyViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<StudentModel> studentModelArrayListe;
    Context ctx;


    public StudentAdapter2(Context ctx, ArrayList<StudentModel> studentModelArrayListe) {
        inflater = LayoutInflater.from(ctx);
        this.studentModelArrayListe = studentModelArrayListe;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public StudentAdapter2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_row_check_student, parent, false);
        StudentAdapter2.MyViewHolder holder = new StudentAdapter2.MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter2.MyViewHolder holder, int position) {

        holder.tvName.setText(studentModelArrayListe.get(position).getName());
        holder.tvRoll.setText(studentModelArrayListe.get(position).getRoll());
        holder.btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ctx, Studentdetails.class);
                intent.putExtra("id", studentModelArrayListe.get(position).getId());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return studentModelArrayListe.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRoll, btnViewDetails;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvRoll = itemView.findViewById(R.id.tvRoll);
            btnViewDetails = itemView.findViewById(R.id.btnViewDetails);

        }
    }
}
