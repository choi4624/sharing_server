package com.example.hellospring.repository;

import com.example.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
class sdasf{
    public static void main(String[] args) {
        MemberRepository aa = new MemoryMemberRepository();




    }
}
//
//class Optional2<ASD>{
//    Optional2(ASD temp) {
//        this.temp =temp;
//    }
//
//    ASD temp;
//
//    ASD get(){
//        return temp;
//    }
//}
//class Optional3{
//    Optional3(Object temp) {
//        this.temp =temp;
//    }
//
//    Object temp;
//
//    Object get(){
//        return temp;
//    }
//}
//
//class List2<T>{
//    Object[] temp = new Object[100];
//    void add(T aaa){
//        temp[0] = aaa;
//    }
//
//    T getFirst(){
//        T a = (T) temp[0];
//        return a;
//    }
//}
//
//
//class AAA{
//    public static void main(String[] args) {
//        Optional2<Integer> op = new Optional2<Integer>(1);
//        Optional2<String> op2 = new Optional2<String>("asdf");
//        Optional3 op3 = new Optional3(1);
//        op.get();
//        op2.get();
//        op3.get();
//
//        List2<Integer> ll = new List2<>();
//        List2<String> ll2 = new List2<>();
//
//        ll.add(21);
//        ll.getFirst();
//
//    }
//}
