package com.haitaichina.rxjavalearning;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
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
    Button bt_base3;
    Button bt_base4;
    Button bt_base5;
    Button bt_base6;
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
        bt_base3 = (Button) findViewById(R.id.bt_base3);
        bt_base3.setOnClickListener(this);
        bt_base4 = (Button) findViewById(R.id.bt_base4);
        bt_base4.setOnClickListener(this);
        bt_base5 = (Button) findViewById(R.id.bt_base5);
        bt_base5.setOnClickListener(this);
        bt_base6 = (Button) findViewById(R.id.bt_base6);
        bt_base6.setOnClickListener(this);
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
        ///////////////////////////////////////////////////////////////////////////////////////////////
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
                Log.e("111","--------1------------");
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
            case R.id.bt_base3:
                Action();
                break;
            case  R.id.bt_base4:
                Map();
                break;
            case R.id.bt_base5:
                FlatMap();
                break;
            case  R.id.bt_base6:
                Scheduler();
                break;
        }
    }


    class  Student {
         String name;
        public String getName() {
            return name;
        }
    }

    /**
     * Action 是RxJava 的一个接口，常用的有Action0和Action1。
     * Action :它只有一个方法 call()，这个方法是无参无返回值的；
     * 由于 onCompleted() 方法也是无参无返回值的，因此 Action0 可以被当成一个包装对象，
     * 将 onCompleted() 的内容打包起来将自己作为一个参数传入 subscribe() 以实现不完整定义的回调。
        Action1 :它同样只有一个方法 call(T param)，这个方法也无返回值，但有一个参数；
        与 Action0 同理，由于 onNext(T obj) 和 onError(Throwable error) 也是单参数无返回值的，
     因此 Action1 可以将 onNext(obj)和 onError(error) 打包起来传入 subscribe() 以实现不完整定义的回调
     */


    /**
     * Action   定义三个对象    onNext(obj)   onError(error)   onCompleted()
     */
    private void Action() {
        Observable Observable= rx.Observable.just("hello","world","!");
        Action1<String> onNextAction=new Action1<String>() {
            @Override
            public void call(String s) {
                sb.append(s+"\n");
                mInfo.setText(sb);
            }
        };

        Action0 onCompletedAction =new Action0() {
            @Override
            public void call() {
                sb.append("onCompletedAction\n");
            }
        };

        Action1<Throwable> onErrorAction =new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        };
        /**
         * 使用 onNextAction，onErrorAction，onCompletedAction
         * 分别来定义onNext() , onError()  , onCompleted().
         */
        Observable.subscribe(onNextAction);
        Observable.subscribe(onNextAction,onErrorAction);
        Observable.subscribe(onNextAction,onErrorAction,onCompletedAction);

    }

    /**
     *  使用map 用到Func1 接口   和 Action1 相似。
     *  Func1 包装的是有返回值的方法。
     *  filter: 过滤器
     */
    private void Map() {
        Action0 onCompletedAction =new Action0() {
            @Override
            public void call() {
                sb.append("onCompletedAction\n");
            }
        };
        Observable.just("1","2","3").map(new Func1<String, Integer>() {

            @Override
            public Integer call(String s) {
                return Integer.parseInt(s);
            }


        }).filter(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer >2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.e("map=======", integer + "------");
                sb.append(integer + "\n");

            }
        });
    }

    /**
     *
     */
    private void FlatMap() {
    }

    /**
     *  Scheduler  线程控制器
     */
    private void Scheduler() {
        Observable.just("1","2").subscribeOn(Schedulers.newThread())// 指定subscribeOn（）发生的线程
                .observeOn(AndroidSchedulers.mainThread())//指定observeOn（）回调的发生的线程
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("Scheduler====",s+"------------");
                    }
                });

        Observable.just("1","2").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s;
                    }
                }).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread());
    }

}
