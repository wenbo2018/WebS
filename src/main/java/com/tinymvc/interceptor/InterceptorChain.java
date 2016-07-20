package com.tinymvc.interceptor;

import com.tinymvc.web.mapping.Handler;

/**
 * Created by shenwenbo on 16/7/3.
 */
public class InterceptorChain {

    int index=0;
    private Interceptor[] interceptors;

    /**
     * ִ执行拦截器
     * @return
     * @throws Exception
     */
    public	void exeInterceptor()throws Exception{
        boolean flag=true;
        //依次执行，遇到false跳出，否则放开执行下一个拦截器
        for (int i = 0; i < interceptors.length; i++) {
            index=i;
            if(!interceptors[i].doInterceptor()){
                flag=false;
                break;
            }
        }
    }
    /**
     * 执行方法后
     */
    public void exeAfterInterceptor(){
        if(interceptors.length!=0){//有拦截器才执行，不然会抛数组越界
            for (int i = index; i>= 0; i--) {
                interceptors[i].afterInterceptor();
            }
        }
    }
}
