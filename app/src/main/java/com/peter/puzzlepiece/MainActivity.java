package com.peter.puzzlepiece;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<ImageView> movableImages = new ArrayList<>();
    ArrayList<ImageView> targetImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movableImages.add(findViewById(R.id.a_move_img));
        movableImages.add(findViewById(R.id.b_move_img));
        movableImages.add(findViewById(R.id.c_move_img));


        targetImages.add(findViewById(R.id.a_img));
        targetImages.add(findViewById(R.id.b_img));
        targetImages.add(findViewById(R.id.c_img));



        for (ImageView movable : movableImages){
            movable.setOnLongClickListener(longClickListener);
        }

        for (ImageView target : targetImages){
            target.setOnDragListener(dragListener);
        }


        Button btn = findViewById(R.id.random_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RandomizePositon();
            }
        });
        RandomizePositon();
    }

    void RandomizePositon(){
        for (ImageView t : movableImages){
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            Float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            Float dpWidth = displayMetrics.widthPixels / displayMetrics.density;


            int x = generateRandomNumber(0, Math.round(dpWidth) * 2);
            int y = generateRandomNumber(0, Math.round(dpHeight) * 2);


            t.setX(x);
            t.setY(y);
        }
    }


    public int generateRandomNumber(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }


    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData clipData = ClipData.newPlainText("", "");
            View.DragShadowBuilder myShadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(clipData, myShadowBuilder, v, 0);
            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();
            //Log.e("Drag", "num : " +  dragEvent);
            final View view = (View)event.getLocalState();

            switch (dragEvent){
                case  DragEvent.ACTION_DRAG_ENTERED:

                    // v is place that was put
                    // view is what was moves

                    if(v.getTag() == view.getTag()){
                        ImageView img = (ImageView) v;

                        Log.e("S", "save: " + v.getTag());
                        img.setBackgroundColor(Color.rgb(100, 100, 50));
                        //view.setAlpha(0);
                    }

                break;
                case DragEvent.ACTION_DRAG_EXITED:

                    if(v.getTag() == view.getTag()){
                        ImageView img = (ImageView) v;

                        img.setBackgroundColor(Color.rgb(255, 255, 255));
                    }
                    break;
            }
            return true;
        }
    };
}