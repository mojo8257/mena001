package com.example;

import java.util.*;

/**
 * 页面置换算法模拟：FIFO / LRU
 * 虚页数：10（0~9），访问序列长度：20
 */
public class App {

    private static final int VIRTUAL_PAGE_COUNT = 10;
    private static final int STREAM_LEN = 20;

    static class VirtualPageEntry {
        int virtualPage;     // 虚页号
        int realPage;        // 所在实页号（-1表示不在内存）
        int state;           // 0不在内存，1在内存
        int enterTime;       // 进入内存时间
        int accessTime;      // 最近访问时间

        VirtualPageEntry(int vp) {
            this.virtualPage = vp;
            this.realPage = -1;
            this.state = 0;
            this.enterTime = 0;
            this.accessTime = 0;
        }
    }

    static class RealPageEntry {
        int realPage;     // 实页号
        int virtualPage;  // 当前装载的虚页号（-1表示空）
        int state;        // 0空闲，1已分配

        RealPageEntry(int rp) {
            this.realPage = rp;
            this.virtualPage = -1;
            this.state = 0;
        }
    }

    enum Algo { FIFO, LRU }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("input the number of real page:");
        int n = readPositiveInt(sc);

        System.out.print("\n\ninput the algorithm of replacement(FIFO/LRU):");
        Algo algo = readAlgo(sc);

        // 初始化页表（10个虚页）
        VirtualPageEntry[] pageTable = new VirtualPageEntry[VIRTUAL_PAGE_COUNT];
        for (int i = 0; i < VIRTUAL_PAGE_COUNT; i++) pageTable[i] = new VirtualPageEntry(i);

        // 初始化实页表（n个实页）
        RealPageEntry[] realPages = new RealPageEntry[n];
        for (int i = 0; i < n; i++) realPages[i] = new RealPageEntry(i);

        System.out.println("\n初始化后的虚页页表：");
        printVirtualTable(pageTable);
        System.out.println("\n初始化后的实页使用情况表：");
        printRealTable(realPages);

        // 生成虚页访问序列：长度20，虚页号0~9
        int[] stream = generateRandomStream(STREAM_LEN, VIRTUAL_PAGE_COUNT);
        System.out.println("\n产生的虚页访问地址流：");
        System.out.println(Arrays.toString(stream));

        int missCount = 0;
        List<Integer> hitIndex = new ArrayList<>();

        for (int t = 1; t <= stream.length; t++) {
            int vp = stream[t - 1];
            System.out.println("\n******第" + t + "次访问开始：******");
            System.out.println("\nAccess virtual page " + vp);
            System.out.println("\nreal page table:");
            printRealTable(realPages);
            System.out.println("\nThese virtual pages are in memory: " + Arrays.toString(currentInMemory(realPages)));

            // 命中
            if (pageTable[vp].state == 1) {
                System.out.println("\nvirtual page " + vp + " is in Memory!");
                pageTable[vp].accessTime = t; // LRU需要更新最近访问时间
                hitIndex.add(t);
                continue;
            }

            // 缺页
            System.out.println("\nvirtual page " + vp + " is not in Memory!");
            missCount++;

            // 先找空实页
            int emptyRp = findEmptyRealPage(realPages);
            if (emptyRp != -1) {
                System.out.println("\nreal page " + emptyRp + " is empty!");
                loadPage(pageTable, realPages, vp, emptyRp, t);
                System.out.println("\nvirtual page " + vp + " is put into real page " + emptyRp + " ");
            } else {
                System.out.println("\nno empty real pages!");
                // 输出当前在内存中的虚页信息
                System.out.println("\nTheir info in virtual talbe is:\n");
                for (int inVp : currentInMemory(realPages)) {
                    VirtualPageEntry e = pageTable[inVp];
                    System.out.println(toMapStyle(e));
                }

                int victimVp = (algo == Algo.FIFO) ? selectVictimFIFO(pageTable, realPages)
                                                   : selectVictimLRU(pageTable, realPages);

                System.out.println("\nvirtual page " + victimVp + " will be replaced!");
                int victimRp = pageTable[victimVp].realPage;

                // 置换：victimVp换出，vp换入
                evictPage(pageTable, realPages, victimVp);
                System.out.println("\nreal page " + victimRp + " will be filled with new virtual page!");
                loadPage(pageTable, realPages, vp, victimRp, t);
                System.out.println("\nvirtual page " + vp + " is put into real page " + victimRp + " ");
            }
        }

