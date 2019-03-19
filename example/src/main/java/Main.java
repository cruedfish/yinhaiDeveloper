import java.math.BigInteger;
import java.util.List;

public class Main {
    public static void main(String[]args){
//        BigInteger bigInteger = new BigInteger("0123456789231423421342132");
//        System.out.println(bigInteger.toString(32));
//        int [][] a = new int[][]{{1,2,3},{4,5,6},{7,8,9}};
//        System.out.println(a[0][2]);
//        rotate(a);
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(2);
        ListNode n3 = new ListNode(3);
        ListNode n4 = new ListNode(4);
        ListNode n5 = new ListNode(5);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;

        rotateRight(n1,10);

    }

    public static   ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null || k==0 ){
            return head;
        }
        ListNode fast = head;
        ListNode slow = head;
        int h = 0;
        for (int i = 0; i < k; i++) {
            if (fast != null) {
                fast = fast.next;
                h++;
            } else {
                i = -1;
                k = k%h;
                fast = head;
            }

        }
        if(fast == null || k == 0){
            return head;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        ListNode flag = slow.next;
        slow.next = null;
        fast.next = head;
        return flag;
    }

        public static void rotate(int[][] matrix) {
        if(matrix == null || matrix.length == 0){
            return ;
        }
        int rowN = matrix.length-1;
        int colN = matrix[0].length-1;

        int row = rowN;

        int col = colN;

        for(int i =0 ;i < rowN;i++){
            for(int j = 0 ; j < colN;j++){
                int x1 = i ;
                int y1 = j;
                int x2 = j;
                int y2 = col - i;
                int x3 = row - i;
                int y3 = col - j;
                int x4 = row - j;
                int y4 = i;
                swap(matrix,x1,y1,x2,y2);
                swap(matrix,x1,y1,x3,y3);
                swap(matrix,x1,y1,x4,y4);
            }
            rowN -- ;
            colN--;
        }
    }
    public static void swap(int [][] matrix,int x1 ,int y1,int x2 , int y2){
        int temp = matrix[x1][y1];
        matrix[x1][y1] = matrix[x2][y2];
        matrix[x2][y2] = temp;
    }
}
