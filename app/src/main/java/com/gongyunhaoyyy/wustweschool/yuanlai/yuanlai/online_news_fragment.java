package com.gongyunhaoyyy.wustweschool.yuanlai.yuanlai;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gongyunhaoyyy.wustweschool.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 99460 on 2017/10/28.
 */

public class online_news_fragment extends Fragment implements View.OnClickListener {

    private CustomPopupWindow mCustomPopupWindow;
    private Button mImageButton;//悬浮窗的关闭按钮
    private View mLayoutPopupWindowView;//悬浮窗的布局
    private TextView mTvActivityRule;//悬浮窗的内容


    private List<element_item> notifications = new ArrayList<>();

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle saveInstanceState){
        final View v = inflater.inflate(R.layout.fragment_online_news,parent,false);

        initNotification();
        recyclerView = (RecyclerView) v.findViewById(R.id.recycleview_5);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        Element_item_Adapter adapter = new Element_item_Adapter(notifications);
        recyclerView.setAdapter(adapter);
        adapter.setmOnItemClickListener(new Element_item_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                mLayoutPopupWindowView = LayoutInflater.from(getActivity()).inflate(R.layout
                        .popupwindow_activity_rule, null);
                mCustomPopupWindow = new CustomPopupWindow(v.findViewById(R.id.test_5),
                        getActivity(), mLayoutPopupWindowView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                        .LayoutParams.WRAP_CONTENT, true);
                mCustomPopupWindow.setOnPopupWindowListener(new CustomPopupWindow
                        .PopupWindowListener() {

                    // TODO 设置活动内容
                    @Override
                    public void initView() {
                        mImageButton = (Button) mLayoutPopupWindowView.findViewById(R.id
                                .i_know);
                        mImageButton.setOnClickListener(online_news_fragment.this);
                        mTvActivityRule = (TextView) mLayoutPopupWindowView.findViewById(R.id
                                .popupwindow_activity_rule_text);
                        mTvActivityRule.setText(notifications.get(position).toString());

                    }
                });
                mCustomPopupWindow.showView();
                Animation scaleAanimation = AnimationUtils.loadAnimation(getActivity(),R.anim.popupwindow_fade_in);
                mLayoutPopupWindowView.startAnimation(scaleAanimation);
                mCustomPopupWindow.setBackgroundAlpha(0.85f);
            }
        });
        return v;
    }

    private void initNotification() {
        for (int i=0; i<2 ;i++){
            element_item notification_1 = new element_item("关于贫困生认定这件事情，我在2017年7月1日、2017年9月6日、2017年9月8日、2017年9月28日，在年级群里面多次提醒过。\n" +
                    "\n" +
                    "如果还有同学说现在没有盖章，能不能过两天交，这只能说明你对自己的事情不负责，肯定是不行的。\n");
            notifications.add(notification_1);
            element_item notification_2 = new element_item("全体同学请注意：\n" +
                    "    根据教育部、学校有关通知要求，为公平、公正、合理地分配资助资源，切实保证国家制定的各项资助政策和措施真正落实到家庭经济困难学生身上，现特在我院16级开展家庭经济困难学生认定。\n" +
                    "    请需要申请认定为家庭经济困难学生，于明天晚上11点之前，向班级评议小组组长递交如下材料：\n" +
                    "    1、去年向学院递交了材料、已经被认定为家庭经济困难学生的（不管有没有获得助学金），如果今年还想认定，请上交盖章了的《武汉科技大学家庭经济困难学生认定申请表》（群共享，7月1日，附件2）；此外，还要用武科大信纸写一份家庭经济情况说明，要求至少800字。\n" +
                    "    2、去年没有向学院递交了材料、没有被认定为家庭经济困难学生的，如果今年想认定，请上交盖章了的《高等学校学生及家庭情况调查表》（群共享，7月1日，附件1）；同时，一并上交《武汉科技大学家庭经济困难学生认定申请表》（群共享，7月1日，附件2），这个表的盖章处不要求盖章，不用家长签字；此外，还要用武科大信纸写一份家庭经济情况说明，要求至少800字。\n" +
                    "\n" +
                    "    凡与班长或者生活委员要求不符的，以我的通知为准。\n" +
                    "\n" +
                    "    特此通知。\n");
            notifications.add(notification_2);
            element_item notification_3 = new element_item("通知:\n" +
                    "    为了进一步推动我校体育活动的蓬勃开展，增强同学们的身体素质，丰富学校的文体生活，学校决定于2017年10月26日至28日举行第55届运动会。\n" +
                    "\n" +
                    "对于大二同学，可以报名的项目如下：\n" +
                    "田径项目\n" +
                    "1、男子组：\n" +
                    "100米、200米、400米、800米、1500米、5000米、110米栏、400米栏、4×100米接力、4×400米接力、跳高、跳远、三级跳远、铅球、五项全能（第一天：100米、跳远、铅球；第二天：跳高、1000米）\n" +
                    "\n" +
                    "2、女子组：\n" +
                    "100米、200米、400米、800米、1500米、3000米、100米栏、4×100米接力、4×400米接力、跳高、跳远、三级跳远、铅球、四项全能（第一天：200米、铅球；第二天：跳远、800米）\n" +
                    "\n" +
                    "3、60米混合迎面接力：每队20名（男、女各10名）运动员，分两组在相距60米的同一跑道上相向站立进行接力比赛，男女站位顺序自定。\n" +
                    "\n" +
                    "    请要参加的同学将报名信息按格式（项目：XXX； 姓名：XXX；学号：XXX；  性别：XXX; 班级：XXX； 电话：XXX； QQ：XXX；)于10月15日晚前发送到指定邮箱:774572296@qq.com。 \n" +
                    "\n" +
                    "    为了鼓励大家参赛，在往年发放补贴和奖金的基础上，从今年开始，学院拟对参与运动会获奖的同学，额外给予F2奖学金加分。希望有意向的同学积极报名，认真准备，为学院增光！\n");
            notifications.add(notification_3);
            element_item notification_4 = new element_item("全体同学请注意：\n" +
                    "    根据湖北省学生资助管理中心的通知，2016-2017学年度国家奖学金评审工作已经启动，我院2016级国家奖学金分配计划名额为2人。\n" +
                    "    国家奖学金奖励对象为具有我校普通全日制学籍的在校学生，要求是二年级及以上年级特别优秀的本科学生，其中只有获得学校优秀学生奖学金特等奖的学生才具备申请资格。\n" +
                    "    请获得校特等奖学金的同学中，有意申请国家奖学金的，于明天中午12点之前，主动与我取得联系，填写申请表。\n" +
                    "    谢谢。特此通知！\n");
            notifications.add(notification_4);
            element_item notification_5 = new element_item("各位同学：\n" +
                    "    关于开展2017-2018学年度大学生科技创新基金研究项目立项工作的通知，我已经上传群共享。我院今年有A类项目指标9个，B类项目指标2个，C类项目指标若干。\n" +
                    "    \n" +
                    "    资助范围：重点资助学术思想新颖、目的意义明确、立论根据充足、具有创新性和探索性、研究方案合理、技术路线可行、实施条件具备的项目。具体资助范围为：\n" +
                    "    1．小发明、小创作、小设计等项目；\n" +
                    "    2．开放实验室或创新基地中的综合性、设计性、应用性、创新性实验项目；\n" +
                    "    3．有关教师科研课题中的子项目；\n" +
                    "    4．基础性研究、应用性研究及创新性研究项目；\n" +
                    "    5．社会调研项目；\n" +
                    "    6．其他有价值的研究与实践项目。\n" +
                    "\n" +
                    "    同学们有好的科技创新想法的，可以积极申报；也可以主动与自己的班主任、自己熟悉的专业老师取得联系，争取利用他们的子项目开展科研。请于10月10日中午12点之前将纸版项目申请书交到学院一楼学团办公室（30120）彭小波老师收。\n" +
                    "\n" +
                    "    欢迎大家积极申报！\n");
            notifications.add(notification_5);
            element_item notification_6 = new element_item("“特色团支部生活竞赛”的加分按照“学科与文体竞赛B4”加分。\n" +
                    "另外，昨天文艺部发的“五月的花海”证明，名单有误，名单以文艺部今天下发的新证明为准。（应该是找文艺委员领取复印件）\n");
            notifications.add(notification_6);
            element_item notification_7 = new element_item("奖学金F1已经上传至群共享，大家直接查找填写即可。\n" +
                    "\n" +
                    "如果自己算的结果与学院给的数据不符，请联系学委咨询计算规则。\n");
            notifications.add(notification_7);
            element_item notification_8 = new element_item("凡是申请奖学金的同学，所有加分项都需要提供证明材料，复印件有效。其中，互联网+直接打印获奖名单，“特色团支部生活竞赛”需要复印证书（找团支书），科技型社团加分需要提供指导老师签字的证明材料，新闻稿直接截图打印（要可以看清楚来源于哪个网站）。\n");
            notifications.add(notification_8);
            element_item notification_9 = new element_item("十、鉴于的F1的计算机比较复杂，很多学委都是多次算错。所以，我这边在进行第二轮的学分计算机核对。预计今晚分数会公布，明早大家肯定可以看到。鉴于此，大家明天下午5点之前，将测评表以及证明材料复印件，交给学委即可。\n");
            notifications.add(notification_9);
            element_item notification_10 = new element_item("六、各团支部在学院“特色团支部生活竞赛”中获奖的，按照相应的级别，团支书加满分，其他同学减半加分。\n");
            notifications.add(notification_10);
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {

            //关闭悬浮窗
            case R.id.i_know:
                mCustomPopupWindow.dismiss();
                mCustomPopupWindow.setBackgroundAlpha(1);
                break;
        }
    }

}
