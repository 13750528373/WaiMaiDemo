package com.kx.waimaidemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.kx.waimaidemo.R;
import com.kx.waimaidemo.model.bean.Seller;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
 *  创建者:   KX
 *  创建时间:  2017/10/25 22:34
 *  描述：    TODO
 */
public class HomeRvAdapter extends RecyclerView.Adapter {


    private List<Seller> mData;
    private Context mContext;

    private static final int TYPE_TITLE = 0;
    private static final int TYPE_NORMAL = 1;

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TITLE;
        } else {
            return TYPE_NORMAL;
        }
    }

    public void setData(List<Seller> data, Context context) {
        mData = data;
        mContext = context;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_TITLE:
                View titleView = View.inflate(mContext, R.layout.item_title, null);
                return new TitleHolder(titleView);
            case TYPE_NORMAL:
                View itemView = View.inflate(mContext, R.layout.item_seller, null);
                return new SellerHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case TYPE_TITLE:
                TitleHolder titleHolder = (TitleHolder) holder;
                break;
            case TYPE_NORMAL:
                SellerHolder sellerHolder = (SellerHolder) holder;
                sellerHolder.bindData(mData.get(position-1));
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }


    static class SellerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.seller_logo)
        ImageView mSellerLogo;
        @BindView(R.id.tvCount)
        TextView mTvCount;
        @BindView(R.id.tv_title)
        TextView mTvTitle;
        @BindView(R.id.ratingBar)
        RatingBar mRatingBar;
        @BindView(R.id.tv_home_sale)
        TextView mTvHomeSale;
        @BindView(R.id.tv_home_send_price)
        TextView mTvHomeSendPrice;
        @BindView(R.id.tv_home_distance)
        TextView mTvHomeDistance;

        SellerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(Seller seller) {
            mTvTitle.setText(seller.getName());
        }
    }

    static class TitleHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.slider)
        SliderLayout sliderLayout;

        TitleHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bindData(String s) {

            HashMap<String, String> url_maps = new HashMap<String, String>();
            if (url_maps.size() == 0) {
                url_maps.put("30分送达", "http://imgsrc.baidu.com/imgad/pic/item/b3119313b07eca80e3c8dbc29b2397dda04483c2.jpg");
                url_maps.put("线上线下", "http://static.ename.cn/data/article/201608/15/1471241392.jpg");
                url_maps.put("外卖达人", "http://www.takefoto.cn/wp-content/uploads/2017/03/201703230315494511.jpg");
                url_maps.put("好吃停不下来", "http://n1.itc.cn/img8/wb/recom/2016/08/18/147146726376185031.JPEG");

                for (String desc : url_maps.keySet()) {
                    TextSliderView textSliderView = new TextSliderView(itemView.getContext());
                    textSliderView
                            .description(desc)
                            .image(url_maps.get(desc));
                    sliderLayout.addSlider(textSliderView);
                }
            }
        }



    }


}
