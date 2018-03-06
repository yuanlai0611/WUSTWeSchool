package com.gongyunhaoyyy.wustweschool.viewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gongyunhaoyyy.wustweschool.Activity.ChooseLessonActivity;
import com.gongyunhaoyyy.wustweschool.Activity.ScoreActivity;
import com.gongyunhaoyyy.wustweschool.Activity.TeachingAssessmentActivity;
import com.gongyunhaoyyy.wustweschool.Base.BaseFragment;
import com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai.library.library_login_activity;
import com.gongyunhaoyyy.wustweschool.R;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;

/**
 * Created by GongYunHao on 2017/10/11.
 */

public class PagerMain extends BaseFragment {

    private Context mContext;
    private String uddt;
    private Boolean isLoginLibrary;
    private String cookie;
    private TextView textViewFirstBook;
    private TextView textViewSecondBook;
    private TextView textViewThirdBook;
    private View bookFirstDot;
    private View bookSecondDot;
    private View bookThirdDot;
    private View bookFirstLine;
    private View bookSecondLine;
    private View bookThirdLine;
    private View bookFirstRow;
    private View bookSecondRow;
    private View bookThirdRow;
    private Elements elements;
    private Document document;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initViews(View view){
        textViewFirstBook = (TextView)view.findViewById(R.id.book_first_name);
        textViewSecondBook = (TextView)view.findViewById(R.id.book_second_name);
        textViewThirdBook = (TextView)view.findViewById(R.id.book_third_name);
        bookFirstDot = (View)view.findViewById(R.id.book_first_dot);
        bookSecondDot = (View)view.findViewById(R.id.book_second_dot);
        bookThirdDot = (View)view.findViewById(R.id.book_third_dot);
        bookFirstLine = (View)view.findViewById(R.id.book_first_line);
        bookSecondLine = (View)view.findViewById(R.id.book_second_line);
        bookThirdLine = (View)view.findViewById(R.id.book_third_line);
        bookFirstRow = (View)view.findViewById(R.id.book_first_row);
        bookSecondRow = (View)view.findViewById(R.id.book_second_row);
        bookThirdRow = (View)view.findViewById(R.id.book_third_row);
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("cookie",Context.MODE_PRIVATE);
        cookie = sharedPreferences.getString("PHPSESSID","");
        isLoginLibrary = sharedPreferences.getBoolean("isLoginLibrary",false);
        if (isLoginLibrary == true){
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try{

                        Connection.Response response = Jsoup.connect("http://opac.lib.wust.edu.cn:8080/reader/book_hist.php")
                                .cookie("PHPSESSID",cookie)
                                .execute();
                        Log.d("library_url",response.url().toString());
                        if (response.url().toString().equals("http://opac.lib.wust.edu.cn:8080/reader/login.php")){
                            Log.d("PagerMain","跳");
                            Intent intent = new Intent(getActivity(),library_login_activity.class);
                            startActivity(intent);
                        }else{
                            document = response.parse();
                            elements = document
                                    .select("table.table_line")
                                    .select("tbody")
                                    .select("tr");
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (elements.size() == 2){
                                        textViewFirstBook.setText(elements.get(1).select("td").select("a").text());
                                        textViewFirstBook.setVisibility(View.VISIBLE);
                                        bookFirstDot.setVisibility(View.VISIBLE);
                                        bookFirstLine.setVisibility(View.VISIBLE);
                                        bookFirstRow.setVisibility(View.VISIBLE);
                                        textViewSecondBook.setVisibility(View.GONE);
                                        bookSecondDot.setVisibility(View.GONE);
                                        bookSecondLine.setVisibility(View.GONE);
                                        bookSecondRow.setVisibility(View.GONE);
                                        textViewThirdBook.setVisibility(View.GONE);
                                        bookThirdDot.setVisibility(View.GONE);
                                        bookThirdLine.setVisibility(View.GONE);
                                        bookThirdRow.setVisibility(View.GONE);
                                    }else if(elements.size() == 3){
                                        textViewFirstBook.setText(elements.get(1).select("td").get(2).select("a").text());
                                        textViewFirstBook.setVisibility(View.VISIBLE);
                                        bookFirstDot.setVisibility(View.VISIBLE);
                                        bookFirstLine.setVisibility(View.VISIBLE);
                                        bookFirstRow.setVisibility(View.VISIBLE);
                                        textViewSecondBook.setText(elements.get(2).select("td").get(2).select("a").text());
                                        textViewSecondBook.setVisibility(View.VISIBLE);
                                        bookSecondDot.setVisibility(View.VISIBLE);
                                        bookSecondLine.setVisibility(View.VISIBLE);
                                        bookSecondRow.setVisibility(View.VISIBLE);
                                        textViewThirdBook.setVisibility(View.GONE);
                                        bookThirdDot.setVisibility(View.GONE);
                                        bookThirdLine.setVisibility(View.GONE);
                                        bookThirdRow.setVisibility(View.GONE);
                                    }else if(elements.size() == 4){
                                        textViewFirstBook.setText(elements.get(1).select("td").get(2).select("a").text());
                                        textViewFirstBook.setVisibility(View.VISIBLE);
                                        bookFirstDot.setVisibility(View.VISIBLE);
                                        bookFirstLine.setVisibility(View.VISIBLE);
                                        bookFirstRow.setVisibility(View.VISIBLE);
                                        textViewSecondBook.setText(elements.get(2).select("td").get(2).select("a").text());
                                        textViewSecondBook.setVisibility(View.VISIBLE);
                                        bookSecondDot.setVisibility(View.VISIBLE);
                                        bookSecondLine.setVisibility(View.VISIBLE);
                                        bookSecondRow.setVisibility(View.VISIBLE);
                                        textViewThirdBook.setText(elements.get(3).select("td").get(2).select("a").text());
                                        textViewThirdBook.setVisibility(View.VISIBLE);
                                        bookThirdDot.setVisibility(View.VISIBLE);
                                        bookThirdLine.setVisibility(View.VISIBLE);
                                        bookThirdRow.setVisibility(View.VISIBLE);
                                    }


                                }
                            });
                        }




                    }catch (IOException e){
                        e.printStackTrace();
                    }


                }
            }).start();
        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view= inflater.inflate( R.layout.pager_main, container, false);

        view.findViewById(R.id.score).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startIntent( ScoreActivity.class );
            }
        });
        view.findViewById( R.id.choose_lesson ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startIntent( ChooseLessonActivity.class );
            }
        } );
        view.findViewById( R.id.empty_class ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
                startIntent( TeachingAssessmentActivity.class );
            }
        } );
        view.findViewById( R.id.more_thing ).setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View v) {
        initViews(view);

            }
        } );

        return view;
    }
}
