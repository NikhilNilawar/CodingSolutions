package com.cp;

import com.sun.source.tree.Tree;

import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode() {
    }

    TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

//class TreeNodeLevel {
//    int val;
//    int level;
//    TreeNodeLevel left;
//    TreeNodeLevel right;
//    TreeNodeLevel() {}
//    TreeNodeLevel(int val, int level) { this.val = val; this.level = level;}
//    TreeNodeLevel(int val, int level, TreeNodeLevel left, TreeNodeLevel right) {
//        this.val = val;
//        this.level = level;
//        this.left = left;
//        this.right = right;
//    }
//    TreeNodeLevel(int level, TreeNode treeNode) {
//        this.level = level;
//        this.val = treeNode.val;
//        this.left = treeNode.left;
//    }
//}

public class Solution {

    private TreeNode node;
    public TreeNode invertTree(TreeNode root) {
        if(root==null) return root;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        TreeNode tempNode = new TreeNode();
        tempNode = left;
        left = right;
        right = tempNode;
        return root;
    }
    public String licenseKeyFormatting(String s, int k) {
        String string = s.replaceAll("-","").toUpperCase();
        int loc = string.length();
        StringBuilder str = new StringBuilder();
        while(loc>-1) {
            if(loc-k >= 0) {
                str.append(new StringBuilder(string.substring(0, loc+1)).reverse());
            } else {
                str.append(string, loc-k, loc+1);
            }
            str.append("-");
            loc -=k;
        }
        return removeTrailing(str.toString(), '-');
    }

    private String removeTrailing(String str, char c) {
        if(str.length() == 0 || str.charAt(str.length() - 1)!=c) return str;
        return removeTrailing(str.substring(0, str.length()-1), '-');
    }

    public int oddEvenJumps(int[] arr) {
        if(arr.length < 2) {
            return  0;
        }
        int result = 0;
        Map<Integer, Integer> nextSmallestLocation = new HashMap<>();
        Map<Integer, Integer> nextLargestLocation = new HashMap<>();
        nextSmallestLocation.put(arr.length - 1, -1);
        nextLargestLocation.put(arr.length - 1, -1);

        for(int i = arr.length - 2; i>=0; i--) {
            if(arr[i] <= arr[i+1]) {
                if (nextLargestLocation.get(i + 1) == -1) {
                    nextLargestLocation.put(i, i + 1);
                } else {
                    nextLargestLocation.put(i,
                            arr[nextLargestLocation.get(i + 1)] <= arr[i + 1] ? nextLargestLocation.get(i + 1) : i + 1);
                }
                if(nextSmallestLocation.get(i+1) == -1) {
                    nextSmallestLocation.put(i, -1);
                } else {
                    nextSmallestLocation.put(i, arr[nextSmallestLocation.get(i+1)] <= arr[i] ? nextSmallestLocation.get(i+1) : -1);
                }
            } else {
                if (nextSmallestLocation.get(i + 1) == -1) {
                    nextSmallestLocation.put(i, i + 1);
                } else {
                    nextSmallestLocation.put(i,
                            arr[nextSmallestLocation.get(i + 1)] <= arr[i + 1] ? nextSmallestLocation.get(i + 1) : i + 1);
                }
                if(nextLargestLocation.get(i+1) == -1) {
                    nextLargestLocation.put(i, -1);
                } else {
                    nextLargestLocation.put(i, arr[nextLargestLocation.get(i + 1)] > arr[i] ? nextLargestLocation.get(i + 1) : -1);
                }
            }
        }

        for( int i=0; i<arr.length;i++) {
            int cur_location = i;
            int jump_num = 1;
            while(true) {
                if(jump_num%2 == 1) {
                    if(nextLargestLocation.get(cur_location) == -1) {
                        break;
                    } else {
                        cur_location = nextLargestLocation.get(cur_location);
                    }
                } else {
                    if(nextSmallestLocation.get(cur_location) == -1) {
                        break;
                    } else {
                        cur_location = nextSmallestLocation.get(cur_location);
                    }
                }
                jump_num++;
            }
            if(cur_location == arr.length-1) {
                result++;
            }
        }


        return result;
    }

