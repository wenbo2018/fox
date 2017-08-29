package com.github.wenbo2018.fox.admin.constants;

/**
 * Created by wenbo.shen on 2017/5/27.
 */
public enum TokenState {
    /**
     * 过期
     */
    EXPIRED("EXPIRED"),
    /**
     * 无效(token不合法)
     */
    INVALID("INVALID"),
    /**
     * 有效的
     */
    VALID("VALID");

    private String  state;

    private TokenState(String state) {
        this.state = state;
    }

    public static TokenState getTokenState(String tokenState){
        TokenState[] states=TokenState.values();
        TokenState ts=null;
        for (TokenState state : states) {
            if(state.toString().equals(tokenState)){
                ts=state;
                break;
            }
        }
        return ts;
    }

    public String toString() {
        return this.state;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
