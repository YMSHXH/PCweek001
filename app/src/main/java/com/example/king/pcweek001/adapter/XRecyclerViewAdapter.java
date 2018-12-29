package com.example.king.pcweek001.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.king.pcweek001.R;
import com.example.king.pcweek001.beans.ProductBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class XRecyclerViewAdapter extends XRecyclerView.Adapter<XRecyclerViewAdapter.XRecyclerVH> {

    private Context context;
    private List<ProductBean.DataBean> list;

    public XRecyclerViewAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();
    }

    public void setList(List<ProductBean.DataBean> list) {
        if (list != null) {
            this.list = list;
        }
        notifyDataSetChanged();
    }

    public void addList(List<ProductBean.DataBean> list) {
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public XRecyclerVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_item,viewGroup,false);
        XRecyclerVH xRecyclerVH  = new XRecyclerVH(view);
        return xRecyclerVH;
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerVH xRecyclerVH, int postion) {
        //获取数据
        ProductBean.DataBean productData = list.get(postion);
        xRecyclerVH.tv_title.setText(productData.getTitle());
        //截取图片数组
        String images = productData.getImages();
        String[] imageArrt = images.split("\\|");
        if (imageArrt != null && imageArrt.length > 0){
            Glide.with(context).load(imageArrt[0]).into(xRecyclerVH.iv_productIcon);
        } else {
            xRecyclerVH.iv_productIcon.setImageResource(R.drawable.ic_launcher_background);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class XRecyclerVH extends XRecyclerView.ViewHolder{

        ImageView iv_productIcon;
        TextView tv_title;

        public XRecyclerVH(View itemView) {
            super(itemView);
            this.iv_productIcon = itemView.findViewById(R.id.iv_productIcon);
            this.tv_title = itemView.findViewById(R.id.tv_title);
        }
    }
}
