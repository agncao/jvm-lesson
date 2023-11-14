package com.caojm.lessons.algorithm;

import com.caojm.lessons.algorithm.support.ListNode;

/**
 * 链表反转
 */
public class LinkedListReverse {
    public static void main(String[] args) {
        ListNode head=new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4,new ListNode(5)))));
        ListNode current=head;
        while (current!=null){
            System.out.print(current.val+" ");
            current=current.next;
        }
        System.out.println();
        current=recursionReverse(head);
//        current=reverse(head);
        while (current!=null){
            System.out.print(current.val+" ");
            current=current.next;
        }
    }

    private static ListNode reverse(ListNode head){
        ListNode current=head,prev=null,next=current.next;
        while (current!=null){
            ListNode temp=current;
            temp.next=prev;
            prev=current;
            current=next;
            if(null!=current) next=current.next;
        }
        return prev;
    }

    private static ListNode recursionReverse(ListNode head){
        if(head==null||head.next==null) return head;
        ListNode newHead= recursionReverse(head.next);
        head.next.next=head;
        head.next=null;

        return newHead;
    }

    //1 -> 2 -> 3 -> 4 -> 5


}
