package com.caojm.lessons.jvm.methodstack.sync;

/**
 * ${DESCRIPTION}
 *
 * @author <a href=mailto:caojianmin@jd.com>caojianmin1</a>
 * @create 2019-09-09 上午11:11
 **/
public class OrderBill {
    private static double BillsSum=22;

    private double currentBIllSum;

    private OrderBill lastOrderBill;

    public synchronized static void compute1(double sum){
        OrderBill.BillsSum=OrderBill.BillsSum+sum;
        System.out.println(OrderBill.BillsSum);
    }

    public void computer2(){
        synchronized (lastOrderBill){
            OrderBill.BillsSum=OrderBill.BillsSum+lastOrderBill.currentBIllSum;
            System.out.println("返回所有账单的总额");
        }
    }

    public synchronized void total(){
        computer2();
    }

    public static void main(String[] args) {
        OrderBill bill=new OrderBill();
        bill.lastOrderBill=new OrderBill(3d);
        bill.total();
//        OrderBill.compute1(3d);
    }

    public OrderBill(double sum){
        currentBIllSum=sum;
    }
    public OrderBill(){
    }
}
