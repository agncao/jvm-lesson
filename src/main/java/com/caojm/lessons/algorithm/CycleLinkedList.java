package com.caojm.lessons.algorithm;

import com.caojm.lessons.algorithm.support.ListNode;

/**
 * 环形链表
 */
public class CycleLinkedList {
    public static void main(String[] args) {
        ListNode head=new ListNode(1,new ListNode(2,new ListNode(3,new ListNode(4,new ListNode(5)))));
        ListNode current=head;
        while (current.next!=null){
            current=current.next;
        }
        current.next=head.next.next;
        System.out.println(hasCycle(head));
    }

    public static boolean hasCycle(ListNode head) {
        ListNode slow=head,fast=head.next;
        while (slow!=fast){
            if(fast==null || fast.next==null) return false;
            slow=slow.next;
            fast=fast.next.next;
        }
        return true;
    }
}
