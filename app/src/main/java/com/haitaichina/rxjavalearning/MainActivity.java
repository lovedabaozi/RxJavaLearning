package com.haitaichina.rxjavalearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.orhanobut.logger.Logger;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity  implements  View.OnClickListener{
    /**
     * RxJAVA  学习使用
     *      Logger 在 RXJava 中 有时候不能打出 logger   不知道咋回事
     * @param savedInstanceState
     */
    Button bt_base0;
    Button bt_base;
    Button bt_base2;
    TextView mInfo;
    StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findById();
    }

    private void findById() {
        sb = new StringBuilder();
        mInfo = (TextView) findViewById(R.id.info);
        bt_base0 = (Button) findViewById(R.id.bt_base0);
        bt_base = (Button) findViewById(R.id.bt_base);
        bt_base.setOnClickListener(this);
        bt_base2 = (Button) findViewById(R.id.bt_base2);
        bt_base2.setOnClickListener(this);
        bt_base0.setOnClickListener(this);
    }

    /**
     * Just  发射指定的Observable  如果是Just（0,1,2）会发射 3次。 如果是just（new Integer [] {1 ,2,3}）
     * 只会发射一次 。
     *      Just（x，y）  x 和 y  可以是不同类型数据
     *      一下示例是  一个 Student 对象 和  一个String 类型数据
     *
     */
    public  void  Just(){
        sb.setLength(0);
        Observer <Object> subscriber=new Observer<Object>() {
            @Override
            public void onCompleted() {
                sb.append("onCompleted()");
                sb.append("\n");
                mInfo.setText(sb.toString());

            }
            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object s) {
               if(s instanceof  Student){

                   sb.append("student.name=="+((Student) s).getName());
                   sb.append("\n");
                }else {

                   sb.append("s===="+s);
                   sb.append("\n");
                }

            }
        };

        Student Student=new Student();
        Student.name="zhangsan";
        Observable Observable= rx.Observable.just(Student,"123");
        Observable.subscribe(subscriber);

    }

    /**
     * From  使用    也可以创建 Observable
     */
    public  void  From(){
        sb.setLength(0);
        Integer []  data={1,2,3};
        Observable.from(data).subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                sb.append("one--"+"onCompleted"+"\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                sb.append("one--"+integer+"\n");
            }
        });
////////////////////////////////////////////////////////////////////////////////////
        Observable.from(data).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                sb.append("two--"+"onCompleted"+"\n");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                sb.append("two--"+integer+"\n");
            }
        });
    /////////////////////////////////////////////////////////////////////////////////////////////
        Observable.from(data).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                sb.append("three"+integer);
                sb.append("\n");
                 mInfo.setText(sb);
            }
        });
    }
    /**
     * RX 最基本用法：
     *      Observable  ：被观察者
     *      Observer    ：观察者，观察者响应触发后将要发生的事件
     */
    public  void  Creat(){
        sb.setLength(0);
        rx.Observable Observable = rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onNext("4");
                subscriber.onCompleted();
            }
        });

        Observer<String > observer=new Observer<String>() {
            @Override
            public void onCompleted() {

                sb.append("onCompleted");
                sb.append("\n");
                mInfo.setText(sb.toString());
                Log.e("111","--------------------");
            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String s) {
                sb.append(s+"\n");
                Log.e("111","---------2-----------");
            }
        };
        Observable.subscribe(observer);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_base0:
                Creat();
                break;
            case R.id.bt_base:
                    Just();
                break;
            case  R.id.bt_base2:
                From();
                break;
        }
    }

    class  Student {
         String name;
        public String getName() {
            return name;
        }
    }

}
