package com.iut.catchpoint.catchpoint;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class DialogAbandon extends Dialog {

    private int height;
    private int width;
    OnMyDialogResult mDialogResult;

    public DialogAbandon(Context context, int height, int width) {
        super(context);
        this.height = height-(height*70/100);
        this.width = width-(width*10/100);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dialog_abandon);
        getWindow().setLayout(width,height);

        ImageView positive = (ImageView) findViewById(R.id.valid_abandon);
        ImageView negative = (ImageView) findViewById(R.id.cancel_abandon);

        positive.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( mDialogResult != null ){
                    mDialogResult.finish("yes");
                }
                DialogAbandon.this.dismiss();
            }
        });

        negative.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if( mDialogResult != null ){
                    mDialogResult.finish("no");
                }
                DialogAbandon.this.dismiss();
            }
        });
    }

    public void setDialogResult(OnMyDialogResult dialogResult){
        mDialogResult = dialogResult;
    }

    public interface OnMyDialogResult{
        void finish(String result);
    }
}
