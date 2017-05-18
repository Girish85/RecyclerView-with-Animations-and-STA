package com.example.usgir.recycler;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Collections;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by usgir on 5/13/2017.
 */

public class Adapt extends RecyclerView.Adapter<myholder> {
    List<listinfo> info= Collections.emptyList();
    LayoutInflater inflater;
    int i = 0;
    int oldposition = 0;
    Context c;
    public Adapt(Context context, List<listinfo> listinfos)
    {
        c = context;
        info = listinfos;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public myholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item1,parent,false);
        myholder hold = new myholder(view,c,info);
        Log.d(TAG, "Creating ViewHolder: "+i);
        i++;
        return hold;
    }

    @Override
    public void onBindViewHolder(myholder holder, int position) {
        listinfo current = info.get(position);
        Log.d(TAG, "BindingViewHolder: "+position);
        holder.textView.setText(current.text);
        holder.imageView.setImageResource(current.imgid);
        if (position>oldposition)
        {
            animate(holder,true);
        }
        else animate(holder,false);
        oldposition = position;
    }

    @Override
    public int getItemCount() {
        return info.size();
    }
    void animate(myholder hold,boolean down)
    {
        ObjectAnimator animator = new ObjectAnimator().ofFloat(hold.itemView,"TranslationX",-100f,0f);
        animator.setInterpolator(new OvershootInterpolator());
        ObjectAnimator animator1 = new ObjectAnimator().ofFloat(hold.itemView,"TranslationX",100f,0f);
        animator1.setInterpolator(new OvershootInterpolator());
        ObjectAnimator animator2 = new ObjectAnimator().ofFloat(hold.itemView,"TranslationY",down?100f:-100f,0f);
        animator.setInterpolator(new OvershootInterpolator());
        ObjectAnimator animator3 = new ObjectAnimator().ofFloat(hold.itemView,"ScaleX",1.2f,1f);
        animator3.setInterpolator(new AnticipateOvershootInterpolator());
        ObjectAnimator animator4 = new ObjectAnimator().ofFloat(hold.itemView,"ScaleY",1.2f,1f);
        animator4.setInterpolator(new AnticipateOvershootInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator,animator1,animator2,animator3,animator4);
        set.setDuration(1000);
        set.start();
    }
}
class myholder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    TextView textView;
    ImageView imageView;
    Context c;
    List<listinfo> name = Collections.emptyList();
    public myholder(View itemView,Context context,List title) {
        super(itemView);
        c = context;
        name = title;
        textView = (TextView)itemView.findViewById(R.id.textView3);
        imageView = (ImageView)itemView.findViewById(R.id.imageView3);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(c,"you choose "+getAdapterPosition()+"  "+name.get(getAdapterPosition()).text,Toast.LENGTH_LONG).show();
    }
}