        double missRate = (missCount * 1.0 / stream.length) * 100.0;
        System.out.println("\nhit situation is: " + hitIndex);
        System.out.printf("\nmissing rate is:%.2f%%\n", missRate);
    }

    private static int readPositiveInt(Scanner sc) {
        while (true) {
            try {
                int x = Integer.parseInt(sc.nextLine().trim());
                if (x > 0) return x;
            } catch (Exception ignored) {}
            System.out.print("please input a positive integer:");
        }
    }

    private static Algo readAlgo(Scanner sc) {
        while (true) {
            String s = sc.nextLine().trim().toUpperCase(Locale.ROOT);
            if ("FIFO".equals(s)) return Algo.FIFO;
            if ("LRU".equals(s)) return Algo.LRU;
            System.out.print("please input FIFO or LRU:");
        }
    }

    private static int[] generateRandomStream(int len, int vpCount) {
        Random r = new Random(); // 如需固定序列，可改为 new Random(固定种子)
        int[] a = new int[len];
        for (int i = 0; i < len; i++) a[i] = r.nextInt(vpCount);
        return a;
    }

    private static int findEmptyRealPage(RealPageEntry[] realPages) {
        for (RealPageEntry e : realPages) {
            if (e.state == 0) return e.realPage;
        }
        return -1;
    }

    private static void loadPage(VirtualPageEntry[] pt, RealPageEntry[] rp, int vp, int realPage, int time) {
        rp[realPage].virtualPage = vp;
        rp[realPage].state = 1;

        pt[vp].realPage = realPage;
        pt[vp].state = 1;
        pt[vp].enterTime = time;
        pt[vp].accessTime = time;
    }

    private static void evictPage(VirtualPageEntry[] pt, RealPageEntry[] rp, int victimVp) {
        int realPage = pt[victimVp].realPage;

        rp[realPage].virtualPage = -1;
        rp[realPage].state = 0;

        pt[victimVp].realPage = -1;
        pt[victimVp].state = 0;
        pt[victimVp].enterTime = 0;
        pt[victimVp].accessTime = 0;
    }

    private static int selectVictimFIFO(VirtualPageEntry[] pt, RealPageEntry[] rp) {
        int victimVp = -1;
        int minEnter = Integer.MAX_VALUE;
        for (RealPageEntry e : rp) {
            if (e.state == 1) {
                int vp = e.virtualPage;
                if (pt[vp].enterTime < minEnter) {
                    minEnter = pt[vp].enterTime;
                    victimVp = vp;
                }
            }
        }
        return victimVp;
    }

    private static int selectVictimLRU(VirtualPageEntry[] pt, RealPageEntry[] rp) {
        int victimVp = -1;
        int minAccess = Integer.MAX_VALUE;
        for (RealPageEntry e : rp) {
            if (e.state == 1) {
                int vp = e.virtualPage;
                if (pt[vp].accessTime < minAccess) {
                    minAccess = pt[vp].accessTime;
                    victimVp = vp;
                }
            }
        }
        return victimVp;
    }

    private static int[] currentInMemory(RealPageEntry[] rp) {
        int[] arr = new int[rp.length];
        for (int i = 0; i < rp.length; i++) arr[i] = rp[i].virtualPage;
        return arr;
    }

    private static void printVirtualTable(VirtualPageEntry[] pt) {
        List<String> list = new ArrayList<>();
        for (VirtualPageEntry e : pt) list.add(toMapStyle(e));
        System.out.println(list);
    }

    private static String toMapStyle(VirtualPageEntry e) {
        return String.format("{'virtualPage': %d, 'realPage': %d, 'state': %d, 'enterTime': %d, 'accessTime': %d}",
                e.virtualPage, e.realPage, e.state, e.enterTime, e.accessTime);
    }

    private static void printRealTable(RealPageEntry[] rp) {
        List<String> list = new ArrayList<>();
        for (RealPageEntry e : rp) {
            list.add(String.format("{'realPage': %d, 'virtualPage': %d, 'state': %d}",
                    e.realPage, e.virtualPage, e.state));
        }
        System.out.println(list);
    }
}