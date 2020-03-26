package com.study.staticPlan;

import com.study.graph.Graph2;
import lombok.Data;

/**
 * @author ldb
 * @date 2019-12-03 14:18
 */
public class Triangle {

    public static void main(String[] args) {

        int[][] matrix = {{5}, {7, 8}, {2, 3, 4}, {4, 9, 6, 1}, {2, 7, 9, 4, 5}};
        Triangle triangle = new Triangle();
        triangle.triangle(0, 0, 0, matrix, 5);
        System.out.println(minValue);

        System.out.println(triangle.minDistDpTriangle(matrix, 5));
    }

    //1. 回溯法
    private static int minValue = Integer.MAX_VALUE;

    /*调用 triangle(0,0,0,matrix,5)*/
    public void triangle(int i, int j, int dist, int[][] matrix, int n) {
        dist += matrix[i][j];
        if (i == n - 1) {
            if (minValue > dist) {
                minValue = dist;
            }
            return;
        }
        triangle(i + 1, j, dist, matrix, n);
        triangle(i + 1, j + 1, dist, matrix, n);

    }

    //2. 动态规划：
    public int minDistDpTriangle(int[][] matrix, int n) {

        int[][] states = new int[n][];
        for (int i = 0; i < n; i++) {
            states[i] = new int[i + 1];
        }
        // 初始化第一列
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += matrix[i][0];
            states[i][0] = sum;
        }

        print(states);

        sum = 0;
        for (int i = 0, j = 0; i < n; i++, j++) {
            sum += matrix[i][j];
            states[i][j] = sum;
        }

        print(states);


        for (int i = 2; i < n; i++) {
            for (int j = 1; j < states[i].length - 1; j++) {
                states[i][j] = matrix[i][j] + Math.min(states[i - 1][j], states[i - 1][j - 1]);
            }
        }
        int minValue = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if (states[n - 1][j] < minValue) {
                minValue = states[n - 1][j];
            }
        }
        return minValue;
    }

    public void print(int[][] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }
}
