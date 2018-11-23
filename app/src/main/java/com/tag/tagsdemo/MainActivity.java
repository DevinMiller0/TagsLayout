package com.tag.tagsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TagsLayout tagsLayout = findViewById(R.id.tags_test);
        ArrayList<String> list = new ArrayList<>();
        list.add("男士工装裤");
        list.add("鞋子 男");
        list.add("弹力牛仔裤");
        list.add("匡威 男鞋");
        list.add("工装 羽绒服");
        list.add("GXG");
        list.add("无印良品");
        list.add("匡威1970s");
        list.add("棉服男");
        list.add("优衣库");
        list.add("H&M");
        list.add("天猫国际");
        list.add("NBA");
        tagsLayout.setTags(list);
        tagsLayout.setOnTagsOnClickListener(new TagsLayout.OnTagsClickListener() {
            @Override
            public void onTagsClick(View view, String tagText) {
                Toast.makeText(MainActivity.this, tagText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
