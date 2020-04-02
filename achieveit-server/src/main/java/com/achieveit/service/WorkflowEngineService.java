package com.achieveit.service;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Comparator;
import java.util.Queue;

public class WorkflowEngineService {
    BitSet bitSets;
    static boolean edge[][];// temporal static 为了少配置几次
    static int v_num;
    static boolean is_initiated = false;

    WorkflowEngineService(){
        bitSets=new BitSet(WorkflowEngineService.v_num);

    }
    public static void setDependency(int from, int to, int v_num){
        if(!is_initiated){
            WorkflowEngineService.v_num=v_num;
            WorkflowEngineService.edge = new boolean[v_num][v_num];
            WorkflowEngineService.is_initiated = true;
        }
        if(from<=to) return;
        if(from<0||from>=v_num) return;
        if(to<0||to>=v_num) return;
        edge[from][to]=true;
    }

    public void updateBits(int in){
        bitSets.clear();
        for (int i = 0; i < v_num; i++) {
            bitSets.set(i,  ((in>>i)%2)==1);
        }
    }

    public boolean isTodo(int i, int in){
        updateBits(in);
        ArrayList<Integer> q = new ArrayList<Integer>();
        if(i>=v_num){//i不在范围内
            return false;
        }
        if(bitSets.get(i)==true){//i本身需要没有完成
            return false;
        }
        //i的依赖已经递归完成
        for (int j = v_num-1; j >= 0; j--) {
            if(edge[i][j]){
                q.add(j);
            }
        }
        while(!q.isEmpty()){// BFS搜索判断
            int head = q.remove(0);
            if(bitSets.get(head)!=true) return false;
            for (int j = 0; j < v_num; j++) {
                if(edge[head][j] && !q.contains(j)){
                    q.add(j);
                }
                q.sort( new Comparator<Integer>() {
                    @Override
                    public int compare(Integer t0, Integer t1) {
                        return t1-t0;
                    }
                });
            }
        }
        return true;
    }

    public int checkTodo(int i, int in){
        if(!isTodo(i,in)) return in;
        bitSets.set(i,true);
        return bitSet2Int();
    }

    public int uncheckTodo(int i, int in){
        updateBits(in);
        if(i>=v_num){//i不在范围内
            return in;
        }
        bitSets.set(i,false);
        return bitSet2Int();
    }

    public int bitSet2Int(){
        int res=0;
        for (int i = 0; i < v_num; i++) {
            res+=(bitSets.get(i)?1:0)*(1<<i);
        }
        return res;
    }
}
