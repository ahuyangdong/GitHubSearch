package com.github.search.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.search.R;
import com.github.search.bean.Owner;
import com.github.search.bean.Repo;
import com.github.search.network.ImageLoader;
import com.github.search.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 仓库数据列表适配器
 */
public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.ViewHolder> {
    private Context context;
    private List<Repo> datas; // 列表
    private OnItemClickListener itemClickListener;

    public RepoListAdapter(Context context) {
        this.context = context;
        datas = new ArrayList<Repo>();
    }

    /**
     * 数据列表
     *
     * @param datas
     */
    public void setList(List<Repo> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    /**
     * 注册点击事件
     *
     * @param itemClickListener
     */
    public void addOnItemClickLister(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null) {
            context = parent.getContext();
        }
        View viewItem = LayoutInflater.from(context).inflate(R.layout.item_repo, parent, false);
        ViewHolder holder = new ViewHolder(viewItem);
        if (itemClickListener != null) {
            viewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onClick(view, (int) view.getTag());
                }
            });
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position); // 保存位置

        Repo data = datas.get(position);
        Owner owner = data.getOwner();
        if (owner == null) {
            // 线上数据无内容就读DB关联数据
            owner = data.getOwnerDB();
        }

        holder.tvFullName.setText(CommonUtil.getNotNullString(data.getFull_name()));
        if (owner == null) {
            holder.tvLogin.setText("");
        } else {
            holder.tvLogin.setText(CommonUtil.getNotNullString(owner.getLogin()));
        }
        holder.tvDesc.setText(CommonUtil.getNotNullString(data.getDescription()));
        holder.tvWatch.setText(String.valueOf(data.getWatchers_count()));
        holder.tvStar.setText(String.valueOf(data.getStargazers_count()));

        // 加载头像
        loadAvatar(owner, holder.imgAvatar);
    }

    /**
     * 加载头像
     *
     * @param owner     作者
     * @param imageView 图片容器
     */
    private void loadAvatar(Owner owner, ImageView imageView) {
        if (owner != null && !CommonUtil.isNull(owner.getAvatar_url())) {
            ImageLoader.loadRoundImage(context, imageView, owner.getAvatar_url(), R.drawable.user_default);
            imageView.setVisibility(View.VISIBLE);
            return;
        }
        imageView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_avatar)
        ImageView imgAvatar; // 头像
        @BindView(R.id.txt_full_name)
        TextView tvFullName; // 项目名
        @BindView(R.id.txt_login)
        TextView tvLogin; // 作者
        @BindView(R.id.txt_desc)
        TextView tvDesc; // 描述
        @BindView(R.id.txt_watch)
        TextView tvWatch; // 阅读数
        @BindView(R.id.txt_star)
        TextView tvStar; // 点赞数


        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        /**
         * 列表单击事件
         *
         * @param view
         * @param position
         */
        void onClick(View view, int position);
    }
}
