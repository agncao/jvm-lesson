package com.caojm.lessons.algorithm.support;

public class ListNode {
    public int val;
    public ListNode() {}
    public ListNode next;
    public ListNode(int x) { val = x; }
    public ListNode(int x,ListNode next) { val = x;this.next=next; }
}