    public int numUniqueEmails(String[] emails) {
        Map<String, Set<String>> addresses = new HashMap<>();
        int total_addresses = 0;

        for (String email : emails) {
            String name, domain;
            int atIndex = email.indexOf("@");
            //name = emails[i].split("@")[0];
            name = email.substring(0, atIndex);
            domain = email.substring(atIndex + 1);
            name = name.split("\\+")[0];
            name = name.replaceAll("\\.", "");
            if (addresses.containsKey(domain) && !addresses.get(domain).contains(name)) {
                addresses.get(domain).add(name);
                total_addresses++;
            } else if (!addresses.containsKey(domain)) {
                addresses.put(domain, new HashSet<>(Collections.singletonList(name)));
                total_addresses++;
            }
        }
        return total_addresses;
    }

    int[][] memoization;

    public int longestCommonSubString(String first, String second) {
        memoization = new int[first.length()+1][second.length()+1];

        for(int i = first.length() - 1; i>-1; i--) {
            for(int j = second.length() - 1; j>-1; j--){
                if(first.charAt(i) == second.charAt(j)) {
                    memoization[i][j] = memoization[i+1][j+1] + 1;
                }
                else {
                    memoization[i][j] = Math.max(memoization[i][j+1], memoization[i+1][j]);
                }
            }
        }
        return memoization[0][0];
    }

    public Object[] getUnsortedList(int[] list) {
        Stack<Integer> stack = new Stack<>();
        int sizeList = list.length;
        //True represents Increasing, False represents Decreasing
        Boolean order = null;
        for(int i=0;i<sizeList;i++) {
            if(stack.isEmpty()) {
                stack.push(list[i]);
                continue;
            }
            if(order != null && ((stack.peek() > list[i] && !order) || (stack.peek() < list[i] && order))) {
                Integer stackTop = stack.pop();
                stack.push(list[i]);
                stack.push(stackTop);
                order = !order;
                continue;
            }
            order = stack.peek() < list[i];
            stack.push(list[i]);
        }
        return stack.toArray();
    }

    private List<Integer> list = new LinkedList<>();

    int repetitionsRequired(String s, String pat) {
        int repetitions = 0;
        Map<Character, List<Integer>> positions = new HashMap<>();
        for(int i=0; i<s.length(); i++) {
            if(positions.containsKey(s.charAt(i))) {
                positions.get(s.charAt(i)).add(i);
            } else {
                positions.put(s.charAt(i), new ArrayList<>(List.of(i)));
            }
        }
        for(int i=0;i<pat.length();i++) {
            if(!positions.containsKey(pat.charAt(i))) return -1;
        }

        for(int i=0;i<pat.length();) {
            int lastPosition = -1;
            while(i<s.length()) {
                char c = pat.charAt(i);
                int charIndex = s.indexOf(c);
                if(charIndex == -1) break;
                if(lastPosition > -1 && positions.get(c).size() > positions.get(c).indexOf(lastPosition) + 1) {
                    break;
                }
                lastPosition = i++;
            }
            repetitions++;
        }
        return repetitions;
    }

    int largestIsland(Queue<String> queue, int[][] visited) {
        if(queue.isEmpty()) {
            return 0;
        }
        while(queue.isEmpty())
        {
            String ele = queue.poll();
            int index = ele.indexOf("-");
            Integer row = Integer.valueOf(ele.substring(0, index));
            Integer column = Integer.valueOf(ele.substring(index+1));

        }
        return 0;
    }

    public int largestIsland(int[][] grid) {
        return 1;
    }

    public boolean isMatch(String s, String p) {
        int s_index=0, p_index=0;

        while(s_index>=s.length()) {
            if(p.charAt(p_index) == '.') {
                s_index++;
                p_index++;
                continue;
            }
            if(p.charAt(p_index) == s.charAt(s_index)) {
                s_index++;
                p_index++;
                continue;
            }
        }

        return true;
    }

    public int maxArea(int[] height) {
        int start = 0;
        int end = height.length-1;
        int max_area = Integer.MIN_VALUE;
        while(start<end) {
            int cur_area = Math.min(height[start], height[end]) *(end-start);
            if(max_area < cur_area) {
                max_area = cur_area;
            }
            if(height[end] < height[start]) {
                end--;
                continue;
            }
            start++;
        }
        return max_area;
    }

