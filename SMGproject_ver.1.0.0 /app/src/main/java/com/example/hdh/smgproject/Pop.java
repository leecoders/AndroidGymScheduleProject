    package com.example.hdh.smgproject;

    import android.app.Activity;
    import android.content.Context;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.util.DisplayMetrics;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ListAdapter;
    import android.widget.ListView;

    import java.util.ArrayList;
    import java.util.List;

    /**
     * Created by hwangdahyeon on 2018. 5. 21..
     */

    public class Pop extends Activity {

        private ListView memberListView;
        private MemberListAdapter memberListAdapter;
        private List<Member> memberList;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.pop);

            DisplayMetrics dm = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dm);

            int width = dm.widthPixels;
            int height = dm.heightPixels;

            getWindow().setLayout((int) (width * 0.9), (int) (height * 0.85));


            //리스트뷰를 어댑터로...
            memberListView = (ListView) findViewById(R.id.ptListView);
            memberList = new ArrayList<Member>();
            memberListAdapter = new MemberListAdapter(getApplicationContext(), memberList, this);
            memberListView.setAdapter(memberListAdapter);

            Member member = new Member("황", "황다현", "컴퓨터과학과 13학번", "kjg123kg@gmail.com");
            memberList.add(member);
            Member member1 = new Member("이", "이재민", "컴퓨터과학과 13학번", "mammoth10@naver.com");
            memberList.add(member1);
            Member member2 = new Member("김종", "김종범", "컴퓨터과학과 13학번", "smucs13@naver.com");
            memberList.add(member2);
            Member member3 = new Member("한", "한상국", "컴퓨터과학과 13학번", "sym00243@naver.com");
            memberList.add(member3);
            Member member4 = new Member("임", "임성민", "컴퓨터과학과 13학번", "tjdals7825@naver.com");
            memberList.add(member4);
            Member member5 = new Member("김연", "김연형", "컴퓨터과학과 13학번", "hyeng1028@naver.com");
            memberList.add(member5);

            setListViewHeightBasedOnItems(memberListView);
        }

        public void setListViewHeightBasedOnItems(ListView listView) {

            // Get list adpter of listview;
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null)  return;

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight() + 20;
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *  (numberOfItems - 1) + 30;

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
        }

    }
