package sesac.spring_boot_default.dto_vo;

import sesac.spring_boot_default.dto_vo.vo.Point;

public class PointExample {
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("두 점사이의 거리 = " + p1.distanceTo(p2));

        Point p3 = new Point(3, 4);
        System.out.println("p1(0,0)와 p3(3,4)는 같은 객체인가? " + p1.equals(p3)); // false
        System.out.println("p2(3,4)와 p3(3,4)는 같은 객체인가? " + p2.equals(p3)); // true

        System.out.println(p1.hashCode()); //   961
        System.out.println(p2.hashCode()); //  1058
        System.out.println(p3.hashCode()); //  1058

    }
}
