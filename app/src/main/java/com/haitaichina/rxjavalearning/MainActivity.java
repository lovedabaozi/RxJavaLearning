package com.haitaichina.rxjavalearning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity  implements  View.OnClickListener{
    /**
     * RxJAVA  学习使用
     *
     * @param savedInstanceState
     */
    Button bt_base;
    Button bt_base2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * RX 最基本用法：
         *      Observable  ：被观察者
         *      Observer    ：观察者，观察者响应触发后将要发生的事件
         */
        rx.Observable Observable = rx.Observable.create(new rx.Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onStart();
                subscriber.onNext("1");
                subscriber.onNext("2");
                subscriber.onNext("3");
                subscriber.onNext("4");
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        Observer<String > observer=new Observer<String>() {
            @Override
            public void onCompleted() {

            }
            @Override
            public void onError(Throwable e) {

            }
            @Override
            public void onNext(String s) {
                Logger.e("----",s);

            }
        };
        Observable.subscribe(observer);
        findById();
    }

    private void findById() {


        bt_base = (Button) findViewById(R.id.bt_base);
        bt_base.setOnClickListener(this);
        bt_base2 = (Button) findViewById(R.id.bt_base2);
        bt_base2.setOnClickListener(this);
    }


    public  void  Just(){
        /**
         * Just  发射指定的Observable  如果是Just（0,1,2）会发射 3次。 如果是just（new Integer [] {1 ,2,3}）
         * 只会发射一次 。
         *      Just（x，y）  x 和 y  可以是不同类型数据
         *      一下示例是  一个 Student 对象 和  一个String 类型数据
         *
         */

        Observer <Object> subscriber=new Observer<Object>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object s) {
               if(s instanceof  Student){
                    Log.e("student.name==",((Student) s).getName());
                }else {
                    Log.e("s==============",s.toString());
                }

            }
        };

        Student Student=new Student();
        Student.name="zhangsan";

        Observable Observable= rx.Observable.just(Student,"123");
        Observable.subscribe(subscriber);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_base:
                    Just();
                break;
            case  R.id.bt_base2:
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
