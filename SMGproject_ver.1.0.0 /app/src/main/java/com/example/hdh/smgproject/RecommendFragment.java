package com.example.hdh.smgproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;


public class RecommendFragment extends Fragment {

    private ArrayAdapter heightApapter;
    private Spinner heightSpinner;
    private ArrayAdapter weightApapter;
    private Spinner weightSpinner;
    private ArrayAdapter purposeApapter;
    private Spinner purposeSpinner;

    private String userGender;
    TextView textView;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommend, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        heightSpinner = (Spinner) getView().findViewById(R.id.heightSpinner);
        weightSpinner = (Spinner) getView().findViewById(R.id.weightSpinner);
        purposeSpinner = (Spinner) getView().findViewById(R.id.purposeSpinner);

        heightApapter = ArrayAdapter.createFromResource(getActivity(), R.array.height, android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setAdapter(heightApapter);
        weightApapter = ArrayAdapter.createFromResource(getActivity(), R.array.weight, android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightApapter);
        purposeApapter = ArrayAdapter.createFromResource(getActivity(), R.array.purpose, android.R.layout.simple_spinner_dropdown_item);
        purposeSpinner.setAdapter(purposeApapter);

        RadioGroup genderGroup = (RadioGroup) getView().findViewById(R.id.genderGroup);
        int gengerGroupID = genderGroup.getCheckedRadioButtonId();  //남자인지 여자인지 확인하는변수
        userGender = ((RadioButton) getView().findViewById(gengerGroupID)).getText().toString(); //현재 선택된 젠더의 ID를 가져옴

        //라디오 버튼을 클릭했을때 대한 이벤트 처리
        genderGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton genderButton = (RadioButton) getView().findViewById(checkedId);
                userGender = genderButton.getText().toString();
            }
        });



        button = (Button)getView().findViewById(R.id.recommendButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String height = heightSpinner.getSelectedItem().toString();
                String weight = weightSpinner.getSelectedItem().toString();
                String purpose = purposeSpinner.getSelectedItem().toString();
                String gender = userGender;

                String heightType;
                String weightType;

                textView = (TextView)getView().findViewById(R.id.textRecommend);

                if(gender.equals("남성")) {   //          키 타입
                    if (height.equals("150cm 이하") || height.equals("151cm~160cm")) {
                        heightType = "매우작음";
                    } else if (height.equals("161cm~170cm")) {
                        heightType = "작음";
                    } else if(height.equals("171cm~180cm")) {
                        heightType = "보통";
                    } else{
                        heightType = "큼";
                    }
                }
                else {  // "여성"
                    if (height.equals("150cm 이하")) {
                        heightType = "작음";
                    } else if(height.equals("151cm~160cm")){
                        heightType = "보통";
                    } else if (height.equals("161cm~170cm") || height.equals("171cm~180cm")) {
                        heightType = "큼";
                    } else {
                        heightType = "매우큼";
                    }
                }

                if(heightType.equals("매우작음")) {         // 몸무게 타입
                    if(weight.equals("40kg 이하")) {
                        weightType = "마름";
                    } else if (weight.equals("41kg~50kg")) {
                        weightType = "보통";
                    } else if (weight.equals("51kg~60kg")) {
                        weightType = "통통";
                    } else if (weight.equals("61kg~70kg")) {
                        weightType = "통통";
                    } else if (weight.equals("71kg~80kg")) {
                        weightType = "뚱뚱";
                    } else if (weight.equals("81kg~90kg")) {
                        weightType = "뚱뚱";
                    } else {
                        weightType = "뚱뚱";
                    }
                }
                else if(heightType.equals("작음")) {
                    if(weight.equals("40kg 이하")) {
                        weightType = "마름";
                    } else if (weight.equals("41kg~50kg")) {
                        weightType = "마름";
                    } else if (weight.equals("51kg~60kg")) {
                        weightType = "보통";
                    } else if (weight.equals("61kg~70kg")) {
                        weightType = "통통";
                    } else if (weight.equals("71kg~80kg")) {
                        weightType = "뚱뚱";
                    } else if (weight.equals("81kg~90kg")) {
                        weightType = "뚱뚱";
                    } else {
                        weightType = "뚱뚱";
                    }
                }
                else if(heightType.equals("보통")) {
                    if(weight.equals("40kg 이하")) {
                        weightType = "마름";
                    } else if (weight.equals("41kg~50kg")) {
                        weightType = "마름";
                    } else if (weight.equals("51kg~60kg")) {
                        weightType = "마름";
                    } else if (weight.equals("61kg~70kg")) {
                        weightType = "보통";
                    } else if (weight.equals("71kg~80kg")) {
                        weightType = "통통";
                    } else if (weight.equals("81kg~90kg")) {
                        weightType = "뚱뚱";
                    } else {
                        weightType = "뚱뚱";
                    }
                }
                else if(heightType.equals("큼")) {
                    if(weight.equals("40kg 이하")) {
                        weightType = "마름";
                    } else if (weight.equals("41kg~50kg")) {
                        weightType = "마름";
                    } else if (weight.equals("51kg~60kg")) {
                        weightType = "마름";
                    } else if (weight.equals("61kg~70kg")) {
                        weightType = "마름";
                    } else if (weight.equals("71kg~80kg")) {
                        weightType = "보통";
                    } else if (weight.equals("81kg~90kg")) {
                        weightType = "통통";
                    } else {
                        weightType = "뚱뚱";
                    }
                }
                else {  // "매우큼"
                    if(weight.equals("40kg 이하")) {
                        weightType = "마름";
                    } else if (weight.equals("41kg~50kg")) {
                        weightType = "마름";
                    } else if (weight.equals("51kg~60kg")) {
                        weightType = "마름";
                    } else if (weight.equals("61kg~70kg")) {
                        weightType = "마름";
                    } else if (weight.equals("71kg~80kg")) {
                        weightType = "보통";
                    } else if (weight.equals("81kg~90kg")) {
                        weightType = "통통";
                    } else {
                        weightType = "뚱뚱";
                    }
                }

                // 목적 구분
                if (purpose.equals("슬림한몸매")) {
                    if(weightType.equals("마름")) {
                        textView.setText("   슬림하고 탄탄한 몸매를 만들기 위해서는 무작정 운동하는 것이 아니라 체형에 맞는 과학적인 운동법과 식단을 구성해서 실행해야 합니다. " +
                                "슬림한 몸매를 위해서는 식단관리 또한 중요한데 저탄수화물 고단백질 식단을 유지해 줍니다. 운동전후로 소량의 탄수화물을 섭취하되 쌀과 같은 고탄수화물 식품보다는 고구마나 감자 같이 그 에너지가 오래갈 수 있는 식품이 좋습니다.\n\n" +
                                "   회원님은 현재 '마른체형'으로 슬림한 몸매를 목표로 하는 경우 우선 체중을 늘리는 것이 중요합니다. 웨이트 트레이닝시 중량 운동을 위주로 근육의 부피를 늘리고, 충분한 음식과 적절한 보충제 섭취로 체형을 키운 다음 근육과 피부사이의 지방층을 제거하는 것이 좋습니다.");
                    } else if(weightType.equals("보통")) {
                        textView.setText("   슬림하고 탄탄한 몸매를 만들기 위해서는 무작정 운동하는 것이 아니라 체형에 맞는 과학적인 운동법과 식단을 구성해서 실행해야 합니다. " +
                                "슬림한 몸매를 위해서는 식단관리 또한 중요한데 저탄수화물 고단백질 식단을 유지해 줍니다. 운동전후로 소량의 탄수화물을 섭취하되 쌀과 같은 고탄수화물 식품보다는 고구마나 감자 같이 그 에너지가 오래갈 수 있는 식품이 좋습니다.\n\n" +
                                "   회원님은 현재 '보통체형'으로 슬림한 몸매를 목표로 하는 경우 자신이 부족한 부위의 근육을 중점적으로 운동하여 전체적인 균형을 잡아주는 것이 좋습니다. " +
                                "중량운동보다는 무게를 줄이고 많은 횟수를 실시하여 근육의 모양을 잡아주는 것이 좋습니다.");
                    } else if(weightType.equals("통통")) {
                        textView.setText("   슬림하고 탄탄한 몸매를 만들기 위해서는 무작정 운동하는 것이 아니라 체형에 맞는 과학적인 운동법과 식단을 구성해서 실행해야 합니다. " +
                                "슬림한 몸매를 위해서는 식단관리 또한 중요한데 저탄수화물 고단백질 식단을 유지해 줍니다. 운동전후로 소량의 탄수화물을 섭취하되 쌀과 같은 고탄수화물 식품보다는 고구마나 감자 같이 그 에너지가 오래갈 수 있는 식품이 좋습니다.\n\n" +
                                "   회원님은 현재 '통통체형'으로 슬림한 몸매를 목표로 하는 경우 약간의 체중조절이 필요합니다. 유산소 운동을 통해 전반적인 체지방을 감소시킨 후 웨이트트레이닝을 진행합니다. " +
                                "중량운동보다는 무게를 줄이고 많은 횟수를 실시하여 근육의 모양을 잡아주는 것이 좋습니다.");
                    } else {    //      "뚱뚱
                        textView.setText("   슬림하고 탄탄한 몸매를 만들기 위해서는 무작정 운동하는 것이 아니라 체형에 맞는 과학적인 운동법과 식단을 구성해서 실행해야 합니다. " +
                                "슬림한 몸매를 위해서는 식단관리 또한 중요한데 저탄수화물 고단백질 식단을 유지해 줍니다. 운동전후로 소량의 탄수화물을 섭취하되 쌀과 같은 고탄수화물 식품보다는 고구마나 감자 같이 그 에너지가 오래갈 수 있는 식품이 좋습니다.\n\n" +
                                "   회원님은 현재 '뚱뚱체형'으로 슬림한 몸매를 목표로 하는 경우 운동을 할 때 유산소 운동에 더 비중을 두어 지방을 빼는 것에 중점을 둡니다. 기계의 도움을 받아 실시하는 스피닝, 가벼운 조깅을 통해 기초체력을 단련하고 동작을 최소화 하면서도 운도에 대한 인내심을 기를 수 있는 필라테스, 요가 등의 운동을 추천합니다. " +
                                "자극적인 음식, 술, 고기 등을 피하고 저염식 식단을 통한 체중관리가 반드시 필요합니다.");
                    }
                } else if (purpose.equals("체력짱짱맨")) {
                    if(weightType.equals("마름")) {
                        textView.setText("   체력기르기는 근력,근지구력, 심폐지구력, 유연성을 높여주며 몸의 체지방률을 낮춰주어 " +
                                "몸을 튼튼하게 만드는 것입니다. " +
                                "유산소 운동은 건강한 다이어트를 위해 없어서는 안될 매우 중요한 요소이며 꾸준히 규칙적으로 " +
                                "하는것이 무엇보다 중요합니다. " +
                                " 사람은 누구나 가지고 있는 체력이 다르기 때문에 본인의 장점과 단점을 먼저 파악하고 " +
                                "어느부분에 중점을 둘것인지를 정해야만 올바른 운동프로그램을 세울수 있습니다.\n\n" +
                                "   회원님은 현재 '마른체형'으로 체력을 목표로 하는 경우라도 근력운동이 필요합니다. " +
                                "운동을 해도 근육이나 지방이 잘 붙지 않는 경우가 많기때문에 식습관에 조금더 신경을 써야합니다. " +
                                "처음에는 무리가 되지 않는 무게로 근력운동을 시작하며 점차 무게와 훈련량을 늘려주어야 하며, " +
                                "강한강도나 장시간의 유산소 운동은 피하는 것이 좋습니다. " +
                                "하체,등,가슴 등 큰근육 위주로 운동을 하며 탄수화물과 단백질이 꼭 포함되어있는 식단으로 " +
                                "자주 식사를 하는 것이 좋습니다. ");
                    } else if(weightType.equals("보통")) {
                        textView.setText("   체력기르기는 근력,근지구력, 심폐지구력, 유연성을 높여주며 몸의 체지방률을 낮춰주어 " +
                                "몸을 튼튼하게 만드는 것입니다. " +
                                "유산소 운동은 건강한 다이어트를 위해 없어서는 안될 매우 중요한 요소이며 꾸준히 규칙적으로 " +
                                "하는것이 무엇보다 중요합니다. " +
                                " 사람은 누구나 가지고 있는 체력이 다르기 때문에 본인의 장점과 단점을 먼저 파악하고 " +
                                "어느부분에 중점을 둘것인지를 정해야만 올바른 운동프로그램을 세울수 있습니다.\n\n" +
                                "   회원님은 현재 '보통체형'으로 근력운동에 조금 더 욕심을 내어보시는것도 좋으며, 유산소 운동은 " +
                                "무리가 가지 않는 선에서 본인이 좋아하는 운동을 골라서 하면 됩니다. " +
                                "신체 밸런스가 무너지지 않도록 근력운동도 반드시 병행하시기 바랍니다.");
                    } else if(weightType.equals("통통")) {
                        textView.setText("   체력기르기는 근력,근지구력, 심폐지구력, 유연성을 높여주며 몸의 체지방률을 낮춰주어 " +
                                "몸을 튼튼하게 만드는 것입니다. " +
                                "유산소 운동은 건강한 다이어트를 위해 없어서는 안될 매우 중요한 요소이며 꾸준히 규칙적으로 " +
                                "하는것이 무엇보다 중요합니다. " +
                                " 사람은 누구나 가지고 있는 체력이 다르기 때문에 본인의 장점과 단점을 먼저 파악하고 " +
                                "어느부분에 중점을 둘것인지를 정해야만 올바른 운동프로그램을 세울수 있습니다.\n\n   회원님은 현재 '통통체형'으로 힘이 들면서도 안전한 유산소 운동 프로그램이 필요합니다. " +
                                "인터벌 트레이닝과 심장강화 운동을 병행하는 것이 좋고, 하체보다는 상체의 지방을 줄일수 있는 " +
                                "줄넘기 같은 유산소 운동과 복근과 팔 근육을 강화할수 있는 근력운동을 병행하는 것이 좋습니다.");
                    } else {    //      "뚱뚱
                        textView.setText("   체력기르기는 근력,근지구력, 심폐지구력, 유연성을 높여주며 몸의 체지방률을 낮춰주어 " +
                                "몸을 튼튼하게 만드는 것입니다. " +
                                "유산소 운동은 건강한 다이어트를 위해 없어서는 안될 매우 중요한 요소이며 꾸준히 규칙적으로 " +
                                "하는것이 무엇보다 중요합니다. " +
                                " 사람은 누구나 가지고 있는 체력이 다르기 때문에 본인의 장점과 단점을 먼저 파악하고 " +
                                "어느부분에 중점을 둘것인지를 정해야만 올바른 운동프로그램을 세울수 있습니다.\n\n   회원님은 현재 '뚱뚱체형'으로 힘이 들면서도 안전한 유산소 운동 프로그램이 필요합니다. " +
                                "이때 관절과 조직(무릎이나 발목)에 부담을 줄일 수 있는 운동을 선택하여야 하고, 무작정 유산소 운동만 하는것이 " +
                                "아니라 근력운동을 병행하여 신체 밸런스를 유지하는것이 중요합니다. 또한 식사조절이 본인의 " +
                                "의지만 가지고 힘든 경우가 많으므로 반드시 트레이너의 도움을 받고 식단조절을 하시기 바랍니다.");
                    }
                } else if (purpose.equals("근육돼지")) {
                    if(weightType.equals("마름")) {
                        textView.setText("   근력운동은 유산소 운동과 함께 병행해서 실시하면 혈당 개선 효과가 매우 뛰어납니다. 근력운동을 통해 근육의 양이 늘어나면, 근육이 사용하는 포도당의 양도 증가하기 때문에 혈당 수치를 조절하는데도 도움이 됩니다. 또한 근육이 늘어나면 기초대사량도 늘어나, 같은 활동에도 더 많은 열량이 소모되어 비만을 예방하거나 개선하는데도 효과적입니다. " +
                                "근력운동의 운동 항목은 크게 핵심운동과 보조운동으로 구분할 수 있는데, 핵심운동은 대근육 집단을 동원하는 운동이고, 보조운동은 주로 단일근육 또는 소근육을 동원하는 운동을 의미합니다 " +
                                "균형적이고 합리적인 트레이닝 프로그램은 최소한 2항목의 상체 핵심운동과 1항목의 허리 및 다리 핵심운동이 포함되어야 합니다. " +
                                "근력운동 동작을 선택할 때는 동작에 어떤 주요근육과 보조근육이 사용되는지 그리고 인체의 어떤 부위가 어떻게 작용하는지 염두에 두고 결정해야 합니다.\n\n" +
                                "   회원님은 현재 '마른체형'으로 다른 체형과 비교해서 똑같은 자극을 줘도 근매스가 훨씬 더 많이 발달 할 수 있습니다. 적은 훈련으로 더 많은 성과를 얻으실 수 있지만 ”마른체형“은 운동결과가 빨리 나타나기 때문에 오버트레이닝현상이 발생할 수 있습니다. " +
                                "'마른체형'이신 회원님은 훈련을 주기별로 분류하고 1주정도 역동적 휴식을 취하시기를 권합니다. 훈련 프로그램은 복합관절 운동과 단순관절 운동을 포함시키기를 권합니다.");
                    } else if(weightType.equals("보통")) {
                        textView.setText("   근력운동은 유산소 운동과 함께 병행해서 실시하면 혈당 개선 효과가 매우 뛰어납니다. 근력운동을 통해 근육의 양이 늘어나면, 근육이 사용하는 포도당의 양도 증가하기 때문에 혈당 수치를 조절하는데도 도움이 됩니다. 또한 근육이 늘어나면 기초대사량도 늘어나, 같은 활동에도 더 많은 열량이 소모되어 비만을 예방하거나 개선하는데도 효과적입니다. \n" +
                                "근력운동의 운동 항목은 크게 핵심운동과 보조운동으로 구분할 수 있는데, 핵심운동은 대근육 집단을 동원하는 운동이고, 보조운동은 주로 단일근육 또는 소근육을 동원하는 운동을 의미합니다. " +
                                "균형적이고 합리적인 트레이닝 프로그램은 최소한 2항목의 상체 핵심운동과 1항목의 허리 및 다리 핵심운동이 포함되어야 합니다. " +
                                "근력운동 동작을 선택할 때는 동작에 어떤 주요근육과 보조근육이 사용되는지 그리고 인체의 어떤 부위가 어떻게 작용하는지 염두에 두고 결정해야 합니다.\n\n" +
                                "   회원님의 체형은 '보통체형'입니다. 보통체형은 신진대사율이 높아 근육과 근력을 형성하기가 어렵습니다. 보통 체형은 근육을 늘리는 데 더 큰 어려움을 겪기 떄문에 웨이트 트레이닝을 할 때 인내심을 갖고 하셔야합니다. " +
                                "빠른 속도로 최적의 근육을 형성하기는 쉽지 않습니다, 따라서 장기간에 걸쳐 느린 속도로 체중을 늘려서 늘어난 체중의 대부분이 근육이 되도록 하시기를 권합니다. " +
                                "식단은 체중1kg당 단백질 3~4g을 섭취하시는게 좋습니다. 단백질 섭취는 총 하루 열량의 최대 30%까지 늘리셔도 됩니다. 탄수화물은 총 하루 열량의 50%, 지방은 20% 정도로 섭취하시기를 권합니다. 근육을 늘리기 위해서는 하루에 연소되는 열량보다 섭취하는 열량이 더 많아야 합니다.");
                    } else if(weightType.equals("통통")) {
                        textView.setText("   근력운동은 유산소 운동과 함께 병행해서 실시하면 혈당 개선 효과가 매우 뛰어납니다. 근력운동을 통해 근육의 양이 늘어나면, 근육이 사용하는 포도당의 양도 증가하기 때문에 혈당 수치를 조절하는데도 도움이 됩니다. 또한 근육이 늘어나면 기초대사량도 늘어나, 같은 활동에도 더 많은 열량이 소모되어 비만을 예방하거나 개선하는데도 효과적입니다. " +
                                "근력운동의 운동 항목은 크게 핵심운동과 보조운동으로 구분할 수 있는데, 핵심운동은 대근육 집단을 동원하는 운동이고, 보조운동은 주로 단일근육 또는 소근육을 동원하는 운동을 의미합니다. " +
                                "균형적이고 합리적인 트레이닝 프로그램은 최소한 2항목의 상체 핵심운동과 1항목의 허리 및 다리 핵심운동이 포함되어야 합니다. " +
                                "근력운동 동작을 선택할 때는 동작에 어떤 주요근육과 보조근육이 사용되는지 그리고 인체의 어떤 부위가 어떻게 작용하는지 염두에 두고 결정해야 합니다.\n\n" +
                                "   회원님은 '통통체형'으로 비교적으로 무거운 중량을 들어올릴 수 있습니다. 하지만 신진대사가 느리기 떄문에 지방연소는 어렵기 떄문에 근육이 잘 들어나지 않습니다. " +
                                "'통통체형'은 유산소 운동을 많이 해서 체지방 연소를 극대화시키는데 중점을 둬야 합니다.저항운동과 지구력 운동을 같이 할 경우 지방연소와 근육형성을 최적화 시킬 수 있습니다. " +
                                "지구력 운동 시 더 많은 칼로리를 소모하기 떄문에 가장 먼저 실시하시기를 권합니다.");
                    } else {    //      "뚱뚱
                        textView.setText("   근력운동은 유산소 운동과 함께 병행해서 실시하면 혈당 개선 효과가 매우 뛰어납니다. 근력운동을 통해 근육의 양이 늘어나면, 근육이 사용하는 포도당의 양도 증가하기 때문에 혈당 수치를 조절하는데도 도움이 됩니다. 또한 근육이 늘어나면 기초대사량도 늘어나, 같은 활동에도 더 많은 열량이 소모되어 비만을 예방하거나 개선하는데도 효과적입니다. " +
                                "근력운동의 운동 항목은 크게 핵심운동과 보조운동으로 구분할 수 있는데, 핵심운동은 대근육 집단을 동원하는 운동이고, 보조운동은 주로 단일근육 또는 소근육을 동원하는 운동을 의미합니다. " +
                                "균형적이고 합리적인 트레이닝 프로그램은 최소한 2항목의 상체 핵심운동과 1항목의 허리 및 다리 핵심운동이 포함되어야 합니다. " +
                                "근력운동 동작을 선택할 때는 동작에 어떤 주요근육과 보조근육이 사용되는지 그리고 인체의 어떤 부위가 어떻게 작용하는지 염두에 두고 결정해야 합니다.\n\n" +
                                "   회원님은 '뚱뚱체형'으로 적절한 운동과 식단조절이 필요합니다. 이 중 더 중요한 것은 식단조절입니다. 뚱뚱한 체형의 경우 운동을 하면 정상 체형인 경우보다 운동의 재미를 잘 느끼지 못 합니다. 체중조절을 위해 가장 훌륭한 운동의 조건은 '종류'보다 '꾸준함'입니다. 운동을 꾸준히 할 수 있게 해주는 가장 큰 원동력은 바로 운동의 재미입니다. " +
                                "이 재미를 느끼기 위해서는 우선적으로 체중감량을 하셔야합니다. " +
                                "체중감량을 하시기 위해서는 3가지를 조절하셔야하는데, 저 탄수화물 식단,과하지 않은 저녁식사,알맞은 양의 식사입니다. " +
                                "탄수화물은 신체가 전부 사용하지 않았을 경우 지방으로 변해서 저장되는 에너지원이기 때문입니다..식단은 하얀 밀가루와 이것으로 만든 음식 (빵, 파스타, 쿠키 등)을 완전히 끊는 것입니다. 저녁식단 조절은 밤 동안에는 신체가 더 많은 독소를 배출하는 경향이 있기 때문입니다. " +
                                "과식의 충동을 피할 수 있는 한가지 방법은 배부른 느낌을 줄 수 있는 소화를 시키는 차를 마시는 것이 있습니다.");
                    }
                } else {        // "탄탄한몸매"
                    if(weightType.equals("마름")) {
                        textView.setText("   탄탄한 몸매를 만들기 위해서는 고난도의 12가지 코어 스트레칭을 통해 혈액순환을 촉진시키고 " +
                                "신진대사를 향상 시킴으로써 근육량 증가와 체중감량 효과적으로 수행할 수 있도록 하는게 중요합니다. " +
                                "그 후에 자신이 원하는 부위(복부, 힙, 다리 등등)를 좀더 집중적으로 트레이닝하여 더 탄탄한 몸매를 가꾸는것을 목표로 삼아야합니다.\n\n" +
                                "   회원님 현재 '마른체형'으로 먼저 고칼로리 저염식 식단으로 체중 증량이 필요하며, " +
                                "몸안의 신진대사를 활발히 하는 코어 스트레칭으로 육체의 부피를 지금보다 키울 필요가 있습니다. " +
                                "그리고 회원님이 원하는 탄탄한 몸매를 가꾸기 위한 웨이트 트레이닝이 진행될 것이며 " +
                                "이 모든 것을 도와줄 임성민 트레이너의 PT를 신청하는 것이 좋습니다.");
                    } else if(weightType.equals("보통")) {
                        textView.setText("   탄탄한 몸매를 만들기 위해서는 고난도의 12가지 코어 스트레칭을 통해 혈액순환을 촉진시키고 " +
                                "신진대사를 향상 시킴으로써 근육량 증가와 체중감량 효과적으로 수행할 수 있도록 하는게 중요합니다. " +
                                "그 후에 자신이 원하는 부위(복부, 힙, 다리 등등)를 좀더 집중적으로 트레이닝하여 더 탄탄한 몸매를 가꾸는것을 목표로 삼아야합니다.\n\n" +
                                "   회원님은 현재 '보통체형'으로 별다른 식단조절 필요없이 본격 웨이트 트레이닝 전에 부상을 방지하기 위한 " +
                                "12가지 코어 스트레칭을 통해 체내의 중심을 단련하고, 신진대사를 활발히 하여 근육량을 효과적으로 증가시킬 수 있도록 합니다. " +
                                "그리고 회원님이 원하는 탄탄한 몸매를 가꾸기 위한 웨이트 트레이닝이 진행될 것이며 " +
                                "이 모든 것을 도와줄 임성민 트레이너의 PT를 신청하는 것이 좋습니다.");
                    } else if(weightType.equals("통통")) {
                        textView.setText("   탄탄한 몸매를 만들기 위해서는 고난도의 12가지 코어 스트레칭을 통해 혈액순환을 촉진시키고 " +
                                "신진대사를 향상 시킴으로써 근육량 증가와 체중감량 효과적으로 수행할 수 있도록 하는게 중요합니다. " +
                                "그 후에 자신이 원하는 부위(복부, 힙, 다리 등등)를 좀더 집중적으로 트레이닝하여 더 탄탄한 몸매를 가꾸는것을 목표로 삼아야합니다.\n\n" +
                                "   회원님은 현재 '통통체형'으로 현재 식단의 칼로리를 조절하고 코어스트레칭으로 신진대사를 촉진시키며, " +
                                "유산소운동을 통해 약간의 체중감량이 필요합니다. 또한 끼니외에 간식은 단백질로 대체할 필요가 있습니다. " +
                                "그리고 회원님이 원하는 탄탄한 몸매를 가꾸기 위한 웨이트 트레이닝이 진행될 것이며 " +
                                "이 모든 것을 도와줄 임성민, 김종범 트레이너의 PT를 신청하는 것이 좋습니다.");
                    } else {    //      "뚱뚱
                        textView.setText("   탄탄한 몸매를 만들기 위해서는 고난도의 12가지 코어 스트레칭을 통해 혈액순환을 촉진시키고 " +
                                "신진대사를 향상 시킴으로써 근육량 증가와 체중감량 효과적으로 수행할 수 있도록 하는게 중요합니다. " +
                                "그 후에 자신이 원하는 부위(복부, 힙, 다리 등등)를 좀더 집중적으로 트레이닝하여 더 탄탄한 몸매를 가꾸는것을 목표로 삼아야합니다.\n\n" +
                                "   회원님은 현재 '뚱뚱체형'으로 저칼로리 저염식 식단으로 체중 감량이 시급하며 " +
                                "초기 유산소 트레이닝 위주로 체내 지방량을 단기간에 감소시키고, 코어 스트레칭을 통한 체내 중심 단련이 필요합니다. " +
                                "그리고 회원님이 원하는 탄탄한 몸매를 가꾸기 위한 웨이트 트레이닝이 진행될 것이며 " +
                                "이 모든 것을 도와줄 임성민, 김종범 트레이너의 PT를 신청하는 것이 좋습니다.");
                    }
                } // 목적 끝
            } // 온클릭 실행부분 구현 끝

        });
    }


}