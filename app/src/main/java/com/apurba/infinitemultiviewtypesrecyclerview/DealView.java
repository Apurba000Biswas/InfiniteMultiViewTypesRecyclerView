package com.apurba.infinitemultiviewtypesrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.Nullable;

public class DealView extends LinearLayout {

    private TextView tvHeader;
    private TextView tvContent;

    public DealView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);

        LayoutInflater.from(context).inflate(R.layout.deal, this, true);

        tvHeader = this.findViewById(R.id.header);
        tvContent = this.findViewById(R.id.content);
    }


    public void setHeader(String header){
        this.tvHeader.setText(header);
    }

    public void setContent(String content){
        this.tvContent.setText(content);
    }


    public class Model implements ItemViewModel{
        private String id;
        private String content;

        public Model(String id, String content){
            this.id = id;
            this.content = content;
        }

        @NotNull
        @Override
        public String getId() {
            return this.id;
        }
    }

}
