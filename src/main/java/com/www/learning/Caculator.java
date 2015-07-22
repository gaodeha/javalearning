package com.www.learning;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 简单计算表达式的程序
 * 例如：
 * String exp = "23+12*(1+3)/3";
 * caculate(exp) 返回39
 * 
 * 主要是练习数据结构 栈的使用，以及 中缀表达式、后缀表达式之间的转换
 * @author wuwei
 *
 */
public class Caculator {
	
	private Stack<Character> operateStack = new Stack<Character>();
	
	private Stack<Integer> numberStack = new Stack<Integer>();
	
	private static final char SPLIT_NUM_CHAR = 's';
	
	
	public int caculate(String exp){
		
		checkValidExp(exp);
		
		String suffix = convertToSuffix(exp);
		
		return caculateSuffix(suffix);
	}
	
	/**
	 * 简单的正则过滤，还剩一些规则会在计算中校验 比如括号的嵌套
	 * @param exp
	 */
	private void checkValidExp(String exp){
		if(exp == null || exp.trim().length() ==0){
			throw new IllegalArgumentException("exp can not be null");
		}
		Pattern p = Pattern.compile("^[\\d+*-/()]+$");
		Matcher m = p.matcher(exp);
		if(!m.matches()){
			throw new IllegalArgumentException("exp contains invalid character");
		}
	}
	
	/**
	 * 将前缀表达式 转化为后缀表达式
	 * 例如：
	 * exp=1+2*3+6/2; suffix=1s2s3s*+6s2s/+
	 * exp=10-4; suffix=10s4s-
	 * 这里要注意多位数 之间要有字符分隔
	 * @param exp
	 * @return
	 */
	private String convertToSuffix(String exp){
		StringBuilder sb = new StringBuilder();
		Map<Character, Integer> priority = new HashMap<Character, Integer>();
		
		priority.put('*', 50);
		priority.put('/', 50);
		priority.put('+', 20);
		priority.put('-', 20);
		priority.put('(', 1);
		priority.put(')', 1);
		char[] chars = exp.toCharArray();
		
		for(int i=0; i<chars.length; i++){
			char c = chars[i];
			if(c >= '0' && c <= '9'){
				boolean isNumberEnd = false;
				if(i == chars.length - 1){
					isNumberEnd = true;
				}else{
					char next = chars[i+1];
					isNumberEnd = next < '0' || next > '9';
				}
				sb.append(c);
				if(isNumberEnd){
					//如果下一个字符不是数字，用s 做数字的标记结束
					sb.append(SPLIT_NUM_CHAR);
				}
				continue;
			}else{
				
				if(c == '('){
					operateStack.push(c);
					continue;
				}else if(c == ')'){
					char last = '$'; //随意一个初始的字符，只要不是'('即可
					while(last != '(' && !operateStack.empty()){
						last = operateStack.pop();
						if(last != '('){
							sb.append(last);
						}
					}
					if(last != '('){
						throw new IllegalArgumentException("brackets does not match");
					}
					continue;
				}
				int pri = priority.get(c);
				if(pri == 0){
					throw new IllegalArgumentException("exp contains invalid character");
				}
				if(operateStack.empty()){
					operateStack.push(c);
				}else{
					Character last = operateStack.lastElement();
					int lastPri = priority.get(last);
					while(lastPri >= pri){
						last = operateStack.pop();
						sb.append(last);
						if(operateStack.empty()){
							break;
						}
						last = operateStack.lastElement();
						lastPri = priority.get(last);
					}
					operateStack.push(c);
				}
			}
		}
		while(!operateStack.empty()){
			sb.append(operateStack.pop());
		}
		System.out.println("exp=" + exp + "; suffix=" + sb.toString());
		return sb.toString();
	}
	
	/**
	 * 计算后缀表达式
	 * @param suffix
	 * @return
	 */
	private int caculateSuffix(String suffix){
		char[] chars = suffix.toCharArray();
		StringBuilder sbnum = new StringBuilder();
		for(int i=0; i<chars.length; i++){
			char c = chars[i];
			if(c >= '0' && c <= '9'){
				sbnum.append(c);
				if(i == chars.length -1){
					numberStack.push(Integer.parseInt(sbnum.toString()));
					break;
				}
				char next = chars[i+1];
				if(next < '0' || next > '9'){
					numberStack.push(Integer.parseInt(sbnum.toString()));
					sbnum = new StringBuilder();
				}
				if(next == SPLIT_NUM_CHAR){
					i++;
				}
				continue;
			}else{
				if(numberStack.size() < 2){
					throw new IllegalArgumentException("invalid exp");
				}
				int second = numberStack.pop();
				int first = numberStack.pop();
				switch(c){
				case '+':
					numberStack.push(first + second);
					break;
				case '/':
					numberStack.push(first / second);
					break;
				case '*':
					numberStack.push(first * second);
					break;
				case '-':
					numberStack.push(first - second);
					break;
				default:
					throw new IllegalArgumentException("invalid exp");
				}
			}
		}
		if(numberStack.size() != 1){
			throw new IllegalArgumentException("invalid exp");
		}
		return numberStack.pop();
	}
	

	public static void main(String[] args) {
		
	}

}