    public int[] canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer,List<Integer>> mustDoBefore = new HashMap<>();
        int[] result = new int[numCourses];
        int counter = 0;
        for(int i=0;i<numCourses;i++) {
            mustDoBefore.put(i, new LinkedList<>());
        }
        int totalEdges = prerequisites.length;
        Queue<Integer> queue = new LinkedList<>();
        int[] dependencies = new int[numCourses];
        for(int i=0;i<totalEdges;i++) {
            mustDoBefore.get(prerequisites[i][1]).add(prerequisites[i][0]);
            dependencies[prerequisites[i][0]]++;
        }
        for(int i=0;i<dependencies.length;i++) {
            if(dependencies[i]==0) queue.add(i);
        }
        while(!queue.isEmpty()) {
            Integer vertex = queue.poll();
            result[counter++] = vertex;
            List<Integer> edges = mustDoBefore.get(vertex);
            for (int i=0; i<edges.size();i++){
                totalEdges--;
                if(--dependencies[edges.get(i)] == 0) queue.add(edges.get(i));
            }
        }
        return totalEdges==0 ? result: new int[0];
    }

    public boolean  inOrderTraversal(TreeNode t) {
        return recursion1(t);
    }

    public List<Integer> getInorderTraversal1(TreeNode t) {
        if(t==null) return new LinkedList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode root= t;
        TreeNode pre= null;
        int k=10;
        while(root!=null || stack.size()!=0) {
            while(root!=null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(--k==0) break;
            //if(pre!=null && pre.val>=root.val) return false;
            pre = root;
            root = root.right;
        }
        return list;
    }

    private boolean recursion1(TreeNode t) {
        if(t==null) return true;

        boolean leftEq=true, rightEq=true;
        if(t.left != null) {
            leftEq = t.left.val<t.val;
        }
        if(t.right != null) {
            rightEq = t.right.val>t.val;
        }
        recursion1(t.left);
        recursion1(t.right);
        return leftEq&&rightEq;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null) return list;
        queue.add(root);
        queue.add(null);
        int level = 0;
        //recursion(root, level);
        while (queue.size() > 0) {
            if (queue.peek() == null) {
                level++;
                queue.remove();
                if (queue.size() == 0) break;
                queue.add(null);
                continue;
            }
            TreeNode treeNode = queue.remove();
            if (list.size() <= level || list.get(level) == null) list.add(level, new LinkedList<>());
            list.get(level).add(treeNode.val);
            if (treeNode.left != null) queue.add(treeNode.left);
            if (treeNode.right != null) queue.add(treeNode.right);
        }
        return list;
    }

    public int numTrees(int n) {
        int[] G = new int[n + 1];
        G[0] = G[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                G[i] += G[j - 1] * G[i - j];
            }
        }
        return G[n];
    }

    private List<Integer> answer = new ArrayList<>();

    public List<Integer> getInorderTraversal(TreeNode treeNode) {
        if (treeNode == null) return answer;
        recursion(treeNode);
        return answer;
    }

    private void recursion(TreeNode treeNode) {
        if (treeNode == null) return;
        recursion(treeNode.left);
        answer.add(treeNode.val);
        recursion(treeNode.right);
    }

    public String getLongestPalindromicString(String s) {
        int maxLengthOdd = 0, maxLengthEven = 0;
        String currentMaxOdd = "", currentMaxEven = "";
        if (s.length() < 2) return s;
        for (int i = 0; i < s.length(); i++) {
            int start = i, end = i;
            while (start >= 0 && end < s.length()) {
                if (s.charAt(start) != s.charAt(end)) {
                    break;
                }
                if (maxLengthOdd <= end - start + 1) {
                    currentMaxOdd = s.substring(start, end + 1);
                    maxLengthOdd = end - start + 1;
                }
                start--;
                end++;
            }
        }
        return currentMaxOdd;
    }

    public List<List<String>> getAnagrams(String[] strs) {
        List<String> s = Arrays.asList(strs);
        Map<String, List<String>> arrayToString = new HashMap<>();
        s.forEach(string -> {
            int[] letters = new int[26];
            for (int i = 0; i < string.length(); i++) {
                char c = string.charAt(i);
                letters[c - 'a']++;
            }
            if (arrayToString.get(Arrays.toString(letters)) == null) {
                arrayToString.put(Arrays.toString(letters), new ArrayList<>(List.of(string)));
            } else {
                arrayToString.get(Arrays.toString(letters)).add(string);
            }
        });
        return new ArrayList<>(arrayToString.values());
    }
}
