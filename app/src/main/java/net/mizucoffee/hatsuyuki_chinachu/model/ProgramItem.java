package net.mizucoffee.hatsuyuki_chinachu.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import net.mizucoffee.hatsuyuki_chinachu.tools.Shirayuki;

public class ProgramItem extends BaseObservable {

    private String title;
    private String description;
    private String date;
    private String thumbUrl;
    private String channelUrl;
    private String id;
    private String category;
    private String subtitle;
    private String channelName;
    private String channelId;
    private long start;
    private long end;
    private long seconds;
    private Boolean isDownloading = false;

    @Bindable
    public long getEnd() {
        return end;
    }
    @Bindable
    public long getSeconds() {
        return seconds;
    }

    @Bindable
    public Boolean getDownloading() {
        return isDownloading;
    }

    public void setDownloading(Boolean downloading) {
        isDownloading = downloading;
    }

    @Bindable
    public String getId() {
        return id;
    }

    @Bindable
    public long getStart() {
        return start;
    }

    @Bindable
    public String getChannelUrl() {
        return channelUrl;
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    @Bindable
    public String getDate() {
        return date;
    }

    @Bindable
    public String getDescription() {
        return description;
    }

    @Bindable
    public String getThumbUrl() {
        return thumbUrl;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getSubtitle() {
        return subtitle;
    }

    @Bindable
    public String getChannelId() {
        return channelId;
    }

    @Bindable
    public String getChannelName() {
        return channelName;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
        notifyChange();
    }

    public void setChannelUrl(String channelUrl) {
        this.channelUrl = channelUrl;
        notifyChange();
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title){
        this.title = title;
        notifyChange();
    }

    public void setDescription(String description){
        this.description = description;
        notifyChange();
    }

    public void setDate(String date){
        this.date = date;
        notifyChange();
    }

    public void setThumbUrl(String thumbUrl){
        this.thumbUrl = thumbUrl;
        notifyChange();
    }

    public void setCategory(String category) {
        this.category = category;
        notifyChange();
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
        notifyChange();
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
        notifyChange();
    }


    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }



    public void setEnd(long end) {
        this.end = end;
    }


    public void setStart(long start) {
        this.start = start;
    }

    @BindingAdapter("backgroundCategory")
    public static void setBackgroundCategory(LinearLayout linearLayout, String category){
        if(category == null) return;
        Shirayuki.log(category);
        linearLayout.setBackgroundColor(ContextCompat.getColor(linearLayout.getContext(), Shirayuki.getBackgroundColorFromCategory(category)));
    }

    @BindingAdapter("thumbUrl")
    public static void setThumbUrl(ImageView i, String url){
        Picasso.with(i.getContext()).load(url + "?pos=30").into(i, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(i.getContext()).load(url + "?pos=0").into(i);
            }
        });
    }

    @BindingAdapter("channelUrl")
    public static void setChannelUrl(ImageView i, String url){
        Picasso.with(i.getContext()).load(url).into(i);
        i.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(i.getDrawable() != null) {
                    ViewGroup.LayoutParams params = i.getLayoutParams();
                    params.width = i.getHeight() / 9 * 16;
                    i.setLayoutParams(params);
                    i.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }
}
